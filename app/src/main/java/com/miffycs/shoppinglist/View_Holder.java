package com.miffycs.shoppinglist;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


// Adapts the ViewHolder for RecylcerView
public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView name;
    TextView price;
    TextView description;
    ImageView imageView;

    public View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        name = (TextView) itemView.findViewById(R.id.name);
        price = (TextView) itemView.findViewById(R.id.price);
        description = (TextView) itemView.findViewById(R.id.description);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

}
