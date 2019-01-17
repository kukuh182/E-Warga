package id.bengkelaplikasi.ewarga.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by KODEOK-KUKUH on 21-Nov-17.
 */

public class KategoriFasum extends RealmObject {

    @PrimaryKey
    private String kode;
    private String deskripsi;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
