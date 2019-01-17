package id.bengkelaplikasi.ewarga.views.menus.home.akun_saya.change_password;

import android.text.TextUtils;

import java.util.HashMap;

import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by Kukuh182 on 18-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class ChangePasswordPresenter implements Presenter<ChangePasswordView> {

    private ChangePasswordView mView;

    @Override
    public void onAttach(ChangePasswordView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }

    public void validationChangePassword(String password_old, String password_new, String password_renew){
        int id = SharedPref.getInt(Constanta.Preference.ID);
        if(id==0){
            mView.showMessage("Data Warga Kosong");
        }else if(TextUtils.isEmpty(password_old)){
            mView.showMessage("Password Lama Kosong");
        }else if (TextUtils.isEmpty(password_new)){
            mView.showMessage("Password Baru Kosong");
        }else if (TextUtils.isEmpty(password_renew)){
            mView.showMessage("Konfirmasi Password Kosong");
        }else {
            if(Utilities.sha1(password_old).equals(SharedPref.getString(Constanta.Preference.PASSWORD))){
                if(password_new.equals(password_renew)){
                    if (Utilities.isInternetAvailable()) {
                        changePasswordToServer(password_old, password_new, id);
                    }else {
                        mView.showMessage("Tidak Terkonesi Internet");
                    }

                }else {
                    mView.showMessage("Password Tidak Sesuai");
                }
            }else {
                mView.showMessage("Password Lama Salah");
            }
        }
    }

    private void changePasswordToServer(String password_old, final String password_new, int id){
        mView.enableProgressBar();
        HashMap<String, String> params = new HashMap<>();
        params.put("sysusermobile_passw_old", Utilities.sha1(password_old));
        params.put("sysusermobile_passw", Utilities.sha1(password_new));
        params.put("sysusermobile_id", String.valueOf(id));
        Service service = Client.initialize(Constanta.Url.APP);
        Client.request(service.ubahPassword(params), new Client.CallBackRequest() {
            @Override
            public void successCode(String message, String rd) {
                mView.disableProgressBar();
                SharedPref.saveString(Constanta.Preference.PASSWORD, Utilities.sha1(password_new));
                mView.showMessage(message);
            }

            @Override
            public void unsuccessCode(String rc, String rm) {
                mView.disableProgressBar();
                mView.showMessage(rm);
            }

            @Override
            public void unsuccessResponse(String message) {
                mView.disableProgressBar();
                mView.showMessage(Constanta.Message.ERR_CONNECTION);
            }

            @Override
            public void parsingError(String message) {
                mView.disableProgressBar();
                mView.showMessage(Constanta.Message.ERR_CONNECTION);
            }

            @Override
            public void otherError(String message) {
                mView.disableProgressBar();
                mView.showMessage(Constanta.Message.ERR_CONNECTION);
            }

            @Override
            public void failure(String message) {
                mView.disableProgressBar();
                mView.showMessage(Constanta.Message.ERR_CONNECTION);
            }
        });
    }
}
