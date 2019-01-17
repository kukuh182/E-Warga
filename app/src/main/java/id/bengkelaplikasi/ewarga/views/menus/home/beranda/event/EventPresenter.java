package id.bengkelaplikasi.ewarga.views.menus.home.beranda.event;

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
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.views.base.Presenter;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga.adapter.DaftarWargaModel;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.event.adapter.EventMA;

import static android.R.attr.data;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class EventPresenter implements Presenter<EventView>{

    EventView view;

    @Override
    public void onAttach(EventView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public interface CallBackEvent{
        void result(List<EventMA> data, String message);
    }

    public void getListEventToServer(final CallBackEvent callBack){
        ProfilWarga warga = getDataWarga();
        if(warga!=null){
            if(Utilities.isInternetAvailable()){
                view.enableProgressBar();
                HashMap<String, String> params = new HashMap<>();
                //params.put("rprov_kode", warga.getRprov_kode());
                //params.put("rkota_kode", warga.getRkota_kode());

                params.put("rprov_kode", "32");
                params.put("rkota_kode", "73");
                Service service = Client.initialize(Constanta.Url.APP);
                Client.request(service.getListEvent(params), new Client.CallBackRequest() {
                    @Override
                    public void successCode(String message, String rd) {
                        view.disableProgressBar();
                        callBack.result(checkResponse(rd), message);
                    }

                    @Override
                    public void unsuccessCode(String rc, String rm) {
                        view.disableProgressBar();
                        view.showMessage(rm);
                    }

                    @Override
                    public void unsuccessResponse(String message) {
                        view.disableProgressBar();
                        view.showMessage(Constanta.Message.ERR_CONNECTION);
                    }

                    @Override
                    public void parsingError(String message) {
                        view.disableProgressBar();
                        view.showMessage(Constanta.Message.ERR_CONNECTION);
                    }

                    @Override
                    public void otherError(String message) {
                        view.disableProgressBar();
                        view.showMessage(Constanta.Message.ERR_CONNECTION);
                    }

                    @Override
                    public void failure(String message) {
                        view.disableProgressBar();
                        view.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                });
            }else {
                view.showMessage(Constanta.Message.ERR_CONNECTION);
            }
        }
    }

    private List<EventMA> checkResponse(String response) {
        List<EventMA> data = null;
        try{
            JSONArray arr_data = new JSONArray(response);
            data = new ArrayList<>();
            for (int i = 0;i<arr_data.length();i++){
                JSONObject obj2 = arr_data.getJSONObject(i);
                EventMA model = new EventMA();
                model.setEvent_id(obj2.getString("tevent_id"));
                model.setMember_id(obj2.getString("rmember_id"));
                model.setProv_kode(obj2.getString("rprov_kode"));
                model.setKota_kode(obj2.getString("rkota_kode"));
                model.setEvent_nama(obj2.getString("tevent_nama"));
                model.setEvent_lokasi(obj2.getString("tevent_lokasi"));
                model.setEvent_deskripsi(obj2.getString("tevent_deskripsi"));
                model.setEvent_latitude(obj2.getString("tevent_lattitude"));
                model.setEvent_longitude(obj2.getString("tevent_longitude"));
                model.setEvent_photo("http://"+obj2.getString("teventfoto_pathfoto").replace("\\", ""));
                data.add(model);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            view.showMessage(Constanta.Message.ERR_FAIED_DATA);
        }
        return data;
    }

    private ProfilWarga getDataWarga(){
        ProfilWargaDao dao = new ProfilWargaDao();
        int id = SharedPref.getInt(Constanta.Preference.ID);
        if(id>0){
            ProfilWarga warga = new ProfilWarga();
            warga.setSysusermobile_id(id);
            return dao.get(warga);
        }else {
            return null;
        }
    }
}
