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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

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
        Intent intent = new Intent();
        intent.setClass(this,OrdersHomeActivity.class);
        startActivity(intent);
    }

    public void OnLogin(View view){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()) {
            Toast.makeText(this,"登入成功", Toast.LENGTH_LONG).show();
            BrowseActivity.VALID_USER = true;
            finish();
        } else {
            Toast.makeText(this,"登入失敗", Toast.LENGTH_LONG).show();
        }
    }
}