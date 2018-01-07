package com.rajatv.surajv.roshank.sac.NewsAndNotice;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

import static android.R.attr.data;

/**
 * Created by CREATOR on 10/28/2017.
 */

public class updatesAdapter extends RecyclerView.Adapter<updatesAdapter.ViewHolder> {

    ArrayList<download_list> mlist = new ArrayList<>();
    download_list downloadList = new download_list();
    String[] data;

    public updatesAdapter(ArrayList<download_list> list){
        mlist=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_files_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        downloadList = mlist.get(position);
        Log.e("url",downloadList.getDownloadlink());
        String[] temp = downloadList.getDownloadlink().split("%2F");
        data = temp[2].split("\\?");
        Log.e("\\?",data[0]);
        holder.upload_filename.setText(data[0]);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView upload_filename;
        public ViewHolder(View itemView) {
            super(itemView);
            upload_filename = (TextView) itemView.findViewById(R.id.upload_file_name);
            upload_filename.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.colorAccent));
            upload_filename.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(itemView.getContext(),"Downloading File",Toast.LENGTH_SHORT).show();
            DownloadManager mManager = (DownloadManager) itemView.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(mlist.get(getAdapterPosition()).getDownloadlink());
            DownloadManager.Request mrequest = new DownloadManager.Request(uri);
            mrequest.setTitle(data[0]);
            mrequest.setDescription("File Downloading...");
            mrequest.allowScanningByMediaScanner();
            mrequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            mrequest.setDestinationInExternalFilesDir(itemView.getContext(), Environment.DIRECTORY_DOWNLOADS,data[0]);
            mManager.enqueue(mrequest);
        }
    }
}
