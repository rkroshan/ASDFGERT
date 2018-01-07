/*
package com.rajatv.surajv.roshank.sac.CoronaMelange;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

import static com.rajatv.surajv.roshank.sac.R.id.clubs_text_content;

*/
/**
 * Created by CREATOR on 11/3/2017.
 *//*


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final String mclub;
    private final Activity mactivity;
    private ArrayList<String> mlist = new ArrayList<>();

    public EventsAdapter(ArrayList<String> list, String club, Activity acti){
        mlist = list;
        mclub = club;
        mactivity = acti;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_element_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.events_text_content.setText(String.valueOf(mlist.get(position).charAt(0)));
        holder.events_text.setText(mlist.get(position));
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView events_text_content,events_text;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            events_text_content = itemView.findViewById(R.id.events_text_content);
            events_text = itemView.findViewById(R.id.events_text);

        }

        @Override
        public void onClick(View view) {
//            events_text_content.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.BLACK));
//            events_text_content.setBackgroundResource(R.drawable.event_bg_onselect);
            Intent intent = new Intent(itemView.getContext(),EventDetail_new.class);
            intent.putExtra("Eventdata",mlist.get(getAdapterPosition()));
            intent.putExtra("EventClub",mlist.get(getAdapterPosition()));

            View sharedView = events_text_content;
            String transitionName = "eventletter_transition";

            ActivityOptions transitionActivityOptions = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(mactivity, sharedView, transitionName);

                itemView.getContext().startActivity(intent, transitionActivityOptions.toBundle());
            }else {
                itemView.getContext().startActivity(intent);
            }
        }

       */
/* @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    events_text_content.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.BLACK));
                    events_text_content.setBackgroundResource(R.drawable.event_bg_onselect);
                    return true;
                case MotionEvent.ACTION_UP:
                    events_text_content.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.WHITE));
                    events_text_content.setBackgroundResource(R.drawable.events_bg);
                    return true;
                default:
                    return false;
            }
        }*//*

    }
}
*/
