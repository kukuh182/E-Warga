package id.bengkelaplikasi.ewarga.views.menus.register.wilayah;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.base.Presenter;
import id.bengkelaplikasi.ewarga.views.menus.register.wilayah.adapter.WilayahModel;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by ASUS on 10/6/2017.
 */

public class WilayahPresenter implements Presenter<WilayahView> {

    WilayahView view;

    @Override
    public void onAttach(WilayahView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public interface CallBackWilayah {
        void result(List<WilayahModel> data, String message);
    }
    
    public void getListWilayah(final int tipe_wilayah, final CallBackWilayah callBack){

        if(Utilities.isInternetAvailable()){
            view.enableProgressBar();
            HashMap<String, String> params = new HashMap<>();
            params.put("type_request", Constanta.TYPE_REQUEST);
            Service service = Client.initialize(Constanta.Url.APP);

            Call<ResponseBody> data_response = null;
            switch (tipe_wilayah){
                case 0:
                    data_response = service.getProvinsi(params);
                    break;
                case 1:
                    params.put("rprov_kode", SharedPref.getString(Constanta.RegPreference.PROV_KODE));
                    data_response = service.getKota(params);
                    break;
                case 2:
                    params.put("rprov_kode", SharedPref.getString(Constanta.RegPreference.PROV_KODE));
                    params.put("rkota_kode", SharedPref.getString(Constanta.RegPreference.KOTA_KODE));
                    data_response = service.getKecamatan(params);
                    break;
                case 3:
                    params.put("rprov_kode", SharedPref.getString(Constanta.RegPreference.PROV_KODE));
                    params.put("rkota_kode", SharedPref.getString(Constanta.RegPreference.KOTA_KODE));
                    params.put("rkec_kode", SharedPref.getString(Constanta.RegPreference.KEC_KODE));
                    data_response = service.getKelurahan(params);
                    break;
            }

            Client.request(data_response, new Client.CallBackRequest() {
                @Override
                public void successCode(String message, String rd) {
                    if(view!=null){
                        view.disableProgressBar();
                        callBack.result(checkResponse(tipe_wilayah, rd), message);
                    }
                }

                @Override
                public void unsuccessCode(String rc, String rm) {
                    if(view!=null){
                        view.disableProgressBar();
                        view.showMessage(rm);
                    }
                }

                @Override
                public void unsuccessResponse(String message) {
                    if(view!=null){
                        view.disableProgressBar();
                        view.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                }

                @Override
                public void parsingError(String message) {
                    if (view != null){
                        view.disableProgressBar();
                        view.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                }

                @Override
                public void otherError(String message) {
                    if (view != null){
                        view.disableProgressBar();
                        view.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                }

                @Override
                public void failure(String message) {
                    if (view != null){
                        view.disableProgressBar();
                        view.showMessage(Constanta.Message.ERR_CONNECTION);
                    }
                }
            });
        }else {
            if (view != null){
                view.showMessage(Constanta.Message.ERR_CONNECTION);
            }
        }
    }

    private List<WilayahModel> checkResponse(int tipe_wilayah, String response) {
        List<WilayahModel> data = null;
        try{
            JSONArray arr_data = new JSONArray(response);
            data = new ArrayList<>();
            for (int i = 0;i<arr_data.length();i++){
                JSONObject obj2 = arr_data.getJSONObject(i);
                WilayahModel model = new WilayahModel();
                switch (tipe_wilayah){
                    case 0:
                        model.setKode(obj2.getString("rprov_kode"));
                        model.setNama(obj2.getString("rprov_nama"));
                        break;
                    case 1:
                        model.setKode(obj2.getString("rkota_kode"));
                        model.setNama(obj2.getString("rkota_nama"));
                        break;
                    case 2:
                        model.setKode(obj2.getString("rkec_kode"));
                        model.setNama(obj2.getString("rkec_nama"));

                        String kd_pos = obj2.getString("rkec_kdpos");
                        if(kd_pos.isEmpty()){
                            kd_pos = "-";
                        }
                        model.setKode_pos(kd_pos);
                        break;
                    case 3:
                        model.setKode(obj2.getString("rkel_kode"));
                        model.setNama(obj2.getString("rkel_nama"));
                        break;
                }
                data.add(model);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            if(view!=null){
                view.showMessage(Constanta.Message.ERROR_PARSING);
            }
        }
        return data;
    }

    public List<WilayahModel> searchWilayah(List<WilayahModel> data, String text){
        List<WilayahModel> temp = new ArrayList();
        for (WilayahModel model : data){
            if(model.getNama().contains(text) ||
                    model.getNama().toLowerCase().contains(text) ||
                    model.getNama().toUpperCase().contains(text)){
                temp.add(model);
            }
        }
        return temp;
    }

    public void saveKodeWilayah(int tipe_wilayah, WilayahModel model){

        switch (tipe_wilayah){
            case 0:
                SharedPref.saveString(Constanta.RegPreference.PROV_KODE,model.getKode());
                SharedPref.saveString(Constanta.RegPreference.PROV_NAMA,model.getNama());
                break;
            case 1:
                SharedPref.saveString(Constanta.RegPreference.KOTA_KODE,model.getKode());
                SharedPref.saveString(Constanta.RegPreference.KOTA_NAMA,model.getNama());
                break;
            case 2:
                SharedPref.saveString(Constanta.RegPreference.KEC_KODE,model.getKode());
                SharedPref.saveString(Constanta.RegPreference.KEC_NAMA,model.getNama());
                SharedPref.saveString(Constanta.RegPreference.KEC_KDPOS,model.getKode_pos());
                break;
            case 3:
                SharedPref.saveString(Constanta.RegPreference.KEL_KODE,model.getKode());
                SharedPref.saveString(Constanta.RegPreference.KEL_NAMA,model.getNama());
                break;
        }

    }
}
