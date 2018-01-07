package com.rajatv.surajv.roshank.sac.SasCouncil;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by CREATOR on 11/8/2017.
 */

public class sasCouncil_frag extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<SacMemberObject> mList = new ArrayList<>();
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private SacCouncilAdapter sasCouncilAdapter;
    private String mCouncilName;

    public sasCouncil_frag() {
        // Required empty public constructor
    }

    public sasCouncil_frag(String CouncilName) {
        mCouncilName=CouncilName;
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view=inflater.inflate(R.layout.frag_sas_council, container, false);
            setHasOptionsMenu(true);

            recyclerView = (RecyclerView) view.findViewById(R.id.sas_council_Recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            if(mCouncilName==null) {
                sasCouncilAdapter = new SacCouncilAdapter(DataList.getmList(), getActivity());
            }else {
                if(mCouncilName.equals("Developer")){
                    sasCouncilAdapter = new SacCouncilAdapter(DataList.getDeveloperList(), getActivity());
                }else {
                    sasCouncilAdapter = new SacCouncilAdapter(DataList.getmList(mCouncilName), getActivity());
                }
            }
            recyclerView.setAdapter(sasCouncilAdapter);
        }
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
           /* EditText e = (EditText)searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
            e.setBackgroundColor(Color.WHITE); //←If you just want a color
           // e.setBackground(getResources().getDrawable(R.drawable.YOUR_DRAWABLE));
            //↑ If you want a drawable ↑*/

            View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            v.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.search));
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String query) {
                    sasCouncilAdapter.getFilter().filter(query);
                    return false;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return;
    }

}
