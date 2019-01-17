package id.bengkelaplikasi.ewarga.views.menus.home;


import id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga.adapter.DaftarWargaModel;

/**
 * Created by Kukuh182 on 10-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface Communicator {
    void sendLongLatToLocation(String nama, double longitude, double latitude);
    void callAboutUs();
    void callChangePassword();
    void callDetailLibraries();
}
