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
import fcu.app.foodturtle.item.OrderItem;

public class OrderArrayAdapter extends ArrayAdapter<OrderItem> {
    private Context context;
    private List<OrderItem> orderItems;


    public OrderArrayAdapter(@NonNull Context context, int resource, List<OrderItem> orderItems) {
        super(context, resource, orderItems);
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        LinearLayout itemLayout = null;

        if (convertView == null) {
            itemLayout = (LinearLayout) inflater.inflate(R.layout.listitem_order, null);
        } else {
            itemLayout = (LinearLayout) convertView;
        }

        OrderItem item = orderItems.get(position);


        TextView tvNumber = itemLayout.findViewById(R.id.order_number);
        tvNumber.setText(item.getOrderNumber());



        TextView tvFood = itemLayout.findViewById(R.id.order_food);
        tvFood.setText(item.getOrderFood());


        TextView tvRemark = itemLayout.findViewById(R.id.order_remark);
        tvRemark.setText(item.getOrderRemark());



        return itemLayout;
    }
}