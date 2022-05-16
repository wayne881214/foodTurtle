package fcu.app.foodturtle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etName;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etName = findViewById(R.id.et_name);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void OnRegister(View view){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()) {
            Toast.makeText(this,"註冊成功", Toast.LENGTH_LONG).show();
            addUser();
            finish();
        } else {
            Toast.makeText(this,"註冊失敗", Toast.LENGTH_LONG).show();
        }
    }

    private void addUser() {
        String email = etEmail.getText().toString();
        String name = etName.getText().toString();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = firebaseDatabase.getReference("users");
        DatabaseReference phoneRef = usersRef.child(name);

        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("name", name);
        phoneRef.updateChildren(userData);
    }
}