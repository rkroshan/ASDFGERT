package com.rajatv.surajv.roshank.sac.NewsAndNotice.SAC_Timeline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rajatv.surajv.roshank.sac.R;

/**
 * Created by CREATOR on 12/16/2017.
 */

public class FullImage extends AppCompatActivity {
    ImageView full_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.full_image);
        full_image = (ImageView) findViewById(R.id.full_image);

        String uri = getIntent().getStringExtra("Image_URI");
        Glide.with(this).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).into(full_image);
    }
}
