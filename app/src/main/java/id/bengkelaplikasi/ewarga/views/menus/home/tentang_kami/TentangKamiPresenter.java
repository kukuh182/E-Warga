package id.bengkelaplikasi.ewarga.views.menus.home.tentang_kami;

import android.app.Activity;

import id.bengkelaplikasi.ewarga.views.base.Presenter;
import id.bengkelaplikasi.ewarga.views.menus.home.Communicator;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class TentangKamiPresenter implements Presenter<TentangKamiView> {

    private TentangKamiView mView;
    private Activity activity;
    private Communicator communicator;

    public TentangKamiPresenter (Activity mActivity){
        this.activity = mActivity;
        this.communicator = (Communicator) activity;
    }

    @Override
    public void onAttach(TentangKamiView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }

    public void showDeatilLibraries(){
        communicator.callDetailLibraries();
    }
}
