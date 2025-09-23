package com.extropos.java;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class TenderReportActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_report);
        TextView titleText = (TextView) findViewById(R.id.textReportTitle);
        titleText.setText("Tender Report");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}