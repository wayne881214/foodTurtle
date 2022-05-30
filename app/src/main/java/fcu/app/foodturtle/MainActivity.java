package fcu.app.foodturtle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fcu.app.foodturtle.ArrayAdapter.OrderArrayAdapter;
import fcu.app.foodturtle.item.OrderItem;

public class MainActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {
    String email="";
    private EditText etEmail;
    private EditText etPassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        firebaseAuth = FirebaseAuth.getInstance();
    }

  public void homePage(View v) {
//	Intent intent = new Intent();
//	intent.setClass(this,MainActivity.class);
//	startActivity(intent);
	setContentView(R.layout.activity_main);
  }
    public void Viewmenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,BrowseActivity.class);
        startActivity(intent);
    }

    public void createmenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,CreateAccountActivity.class);
        startActivity(intent);
    }

    public void deliverymenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,deliveryOrderActivity.class);
        startActivity(intent);
    }
    public void Ordermenu(View v) {
        UserDetail.username="store1";
        Intent intent = new Intent();
        intent.setClass(this,OrdersHomeActivity.class);
        startActivity(intent);
    }
		public void debug(View v) {
			Intent intent = new Intent();
			intent.setClass(this,debugActivity.class);
			startActivity(intent);
		}
    public void OnLogin(View view){
        email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("users/");
            userRef.addValueEventListener(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if(email.equals(ds.child("email").getValue().toString())) {
                            UserDetail.username=ds.child("name").getValue().toString();
                            break;
                        }
                    }

                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
            Toast.makeText(this,UserDetail.username+"登入成功", Toast.LENGTH_LONG).show();
            BrowseActivity.VALID_USER = true;
            finish();
        } else {
            Toast.makeText(this,"登入失敗", Toast.LENGTH_LONG).show();
        }
    }


}