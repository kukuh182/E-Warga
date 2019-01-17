package id.bengkelaplikasi.ewarga.views.menus.home.beranda.pengaduan;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Dialog;
import id.bengkelaplikasi.ewarga.helper.Permissions;
import id.bengkelaplikasi.ewarga.helper.Utilities;
import id.bengkelaplikasi.ewarga.main.App;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Kukuh182 on 10-Oct-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class PengaduanActivity extends AppCompatActivity implements PengaduanView{

    PengaduanPresenter presenter;
    MaterialDialog progressDialog;
    byte[] byte_image = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE_REQUEST = 2;

    @BindView(R.id.toolbar_pengaduan)Toolbar toolbar_pengaduan;
    @BindView(R.id.riv_pengaduan)RoundedImageView riv_pengaduan;
    @BindView(R.id.iv_pengaduan)ImageView iv_pengaduan;
    @BindView(R.id.iv_gallery)ImageView iv_gallery;
    @BindView(R.id.et_judul_pengaduan)AppCompatEditText et_judul_pengaduan;
    @BindView(R.id.et_deskripsi_pengaduan)AppCompatEditText et_deskripsi_pengaduan;
    @BindView(R.id.acb_pengaduan)AppCompatButton acb_pengaduan;
    @BindView(R.id.pb_pengaduan)ProgressBar pb_pengaduan;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaduan);
        onAttachView();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    public void onBackPressed() {
        closePengaduan();
    }


    @Override
    public void onAttachView() {
        presenter = new PengaduanPresenter(this, this);
        presenter.onAttach(this);
        ButterKnife.bind(this);
        addActionNavigationClose();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy(){
        onDetachView();
        super.onDestroy();
    }

    @Override
    public void closePengaduan() {
        finish();
        overridePendingTransition(R.transition.pull_in_left, R.transition.push_out_right);
    }

    @Override
    public void addActionNavigationClose() {
        toolbar_pengaduan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePengaduan();
            }
        });
    }

    @OnClick(R.id.acb_pengaduan)
    public void sendPengaduan() {
        String judul_pengaduan = et_judul_pengaduan.getText().toString();
        String deskripsi_pengaduan = et_deskripsi_pengaduan.getText().toString();
        presenter.sendPengaduanToServer(judul_pengaduan, deskripsi_pengaduan, byte_image);
    }

    @OnClick(R.id.iv_gallery)
    public void galleryImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "data"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void showMessage(String message) {
        Utilities.snackbar(toolbar_pengaduan, message);
    }

    @Override
    public void showProgressDialog(String title, String content) {
        progressDialog = Dialog.progress(this, title, content, false);
    }

    @Override
    public void hideProgressDialog() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @OnClick(R.id.iv_pengaduan)
    public void callCamera(){
        String permission [] = {Permissions.Storage.WRITE_EXTERNAL_STORAGE};
        if(Utilities.checkPermission(this, permission)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                ContentValues values = new ContentValues(1);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                Uri fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                byte_image = Utilities.bitmapToByteArray(bitmap);
                Glide.with(App.getContext()).asBitmap().load(byte_image).into(riv_pengaduan);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            byte_image = Utilities.bitmapToByteArray(bitmap);
            Glide.with(App.getContext()).asBitmap().load(byte_image).into(riv_pengaduan);
        }
    }
}
