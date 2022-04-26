package fcu.app.foodturtle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getSupportActionBar() != null){
			getSupportActionBar().hide();
		}
//		setContentView(R.layout.activity_main);
			setContentView(R.layout.activity_browse);

	}

}