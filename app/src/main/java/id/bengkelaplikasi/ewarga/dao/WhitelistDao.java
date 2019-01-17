package id.bengkelaplikasi.ewarga.dao;

import java.util.List;

import id.bengkelaplikasi.ewarga.models.ProfilWarga;
import id.bengkelaplikasi.ewarga.models.Whitelist;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kukuh182 on 14-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class WhitelistDao {

    private Realm realm = null;
    private RealmResults<Whitelist> realmResult;

    public void add(final Whitelist obj, final RealmCallBack callBack){
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Number maxId = bgRealm.where(Whitelist.class).max("id");
                int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                obj.setId(nextId);
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

    public void update(final Whitelist obj, final RealmCallBack callBack) {
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.where(ProfilWarga.class).equalTo("id",obj.getId()).findFirst();
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

    public void delete(final Whitelist obj, final RealmCallBack callBack) {
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                realmResult      = bgRealm.where(Whitelist.class).equalTo("id",obj.getId()).findAll();
                Whitelist data = realmResult.get(0);
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

    public Whitelist get(Whitelist obj) {
        realm = Realm.getDefaultInstance();
        Whitelist data = realm.where(Whitelist.class).equalTo("id",obj.getId()).findFirst();
        return realm.copyFromRealm(data);
    }

    public List<Whitelist> get() {
        realm = Realm.getDefaultInstance();
        RealmResults<Whitelist> data = realm.where(Whitelist.class).findAll();
        return realm.copyFromRealm(data);

    }
}
