package com.rajatv.surajv.roshank.sac.dashboard;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.CoronaMelange.EventDetail_new;
import com.rajatv.surajv.roshank.sac.CoronaMelange.EventDetails;
import com.rajatv.surajv.roshank.sac.EventFinder.CustomFilter;
import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

import static android.R.attr.filter;

/**
 * Created by CREATOR on 11/10/2017.
 */

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> implements Filterable {

    Activity mactivity;
    public ArrayList<RegisterObject> mlist;
    ArrayList<RegisterObject> filterList;
    RegisterObject rejobject = new RegisterObject();
    CustomFilter filter;

    Typeface type, type1;

    public RegisterAdapter(ArrayList<RegisterObject> list, Activity acti){
        mactivity=acti;
        this.mlist = list;
        this.filterList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_registered_events,parent,false);

        type = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/century_gothic.TTF");
        type1 = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/calibrilight.ttf");

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        rejobject = mlist.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.registered_event_imageview.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mactivity.getApplicationContext(),getcolor(position))));
        }
        holder.registered_event_imageview.setText(rejobject.getButtonkey());
        holder.registered_event_name.setText(rejobject.getEventname());
        holder.registered_event_Post.setText(rejobject.getClubname());
    }

    private int getcolor(int position) {
        int mposition=0;
        int color=0;
        mposition=position%6;
        switch (mposition){
            case 0:
                color = R.color.color1;
                break;
            case 1:
                color = R.color.color2;
                break;
            case 2:
                color = R.color.color3;
                break;
            case 3:
                color = R.color.color4;
                break;
            case 4:
                color = R.color.color5;
                break;
            case 5:
                color = R.color.color6;
                break;
            case 6:
                color = R.color.color7;
                break;
        }
        return color;
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button registered_event_imageview;
        ImageView registerevents_image_info;
        TextView registered_event_name,registered_event_Post;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            registered_event_imageview = itemView.findViewById(R.id.registered_event_imageview);
            registered_event_imageview.setOnClickListener(this);
            registerevents_image_info = itemView.findViewById(R.id.registerevents_image_info);
            registered_event_name = itemView.findViewById(R.id.registered_event_name);
            registered_event_Post = itemView.findViewById(R.id.registered_event_Post);

            registered_event_name.setTypeface(type);
            registered_event_Post.setTypeface(type1);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(itemView.getContext(),EventDetail_new.class);
            intent.putExtra("Eventdata",mlist.get(getAdapterPosition()).getEventname());
            intent.putExtra("EventClub",mlist.get(getAdapterPosition()).getClubname());

            View sharedView = registered_event_imageview;
            String transitionName = "eventletter_transition";

            ActivityOptions transitionActivityOptions = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(mactivity, sharedView, transitionName);

                itemView.getContext().startActivity(intent, transitionActivityOptions.toBundle());
            }else {
                itemView.getContext().startActivity(intent);
            }
        }
    }

}



