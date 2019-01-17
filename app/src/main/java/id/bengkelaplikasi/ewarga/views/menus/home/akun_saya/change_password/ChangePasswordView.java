package id.bengkelaplikasi.ewarga.views.menus.home.akun_saya.change_password;

import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by Kukuh182 on 18-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface ChangePasswordView extends View {
    void enableProgressBar();
    void disableProgressBar();
    void showMessage(String message);
}
