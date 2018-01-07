package com.rajatv.surajv.roshank.sac.dashboard;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.SasCouncil.sasCouncil_frag;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * Created by CREATOR on 11/10/2017.
 */


public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout_dashboard;
    private ViewPager viewpager_dashboard;
    private Toolbar toolbar_dashboard;
    private ImageView back_dashboard;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dashboard);
        setupWindowAnimations();

        tabLayout_dashboard = (TabLayout) findViewById(R.id.tabLayout_dashboard);
        viewpager_dashboard = (ViewPager) findViewById(R.id.viewpager_dashboard);
        toolbar_dashboard = (Toolbar) findViewById(R.id.toolbar_dashboard);

        setSupportActionBar(toolbar_dashboard);
        getSupportActionBar().setTitle("");

        tabLayout_dashboard.setupWithViewPager(viewpager_dashboard);
        viewpager_dashboard.setAdapter(new pageAdapter(getSupportFragmentManager()));

        back_dashboard = (ImageView) findViewById(R.id.dashboard_back);
        back_dashboard.setOnClickListener(this);


    }

    private void setupWindowAnimations() {
        Slide slide = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            slide.setSlideEdge(Gravity.BOTTOM);
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);
            getWindow().setExitTransition(slide);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dashboard_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public class pageAdapter extends FragmentPagerAdapter {

        public pageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Profile();
                case 1:
                    return new Favourite();
                case 2:
                    return new Registered();
                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] title = {"PROFILE","FAVOURITES","REGISTERED"};

            return title[position];
        }

    }
}
