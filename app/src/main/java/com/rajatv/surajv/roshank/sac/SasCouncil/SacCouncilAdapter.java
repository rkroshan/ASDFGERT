package com.rajatv.surajv.roshank.sac.SasCouncil;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

/**
 * Created by CREATOR on 11/7/2017.
 */

public class SacCouncilAdapter extends RecyclerView.Adapter<SacCouncilAdapter.ViewHolder> implements Filterable {

    private final Activity mactivity;
    public ArrayList<SacMemberObject> mList =  new ArrayList<>() ;
    private  ArrayList<SacMemberObject> filterList =  new ArrayList<>();
    CustomFilter_sac filter;
    private  SacMemberObject sacmemberObject = new SacMemberObject();
    private Typeface type, type1;

    public  SacCouncilAdapter(ArrayList<SacMemberObject> list, Activity activity){
        mList = list;
        this.filterList = list;
        mactivity = activity;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sas_council_element_view,parent,false);

        type = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/century_gothic.TTF");
        type1 = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/calibrilight.ttf");

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        sacmemberObject = mList.get(position);
        holder.name.setText(sacmemberObject.getName());
        holder.post.setText(sacmemberObject.getPost());
        if(sacmemberObject.getImage_id()!=0)
            holder.person_image_small.setImageResource(sacmemberObject.getImage_id());
        else
            holder.person_image_small.setImageResource(R.mipmap.londa);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new CustomFilter_sac(filterList,this);
        }

        return filter;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView person_image_small,call;
        TextView name,post;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            person_image_small = itemView.findViewById(R.id.person_image_small);
            call = itemView.findViewById(R.id.call);
            name = itemView.findViewById(R.id.name);
            post = itemView.findViewById(R.id.post);
            call.setOnClickListener(this);
            person_image_small.setOnClickListener(this);
            post.setOnClickListener(this);
            name.setOnClickListener(this);

            name.setTypeface(type);
            post.setTypeface(type1);

        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.call){
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+mList.get(getAdapterPosition()).getCalling()));
                itemView.getContext().startActivity(intent);
            }
            else {
                Intent i = getIntent();

                View sharedView = person_image_small;
                String transitionName = "contact_image";

                ActivityOptions transitionActivityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(mactivity, sharedView, transitionName);

                    itemView.getContext().startActivity(i, transitionActivityOptions.toBundle());
                }else {
                    itemView.getContext().startActivity(i);
                }

            }
        }

        private Intent getIntent(){
            Intent intent = new Intent(itemView.getContext(), EachMemberDetails.class);
            Bundle args = new Bundle();
            args.putString("name",mList.get(getAdapterPosition()).getName());
            args.putInt("image_id",mList.get(getAdapterPosition()).getImage_id());
            args.putString("post",mList.get(getAdapterPosition()).getPost());
            args.putString("Branch",mList.get(getAdapterPosition()).getBranch());
            args.putString("Year",mList.get(getAdapterPosition()).getYear());
            args.putString("Calling",mList.get(getAdapterPosition()).getCalling());
            args.putString("WhatsApp",mList.get(getAdapterPosition()).getWhatsapp());
            args.putString("facebook",mList.get(getAdapterPosition()).getFacebook());
            args.putString("Instagram",mList.get(getAdapterPosition()).getInstagram());
            args.putString("Gmail",mList.get(getAdapterPosition()).getGmail());
            args.putString("Quote",mList.get(getAdapterPosition()).getQuote());
            args.putString("index",mList.get(getAdapterPosition()).getIndex());
            Log.e("index",mList.get(getAdapterPosition()).getIndex());
            intent.putExtras(args);
            return intent;
        }
    }
}
