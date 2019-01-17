package id.bengkelaplikasi.ewarga.views.menus.login;

import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface LoginView extends View{

    void callHome();
    void showMessage(String message);
    void enableProgressBar();
    void disableProgressBar();

}
