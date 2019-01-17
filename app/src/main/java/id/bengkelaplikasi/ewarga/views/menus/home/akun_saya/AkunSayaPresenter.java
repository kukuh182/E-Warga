package id.bengkelaplikasi.ewarga.views.menus.home.akun_saya;

import android.app.Activity;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.dao.ProfilWargaDao;
import id.bengkelaplikasi.ewarga.dao.RealmCallBack;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.GPSTracker;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.views.base.Presenter;
import id.bengkelaplikasi.ewarga.views.menus.home.Communicator;

import static id.bengkelaplikasi.ewarga.helper.SharedPref.getInt;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class AkunSayaPresenter implements Presenter<AkunSayaView> {
    private AkunSayaView mView;
    private ProfilWargaDao dao = new ProfilWargaDao();
    private Communicator communicator;
    private Activity activity;
    private Context context;
    private GPSTracker gpsTracker;

    public AkunSayaPresenter(Activity act, Context ctx){
        this.activity = act;
        this.context = ctx;
    }
    @Override
    public void onAttach(AkunSayaView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }

    public void getDataProfil(){
        int id = getInt(Constanta.Preference.ID);
        if(id>0){
            ProfilWarga warga = new ProfilWarga();
            warga.setSysusermobile_id(id);
            warga = dao.get(warga);
            mView.showProfil(warga);
        }
    }

    public void showLocation(){
        int id = getInt(Constanta.Preference.ID);
        if(id>0){
            ProfilWarga warga = new ProfilWarga();
            warga.setSysusermobile_id(id);
            warga = dao.get(warga);
            try{
                String nama = warga.getRktp_nama();
                double latitude = Double.parseDouble(warga.getRktp_lat());
                double longitude = Double.parseDouble(warga.getRktp_long());
                communicator.sendLongLatToLocation(nama, longitude, latitude);
            } catch (Exception e) {
                mView.showMessage("Lokasi Tidak Ditemukan, Silakan Update Lokasi");
            }
        }
    }

    public LatLng getCurrentLocation() {
        if(Utilities.permissionLocationOn(activity)){
            gpsTracker = new GPSTracker(context, activity);
            if(gpsTracker.canGetLocation()){
                double longitude = gpsTracker.getLongitude();
                double latitude = gpsTracker.getLatitude();
                return new LatLng(latitude, longitude);
            } else {
                gpsTracker.showSettingsAlert();
            }
        }
        return null;
    }

    public void updateLocationToServer(final LatLng latLng, MaterialDialog mdGetLocation) {
        mdGetLocation.dismiss();
        mView.showProgressDialog("Update Lokasi", "Proses Update Lokasi");
        HashMap<String, Double> params = new HashMap<>();
        params.put("rktp_long", latLng.longitude);
        params.put("rktp_lat", latLng.latitude);

        Service service = Client.initialize(Constanta.Url.APP);
        String nik = getNik();
        if(nik!=null){

            Client.request(service.updateLokasi(nik,params), new Client.CallBackRequest() {
                @Override
                public void successCode(String message, String rd) {
                    if(mView!=null){
                        mView.hideProgressDialog();
                        checkResponse(message, latLng);
                    }
                }

                @Override
                public void unsuccessCode(String rc, String rm) {
                    if(mView!=null){
                        mView.hideProgressDialog();
                        mView.showMessage(rm);
                    }
                }

                @Override
                public void unsuccessResponse(String message) {
                    if(mView!=null){
                        mView.hideProgressDialog();
                        mView.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                }

                @Override
                public void parsingError(String message) {
                    if(mView!=null){
                        mView.hideProgressDialog();
                        mView.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                }

                @Override
                public void otherError(String message) {
                    if(mView!=null){
                        mView.hideProgressDialog();
                        mView.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                }

                @Override
                public void failure(String message) {
                    if(mView!=null){
                        mView.hideProgressDialog();
                        mView.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                }
            });
        }else {
            if(mView!=null){
                mView.hideProgressDialog();
                mView.showMessage("Data Warga Kosong");
            }
        }
    }

    private void checkResponse(final String message, LatLng latLng) {
        ProfilWarga warga = new ProfilWarga();
        warga.setSysusermobile_id(SharedPref.getInt(Constanta.Preference.ID));
        warga = dao.get(warga);
        warga.setRktp_lat(String.valueOf(latLng.latitude));
        warga.setRktp_long(String.valueOf(latLng.longitude));

        //Update data warga
        ProfilWargaDao dao = new ProfilWargaDao();
        final ProfilWarga finalWarga = warga;
        dao.update(warga, new RealmCallBack() {
            @Override
            public void result(boolean status) {
                if(status) {
                    if(mView!=null){
                        mView.showMessage(message);
                        mView.showProfil(finalWarga);
                    }
                }else {
                    if(mView!=null){
                        mView.showMessage("Gagal Update Lokasi");
                    }
                }
            }
        });
    }

    private String getNik(){

        ProfilWargaDao dao = new ProfilWargaDao();
        ProfilWarga warga = new ProfilWarga();
        try{
            int id = getInt(Constanta.Preference.ID);
            warga.setSysusermobile_id(id);
            warga = dao.get(warga);
            return warga.getRktp_nik();
        }catch(Exception e){
            return null;
        }
    }

    public void clearDataProfilWarga(final MaterialDialog md){
        int id = SharedPref.getInt(Constanta.Preference.ID);
        if(id!=0){
            ProfilWarga warga = new ProfilWarga();
            warga.setSysusermobile_id(id);
            new ProfilWargaDao().delete(warga, new RealmCallBack() {
                @Override
                public void result(boolean status) {
                    if(status){
                        clearPreference();
                        mView.callLogin();
                    }else{
                        mView.showMessage("Unsuccess clear data");
                    }
                }
            });
        }else {
            mView.showMessage("Data warga kosong");
        }
        md.dismiss();
    }

    private void clearPreference(){
        SharedPref.deleteString(Constanta.Preference.ID);
        SharedPref.deleteString(Constanta.Preference.USERNAME);
        SharedPref.deleteString(Constanta.Preference.PASSWORD);
        SharedPref.deleteString(Constanta.Preference.DEVICEID);
    }
}
