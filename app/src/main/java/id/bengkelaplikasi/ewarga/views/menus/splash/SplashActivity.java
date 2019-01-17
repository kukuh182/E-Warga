package id.bengkelaplikasi.ewarga.views.menus.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.views.menus.home.HomeActivity;
import id.bengkelaplikasi.ewarga.views.menus.login.LoginActivity;

public class SplashActivity extends AppCompatActivity implements SplashView{

    private SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        onAttachView();
    }


    @Override
    public void onAttachView() {
        presenter = new SplashPresenter();
        presenter.onAttach(this);
        presenter.timerSplash();
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
    public void callLogin() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
        overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }

    @Override
    public void callHome() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
        overridePendingTransition(R.transition.pull_in_right, R.transition.push_out_left);
    }
}
