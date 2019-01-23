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

public class product_adapter extends RecyclerView.Adapter<product_adapter.product_catagory_viewHolder> {
    List<product> products;
    Context context;
    SharedPreferences preferences;
    public product_adapter(List<product> products, Context context) {
        this.products = products;
        this.context = context;
    }



    @Override
    public product_catagory_viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catorgery_list_layout,viewGroup,false);
        return new product_adapter.product_catagory_viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull product_catagory_viewHolder product_catagory_viewHolder, final int i) {
        product_catagory_viewHolder.catorgery_name.setText(products.get(i).product_name);
        Picasso.get().load(products.get(i).product_image).into(product_catagory_viewHolder.catorgery_image);
        product_catagory_viewHolder.catorgery_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getDefaultSharedPreferences(context);
                String Products = new Gson().toJson(products.get(i));
                context.startActivity(new Intent(context,Edit_Product_detail.class).putExtra("Products",Products));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class product_catagory_viewHolder extends RecyclerView.ViewHolder {
        TextView  catorgery_name;
        ImageView catorgery_image;
        CardView  catorgery_card;

     public product_catagory_viewHolder(@NonNull View itemView) {
         super(itemView);
         catorgery_image=itemView.findViewById(R.id.catorgery_image);
         catorgery_name=itemView.findViewById(R.id.catorgery_name);
         catorgery_card=itemView.findViewById(R.id.catorgery_card);
     }
 }
}
