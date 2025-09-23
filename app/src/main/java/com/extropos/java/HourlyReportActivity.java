package com.extropos.java;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.extropos.java.utils.Shared;

public class HourlyReportActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_report);
        
        TextView titleText = (TextView) findViewById(R.id.textReportTitle);
        titleText.setText("Hourly Report");
        
        if (Shared.openSansLight != null) {
            titleText.setTypeface(Shared.openSansLight);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}