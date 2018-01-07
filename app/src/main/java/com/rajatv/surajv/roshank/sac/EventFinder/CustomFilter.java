package com.rajatv.surajv.roshank.sac.EventFinder;

import android.widget.Filter;

import com.rajatv.surajv.roshank.sac.SasCouncil.SacMemberObject;
import com.rajatv.surajv.roshank.sac.dashboard.RegisterAdapter;
import com.rajatv.surajv.roshank.sac.dashboard.RegisterObject;

import java.util.ArrayList;

/**
 * Created by Hp on 3/17/2016.
 */
public class CustomFilter extends Filter{

    RegisterAdapter adapter;
    ArrayList<RegisterObject> filterList;


    public CustomFilter(ArrayList<RegisterObject> filterList, RegisterAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }


    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<RegisterObject> filteredObject=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if((filterList.get(i).getEventname().toUpperCase().contains(constraint))||(filterList.get(i).getClubname().toUpperCase().contains(constraint)))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredObject.add(filterList.get(i));
                }
            }

            results.count=filteredObject.size();
            results.values=filteredObject;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

       adapter.mlist= (ArrayList<RegisterObject>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
