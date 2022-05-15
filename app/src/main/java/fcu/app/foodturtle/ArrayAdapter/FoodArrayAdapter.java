package fcu.app.foodturtle.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fcu.app.foodturtle.R;
import fcu.app.foodturtle.item.FoodItem;

public class FoodArrayAdapter extends ArrayAdapter<FoodItem> {
	private Context context;
	private List<FoodItem> foodItems;


	public FoodArrayAdapter(@NonNull Context context, int resource, List<FoodItem> foodItems) {
		super(context, resource, foodItems);
		this.context = context;
		this.foodItems = foodItems;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(this.context);
		LinearLayout itemLayout = null;

		if (convertView == null) {
			itemLayout = (LinearLayout) inflater.inflate(R.layout.listitem_store, null);
		} else {
			itemLayout = (LinearLayout) convertView;
		}

	  FoodItem item = foodItems.get(position);


		TextView tvName = itemLayout.findViewById(R.id.food_name);
		tvName.setText(item.getStoreName());


		ImageView iv = itemLayout.findViewById(R.id.food_img);
		iv.setImageResource(item.getImgResId());



		TextView tvFreight = itemLayout.findViewById(R.id.store_freight);
		tvFreight.setText("$"+item.getStoreFreight());

		TextView tvFraction = itemLayout.findViewById(R.id.store_fraction);
		tvFraction.setText(item.getStoreFraction());

		TextView tvType = itemLayout.findViewById(R.id.stort_type);
		tvType.setText("$$$."+item.getStoreType());


		return itemLayout;
	}
}
