package com.extropos.java.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.extropos.java.R;
import com.extropos.java.models.ReportItem;
import com.extropos.java.utils.Shared;

import java.util.ArrayList;

public class ReportListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ReportItem> reportList;
    private LayoutInflater inflater;

    public ReportListAdapter(Context context, ArrayList<ReportItem> reportList) {
        this.context = context;
        this.reportList = reportList;
        this.inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.item_report_list, parent, false);
            holder = new ViewHolder();
            holder.titleText = (TextView) convertView.findViewById(R.id.textReportTitle);
            holder.descriptionText = (TextView) convertView.findViewById(R.id.textReportDescription);
            holder.iconImage = (ImageView) convertView.findViewById(R.id.imageReportIcon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReportItem report = reportList.get(position);
        
        holder.titleText.setText(report.getTitle());
        holder.descriptionText.setText(report.getDescription());
        holder.iconImage.setImageResource(report.getIconResId());
        
        // Set custom fonts if available
        if (Shared.openSansLight != null) {
            holder.titleText.setTypeface(Shared.openSansLight);
            holder.descriptionText.setTypeface(Shared.openSansLight);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView titleText;
        TextView descriptionText;
        ImageView iconImage;
    }
}