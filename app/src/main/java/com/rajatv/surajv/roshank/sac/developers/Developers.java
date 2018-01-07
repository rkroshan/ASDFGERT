package com.rajatv.surajv.roshank.sac.developers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;
import java.util.List;

public class Developers extends AppCompatActivity implements View.OnClickListener {


    ImageView dev_back;
    List<Integer> lstImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.developers_main);

        dev_back = (ImageView) findViewById(R.id.dev_back);
        dev_back.setOnClickListener(this);
        initData();

        HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager)findViewById(R.id.horizontal_cycle);
        MyAdapter adapter = new MyAdapter(lstImages,getBaseContext());
        pager.setAdapter(adapter);
    }

    private void initData() {
        lstImages.add(R.mipmap.dev_tile_1);
        lstImages.add(R.mipmap.dev_tile_3);
        lstImages.add(R.mipmap.dev_tile_2);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.dev_back)
        finish();
    }
}
