package br.com.udacity.famousmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageUtils {

    public static void renderImage(Context context, Uri imageUri, ImageView imageView) {
        Picasso.with(context)
                .load(imageUri)
                .into(imageView);
    }
}
