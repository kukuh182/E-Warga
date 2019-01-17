package id.bengkelaplikasi.ewarga.views.menus.home.akun_saya.lokasi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import id.bengkelaplikasi.ewarga.R;

/**
 * Created by Kukuh182 on 10-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class LokasiFragment extends Fragment implements LokasiView, OnMapReadyCallback {

    private LokasiPresenter presenter;
    private GoogleMap mMap;

    public static LokasiFragment newInstance() {
        return new LokasiFragment();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttachView();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lokasi, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onAttachView() {
        presenter = new LokasiPresenter();
        presenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    public void onDestroyView() {
        onDetachView();
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Bundle bundle = getArguments();
        double longitude = bundle.getDouble("longitude");
        double latitude = bundle.getDouble("latitude");
        String nama = bundle.getString("nama");

        mMap = googleMap;
        Log.e("LONGLAT","LONG : "+longitude+" == LATITUDE : "+latitude);
        LatLng lokasiWarga = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(lokasiWarga)
                .zoom(17)
                .build();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.addMarker(new MarkerOptions().position(lokasiWarga).title(nama));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
