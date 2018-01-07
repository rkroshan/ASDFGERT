package com.rajatv.surajv.roshank.sac.CoronaMelange;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

/**
 * Created by CREATOR on 11/3/2017.
 */

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder> {

    private final Activity mactivity;
    private ArrayList<String> mlist = new ArrayList<>();
    Typeface type, type1;
    int colorID_brown;
    Drawable id_background;

    public ClubsAdapter(ArrayList<String> list, Activity acti){
        mlist = list;
        mactivity=acti;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clubs_content_veiw,parent,false);

        type = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/mayeka_bold_eventfirstletter.otf");
        type1 = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/mayeka_regular_event_name.otf");

        colorID_brown = parent.getContext().getResources().getColor(R.color.brown);
        id_background = parent.getContext().getResources().getDrawable(R.drawable.events_bg_cultural);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s = mlist.get(position);
        holder.clubs_text_content.setText(String.valueOf(s.charAt(0)));
        holder.clubs_text.setText(s);
        if(s=="PRATIBIMB" || s=="RAAGA"||s=="SANHITA"||s=="AVLOKAN"||s=="NRITYANGANA"||s=="KALAKRITI"||s=="ABHINAY")
        {
            holder.clubs_text_content.setTextColor(colorID_brown);
            holder.clubs_text_content.setBackground(id_background);
            holder.clubs_text.setTextColor(colorID_brown);
        }
        Log.e("mlist.get(position)",String.valueOf(mlist.get(position).charAt(0)));
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView clubs_text_content,clubs_text;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            clubs_text_content = itemView.findViewById(R.id.clubs_text_content);
            clubs_text = itemView.findViewById(R.id.clubs_text);

            clubs_text_content.setTypeface(type);
            clubs_text.setTypeface(type1);

        }

        @Override
        public void onClick(View view) {
            Intent intent;
            String val = mlist.get(getAdapterPosition());
            if(val.equals("INFERNO X")||val.equals("GULLY CRICKET")||val.equals("FUTSAL")||val.equals("PAPER DANCE")||val.equals("VIRTUAL JUNKIE")||val.equals("STREETS")){
                intent = new Intent(itemView.getContext(),EventDetail_new.class);
            }else {
                intent = new Intent(itemView.getContext(), Events.class);
            }
            intent.putExtra("Eventdata",mlist.get(getAdapterPosition()));
            View sharedView = clubs_text_content;
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
