package com.example.mygrocerystore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygrocerystore.R;
import com.example.mygrocerystore.activities.NavCategoryActivity;
import com.example.mygrocerystore.models.NavCategoryModel;


import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHoler> {

    Context context;
    List<NavCategoryModel> list;

    public NavCategoryAdapter(Context context, List<NavCategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NavCategoryAdapter.ViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_cat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.discount.setText(list.get(position).getDiscount());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NavCategoryActivity.class);
                intent.putExtra("type", list.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, description, discount;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cat_nav_img);
            name = itemView.findViewById(R.id.nav_cat_name);
            description = itemView.findViewById(R.id.nav_cat_description);
            discount = itemView.findViewById(R.id.nav_cat_discount);
        }
    }
}
