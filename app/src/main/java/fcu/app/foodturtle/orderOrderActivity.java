package fcu.app.foodturtle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
		//存放資料庫資料list
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();

		//資料庫與路徑相關
		String foodPath;
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference foodRef;

		//食物分類 寫死中
		String foodType[]={"type1","type2","type3"};
	ListView lvFood;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_order);

				//設定分類
				TextView txv_type1=(TextView)findViewById(R.id.foodType1);
				TextView txv_type2=(TextView)findViewById(R.id.foodType2);
				TextView txv_type3=(TextView)findViewById(R.id.foodType3);
				String type="";
				txv_type1.setText(type+foodType[0]);
				txv_type2.setText(type+foodType[1]);
				txv_type3.setText(type+foodType[2]);



				Intent it = getIntent();
				storeName = it.getStringExtra("商店名稱");
				foodPath="/stores/"+storeName+"/foods";
				foodRef = database.getReference(foodPath);
				Toast.makeText(this,"歡迎進入:"+storeName , Toast.LENGTH_LONG).show();

				lvFood = this.findViewById(R.id.lv_food);

				foodList.clear();

				foodRef.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
							System.out.println("FoodData:"+foodSnapshot);
							FoodItem food = foodSnapshot.getValue(FoodItem.class);
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

	public void showAll(View v) {
		lvFood = this.findViewById(R.id.lv_food);

		foodList.clear();

		foodRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
					System.out.println("FoodData:"+foodSnapshot);
					FoodItem food = foodSnapshot.getValue(FoodItem.class);
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

		//顯示分類 分類目前寫死
		public void showType1(View v) {
			lvFood = this.findViewById(R.id.lv_food);

			foodList.clear();

			foodRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
						System.out.println("FoodData:"+foodSnapshot);
						FoodItem food = foodSnapshot.getValue(FoodItem.class);
						if(food.getFoodType().equals(foodType[0])){
							foodList.add(food);
						}
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

	public void showType2(View v) {
		lvFood = this.findViewById(R.id.lv_food);

		foodList.clear();

		foodRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
					System.out.println("FoodData:"+foodSnapshot);
					FoodItem food = foodSnapshot.getValue(FoodItem.class);
					if(food.getFoodType().equals(foodType[1])){
						foodList.add(food);
					}
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
	public void showType3(View v) {
		ListView lvFood = this.findViewById(R.id.lv_food);

		foodList.clear();

		foodRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
					System.out.println("FoodData:"+foodSnapshot);
					FoodItem food = foodSnapshot.getValue(FoodItem.class);
					if(food.getFoodType().equals(foodType[2])){
						foodList.add(food);
					}
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
}