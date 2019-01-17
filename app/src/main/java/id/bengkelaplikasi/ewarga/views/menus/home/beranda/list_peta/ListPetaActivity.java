package id.bengkelaplikasi.ewarga.views.menus.home.beranda.list_peta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.list_peta.adapter.ListPetaAdapter;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.list_peta.adapter.ListPetaMA;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.peta.PetaActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static id.bengkelaplikasi.ewarga.main.App.getContext;

/**
 * Created by FLYCODE-KUKUH on 23-Nov-17.
 */

public class ListPetaActivity extends AppCompatActivity implements ListPetaView {

    ListPetaPresenter presenter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @BindView(R.id.toolbar_listpeta)Toolbar toolbar_listpeta;
    @BindView(R.id.rv_listpeta)RecyclerView rv_listpeta;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpeta);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onAttachView() {
        presenter = new ListPetaPresenter();
        presenter.onAttach(this);
        ButterKnife.bind(this);
        addActionNavigationClose();
        presenter.getListPeta();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }


    @Override
    public void closeListPeta() {
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void addActionNavigationClose() {
        toolbar_listpeta.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeListPeta();
            }
        });
    }

    @Override
    public void showDaftarPeta(List<ListPetaMA> listPetaMAS) {
        rv_listpeta.getRecycledViewPool().clear();
        rv_listpeta.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rv_listpeta.setLayoutManager(layoutManager);
        adapter = new ListPetaAdapter(listPetaMAS, new ListPetaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListPetaMA item) {
                presenter.checkMenu(item.getTitle());
            }
        });
        rv_listpeta.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(toolbar_listpeta, message);
    }

    @Override
    public void callPeta(String[] kode_peta) {
        Intent i = new Intent(this, PetaActivity.class);
        i.putExtra("kode_peta", kode_peta);
        startActivity(i);
        overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }
}
