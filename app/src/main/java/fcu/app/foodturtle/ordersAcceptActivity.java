package fcu.app.foodturtle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ordersAcceptActivity extends AppCompatActivity {


    public class Order_data {
        //ArrayList number ;
        String food;
        String number;
        String remark;
    }
    String name = UserDetail.username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orders_accept);
        LinearLayout layout = (LinearLayout) findViewById(R.id.order_menu);
        TextView customer = (TextView) findViewById(R.id.customer);
        TextView food1 = (TextView) findViewById(R.id.food1);
        TextView number1 = (TextView) findViewById(R.id.number1);
        TextView remark1 = (TextView) findViewById(R.id.remark1);
        TextView food2 = (TextView) findViewById(R.id.food2);
        TextView number2 = (TextView) findViewById(R.id.number2);
        TextView remark2 = (TextView) findViewById(R.id.remark2);
        TextView food3 = (TextView) findViewById(R.id.food3);
        TextView number3 = (TextView) findViewById(R.id.number3);
        TextView remark3 = (TextView) findViewById(R.id.remark3);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference customerRef = database.getReference("order/bobo/1/customer");

        customerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                customer.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        /*
        food1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                food1.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });*/

        DatabaseReference foodRef = database.getReference("order/bobo/1/food");
        foodRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String food=ds.child("food").getValue().toString();
                    String number=ds.child("number").getValue().toString();
                    String remark=ds.child("remark").getValue().toString();
                    i++;
                    if(i==1){
                        food1.setText(food);
                        number1.setText(number);
                        remark1.setText(remark);
                    }
                    if(i==2){
                        food2.setText(food);
                        number2.setText(number);
                        remark2.setText(remark);
                    }
                    if(i==3){
                        food3.setText(food);
                        number3.setText(number);
                        remark3.setText(remark);
                    }
                    /*String word=ds.child("food").getValue().toString();
                    TextView textView = new TextView(ordersAcceptActivity.this);
                    LinearLayout linearLayout =new LinearLayout(ordersAcceptActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(20, 10, 20, 20);
                    textView.setLayoutParams(lp);
                    textView.setText(word);
                    layout.addView(textView);*/
                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }


}