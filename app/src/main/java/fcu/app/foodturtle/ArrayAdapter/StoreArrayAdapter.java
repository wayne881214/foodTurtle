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
import fcu.app.foodturtle.item.StoreItem;

public class StoreArrayAdapter extends ArrayAdapter<StoreItem> {
	private Context context;
	private List<StoreItem> storeItems;


	public StoreArrayAdapter(@NonNull Context context, int resource, List<StoreItem> storeItems) {
		super(context, resource, storeItems);
		this.context = context;
		this.storeItems = storeItems;
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

		StoreItem item = storeItems.get(position);


		TextView tvName = itemLayout.findViewById(R.id.store_name);
		tvName.setText(item.getStoreName());


		ImageView iv = itemLayout.findViewById(R.id.store_img);
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
