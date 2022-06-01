package com.example.mygrocerystore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mygrocerystore.models.PopularModel;
import com.example.mygrocerystore.models.ViewAllModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddStoreActivity extends AppCompatActivity {
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

		etName = findViewById(R.id.storeName1);
		etFreight = findViewById(R.id.storeFreight1);
		etFraction = findViewById(R.id.storeFraction1);
		etType = findViewById(R.id.storeType1);
//
//		etFoodName = findViewById(R.id.foodName);
//		etFoodCommit = findViewById(R.id.foodCommit);
//		etFoodMoney = findViewById(R.id.foodMoney);
//		etFoodType = findViewById(R.id.foodType1);

		firebaseAuth = FirebaseAuth.getInstance();
	}
	public void addStore(View view) {

		String Name = etName.getText().toString();
		String Freight = etFreight.getText().toString();
		String Fraction = etFraction.getText().toString();
		String Type = etType.getText().toString();

		String img_url="https://firebasestorage.googleapis.com/v0/b/my-grocery-store-60a2c.appspot.com/o/grocery10.jpg?alt=media&token=4de96ce4-cd4f-44e6-9513-56e83944bd91";

		FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
		PopularModel store =new PopularModel(Name,Name,Fraction,Name,Name,Type,img_url);
		firebaseDatabase.collection("PopularProducts").document(Name).set(store);

		//Real time
		FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
		DatabaseReference storesRef = firebaseDatabase2.getReference("Stores");
		DatabaseReference nameRef = storesRef.child(Name);
		Map<String, Object> store2 = new HashMap<>();
		store2.put("storeName", Name);
		store2.put("type", Type);
		nameRef.updateChildren(store2);
		Toast.makeText(this,Name+"新增成功", Toast.LENGTH_LONG).show();

	}
	public void addFood(View view) {

		String Name = etName.getText().toString();
//		String FoodName = etFoodName.getText().toString();
//		String FoodCommit = etFoodCommit.getText().toString();
//		String FoodType = etFoodType.getText().toString();
//		String FoodMoney = etFoodMoney.getText().toString();

		FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();

		ViewAllModel food=new ViewAllModel(Name,Name,Name,Name,Name,Name,100);
		firebaseDatabase.collection("AllProducts").document(Name).set(food);

//		PopularModel store =new PopularModel(Name,Fraction,Name,Name,Freight,Type);
//		firebaseDatabase.collection("PopularProducts").document(Name).set(store);


	
		Toast.makeText(this,Name+"新增成功", Toast.LENGTH_LONG).show();

	}
}

