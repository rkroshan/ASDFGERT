package com.rajatv.surajv.roshank.sac.Splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.Signup.EntryPage;
import com.rajatv.surajv.roshank.sac.Signup.EntryPage1;

public class Splash2 extends AppCompatActivity {
  //private ProgressBar progressBar;
    ImageView splash_icon;
    Thread thread;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spalsh2);
        splash_icon = (ImageView) findViewById(R.id.splash_icon);
        splashAnimation();

       /* progressBar = (ProgressBar)findViewById(R.id.splash2_progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);*/

        thread = new Thread() {
            @Override
            public void run() {
                //super.run();
                try {
                    thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(Splash2.this, EntryPage1.class));
                finish();
            }
        };
        thread.start();
    }
    public void splashAnimation()
    {
        final Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        splash_icon.startAnimation(animation);
    }
}