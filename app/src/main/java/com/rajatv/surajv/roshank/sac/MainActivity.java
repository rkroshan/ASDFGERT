package com.rajatv.surajv.roshank.sac;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.rajatv.surajv.roshank.sac.Results.Publish_Result;
import com.rajatv.surajv.roshank.sac.Signup.EntryPage;
import com.rajatv.surajv.roshank.sac.NewsAndNotice.SAC_Timeline.blogfragment;
import com.rajatv.surajv.roshank.sac.Signup.EntryPage1;
import com.rajatv.surajv.roshank.sac.developers.Developers;
import com.rajatv.surajv.roshank.sac.home.Home;
import com.rajatv.surajv.roshank.sac.maps.MapsActivity;
import com.rajatv.surajv.roshank.sac.menu.AboutNITPactivity;
import com.rajatv.surajv.roshank.sac.NewsAndNotice.CreateUpdate;
import com.rajatv.surajv.roshank.sac.menu.AboutSac;

//import static com.rajatv.surajv.roshank.sac.R.id.fab;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    public static Boolean Homeshown = false;
    private String AdminStatus = StringVariable.STUDENTDATA_ADMIN_STATUS_FALSE;
    private SharedPreferences sharedPreferences;
    int cont;

    ProgressDialog progressDialog;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity---","Started");
        super.onCreate(savedInstanceState);

        //getSupportActionBar().setTitle("");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        sharedPreferences = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);

        cont=0;
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(auth.getCurrentUser()==null){
                    startActivity(new Intent(getApplicationContext(), EntryPage1.class));
                }
            }
        };

        //getting Admin status
         AdminStatus=sharedPreferences.getString(StringVariable.STUDENTDATA_ADMIN_STATUS,StringVariable.STUDENTDATA_ADMIN_STATUS_FALSE);

        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SAC.NITP/"));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Could not find the Link.",Toast.LENGTH_SHORT).show();}
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(AdminStatus.equals(StringVariable.STUDENTDATA_ADMIN_STATUS_FALSE)){
            Log.e("MAINACTIVITY",AdminStatus);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_2);
        }else {
            Log.e("MAINACTIVITY",AdminStatus);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }
        navigationView.setNavigationItemSelectedListener(this);

        Homeshown = true;
        Log.e("HomeShown",Homeshown.toString());
        setFragment(new Home());
    }

    @Override
    protected void onStart() {
        super.onStart();
        cont=0;
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cont=0;
        setFragment(new Home());
        auth.addAuthStateListener(authStateListener);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(auth.getCurrentUser()==null){
                    startActivity(new Intent(getApplicationContext(), EntryPage1.class));
                }
            }
        };
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(Homeshown){
            if(cont==0){
                cont++;
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            else {
                cont=0;
                Log.e("BackPressed MainActity", Homeshown.toString());
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void setupWindowAnimations() {
        Fade fade = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
           logOut();
        }
        else if(id == R.id.feedback)
        {
            try {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "sacnitp1@gmail.com", null));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(this,"Could not find the link.",Toast.LENGTH_SHORT).show();}

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.menu_home){
            setFragment(new Home());
        }
        if (id == R.id.AboutNitp) {
            startActivity(new Intent(MainActivity.this, AboutNITPactivity.class));
        }else if  (id == R.id.AboutSac) {
            startActivity(new Intent(MainActivity.this, AboutSac.class));
        }
        else if (id == R.id.Create_blog) {
            startActivity(new Intent(MainActivity.this, blogfragment.class));
        } else if (id == R.id.create_update) {
            setFragment(new CreateUpdate());

        } else if (id == R.id.nitp_maps) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }else if(id==R.id.dev){
            startActivity(new Intent(MainActivity.this, Developers.class));
        }else if(id==R.id.Publish_Result){
            startActivity(new Intent(MainActivity.this, Publish_Result.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(Fragment fragment){
        if(fragment == new Home()){
            Homeshown = true;
            Log.e("HomeShown",Homeshown.toString());
        }else{
            Homeshown = false;
            Log.e("HomeShown",Homeshown.toString());
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main,fragment)
                .addToBackStack(fragment.toString())
                .commit();
    }

    public void logOut()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure to logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                clearSharedPreference();
//                progressDialog.setCancelable(false);
//                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show(MainActivity.this,"", "Logging you out....");
                thread = new Thread(){
                    @Override
                    public void run() {
                        //super.run();
                        try {
                            thread.sleep(1000);
                            auth.signOut();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void clearSharedPreference()
    {
        this.getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0).edit().clear().commit();
        this.getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTEVENTDATA,0).edit().clear().commit();
        this.getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA_PROFILE_PIC,0).edit().clear().commit();
    }
}
