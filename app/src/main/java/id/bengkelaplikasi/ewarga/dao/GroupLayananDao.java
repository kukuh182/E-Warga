package id.bengkelaplikasi.ewarga.dao;

import java.util.List;

import id.bengkelaplikasi.ewarga.models.GroupLayanan;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by KODEOK-KUKUH on 15-Nov-17.
 */


public class GroupLayananDao {

    private Realm realm = null;
    private RealmResults<GroupLayanan> realmResult;

    public void save(GroupLayanan obj){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        realm.close();
    }

    public void delete(GroupLayanan obj){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<GroupLayanan> data = realm.where(GroupLayanan.class)
                .findAll();
        data.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public List<GroupLayanan> gets() {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realmResult = realm.where(GroupLayanan.class).findAll();
        realm.commitTransaction();
        realm.close();
        return realmResult;
    }
}
