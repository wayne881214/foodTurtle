package com.example.mygrocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mygrocerystore.MainActivity;
import com.example.mygrocerystore.R;
import com.example.mygrocerystore.models.UserModel;
import com.example.mygrocerystore.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetailedActivity extends AppCompatActivity {

    TextView quantity;
    int totalQuantity = 1;
    int totalPrice = 0;

    ImageView detailedImg;
    TextView price, rating, description;
    Button addToCart;
    ImageView addItem, removeItem;
    Toolbar toolbar;
    String name;
    FirebaseFirestore firestore;
    FirebaseDatabase database;
    FirebaseAuth auth;

    ViewAllModel viewAllModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel) {
            viewAllModel = (ViewAllModel) object;
        }

        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_dec);
        database .getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        name=userModel.getName();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        if (viewAllModel != null) {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("Price :$"+viewAllModel.getPrice()+"/kg");

            totalPrice = viewAllModel.getPrice() * totalQuantity;

            if(viewAllModel.getType().equals("egg")) {
                price.setText("Price :$"+viewAllModel.getPrice()+"/dozen");
                totalPrice = viewAllModel.getPrice() * totalQuantity;
            }

            if(viewAllModel.getType().equals("milk")) {
                price.setText("Price :$"+viewAllModel.getPrice()+"/litre");
                totalPrice = viewAllModel.getPrice() * totalQuantity;
            }

        }
        addToCart = findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                }
            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                }
            }
        });

    }

    private void addedToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentData = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentData.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> carMap = new HashMap<>();

        carMap.put("productName", viewAllModel.getName());
        carMap.put("productPrice", price.getText().toString());
        carMap.put("currentData", saveCurrentDate);
        carMap.put("currentTime", saveCurrentTime);
        carMap.put("totalQuantity", quantity.getText().toString());
        carMap.put("totalPrice", totalPrice);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = firebaseDatabase.getReference("Orders");
        DatabaseReference listRef = ordersRef.child(name+viewAllModel.getStoreName()+"shopcar");
        DatabaseReference productRef = listRef.child("product").child(viewAllModel.getName());


        Map<String, Object> order = new HashMap<>();
        order.put("customer", name);
        order.put("store", viewAllModel.getStoreName());
        listRef.updateChildren(order);


        Map<String, Object> product = new HashMap<>();
        product.put("totalPrice",totalPrice);
        product.put("count",  quantity.getText().toString());
        product.put("price", price.getText().toString());
        product.put("name", viewAllModel.getName());
        productRef.updateChildren(product);

        Toast.makeText(this,"新增成功", Toast.LENGTH_LONG).show();


        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(carMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }
}