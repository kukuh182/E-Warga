package id.bengkelaplikasi.ewarga.views.menus.home.beranda.pengaduan;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.HashMap;

import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class PengaduanPresenter implements Presenter<PengaduanView>{

    PengaduanView view;
    Activity activity;
    Context context;

    public PengaduanPresenter(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void onAttach(PengaduanView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public void sendPengaduanToServer(String judul_pengaduan, String deskripsi_pengaduan, byte[] byte_image){
        if(TextUtils.isEmpty(judul_pengaduan)){
            view.showMessage("Judul Kosong");
        }else if(TextUtils.isEmpty(deskripsi_pengaduan)){
            view.showMessage("Deskripsi Kosong");
        }else if(byte_image==null){
            view.showMessage("Gambar Pengaduan Kosong");
        }else {
            if(Utilities.isLocationPermission(activity)){
                final Location location = Utilities.getYourLocation(context, activity);
                if(location!=null){
                    if (location.getLatitude()!=0&&location.getLongitude()!=0){
                        new ImageProcessTask(judul_pengaduan,deskripsi_pengaduan, location, byte_image)
                        .execute("");
                    }else {
                        view.showMessage("Lokasi Belum Ditemukan");
                    }
                }else {
                    view.showMessage("Lokasi Belum Ditemukan");
                }
            }
        }
    }

    class ImageProcessTask extends AsyncTask<String, String, String> {
        String judul_pengaduan = null;
        String deskripsi_pengaduan = null;
        Location location = null;
        byte[] byte_image;

        public ImageProcessTask(String judul_pengaduan, String deskripsi_pengaduan, Location location, byte[] byte_image){
            this.judul_pengaduan = judul_pengaduan;
            this.deskripsi_pengaduan = deskripsi_pengaduan;
            this.location = location;
            this.byte_image = byte_image;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.showProgressDialog("Load", "Image Processing");
        }

        @Override
        protected String doInBackground(String... strings) {
            String str_image = null;
            try{
                Thread.sleep(5000);
                str_image = Utilities.byteArrayToString(this.byte_image);
            }catch (Exception e){

            }
            return str_image;
        }

        @Override
        protected void onPostExecute(String str_image) {
            view.hideProgressDialog();
            sendPengaduan(this.judul_pengaduan, this.deskripsi_pengaduan, this.location, str_image);
        }

    }

    private void sendPengaduan(String judul_pengaduan, String deskripsi_pengaduan, Location location, String str_image){
        view.showProgressDialog("Proses", "Kirim Pengaduan...");
        String id_kategori = "P01";
        String id_user = String.valueOf(SharedPref.getInt(Constanta.Preference.ID));
        String path_photo = "pengaduan_"+id_user+ Utilities.getTimeStamp()+".PNG";

        HashMap<String, String> params = new HashMap<>();
        params.put("id_kategori", id_kategori);
        params.put("judul_pengaduan", judul_pengaduan);
        params.put("deskripsi_pengaduan", deskripsi_pengaduan);
        params.put("path_photo", path_photo);
        params.put("lattitude", String.valueOf(location.getLatitude()));
        params.put("longitude", String.valueOf(location.getLongitude()));
        params.put("sysusermobile_id", id_user);
        params.put("image", str_image);

        Service service = Client.initialize(Constanta.Url.APP);
        Client.request(service.sendPengaduan(params), new Client.CallBackRequest() {
            @Override
            public void successCode(String message, String rd) {
                if(view!=null){
                    view.hideProgressDialog();
                    view.showMessage(message);
                }
            }

            @Override
            public void unsuccessCode(String rc, String rm) {
                if(view!=null){
                    view.hideProgressDialog();
                    view.showMessage(rm);
                }
            }

            @Override
            public void unsuccessResponse(String message) {
                if(view!=null){
                    view.hideProgressDialog();
                    view.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            }

            @Override
            public void parsingError(String message) {
                if(view!=null){
                    view.hideProgressDialog();
                    view.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            }

            @Override
            public void otherError(String message) {
                if(view!=null){
                    view.hideProgressDialog();
                    view.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            }

            @Override
            public void failure(String message) {
                if(view!=null){
                    view.hideProgressDialog();
                    view.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            }
        });
    }
}
