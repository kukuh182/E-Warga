package id.bengkelaplikasi.ewarga.dao;

import java.util.List;

import id.bengkelaplikasi.ewarga.models.KategoriFasum;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by KODEOK-KUKUH on 21-Nov-17.
 */

public class KategoriFasumDao {

    private Realm realm = null;
    private RealmResults<KategoriFasum> realmResult;

    public void save(KategoriFasum obj){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        realm.close();
    }

    public void delete(KategoriFasum obj){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<KategoriFasum> data = realm.where(KategoriFasum.class)
                .findAll();
        data.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public List<KategoriFasum> gets() {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realmResult = realm.where(KategoriFasum.class).findAll();
        realm.commitTransaction();
        realm.close();
        return realmResult;
    }
}
