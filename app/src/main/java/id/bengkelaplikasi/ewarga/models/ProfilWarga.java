package id.bengkelaplikasi.ewarga.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Kukuh182 on 10-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class ProfilWarga extends RealmObject {

    @PrimaryKey
    private int sysusermobile_id;
    private String sysusermobile_username;
    private String sysusermobile_deviceid;
    private String sysusermobile_privinfo;
    private String rktp_nik;
    private String rktp_nama;
    private String rktp_tptlahir;
    private String rktp_tgllahir;
    private String rktp_alamat;
    private String rktp_rt;
    private String rktp_rw;
    private String rktp_long;
    private String rktp_lat;
    private String rprov_kode;
    private String rkota_kode;
    private String rkec_kode;
    private String rkel_kode;
    private String rmember_id;

    public int getSysusermobile_id() {
        return sysusermobile_id;
    }

    public void setSysusermobile_id(int sysusermobile_id) {
        this.sysusermobile_id = sysusermobile_id;
    }

    public String getSysusermobile_username() {
        return sysusermobile_username;
    }

    public void setSysusermobile_username(String sysusermobile_username) {
        this.sysusermobile_username = sysusermobile_username;
    }

    public String getSysusermobile_deviceid() {
        return sysusermobile_deviceid;
    }

    public void setSysusermobile_deviceid(String sysusermobile_deviceid) {
        this.sysusermobile_deviceid = sysusermobile_deviceid;
    }

    public String getSysusermobile_privinfo() {
        return sysusermobile_privinfo;
    }

    public void setSysusermobile_privinfo(String sysusermobile_privinfo) {
        this.sysusermobile_privinfo = sysusermobile_privinfo;
    }

    public String getRktp_nik() {
        return rktp_nik;
    }

    public void setRktp_nik(String rktp_nik) {
        this.rktp_nik = rktp_nik;
    }

    public String getRktp_nama() {
        return rktp_nama;
    }

    public void setRktp_nama(String rktp_nama) {
        this.rktp_nama = rktp_nama;
    }

    public String getRktp_tptlahir() {
        return rktp_tptlahir;
    }

    public void setRktp_tptlahir(String rktp_tptlahir) {
        this.rktp_tptlahir = rktp_tptlahir;
    }

    public String getRktp_tgllahir() {
        return rktp_tgllahir;
    }

    public void setRktp_tgllahir(String rktp_tgllahir) {
        this.rktp_tgllahir = rktp_tgllahir;
    }

    public String getRktp_alamat() {
        return rktp_alamat;
    }

    public void setRktp_alamat(String rktp_alamat) {
        this.rktp_alamat = rktp_alamat;
    }

    public String getRktp_rt() {
        return rktp_rt;
    }

    public void setRktp_rt(String rktp_rt) {
        this.rktp_rt = rktp_rt;
    }

    public String getRktp_rw() {
        return rktp_rw;
    }

    public void setRktp_rw(String rktp_rw) {
        this.rktp_rw = rktp_rw;
    }

    public String getRktp_long() {
        return rktp_long;
    }

    public void setRktp_long(String rktp_long) {
        this.rktp_long = rktp_long;
    }

    public String getRktp_lat() {
        return rktp_lat;
    }

    public void setRktp_lat(String rktp_lat) {
        this.rktp_lat = rktp_lat;
    }

    public String getRprov_kode() {
        return rprov_kode;
    }

    public void setRprov_kode(String rprov_kode) {
        this.rprov_kode = rprov_kode;
    }

    public String getRkota_kode() {
        return rkota_kode;
    }

    public void setRkota_kode(String rkota_kode) {
        this.rkota_kode = rkota_kode;
    }

    public String getRkec_kode() {
        return rkec_kode;
    }

    public void setRkec_kode(String rkec_kode) {
        this.rkec_kode = rkec_kode;
    }

    public String getRkel_kode() {
        return rkel_kode;
    }

    public void setRkel_kode(String rkel_kode) {
        this.rkel_kode = rkel_kode;
    }

    public String getRmember_id() {
        return rmember_id;
    }

    public void setRmember_id(String rmember_id) {
        this.rmember_id = rmember_id;
    }
}
