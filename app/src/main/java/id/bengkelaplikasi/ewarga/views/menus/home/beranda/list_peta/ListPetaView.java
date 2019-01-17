package id.bengkelaplikasi.ewarga.views.menus.home.beranda.list_peta;

import java.util.List;

import id.bengkelaplikasi.ewarga.views.base.View;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.list_peta.adapter.ListPetaMA;

/**
 * Created by FLYCODE-KUKUH on 23-Nov-17.
 */

public interface ListPetaView extends View {

    void closeListPeta();
    void addActionNavigationClose();
    void showDaftarPeta(List<ListPetaMA> listPetaMAS);
    void showMessage(String message);
    void callPeta(String[] kode_peta);
}
