package fcu.app.foodturtle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CouponArrayAdapter extends ArrayAdapter<CouponItem> {

	private Context context;
	private List<CouponItem> couponItemItems;

	public CouponArrayAdapter(@NonNull Context context, int resource, List<CouponItem> couponItemItems) {
		super(context, resource, couponItemItems);
		this.context = context;
		this.couponItemItems = couponItemItems;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

		LayoutInflater inflater = LayoutInflater.from(this.context);
		LinearLayout itemLayout = null;

		if (convertView == null) {
			itemLayout = (LinearLayout) inflater.inflate(R.layout.listitem_coupon, null);
		} else {
			itemLayout = (LinearLayout) convertView;
		}

		CouponItem item = couponItemItems.get(position);


		TextView tvName = itemLayout.findViewById(R.id.coupon_name);
		tvName.setText(item.getName());

		TextView tvMoney = itemLayout.findViewById(R.id.coupon_money);
		String str="$"+item.getMoney();
		tvMoney.setText(str);

		TextView tvCode = itemLayout.findViewById(R.id.coupon_code);
		tvCode.setText(item.getCode());

		TextView tvDescription = itemLayout.findViewById(R.id.coupon_description);
		tvDescription.setText(item.getDescription());

		TextView tvDate = itemLayout.findViewById(R.id.coupon_date);
		String strDate="有效日至 "+item.getDate();
		tvDate.setText(strDate);


		return itemLayout;
	}

}

