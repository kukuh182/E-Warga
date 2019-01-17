package id.bengkelaplikasi.ewarga.views.menus.home.beranda.pelayanan;

import android.text.TextUtils;

import java.util.HashMap;

import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.dao.GroupLayananDao;
import id.bengkelaplikasi.ewarga.dao.JenisLayananDao;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by KODEOK-KUKUH on 12-Nov-17.
 */

public class PelayananPresenter implements Presenter<PelayananView> {

    PelayananView view;
    GroupLayananDao glDao;
    JenisLayananDao jnDao;

    public PelayananPresenter(){
        this.glDao = new GroupLayananDao();
        this.jnDao = new JenisLayananDao();
    }

    @Override
    public void onAttach(PelayananView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public void getListGroupLayanan(){
        view.showListGroupLayanan(glDao.gets());
    }

    public void getListJenisLayanan(String kode_group){
        view.showListJenisLayanan(jnDao.get(kode_group));
    }

    public void validasiKirimPelayanan(String judul, String deskripsi, String jenis_layanan){

        if(TextUtils.isEmpty(judul)){
            view.showMessage("Judul Kosong");
        }else if(TextUtils.isEmpty(deskripsi)){
            view.showMessage("Deskripsi Kosong");
        }else {
            sendPermohonanLayananToServer(judul, deskripsi, jenis_layanan);
        }
    }

    private void sendPermohonanLayananToServer(String judul, String deskripsi, String jenis_layanan){
        view.showProgressDialog("Proses","Kirim layanan");
        int id = SharedPref.getInt(Constanta.Preference.ID);
        HashMap<String, String> params = new HashMap<>();
        params.put("rjlayan_kode", jenis_layanan);
        params.put("tlayanan_judul", judul);
        params.put("tlayanan_deskripsi", deskripsi);
        params.put("sysusermobile_id", String.valueOf(id));

        Service service = Client.initialize(Constanta.Url.APP);
        Client.request(service.addLayanan(params), new Client.CallBackRequest() {
            @Override
            public void successCode(String message, String rd) {
                view.hideProgressDialog();
                view.showDialogInformation("Informasi", "Layanan Berhasil Dikirim", true);
            }

            @Override
            public void unsuccessCode(String rc, String rm) {
                view.hideProgressDialog();
                view.showDialogInformation("Informasi", rm, false);
            }

            @Override
            public void unsuccessResponse(String message) {
                view.hideProgressDialog();
                view.showDialogInformation("Informasi", message, false);
            }

            @Override
            public void parsingError(String message) {
                view.hideProgressDialog();
            }

            @Override
            public void otherError(String message) {
                view.hideProgressDialog();
                view.showDialogInformation("Informasi", message, false);
            }

            @Override
            public void failure(String message) {
                view.hideProgressDialog();
                view.showDialogInformation("Informasi", message, false);
            }
        });
    }
}
