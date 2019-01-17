package id.bengkelaplikasi.ewarga.views.menus.home.beranda.peta;

import java.util.List;

import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface PetaView extends View{
    void closePeta();
    void addActionNavigationClose();
    void showDaftarPeta();
    void showMessage(String message);
    void setAction(List<PetaModel> mData);
    void receiveKodePeta();
}
