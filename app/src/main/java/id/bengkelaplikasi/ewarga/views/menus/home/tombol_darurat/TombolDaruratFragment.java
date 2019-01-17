package id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.whitelist.WhitelistActivity;

import static android.view.KeyCharacterMap.load;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class TombolDaruratFragment extends Fragment implements TombolDaruratView {

    TombolDaruratPresenter presenter;
    View view;

    @BindView(R.id.iv_panicbutton)ImageView iv_panicbutton;
    @BindView(R.id.et_panic_button)EditText et_panic_button;
    @BindView(R.id.tv_whitelist)TextView tv_whitelist;

    public static TombolDaruratFragment newInstance() {
        return new TombolDaruratFragment();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttachView();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tombol_darurat, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttachView() {
        presenter = new TombolDaruratPresenter(getContext(), getActivity());
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

    @OnLongClick(R.id.iv_panicbutton)
    public boolean actionPanicButton(){
        String message_user = et_panic_button.getText().toString();
        presenter.daruratProcess(message_user);
        return true;
    }

    @OnClick(R.id.tv_whitelist)
    public void callWhitelist() {
        startActivity(new Intent(getContext(),WhitelistActivity.class));
        getActivity().overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(iv_panicbutton, message);
    }
}
