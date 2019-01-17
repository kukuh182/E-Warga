package id.bengkelaplikasi.ewarga.views.menus.register;

import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by Kukuh182 on 05-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface RegisterView extends View {

    void callLogin();
    void showDeskripsiWilayah();
    void showMessage(String message);
    void showDialogProgress(String title, String content);
    void hideDialogProgress();
    void showDialogRegisterSuccess();
    void actionNavigationToolbar();
}
