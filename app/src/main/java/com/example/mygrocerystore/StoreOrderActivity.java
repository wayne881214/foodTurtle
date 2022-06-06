package com.example.mygrocerystore;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerystore.activities.OrderDetail;
import com.example.mygrocerystore.adapter.OrderAdapter;
import com.example.mygrocerystore.models.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoreOrderActivity extends AppCompatActivity {
	OrderAdapter orderAdapter;
	List<OrderModel> OrderModelList;
	RecyclerView recyclerView;
	int total=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_agree);
		String customerName="pp";
		String storeName="123";
		String deliveryManName="null";
		String orderName=customerName+storeName+"shopcar";
		TextView customer=(TextView) findViewById(R.id.customer);
		customer.setText(customerName);

		TextView deliveryMan=(TextView)findViewById(R.id.deliveryMan);
		deliveryMan.setText(deliveryManName);

		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference storesRef = database.getReference("/Orders/"+orderName+"/product");
//		DatabaseReference orderRef = database.getReference("/Orders/"+orderName);

		recyclerView = findViewById(R.id.order_rec);
		recyclerView.setVisibility(View.GONE);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));


		OrderModelList=new ArrayList<>();
		orderAdapter = new OrderAdapter(this, OrderModelList);
		recyclerView.setAdapter(orderAdapter);

		storesRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot ds : dataSnapshot.getChildren()) {
					System.out.println("Data:"+ds);

					String name=ds.child("name").getValue().toString();
					String count=ds.child("count").getValue().toString();
					int price=Integer.parseInt(ds.child("price").getValue().toString());
					int totalPrice=Integer.parseInt(ds.child("totalPrice").getValue().toString());
					String imgUrl=ds.child("img_url").getValue().toString();
					total+=totalPrice;
					OrderModelList.add(new OrderModel(name, count, price, totalPrice,imgUrl));

				}
				orderAdapter.notifyDataSetChanged();
				recyclerView.setVisibility(View.VISIBLE);

			}
			@Override
			public void onCancelled(DatabaseError error) {
				// Failed to read value
			}
		});

	}
}
