package com.example.mygrocerystore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.adapter.OrderAdapter;
import com.example.mygrocerystore.models.FullOrderModel;
import com.example.mygrocerystore.models.OrderModel;
import com.example.mygrocerystore.ui.slideshow.SlideshowFragment;
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

public class StoreFullOrderActivity extends AppCompatActivity {
    FirebaseDatabase database;
    RecyclerView recyclerView;
    EditText remarkEdit,paymentEdit,addressEdit;
    Button button,chat;
    TextView textview,textView11,textView14,textView12;
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
        setContentView(R.layout.activity_store_order);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        textview = findViewById(R.id.textView5);

				textView11=findViewById(R.id.textView11);
				textView14=findViewById(R.id.textView14);
				textView12=findViewById(R.id.textView12);

        remarkEdit=findViewById(R.id.remark);
        paymentEdit=findViewById(R.id.payment);
        addressEdit=findViewById(R.id.address);
        button =findViewById(R.id.toOrder);
        button.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.order_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chat=findViewById(R.id.chat);
        storeName = getIntent().getStringExtra("storeName");
        toolbar.setTitle(storeName);
        database =FirebaseDatabase.getInstance();

        OrderModelList=new ArrayList<>();
        orderAdapter = new OrderAdapter(this, OrderModelList);
        recyclerView.setAdapter(orderAdapter);

        database.getReference().child("Stores").child(FirebaseAuth.getInstance().getUid()).child("name")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                        Username = snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

      //先把訂單資料存下
			DatabaseReference OrdersTypeRef = database.getReference().child("Orders").child(OrderDetail.order);
			OrdersTypeRef.addValueEventListener(new ValueEventListener() {
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
					fullOrder = dataSnapshot.getValue(FullOrderModel.class);
					textView11.setText("備註:"+fullOrder.getRemark());
					textView12.setText("付款方式:"+fullOrder.getPayment());
					textView14.setText("地址"+fullOrder.getAddress());

					TextView customer=(TextView) findViewById(R.id.customer);
					customer.setText(fullOrder.getCustomer());
					TextView deliveryMan=(TextView)findViewById(R.id.deliveryMan);
					deliveryMan.setText(fullOrder.getDelivery());
                    //按鈕顯示隱藏
                    if(fullOrder.getType()==1) {
                        button.setVisibility(View.VISIBLE);
                        button.setText("確認接受");
                    }
				}

				@Override
				public void onCancelled(DatabaseError error) {
					// Failed to read value
				}
			});

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
            		System.out.println("OrderDetail.order:"+OrderDetail.order);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
										OrderModel order = ds.getValue(OrderModel.class);
										total+=order.getTotalPrice();
                    OrderModelList.add(order);
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

        //點擊修改訂單
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
							Map<String, Object> order = new HashMap<>();
							order.put("key",fullOrder.getKey());
							order.put("type",fullOrder.getType()+1);
							database.getReference().child("Orders").child(OrderDetail.order).updateChildren(order);
							Toast.makeText(StoreFullOrderActivity.this,fullOrder.getType()+"接受修改", Toast.LENGTH_LONG).show();
//							Intent intent=new Intent();
//							intent.setClass(StoreFullOrderActivity.this, SlideshowFragment.class);
//							startActivity(intent);
							finish();

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
						Intent intent=new Intent();
                        intent.putExtra("name",Username);
						intent.setClass(StoreFullOrderActivity.this, ChatActivity.class);
						startActivity(intent);
            }
        });
    }
}
