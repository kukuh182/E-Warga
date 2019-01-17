package id.bengkelaplikasi.ewarga.views.menus.home.beranda.event;

import java.util.List;

import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface EventView extends View{
    void closeEvent();
    void addActionNavigationClose();
    void showDataEvent();
    void enableProgressBar();
    void disableProgressBar();
    void showMessage(String message);
    void searchAction();
}
