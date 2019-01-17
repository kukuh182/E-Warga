package id.bengkelaplikasi.ewarga.views.menus.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.Dialog;
import id.bengkelaplikasi.ewarga.helper.GPSTracker;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.main.App;
import id.bengkelaplikasi.ewarga.views.menus.home.akun_saya.AkunSayaFragment;
import id.bengkelaplikasi.ewarga.views.menus.home.akun_saya.change_password.ChangePasswordFragment;
import id.bengkelaplikasi.ewarga.views.menus.home.akun_saya.lokasi.LokasiFragment;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.BerandaFragment;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.InformasiFragment;
import id.bengkelaplikasi.ewarga.views.menus.home.tentang_kami.TentangKamiFragment;
import id.bengkelaplikasi.ewarga.views.menus.home.tentang_kami.used_libraries.UsedLibrariesFragment;
import id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.TombolDaruratFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class HomeActivity extends AppCompatActivity implements HomeView,
        Communicator, NavigationView.OnNavigationItemSelectedListener {

    private HomePresenter presenter;
    private static final String SELECTED_ITEM_ID = "SELECTED_ITEM_ID";
    private final Handler mDrawerHandler = new Handler();
    private int mSelectedId;
    private Bundle bundle;
    private GPSTracker gpsTracker;

    @BindView(R.id.navigation_view)NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bundle = savedInstanceState;
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onAttachView() {
        presenter = new HomePresenter();
        presenter.onAttach(this);
        ButterKnife.bind(this);
        presenter.clearSessionRegister();
        gpsTracker = new GPSTracker(App.getContext(), this);
        initDrawerLayout();
        setProfilHeader();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy(){
        onDetachView();
        super.onDestroy();
    }

    @Override
    public void initDrawerLayout() {
        setSupportActionBar(mToolbar);

        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                super.onDrawerSlide(drawerView, 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSelectedId = mNavigationView.getMenu().getItem(prefs.getInt("default_view", 0)).getItemId();
        mSelectedId = bundle == null ? mSelectedId : bundle.getInt(SELECTED_ITEM_ID);
        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);

        if (bundle == null) {
            mDrawerHandler.removeCallbacksAndMessages(null);
            mDrawerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigate(mSelectedId);
                }
            }, 250);

            boolean openDrawer = prefs.getBoolean("open_drawer", false);

            if (openDrawer)
                mDrawerLayout.openDrawer(GravityCompat.START);
            else
                mDrawerLayout.closeDrawers();
        }
    }

    @Override
    public void navigate(final int itemId) {
        Fragment navFragment = null;
        switch (itemId) {
            case R.id.nav_1:
                setTitle(R.string.menu_beranda);
                navFragment = new BerandaFragment();
                break;
            case R.id.nav_2:
                setTitle(R.string.menu_tombol_darurat);
                navFragment = new TombolDaruratFragment();
                break;
            case R.id.nav_3:
                setTitle(R.string.menu_informasi);
                navFragment = new InformasiFragment();
                break;
            case R.id.nav_4:
                setTitle(R.string.menu_akunsaya);
                navFragment = new AkunSayaFragment();
                break;
            case R.id.nav_o1:
                setTitle(R.string.menu_tentangkami);
                navFragment = new TentangKamiFragment();
                break;
        }
        transactionFragment(navFragment, null);
    }

    @Override
    public void setProfilHeader() {
        View header = mNavigationView.getHeaderView(0);
        AppCompatTextView app_name = header.findViewById(R.id.app_name);
        AppCompatTextView app_desc = header.findViewById(R.id.app_description);
        app_name.setText(R.string.app_name);
        app_desc.setText(SharedPref.getString(Constanta.Preference.USERNAME));
    }


    @Override
    public void callProfil() {
        setTitle(R.string.menu_akunsaya);
        Fragment fragment = AkunSayaFragment.newInstance();
        transactionFragment(fragment, null);
    }

    @Override
    public void showDialogCloseApp() {
        Dialog.basic(this, "E-Warga", "Apakah anda yakin mau keluar aplikasi ini ?",
                "ya", "tidak", false, new Dialog.CallBack2() {
            @Override
            public void onPositive(MaterialDialog md, DialogAction which) {
                md.dismiss();
                if(gpsTracker.canGetLocation()){
                    gpsTracker.stopUsingGPS();
                }
                finish();
            }

            @Override
            public void onNegative(MaterialDialog md, DialogAction which) {
                md.dismiss();
            }
        });
    }

    @Override
    public void transactionFragment(Fragment fragment, Bundle bundle) {
        if (fragment!=null) {
            if(bundle!=null){
                fragment.setArguments(bundle);
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in_item, R.anim.fade_out_item);
            try {
                transaction.replace(R.id.content_frame, fragment).commit();
            } catch (IllegalStateException ignored) {}
        }
    }

    @Override
    public void sendLongLatToLocation(String nama, double longitude, double latitude) {
        Bundle mBundle = new Bundle();
        mBundle.putString("nama", nama);
        mBundle.putDouble("longitude", longitude);
        mBundle.putDouble("latitude", latitude);

        setTitle(R.string.lokasi);
        Fragment fragment = LokasiFragment.newInstance();
        transactionFragment(fragment, mBundle);
    }

    @Override
    public void callAboutUs() {
        setTitle(R.string.menu_tentangkami);
        Fragment fragment = TentangKamiFragment.newInstance();
        transactionFragment(fragment, null);
    }

    @Override
    public void callAkunSaya() {
        setTitle(R.string.menu_akunsaya);
        Fragment fragment = AkunSayaFragment.newInstance();
        transactionFragment(fragment, null);
    }

    @Override
    public void callChangePassword() {
        setTitle(R.string.menu_gantipassword);
        Fragment fragment = ChangePasswordFragment.newInstance();
        transactionFragment(fragment, null);
    }

    /*@Override
    public void callDetailWarga(DaftarWargaModel model) {
        Bundle mBundle = new Bundle();
        mBundle.putString("nama", model.getRktpNama());
        mBundle.putString("nik", model.getRktpNik());
        mBundle.putString("provinsi", model.getRprovNama());
        mBundle.putString("kota", model.getRkotaNama());
        mBundle.putString("kecamatan", model.getRkecNama());
        mBundle.putString("kelurahan", model.getRkelNama());
        mBundle.putString("rt", model.getRktpRt());
        mBundle.putString("rw", model.getRktpRw());
        mBundle.putString("alamat", model.getRktpAlamat());

        setTitle(R.string.menu_detailwarga);
        Fragment fragment = DetailWargaFragment.newInstance();
        transactionFragment(fragment, mBundle);
    }*/

    @Override
    public void callDetailLibraries() {
        setTitle(R.string.menu_detaillibraries);
        Fragment fragment = UsedLibrariesFragment.newInstance();
        transactionFragment(fragment, null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        mSelectedId = item.getItemId();
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            String title = getTitle().toString();

            switch (title){
                case "Lokasi" :
                    callProfil();
                    break;
                case "Detail Libraries" :
                    callAboutUs();
                    break;
                case "Ganti Password" :
                    callAkunSaya();
                    break;
                default:
                   showDialogCloseApp();
                    break;
            }
        }
    }

}
