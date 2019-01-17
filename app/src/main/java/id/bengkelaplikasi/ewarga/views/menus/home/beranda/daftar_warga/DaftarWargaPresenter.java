package id.bengkelaplikasi.ewarga.views.menus.home.beranda.daftar_warga;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class DaftarWargaPresenter implements Presenter<DaftarWargaView>{

    DaftarWargaView view;

    @Override
    public void onAttach(DaftarWargaView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }


    public interface callBackDataWarga{
        void result(List<DaftarWargaModel> data, String message);
    }

    public void getListWargaToServer(final callBackDataWarga callBack){
        ProfilWarga warga = getDataWarga();
        if(warga!=null){
            if(Utilities.isInternetAvailable()){
                view.enableProgressBar();
                HashMap<String, String> params = new HashMap<>();
                params.put("rprov_kode", warga.getRprov_kode());
                params.put("rkota_kode", warga.getRkota_kode());
                params.put("rkec_kode", warga.getRkec_kode());
                params.put("rkel_kode", warga.getRkel_kode());
                params.put("rktp_rw", warga.getRktp_rw());
                params.put("rmember_id", warga.getRmember_id());
                Service service = Client.initialize(Constanta.Url.APP);
                Client.request(service.getListWarga(params), new Client.CallBackRequest() {
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

    private List<DaftarWargaModel> checkResponse(String response) {
        List<DaftarWargaModel> data = null;
        try{
            JSONArray arr_data = new JSONArray(response);
            data = new ArrayList<>();
            for (int i = 0;i<arr_data.length();i++){
                JSONObject obj2 = arr_data.getJSONObject(i);
                DaftarWargaModel wargaModel = new DaftarWargaModel();
                wargaModel.setRktpNik(obj2.getString("rktp_nik"));
                wargaModel.setRktpNama(obj2.getString("rktp_nama"));
                wargaModel.setRktpAlamat(obj2.getString("rktp_alamat"));
                wargaModel.setRktpRt(obj2.getString("rktp_rt"));
                wargaModel.setRktpRw(obj2.getString("rktp_rw"));
                wargaModel.setRprovNama(obj2.getString("rprov_nama"));
                wargaModel.setRkotaNama(obj2.getString("rkota_nama"));
                wargaModel.setRkecNama(obj2.getString("rkec_nama"));
                wargaModel.setRkelNama(obj2.getString("rkel_nama"));
                data.add(wargaModel);
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

    public List<DaftarWargaModel> serachWarga(List<DaftarWargaModel> data, String text){
        List<DaftarWargaModel> temp = new ArrayList();
        for (DaftarWargaModel model : data){
            if(model.getRktpNama().contains(text) ||
                    model.getRktpNama().toLowerCase().contains(text) ||
                    model.getRktpNama().toUpperCase().contains(text)){
                temp.add(model);
            }
        }
        Collections.sort(temp, new Comparator<DaftarWargaModel>() {
            @Override
            public int compare(DaftarWargaModel daftarWargaModel, DaftarWargaModel t1) {
                return daftarWargaModel.getRktpNama().compareTo(t1.getRktpNama());
            }
        });
        return temp;
    }

}
