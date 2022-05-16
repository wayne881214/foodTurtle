package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fcu.app.foodturtle.ArrayAdapter.StoreArrayAdapter;
import fcu.app.foodturtle.item.StoreItem;

public class BrowseActivity extends AppCompatActivity {

    public static boolean VALID_USER = false;

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