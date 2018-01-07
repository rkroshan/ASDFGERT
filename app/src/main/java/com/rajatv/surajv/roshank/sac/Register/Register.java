package com.rajatv.surajv.roshank.sac.Register;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> mlist = new ArrayList<>();
    RegisterAdapter registeradapter;

    public Register() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view=inflater.inflate(R.layout.fragment_register, container, false);

            recyclerView = (RecyclerView) view.findViewById(R.id.register_recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            setList();
            registeradapter = new RegisterAdapter(mlist);
            recyclerView.setAdapter(registeradapter);
        }
        return view;
    }

    private void setList() {
        mlist.add("Byte World");
        mlist.add("Ayam");
        mlist.add("OHM");
        mlist.add("Concrete");
        mlist.add("Robotics");
    }

}
