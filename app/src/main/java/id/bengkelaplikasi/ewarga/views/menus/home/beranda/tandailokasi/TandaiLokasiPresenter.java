package id.bengkelaplikasi.ewarga.views.menus.home.beranda.tandailokasi;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.dao.KategoriFasumDao;
import id.bengkelaplikasi.ewarga.dao.ProfilWargaDao;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.GPSTracker;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by KODEOK-KUKUH on 20-Nov-17.
 */

public class TandaiLokasiPresenter implements Presenter<TandaiLokasiView> {

    TandaiLokasiView view;
    Activity activity;
    Context context;

    public TandaiLokasiPresenter (Activity activity, Context context){
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void onAttach(TandaiLokasiView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public void getListKategoriFasum(){
        view.showListKategoriFasum(new KategoriFasumDao().gets());
    }

    public LatLng getCurrentLocation() {
        if(Utilities.permissionLocationOn(activity)){
            GPSTracker gpsTracker = new GPSTracker(context, activity);
            if(gpsTracker.canGetLocation()){
                double longitude = gpsTracker.getLongitude();
                double latitude = gpsTracker.getLatitude();
                gpsTracker.stopUsingGPS();
                return new LatLng(latitude, longitude);
            } else {
                gpsTracker.showSettingsAlert();
            }
        }
        return null;
    }

    public void validasi(String nama_lokasi, String keterangan, String kategori,LatLng latLng){
        int id = SharedPref.getInt(Constanta.Preference.ID);
        ProfilWarga warga = new ProfilWarga();
        warga.setSysusermobile_id(id);
        warga = new ProfilWargaDao().get(warga);
        if(warga.getRprov_kode().equals("-")||warga.getRkota_kode().equals("-")){
            view.showDialogInformation("Informasi","Anda Belum Terverifikasi",false);
        }else if(TextUtils.isEmpty(nama_lokasi)){
            view.showMessage("Nama Lokasi Kosong");
        }else if(TextUtils.isEmpty(keterangan)){
            keterangan = "-";
        }else if(TextUtils.isEmpty(kategori)){
            view.showMessage("Kategori Kosong");
        }else if(latLng==null){
            view.showMessage("Lokasi Kosong");
        }else {
            sendTandaiLokasiToServer(warga, id, nama_lokasi, keterangan, kategori,latLng.latitude, latLng.longitude);
        }
    }

    public void sendTandaiLokasiToServer(ProfilWarga warga,int id,String nama_lokasi, String keterangan, String kategori,double latitude, double longitude){
        view.showProgressDialog("Proses","Kirim Lokasi Baru");
        HashMap<String, String> params = new HashMap<>();
        params.put("rfasum_nama", nama_lokasi);
        params.put("rprov_kode", warga.getRprov_kode());
        params.put("rkota_kode", warga.getRkota_kode());
        params.put("rkec_kode", "");
        params.put("rkel_kode", "");
        params.put("rfasum_rw","");
        params.put("rfasum_rt", "");
        params.put("rfasum_long", String.valueOf(longitude));
        params.put("rfasum_lat", String.valueOf(latitude));
        params.put("rfasum_alamat", "");
        params.put("rfasum_keterangan", keterangan);
        params.put("rfasum_telp", "");
        params.put("rfasum_hp", "");
        params.put("rfasum_email", "");
        params.put("rfasum_fax", "");
        params.put("rtypemember_id", "");
        params.put("rfasum_path", "");
        params.put("rfasumtype_id", kategori);
        params.put("rfasum_status", "0");
        params.put("sysusermobile_id", String.valueOf(id));

        Service service = Client.initialize(Constanta.Url.APP);
        Client.request(service.addFasilitasUmum(params), new Client.CallBackRequest() {
            @Override
            public void successCode(String message, String rd) {
                view.hideProgressDialog();
                view.showDialogInformation("Informasi", "Lokasi Baru Berhasil Dikirim", true);
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
