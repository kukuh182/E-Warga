package id.bengkelaplikasi.ewarga.views.menus.register;

import android.app.Activity;
import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import id.bengkelaplikasi.ewarga.api.Client;
import id.bengkelaplikasi.ewarga.api.Service;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

/**
 * Created by Kukuh182 on 05-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class RegisterPresenter implements Presenter<RegisterView>{

    private RegisterView mView;

    Activity activity;

    public RegisterPresenter(Activity activity){
        this.activity = activity;
    }
    @Override
    public void onAttach(RegisterView view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        this.mView = null;
    }

    public void validasiRegistrasi(RegisterModel model){
        String deviceid = Utilities.getDeviceID(activity);
        if(!TextUtils.isEmpty(deviceid)){
            model.setDevice_id(deviceid);
            if(TextUtils.isEmpty(model.getNik())){
                mView.showMessage("NIK Kosong");
            }else if(TextUtils.isEmpty(model.getNama_lengkap())){
                mView.showMessage("Nama Lengkap Kosong");
            }else if(TextUtils.isEmpty(model.getPassword())){
                mView.showMessage("Password Kosong");
            }else if(TextUtils.isEmpty(model.getRepassword())){
                mView.showMessage("Konfirmasi Password Kosong");
            }else if(TextUtils.isEmpty(model.getEmail())){
                mView.showMessage("Email Kosong");
            }else if(!Utilities.isValidEmail(model.getEmail())){
                mView.showMessage("Email Tidak Valid");
            }else if(model.getSmartphone().equals("+62")){
                mView.showMessage("No Handphone Kosong");
            }else if(TextUtils.isEmpty(model.getRt())){
                mView.showMessage("RT Kosong");
            }else if(TextUtils.isEmpty(model.getRw())){
                mView.showMessage("RW Kosong");
            }else if(TextUtils.isEmpty(model.getKode_pos())){
                mView.showMessage("Kode Pos Kosong");
            }else if(TextUtils.isEmpty(model.getAlamat())){
                mView.showMessage("Alamat Kosong");
            }else if(model.getPassword().length()<6) {
                mView.showMessage("Password Minimal 6 Digit");
            }else {
                if(!model.getPassword().equals(model.getRepassword())){
                    mView.showMessage("Password Tidak Sesuai");
                }else {
                    if(TextUtils.isEmpty(model.getPhone())){
                        model.setPhone("-");
                    }
                    registrasiToServer(model);
                }
            }
        }
    }

    private void registrasiToServer(RegisterModel model){
        mView.showDialogProgress("Registrasi", "Proses Registrasi User");
        String mId = FirebaseInstanceId.getInstance().getToken();
        HashMap<String, String> params = new HashMap<>();
        params.put("rmember_nik", model.getNik());
        params.put("rmember_email", checkString(model.getEmail()));
        params.put("rmember_namalengkap", checkString(model.getNama_lengkap()));
        params.put("rmember_password", checkString(Utilities.sha1(model.getPassword())));
        params.put("rmember_hp", checkString(model.getSmartphone()));
        params.put("rmember_telp", checkString(model.getPhone()));
        params.put("rmember_rt", checkString(model.getRt()));
        params.put("rmember_rw", checkString(model.getRw()));
        params.put("rmember_kodepos", checkString(model.getKode_pos()));
        params.put("rmember_alamat", checkString(model.getAlamat()));
        params.put("rprov_kode", checkString(model.getProv_kode()));
        params.put("rkota_kode", checkString(model.getKota_kode()));
        params.put("rkec_kode", checkString(model.getKec_kode()));
        params.put("rkel_kode", checkString(model.getKel_kode()));
        params.put("rmember_verifikasi", "0");
        params.put("rmember_latitude", "-");
        params.put("rmember_longitude", "-");
        params.put("rtypemember_id", "1");
        params.put("rmember_status", "0");
        params.put("rmember_deviceid", checkString(model.getDevice_id()));
        params.put("rmember_idfcm", mId);
        Service service = Client.initialize(Constanta.Url.APP);
        Client.request(service.registrasi(params), new Client.CallBackRequest() {
            @Override
            public void successCode(String message, String rd) {
                if(mView!=null){
                    mView.hideDialogProgress();
                    mView.showDialogRegisterSuccess();
                }
            }

            @Override
            public void unsuccessCode(String rc, String rm) {
                if(mView!=null){
                    mView.hideDialogProgress();
                    mView.showMessage(rm);
                }
            }

            @Override
            public void unsuccessResponse(String message) {
                if(mView!=null){
                    mView.hideDialogProgress();
                    mView.showMessage(message);
                }
            }

            @Override
            public void parsingError(String message) {
                if(mView!=null){
                    mView.hideDialogProgress();
                    mView.showMessage(message);
                }
            }

            @Override
            public void otherError(String message) {
                if(mView!=null){
                    mView.hideDialogProgress();
                    mView.showMessage(message);
                }
            }

            @Override
            public void failure(String message) {
                if(mView!=null){
                    mView.hideDialogProgress();
                    mView.showMessage(message);
                }
            }
        });
    }

    private String checkString(String message){
        if(TextUtils.isEmpty(message)){
            return "-";
        }else{
            return message;
        }
    }
}
