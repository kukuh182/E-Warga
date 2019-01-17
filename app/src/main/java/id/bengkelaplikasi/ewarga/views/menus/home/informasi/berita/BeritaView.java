package id.bengkelaplikasi.ewarga.views.menus.home.informasi.berita;

import java.util.List;

import id.bengkelaplikasi.ewarga.views.base.View;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter.InformasiModel;

/**
 * Created by Kukuh182 on 13-Aug-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface BeritaView extends View{
    void initData();
    void showMessage(String message);
    void enableProgressBar();
    void disableProgressBar();
    void searchAction(List<InformasiModel> data);
}
