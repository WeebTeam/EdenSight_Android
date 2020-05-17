package com.example.edensight;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadImage(Context c, String url, ImageView img){
        if (url != null && !url.isEmpty()){
            Picasso.with(c)
                    .load(url)
                    .placeholder(R.drawable.person_placeholder)
                    .error(R.drawable.error)
                    .fit()
                    .noFade()
                    .into(img);
        } else {
            img.setImageResource(R.drawable.error);
        }
    }
}
