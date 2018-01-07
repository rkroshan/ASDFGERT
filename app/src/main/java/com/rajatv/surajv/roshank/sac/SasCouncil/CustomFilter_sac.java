package com.rajatv.surajv.roshank.sac.SasCouncil;

import android.widget.Filter;


import java.util.ArrayList;

/**
 * Created by CREATOR on 11/10/2017.
 */

public class CustomFilter_sac extends Filter {

    private ArrayList<SacMemberObject> filterList =  new ArrayList<>();
    private SacCouncilAdapter adapter;

    public CustomFilter_sac(ArrayList<SacMemberObject> filterList, SacCouncilAdapter sacCouncilAdapter) {
        this.adapter = sacCouncilAdapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<SacMemberObject> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.mList= (ArrayList<SacMemberObject>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
