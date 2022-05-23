package fcu.app.foodturtle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fcu.app.foodturtle.ArrayAdapter.FoodArrayAdapter;
import fcu.app.foodturtle.ArrayAdapter.StoreArrayAdapter;
import fcu.app.foodturtle.item.FoodItem;
import fcu.app.foodturtle.item.StoreItem;

public class orderOrderActivity extends AppCompatActivity {
		Context T=this;
		public String storeName;
		@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_order);
				Intent it = getIntent();
				storeName = it.getStringExtra("商店名稱");
				Toast.makeText(this,"歡迎進入:"+storeName , Toast.LENGTH_LONG).show();

			ListView lvFood = this.findViewById(R.id.lv_food);
//		foodList.add(new FoodItem(R.drawable.menu01,"羊肉漢堡" ,"附贈小杯飲料",30,"漢堡"));
//		foodList.add(new FoodItem(R.drawable.menu01,"豬漢堡" ,"附贈小杯飲料",30,"漢堡"));
//		foodList.add(new FoodItem(R.drawable.menu01,"雞肉漢堡" ,"附贈小杯飲料",30,"漢堡"));

			FirebaseDatabase database = FirebaseDatabase.getInstance();

			String foodPath="/stores/"+storeName+"/foods";
			DatabaseReference foodRef = database.getReference(foodPath);
			foodRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
					for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
						System.out.println("FoodData:"+foodSnapshot);
						FoodItem food = foodSnapshot.getValue(FoodItem.class);
						System.out.println("DataName:"+food.getFoodName()+food.getFoodCommit()+food.getFoodType()+food.getFoodMoney());
						foodList.add(food);
					}
					FoodArrayAdapter adapter = new FoodArrayAdapter(T, R.layout.listitem_food, foodList);
					lvFood.setAdapter(adapter);
				}
				@Override
				public void onCancelled(DatabaseError error) {
					// Failed to read value
				}
			});

    }

    public void shopcarview(View v) {
        Intent intent = new Intent();
        intent.setClass(this,ShopcarActivity.class);
        startActivity(intent);
    }

}