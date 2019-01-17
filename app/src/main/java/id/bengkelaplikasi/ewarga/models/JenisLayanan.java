package id.bengkelaplikasi.ewarga.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by KODEOK-KUKUH on 15-Nov-17.
 */

public class JenisLayanan extends RealmObject {

    @PrimaryKey
    private String kode_group;
    private String kode_jenis;
    private String deskripsi;

    public String getKode_group() {
        return kode_group;
    }

    public void setKode_group(String kode_group) {
        this.kode_group = kode_group;
    }

    public String getKode_jenis() {
        return kode_jenis;
    }

    public void setKode_jenis(String kode_jenis) {
        this.kode_jenis = kode_jenis;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
