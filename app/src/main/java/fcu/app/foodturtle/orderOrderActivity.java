package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fcu.app.foodturtle.ArrayAdapter.FoodArrayAdapter;
import fcu.app.foodturtle.ArrayAdapter.StoreArrayAdapter;
import fcu.app.foodturtle.item.FoodItem;
import fcu.app.foodturtle.item.StoreItem;

public class orderOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_order);

		ListView lvFood = this.findViewById(R.id.lv_food);
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
		foodList.add(new FoodItem(R.drawable.menu01,"牛肉漢堡" ,"附贈小杯飲料",30,"漢堡"));
		foodList.add(new FoodItem(R.drawable.menu01,"羊肉漢堡" ,"附贈小杯飲料",30,"漢堡"));
		foodList.add(new FoodItem(R.drawable.menu01,"豬漢堡" ,"附贈小杯飲料",30,"漢堡"));
		foodList.add(new FoodItem(R.drawable.menu01,"雞肉漢堡" ,"附贈小杯飲料",30,"漢堡"));
    }

    public void shopcarview(View v) {
        Intent intent = new Intent();
        intent.setClass(this,ShopcarActivity.class);
        startActivity(intent);
    }

}