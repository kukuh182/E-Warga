package id.bengkelaplikasi.ewarga.views.menus.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.Dialog;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.menus.login.LoginActivity;
import id.bengkelaplikasi.ewarga.views.menus.register.wilayah.WilayahActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Kukuh182 on 05-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    RegisterPresenter presenter;
    MaterialDialog md = null;

    @BindView(R.id.toolbar_reg)Toolbar toolbar_reg;
    @BindView(R.id.et_reg_nik)AppCompatEditText et_reg_nik;
    @BindView(R.id.et_reg_email)AppCompatEditText et_reg_email;
    @BindView(R.id.et_reg_namalengkap)AppCompatEditText et_reg_namalengkap;
    @BindView(R.id.et_reg_password)AppCompatEditText et_reg_password;
    @BindView(R.id.et_reg_repassword)AppCompatEditText et_reg_repassword;
    @BindView(R.id.et_reg_smartphone)AppCompatEditText et_reg_smartphone;
    @BindView(R.id.et_reg_phone)AppCompatEditText et_reg_phone;
    @BindView(R.id.et_rt)AppCompatEditText et_rt;
    @BindView(R.id.et_rw)AppCompatEditText et_rw;
    @BindView(R.id.et_alamat)AppCompatEditText et_alamat;
    @BindView(R.id.atv_deskripsi_wilayah)AppCompatTextView atv_deskripsi_wilayah;
    @BindView(R.id.et_kodepos)AppCompatEditText et_kodepos;
    @BindView(R.id.acb_pilih_wilayah)AppCompatButton acb_pilih_wilayah;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onBackPressed() {
        callLogin();
    }

    @Override
    public void onAttachView() {
        presenter = new RegisterPresenter(this);
        presenter.onAttach(this);
        ButterKnife.bind(this);
        showDeskripsiWilayah();
        actionNavigationToolbar();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy() {
        onDetachView();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        showDeskripsiWilayah();
    }


    @OnClick(R.id.acb_pilih_wilayah)
    public void callWilayah(){
        SharedPref.saveInt(Constanta.RegPreference.STAT_WILAYAH,1);
        startActivity(new Intent(this,WilayahActivity.class));
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void callLogin() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void showDeskripsiWilayah() {

        String provinsi = SharedPref.getString(Constanta.RegPreference.PROV_NAMA);
        String kabkot = SharedPref.getString(Constanta.RegPreference.KOTA_NAMA);
        String kecamatan = SharedPref.getString(Constanta.RegPreference.KEC_NAMA);
        String kdpos = SharedPref.getString(Constanta.RegPreference.KEC_KDPOS);
        String kelurahan = SharedPref.getString(Constanta.RegPreference.KEL_NAMA);
        String desc_wilayah = provinsi+" | "+kabkot+" | "+kecamatan+" | "+kelurahan;
        atv_deskripsi_wilayah.setAllCaps(false);
        atv_deskripsi_wilayah.setText(desc_wilayah);
        et_kodepos.setText(kdpos);
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(acb_pilih_wilayah, message);
    }

    @Override
    public void showDialogProgress(String title, String content) {
        md = Dialog.progress(this, title, content, false);
    }

    @Override
    public void hideDialogProgress() {
        if(md!=null){
            md.dismiss();
        }
    }

    @Override
    public void showDialogRegisterSuccess() {
        Dialog.basic(this, "Registrasi", "Registrasi Sukses\nSilakan Login Aplikasi",
                "OK", false, new Dialog.CallBack1() {
                    @Override
                    public void onPositive(MaterialDialog md, DialogAction which) {
                        md.dismiss();
                       callLogin();
                    }
                });
    }

    @Override
    public void actionNavigationToolbar() {
        toolbar_reg.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                callLogin();
            }
        });
    }

    @OnClick(R.id.fab_register)
    public void registerProcess(){
        RegisterModel model = new RegisterModel();
        model.setNik(et_reg_nik.getText().toString());
        model.setNama_lengkap(et_reg_namalengkap.getText().toString());
        model.setPassword(et_reg_password.getText().toString());
        model.setRepassword(et_reg_repassword.getText().toString());
        model.setEmail(et_reg_email.getText().toString());
        model.setSmartphone(et_reg_smartphone.getText().toString());
        model.setPhone(et_reg_phone.getText().toString());
        model.setRt(et_rt.getText().toString());
        model.setRw(et_rw.getText().toString());
        model.setKode_pos(et_kodepos.getText().toString());
        model.setAlamat(et_alamat.getText().toString());
        model.setProv_kode(SharedPref.getString(Constanta.RegPreference.PROV_KODE));
        model.setKota_kode(SharedPref.getString(Constanta.RegPreference.KOTA_KODE));
        model.setKec_kode(SharedPref.getString(Constanta.RegPreference.KEC_KODE));
        model.setKel_kode(SharedPref.getString(Constanta.RegPreference.KEL_KODE));
        presenter.validasiRegistrasi(model);
    }
}
