package id.bengkelaplikasi.ewarga.views.menus.login;

import android.app.Activity;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.dao.ProfilWargaDao;
import id.bengkelaplikasi.ewarga.dao.RealmCallBack;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class LoginPresenter implements Presenter<LoginView> {

    private LoginView mView;
    private Activity activity;
    private ProfilWargaDao dao;


    public LoginPresenter(Activity activity){
        this.activity = activity;
        this.dao = new ProfilWargaDao();

    }

    @Override
    public void onAttach(LoginView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }

    public void validationLogin(String email, String password) {
        //get device id
        String deviceid = Utilities.getDeviceID(activity);
        if(!TextUtils.isEmpty(deviceid)){
            //check internet connection
            if (Utilities.isInternetAvailable()) {

                if (TextUtils.isEmpty(email)) {
                    if(mView!=null){
                        mView.showMessage("Email Kosong");
                    }
                }else if (TextUtils.isEmpty(password)) {
                    if(mView!=null){
                        mView.showMessage("Password Kosong");
                    }
                } else {
                    if(!Utilities.isValidEmail(email)){
                        if(mView!=null){
                            mView.showMessage("Email Tidak Valid Kosong");
                        }
                    }else {
                        loginToServer(email, Utilities.sha1(password), deviceid);
                    }
                }
            } else {
                if(mView!=null){
                    mView.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            }
        }
    }

    public void clearSessionRegister(){
        SharedPref.saveString(Constanta.RegPreference.PROV_KODE,"");
        SharedPref.saveString(Constanta.RegPreference.PROV_NAMA,"");
        SharedPref.saveString(Constanta.RegPreference.KOTA_KODE,"");
        SharedPref.saveString(Constanta.RegPreference.KOTA_NAMA,"");
        SharedPref.saveString(Constanta.RegPreference.KEC_KODE,"");
        SharedPref.saveString(Constanta.RegPreference.KEC_NAMA,"");
        SharedPref.saveString(Constanta.RegPreference.KEC_KDPOS,"");
        SharedPref.saveString(Constanta.RegPreference.KEL_KODE,"");
        SharedPref.saveString(Constanta.RegPreference.KEL_NAMA,"");
        SharedPref.saveInt(Constanta.RegPreference.STAT_WILAYAH,0);
    }

    private void loginToServer(String email, final String password, String deviceid) {
        mView.enableProgressBar();
        HashMap<String, String> params = new HashMap<>();
        params.put("sysusermobile_username", email);
        params.put("sysusermobile_passw", password);
        params.put("sysusermobile_deviceid", deviceid);
        Service service = Client.initialize(Constanta.Url.APP);
        Client.request(service.Login(params), new Client.CallBackRequest() {
            @Override
            public void successCode(String message, String rd) {
                if(mView!=null){
                    mView.disableProgressBar();
                    checkResponseLogin(message, rd, password);

                }
            }

            @Override
            public void unsuccessCode(String rc, String rm) {
                if(mView!=null){
                    mView.disableProgressBar();
                    mView.showMessage(rm);
                }
            }

            @Override
            public void unsuccessResponse(String message) {
                if(mView!=null){
                    mView.disableProgressBar();
                    mView.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            }

            @Override
            public void parsingError(String message) {
                if(mView!=null){
                    mView.disableProgressBar();
                    mView.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            }

            @Override
            public void otherError(String message) {
                if(mView!=null){
                    mView.disableProgressBar();
                    mView.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            }

            @Override
            public void failure(String message) {
                if(mView!=null){
                    mView.disableProgressBar();
                    mView.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            }
        });
    }

    private void checkResponseLogin(final String rm, String rd, final String password) {

        try{
            JSONObject obj2 = new JSONObject(rd);
            ProfilWarga profilWarga = new ProfilWarga();
            final int id = obj2.getInt("sysusermobile_id");
            final String username = obj2.getString("sysusermobile_username");
            final String deviceid = obj2.getString("sysusermobile_deviceid");
            final String privinfo = obj2.getString("sysusermobile_privinfo");
            final String group_darurat = obj2.getString("rgroup_darurat");
            final String rmember_id = obj2.getString("rmember_id");

            profilWarga.setSysusermobile_id(id);
            profilWarga.setSysusermobile_username(username);
            profilWarga.setSysusermobile_deviceid(deviceid);
            profilWarga.setRktp_nik(obj2.getString("rktp_nik"));
            profilWarga.setRktp_nama(obj2.getString("rktp_nama"));
            profilWarga.setRktp_tptlahir(obj2.getString("rktp_tptlahir"));
            profilWarga.setRktp_tgllahir(obj2.getString("rktp_tgllahir"));
            profilWarga.setRktp_alamat(obj2.getString("rktp_alamat"));
            profilWarga.setRktp_rt(obj2.getString("rktp_rt"));
            profilWarga.setRktp_rw(obj2.getString("rktp_rw"));
            profilWarga.setRktp_long(obj2.getString("rktp_long"));
            profilWarga.setRktp_lat(obj2.getString("rktp_lat"));
            profilWarga.setRprov_kode(obj2.getString("rprov_kode"));
            profilWarga.setRkota_kode(obj2.getString("rkota_kode"));
            profilWarga.setRkec_kode(obj2.getString("rkec_kode"));
            profilWarga.setRkel_kode(obj2.getString("rkel_kode"));
            profilWarga.setRmember_id(rmember_id);

            dao.add(profilWarga, new RealmCallBack() {
                @Override
                public void result(boolean status) {
                    if(status){
                        SharedPref.saveInt(Constanta.Preference.ID, id);
                        SharedPref.saveString(Constanta.Preference.USERNAME, username);
                        SharedPref.saveString(Constanta.Preference.PASSWORD, password);
                        SharedPref.saveString(Constanta.Preference.DEVICEID, deviceid);
                        SharedPref.saveString(Constanta.Preference.PRIVINFO, privinfo);
                        SharedPref.saveString(Constanta.Preference.RMEMBERID, rmember_id);
                        SharedPref.saveString(Constanta.Preference.GROUP_DARURAT, group_darurat);
                        if(mView!=null){
                            mView.callHome();
                        }
                    } else {
                        if(mView!=null){
                            mView.showMessage(rm);
                        }
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
            if(mView!=null){
                mView.showMessage(Constanta.Message.ERROR_PARSING);
            }
        }
    }

}
