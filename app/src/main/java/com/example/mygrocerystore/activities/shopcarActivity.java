package com.example.mygrocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.mygrocerystore.R;
import com.example.mygrocerystore.adapter.OrderAdapter;
import com.example.mygrocerystore.adapter.ViewAllAdapter;
import com.example.mygrocerystore.models.OrderModel;
import com.example.mygrocerystore.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class shopcarActivity extends AppCompatActivity {
    FirebaseDatabase database;
    RecyclerView recyclerView;
    TextView textview;
    OrderAdapter orderAdapter;
    List<OrderModel> OrderModelList;
    Toolbar toolbar;
    ProgressBar progressBar;
    String storeName;
    String name;
    int total=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        textview = findViewById(R.id.textView5);
        recyclerView = findViewById(R.id.order_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeName = getIntent().getStringExtra("storeName");
        toolbar.setTitle(storeName);
        database =FirebaseDatabase.getInstance();

        OrderModelList=new ArrayList<>();
        orderAdapter = new OrderAdapter(this, OrderModelList);
        recyclerView.setAdapter(orderAdapter);

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

        }
}
