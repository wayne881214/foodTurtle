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

import com.example.mygrocerystore.AddStoreActivity;
import com.example.mygrocerystore.R;
import com.example.mygrocerystore.StoreOrderActivity;
import com.example.mygrocerystore.databinding.FragmentSlideshowBinding;
import com.example.mygrocerystore.models.FullOrderModel;
import com.example.mygrocerystore.models.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SlideshowFragment extends Fragment {


	Activity activity;
	Button button;
	Button testOrder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


			View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
			activity = getActivity();
			button = root.findViewById(R.id.button);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, AddStoreActivity.class);
					startActivity(intent);
				}
			});

			testOrder = root.findViewById(R.id.waitAceptOrder);
			testOrder.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, StoreOrderActivity.class);
					startActivity(intent);
				}
			});

			FirebaseDatabase database = FirebaseDatabase.getInstance();
			DatabaseReference ordersRef = database.getReference("/Orders/");

			//讀取商家 寫死中
			String storeName="123";
			TextView store=root.findViewById(R.id.storeName);
			store.setText("商店名稱:"+storeName);
			ordersRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot ds : dataSnapshot.getChildren()) {
						System.out.println("Data:"+ds);
						FullOrderModel fullOrder = ds.getValue(FullOrderModel.class);
						System.out.println("Type????"+fullOrder.getType());

						if(fullOrder.getType()==1&& fullOrder.getStore().equals(storeName)){
							System.out.println("Type!!!!!"+fullOrder.getType());

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