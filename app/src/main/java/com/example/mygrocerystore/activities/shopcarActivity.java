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
    EditText editText;
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
        editText=findViewById(R.id.remark);
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
        //顯示購物車中內的商品資訊
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
                button.setText("送出訂單"+" "+total+"$");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        //送出訂單按鈕被點擊
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //新訂單key為資料庫訂單數量ProductCount

                //添加舊有購物車資訊至新訂單中
                Map<String, Object> order = new HashMap<>();
                order.put("customer",OrderDetail.customer);
                order.put("delivery","無");
                order.put("store",storeName);
                order.put("type", 1);
                order.put("remark",editText.getText().toString());
                DatabaseReference listRef =  database.getReference().child("Orders").child(Integer.toString(productCount));
                listRef.updateChildren(order);



                //添加舊有購物車商品至新訂單中
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


                //刪除現有購物車資料
                database.getReference().child("Orders").child(OrderDetail.order).removeValue();

                //更新訂單總數量
                Map<String, Object> newCount = new HashMap<>();
                newCount.put("count",productCount+1);
                database.getReference().child("Counts").updateChildren(newCount);

                //跳出提示 跳轉到該我的訂單-該筆資料
                OrderDetail.order=Integer.toString(productCount);
                Toast.makeText(shopcarActivity.this,"加入訂單成功", Toast.LENGTH_LONG).show();
                Intent intent=new Intent();
                intent.setClass(shopcarActivity.this, OrderActivity.class);
                intent.putExtra("storeName",storeName);
                intent.putExtra("count",productCount);
                startActivity(intent);
            }
        });
    }
}
