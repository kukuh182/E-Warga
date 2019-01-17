package id.bengkelaplikasi.ewarga.views.menus.home.beranda.peta;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class PetaActivity extends AppCompatActivity implements PetaView, OnMapReadyCallback {

    PetaPresenter presenter;
    GoogleMap mMap;
    String[] kode_peta = new String[]{};
    private List<PetaModel> data = new ArrayList<>();
    public static Double newLat = null;
    public static Double newLong = null;
    Timer timer;
    TimerTask doAsyncTask;
    Integer countLoop = 0;
    ProfilWarga warga;
    protected Boolean timerTask = false;

    @BindView(R.id.toolbar_peta)Toolbar toolbar_peta;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onBackPressed() {
        closePeta();
    }


    @Override
    public void onAttachView() {
        presenter = new PetaPresenter(this, this);
        presenter.onAttach(this);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        addActionNavigationClose();
        receiveKodePeta();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy(){
        if(timerTask) {
            timer.cancel();
            doAsyncTask.cancel();
            timerTask = false;
        }
        onDetachView();
        super.onDestroy();
    }

    @Override
    public void closePeta() {
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void addActionNavigationClose() {
        toolbar_peta.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePeta();
            }
        });
    }

    @Override
    public void showDaftarPeta() {
        LatLng latLng = presenter.getCurrentLocation();
        if (latLng != null) {
            newLat = latLng.latitude;
            newLong = latLng.longitude;
            presenter.getListFasumToServer(new PetaPresenter.callBackPetaWarga() {
                @Override
                public void result(List<PetaModel> data, String message) {
                    if (data != null) {
                        setAction(data);
                    } else {
                        showMessage(message);
                    }
                }
            });
        } else {
            showMessage("Lokasi Tidak Ditemukan, Silakan Update Lokasi");
        }
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(toolbar_peta, message);
    }

    @Override
    public void setAction(List<PetaModel> mData) {
        this.data = mData;
        LatLng lokasiWarga = null;
        warga = presenter.getDataWarga();

        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                try {
                    String nama = data.get(i).getRktpNama();
                    String alamat = data.get(i).getRktpAlamat();
                    String rw = data.get(i).getRktpRw();
                    String rt = data.get(i).getRktpRt();
                    String type = data.get(i).getrType();
                    double latitude = Double.parseDouble(data.get(i).getrLatitude());
                    double longitude = Double.parseDouble(data.get(i).getrLongitude());

                    for (int x = 0; x<kode_peta.length; x++){

                        if(type.equals(kode_peta[x])){
                            String info = alamat + " RT/RW " +
                                    rw + "/" + rt;

                            lokasiWarga = new LatLng(latitude, longitude);

                            BitmapDescriptor bmp = Utilities.getBitmap(type);


                            Marker nMarker = mMap.addMarker(
                                    new MarkerOptions()
                                            .position(lokasiWarga)
                                            .title(nama)
                                            .icon(bmp)
                                            .snippet(info)
                            );
                        }
                    }

                } catch (Exception e) {
                    showMessage("Lokasi Tidak Ditemukan, Silakan Update Lokasi");
                }
            }
        }

        if (countLoop > 1) {
            countLoop = 0;
        }
        callAsyncTask();
    }

    @Override
    public void receiveKodePeta() {
        Bundle b = getIntent().getExtras();
        kode_peta = b.getStringArray("kode_peta");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constanta.Preference.CENTER_INA,5));
        showDaftarPeta();
    }

    public void callAsyncTask(){
        final Handler handler = new Handler();
        timer = new Timer();
        doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                boolean post = handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            mMap.clear();
                            setRefresh();
                        } catch (Exception e) {


                        }
                    }
                });
            }
        };

        timer.schedule(doAsyncTask,0,10000);
        timerTask = true;
    }

    public void setRefresh() {
        LatLng latLng = presenter.getCurrentLocation();
        LatLng lokasiWarga = null;

        if (countLoop <= 10) {
            if (latLng != null) {
                newLat = latLng.latitude;
                newLong = latLng.longitude;
                if (data != null) {
                    for (int i = 0; i < data.size(); i++) {
                        try {
                            String nama = data.get(i).getRktpNama();
                            String alamat = data.get(i).getRktpAlamat();
                            String rw = data.get(i).getRktpRw();
                            String rt = data.get(i).getRktpRt();
                            String type = data.get(i).getrType();
                            double latitude = Double.parseDouble(data.get(i).getrLatitude());
                            double longitude = Double.parseDouble(data.get(i).getrLongitude());

                            for (int x = 0; x<kode_peta.length; x++) {

                                if (type.equals(kode_peta[x])) {
                                    String info = alamat + " RT/RW " +
                                            rw + "/" + rt;

                                    lokasiWarga = new LatLng(latitude, longitude);
                                    BitmapDescriptor bmp = Utilities.getBitmap(type);


                                    Marker nMarker = mMap.addMarker(
                                            new MarkerOptions()
                                                    .position(lokasiWarga)
                                                    .title(nama)
                                                    .icon(bmp)
                                                    .snippet(info)
                                    );
                                }
                            }


                        } catch (Exception e) {
                            showMessage("Lokasi Tidak Ditemukan, Silakan Update Lokasi");
                        }
                    }
                }

                String nama = warga.getRktp_nama();
                Double latitude = newLat;
                Double longitude = newLong;

                String info = String.valueOf(latitude) + ", " + String.valueOf(longitude);

                lokasiWarga = new LatLng(latitude, longitude);
                Marker nMarker = mMap.addMarker(
                        new MarkerOptions()
                                .position(lokasiWarga)
                                .title(nama)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                                .snippet(info)
                );

                nMarker.showInfoWindow();

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(lokasiWarga)
                        .zoom(17)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setRotateGesturesEnabled(true);
                countLoop++;
                timerTask = true;
            }
        } else {
            timer.cancel();
            doAsyncTask.cancel();
            timerTask = false;
            showDaftarPeta();
        }
    }

}
