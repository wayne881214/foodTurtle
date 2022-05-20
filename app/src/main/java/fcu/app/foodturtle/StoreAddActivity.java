package fcu.app.foodturtle;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StoreAddActivity extends AppCompatActivity  {
	private EditText etName;
	private EditText etFreight;
	private EditText etFraction;
	private EditText etType;

	private EditText etFoodName;
	private EditText etFoodCommit;
	private EditText etFoodMoney;
	private EditText etFoodType;

	private FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_add);

		etName = findViewById(R.id.storeName);
		etFreight = findViewById(R.id.storeFreight);
		etFraction = findViewById(R.id.storeFraction);
		etType = findViewById(R.id.storeType);

		etFoodName = findViewById(R.id.foodName);
		etFoodCommit = findViewById(R.id.foodCommit);
		etFoodMoney = findViewById(R.id.foodMoney);
		etFoodType = findViewById(R.id.foodType);

		firebaseAuth = FirebaseAuth.getInstance();
//		temp();
//		addStore();
	}

	public void addStore(View view) {
		String Name = etName.getText().toString();
		String Freight = etFreight.getText().toString();
		String Fraction = etFraction.getText().toString();
		String Type = etType.getText().toString();

		FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
		DatabaseReference storesRef = firebaseDatabase.getReference("stores");
		DatabaseReference nameRef = storesRef.child(Name);
		Map<String, Object> store = new HashMap<>();
		store.put("imgResId", Type);
		store.put("storeName", Name);
		store.put("storeFreight", Freight);
		store.put("storeFraction", Fraction);
		store.put("storeType", Type);
		nameRef.updateChildren(store);

		Toast.makeText(this,Name+"新增成功", Toast.LENGTH_LONG).show();

	}
	public void addFood(View view) {

		String Name = etName.getText().toString();
		String FoodName = etFoodName.getText().toString();
		String FoodCommit = etFoodCommit.getText().toString();
		String FoodType = etFoodType.getText().toString();
		String FoodMoney = etFoodMoney.getText().toString();

		String path="stores/"+Name+"/foods";
		Toast.makeText(this,path+"新增!!", Toast.LENGTH_LONG).show();

		FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
		DatabaseReference foodsRef = firebaseDatabase.getReference(path);
		DatabaseReference nameRef = foodsRef.child(FoodName);
		Map<String, Object> food = new HashMap<>();
		food.put("name", FoodName);
		food.put("commit", FoodCommit);
		food.put("type", FoodType);
		food.put("money", FoodMoney);
		nameRef.updateChildren(food);

		Toast.makeText(this,FoodName+"新增成功", Toast.LENGTH_LONG).show();

	}
//	public void temp(){
//			Toast.makeText(this,"Try", Toast.LENGTH_LONG).show();
//			String T="877";
//			String Name = T;
//			String Freight = T;
//			String Fraction = T;
//			String Type = T;
//			FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//			DatabaseReference storesRef = firebaseDatabase.getReference("stores");
//			DatabaseReference nameRef = storesRef.child(Name);
//			Map<String, Object> store = new HashMap<>();
//			store.put("name", Name);
//			store.put("freight", Freight);
//			store.put("fraction", Fraction);
//			store.put("type", Type);
//			nameRef.updateChildren(store);
//	}

}
