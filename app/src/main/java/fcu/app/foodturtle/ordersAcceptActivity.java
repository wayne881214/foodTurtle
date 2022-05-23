package fcu.app.foodturtle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fcu.app.foodturtle.ArrayAdapter.OrderArrayAdapter;
import fcu.app.foodturtle.ArrayAdapter.StoreArrayAdapter;
import fcu.app.foodturtle.item.OrderItem;

public class ordersAcceptActivity extends AppCompatActivity {
    String name = UserDetail.username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_accept);

        //firebase連接
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        //訂單資料位置
        DatabaseReference customerRef = database.getReference("order/"+name+"/1/customer");
        TextView customer = (TextView) findViewById(R.id.customer);
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
        //食物資料位置
        DatabaseReference foodRef = database.getReference("order/"+name+"/1/food");
        ListView lvOrder = this.findViewById(R.id.lv_order);
        ArrayList<OrderItem> orderList = new ArrayList<OrderItem>();
        foodRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String food=ds.child("food").getValue().toString();
                    String number=ds.child("number").getValue().toString();
                    String remark=ds.child("remark").getValue().toString();
                    orderList.add(new OrderItem(number,food,remark));
                }
                 OrderArrayAdapter adapter = new OrderArrayAdapter(ordersAcceptActivity.this, R.layout.listitem_order, orderList);
                  lvOrder.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }


}