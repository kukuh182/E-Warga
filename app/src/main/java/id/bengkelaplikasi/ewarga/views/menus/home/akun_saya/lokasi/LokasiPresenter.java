package id.bengkelaplikasi.ewarga.views.menus.home.akun_saya.lokasi;

import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by Kukuh182 on 01-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class LokasiPresenter implements Presenter<LokasiView> {

    private LokasiView mView;

    @Override
    public void onAttach(LokasiView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }
}
