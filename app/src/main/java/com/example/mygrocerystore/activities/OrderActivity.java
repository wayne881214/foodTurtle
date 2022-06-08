package com.example.mygrocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mygrocerystore.R;
import com.example.mygrocerystore.StoreMainActivity;
import com.example.mygrocerystore.adapter.OrderAdapter;
import com.example.mygrocerystore.adapter.ViewAllAdapter;
import com.example.mygrocerystore.models.FullOrderModel;
import com.example.mygrocerystore.models.OrderModel;
import com.example.mygrocerystore.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    FirebaseDatabase database;
    RecyclerView recyclerView;
    TextView customer,deliveryman,remark,payment,address;
    Button button,chat;
    TextView textview;
    OrderAdapter orderAdapter;
    List<OrderModel> OrderModelList;
    Toolbar toolbar;
    ProgressBar progressBar;
    String storeName;
    String name;
    String Username;
    int total=0;
    int productCount=0;
    FullOrderModel fullOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customer = findViewById(R.id.customer);
        deliveryman = findViewById(R.id.deliveryMan);
        remark = findViewById(R.id.remark);
        payment= findViewById(R.id.payment);
        address= findViewById(R.id.address);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        textview = findViewById(R.id.textView5);
        chat=findViewById(R.id.chat);
        button =findViewById(R.id.toOrder);
        button.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.order_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeName = getIntent().getStringExtra("storeName");
        toolbar.setTitle(storeName);
        database =FirebaseDatabase.getInstance();

        OrderModelList=new ArrayList<>();
        orderAdapter = new OrderAdapter(this, OrderModelList);
        recyclerView.setAdapter(orderAdapter);


        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("name")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                        Username = snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




        //顯示訂單中內的人物資訊
        DatabaseReference ShowsRef = database.getReference().child("Orders").child(OrderDetail.order);
        ShowsRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fullOrder = dataSnapshot.getValue(FullOrderModel.class);
                customer.setText(fullOrder.getCustomer());
                deliveryman.setText(fullOrder.getDelivery());
                remark.setText(fullOrder.getRemark());
                payment.setText(fullOrder.getPayment());
                address.setText(fullOrder.getAddress());

                //按鈕顯示隱藏
                if(fullOrder.getType()==3){
                    button.setVisibility(View.VISIBLE);
                    button.setText("確認餐點送達");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        //顯示訂單中內的商品資訊
        DatabaseReference OrdersRef = database.getReference().child("Orders").child(OrderDetail.order).child("product");
        OrdersRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String name=ds.child("name").getValue().toString();
                    String count=ds.child("count").getValue().toString();
                    int price=Integer.parseInt(ds.child("price").getValue().toString());
                    int totalPrice=Integer.parseInt(ds.child("totalPrice").getValue().toString());
                    String imgUrl=ds.child("img_url").getValue().toString();
                    total+=totalPrice;
                    OrderModelList.add(new OrderModel(name, count, price, totalPrice,imgUrl));
                    orderAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                textview.setText("總計金額:"+total+"$");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        //點擊修改訂單
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> order = new HashMap<>();
                order.put("key",fullOrder.getKey());
                order.put("type",fullOrder.getType()+1);
                database.getReference().child("Orders").child(OrderDetail.order).updateChildren(order);
                Toast.makeText(OrderActivity.this,fullOrder.getType()+"成功修改", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("name",Username);
                intent.setClass(OrderActivity.this, ChatActivity.class);
                Log.w(Username,Username);
                startActivity(intent);
            }
        });

    }
}
