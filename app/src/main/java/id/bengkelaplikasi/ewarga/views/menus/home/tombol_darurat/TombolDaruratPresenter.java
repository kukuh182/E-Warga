package id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.telephony.SmsManager;
import android.text.TextUtils;

import java.util.List;

import id.bengkelaplikasi.ewarga.dao.ProfilWargaDao;
import id.bengkelaplikasi.ewarga.dao.WhitelistDao;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import id.bengkelaplikasi.ewarga.helper.GPSTracker;
import id.bengkelaplikasi.ewarga.helper.Permissions;
import id.bengkelaplikasi.ewarga.helper.SharedPref;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.models.Whitelist;
import id.bengkelaplikasi.ewarga.views.base.Presenter;

import static android.R.attr.name;
import static id.bengkelaplikasi.ewarga.helper.Utilities.checkPermission;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class TombolDaruratPresenter implements Presenter<TombolDaruratView> {

    TombolDaruratView view;
    Activity activity;
    Context context;

    public TombolDaruratPresenter(Context context, Activity activity){
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void onAttach(TombolDaruratView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public void daruratProcess(final String message_user){
        if(Utilities.isLocationPermission(activity)){
            final Location location = Utilities.getYourLocation(context, activity);
            if(location!=null){
                if (location.getLatitude()!=0&&location.getLongitude()!=0){
                    List<Whitelist> whitelists = getWhitelist();
                    if (whitelists.size()>0){
                        for (int i = 0;i<whitelists.size(); i++){
                            String message = createMessageUrgent(message_user, getUser(),
                                    location.getLongitude(), location.getLatitude());
                            String phone = whitelists.get(i).getPhone();
                            Utilities.sendMessage(activity, message, phone);
                        }
                        view.showMessage("Pesan Darurat Telah Terkirim");
                    }else {
                        view.showMessage("Kontak Whitelist Kosong");
                    }
                }else {
                    view.showMessage("Lokasi Belum Ditemukan");
                }
            }else {
                view.showMessage("Lokasi Belum Ditemukan");
            }
        }

    }



    private String createMessageUrgent(String message_user, String nama, double longitude, double latitude){
        String message_location = "- Tombol Darurat. Koordinat: google.com/maps/search/?api=1&query="+latitude+","+longitude;
        if (!TextUtils.isEmpty(message_user)){
            return "["+nama+"] "+message_user+message_location;
        }else {
            return "Kerabat Anda "+nama+" dalam bahaya"+message_location;
        }
    }

    private List<Whitelist> getWhitelist(){
        WhitelistDao dao = new WhitelistDao();
        return dao.get();
    }

    private String getUser(){
        ProfilWargaDao dao = new ProfilWargaDao();
        return dao.get().get(0).getRktp_nama();
    }
}
