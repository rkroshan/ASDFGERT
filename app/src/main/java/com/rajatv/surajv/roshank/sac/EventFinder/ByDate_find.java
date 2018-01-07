package com.rajatv.surajv.roshank.sac.EventFinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;

/**
 * Created by CREATOR on 11/14/2017.
 */

public class ByDate_find extends Fragment {

    LinearLayout updates_frame;

    public ByDate_find() {
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
            updates_frame = view.findViewById(R.id.updates_frame);
            updates_frame.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
