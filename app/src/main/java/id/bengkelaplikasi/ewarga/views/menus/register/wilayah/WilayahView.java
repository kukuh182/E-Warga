package id.bengkelaplikasi.ewarga.views.menus.register.wilayah;

import java.util.List;

import id.bengkelaplikasi.ewarga.views.base.View;
import id.bengkelaplikasi.ewarga.views.menus.register.wilayah.adapter.WilayahModel;

import static android.R.attr.data;

/**
 * Created by ASUS on 10/6/2017.
 */

public interface WilayahView extends View {

    void loadWilayah(int tipe_wilayah);
    void searchAction(int tipe_wilayah, List<WilayahModel> data);
    void nextWilayah(int tipe_wilayah, WilayahModel item);
    void enableProgressBar();
    void disableProgressBar();
    void showMessage(String message);
    void callRegister();
    void showFloatingButtonBack();
    void hideFloatingButtonBack();
}
