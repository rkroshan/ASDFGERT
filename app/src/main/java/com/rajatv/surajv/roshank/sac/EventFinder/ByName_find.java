package com.rajatv.surajv.roshank.sac.EventFinder;

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

import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.SasCouncil.SacMemberObject;
import com.rajatv.surajv.roshank.sac.dashboard.RegisterAdapter;
import com.rajatv.surajv.roshank.sac.dashboard.RegisterObject;

import java.util.ArrayList;

/**
 * Created by CREATOR on 11/14/2017.
 */

public class ByName_find extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<RegisterObject> mList = new ArrayList<>();
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private RegisterAdapter registerAdapter;

    public ByName_find() {
        // Required empty public constructor
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view=inflater.inflate(R.layout.fragment_updates, container, false);
            setHasOptionsMenu(true);

            recyclerView = (RecyclerView) view.findViewById(R.id.updates_main_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            setmList();
            registerAdapter = new RegisterAdapter(mList,getActivity());
            recyclerView.setAdapter(registerAdapter);
        }
        return view;
    }

    private void setmList() {

        mList.add(new RegisterObject("C","Cryptic","Concrete"));
        mList.add(new RegisterObject("R", "Real Estate", "Concrete"));
        mList.add(new RegisterObject("B", "Bridge Designing", "Concrete"));
        mList.add(new RegisterObject("E", "Estratto", "Concrete"));
        mList.add(new RegisterObject("N","Novus","Concrete"));
        mList.add(new RegisterObject("A", "Algo-Z-Ripper", "Byteworld"));
        mList.add(new RegisterObject("W", "Web Weaver", "Byteworld"));
        mList.add(new RegisterObject("P", "Panorama", "Byteworld"));
        mList.add(new RegisterObject("H","Hackerzilla", "Byteworld"));
        mList.add(new RegisterObject("A", "Appathon", "Byteworld"));
        mList.add(new RegisterObject("L", "Logo Design", "Byteworld"));
        mList.add(new RegisterObject("P", "Path Seguidor", "Robotics"));
        mList.add(new RegisterObject("D", "Death Race", "Robotics"));
        mList.add(new RegisterObject("R", "Robo Soccer", "Robotics"));
        mList.add(new RegisterObject("L", "Lift Off", "Robotics"));
        mList.add(new RegisterObject("V", "virtual circuitarix", "Ohm"));
        mList.add(new RegisterObject("S", "Smash debug", "Ohm"));
        mList.add(new RegisterObject("E", "Electromaze", "Ohm"));
        mList.add(new RegisterObject("O", "Open hardware", "Ohm"));
        mList.add(new RegisterObject("J", "Jenga", "Aayam"));
        mList.add(new RegisterObject("A", "Ayame (Main Design)", "Aayam"));
        mList.add(new RegisterObject("H", "Hatsumi (Model Making)", "Aayam"));
        mList.add(new RegisterObject("J", "Junihitoe (Apparel Making)", "Aayam"));
        mList.add(new RegisterObject("T", "Tsukimi (Art Expo)", "Aayam"));
        mList.add(new RegisterObject("J","Jinsei (Photography)", "Aayam"));
        mList.add(new RegisterObject("M", "Machinist", "Yantriki"));
        mList.add(new RegisterObject("M", "Mech-a-rush","Yantriki"));
        mList.add(new RegisterObject("T", "Trivia do moto","Yantriki"));
        mList.add(new RegisterObject("I", "Innovative Examplar", "Yantriki"));
        mList.add(new RegisterObject("A", "Andaaz e adakari", "Abhinay"));
        mList.add(new RegisterObject("P", "Poezia", "Ahhinay"));
        mList.add(new RegisterObject("S", "Skit/Play", "Abhinay"));
        mList.add(new RegisterObject("F", "Film-ignito","Abhinay"));
        mList.add(new RegisterObject("C", "Cine Quiz","Abhinay"));
        mList.add(new RegisterObject("G", "Geek-o-mania", "Avlokan"));
        mList.add(new RegisterObject("V", "Virtual placement", "Avlokan"));
        mList.add(new RegisterObject("M", "Mystery solving", "Avlokan"));
        mList.add(new RegisterObject("W", "Word rush", "Avlokan"));
        mList.add(new RegisterObject("C", "Clash-o-mania", "Avlokan"));
        mList.add(new RegisterObject("S", "Solo Dance", "Nrityangana"));
        mList.add(new RegisterObject("D", "Duet / Couple Dance", "Nrityangana"));
        mList.add(new RegisterObject("G", "Group Dance", "Nrityangana"));
        mList.add(new RegisterObject("I", "Impromptu", "Nrityangana"));
        mList.add(new RegisterObject("O", "Origami", "Kalakriti"));
        mList.add(new RegisterObject("F", "Face Canvas","Kalakriti"));
        mList.add(new RegisterObject("E", "Express your mood", "Kalakriti"));
        mList.add(new RegisterObject("B","Build your shape","Kalakriti"));
        mList.add(new RegisterObject("N","No brushes","Kalakriti"));
        mList.add(new RegisterObject("T", "Tooncon", "Kalakriti"));
        mList.add(new RegisterObject("S","Salty feeling", "Kalakriti"));
        mList.add(new RegisterObject("M", "Make your own T", "Kalakriti"));
        mList.add(new RegisterObject("R", "Rang barse", "Kalakriti"));
        mList.add(new RegisterObject("G", "Get inked","Kalakriti"));
        mList.add(new RegisterObject("P","Parliamentary Debate", "Sanhita"));
        mList.add(new RegisterObject("D", "Debate", "Sanhita"));
        mList.add(new RegisterObject("M", "A Minute to Win it","Sanhita"));
        mList.add(new RegisterObject("P", "Pic a Story","Sanhita"));
        mList.add(new RegisterObject("S","Six Word Story", "Sanhita"));
        mList.add(new RegisterObject("O", "Open Mic","Sanhita"));
        mList.add(new RegisterObject("S","Solo Singing","Raaga"));
        mList.add(new RegisterObject("M","Music Mob","Raaga"));
        mList.add(new RegisterObject("A", "Antakshari","Raaga"));
        mList.add(new RegisterObject("B","Brain Stroke(compulsary round)","Pratibimb"));
        mList.add(new RegisterObject("E", "Exhiber(Talent Round)", "Pratibimb"));
        mList.add(new RegisterObject("R","Round-De NIT Patna(Treasure Hunt)","Pratibimb"));
        mList.add(new RegisterObject("P", "Personal Interview", "Pratibimb"));
        mList.add(new RegisterObject("P","Photoshoot","Pratibimb"));
        mList.add(new RegisterObject("G","Grand Finale", "Pratibimb"));
        mList.add(new RegisterObject("I","INFERNO X","Fun Events"));
        mList.add(new RegisterObject("G","GULLY CRICKET","Fun Events"));
        mList.add(new RegisterObject("F","FUTSAL","Fun Events"));
        mList.add(new RegisterObject("P","PAPER DANCE","Fun Events"));
        mList.add(new RegisterObject("V","VIRTUAL JUNKIE","Fun Events"));
        mList.add(new RegisterObject("S","STREETS","Fun Events"));

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
                    registerAdapter.getFilter().filter(query);
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
