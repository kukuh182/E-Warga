package id.bengkelaplikasi.ewarga.views.menus.home;

import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class HomePresenter implements Presenter<HomeView> {

    private HomeView mView;

    @Override
    public void onAttach(HomeView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
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
}
