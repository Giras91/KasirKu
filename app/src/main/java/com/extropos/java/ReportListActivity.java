package com.extropos.java;

public class ReportListActivity extends android.app.Activity {

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        
        // Initialize list view
        android.widget.ListView listView = (android.widget.ListView) findViewById(R.id.listViewReport);
        
        // Create simple report items
        String[] reportTypes = {
            "Daily Report",
            "Hourly Report", 
            "Monthly Report",
            "Tax Report",
            "Tender Report",
            "Bill Report",
            "Category & Product Report"
        };
        
        // Create simple adapter
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<String>(
            this, 
            android.R.layout.simple_list_item_1, 
            reportTypes
        );
        
        listView.setAdapter(adapter);
        
        // Set click listener
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                android.content.Intent intent = null;
                
                switch (position) {
                    case 0: // Daily Report
                        intent = new android.content.Intent(ReportListActivity.this, DailyReportActivity.class);
                        break;
                    case 1: // Hourly Report
                        intent = new android.content.Intent(ReportListActivity.this, HourlyReportActivity.class);
                        break;
                    case 2: // Monthly Report
                        intent = new android.content.Intent(ReportListActivity.this, MonthlyReportActivity.class);
                        break;
                    case 3: // Tax Report
                        intent = new android.content.Intent(ReportListActivity.this, TaxReportActivity.class);
                        break;
                    case 4: // Tender Report
                        intent = new android.content.Intent(ReportListActivity.this, TenderReportActivity.class);
                        break;
                    case 5: // Bill Report
                        intent = new android.content.Intent(ReportListActivity.this, BillReportActivity.class);
                        break;
                    case 6: // Category & Product Report
                        intent = new android.content.Intent(ReportListActivity.this, ProductReportActivity.class);
                        break;
                }
                
                if (intent != null) {
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}