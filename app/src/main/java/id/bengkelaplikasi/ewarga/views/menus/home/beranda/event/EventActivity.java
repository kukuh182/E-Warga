package id.bengkelaplikasi.ewarga.views.menus.home.beranda.event;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.main.App;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.adapter.MenuBerandaAdapter;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.adapter.MenuBerandaMA;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.event.adapter.EventAdapter;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.event.adapter.EventMA;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static id.bengkelaplikasi.ewarga.R.id.rv_event;
import static id.bengkelaplikasi.ewarga.R.id.toolbar_daftar_warga;
import static id.bengkelaplikasi.ewarga.R.id.toolbar_event;
import static id.bengkelaplikasi.ewarga.main.App.getContext;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class EventActivity extends AppCompatActivity implements EventView{

    EventPresenter presenter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @BindView(R.id.toolbar_event)Toolbar toolbar_event;
    @BindView(R.id.rv_event)RecyclerView rv_event;
    @BindView(R.id.pb_event)ProgressBar pb_event;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onBackPressed() {
        closeEvent();
    }

    @Override
    public void onAttachView() {
        presenter = new EventPresenter();
        presenter.onAttach(this);
        ButterKnife.bind(this);
        addActionNavigationClose();
        showDataEvent();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    public void closeEvent() {
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void addActionNavigationClose() {
        toolbar_event.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeEvent();
            }
        });
    }

    @Override
    public void showDataEvent() {
        presenter.getListEventToServer(new EventPresenter.CallBackEvent() {
            @Override
            public void result(List<EventMA> data, String message) {
                if(data!=null){
                    rv_event.getRecycledViewPool().clear();
                    rv_event.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getContext());
                    rv_event.setLayoutManager(layoutManager);
                    adapter = new EventAdapter(data, new EventAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(EventMA item) {

                        }
                    });
                    rv_event.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void enableProgressBar() {
        pb_event.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableProgressBar() {
        pb_event.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(toolbar_event, message);
    }

    @Override
    public void searchAction() {

    }
}
