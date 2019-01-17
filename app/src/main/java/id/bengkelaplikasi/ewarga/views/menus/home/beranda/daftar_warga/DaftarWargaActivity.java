package id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga.adapter.DaftarWargaAdapter;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga.adapter.DaftarWargaModel;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static id.bengkelaplikasi.ewarga.main.App.getContext;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class DaftarWargaActivity extends AppCompatActivity implements DaftarWargaView {

    DaftarWargaPresenter presenter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @BindView(R.id.toolbar_daftar_warga)Toolbar toolbar_daftar_warga;
    @BindView(R.id.ll_empty)LinearLayout ll_empty;
    @BindView(R.id.et_search_warga)AppCompatEditText et_search_warga;
    @BindView(R.id.pb_daftarwarga)ProgressBar pb_daftarwarga;
    @BindView(R.id.ib_refresh_daftar_warga)ImageButton ib_refresh_daftar_warga;
    @BindView(R.id.rv_daftar_warga)RecyclerView rv_daftar_warga;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_warga);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onBackPressed() {
        closeDaftarWarga();
    }

    @Override
    public void onAttachView() {
        presenter = new DaftarWargaPresenter();
        presenter.onAttach(this);
        ButterKnife.bind(this);
        addActionNavigationClose();
        showDataDaftarWarga();
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
    public void closeDaftarWarga() {
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void addActionNavigationClose() {
        toolbar_daftar_warga.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDaftarWarga();
            }
        });
    }

    @Override
    public void showDataDaftarWarga() {

        presenter.getListWargaToServer(new DaftarWargaPresenter.callBackDataWarga() {
            @Override
            public void result(List<DaftarWargaModel> data, String message) {
                if(data!=null){
                    ll_empty.setVisibility(View.GONE);
                    rv_daftar_warga.setVisibility(View.VISIBLE);
                    rv_daftar_warga.getRecycledViewPool().clear();
                    rv_daftar_warga.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getContext());
                    rv_daftar_warga.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    rv_daftar_warga.setLayoutManager(layoutManager);

                    searchAction(data);
                    adapter = new DaftarWargaAdapter(data, new DaftarWargaAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(DaftarWargaModel item) {
                            //presenter.sendDetailWarga(item);
                        }
                    });
                    rv_daftar_warga.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else {
                    ll_empty.setVisibility(View.VISIBLE);
                    rv_daftar_warga.setVisibility(View.GONE);
                    showMessage(message);
                }
            }
        });
    }

    @Override
    public void enableProgressBar() {
        pb_daftarwarga.setVisibility(View.VISIBLE);
        ib_refresh_daftar_warga.setVisibility(View.GONE);
        ll_empty.setVisibility(View.GONE);
        rv_daftar_warga.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableProgressBar() {
        pb_daftarwarga.setVisibility(View.GONE);
        ib_refresh_daftar_warga.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(toolbar_daftar_warga, message);
    }

    @Override
    public void searchAction(final List<DaftarWargaModel> data) {
        et_search_warga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    List<DaftarWargaModel> listWarga = presenter.serachWarga(data, editable.toString());
                    adapter = new DaftarWargaAdapter(listWarga, new DaftarWargaAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(DaftarWargaModel item) {
                            //presenter.sendDetailWarga(item);
                        }
                    });
                    rv_daftar_warga.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    adapter = new DaftarWargaAdapter(data, new DaftarWargaAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(DaftarWargaModel item) {

                        }
                    });
                    rv_daftar_warga.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }



    @OnClick(R.id.ib_refresh_daftar_warga)
    public void refreshDataWarga(){
        showDataDaftarWarga();
    }
}
