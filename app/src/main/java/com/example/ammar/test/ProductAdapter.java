package com.example.ammar.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter {

    Context mContext;
    ArrayList<Product> dataList;
    public ProductAdapter(@NonNull Context context, ArrayList<Product> ObjectList) {
        super(context, R.layout.item_product_list,ObjectList);
        mContext = context;
        dataList = ObjectList;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_product_list, parent, false);
            viewHolder.device = (TextView) convertView.findViewById(R.id.deviceName);
            viewHolder.quantity =(TextView)convertView.findViewById(R.id.deviceQty);
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.

        viewHolder.device.setText(dataList.get(position).getProductName());
        viewHolder.quantity.setText(dataList.get(position).getProductQuantity()+"");

        // Return the completed view to render on screen
        return convertView;
    }

    private static class ViewHolder {

        TextView device;
        TextView quantity;

    }
}
