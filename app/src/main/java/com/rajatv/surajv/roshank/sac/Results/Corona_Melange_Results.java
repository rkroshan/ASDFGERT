package com.rajatv.surajv.roshank.sac.Results;

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
import com.rajatv.surajv.roshank.sac.StringVariable;

/**
 * Created by CREATOR on 11/14/2017.
 */

public class Corona_Melange_Results extends Fragment {

    RecyclerView updates_main_recyclerview;
    FirebaseRecyclerAdapter<ResultsObject,ResultViewHolder> firebaseRecyclerAdapter;
    DatabaseReference databaseReference;

    public Corona_Melange_Results() {
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
            databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.RESULTS);
            databaseReference.keepSynced(true);
            updates_main_recyclerview = view.findViewById(R.id.updates_main_recyclerview);
            updates_main_recyclerview.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            updates_main_recyclerview.setLayoutManager(linearLayoutManager);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ResultsObject, ResultViewHolder>(ResultsObject.class,R.layout.results_element_view,
                ResultViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(ResultViewHolder viewHolder, ResultsObject model, int position) {
                viewHolder.setResults_event_type(model.getEvent_Type());
                viewHolder.setResults_event_name(model.getSubEvent_Name());
                viewHolder.setResults_event_result(model.getEvent_Result());
            }
        };
        updates_main_recyclerview.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder{
        TextView results_event_type,results_event_name,results_event_result;
        public ResultViewHolder(View itemView) {
            super(itemView);
            results_event_type = itemView.findViewById(R.id.results_event_type);
            results_event_name = itemView.findViewById(R.id.results_event_name);
            results_event_result = itemView.findViewById(R.id.results_event_result);
        }
        public void setResults_event_type(String data){
            results_event_type.setText(data);
        }
        public void setResults_event_name(String data){
            results_event_name.setText(data);
        }
        public void setResults_event_result(String data){
            results_event_result.setText(data);
        }
    }
}
