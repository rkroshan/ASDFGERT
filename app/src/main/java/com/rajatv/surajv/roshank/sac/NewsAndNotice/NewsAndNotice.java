package com.rajatv.surajv.roshank.sac.NewsAndNotice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.NewsAndNotice.SAC_Timeline.SAC_Timeline;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.dashboard.Favourite;
import com.rajatv.surajv.roshank.sac.dashboard.Profile;
import com.rajatv.surajv.roshank.sac.dashboard.Registered;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsAndNotice extends AppCompatActivity implements View.OnClickListener {


    private TabLayout tabLayout_dashboard;
    private ViewPager viewpager_dashboard;
    private Toolbar toolbar_dashboard;
    private View back_dashboard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_news_and_notice);

        tabLayout_dashboard = (TabLayout) findViewById(R.id.tabLayout_timeline);
        viewpager_dashboard = (ViewPager) findViewById(R.id.viewpager_timeline);
        toolbar_dashboard = (Toolbar) findViewById(R.id.toolbar_timeline);

        setSupportActionBar(toolbar_dashboard);
        getSupportActionBar().setTitle("");

        tabLayout_dashboard.setupWithViewPager(viewpager_dashboard);
        viewpager_dashboard.setAdapter(new pageAdapter(getSupportFragmentManager()));

        back_dashboard = (View) findViewById(R.id.timeline_back);
        back_dashboard.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        Log.e("News Notice ----","finish()");
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.timeline_back:
                finish();
        }
    }

    public class pageAdapter extends FragmentPagerAdapter {

        public pageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new SAC_Timeline();
                case 1:
                    return new Updates();

                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] title = {"SAC Timeline","Notice"};

            return title[position];
        }
    }
}
