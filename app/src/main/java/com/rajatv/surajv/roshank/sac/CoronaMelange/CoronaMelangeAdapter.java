package com.rajatv.surajv.roshank.sac.CoronaMelange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.Sponsership.Sponsership;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcfID.tcfID;

import java.util.ArrayList;

/**
 * Created by CREATOR on 11/3/2017.
 */

public class CoronaMelangeAdapter extends RecyclerView.Adapter<CoronaMelangeAdapter.ViewHolder> {

    Typeface type;
    CoronaObject coronaObject = new CoronaObject();
    private  ArrayList<CoronaObject> mlist = new ArrayList<>();

    SharedPreferences sharedPreferences;
    Boolean registered;

    public CoronaMelangeAdapter(ArrayList<CoronaObject> list){
        mlist = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.corona_melange_element_view,parent,false);

        sharedPreferences = parent.getContext().getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);
        registered = sharedPreferences.getBoolean(StringVariable.REGISTERED,false);
        type = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/century_gothic.TTF");
        //type1 = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/calibrilight.ttf");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        coronaObject = mlist.get(position);
        holder.corona_melange_text_content.setText(coronaObject.getName());
        holder.person_image_small.setImageResource(coronaObject.getImageResId());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView corona_melange_text_content;
       // TextView corona_melange_text_content_small;
        ImageView person_image_small;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            corona_melange_text_content = itemView.findViewById(R.id.corona_melange_text_content);
            person_image_small = itemView.findViewById(R.id.person_image_small);
           // corona_melange_text_content_small = itemView.findViewById(R.id.corona_melange_text_content_small);
            corona_melange_text_content.setTypeface(type);
           // corona_melange_text_content_small.setTypeface(type1);

        }

        @Override
        public void onClick(View view) {
            if(getAdapterPosition() == 0){
                if(registered)
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), tcfID.class));
                else
                    Toast.makeText(itemView.getContext(), "Please Register Yourself first", Toast.LENGTH_SHORT).show();
            }
            else if(getAdapterPosition()==mlist.size()-1){
                itemView.getContext().startActivity(new Intent(itemView.getContext(), Sponsership.class));
            }else {
                Intent intent = new Intent(itemView.getContext(), Clubs.class);
                intent.putExtra("field",mlist.get(getAdapterPosition()).getName());
                itemView.getContext().startActivity(intent);
            }
        }
    }
}
