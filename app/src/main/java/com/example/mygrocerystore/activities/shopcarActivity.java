package com.example.mygrocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class shopcarActivity extends AppCompatActivity {
    FirebaseDatabase database;
    RecyclerView recyclerView;
    EditText remarkEdit,paymentEdit,addressEdit;
    Button button;
    TextView textview;
    OrderAdapter orderAdapter;
    List<OrderModel> OrderModelList;
    Toolbar toolbar;
    ProgressBar progressBar;
    String storeName;
    String name;
    int total=0;
    int productCount=0;
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
        remarkEdit=findViewById(R.id.remark);
        paymentEdit=findViewById(R.id.payment);
        addressEdit=findViewById(R.id.address);
        button =findViewById(R.id.toOrder);
        recyclerView = findViewById(R.id.order_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeName = getIntent().getStringExtra("storeName");
        toolbar.setTitle(storeName);
        database =FirebaseDatabase.getInstance();

        OrderModelList=new ArrayList<>();
        orderAdapter = new OrderAdapter(this, OrderModelList);
        recyclerView.setAdapter(orderAdapter);


        DatabaseReference ProductCountRef = database.getReference("Counts/count");
        ProductCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productCount=dataSnapshot.getValue(int.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        //????????????????????????????????????
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
                textview.setText("????????????:"+total+"$");
                button.setText("????????????"+" "+total+"$");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        //???????????????????????????
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //?????????key????????????????????????ProductCount

                //??????????????????????????????????????????
                Map<String, Object> order = new HashMap<>();
                order.put("key",productCount);
                order.put("customer",OrderDetail.customer);
                order.put("delivery","???");
                order.put("store",storeName);
                order.put("type", 1);
                order.put("remark",remarkEdit.getText().toString());
                order.put("payment",paymentEdit.getText().toString());
                order.put("address",addressEdit.getText().toString());
                DatabaseReference listRef =  database.getReference().child("Orders").child(Integer.toString(productCount));
                listRef.updateChildren(order);



                //??????????????????????????????????????????
                DatabaseReference addProductRef = database.getReference().child("Orders").child(OrderDetail.order).child("product");
                addProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            String name=ds.child("name").getValue().toString();
                            String count=ds.child("count").getValue().toString();
                            int price=Integer.parseInt(ds.child("price").getValue().toString());
                            int totalPrice=Integer.parseInt(ds.child("totalPrice").getValue().toString());
                            String imgUrl=ds.child("img_url").getValue().toString();
                            DatabaseReference productRef =  database.getReference().child("Orders")
                                                            .child(Integer.toString(productCount)).child("product").child(name);
                            Map<String, Object> product = new HashMap<>();
                            product.put("totalPrice",totalPrice);
                            product.put("name",name);
                            product.put("count",count);
                            product.put("price",price);
                            product.put("img_url",imgUrl);
                            productRef.updateChildren(product);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });


                //???????????????????????????
                database.getReference().child("Orders").child(OrderDetail.order).removeValue();

                //?????????????????????
                Map<String, Object> newCount = new HashMap<>();
                newCount.put("count",productCount+1);
                database.getReference().child("Counts").updateChildren(newCount);

                //???????????? ????????????????????????-????????????
                OrderDetail.order=Integer.toString(productCount);
                Toast.makeText(shopcarActivity.this,"??????????????????", Toast.LENGTH_LONG).show();
                Intent intent=new Intent();
                intent.setClass(shopcarActivity.this, OrderActivity.class);
                intent.putExtra("storeName",storeName);
                intent.putExtra("count",productCount);
                startActivity(intent);
            }
        });
    }
}
