package id.bengkelaplikasi.ewarga.views.menus.register.wilayah;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.menus.login.LoginActivity;
import id.bengkelaplikasi.ewarga.views.menus.register.RegisterActivity;
import id.bengkelaplikasi.ewarga.views.menus.register.wilayah.adapter.WilayahAdapter;
import id.bengkelaplikasi.ewarga.views.menus.register.wilayah.adapter.WilayahModel;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.attr.data;
import static id.bengkelaplikasi.ewarga.main.App.getContext;

/**
 * Created by ASUS on 10/6/2017.
 */

public class WilayahActivity extends AppCompatActivity implements WilayahView{

    WilayahPresenter presenter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    int next_tipe_wilayah = 0;

    @BindView(R.id.fab_back)FloatingActionButton fab_back;
    @BindView(R.id.fab_close)FloatingActionButton fab_close;
    @BindView(R.id.et_search_wilayah)AppCompatEditText et_search_wilayah;
    @BindView(R.id.pb_wilyah)ProgressBar pb_wilyah;
    @BindView(R.id.ib_refresh_wilayah)ImageButton ib_refresh_wilayah;
    @BindView(R.id.rv_wilayah)RecyclerView rv_wilayah;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilayah);
        onAttachView();
    }

    @Override
    public void onBackPressed() {
        backProcess();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


    @Override
    public void onAttachView() {
        presenter = new WilayahPresenter();
        presenter.onAttach(this);
        ButterKnife.bind(this);
        disableProgressBar();
        loadWilayah(0);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy() {
        onDetachView();
        super.onDestroy();
    }

    @OnClick(R.id.ib_refresh_wilayah)
    public void refreshDataWilayah(){
        loadWilayah(next_tipe_wilayah);
    }

    @OnClick(R.id.fab_close)
    public void backProcess(){
        int stat_wilayah = SharedPref.getInt(Constanta.RegPreference.STAT_WILAYAH);
        if(stat_wilayah==0){
            startActivity(new Intent(this,LoginActivity.class));
        }
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @OnClick(R.id.fab_back)
    public void back(){
        int node = next_tipe_wilayah-1;
        if(node>=0){
            loadWilayah(node);
            next_tipe_wilayah = node;
        }
    }

    @Override
    public void loadWilayah(final int tipe_wilayah) {

        rv_wilayah.getRecycledViewPool().clear();
        rv_wilayah.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rv_wilayah.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        rv_wilayah.setLayoutManager(layoutManager);

        if(tipe_wilayah==0){
            hideFloatingButtonBack();
        }else{
            showFloatingButtonBack();
        }

        switch (tipe_wilayah){
            case 0:
                et_search_wilayah.setHint(R.string.search_provinsi);
                break;
            case 1:
                et_search_wilayah.setHint(R.string.search_kota);
                break;
            case 2:
                et_search_wilayah.setHint(R.string.search_kecamatan);
                break;
            case 3:
                et_search_wilayah.setHint(R.string.search_kelurahan);
                break;
        }

        presenter.getListWilayah(tipe_wilayah, new WilayahPresenter.CallBackWilayah() {
            @Override
            public void result(List<WilayahModel> data, String message) {
                if(data!=null){
                    searchAction(tipe_wilayah, data);
                    adapter = new WilayahAdapter(data, new WilayahAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(WilayahModel item) {
                            nextWilayah(tipe_wilayah, item);
                        }
                    });
                    rv_wilayah.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else {
                    showMessage(message);
                }
            }
        });
    }



    @Override
    public void nextWilayah(int tipe_wilayah, WilayahModel item) {
        presenter.saveKodeWilayah(tipe_wilayah, item);
        next_tipe_wilayah = tipe_wilayah+1;

        if(next_tipe_wilayah<4){
            loadWilayah(next_tipe_wilayah);
        }else {
            callRegister();
        }
    }

    @Override
    public void searchAction(final int tipe_wilayah, final List<WilayahModel> data) {
        et_search_wilayah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    List<WilayahModel> listWilayah = presenter.searchWilayah(data, editable.toString());
                    adapter = new WilayahAdapter(listWilayah, new WilayahAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(WilayahModel item) {
                            nextWilayah(tipe_wilayah, item);
                        }
                    });
                    rv_wilayah.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    adapter = new WilayahAdapter(data, new WilayahAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(WilayahModel item) {
                            nextWilayah(tipe_wilayah, item);
                        }
                    });
                    rv_wilayah.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void enableProgressBar() {
        pb_wilyah.setVisibility(View.VISIBLE);
        ib_refresh_wilayah.setVisibility(View.GONE);
        rv_wilayah.setVisibility(View.INVISIBLE);
    }

    @Override
    public void disableProgressBar() {
        pb_wilyah.setVisibility(View.GONE);
        ib_refresh_wilayah.setVisibility(View.VISIBLE);
        rv_wilayah.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(rv_wilayah, message);
    }

    @Override
    public void callRegister() {
        int stat_wilayah = SharedPref.getInt(Constanta.RegPreference.STAT_WILAYAH);
        if(stat_wilayah==0){
            startActivity(new Intent(this,RegisterActivity.class));
        }
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void showFloatingButtonBack() {
        fab_back.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFloatingButtonBack() {
        fab_back.setVisibility(View.GONE);
    }
}
