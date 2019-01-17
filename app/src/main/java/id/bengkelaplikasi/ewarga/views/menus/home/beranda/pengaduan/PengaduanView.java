package id.bengkelaplikasi.ewarga.views.menus.home.beranda.pengaduan;

import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface PengaduanView extends View{
    void closePengaduan();
    void addActionNavigationClose();
    void showMessage(String message);
    void showProgressDialog(String title, String content);
    void hideProgressDialog();
}
