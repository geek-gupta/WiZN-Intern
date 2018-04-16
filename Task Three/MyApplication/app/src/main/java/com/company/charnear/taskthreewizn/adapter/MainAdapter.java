package com.company.charnear.taskthreewizn.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.charnear.taskthreewizn.R;
import com.company.charnear.taskthreewizn.model.ResultModel;

import java.util.ArrayList;

/**
 * Created by gaurav on 14/4/18.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>  {


    Context context;
    ArrayList<ResultModel> mResultModel;
    ArrayList<ResultModel> masterList;

    public MainAdapter(Context context, ArrayList<ResultModel> mResultModel) {
        this.context = context;
        this.mResultModel = mResultModel;
        this.masterList = mResultModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.titleTextView.setText(mResultModel.get(position).getmRepoTitle());
        holder.starCountTextView.setText(mResultModel.get(position).getmStarCount());
        holder.forkCountTextView.setText(mResultModel.get(position).getmForkCount());

        final String urlText = mResultModel.get(position).getHtml_url();

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlText));
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mResultModel.size();
    }



    public void update(ArrayList<ResultModel> imDbModels){
        this.mResultModel=imDbModels;
        this.masterList = imDbModels;
        notifyDataSetChanged();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTextView,starCountTextView,forkCountTextView;
        private LinearLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.repo_title);
            starCountTextView = (TextView) itemView.findViewById(R.id.star_count);
            forkCountTextView = (TextView) itemView.findViewById(R.id.for_count);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
        }
    }

}
