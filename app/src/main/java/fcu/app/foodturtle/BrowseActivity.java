package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fcu.app.foodturtle.ArrayAdapter.StoreArrayAdapter;
import fcu.app.foodturtle.item.CouponItem;
import fcu.app.foodturtle.item.StoreItem;

public class BrowseActivity extends AppCompatActivity {

	public static boolean VALID_USER = false;
	Context T=this;
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference storesRef = database.getReference("/stores");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

			ListView lvStore = this.findViewById(R.id.lv_store);
			ArrayList<StoreItem> storeList = new ArrayList<StoreItem>();
			storeList.add(new StoreItem(R.drawable.test_view,"好吃火鍋" ,30,"4.5(5K+)","台式"));
			System.out.println("!!!!!!!!!!!!!!!!!!!!R.drawable.test_view:"+R.drawable.test_view);

			String[] storeL={"imgResId", "storeName", "storeFreight", "storeFraction","storeType"};

			storesRef.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
						System.out.println("Data:"+storeSnapshot);

						String[] item = {"imgResId", "storeName", "storeFreight", "storeFraction","storeType"};
						int top=-1;
						for(String i:item) {
							System.out.println(i + ":" + storeSnapshot.child(i).getValue());
							storeL[++top]=storeSnapshot.child(i).getValue().toString();
						}

						storeList.add(new StoreItem(R.drawable.test_view,storeL[1],Integer.parseInt(storeL[2]),storeL[3],storeL[4]));

// 					物件傳入的方式有bug 先用array代替
//							StoreItem store = storeSnapshot.getValue(StoreItem.class);
//							storeList.add(store);
//						System.out.println("??"+store.getStoreName()+":"+store.getStoreFraction()+":"+store.getStoreFreight()+":"+store.getStoreType());
					}
					StoreArrayAdapter adapter = new StoreArrayAdapter(T, R.layout.listitem_store, storeList);
					lvStore.setAdapter(adapter);
				}
				@Override
				public void onCancelled(DatabaseError error) {
					// Failed to read value
				}
			});


        if(!VALID_USER) {
            Intent intent = new Intent();
            intent.setClass(this,MainActivity.class);
            startActivity(intent);
        }
			lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					System.out.println("position!!!!"+position);

					Intent intent = new Intent();
					intent.setClass(BrowseActivity.this, orderOrderActivity.class);
					intent.putExtra("商店名稱", storeList.get(position).getStoreName());
					startActivity(intent);
				}
			});


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