package id.bengkelaplikasi.ewarga.views.menus.home.tentang_kami.used_libraries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.main.App;
import id.bengkelaplikasi.ewarga.views.menus.home.tentang_kami.used_libraries.adapter.DetailLibrariesAdapter;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class UsedLibrariesFragment extends Fragment implements UsedLibrariesView {

    private UsedLibrariesPresenter presenter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private View view;

    @BindView(R.id.rv_libraries)RecyclerView rv_libraries;

    public static UsedLibrariesFragment newInstance() {
        return new UsedLibrariesFragment();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttachView();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_usedlibraries, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttachView() {
        presenter = new UsedLibrariesPresenter();
        presenter.onAttach(this);
        initData();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    public void onDestroyView() {
        onDetachView();
        super.onDestroyView();
    }

    @Override
    public void initData() {
        rv_libraries.getRecycledViewPool().clear();
        rv_libraries.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(App.getContext());
        rv_libraries.setLayoutManager(layoutManager);
        adapter = new DetailLibrariesAdapter(presenter.getlistLibraries());
        rv_libraries.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
