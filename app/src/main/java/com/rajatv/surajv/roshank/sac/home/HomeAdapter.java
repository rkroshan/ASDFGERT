package com.rajatv.surajv.roshank.sac.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rajatv.surajv.roshank.sac.CoronaMelange.CoronaMelange;
import com.rajatv.surajv.roshank.sac.Dangal.Dangal;
import com.rajatv.surajv.roshank.sac.EventFinder.EventFinder;
import com.rajatv.surajv.roshank.sac.NewsAndNotice.NewsAndNotice;
import com.rajatv.surajv.roshank.sac.NitpClubs.NitpClubs;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.Results.Results;
import com.rajatv.surajv.roshank.sac.SasCouncil.SASCouncilActivity;

import java.util.ArrayList;

/**
 * Created by CREATOR on 11/3/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private final Context mcontext;
    private ArrayList<HomeObject> mlist = new ArrayList<>();
    private HomeObject homeObject = new HomeObject();

    public HomeAdapter(ArrayList<HomeObject> list, Context context){
        mlist = list;
        mcontext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_element_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        homeObject = mlist.get(position);
        Glide.with(mcontext).load(homeObject.getImageResId()).into(holder.home_imageview);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView home_imageview;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            home_imageview = itemView.findViewById(R.id.home_imageview);
        }

        @Override
        public void onClick(View view) {
            switch (getAdapterPosition()){
                case 0:
                    itemView.getContext().startActivity(new Intent(itemView.getContext(),CoronaMelange.class));
                    break;
                case 1:
                    mcontext.startActivity(new Intent(mcontext, Dangal.class));
                    break;
                case 2:
                    mcontext.startActivity(new Intent(mcontext, SASCouncilActivity.class));
                    break;
                case 3:
                    mcontext.startActivity(new Intent(mcontext, NewsAndNotice.class));
                    break;
                case 4:
                    mcontext.startActivity(new Intent(mcontext, Results.class));
                    break;
                case 5:
                    mcontext.startActivity(new Intent(mcontext, EventFinder.class));
                    break;
                case 6:
                    mcontext.startActivity(new Intent(mcontext, NitpClubs.class));
                    break;
            }
        }

        public void setFragment(Fragment fragment){
            FragmentTransaction fragmentTransaction = ((FragmentActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main,fragment)
                    .addToBackStack(fragment.toString())
                    .commit();
        }
    }
}
