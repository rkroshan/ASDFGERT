package com.rajatv.surajv.roshank.sac.NewsAndNotice.SAC_Timeline;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SAC_Timeline extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<SAC_Timeline_content, blogviewholder> firebaseRecyclerAdapter;
    private DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;

    public SAC_Timeline() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.Homeshown = false;
        if (view == null) {
            Log.e("Sac Timeline","Created");
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.sac_timeline, container, false);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Timeline");
            databaseReference.keepSynced(true);
            recyclerView = (RecyclerView) view.findViewById(R.id.timeline_recyclerview);
            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);

        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Sac Timeline","OnStart");
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SAC_Timeline_content, blogviewholder>(SAC_Timeline_content.class
        ,R.layout.blog_cardview,blogviewholder.class,databaseReference) {
            @Override
            protected void populateViewHolder(blogviewholder viewHolder, SAC_Timeline_content model, int position) {
                try{
                viewHolder.setPost_image(model.getImageUri());
                Log.e("Post Image Uri",model.getImageUri());
                Log.e("Post Title",model.getPostTitle());
                viewHolder.setPost_title(model.getPostTitle());
                viewHolder.setPost_description(model.getPostDescription());
                    viewHolder.setAdmin(model.getAdminName() + " (" + model.getStudentPost() + ") ");
                viewHolder.setPostTime(model.getPostTime());
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class blogviewholder extends RecyclerView.ViewHolder{
        View mview;
        String ImageUri;
        TextView post_title,post_description,set_postwriter,set_dateofcreation;
        ImageView post_image;
        public blogviewholder(final View itemView) {
            super(itemView);
            mview=itemView;
            post_title = (TextView)itemView.findViewById(R.id.set_posttitle);
            post_description = (TextView) itemView.findViewById(R.id.set_postDescription);
            set_postwriter = (TextView) itemView.findViewById(R.id.set_postwriter);
            set_dateofcreation = (TextView) itemView.findViewById(R.id.set_dateofcreation);
            post_image = (ImageView)itemView.findViewById(R.id.set_postimage);
            post_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),FullImage.class);
                    intent.putExtra("Image_URI",ImageUri);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void setPost_title(String title){
            post_title.setText(title);
        }
        public void setPost_description(String Description){
            post_description.setText(Description);
        }
        public void setAdmin(String AdminData){
            set_postwriter.setText("Posted By : "+AdminData);
        }
        public void setPostTime(String TimeData){
            set_dateofcreation.setText(TimeData);
        }
        public void setPost_image(final String uri){
            ImageUri = uri;
            Glide.with(itemView.getContext()).load(uri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(post_image);
        }
    }
}
