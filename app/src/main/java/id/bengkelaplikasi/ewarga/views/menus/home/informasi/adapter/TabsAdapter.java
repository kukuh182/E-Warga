package id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.bengkelaplikasi.ewarga.views.menus.home.informasi.agenda.AgendaFragment;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.berita.BeritaFragment;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.pengumuman.PengumumanFragment;


/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new AgendaFragment();
            case 1:
                return new PengumumanFragment();
            case 2:
                return new BeritaFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Agenda";
            case 1:
                return "Pengumuman";
            case 2:
                return "Berita";
        }
        return "";
    }
}