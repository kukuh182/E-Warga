package id.bengkelaplikasi.ewarga.views.menus.home.beranda.list_peta;

import java.util.ArrayList;
import java.util.List;

import id.bengkelaplikasi.ewarga.views.base.Presenter;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.list_peta.adapter.ListPetaMA;

/**
 * Created by FLYCODE-KUKUH on 23-Nov-17.
 */

public class ListPetaPresenter implements Presenter<ListPetaView> {

    ListPetaView view;

    @Override
    public void onAttach(ListPetaView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public void getListPeta(){
        String[] data = {"Tempat Ibadah", "Pendidikan", "Kantor Polisi"};
        List<ListPetaMA> listPetaMA = new ArrayList<>();
        for (int i = 0; i<data.length; i++){
            ListPetaMA model = new ListPetaMA();
            model.setTitle(data[i]);
            listPetaMA.add(model);
        }
        view.showDaftarPeta(listPetaMA);
    }

    public void checkMenu(String menu){
        String[] kode_peta = new String[]{};
        switch (menu){
            case "Tempat Ibadah":
                kode_peta = new String[]{"001","002","003","004"};
                break;
            case "Pendidikan":
                kode_peta = new String[]{"101","102","103","104","105","106","107","108","109","110",
                        "111","112","113","114","115","116","117","118","119","120","121","122","123","124","125","126","127",
                        "128","129","130","199"};
                break;
            case "Kantor Polisi":
                kode_peta = new String[]{"801"};
                break;
        }

        view.callPeta(kode_peta);
    }
}
