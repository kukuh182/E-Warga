package id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.whitelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Dialog;
import id.bengkelaplikasi.ewarga.helper.Permissions;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.whitelist.adapter.WhitelistAdapter;
import id.bengkelaplikasi.ewarga.views.menus.home.tombol_darurat.whitelist.adapter.WhitelistMA;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static id.bengkelaplikasi.ewarga.main.App.getContext;

/**
 * Created by ASUS on 10/12/2017.
 */

public class WhitelistActivity extends AppCompatActivity implements WhitelistView {

    static final int PICK_CONTACT = 1;
    WhitelistPresenter presenter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @BindView(R.id.fab_add_whitelist)FloatingActionButton fab_add_whitelist;
    @BindView(R.id.toolbar_whitelist)Toolbar toolbar_whitelist;
    @BindView(R.id.et_search_whitelist)AppCompatEditText et_search_whitelist;
    @BindView(R.id.ib_contact)ImageButton ib_contact;
    @BindView(R.id.rv_whitelist)RecyclerView rv_whitelist;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whitelist);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onBackPressed() {
        closeWhitelist();
    }

    @Override
    public void onAttachView() {
        presenter = new WhitelistPresenter(this);
        presenter.onAttach(this);
        ButterKnife.bind(this);
        initDataWhitelist();
        addActionNavigationClose();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    public void onResume(){
        super.onResume();
        initDataWhitelist();
    }

    @Override
    protected void onDestroy(){
        onDetachView();
        super.onDestroy();
    }


    @OnClick(R.id.ib_contact)
    public void showContactPhone() {
        String[] permissions = {Permissions.Contact.READ_CONTACT};
        if (Utilities.checkPermission(this, permissions)) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }

    @OnClick(R.id.fab_add_whitelist)
    public void addWhitelistProcess(){
        showDialogInputContact(InputType.TYPE_CLASS_TEXT, "INPUT", "Nama", "", new InputCallback() {
            @Override
            public void input(MaterialDialog md, String value) {
                md.dismiss();
                final String nama = value;
                if(!TextUtils.isEmpty(nama)){
                    showDialogInputContact(InputType.TYPE_CLASS_NUMBER, "INPUT", "Nomer Handphone", "", new InputCallback() {
                        @Override
                        public void input(MaterialDialog md, String value) {
                            String phone = value;
                            presenter.addWhitelist(nama, phone);
                            md.dismiss();
                        }
                    });
                }else {
                    showMessage("Nama Kosong");
                }

            };
        });
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(rv_whitelist, message);
    }

    @Override
    public void showDialogContact(ArrayList<String> contacts) {


        Dialog.multiChoice(this, "Daftar Kontak", contacts, new Dialog.CallBack3() {
            @Override
            public void onSelection(MaterialDialog md, CharSequence text) {
                md.dismiss();
                String[] texts = text.toString().split("\n");
                presenter.addWhitelist(texts[0], texts[1]);
            }
        });
    }

    @Override
    public void showDialogInputContact(int inputType, String title, String name_input, String value, final InputCallback callback) {
        Dialog.input(this, inputType,title, name_input, value, new Dialog.CallBack4() {
            @Override
            public void onInput(MaterialDialog md, CharSequence input) {
                callback.input(md, input.toString());
            }
        });
    }

    @Override
    public void initDataWhitelist() {
        ArrayList<WhitelistMA> datas = presenter.getWhitelist();
        if(datas.size()>0){
            rv_whitelist.setVisibility(View.VISIBLE);
            rv_whitelist.getRecycledViewPool().clear();
            rv_whitelist.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getContext());
            rv_whitelist.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            rv_whitelist.setLayoutManager(layoutManager);
            adapter = new WhitelistAdapter(datas, new WhitelistAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(WhitelistMA item) {
                    showDialogDeteleWhitelist(item.getId());
                }
            });
            rv_whitelist.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else {
            rv_whitelist.setVisibility(View.GONE);
            showMessage("Data Whitelist Kosong");
        }

    }

    @Override
    public void showDialogDeteleWhitelist(final int id) {
        Dialog.basic(this, "Whitelist", "Hapus Kontak ?", "Ya", "Tidak", false, new Dialog.CallBack2() {
            @Override
            public void onPositive(MaterialDialog md, DialogAction which) {
                presenter.deleteWhitelist(id);
                md.dismiss();
            }

            @Override
            public void onNegative(MaterialDialog md, DialogAction which) {
                md.dismiss();
            }
        });
    }

    @Override
    public void addActionNavigationClose() {
        toolbar_whitelist.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeWhitelist();
            }
        });
    }


    @Override
    public void closeWhitelist() {
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void searchAction(final List<WhitelistMA> data) {
        et_search_whitelist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    List<WhitelistMA> result = presenter.searchWhitelist(data, editable.toString());
                    adapter = new WhitelistAdapter(result, new WhitelistAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(WhitelistMA item) {
                            showDialogDeteleWhitelist(item.getId());
                        }
                    });
                    rv_whitelist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    adapter = new WhitelistAdapter(data, new WhitelistAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(WhitelistMA item) {
                            showDialogDeteleWhitelist(item.getId());
                        }
                    });
                    rv_whitelist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String phoneNumber = null;
                        if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                            while (phones.moveToNext()) {
                                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                presenter.addWhitelist(name, phoneNumber);
                            }
                            phones.close();
                            c.close();
                        }
                    }
                }
                break;
        }
    }
}
