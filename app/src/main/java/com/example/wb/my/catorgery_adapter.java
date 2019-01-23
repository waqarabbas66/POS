package com.example.wb.my;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wb.my.Model.catorgery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class catorgery_adapter extends RecyclerView.Adapter<catorgery_adapter.catorgery_viewholder> {
    List<catorgery> catorgeries;
    Context context;

    public catorgery_adapter(List<catorgery> catorgeries, Context context) {
        this.catorgeries = catorgeries;
        this.context = context;
    }

    @NonNull
    @Override
    public catorgery_viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catorgery_list_layout,viewGroup,false);
        return new catorgery_viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final catorgery_viewholder catorgery_viewholder, int i) {
       catorgery_viewholder.catorgery_name.setText(catorgeries.get(i).name);
        Picasso.get().load(catorgeries.get(i).image).into(catorgery_viewholder.catorgery_image);
       catorgery_viewholder.catorgery_card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                context.startActivity(new Intent(context,Product_display.class).putExtra("Catagory",catorgery_viewholder.catorgery_name.getText().toString()));
           }
       });
    }

    @Override
    public int getItemCount() {
        return catorgeries.size();
    }

    class catorgery_viewholder extends RecyclerView.ViewHolder{
      TextView catorgery_name;
      ImageView catorgery_image;
      CardView catorgery_card;
        public catorgery_viewholder(@NonNull View itemView) {
            super(itemView);
            catorgery_image=itemView.findViewById(R.id.catorgery_image);
            catorgery_name=itemView.findViewById(R.id.catorgery_name);
            catorgery_card=itemView.findViewById(R.id.catorgery_card);
        }
    }
}
