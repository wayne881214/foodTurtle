package com.example.mygrocerystore.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mygrocerystore.models.UserModel;
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

public class DeliveryFullOrderActivity extends AppCompatActivity {
    FirebaseDatabase database;
    RecyclerView recyclerView;
    EditText remarkEdit,paymentEdit,addressEdit;
    Button button,map,chat;
    TextView textview,textView11,textView14,textView12;
    OrderAdapter orderAdapter;
    List<OrderModel> OrderModelList;
    Toolbar toolbar;
    ProgressBar progressBar;
    String storeName;
    String name;
    int total=0;
    int productCount=0;
    FullOrderModel fullOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_order);
        database=FirebaseDatabase.getInstance();
        database.getReference().child("DeliveryMan").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					UserModel userModel = snapshot.getValue(UserModel.class);
					OrderDetail.customer=userModel.getName();
					name=userModel.getName();
				}
				@Override
				public void onCancelled(@NonNull DatabaseError error) {
				}
			});
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
        map =findViewById(R.id.map);
        chat=findViewById(R.id.chat);
        recyclerView = findViewById(R.id.order_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeName = getIntent().getStringExtra("storeName");
        toolbar.setTitle(storeName);
        database =FirebaseDatabase.getInstance();

        OrderModelList=new ArrayList<>();
        orderAdapter = new OrderAdapter(this, OrderModelList);
        recyclerView.setAdapter(orderAdapter);

      //先把訂單資料存下
			DatabaseReference OrdersTypeRef = database.getReference().child("Orders").child(OrderDetail.order);
			OrdersTypeRef.addValueEventListener(new ValueEventListener() {
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
					fullOrder = dataSnapshot.getValue(FullOrderModel.class);
					textView11.setText("備註:"+fullOrder.getRemark());
					textView12.setText("付款方式:"+fullOrder.getPayment());
					textView14.setText("地址:"+fullOrder.getAddress());

					TextView customer=(TextView) findViewById(R.id.customer);
					customer.setText(fullOrder.getCustomer());
					TextView deliveryMan=(TextView)findViewById(R.id.deliveryMan);
					deliveryMan.setText(fullOrder.getDelivery());
					Log.w("type","type:"+fullOrder.getType());
                    if(fullOrder.getType()==2) {
                        button.setText("確認接受");
                        button.setVisibility(View.VISIBLE);
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
            		//System.out.println("OrderDetail.order:"+OrderDetail.order);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
										OrderModel order = ds.getValue(OrderModel.class);
										total+=order.getTotalPrice();
                    OrderModelList.add(order);
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
							order.put("delivery",name);
							order.put("type",fullOrder.getType()+1);
							database.getReference().child("Orders").child(OrderDetail.order).updateChildren(order);
							Toast.makeText(DeliveryFullOrderActivity.this,fullOrder.getType()+"接受修改", Toast.LENGTH_LONG).show();
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
                intent.putExtra("name",name);
                intent.setClass(DeliveryFullOrderActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

			map.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String sSource = storeName.trim();
					String sDestination = fullOrder.getAddress().trim();

					if (sSource.equals("") && sDestination.equals("")) {
						Toast.makeText(getApplicationContext(), "輸入位址", Toast.LENGTH_SHORT).show();
					} else {
						DisplayTrack(sSource, sDestination);
					}

				}
			});
    }

	private void DisplayTrack(String sSource, String sDestination) {
		try {
			Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sSource + "/" + sDestination);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.setPackage("com.google.android.apps.maps");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}
}
