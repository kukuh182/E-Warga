package id.bengkelaplikasi.ewarga.views.menus.home.beranda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Dialog;
import id.bengkelaplikasi.ewarga.main.App;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.adapter.MenuBerandaAdapter;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.adapter.MenuBerandaMA;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga.DaftarWargaActivity;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.event.EventActivity;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.list_peta.ListPetaActivity;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.pelayanan.PelayananActivity;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.pengaduan.PengaduanActivity;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.tandailokasi.TandaiLokasiActivity;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class BerandaFragment extends Fragment implements BerandaView {

    BerandaPresenter presenter;
    MaterialDialog materialDialog;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    View view;

    @BindView(R.id.rv_menu_beranda)RecyclerView rv_menu_beranda;

    public static BerandaFragment newInstance(){
        return new BerandaFragment();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttachView();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_beranda, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttachView() {
        presenter = new BerandaPresenter();
        presenter.onAttach(this);
        showMenuBeranda();

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
    public void showMenuBeranda() {
        rv_menu_beranda.getRecycledViewPool().clear();
        rv_menu_beranda.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(App.getContext(),2);
        rv_menu_beranda.setLayoutManager(layoutManager);
        adapter = new MenuBerandaAdapter(presenter.initListMenuBeranda(), new MenuBerandaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MenuBerandaMA item) {
                presenter.checkMenu(item.getDescription());
            }
        });
        rv_menu_beranda.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void callAcara() {
        startActivity(new Intent(getContext(),EventActivity.class));
        getActivity().overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }

    @Override
    public void callPengaduan() {
        startActivity(new Intent(getContext(),PengaduanActivity.class));
        getActivity().overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }

    @Override
    public void callPelayanan() {
        startActivity(new Intent(getContext(),PelayananActivity.class));
        getActivity().overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }

    @Override
    public void callDaftarWarga() {
        startActivity(new Intent(getContext(),DaftarWargaActivity.class));
        getActivity().overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }

    @Override
    public void callPeta() {
        startActivity(new Intent(getContext(),ListPetaActivity.class));
        getActivity().overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }

    @Override
    public void callTandaiLokasi() {
        startActivity(new Intent(getContext(),TandaiLokasiActivity.class));
        getActivity().overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }

    @Override
    public void showDialogProgress(String title, String content) {
        materialDialog = Dialog.progress(getActivity(), title, content, false);
    }

    @Override
    public void hideDialogProgress() {
        if(materialDialog!=null){
            materialDialog.dismiss();
        }
    }
}
