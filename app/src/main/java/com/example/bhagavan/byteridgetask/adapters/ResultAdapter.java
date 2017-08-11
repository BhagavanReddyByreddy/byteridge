package com.example.bhagavan.byteridgetask.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
//import com.squareup.picasso.Picasso;

import com.example.bhagavan.byteridgetask.R;
import com.example.bhagavan.byteridgetask.gsonmodelclasses.Subject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bhagavan on 09-08-2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    List<String> list;
    Context context;

    public  ResultAdapter(Context context, List<String> list){
        this.list = list;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final String subject = list.get(position);
        Picasso.with(context).load(subject).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public  ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
