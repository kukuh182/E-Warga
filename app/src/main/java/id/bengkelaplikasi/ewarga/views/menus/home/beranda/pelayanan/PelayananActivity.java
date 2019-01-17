package id.bengkelaplikasi.ewarga.views.menus.home.beranda.pelayanan;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Dialog;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.main.App;
import id.bengkelaplikasi.ewarga.models.GroupLayanan;
import id.bengkelaplikasi.ewarga.models.JenisLayanan;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by KODEOK-KUKUH on 12-Nov-17.
 */

public class PelayananActivity extends AppCompatActivity implements PelayananView {

    PelayananPresenter presenter;
    MaterialDialog progressDialog;
    List<GroupLayanan> groupLayanans;
    List<JenisLayanan> jenisLayanans;
    @BindView(R.id.toolbar_pelayanan) Toolbar toolbar_pelayanan;
    @BindView(R.id.acs_grouplayanan) AppCompatSpinner acs_grouplayanan;
    @BindView(R.id.acs_jenislayanan) AppCompatSpinner acs_jenislayanan;
    @BindView(R.id.aet_judul) AppCompatEditText aet_judul;
    @BindView(R.id.aet_deskripsi) AppCompatEditText aet_deskripsi;
    @BindView(R.id.acb_kirimlayanan) AppCompatButton acb_kirimlayanan;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelayanan);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onBackPressed() {
        closePelayanan();
    }

    @Override
    public void onAttachView() {
        presenter = new PelayananPresenter();
        presenter.onAttach(this);
        ButterKnife.bind(this);
        addActionNavigationClose();
        presenter.getListGroupLayanan();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy(){
        onDetachView();
        super.onDestroy();
    }

    @OnClick(R.id.acb_kirimlayanan)
    public void kirimLayanan(){
        String judul = aet_judul.getText().toString();
        String deskripsi = aet_deskripsi.getText().toString();
        int position_jl = acs_jenislayanan.getSelectedItemPosition();
        JenisLayanan jenisLayanan = jenisLayanans.get(position_jl);
        presenter.validasiKirimPelayanan(judul, deskripsi,jenisLayanan.getKode_jenis());
    }

    @Override
    public void showProgressDialog(String title, String content) {
        progressDialog = Dialog.progress(this, title, content, false);
    }

    @Override
    public void hideProgressDialog() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showDialogInformation(String title, String content, final boolean status) {
        Dialog.basic(this, title, content, "OK", false, new Dialog.CallBack1() {
            @Override
            public void onPositive(MaterialDialog md, DialogAction which) {
                md.dismiss();
                if(status){
                    closePelayanan();
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
            Utilities.snackbar(toolbar_pelayanan, message);
    }

    @Override
    public void addActionNavigationClose() {
        toolbar_pelayanan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePelayanan();
            }
        });
    }

    @Override
    public void closePelayanan() {
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void showListGroupLayanan(List<GroupLayanan> datas) {
        groupLayanans = datas;
        List<String> data = new ArrayList<>();
        for (GroupLayanan groupLayanan : groupLayanans){
            data.add(groupLayanan.getDeskripsi());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(App.getContext(),
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acs_grouplayanan.setAdapter(adapter);
        acs_grouplayanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GroupLayanan groupLayanan = groupLayanans.get(position);
                presenter.getListJenisLayanan(groupLayanan.getKode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showListJenisLayanan(List<JenisLayanan> datas) {
        jenisLayanans = datas;
        List<String> data = new ArrayList<>();
        for (JenisLayanan jenisLayanan: jenisLayanans){
            data.add(jenisLayanan.getDeskripsi());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(App.getContext(),
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acs_jenislayanan.setAdapter(adapter);
    }
}
