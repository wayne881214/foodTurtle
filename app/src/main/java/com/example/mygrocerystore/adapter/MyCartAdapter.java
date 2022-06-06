package com.example.mygrocerystore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mygrocerystore.activities.shopcarActivity;
import com.example.mygrocerystore.PlacedOrderActivity;
import com.example.mygrocerystore.R;
import com.example.mygrocerystore.activities.OrderDetail;
import com.example.mygrocerystore.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String typeText="未讀取";
    String typeOrder="未讀取";

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCartAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.store.setText(cartModelList.get(position).getStore());
        holder.customer.setText(cartModelList.get(position).getCustomer());
        holder.delivery.setText(cartModelList.get(position).getDelivery());
        switch(cartModelList.get(position).getType())
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
        holder.type.setText(typeText);
        holder.remark.setText(cartModelList.get(position).getRemark());
        holder.payment.setText(cartModelList.get(position).getPayment());
        holder.address.setText(cartModelList.get(position).getAddress());

        holder.viewCarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(cartModelList.get(position).getType()) {
                    case 0:
                        OrderDetail.order = cartModelList.get(position).getCustomer() + cartModelList.get(position).getStore() + "shopcar";
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        OrderDetail.order = Integer.toString(cartModelList.get(position).getKey());
                        break;
                    default:
                        break;
                }
                Intent intent = new Intent(context,shopcarActivity.class);
                intent.putExtra("storeName",cartModelList.get(position).getStore());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView customer, store, delivery, type, remark,payment,address;
        Button viewCarItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            customer = itemView.findViewById(R.id.customer);
            store = itemView.findViewById(R.id.store);
            delivery = itemView.findViewById(R.id.delivery);
            type = itemView.findViewById(R.id.type);
            remark = itemView.findViewById(R.id.remark);
            payment = itemView.findViewById(R.id.payment);
            address = itemView.findViewById(R.id.address);
            viewCarItem = itemView.findViewById(R.id.viewCar);
        }
    }
}
