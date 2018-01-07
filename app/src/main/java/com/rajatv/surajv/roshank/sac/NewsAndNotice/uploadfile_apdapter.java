package com.rajatv.surajv.roshank.sac.NewsAndNotice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

/**
 * Created by CREATOR on 10/28/2017.
 */

public class uploadfile_apdapter extends RecyclerView.Adapter<uploadfile_apdapter.uploadViewholder> {

    ArrayList<Uri_List> mlist = new ArrayList<>();
    Uri_List uridata = new Uri_List();

    public uploadfile_apdapter(Context context, ArrayList<Uri_List> list){
        mlist=list;
    }

    @Override
    public uploadViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_files_list,parent,false);
        return new uploadViewholder(v);
    }

    @Override
    public void onBindViewHolder(uploadViewholder holder, int position) {
        uridata = mlist.get(position);
        holder.file_name.setText(uridata.getUriFile_name());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class uploadViewholder extends RecyclerView.ViewHolder {
        TextView file_name;
        public uploadViewholder(View itemView) {
            super(itemView);
            file_name = itemView.findViewById(R.id.upload_file_name);
        }
    }
}
