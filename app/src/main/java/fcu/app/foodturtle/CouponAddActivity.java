
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

public class CouponAddActivity extends AppCompatActivity  {
	private EditText etId;
	private EditText etName;
	private EditText etMoney;
	private EditText etCode;
	private EditText etDescription;
	private EditText etDate;


//	String id, String name, int money, String code, String description, String date
	private FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_add);

		etId = findViewById(R.id.couponID);
		etName = findViewById(R.id.couponName);
		etMoney = findViewById(R.id.couponMoney);
		etCode = findViewById(R.id.couponCode);
		etDescription = findViewById(R.id.couponDescription);
		etDate = findViewById(R.id.couponDate);


		firebaseAuth = FirebaseAuth.getInstance();
		System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFf");

	}

	public void addCoupon(View view) {
		System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUu");

		String Id = etId.getText().toString();
		String Name = etName.getText().toString();
		String Money = etMoney.getText().toString();
		String Code = etCode.getText().toString();
		String Description = etDescription.getText().toString();
		String Date = etDate.getText().toString();

		System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
		DatabaseReference couponsRef = firebaseDatabase.getReference("coupons");
		DatabaseReference nameRef = couponsRef.child(Name);
		Map<String, Object> coupon = new HashMap<>();
		coupon.put("id", Id);
		coupon.put("name", Name);
		coupon.put("money", Integer.parseInt(Money));
		coupon.put("code", Code);
		coupon.put("description", Description);
		coupon.put("date", Date);
		nameRef.updateChildren(coupon);
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
