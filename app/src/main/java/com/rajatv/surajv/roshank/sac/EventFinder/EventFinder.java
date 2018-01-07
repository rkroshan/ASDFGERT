package com.rajatv.surajv.roshank.sac.EventFinder;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.Results.Corona_Melange_Results;
import com.rajatv.surajv.roshank.sac.Results.Dangal_results;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFinder extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout_dashboard;
    private ViewPager viewpager_dashboard;
    private Toolbar toolbar_dashboard;
    private View back_dashboard;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_event_finder);

        tabLayout_dashboard = (TabLayout) findViewById(R.id.tabLayout_ef);
        viewpager_dashboard = (ViewPager) findViewById(R.id.viewpager_ef);
        toolbar_dashboard = (Toolbar) findViewById(R.id.toolbar_ef);

        setSupportActionBar(toolbar_dashboard);
        getSupportActionBar().setTitle("");
        toolbar_dashboard.inflateMenu(R.menu.menu_sas_council);

        tabLayout_dashboard.setupWithViewPager(viewpager_dashboard);
        viewpager_dashboard.setAdapter(new pageAdapter(getSupportFragmentManager()));

        back_dashboard = (View) findViewById(R.id.ef_back);
        back_dashboard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ef_back :
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
                    return new ByName_find();
                case 1:
                    return new ByDate_find();

                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] title = {"By Name","By Date"};

            return title[position];
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sas_council,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.search));
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }
}
