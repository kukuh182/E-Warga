package id.bengkelaplikasi.ewarga.views.menus.home.akun_saya;

import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface AkunSayaView extends View{
    void showProfil(ProfilWarga profilWarga);
    void showMessage(String message);
    void showProgressDialog(String title, String content);
    void hideProgressDialog();
    void callLogin();
}
