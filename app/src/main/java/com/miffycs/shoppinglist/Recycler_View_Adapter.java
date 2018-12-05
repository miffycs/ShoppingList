package com.miffycs.shoppinglist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class Recycler_View_Adapter extends RecyclerView.Adapter<View_Holder>{

    List<Item> list = Collections.emptyList();
    Context context;

    public Recycler_View_Adapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        holder.name.setText(list.get(position).name);
        DecimalFormat df = new DecimalFormat("$#.00");
        holder.price.setText(df.format(list.get(position).price));
        holder.description.setText(list.get(position).description);
        holder.imageView.setImageResource(list.get(position).imageID);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView
    public void insert(int position, Item item) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing the Data object
    public void remove(Item item) {
        int position = list.indexOf(item);
        list.remove(position);
        notifyItemRemoved(position);
    }

    // Remove a RecyclerView item containing the Data object
    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
}
