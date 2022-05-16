package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BrowseActivity extends AppCompatActivity {

    public static boolean VALID_USER = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

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