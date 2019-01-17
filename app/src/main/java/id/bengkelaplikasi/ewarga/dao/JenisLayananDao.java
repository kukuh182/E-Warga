package id.bengkelaplikasi.ewarga.dao;

import java.util.List;

import id.bengkelaplikasi.ewarga.models.JenisLayanan;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by KODEOK-KUKUH on 15-Nov-17.
 */

public class JenisLayananDao {

    private Realm realm = null;
    private RealmResults<JenisLayanan> realmResult;

    public void save(JenisLayanan obj){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        realm.close();
    }

    public void delete(JenisLayanan obj){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JenisLayanan> data = realm.where(JenisLayanan.class)
                .findAll();
        data.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public List<JenisLayanan> get(String kode_group) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realmResult = realm.where(JenisLayanan.class).equalTo("kode_group", kode_group).findAll();
        realm.commitTransaction();
        realm.close();
        return realmResult;
    }
}
