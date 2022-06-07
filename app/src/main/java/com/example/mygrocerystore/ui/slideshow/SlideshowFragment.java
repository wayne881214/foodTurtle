package com.example.mygrocerystore.ui.slideshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerystore.AddStoreActivity;
import com.example.mygrocerystore.R;
import com.example.mygrocerystore.StoreOrderActivity;
import com.example.mygrocerystore.activities.OrderDetail;
import com.example.mygrocerystore.adapter.FullOrderAdapter;
import com.example.mygrocerystore.adapter.MyCartAdapter;
import com.example.mygrocerystore.databinding.FragmentSlideshowBinding;
import com.example.mygrocerystore.models.FullOrderModel;
import com.example.mygrocerystore.models.MyCartModel;
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
import java.util.concurrent.TimeUnit;

public class SlideshowFragment extends Fragment {


	Activity activity;
	Button button;
	Button testOrder;
	String storeName="null";
	List<MyCartModel> cartModelList,cartModelList2,cartModelList3;
	List<FullOrderModel> fullOrderModelList,fullOrderModelList2,fullOrderModelList3;
	MyCartAdapter cartAdapter,cartAdapter2,cartAdapter3;
	FullOrderAdapter fullOrderAdapter,fullOrderAdapter2,fullOrderAdapter3;
	RecyclerView recyclerView,recyclerView2,recyclerView3;



	public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


			View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
			activity = getActivity();

			TextView store=root.findViewById(R.id.storeName);

			FirebaseDatabase database = FirebaseDatabase.getInstance();
			database.getReference().child("Stores").child(FirebaseAuth.getInstance().getUid()).child("storeName")
			.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					storeName=snapshot.getValue(String.class);
					store.setText("商店名稱:"+storeName);
				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});

		fullOrderModelList = new ArrayList<>();
		fullOrderAdapter = new FullOrderAdapter(getActivity(),fullOrderModelList);

		fullOrderModelList2 = new ArrayList<>();
		fullOrderAdapter2 = new FullOrderAdapter(getActivity(),fullOrderModelList2);

		fullOrderModelList3 = new ArrayList<>();
		fullOrderAdapter3 = new FullOrderAdapter(getActivity(),fullOrderModelList3);

		recyclerView = root.findViewById(R.id.store_recyclerview);
		recyclerView2 = root.findViewById(R.id.store_recyclerview2);
		recyclerView3 = root.findViewById(R.id.store_recyclerview3);

		recyclerView.setVisibility(View.GONE);
		recyclerView2.setVisibility(View.GONE);
		recyclerView3.setVisibility(View.GONE);

		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),  RecyclerView.HORIZONTAL, false));
		recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(),  RecyclerView.HORIZONTAL, false));
		recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(),  RecyclerView.HORIZONTAL, false));


		recyclerView.setAdapter(fullOrderAdapter);
		recyclerView2.setAdapter(fullOrderAdapter2);
		recyclerView3.setAdapter(fullOrderAdapter3);



		DatabaseReference ordersRef = database.getReference("/Orders/");

			ordersRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					recyclerView.setVisibility(View.VISIBLE);
					recyclerView2.setVisibility(View.VISIBLE);
					recyclerView3.setVisibility(View.VISIBLE);
					for (DataSnapshot ds : dataSnapshot.getChildren()) {
//						System.out.println("Data:"+ds);
						FullOrderModel fullOrder = ds.getValue(FullOrderModel.class);
						fullOrder.showAll();
						if(fullOrder.getStore().equals(storeName)){
							switch(fullOrder.getType()){
								case 1:
									fullOrderModelList.add(fullOrder);
									fullOrderModelList.add(fullOrder);
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
				}
				@Override
				public void onCancelled(DatabaseError error) {
					// Failed to read value
				}
			});







        return root;
    }

}