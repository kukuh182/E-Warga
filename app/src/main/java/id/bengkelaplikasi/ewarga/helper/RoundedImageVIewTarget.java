package id.bengkelaplikasi.ewarga.helper;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;

/**
 * Created by Kukuh182 on 17-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class RoundedImageVIewTarget extends ImageViewTarget<Bitmap> {
    public RoundedImageVIewTarget(ImageView view) {
        super(view);
    }

    @Override
    protected void setResource(@Nullable Bitmap resource) {
        RoundedBitmapDrawable rounded =
                RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
        rounded.setCircular(true);
        view.setImageDrawable(rounded);
    }
}