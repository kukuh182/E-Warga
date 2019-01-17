package id.bengkelaplikasi.ewarga.views.menus.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.menus.home.HomeActivity;
import id.bengkelaplikasi.ewarga.views.menus.register.wilayah.WilayahActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter presenter;

    @BindView(R.id.et_log_email)AppCompatEditText et_log_email;
    @BindView(R.id.et_log_password)AppCompatEditText et_log_password;
    @BindView(R.id.acb_login)AppCompatButton acb_login;
    @BindView(R.id.pb_login)ProgressBar pb_login;
    @BindView(R.id.acb_register)AppCompatButton acb_register;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onAttachView() {
        presenter = new LoginPresenter(this);
        presenter.onAttach(this);
        ButterKnife.bind(this);
        disableProgressBar();
        presenter.clearSessionRegister();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy() {
        onDetachView();
        super.onDestroy();
    }

    @Override
    public void callHome() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
        overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(acb_login, message);
    }

    @Override
    public void enableProgressBar() {
        //Disable element form login
        et_log_email.setEnabled(false);
        et_log_password.setEnabled(false);
        acb_login.setVisibility(View.GONE);
        acb_register.setVisibility(View.GONE);

        //Enable progressbar
        pb_login.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableProgressBar() {
        //Enable element form login
        et_log_email.setEnabled(true);
        et_log_password.setEnabled(true);
        acb_login.setVisibility(View.VISIBLE);
        acb_register.setVisibility(View.VISIBLE);

        //Disable progressbar
        pb_login.setVisibility(View.GONE);
    }

    @OnClick(R.id.acb_login)
    public void loginProcess(){
        String email = et_log_email.getText().toString();
        String password = et_log_password.getText().toString();
        presenter.validationLogin(email, password);
    }

    @OnClick(R.id.acb_register)
    public void callRegistrasi(){
        startActivity(new Intent(this,WilayahActivity.class));
        finish();
        overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }
}
