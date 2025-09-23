package com.extropos.java.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.extropos.java.R;
import com.extropos.java.models.DailyReportData;
import com.extropos.java.utils.Constants;
import com.extropos.java.utils.Shared;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DailyReportAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DailyReportData> reportList;
    private LayoutInflater inflater;
    private SimpleDateFormat timeFormat;

    public DailyReportAdapter(Context context, ArrayList<DailyReportData> reportList) {
        this.context = context;
        this.reportList = reportList;
        this.inflater = LayoutInflater.from(context);
        this.timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    }

    @Override
    public int getCount() {
        return reportList.size();
    }

    @Override
    public Object getItem(int position) {
        return reportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_daily_report, parent, false);
            holder = new ViewHolder();
            holder.textPeriod = (TextView) convertView.findViewById(R.id.textPeriod);
            holder.textOrderCount = (TextView) convertView.findViewById(R.id.textOrderCount);
            holder.textAmount = (TextView) convertView.findViewById(R.id.textAmount);
            holder.textTax = (TextView) convertView.findViewById(R.id.textTax);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DailyReportData report = reportList.get(position);
        String currency = Shared.read(Constants.KEY_SETTING_CURRENCY_SYMBOL, Constants.VAL_DEFAULT_CURRENCY_SYMBOL);
        
        holder.textPeriod.setText(report.getPeriod());
        holder.textOrderCount.setText("Orders: " + report.getOrderCount());
        holder.textAmount.setText("Sales: " + currency + String.format("%.2f", report.getTotalAmount()));
        holder.textTax.setText("Tax: " + currency + String.format("%.2f", report.getTaxAmount()));
        
        // Set custom fonts if available
        if (Shared.openSansLight != null) {
            holder.textPeriod.setTypeface(Shared.openSansLight);
            holder.textOrderCount.setTypeface(Shared.openSansLight);
            holder.textAmount.setTypeface(Shared.openSansLight);
            holder.textTax.setTypeface(Shared.openSansLight);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textPeriod;
        TextView textOrderCount;
        TextView textAmount;
        TextView textTax;
    }
}