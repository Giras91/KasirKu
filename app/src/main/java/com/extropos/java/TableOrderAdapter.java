package com.extropos.java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.extropos.java.entity.Order;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class TableOrderAdapter extends BaseAdapter {
    
    private Context context;
    private ArrayList<Order> orders;
    private LayoutInflater inflater;
    private NumberFormat currency;
    private SimpleDateFormat dateFormat;
    
    public TableOrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
        this.currency = NumberFormat.getCurrencyInstance(new Locale("ms", "MY"));
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }
    
    @Override
    public int getCount() {
        return orders != null ? orders.size() : 0;
    }
    
    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_table_order, parent, false);
            holder = new ViewHolder();
            holder.tvOrderId = convertView.findViewById(R.id.tvOrderId);
            holder.tvOrderAmount = convertView.findViewById(R.id.tvOrderAmount);
            holder.tvOrderDate = convertView.findViewById(R.id.tvOrderDate);
            holder.tvOrderStatus = convertView.findViewById(R.id.tvOrderStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        Order order = orders.get(position);
        
        holder.tvOrderId.setText(order.getOrderID());
        holder.tvOrderAmount.setText(currency.format(order.getAmount()));
        
        if (order.getCreatedOn() != null) {
            holder.tvOrderDate.setText(dateFormat.format(order.getCreatedOn()));
        } else {
            holder.tvOrderDate.setText("N/A");
        }
        
        String status = order.getStatus();
        if (status == null || status.isEmpty()) {
            holder.tvOrderStatus.setText("Open");
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(android.R.color.holo_orange_dark));
        } else if ("paid".equalsIgnoreCase(status)) {
            holder.tvOrderStatus.setText("Paid");
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.tvOrderStatus.setText(status);
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(android.R.color.black));
        }
        
        return convertView;
    }
    
    private static class ViewHolder {
        TextView tvOrderId;
        TextView tvOrderAmount;
        TextView tvOrderDate;
        TextView tvOrderStatus;
    }
}