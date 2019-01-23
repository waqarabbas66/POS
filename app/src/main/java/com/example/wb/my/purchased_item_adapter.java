package com.example.wb.my;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wb.my.Model.product;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class purchased_item_adapter extends RecyclerView.Adapter<purchased_item_adapter.product_catagory_viewHolder> {
    List<product> products;
    Context context;
    SharedPreferences preferences;
    public purchased_item_adapter(List<product> products, Context context) {
        this.products = products;
        this.context = context;
    }



    @Override
    public product_catagory_viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchased_item_layout,viewGroup,false);
        return new product_catagory_viewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull product_catagory_viewHolder product_catagory_viewHolder, final int i) {
         product_catagory_viewHolder.catorgery_name.setText(products.get(i).product_name);
         product_catagory_viewHolder.catorgery_price.setText("Rs "+String.valueOf(products.get(i).product_price));
        product_catagory_viewHolder.catorgery_quantity.setText("x"+String.valueOf(products.get(i).product_quantity));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class product_catagory_viewHolder extends RecyclerView.ViewHolder {
        TextView catorgery_name;
        TextView catorgery_price;
        TextView catorgery_quantity;
        public product_catagory_viewHolder(@NonNull View itemView) {
            super(itemView);
            catorgery_price=itemView.findViewById(R.id.checkout_item_price);
            catorgery_name=itemView.findViewById(R.id.checkout_item_name);
            catorgery_quantity=itemView.findViewById(R.id.checkout_item_quantity);
        }
    }
}
