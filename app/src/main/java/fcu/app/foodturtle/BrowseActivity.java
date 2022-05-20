package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fcu.app.foodturtle.ArrayAdapter.StoreArrayAdapter;
import fcu.app.foodturtle.item.StoreItem;

public class BrowseActivity extends AppCompatActivity {

	public static boolean VALID_USER = true;
//	public static boolean VALID_USER = false;

		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference storesRef = database.getReference("/stores");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

			ListView lvStore = this.findViewById(R.id.lv_store);
			ArrayList<StoreItem> storeList = new ArrayList<StoreItem>();
			storeList.add(new StoreItem(R.drawable.test_view,"好吃火鍋" ,30,"4.5(5K+)","台式"));
			storeList.add(new StoreItem(R.drawable.test_view,"好吃火鍋" ,30,"4.5(5K+)","台式"));
			storeList.add(new StoreItem(R.drawable.test_view,"好吃火鍋" ,30,"4.5(5K+)","台式"));
			storeList.add(new StoreItem(R.drawable.test_view,"好吃火鍋" ,30,"4.5(5K+)","台式"));
			storeList.add(new StoreItem(R.drawable.test_view,"好吃火鍋" ,30,"4.5(5K+)","台式"));
			storeList.add(new StoreItem(R.drawable.test_view,"好吃火鍋" ,30,"4.5(5K+)","台式"));

			storesRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
						System.out.println("Data:"+storeSnapshot);
						String[] item = {"imgResId", "storeName", "storeFreight", "storeFraction","storeType"};
						String[] storeList={"imgResId", "storeName", "storeFreight", "storeFraction","storeType"};
						int top=-1;
						for(String i:item) {
							System.out.println(i + ":" + storeSnapshot.child(i).getValue());
							storeList[++top]=storeSnapshot.child(i).getValue().toString();
						}

						System.out.println(storeList[0]);
//					StoreItem store = storeSnapshot.getValue(StoreItem.class);
//					System.out.println("??"+store.getStoreName()+":"+store.getStoreFraction()+":"+store.getStoreFreight()+":"+store.getStoreType());
					}
				}
				@Override
				public void onCancelled(DatabaseError error) {
					// Failed to read value
				}
			});


        StoreArrayAdapter adapter = new StoreArrayAdapter(this, R.layout.listitem_store, storeList);
        lvStore.setAdapter(adapter);



        if(!VALID_USER) {
            Intent intent = new Intent();
            intent.setClass(this,MainActivity.class);
            startActivity(intent);
        }



    }
    public void shopcarmenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,ShopcarActivity.class);
        startActivity(intent);
    }
    public void orderordermenu(View v) {
        Intent intent = new Intent();
        intent.setClass(this,orderOrderActivity.class);
        startActivity(intent);
    }
    public void Setmenu(View v) {
            Intent intent = new Intent();
            intent.setClass(this,SettingActivity.class);
            startActivity(intent);
    }

}