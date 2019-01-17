package id.bengkelaplikasi.ewarga.views.menus.home.tentang_kami.used_libraries;

import java.util.ArrayList;
import java.util.List;

import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.views.base.Presenter;
import id.bengkelaplikasi.ewarga.views.menus.home.tentang_kami.used_libraries.adapter.DetailLibrariesModel;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class UsedLibrariesPresenter implements Presenter<UsedLibrariesView>{

    private UsedLibrariesView mView;
    private static String [] content_libraries = {Constanta.Libraries.Content.retrofit,
            Constanta.Libraries.Content.glide, Constanta.Libraries.Content.butterknife,
            Constanta.Libraries.Content.expandabletextview, Constanta.Libraries.Content.materialdialog,
            Constanta.Libraries.Content.realm, Constanta.Libraries.Content.flaticon};

    @Override
    public void onAttach(UsedLibrariesView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }

    public List<DetailLibrariesModel> getlistLibraries(){
        List<DetailLibrariesModel> data = new ArrayList<>();
        int count_title = Constanta.Libraries.title.length;
        for (int i = 0; i<count_title; i++){
            DetailLibrariesModel item = new DetailLibrariesModel();
            item.setTitle(Constanta.Libraries.title[i]);
            item.setContent(content_libraries[i]);
            data.add(item);
        }
        return data;
    }
}
