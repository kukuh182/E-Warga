package id.bengkelaplikasi.ewarga.views.menus.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import id.bengkelaplikasi.ewarga.views.base.View;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface HomeView  extends View {

    void initDrawerLayout();
    void navigate(int itemId);
    void setProfilHeader();
    void callProfil();
    void callAboutUs();
    void callAkunSaya();
    void showDialogCloseApp();
    void transactionFragment(Fragment fragment, Bundle bundle);
}
