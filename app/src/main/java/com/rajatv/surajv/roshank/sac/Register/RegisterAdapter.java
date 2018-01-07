package com.rajatv.surajv.roshank.sac.Register;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.Signup.SignUp2;

import java.util.ArrayList;

/**
 * Created by CREATOR on 11/4/2017.
 */

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> {

    private  ArrayList<String> mlist = new ArrayList<>();

    public RegisterAdapter(ArrayList<String> list){
        mlist = list;
    }

    @Override
    public RegisterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_element_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RegisterAdapter.ViewHolder holder, int position) {
        holder.register_check_textview.setText(mlist.get(position));
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        String eventData;
        TextView register_check_textview;
        ImageView register_check_imageview,register_check_imageview2;
        ProgressBar register_progressbar;
        SharedPreferences pref;
        DatabaseReference databaseReference;
        public ViewHolder(View itemView) {
            super(itemView);
            register_check_textview = itemView.findViewById(R.id.register_check_textview);
            register_check_imageview = itemView.findViewById(R.id.register_check_imageview);
            register_progressbar = itemView.findViewById(R.id.register_progressbar);
            register_check_imageview2 = itemView.findViewById(R.id.register_check_imageview2);
           register_check_imageview.setOnClickListener(this);
            register_check_imageview2.setOnClickListener(this);
            pref = itemView.getContext().getSharedPreferences("StudentData", Context.MODE_PRIVATE);
           // setImage();
        }

        private void setImage() {
         if(eventData.isEmpty()||eventData.contains(mlist.get(getAdapterPosition()))){
             register_check_imageview.setVisibility(View.INVISIBLE);
             register_check_imageview2.setVisibility(View.VISIBLE);
         }
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.register_check_imageview:
                    alertbox(1);
                    break;
                case R.id.register_check_imageview2:
                    alertbox(0);
                    break;
            }
        }

        private void alertbox(int i) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("College").child(pref.getString("College",""))
                    .child(pref.getString("Rollno","")).child(mlist.get(getAdapterPosition()));
            String message;
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(itemView.getContext(),R.style.myDialog));
            builder.setTitle("Registration");
            if(i==1){
                message = "Are you sure, you wanna Register";
                builder.setMessage(message);
                builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        register_check_imageview.setVisibility(View.INVISIBLE);
                        register_progressbar.setVisibility(View.VISIBLE);
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("College").child(pref.getString("College",""))
                                .child(pref.getString("Rollno","")).child("Events");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                eventData = dataSnapshot.getValue().toString();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        databaseReference.setValue(eventData+":sac:"+mlist.get(getAdapterPosition())).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    register_progressbar.setVisibility(View.INVISIBLE);
                                    register_check_imageview2.setVisibility(View.VISIBLE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("Events",eventData);
                                    editor.apply();
                                    Toast.makeText(itemView.getContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                                }else {
                                    register_progressbar.setVisibility(View.INVISIBLE);
                                    register_check_imageview.setVisibility(View.VISIBLE);
                                    Toast.makeText(itemView.getContext(),"Sorry Try Again!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }else{
                message = "Are you sure, you wanna Unregister";
                builder.setMessage(message);
                builder.setPositiveButton("Unregister", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        register_check_imageview2.setVisibility(View.INVISIBLE);
                        register_progressbar.setVisibility(View.VISIBLE);
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("College").child(pref.getString("College",""))
                                .child(pref.getString("Rollno","")).child("Events");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                eventData = dataSnapshot.getValue().toString();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        String[] eventarray = eventData.split(":sac:");
                        String neweventarray = ":sac:";
                        for(String s:eventarray){
                            if(!s.equals(mlist.get(getAdapterPosition()))){
                                neweventarray += s;
                            }
                        }


                        final String finalNeweventarray = neweventarray;
                        databaseReference.setValue(neweventarray).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    register_progressbar.setVisibility(View.INVISIBLE);
                                    register_check_imageview.setVisibility(View.VISIBLE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("Events", finalNeweventarray);
                                    editor.apply();
                                    Toast.makeText(itemView.getContext(),"Unregistered Successfully",Toast.LENGTH_SHORT).show();
                                }else {
                                    register_progressbar.setVisibility(View.INVISIBLE);
                                    register_check_imageview2.setVisibility(View.VISIBLE);
                                    Toast.makeText(itemView.getContext(),"Sorry Try Again!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }
        }
    }
}
