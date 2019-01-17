package id.bengkelaplikasi.ewarga.views.menus.splash;

import android.os.Handler;
import android.text.TextUtils;

import id.bengkelaplikasi.ewarga.dao.ProfilWargaDao;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class SplashPresenter implements Presenter<SplashView> {

    private SplashView mView;
    private static final int SPLASH_TIMEOUT = 2000;
    private ProfilWarga warga;
    private ProfilWargaDao dao;

    public SplashPresenter(){
        this.warga = new ProfilWarga();
        this.dao = new ProfilWargaDao();
    }

    @Override
    public void onAttach(SplashView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }

    public void timerSplash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserExist();
            }
        }, SPLASH_TIMEOUT);
    }

    private void checkUserExist(){

        int id = SharedPref.getInt(Constanta.Preference.ID);
        String user = SharedPref.getString(Constanta.Preference.USERNAME);
        String pass = SharedPref.getString(Constanta.Preference.PASSWORD);
        String device = SharedPref.getString(Constanta.Preference.DEVICEID);

        if (TextUtils.isEmpty(user)||TextUtils.isEmpty(pass) || TextUtils.isEmpty(device)){
            mView.callLogin();
        }else{
            warga.setSysusermobile_id(id);
            warga = dao.get(warga);
            if(warga.getSysusermobile_id() == id &&
                    warga.getSysusermobile_username().equals(user) &&
                    warga.getSysusermobile_deviceid().equals(device)){
                mView.callHome();
            } else {
                mView.callLogin();
            }
        }
    }
}
