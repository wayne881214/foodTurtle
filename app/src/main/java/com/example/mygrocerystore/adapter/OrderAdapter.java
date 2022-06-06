package com.example.mygrocerystore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygrocerystore.R;
import com.example.mygrocerystore.models.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    List<OrderModel> orderModelList;
    int totalPrice = 0;
    FirebaseDatabase firebasedatabase;
    FirebaseAuth auth;


    public OrderAdapter(Context context, List<OrderModel> orderModelList) {
        this.context = context;
        this.orderModelList = orderModelList;
        firebasedatabase =FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.count.setText(String.valueOf(orderModelList.get(position).getCount()));
        Glide.with(context).load(orderModelList.get(position).getImg_url()).into(holder.imgUrl);
        holder.name.setText(orderModelList.get(position).getName());
        holder.price.setText(orderModelList.get(position).getPrice()+"$");
        holder.totalPrice.setText(String.valueOf(orderModelList.get(position).getTotalPrice())+"$");

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebasedatabase.getReference().child("Orders").child(com.example.mygrocerystore.activities.OrderDetail.order).child("product").child(orderModelList.get(position).getName()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    orderModelList.remove(orderModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, price,count ,totalPrice;
        ImageView deleteItem;
        ImageView imgUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            count = itemView.findViewById(R.id.count);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            totalPrice = itemView.findViewById(R.id.total_price);
            imgUrl= itemView.findViewById(R.id.img_url);
            deleteItem = itemView.findViewById(R.id.delete);
        }
    }
}
