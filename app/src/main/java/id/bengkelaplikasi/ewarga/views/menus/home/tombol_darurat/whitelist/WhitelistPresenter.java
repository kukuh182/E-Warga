package id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.whitelist;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import id.bengkelaplikasi.ewarga.dao.RealmCallBack;
import id.bengkelaplikasi.ewarga.dao.WhitelistDao;
import id.bengkelaplikasi.ewarga.helper.Permissions;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.models.Whitelist;
import id.bengkelaplikasi.ewarga.views.base.Presenter;
import id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.whitelist.adapter.WhitelistMA;

/**
 * Created by ASUS on 10/12/2017.
 */

public class WhitelistPresenter implements Presenter<WhitelistView> {

    WhitelistView view;
    Activity activity;

    public WhitelistPresenter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onAttach(WhitelistView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public ArrayList<String> getListContactPhone() {
        String[] permissions = {Permissions.Contact.READ_CONTACT};
        ArrayList<String> contacts = new ArrayList<>();
        if (Utilities.checkPermission(activity, permissions)) {
            Cursor cursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String contact = name + "\n" + phonenumber;
                contacts.add(contact);
            }
            cursor.close();
            Collections.sort(contacts, new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return s.compareTo(t1);
                }
            });
            return contacts;
        }
        return null;
    }

    public void addWhitelist(String name, String phone) {
        WhitelistDao dao = new WhitelistDao();
        Whitelist obj = new Whitelist();
        obj.setName(name);
        obj.setPhone(phone);
        dao.add(obj, new RealmCallBack() {
            @Override
            public void result(boolean status) {
                if (status) {
                    view.initDataWhitelist();
                } else {
                    view.showMessage("Gagal Menambahkan Whitelist");
                }
            }
        });
    }

    public ArrayList<WhitelistMA> getWhitelist() {
        ArrayList<WhitelistMA> datas = new ArrayList<>();
        WhitelistDao dao = new WhitelistDao();
        List<Whitelist> result = dao.get();

        for (int i = 0; i < result.size(); i++) {
            WhitelistMA model = new WhitelistMA();
            model.setId(result.get(i).getId());
            model.setAlfabet(result.get(i).getName());
            model.setName(result.get(i).getName());
            model.setPhone(result.get(i).getPhone());
            datas.add(model);
        }
        return datas;
    }

    public void deleteWhitelist(int id) {
        WhitelistDao dao = new WhitelistDao();
        Whitelist whitelist = new Whitelist();
        whitelist.setId(id);
        dao.delete(whitelist, new RealmCallBack() {
            @Override
            public void result(boolean status) {
                if (status) {
                    view.initDataWhitelist();
                } else {
                    view.showMessage("Gagal Hapus Whitelist");
                }
            }
        });
    }

    public List<WhitelistMA> searchWhitelist(List<WhitelistMA> data, String text) {
        List<WhitelistMA> temp = new ArrayList();
        for (WhitelistMA model : data) {
            if (model.getName().contains(text) ||
                    model.getName().toLowerCase().contains(text) ||
                    model.getName().toUpperCase().contains(text)) {
                temp.add(model);
            }
        }
        Collections.sort(temp, new Comparator<WhitelistMA>() {
            @Override
            public int compare(WhitelistMA model, WhitelistMA t1) {
                return model.getName().compareTo(t1.getName());
            }
        });
        return temp;
    }
}
