package id.bengkelaplikasi.ewarga.views.menus.home.beranda.tandailokasi;

import java.util.List;

import id.bengkelaplikasi.ewarga.models.KategoriFasum;
import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by KODEOK-KUKUH on 20-Nov-17.
 */

public interface TandaiLokasiView extends View{

    void showProgressDialog(String title, String content);
    void hideProgressDialog();
    void showDialogInformation(String title, String content, boolean status);
    void showMessage(String message);
    void addActionNavigationClose();
    void closeTandaiLokasi();
    void showListKategoriFasum(List<KategoriFasum> datas);
    void showMyLocation();
    void callAsyncTask();
    void setRefresh();
}
