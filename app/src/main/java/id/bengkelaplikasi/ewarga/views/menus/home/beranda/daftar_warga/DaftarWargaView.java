package id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga;

import java.util.List;

import id.bengkelaplikasi.ewarga.views.base.View;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga.adapter.DaftarWargaModel;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface DaftarWargaView extends View{
    void closeDaftarWarga();
    void addActionNavigationClose();
    void showDataDaftarWarga();
    void enableProgressBar();
    void disableProgressBar();
    void showMessage(String message);
    void searchAction(List<DaftarWargaModel> data);
}
