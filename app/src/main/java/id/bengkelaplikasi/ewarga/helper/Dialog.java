package id.bengkelaplikasi.ewarga.helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import id.bengkelaplikasi.ewarga.R;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class Dialog {

    /**
     * Prepare
     * libraries compile 'com.afollestad.material-dialogs:core:0.9.4.7'
     * libraries compile 'com.afollestad.material-dialogs:commons:0.9.4.7'
     * action onPositive, onNeutral, onNegative, onAny
     */

    public interface CallBack1 {
        void onPositive(MaterialDialog md, DialogAction which);
    }

    public interface CallBack2{
        void onPositive(MaterialDialog md, DialogAction which);
        void onNegative(MaterialDialog md, DialogAction which);
    }

    public interface CallBack3{
        void onSelection(MaterialDialog md, CharSequence text);
    }

    public interface CallBack4{
        void onInput(MaterialDialog md, CharSequence input);
    }

    public interface CallBack5{
        void onPositive(MaterialDialog md, DialogAction which);
        void onNegative(MaterialDialog md, DialogAction which);
        void onSelection(MaterialDialog md, CharSequence text);
    }

    public static void basic(Activity activity, String title, String content,
                             String positive, String negative,
                             Boolean cancelable, final CallBack2 callBack){
        new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(positive)
                .negativeText(negative)
                .cancelable(cancelable)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callBack.onPositive(dialog, which);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callBack.onNegative(dialog, which);
                    }
                })
                .show();
    }

    public static void basic(Activity activity, String title, String content,
                             String positive, Boolean cancelable, final CallBack1 callBack){
        new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(positive)
                .cancelable(cancelable)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        callBack.onPositive(dialog, which);
                    }
                })
                .show();
    }

    public static MaterialDialog progress(Activity activity, String title, String content, Boolean cancelable){
        return new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .progress(true, 0)
                .cancelable(cancelable)
                .show();
    }

    public static MaterialDialog multiChoice(Activity activity, String title, ArrayList<String> items, final CallBack3 callBack){

        return new MaterialDialog.Builder(activity)
                .title(title)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        callBack.onSelection(dialog, text);
                    }
                })
                .show();
    }

    public static MaterialDialog input(Activity activity, int input_type, String title, String content, String value, final CallBack4 callBack){
       return new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .inputType(input_type)
                .input(content, value, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        callBack.onInput(dialog, input);
                    }
                }).show();
    }
}
