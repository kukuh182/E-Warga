package id.bengkelaplikasi.ewarga.views.menus.home.informasi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter.TabsAdapter;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class InformasiFragment extends Fragment implements InformasiView {

    private InformasiPresenter presenter;
    private View view;

    @BindView(R.id.pager)ViewPager mPager;
    @BindView(R.id.tab_layout)TabLayout mTabLayout;

    public static InformasiFragment newInstance() {
        return new InformasiFragment();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttachView();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_informasi, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttachView() {
        presenter = new InformasiPresenter();
        presenter.onAttach(this);
        initTabLayout();
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
    public void initTabLayout() {
        mPager.setAdapter(new TabsAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mPager);
        setHasOptionsMenu(true);
    }
}
