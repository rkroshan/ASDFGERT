package com.rajatv.surajv.roshank.sac.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.SasCouncil.DataList;
import com.rajatv.surajv.roshank.sac.SasCouncil.SacCouncilAdapter;
import com.rajatv.surajv.roshank.sac.SasCouncil.SacMemberObject;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by CREATOR on 11/10/2017.
 */

public class Favourite extends Fragment {

    RecyclerView sas_council_Recyclerview;
    private ArrayList<SacMemberObject> mList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    public Favourite() {
        // Required empty public constructor
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view=inflater.inflate(R.layout.frag_sas_council, container, false);
            sas_council_Recyclerview = view.findViewById(R.id.sas_council_Recyclerview);
            sas_council_Recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            sharedPreferences = getActivity().getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA,0);
            setmList(sharedPreferences.getString(StringVariable.STUDENTDATA_FAVOURITE,""));
            sas_council_Recyclerview.setAdapter(new SacCouncilAdapter(mList,getActivity()));
        }
        return view;
    }

    private void setmList(String datalist) {
        if (!datalist.equals("")) {
            String[] data = datalist.split(":");
            int i;
            for (i = 1; i < data.length; i++) {
                try{
                    Log.e("favourite list :x", data[i]);
                    mList.add(DataList.getmList(Integer.valueOf(data[i])));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
    }

}
