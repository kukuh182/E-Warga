package id.bengkelaplikasi.ewarga.views.menus.home.akun_saya;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Dialog;
import id.bengkelaplikasi.ewarga.helper.Permissions;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.views.menus.home.Communicator;
import id.bengkelaplikasi.ewarga.views.menus.login.LoginActivity;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class AkunSayaFragment extends Fragment implements  AkunSayaView{

    private AkunSayaPresenter presenter;
    private View view;
    private ProfilWarga warga;
    private MaterialDialog progressDialog;
    private Communicator communicator;

    @BindView(R.id.fat_profil_nama)AppCompatTextView fat_profil_nama;
    @BindView(R.id.fat_profil_nik)AppCompatTextView fat_profil_nik;
    @BindView(R.id.aet_profil_tptlahir)AppCompatEditText aet_profil_tptlahir;
    @BindView(R.id.aet_profil_tgllahir)AppCompatEditText aet_profil_tgllahir;
    @BindView(R.id.aet_profil_alamat)AppCompatEditText aet_profil_alamat;
    @BindView(R.id.aet_profil_rt)AppCompatEditText aet_profil_rt;
    @BindView(R.id.aet_profil_rw)AppCompatEditText aet_profil_rw;
    @BindView(R.id.aet_profil_lokasi)AppCompatEditText aet_profil_lokasi;
    @BindView(R.id.ll_profil_tampil_lokasi)LinearLayout ll_profil_tampil_lokasi;
    @BindView(R.id.ll_profil_update_lokasi)LinearLayout ll_profil_update_lokasi;
    @BindView(R.id.ll_profil_ganti_password)LinearLayout ll_profil_ganti_password;
    @BindView(R.id.ll_profil_logout)LinearLayout ll_profil_logout;


    public static AkunSayaFragment newInstance() {
        return new AkunSayaFragment();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttachView();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_akunsaya, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttachView() {
        presenter = new AkunSayaPresenter(getActivity(),getContext());
        presenter.onAttach(this);
        communicator = (Communicator) getActivity();
        presenter.getDataProfil();
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
    public void showProfil(ProfilWarga profilWarga) {
        this.warga = profilWarga;
        String lokasi = "Long : "+profilWarga.getRktp_long()+"; Lat : "+profilWarga.getRktp_lat();
        fat_profil_nama.setText(profilWarga.getRktp_nama());
        fat_profil_nik.setText(profilWarga.getRktp_nik());
        aet_profil_tptlahir.setText(profilWarga.getRktp_tptlahir());
        aet_profil_tgllahir.setText(profilWarga.getRktp_tgllahir());
        aet_profil_alamat.setText(profilWarga.getRktp_alamat());
        aet_profil_rt.setText(profilWarga.getRktp_rt());
        aet_profil_rw.setText(profilWarga.getRktp_rw());
        aet_profil_lokasi.setText(lokasi);
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(view,message);
    }

    @Override
    public void showProgressDialog(String title, String content) {
        progressDialog = Dialog.progress(getActivity(), title, content, false);
    }

    @Override
    public void hideProgressDialog() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void callLogin() {
        startActivity(new Intent(getActivity(),LoginActivity.class));
        getActivity().finish();
        getActivity().overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @OnClick(R.id.ll_profil_tampil_lokasi)
    public void callLocation(){
        String [] permission = {Permissions.Location.ACCESS_COARSE_LOCATION, Permissions.Location.ACCESS_FINE_LOCATION};
        if(Utilities.checkPermission(getActivity(), permission)){
            presenter.showLocation();
        }
    }

    @OnClick(R.id.ll_profil_update_lokasi)
    public void updateLocation(){
        //Get location
        Dialog.basic(getActivity(), "Update Lokasi", "Melakukan update lokasi terkini",
                "YA", "TIDAK", false, new Dialog.CallBack2() {
                    @Override
                    public void onPositive(MaterialDialog md, DialogAction which) {
                        LatLng latLng = presenter.getCurrentLocation();
                        if (latLng!=null){
                            presenter.updateLocationToServer(latLng, md);
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog md, DialogAction which) {
                        md.dismiss();
                    }
                });
    }

    @OnClick(R.id.ll_profil_ganti_password)
    public void ganti_password(){
        communicator.callChangePassword();
    }

    @OnClick(R.id.ll_profil_logout)
    public void logout(){
        Dialog.basic(getActivity(), "Logout", "Apakah anda mau keluar dari aplikasi ini ?",
                "YA", "TIDAK", false, new Dialog.CallBack2() {
                    @Override
                    public void onPositive(MaterialDialog md, DialogAction which) {
                        presenter.clearDataProfilWarga(md);
                    }

                    @Override
                    public void onNegative(MaterialDialog md, DialogAction which) {
                        md.dismiss();
                    }
                });
    }
}
