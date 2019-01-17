package id.bengkelaplikasi.ewarga.dao;

import java.util.List;

import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kukuh182 on 10-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class ProfilWargaDao {

    private Realm realm = null;
    private RealmResults<ProfilWarga> realmResult;

    public void add(final ProfilWarga obj, final RealmCallBack callBack){
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.copyToRealm(obj);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callBack.result(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callBack.result(false);
            }
        });
    }

    public void update(final ProfilWarga obj, final RealmCallBack callBack) {
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.where(ProfilWarga.class).equalTo("sysusermobile_id",obj.getSysusermobile_id()).findFirst();
                bgRealm.copyToRealmOrUpdate(obj);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callBack.result(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callBack.result(false);
            }
        });
    }

    public void delete(final ProfilWarga obj, final RealmCallBack callBack) {
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                realmResult      = bgRealm.where(ProfilWarga.class).equalTo("sysusermobile_id",obj.getSysusermobile_id()).findAll();
                ProfilWarga data = realmResult.get(0);
                data.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callBack.result(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callBack.result(false);
            }
        });
    }

    public ProfilWarga get(ProfilWarga obj) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ProfilWarga data = realm.where(ProfilWarga.class).equalTo("sysusermobile_id",obj.getSysusermobile_id()).findFirst();
        realm.commitTransaction();
        realm.close();
        return data;
    }

    public List<ProfilWarga> get() {
        realm = Realm.getDefaultInstance();
        RealmResults<ProfilWarga> data = realm.where(ProfilWarga.class).findAll();
        return realm.copyFromRealm(data);

    }

}
