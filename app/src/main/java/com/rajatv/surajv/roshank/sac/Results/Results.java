package com.rajatv.surajv.roshank.sac.Results;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.NewsAndNotice.SAC_Timeline.SAC_Timeline;
import com.rajatv.surajv.roshank.sac.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Results extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout_dashboard;
    private ViewPager viewpager_dashboard;
    private Toolbar toolbar_dashboard;
    private View back_dashboard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_results);

        tabLayout_dashboard = (TabLayout) findViewById(R.id.tabLayout_results);
        viewpager_dashboard = (ViewPager) findViewById(R.id.viewpager_results);
        toolbar_dashboard = (Toolbar) findViewById(R.id.toolbar_results);

        setSupportActionBar(toolbar_dashboard);
        getSupportActionBar().setTitle("");

        tabLayout_dashboard.setupWithViewPager(viewpager_dashboard);
        viewpager_dashboard.setAdapter(new pageAdapter(getSupportFragmentManager()));

        back_dashboard = (View) findViewById(R.id.results_back);
        back_dashboard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.results_back:
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
                    return new Corona_Melange_Results();
                case 1:
                    return new Dangal_results();

                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] title = {"Corona & Melange","Dangal"};

            return title[position];
        }
    }

}
