package com.rajatv.surajv.roshank.sac.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.rajatv.surajv.roshank.sac.R;

public class Splash1 extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private Button getstarted;
    private  int[] layouts;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Splash1---","Started");
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash1);

        init();
    }

    public void init()
    {

        getstarted = (Button)findViewById(R.id.splash1_btn_getstarted);
        dotsLayout = (LinearLayout)findViewById(R.id.splash1_layoutDots);
        viewPager = (ViewPager)findViewById(R.id.splash1_viewpager);
        getstarted.setVisibility(View.GONE);
        //next_slide.setVisibility(View.GONE);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/widolte-demo.regular-demo.otf");
        getstarted.setTypeface(type);

        sharedPreferences = getSharedPreferences("checkFirstRun", MODE_PRIVATE);
        int first = sharedPreferences.getInt("first", 0);

        if(first != 0)
        {
            Intent intent = new Intent(Splash1.this, Splash2.class);
            startActivity(intent);
            finish();
        }

        layouts = new int[]{R.layout.slide1, R.layout.slide2, R.layout.slide3,
                R.layout.slide4, R.layout.slide5, R.layout.slide6, R.layout.slide7, R.layout.slide8,R.layout.slide9};

        addBottomDots(0);

        viewPageAdapter = new ViewPageAdapter();
        viewPager.setAdapter(viewPageAdapter);
        viewPager.addOnPageChangeListener(viewListener);



      /*  next_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int current = getItem(+1);
                if(current < layouts.length)
                {
                    viewPager.setCurrentItem(current);
                }
            }
        });*/
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("first", 1);
                editor.commit();
                Intent intent = new Intent(Splash1.this, Splash2.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private int getItem(int i)
    {
        return viewPager.getCurrentItem()+i;
    }

    private void addBottomDots(int position)
    {
        dots = new TextView[layouts.length];
        int colorActive = getResources().getColor(R.color.dot_dark_active);
        int colorInactive = getResources().getColor(R.color.dot_dark_inactive);

        dotsLayout.removeAllViews();
        for(int i=0; i<dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive);
            dotsLayout.addView(dots[i]);
        }
        if(dots.length>0)
            dots[position].setTextColor(colorActive);
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener()
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if(position==layouts.length-1)
            {
                // next_slide.setVisibility(View.GONE);
                getstarted.setVisibility(View.VISIBLE);
            }
            else
            {
                getstarted.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class ViewPageAdapter extends PagerAdapter
    {
        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }

}
