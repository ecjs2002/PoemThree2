package com.fireegg1991.poemthree;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alfo6-17 on 2017-08-07.
 */

public class RecyclerAdapter extends RecyclerView.Adapter {

    ArrayList<Item> items;
    Context context;

    public RecyclerAdapter(ArrayList<Item> items, Context context) {

        this.items = items;
        this.context = context;



    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if(position==0) viewType=10;
        else viewType=0;

        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i("asd",""+viewType);

//        if(viewType==10){
//            View header= LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false);
//            HeaderVH headerVH= new HeaderVH(header);
//            return headerVH;

        //else{
            View itemView= LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
            ViewHolder holder= new ViewHolder(itemView);

            return holder;



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        if(position==0){
//            return;
//        }

        ((ViewHolder)holder).num.setText( ""+items.get(position).num);
        ((ViewHolder)holder).title.setText( items.get(position).title);
        ((ViewHolder)holder).userName.setText( items.get(position).userName);
        ((ViewHolder)holder).date.setText( items.get(position).date);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class HeaderVH extends RecyclerView.ViewHolder{

        public HeaderVH(View itemView) {
            super(itemView);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView num;
        TextView title;
        TextView userName;
        TextView date;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;

            num= (TextView) itemView.findViewById(R.id.tv_num);
            title= (TextView) itemView.findViewById(R.id.tv_title);
            userName= (TextView) itemView.findViewById(R.id.tv_userName);
            date= (TextView) itemView.findViewById(R.id.tv_date);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.i("asda",items.get(getLayoutPosition()-1).num+"");
                    ((BoardActivity)context).textAct(items.get(getLayoutPosition()));
                }

            });
        }

    }

}




























