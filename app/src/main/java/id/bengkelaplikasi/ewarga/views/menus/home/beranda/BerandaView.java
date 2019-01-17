package id.bengkelaplikasi.ewarga.views.menus.home.beranda;

import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface BerandaView extends View{

    void showMenuBeranda();
    void callAcara();
    void callPengaduan();
    void callPelayanan();
    void callDaftarWarga();
    void callPeta();
    void callTandaiLokasi();
    void showDialogProgress(String title, String content);
    void hideDialogProgress();
}
