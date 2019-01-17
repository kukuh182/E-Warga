package id.bengkelaplikasi.ewarga.views.menus.home.beranda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.dao.GroupLayananDao;
import id.bengkelaplikasi.ewarga.dao.JenisLayananDao;
import id.bengkelaplikasi.ewarga.dao.KategoriFasumDao;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.models.GroupLayanan;
import id.bengkelaplikasi.ewarga.models.JenisLayanan;
import id.bengkelaplikasi.ewarga.models.KategoriFasum;
import id.bengkelaplikasi.ewarga.views.base.Presenter;
import id.bengkelaplikasi.ewarga.views.menus.home.beranda.adapter.MenuBerandaMA;

/**
 * Created by Kukuh182 on 25-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class BerandaPresenter implements Presenter<BerandaView>{

    private BerandaView mView;
    private GroupLayananDao glDao;
    private JenisLayananDao jnDao;

    public BerandaPresenter(){
        this.glDao = new GroupLayananDao();
        this.jnDao = new JenisLayananDao();
    }

    @Override
    public void onAttach(BerandaView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }

    public ArrayList<MenuBerandaMA> initListMenuBeranda(){
        int [] icons = {R.drawable.acara, R.drawable.peta, R.drawable.pengaduan, R.drawable.pelayanan, R.drawable.daftar_warga, R.drawable.penanda_lokasi};
        String [] menus = {"ACARA",  "PETA", "PENGADUAN", "PELAYANAN", "DAFTAR WARGA", "TANDAI LOKASI"};
        ArrayList<MenuBerandaMA> data = new ArrayList<>();
        for (int i = 0;i<menus.length; i++){
            MenuBerandaMA model = new MenuBerandaMA();
            model.setIcon(icons[i]);
            model.setDescription(menus[i]);
            data.add(model);
        }
        return data;
    }

    public void checkMenu(String menu){
        switch (menu){
            case "ACARA":
                mView.callAcara();
                break;
            case "PETA":
                mView.callPeta();
                break;
            case "PENGADUAN":
                mView.callPengaduan();
                break;
            case "PELAYANAN":
                checkDataLayanan();
                break;
            case "DAFTAR WARGA":
                mView.callDaftarWarga();
                break;
            case "TANDAI LOKASI":
                checkDataKategoriFasum();
                break;

        }
    }

    private void checkDataLayanan(){
        List<GroupLayanan> data = new GroupLayananDao().gets();
        if(data.size()<=0){
            getListGroupLayanan();
        }else {
            mView.callPelayanan();
        }
    }

    private void checkDataKategoriFasum(){
        List<KategoriFasum> data = new KategoriFasumDao().gets();
        if(data.size()<=0){
            getListKategoriFasum();
        }else {
            mView.callTandaiLokasi();
        }
    }

    private void getListGroupLayanan(){
        mView.showDialogProgress("Progress", "Preparing Component Layanan...");
        HashMap<String, String> params = new HashMap<>();
        int id = SharedPref.getInt(Constanta.Preference.ID);
        params.put("sysusermobile_id", String.valueOf(id));
        Service service = Client.initialize(Constanta.Url.APP);
        Client.request(service.getGroupLayanan(params), new Client.CallBackRequest() {
            @Override
            public void successCode(String message, String rd) {
                checkResponseGroupLayanan(rd);
            }

            @Override
            public void unsuccessCode(String rc, String rm) {

            }

            @Override
            public void unsuccessResponse(String message) {

            }

            @Override
            public void parsingError(String message) {

            }

            @Override
            public void otherError(String message) {

            }

            @Override
            public void failure(String message) {

            }
        });
    }

    private void getListJenisLayanan(){

        int id = SharedPref.getInt(Constanta.Preference.ID);
        HashMap<String, String> params = new HashMap<>();
        params.put("sysusermobile_id", String.valueOf(id));
        params.put("rgjlayan_kode", "");

        Service service = Client.initialize(Constanta.Url.APP);
        Client.request(service.getJenisLayanan(params), new Client.CallBackRequest() {
            @Override
            public void successCode(String message, String rd) {
                checkResponseJenisLayanan(rd);
            }

            @Override
            public void unsuccessCode(String rc, String rm) {

            }

            @Override
            public void unsuccessResponse(String message) {

            }

            @Override
            public void parsingError(String message) {

            }

            @Override
            public void otherError(String message) {

            }

            @Override
            public void failure(String message) {

            }
        });
    }

    private void getListKategoriFasum(){
        mView.showDialogProgress("Progress", "Preparing Component Category...");
        HashMap<String, String> params = new HashMap<>();
        int id = SharedPref.getInt(Constanta.Preference.ID);
        params.put("sysusermobile_id", String.valueOf(id));
        Service service = Client.initialize(Constanta.Url.APP);
        Client.request(service.getKategoriFasum(params), new Client.CallBackRequest() {
            @Override
            public void successCode(String message, String rd) {
                checkResponseKategoriFasum(rd);
            }

            @Override
            public void unsuccessCode(String rc, String rm) {

            }

            @Override
            public void unsuccessResponse(String message) {

            }

            @Override
            public void parsingError(String message) {

            }

            @Override
            public void otherError(String message) {

            }

            @Override
            public void failure(String message) {

            }
        });
    }

    private void checkResponseGroupLayanan(String rd){
        try{
            JSONArray jarray = new JSONArray(rd);
            for (int i = 0; i<jarray.length();i++){
                JSONObject obj = jarray.getJSONObject(i);
                GroupLayanan model = new GroupLayanan();
                model.setKode(obj.getString("rgjlayan_kode"));
                model.setDeskripsi(obj.getString("rgjlayan_desk"));
                glDao.save(model);
            }
            getListJenisLayanan();
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkResponseJenisLayanan(String rd){
        try{
            JSONArray jarray = new JSONArray(rd);
            for (int i = 0; i<jarray.length();i++){
                JSONObject obj = jarray.getJSONObject(i);
                JenisLayanan model = new JenisLayanan();
                model.setKode_group(obj.getString("rgjlayan_kode"));
                model.setKode_jenis(obj.getString("rjlayan_kode"));
                model.setDeskripsi(obj.getString("rjlayan_nama"));
                jnDao.save(model);
            }
            mView.hideDialogProgress();
            mView.callPelayanan();

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkResponseKategoriFasum(String rd){
        try{
            JSONArray jarray = new JSONArray(rd);
            for (int i = 0; i<jarray.length();i++){
                JSONObject obj = jarray.getJSONObject(i);
                KategoriFasum model = new KategoriFasum();
                model.setKode(obj.getString("rkategori_id"));
                model.setDeskripsi(obj.getString("rkategori_desc"));
                new KategoriFasumDao().save(model);
            }
            mView.hideDialogProgress();
            mView.callTandaiLokasi();
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
