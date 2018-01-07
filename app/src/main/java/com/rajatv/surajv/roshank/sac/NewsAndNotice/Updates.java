package com.rajatv.surajv.roshank.sac.NewsAndNotice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Updates extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<update, updateViewholder> firebaseRecyclerAdapter;
   // private FirebaseRecyclerAdapter<SAC_Timeline_content,blogviewholder> firebaseRecyclerAdapter;
    private DatabaseReference databaseReference;


    public Updates() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.Homeshown = false;
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_updates, container, false);

            Log.e("Updates Fragment----","created");
            databaseReference = FirebaseDatabase.getInstance().getReference().child("News");
            databaseReference.keepSynced(true);
            Log.e("databasereference----",databaseReference.toString());
            recyclerView = (RecyclerView) view.findViewById(R.id.updates_main_recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);

        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("updates Fragment----","started");
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<update, updateViewholder>(update.class,
                R.layout.updates_cardview,updateViewholder.class,databaseReference) {
            @Override
            protected void populateViewHolder(updateViewholder viewHolder, update model, int position) {
                viewHolder.setHeading(model.getHeading());
                Log.e("Model Heading---",model.getHeading());
                viewHolder.setNews(model.getNews());
                Log.e("Model News---",model.getNews());
                viewHolder.setCyclerView(model.getImages());
              //  Log.e("Model Cyclerview---",model.getImages());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class updateViewholder extends RecyclerView.ViewHolder {
        TextView heading,news;
        RecyclerView cyclerView;
        updatesAdapter updatesAdapter;
        ArrayList<download_list> mlist = new ArrayList<>();
        download_list downloadList = new download_list();
        public updateViewholder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.updates_heading);
            news = (TextView) itemView.findViewById(R.id.updates_news);
            cyclerView = (RecyclerView) itemView.findViewById(R.id.updates_recyclerview);
            cyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            cyclerView.setHasFixedSize(true);
        }

        public void setHeading(String title){
            heading.setText(title);
        }
        public void setNews(String body){
            news.setText(body);
        }
        public void setCyclerView(HashMap<String,String> uris){
            if(uris!=null) {
                for (String value : uris.values()) {
                    downloadList.setDownloadlink(value);
                    mlist.add(downloadList);
                }
            }
            updatesAdapter = new updatesAdapter(mlist);
            cyclerView.setAdapter(updatesAdapter);

        }
    }



}
