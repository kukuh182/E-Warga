package id.bengkelaplikasi.ewarga.views.menus.home.beranda.pelayanan;

import java.util.List;

import id.bengkelaplikasi.ewarga.models.GroupLayanan;
import id.bengkelaplikasi.ewarga.models.JenisLayanan;
import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by KODEOK-KUKUH on 12-Nov-17.
 */

public interface PelayananView extends View{

    void showProgressDialog(String title, String content);
    void hideProgressDialog();
    void showDialogInformation(String title, String content, boolean status);
    void showMessage(String message);
    void addActionNavigationClose();
    void closePelayanan();
    void showListGroupLayanan(List<GroupLayanan> datas);
    void showListJenisLayanan(List<JenisLayanan> datas);
}
