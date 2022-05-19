package fcu.app.foodturtle;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
	private FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_add);

		etName = findViewById(R.id.storeName);
		etFreight = findViewById(R.id.storeFreight);
		etFraction = findViewById(R.id.storeFraction);
		etType = findViewById(R.id.storeType);

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
		store.put("name", Name);
		store.put("freight", Freight);
		store.put("fraction", Fraction);
		store.put("type", Type);
		nameRef.updateChildren(store);

		Toast.makeText(this,Name+"新增成功", Toast.LENGTH_LONG).show();

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
