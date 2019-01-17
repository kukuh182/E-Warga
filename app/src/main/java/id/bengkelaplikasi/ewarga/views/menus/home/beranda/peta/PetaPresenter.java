package id.bengkelaplikasi.ewarga.views.menus.home.beranda.peta;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.dao.ProfilWargaDao;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.GPSTracker;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class PetaPresenter implements Presenter<PetaView>{

    PetaView view;
    Activity activity;
    Context context;

    public PetaPresenter(Context context, Activity activity){
        this.context=context;
        this.activity=activity;
    }

    @Override
    public void onAttach(PetaView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public interface callBackPetaWarga{
        void result(List<PetaModel> data, String message);
    }


    public void getListFasumToServer(final callBackPetaWarga callBackPetaWarga){
        ProfilWarga warga = getDataWarga();
        if(warga!=null) {
            if (Utilities.isInternetAvailable()) {
                HashMap<String, String> params = new HashMap<>();
                params.put("rprov_kode", warga.getRprov_kode());
                params.put("rkota_kode", warga.getRkota_kode());
                params.put("rkec_kode", warga.getRkec_kode());
                params.put("rkel_kode", warga.getRkel_kode());
                params.put("rktp_rw", warga.getRktp_rw());
                params.put("rktp_rt", warga.getRktp_rt());
                params.put("rktp_lat", String.valueOf(PetaActivity.newLat));
                params.put("rktp_long", String.valueOf(PetaActivity.newLong));


                Service service = Client.initialize(Constanta.Url.APP);
                Client.request(service.getListFasum(params), new Client.CallBackRequest() {
                    @Override
                    public void successCode(String message, String rd) {
                        //mView.disableProgressBar();
                        callBackPetaWarga.result(checkResponse(rd), message);
                    }

                    @Override
                    public void unsuccessCode(String rc, String rm) {
                        //mView.disableProgressBar();
                        view.showMessage(rm);
                    }

                    @Override
                    public void unsuccessResponse(String message) {
                        //mView.disableProgressBar();
                        //mView.showMessage(Constanta.Message.ERR_CONNECTION);
                    }

                    @Override
                    public void parsingError(String message) {
                        //mView.disableProgressBar();
                        //mView.showMessage(Constanta.Message.ERR_CONNECTION);
                    }

                    @Override
                    public void otherError(String message) {
                    }

                    @Override
                    public void failure(String message) {
                        //mView.disableProgressBar();
                        //mView.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                });
            } else {
                callBackPetaWarga.result(null, "Tidak Terkonesi Internet");
            }
        }
    }


    private List<PetaModel> checkResponse(String response) {
        List<PetaModel> data = null;
        try{
            JSONArray arr_data = new JSONArray(response);
            data = new ArrayList<>();
            for (int i = 0;i<arr_data.length();i++){
                JSONObject obj2 = arr_data.getJSONObject(i);
                PetaModel wargaModel = new PetaModel();

                wargaModel.setRktpNik(obj2.getString("rktp_nik"));
                wargaModel.setRktpNama(obj2.getString("rktp_nama"));
                wargaModel.setRktpAlamat(obj2.getString("rktp_alamat"));
                wargaModel.setRktpRt(obj2.getString("rktp_rt"));
                wargaModel.setRktpRw(obj2.getString("rktp_rw"));
                wargaModel.setRLongitude(obj2.getString("rktp_long"));
                wargaModel.setRLatitude(obj2.getString("rktp_lat"));
                wargaModel.setRType(obj2.getString("type"));
                data.add(wargaModel);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            view.showMessage(Constanta.Message.ERR_FAIED_DATA);
        }
        return data;
    }

    public ProfilWarga getDataWarga(){
        int id = SharedPref.getInt(Constanta.Preference.ID);
        if(id>0){
            ProfilWarga warga = new ProfilWarga();
            warga.setSysusermobile_id(id);
            return new ProfilWargaDao().get(warga);
        }else {
            return null;
        }
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
}
