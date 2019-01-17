package id.bengkelaplikasi.ewarga.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.main.App;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class Utilities {

    private static Context context = App.getContext();

    public static void snackbar(View v, String message){
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void snackbarAction(View v, String message, String nameAction, final SnackbarActionCallBack callBack){
        Snackbar snackbar = Snackbar
                .make(v, message, Snackbar.LENGTH_LONG)
                .setAction(nameAction, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callBack.onAction();
                    }
                });

        snackbar.show();
    }

    public static void snackbarAction(View v, String message, String nameAction,
                                      final String id, final SnackbarActionCallBack callBack){
        Snackbar snackbar = Snackbar
                .make(v, message, Snackbar.LENGTH_LONG)
                .setAction(nameAction, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callBack.onAction(id);
                    }
                });

        snackbar.show();
    }

    public static Boolean isInternetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnectedOrConnecting();
    }

    public interface SnackbarActionCallBack {
        void onAction();
        void onAction(String id);
    }

    public static Boolean checkPermission(Activity activity, String[] PERMISSIONS){
        Boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int PERMISSION_ALL   = 1;
            if (!hasPermission(PERMISSIONS)) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
            } else {
                return true;
            }
            return false;
        }else {
            return true;
        }
    }

    private static boolean hasPermission(String... permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String getDeviceID(Activity activity) {
        String result = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission [] = {Permissions.Phone.READ_PHONE_STATE};
            if(checkPermission(activity,permission)) {
                TelephonyManager mngr = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
                result = mngr.getDeviceId();
            }
        } else {
            TelephonyManager mngr = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
            result = mngr.getDeviceId();
        }
        return result;
    }

    public static boolean permissionLocationOn(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission [] = {Permissions.Location.ACCESS_COARSE_LOCATION,
                                    Permissions.Location.ACCESS_FINE_LOCATION};
            return checkPermission(activity,permission);
        }else {
            return true;
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String sha1(String value){
        byte[] sha1hash = new byte[40];
        try{
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            md.update(value.getBytes("iso-8859-1"), 0, value.length());
            sha1hash = md.digest();
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e){}
        return convertToHex(sha1hash);
    }

    public static String alphabeticName(String name){
        return name.substring(0,1).toUpperCase();
    }

    public static boolean isValidEmail(String email){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public static void sendMessage(Activity activity, String message, String phone_number){
        try{
            String[] permission = {Permissions.Sms.SEND_SMS};
            if(checkPermission(activity, permission)){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone_number, null, message, null, null);
            }
        }catch (Exception e){
            Log.d("SND_ERROR", e.getMessage().toString());
        }
    }

    public static String getTimeStamp(){
        return new SimpleDateFormat("ddMMyyyyhhmmss").format(new Date());
    }

    public static boolean isLocationPermission(Activity activity){
        String[] permissions = {Permissions.Location.ACCESS_FINE_LOCATION,Permissions.Location.ACCESS_COARSE_LOCATION};
        return Utilities.checkPermission(activity, permissions);
    }

    public static Location getYourLocation(Context context, Activity activity){
        GPSTracker gps = new GPSTracker(context, activity);
        if(gps.canGetLocation()){
            return gps.getLocation();
        }else {
            gps.showSettingsAlert();
            return null;
        }
    }

    public static String byteArraytoBase64(byte[] byte_image){
        return Base64.encodeToString(byte_image,
                Base64.DEFAULT);
    }

    public static String byteArrayToString(byte [] data_byte){
        try{
            System.gc();
            return Base64.encodeToString(data_byte, Base64.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }catch(OutOfMemoryError e){
            Log.e("EWN", "Out of memory error catched");
            return Base64.encodeToString(data_byte, Base64.DEFAULT);
        }
        return null;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
        return baos.toByteArray();
    }

    public static String splitText(String text, String type_split, int index){
        return text.split(type_split)[index];
    }

    public static BitmapDescriptor getBitmap(String type){
        BitmapDescriptor bmp = null;
        if(type.equals("000")) { //warga
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.home);
        }else if(type.equals("001")) { //Masjid
            bmp = BitmapDescriptorFactory.fromResource(R.mipmap.ic_mesjid);
        }else if(type.equals("002")) { //Gereja
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.gereja);
        }else if(type.equals("003")) { //Vihara
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.vihara);
        }else if(type.equals("004")) { //Pure
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.pure);
        }else if(type.equals("005")) { //taman
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.taman);
        }else if(type.equals("006")) { //rumah_duka
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.rumah_duka);
        }else if(type.equals("007")) { //pasar
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.pasar);
        }else if(type.equals("008")) { //lembaga
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.lembaga);
        }else if(type.equals("009")) { //lapangan
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.lapangan);
        }else if(type.equals("011")) { //wc_umum
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.wc_umum);
        }else if(type.equals("101")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("102")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("103")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("104")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("105")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("106")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("107")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("108")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("109")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("110")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("111")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("112")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("113")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("114")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("115")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("116")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("117")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("118")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("119")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("120")) { //perpustakaan
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.perpustakaan);
        }else if(type.equals("121")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("122")) { //laboratorium
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.laboratorium);
        }else if(type.equals("123")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("124")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("125")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("126")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("127")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("128")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("129")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("130")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.universitas);
        }else if(type.equals("199")) { //Universitas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.pendidikan_lain);
        }else if(type.equals("201")) { //PAM
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.pam);
        }else if(type.equals("301")) { //Kantor RW
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.kantor_rw);
        }else if(type.equals("305")) { //Kantor RT
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.kantor_rt);
        }else if(type.equals("302")) { //Pos Kamling
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.pos_kamling);
        }else if(type.equals("303")) { //gedung_swadaya
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.gedung_swadaya);
        }else if(type.equals("304")) { //gedung_serbaguna
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.gedung_serbaguna);
        }else if(type.equals("306")) { //Instansi
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.instansi);
        }else if(type.equals("307")) { //KUA
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.kua);
        }else if(type.equals("308")) { //Pengadilan
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.pengadilan);
        }else if(type.equals("309")) { //Kelurahan
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.kelurahan);
        }else if(type.equals("310")) { //Desa
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.kantor_desa);
        }else if(type.equals("311")) { //Umum
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.kantor_umum);
        }else if(type.equals("312")) { //Kecamatan
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.kecamatan);
        }else if(type.equals("313")) { //kud
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.kud);
        }else if(type.equals("401")) { //lainnya
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.biru);
        }else if(type.equals("402")) { //tpu
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.tpu);
        }else if(type.equals("501")) { //yayasan
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.yayasan);
        }else if(type.equals("502")) { //pos_yandu
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.pos_yandu);
        }else if(type.equals("503")) { //panti
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.panti);
        }else if(type.equals("504")) { //rumah_zakat
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.rumah_zakat);
        }else if(type.equals("601")) { //tni
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.tni);
        }else if(type.equals("602")) { //tni
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.tni);
        }else if(type.equals("603")) { //tni
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.tni);
        }else if(type.equals("604")) { //tni
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.tni);
        }else if(type.equals("605")) { //tni
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.tni);
        }else if(type.equals("606")) { //tni
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.tni);
        }else if(type.equals("601")) { //tni
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.tni);
        }else if(type.equals("701")) { //RS
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.hospital);
        }else if(type.equals("702")) { //rs_bersalin
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.rs_bersalin);
        }else if(type.equals("703")) { //Puskesmas
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.puskesmas);
        }else if(type.equals("704")) { //Poliklinik
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.poliklinik);
        }else if(type.equals("801")) { //kantor_polisi
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.badge);
        }else if(type.equals("802")) { //damkar
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.damkar);
        }else if(type.equals("901")) { //rumah_kaca
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.rumah_kaca);
        }else if(type.equals("902")) { //posko
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.posko);
        }else if(type.equals("903")) { //organisasi
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.organisasi);
        }else if(type.equals("904")) { //asrama
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.asrama);
        }else if(type.equals("905")) { //aula
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.aula);
        }else if(type.equals("999")) { //aula
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.bengkelaplikasi_icon);
        }else{
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.biru);
        }
        return bmp;
    }
}
