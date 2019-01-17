package id.bengkelaplikasi.ewarga.views.menus.home.informasi.pengumuman;

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
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.base.Presenter;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter.CallBackData;
import id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter.InformasiModel;

/**
 * Created by Kukuh182 on 13-Aug-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class PengumumanPresenter implements Presenter<PengumumanView>{

    private PengumumanView mView;

    @Override
    public void onAttach(PengumumanView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }

    public void getListDataToServer(final CallBackData callBack){

        if(Utilities.isInternetAvailable()){
            mView.enableProgressBar();
            String privinfo = SharedPref.getString(Constanta.Preference.PRIVINFO);
            HashMap<String, String> params = new HashMap<>();
            params.put("sysusermobile_privinfo", privinfo);
            params.put("rmember_id", SharedPref.getString(Constanta.Preference.RMEMBERID));
            Service service = Client.initialize(Constanta.Url.APP);
            Client.request(service.getPengumuman(params), new Client.CallBackRequest() {
                @Override
                public void successCode(String message, String rd) {
                    mView.disableProgressBar();
                    callBack.result(checkResponse(rd), message);
                }

                @Override
                public void unsuccessCode(String rc, String rm) {
                    mView.disableProgressBar();
                    mView.showMessage(rm);
                }

                @Override
                public void unsuccessResponse(String message) {
                    mView.disableProgressBar();
                    mView.showMessage(Constanta.Message.ERR_CONNECTION);
                }

                @Override
                public void parsingError(String message) {
                    mView.disableProgressBar();
                    mView.showMessage(Constanta.Message.ERR_CONNECTION);
                }

                @Override
                public void otherError(String message) {
                    mView.disableProgressBar();
                    mView.showMessage(Constanta.Message.ERR_CONNECTION);
                }

                @Override
                public void failure(String message) {
                    mView.disableProgressBar();
                    mView.showMessage(Constanta.Message.ERR_CONNECTION);
                }
            });
        }else {
            mView.showMessage(Constanta.Message.ERR_CONNECTION);
        }
    }

    private List<InformasiModel> checkResponse(String response) {
        List<InformasiModel> data = null;
        try{
            JSONArray arr_data = new JSONArray(response);

            data = new ArrayList<>();
            for (int i = 0;i<arr_data.length();i++){
                JSONObject obj2 = arr_data.getJSONObject(i);
                InformasiModel InformasiModel = new InformasiModel();
                InformasiModel.setTinformasiId(obj2.getLong("tinformasi_id"));
                InformasiModel.setRlevelinfoNama(obj2.getString("rlevelinfo_nama"));
                InformasiModel.setRinfotypeNama(obj2.getString("rinfotype_nama"));
                InformasiModel.setTinformasiJudul(obj2.getString("tinformasi_judul"));
                InformasiModel.setTinformasiContent(obj2.getString("tinformasi_content"));
                InformasiModel.setTinformasiTglinput(obj2.getString("tinformasi_tglinput"));
                data.add(InformasiModel);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            mView.showMessage(Constanta.Message.ERROR_PARSING);
        }
        return data;
    }

    public List<InformasiModel> serachData(List<InformasiModel> data, String text){
        List<InformasiModel> temp = new ArrayList();
        for (InformasiModel model : data){
            if(model.getTinformasiJudul().contains(text) ||
                    model.getTinformasiJudul().toLowerCase().contains(text) ||
                    model.getTinformasiJudul().toUpperCase().contains(text)){
                temp.add(model);
            }
        }
        Collections.sort(temp, new Comparator<InformasiModel>() {
            @Override
            public int compare(InformasiModel InformasiModel, InformasiModel t1) {
                return InformasiModel.getTinformasiJudul().compareTo(t1.getTinformasiJudul());
            }
        });
        return temp;
    }
}
