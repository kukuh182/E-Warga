package id.bengkelaplikasi.ewarga.views.menus.home.informasi;

import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class InformasiPresenter implements Presenter<InformasiView> {

    private InformasiView mView;

    @Override
    public void onAttach(InformasiView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }
}
