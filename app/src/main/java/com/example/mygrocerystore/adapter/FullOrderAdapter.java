package com.example.mygrocerystore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.StoreOrderActivity;
import com.example.mygrocerystore.activities.OrderDetail;
import com.example.mygrocerystore.activities.StoreFullOrderActivity;
import com.example.mygrocerystore.activities.shopcarActivity;
import com.example.mygrocerystore.models.FullOrderModel;
import com.example.mygrocerystore.models.MyCartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FullOrderAdapter extends RecyclerView.Adapter<FullOrderAdapter.ViewHolder> {

    Context context;
    List<FullOrderModel> fullOrderModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String typeText="未讀取";
    String typeOrder="未讀取";

    public FullOrderAdapter(Context context, List<FullOrderModel> fullOrderModelList) {
        this.context = context;
        this.fullOrderModelList = fullOrderModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FullOrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.store_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.store.setText(fullOrderModelList.get(position).getStore());
        holder.customer.setText(fullOrderModelList.get(position).getCustomer());
        holder.delivery.setText(fullOrderModelList.get(position).getDelivery());
        switch(fullOrderModelList.get(position).getType())
        {
            case 0:
                typeText="購物車";
                break;
            case 1:
                typeText="等待商家接受訂單";
                break;
            case 2:
                typeText="商家已接受，等待外送員中";
                break;
            case 3:
                typeText="外送員已接受，等待餐點送達";
                break;
            case 4:
                typeText="已完成訂單";
                break;
            default:
                break;

        }
        holder.remark.setText(fullOrderModelList.get(position).getRemark());
        holder.payment.setText(fullOrderModelList.get(position).getPayment());
        holder.address.setText(fullOrderModelList.get(position).getAddress());

        holder.viewCarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(fullOrderModelList.get(position).getType()) {
                    case 0:
                        OrderDetail.order = fullOrderModelList.get(position).getCustomer() + fullOrderModelList.get(position).getStore() + "shopcar";
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        OrderDetail.order = Integer.toString(fullOrderModelList.get(position).getKey());
                        break;
                    default:
                        break;
                }
                Intent intent = new Intent(context, StoreFullOrderActivity.class);
                intent.putExtra("storeName",fullOrderModelList.get(position).getStore());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return fullOrderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView customer, store, delivery, type, remark,payment,address;
        Button viewCarItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            customer = itemView.findViewById(R.id.customer);
            delivery = itemView.findViewById(R.id.delivery);
            remark = itemView.findViewById(R.id.remark);
            payment = itemView.findViewById(R.id.payment);
            address = itemView.findViewById(R.id.address);
            viewCarItem = itemView.findViewById(R.id.viewCar);
        }
    }
}
