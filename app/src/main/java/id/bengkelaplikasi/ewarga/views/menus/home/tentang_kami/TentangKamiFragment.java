package id.bengkelaplikasi.ewarga.views.menus.home.tentang_kami;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class TentangKamiFragment extends Fragment implements TentangKamiView {

    private TentangKamiPresenter presenter;
    private View view;

    @BindView(R.id.ll_aboutus_libraries)LinearLayout ll_aboutus_libraries;

    public static TentangKamiFragment newInstance() {
        return new TentangKamiFragment();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttachView();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tentangkami, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttachView() {
        presenter = new TentangKamiPresenter(getActivity());
        presenter.onAttach(this);
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

    @OnClick(R.id.ll_aboutus_libraries)
    public void callLibraries(){
        presenter.showDeatilLibraries();
    }
}
