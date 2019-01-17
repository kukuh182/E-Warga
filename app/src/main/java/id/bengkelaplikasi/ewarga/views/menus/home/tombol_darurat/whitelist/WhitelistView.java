package id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.whitelist;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import id.bengkelaplikasi.ewarga.views.base.View;
import id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.whitelist.adapter.WhitelistMA;

import static android.R.attr.name;
import static android.R.attr.value;

/**
 * Created by ASUS on 10/12/2017.
 */

public interface WhitelistView extends View{
    interface InputCallback{
        void input(MaterialDialog md, String value);
    }

    void showMessage(String message);
    void showDialogContact(ArrayList<String> contacts);
    void showDialogInputContact(int inputType, String title, String name_input, String value, InputCallback callback);
    void initDataWhitelist();
    void showDialogDeteleWhitelist(int id);
    void addActionNavigationClose();
    void closeWhitelist();
    void searchAction(List<WhitelistMA> data);
}
