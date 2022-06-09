package com.example.mygrocerystore.ui.DeliveryFragment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.activities.OrderDetail;
import com.example.mygrocerystore.adapter.DeliveryOrderAdapter;
import com.example.mygrocerystore.adapter.FullOrderAdapter;
import com.example.mygrocerystore.adapter.MyCartAdapter;
import com.example.mygrocerystore.models.FullOrderModel;
import com.example.mygrocerystore.models.MyCartModel;
import com.example.mygrocerystore.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryFragment2 extends Fragment {
	FirebaseDatabase database;
	String name="null";
	List<MyCartModel> cartModelList,cartModelList2,cartModelList3;
	List<FullOrderModel> fullOrderModelList,fullOrderModelList2,fullOrderModelList3;
	MyCartAdapter cartAdapter,cartAdapter2,cartAdapter3;
	DeliveryOrderAdapter fullOrderAdapter,fullOrderAdapter2,fullOrderAdapter3;
	RecyclerView recyclerView,recyclerView2,recyclerView3;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_delivery2, container, false);

			database=FirebaseDatabase.getInstance();
			database.getReference().child("DeliveryMan").child(FirebaseAuth.getInstance().getUid())
			.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					UserModel userModel = snapshot.getValue(UserModel.class);
					OrderDetail.customer=userModel.getName();
					name=userModel.getName();
					TextView DeliveryName=root.findViewById(R.id.DeliveryName2);
					DeliveryName.setText("外送員名稱:"+name);
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
				fullOrderModelList2 = new ArrayList<>();
				fullOrderAdapter2 = new DeliveryOrderAdapter(getActivity(),fullOrderModelList2);
				recyclerView2 = root.findViewById(R.id.store_recyclerview2);
				recyclerView2.setVisibility(View.GONE);
				recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(),  RecyclerView.HORIZONTAL, false));
				recyclerView2.setAdapter(fullOrderAdapter2);

			fullOrderModelList3 = new ArrayList<>();
			fullOrderAdapter3 = new DeliveryOrderAdapter(getActivity(),fullOrderModelList3);
			recyclerView3 = root.findViewById(R.id.store_recyclerview3);
			recyclerView3.setVisibility(View.GONE);
			recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(),  RecyclerView.HORIZONTAL, false));
			recyclerView3.setAdapter(fullOrderAdapter3);
			FirebaseDatabase database = FirebaseDatabase.getInstance();
			DatabaseReference ordersRef = database.getReference("/Orders/");

			ordersRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {

					recyclerView2.setVisibility(View.VISIBLE);
					recyclerView3.setVisibility(View.VISIBLE);
					for (DataSnapshot ds : dataSnapshot.getChildren()) {
						FullOrderModel fullOrder = ds.getValue(FullOrderModel.class);
						fullOrder.showAll();
							switch(fullOrder.getType()){
								case 1:
//									fullOrderModelList.add(fullOrder);
									break;
								case 2:
									fullOrderModelList2.add(fullOrder);
									break;
								case 3:
									fullOrderModelList3.add(fullOrder);
									break;
								default:
									break;
							}
					}
				}
				@Override
				public void onCancelled(DatabaseError error) {
					// Failed to read value
				}
			});


			return root;
    }

}