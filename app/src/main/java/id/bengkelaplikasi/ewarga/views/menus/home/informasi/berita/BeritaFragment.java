package id.bengkelaplikasi.ewarga.views.menus.home.informasi.berita;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter.CallBackData;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter.InformasiAdapter;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter.InformasiModel;

/**
 * Created by Kukuh182 on 13-Aug-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class BeritaFragment extends Fragment implements BeritaView {

    private BeritaPresenter presenter;
    private View view;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @BindView(R.id.pb_berita)ProgressBar pb_berita;
    @BindView(R.id.rv_berita)RecyclerView rv_berita;
    @BindView(R.id.ib_refresh_berita)ImageButton ib_refresh_berita;
    @BindView(R.id.et_search_berita)AppCompatEditText et_search_berita;

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_berita, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttachView() {
        presenter = new BeritaPresenter();
        presenter.onAttach(this);
        disableProgressBar();
        initData();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    public void initData() {
        rv_berita.getRecycledViewPool().clear();
        rv_berita.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rv_berita.setLayoutManager(layoutManager);

        presenter.getListDataToServer(new CallBackData() {
            @Override
            public void result(final List<InformasiModel> data, String message) {
                if(data!=null){
                    searchAction(data);
                    adapter = new InformasiAdapter(data, new InformasiAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(InformasiModel item) {

                        }
                    });
                    rv_berita.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else {
                    showMessage(message);
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(view, message);
    }

    @Override
    public void enableProgressBar() {
        pb_berita.setVisibility(View.VISIBLE);
        ib_refresh_berita.setVisibility(View.GONE);
    }

    @Override
    public void disableProgressBar() {
        pb_berita.setVisibility(View.GONE);
        ib_refresh_berita.setVisibility(View.VISIBLE);
    }

    @Override
    public void searchAction(final List<InformasiModel> data) {
        et_search_berita.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    List<InformasiModel> listAgenda = presenter.serachData(data, editable.toString());
                    adapter = new InformasiAdapter(listAgenda, new InformasiAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(InformasiModel item) {

                        }
                    });
                    rv_berita.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    adapter = new InformasiAdapter(data, new InformasiAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(InformasiModel item) {

                        }
                    });
                    rv_berita.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick(R.id.ib_refresh_berita)
    public void refreshData(){
        initData();
    }
}
