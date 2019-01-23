package com.example.wb.my;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wb.my.Model.dbhelper;
import com.example.wb.my.Model.product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class item_list_adapter extends RecyclerView.Adapter<item_list_adapter.item_list_viewholder>
{
List<product> items;
Context context;
    public item_list_adapter(List<product> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public item_list_adapter.item_list_viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_list_layout,viewGroup,false);
        return new item_list_adapter.item_list_viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final item_list_adapter.item_list_viewholder item_list_viewholder, final int i) {
        Picasso.get().load(items.get(i).product_image).into(item_list_viewholder.p_image);
        item_list_viewholder.p_name.setText(items.get(1).product_name);
        item_list_viewholder.p_price.setText(items.get(2).product_price);
        item_list_viewholder.p_quantity.setText(items.get(1).product_quantity);
        item_list_viewholder.p_weight.setText(items.get(1).product_weight);
        item_list_viewholder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, item_list_viewholder.item_card);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context,"You Clicked : " + String.valueOf(i), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        item_list_viewholder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Integer rows = new dbhelper(context).delete(String.valueOf(items.get(i).id));
                if (rows > 0) {
                    items.remove(item_list_viewholder.getAdapterPosition());
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(item_list_viewholder.getAdapterPosition(),getItemCount());
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context,"Item not Removed From Cart",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();

    }

    class item_list_viewholder extends RecyclerView.ViewHolder{
        ImageView p_image;
        TextView p_name, p_price, p_weight, p_quantity;
        Button remove_btn;
        CardView item_card;
        public item_list_viewholder(@NonNull View itemView) {
            super(itemView);
            p_image = itemView.findViewById(R.id.productImage);
            p_name = itemView.findViewById(R.id.productName);
            p_price = itemView.findViewById(R.id.productPrice);
            p_quantity = itemView.findViewById(R.id.productQuantity);
            p_weight=itemView.findViewById(R.id.productWeight);
            remove_btn = itemView.findViewById(R.id.excludefromcart);
            item_card=itemView.findViewById(R.id.item_card);

        }
    }
}
