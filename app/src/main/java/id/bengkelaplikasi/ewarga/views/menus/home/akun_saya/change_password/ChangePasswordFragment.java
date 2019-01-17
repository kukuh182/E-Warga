package id.bengkelaplikasi.ewarga.views.menus.home.akun_saya.change_password;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Utilities;

/**
 * Created by Kukuh182 on 18-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class ChangePasswordFragment extends Fragment implements ChangePasswordView {

    private ChangePasswordPresenter presenter;
    private View view;

    @BindView(R.id.et_password_old)AppCompatEditText et_password_old;
    @BindView(R.id.et_password_new)AppCompatEditText et_password_new;
    @BindView(R.id.et_password_renew)AppCompatEditText et_password_renew;
    @BindView(R.id.acb_change_password)AppCompatButton acb_change_password;
    @BindView(R.id.pb_change_password)ProgressBar pb_change_password;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAttachView();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttachView() {
        presenter = new ChangePasswordPresenter();
        presenter.onAttach(this);
        disableProgressBar();
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
    public void enableProgressBar() {
        //Disable element form change password
        et_password_old.setEnabled(false);
        et_password_new.setEnabled(false);
        et_password_renew.setEnabled(false);
        acb_change_password.setVisibility(View.GONE);

        //Enable progressbar
        pb_change_password.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableProgressBar() {
        //Disable element form change password
        et_password_old.setEnabled(true);
        et_password_new.setEnabled(true);
        et_password_renew.setEnabled(true);
        acb_change_password.setVisibility(View.VISIBLE);

        //Enable progressbar
        pb_change_password.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(view, message);
    }

    @OnClick(R.id.acb_change_password)
    public void gantiPassword(){
        String password_old = et_password_old.getText().toString();
        String password_new = et_password_new.getText().toString();
        String password_renew = et_password_renew.getText().toString();
        presenter.validationChangePassword(password_old, password_new, password_renew);
    }
}
