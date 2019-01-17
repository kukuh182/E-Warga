package id.bengkelaplikasi.ewarga.views.menus.home.beranda.tandailokasi;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.Dialog;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.main.App;
import id.bengkelaplikasi.ewarga.models.KategoriFasum;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by KODEOK-KUKUH on 20-Nov-17.
 */

public class TandaiLokasiActivity extends AppCompatActivity implements TandaiLokasiView, OnMapReadyCallback {

    TandaiLokasiPresenter presenter;
    MaterialDialog progressDialog;
    List<KategoriFasum> kategoriFasums;
    LatLng latLng = null;
    GoogleMap mMap;
    Timer timer;
    TimerTask doAsyncTask;
    Integer countLoop = 0;
    protected Boolean timerTask = false;


    @BindView(R.id.toolbar_tandailokasi) Toolbar toolbar_tandailokasi;
    @BindView(R.id.acs_kategorifasum) AppCompatSpinner acs_kategorifasum;
    @BindView(R.id.aet_nama_lokasi) AppCompatEditText aet_nama;
    @BindView(R.id.aet_keterangan_lokasi) AppCompatEditText aet_keterangan;
    @BindView(R.id.acb_kirimtandailokasi) AppCompatButton acb_kirimtandailokasi;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tandai_lokasi);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onBackPressed() {
        closeTandaiLokasi();
    }

    @Override
    public void onAttachView() {
        presenter = new TandaiLokasiPresenter(this, this);
        presenter.onAttach(this);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_tandailokasi);
        mapFragment.getMapAsync(this);
        addActionNavigationClose();
        presenter.getListKategoriFasum();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy(){
        onDetachView();
        super.onDestroy();
    }

    @Override
    public void showProgressDialog(String title, String content) {
        progressDialog = Dialog.progress(this, title, content, false);
    }

    @Override
    public void hideProgressDialog() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showDialogInformation(String title, String content, final boolean status) {
        Dialog.basic(this, title, content, "OK", false, new Dialog.CallBack1() {
            @Override
            public void onPositive(MaterialDialog md, DialogAction which) {
                md.dismiss();
                if(status){
                    closeTandaiLokasi();
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(toolbar_tandailokasi, message);
    }

    @Override
    public void addActionNavigationClose() {
        toolbar_tandailokasi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeTandaiLokasi();
            }
        });
    }

    @Override
    public void closeTandaiLokasi() {
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void showListKategoriFasum(List<KategoriFasum> datas) {
        kategoriFasums = datas;
        List<String> data = new ArrayList<>();
        for (KategoriFasum kategoriFasum : kategoriFasums){
            data.add(kategoriFasum.getDeskripsi());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(App.getContext(),
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acs_kategorifasum.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showMyLocation();
    }
    @Override
    public void showMyLocation(){
        latLng = presenter.getCurrentLocation();
        if (latLng!=null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constanta.Preference.CENTER_INA,5));
            Marker nMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Your Location"));
            nMarker.showInfoWindow();

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(17)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setRotateGesturesEnabled(true);
            countLoop++;
            timerTask = true;
        }else {
            showMessage("Belum Menemukan Lokasi");
        }

        if (countLoop > 1) {
            countLoop = 0;
        }
        callAsyncTask();
    }

    @Override
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

    @Override
    public void setRefresh() {
        latLng = presenter.getCurrentLocation();

        if (countLoop <= 10) {
            if (latLng!=null){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constanta.Preference.CENTER_INA,5));
                Marker nMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Your Location"));
                nMarker.showInfoWindow();

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)
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
            showMyLocation();
        }
    }

    @OnClick(R.id.acb_kirimtandailokasi)
    public void kirimLokasi(){
        String nama_lokasi = aet_nama.getText().toString();
        String keterangan = aet_keterangan.getText().toString();
        String kategori = kategoriFasums.get(acs_kategorifasum.getSelectedItemPosition()).getKode();
        presenter.validasi(nama_lokasi, keterangan, kategori,latLng);
    }
}
