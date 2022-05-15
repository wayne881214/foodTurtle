package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ordersAcceptActivity extends AppCompatActivity {
    String name = UserDetail.username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_accept);
    }
}