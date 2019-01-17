package id.bengkelaplikasi.ewarga.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Kukuh182 on 14-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class Whitelist extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
