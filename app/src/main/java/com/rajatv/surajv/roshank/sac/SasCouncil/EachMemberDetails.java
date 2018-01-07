package com.rajatv.surajv.roshank.sac.SasCouncil;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

/**
 * A simple {@link Fragment} subclass.
 */
public class EachMemberDetails extends AppCompatActivity implements View.OnClickListener {

    ImageButton each_member_backbutton;
    TextView sac_member_name_textview,sac_member_post_textview,quotation;
    ImageView sac_member_imageview,call_image,whatsApp_image,Gmail_image,Facebook_image,instagram_image,each_member_Favourite;

    String name,post,Branch,Year,Calling,Whatsapp,facebook,Instagram,Gmail,Quote,index,favouriteData="";
    int image_id;
    SharedPreferences sharedPreferences;
    private boolean check=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_each_member_details);

        init();
        getBundleData();
    }

    private void getBundleData() {
        Bundle args = getIntent().getExtras();
        name = args.getString("name","");
        sac_member_name_textview.setText(name);
        Log.e("name",name+"-");

        image_id = args.getInt("image_id",1);
        if(image_id != 0)
            sac_member_imageview.setImageResource(image_id);
        else
            sac_member_imageview.setImageResource(R.mipmap.londa);


        post = args.getString("post","");
        Log.e("post",post+"-");

        Branch = args.getString("Branch","");
        Log.e("Branch",Branch+"-");

        Year = args.getString("Year","");
        if(Year.equals("NA")){
            Year="";
        }else {
            Year+=" year";
        }
        Log.e("Year",Year+"-");

        index = args.getString("index","");
        Log.e("index",index);

        sac_member_post_textview.setText(post+"\n"+Branch+" "+Year);
        Calling = args.getString("Calling","");
        Log.e("Calling",Calling+"-");

        if(Calling.isEmpty()||Calling.equals("NA")){
            call_image.setVisibility(View.GONE);
        }
        Whatsapp = args.getString("WhatsApp","");
        Log.e("WhatsApp",Whatsapp+"-");
        if(Whatsapp.isEmpty()||Whatsapp.equals("NA")){
            whatsApp_image.setVisibility(View.GONE);
        }
        facebook = args.getString("facebook","");
        Log.e("facebook",facebook+"-");
        if(facebook.isEmpty()||facebook.equals("NA")){
            Facebook_image.setVisibility(View.GONE);
        }
        Instagram = args.getString("Instagram","");
        Log.e("Instagram",Instagram+"-");

        if(Instagram.isEmpty()||Instagram.equals("NA")){
            instagram_image.setVisibility(View.GONE);
        }
        Gmail = args.getString("Gmail","");
        Log.e("Gmail",Gmail+"-");

        if(Gmail.isEmpty()||Gmail.equals("NA")){
            Gmail_image.setVisibility(View.GONE);
        }
        Quote = args.getString("Quote","");
        Log.e("Quote",Quote+"-");

        if(Quote.isEmpty()||Quote.equals("NA")){
            quotation.setText("");
        }else {
            quotation.setText("\""+Quote+"\"");
        }

        favouriteData = sharedPreferences.getString(StringVariable.STUDENTDATA_FAVOURITE,"");
            Log.e("favouriteData---",favouriteData);
            if (favouriteData.contains(":"+index+":")) {
                Log.e("favoriteData","yesssss");
                each_member_Favourite.setImageResource(R.mipmap.highstar);
                check = true;
            }

    }

    private void init() {

        sharedPreferences = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);

        each_member_backbutton = (ImageButton) findViewById(R.id.each_member_backbutton);
        each_member_backbutton.setOnClickListener(this);

        sac_member_imageview = (ImageView) findViewById(R.id.sac_member_imageview);
        each_member_Favourite = (ImageView) findViewById(R.id.each_member_Favourite);
        each_member_Favourite.setOnClickListener(this);
        sac_member_imageview.setOnClickListener(this);


            call_image = (ImageView) findViewById(R.id.call_image);
        call_image.setOnClickListener(this);

        whatsApp_image = (ImageView) findViewById(R.id.whatsApp_image);
        whatsApp_image.setOnClickListener(this);

        Gmail_image = (ImageView) findViewById(R.id.Gmail_image);
        Gmail_image.setOnClickListener(this);

        Facebook_image = (ImageView) findViewById(R.id.Facebook_image);
        Facebook_image.setOnClickListener(this);

        instagram_image = (ImageView) findViewById(R.id.instagram_image);
        instagram_image.setOnClickListener(this);

        sac_member_name_textview = (TextView) findViewById(R.id.sac_member_name_textview);
        sac_member_post_textview = (TextView) findViewById(R.id.sac_member_post_textview);
        quotation = (TextView) findViewById(R.id.quotation);

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/century_gothic.TTF");
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/calibrilight.ttf");
        sac_member_name_textview.setTypeface(type);
        sac_member_post_textview.setTypeface(type1);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent intent;
        switch (view.getId()){
            case R.id.each_member_backbutton:
                finish();
                break;
            case R.id.each_member_Favourite:
                if(!check){
                    each_member_Favourite.setImageResource(R.mipmap.highstar);
                    editor.putString(StringVariable.STUDENTDATA_FAVOURITE,favouriteData.concat(":"+index+":"));
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Contact Added into Favourites",Toast.LENGTH_SHORT).show();
                    check=true;
                }else {
                    each_member_Favourite.setImageResource(R.mipmap.star);
                    editor.putString(StringVariable.STUDENTDATA_FAVOURITE,favouriteData.replace(":"+index+":",""));
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Contact Removed From Favourites",Toast.LENGTH_SHORT).show();
                    check=false;
                }
                break;
            case R.id.call_image:
                try {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Calling));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(this,"Could not find the Link.",Toast.LENGTH_SHORT).show();}
                break;
            case R.id.whatsApp_image:
                PackageManager packageManager = this.getPackageManager();
                intent = new Intent(Intent.ACTION_VIEW);
                try{
                    String url = "https://api.whatsapp.com/send?phone=91"+Whatsapp;
                    intent.setPackage("com.whatsapp");
                    intent.setData(Uri.parse(url));
                    if(intent.resolveActivity(packageManager)!=null){
                        startActivity(intent);
                    }
                }catch (Exception e){e.printStackTrace();}
                break;
            case R.id.Gmail_image:
                try {
                    intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", Gmail, null));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(this,"Could not find the Link.",Toast.LENGTH_SHORT).show();}
                break;
            case R.id.Facebook_image:
                try{
                intent = new Intent(Intent.ACTION_VIEW,Uri.parse(facebook));
                startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(this,"Could not find the Link.",Toast.LENGTH_SHORT).show();}
                break;
            case R.id.instagram_image:
                try{
                intent = new Intent(Intent.ACTION_VIEW,Uri.parse(Instagram));
                startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(this,"Could not find the Link.",Toast.LENGTH_SHORT).show();}
                break;
        }
    }
}
