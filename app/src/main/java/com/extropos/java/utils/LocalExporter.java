package com.extropos.java.utils;

import android.content.Context;
import android.os.Environment;

import com.extropos.java.models.DailyReportData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LocalExporter {
    
    private static final String REPORTS_FOLDER = "QuickCashReports";
    
    public static String exportDailyReport(Context context, ArrayList<DailyReportData> reportData, Date selectedDate) {
        try {
            File reportsDir = getReportsDirectory();
            if (reportsDir == null) {
                return null;
            }
            
            SimpleDateFormat fileFormat = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
            String fileName = "daily_report_" + fileFormat.format(selectedDate) + ".txt";
            File reportFile = new File(reportsDir, fileName);
            
            FileWriter writer = new FileWriter(reportFile);
            writer.write(generateDailyReportContent(reportData, selectedDate));
            writer.close();
            
            return reportFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String exportHourlyReport(Context context, ArrayList<DailyReportData> reportData, Date selectedDate) {
        try {
            File reportsDir = getReportsDirectory();
            if (reportsDir == null) {
                return null;
            }
            
            SimpleDateFormat fileFormat = new SimpleDateFormat("yyyy_MM_dd_HH", Locale.getDefault());
            String fileName = "hourly_report_" + fileFormat.format(selectedDate) + ".txt";
            File reportFile = new File(reportsDir, fileName);
            
            FileWriter writer = new FileWriter(reportFile);
            writer.write(generateHourlyReportContent(reportData, selectedDate));
            writer.close();
            
            return reportFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String exportMonthlyReport(Context context, ArrayList<DailyReportData> reportData, Date selectedDate) {
        try {
            File reportsDir = getReportsDirectory();
            if (reportsDir == null) {
                return null;
            }
            
            SimpleDateFormat fileFormat = new SimpleDateFormat("yyyy_MM", Locale.getDefault());
            String fileName = "monthly_report_" + fileFormat.format(selectedDate) + ".txt";
            File reportFile = new File(reportsDir, fileName);
            
            FileWriter writer = new FileWriter(reportFile);
            writer.write(generateMonthlyReportContent(reportData, selectedDate));
            writer.close();
            
            return reportFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static File getReportsDirectory() {
        File externalStorage = Environment.getExternalStorageDirectory();
        File reportsDir = new File(externalStorage, REPORTS_FOLDER);
        
        if (!reportsDir.exists()) {
            if (!reportsDir.mkdirs()) {
                return null;
            }
        }
        
        return reportsDir;
    }
    
    private static String generateDailyReportContent(ArrayList<DailyReportData> reportData, Date selectedDate) {
        StringBuilder content = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        
        content.append("QUICK CASH - DAILY REPORT\n");
        content.append("========================\n\n");
        content.append("Date: ").append(dateFormat.format(selectedDate)).append("\n");
        content.append("Generated: ").append(dateFormat.format(new Date())).append("\n\n");
        
        double totalSales = 0;
        int totalOrders = 0;
        double totalTax = 0;
        
        for (DailyReportData data : reportData) {
            totalSales += data.getTotalAmount();
            totalOrders += data.getOrderCount();
            totalTax += data.getTaxAmount();
        }
        
        String currency = Shared.read(Constants.KEY_SETTING_CURRENCY_SYMBOL, Constants.VAL_DEFAULT_CURRENCY_SYMBOL);
        content.append("SUMMARY\n");
        content.append("-------\n");
        content.append("Total Sales: ").append(currency).append(String.format("%.2f", totalSales)).append("\n");
        content.append("Total Orders: ").append(totalOrders).append("\n");
        content.append("Total Tax: ").append(currency).append(String.format("%.2f", totalTax)).append("\n\n");
        
        content.append("DETAILED BREAKDOWN\n");
        content.append("------------------\n");
        
        for (DailyReportData data : reportData) {
            content.append("Period: ").append(data.getPeriod()).append("\n");
            content.append("Orders: ").append(data.getOrderCount()).append("\n");
            content.append("Amount: ").append(currency).append(String.format("%.2f", data.getTotalAmount())).append("\n");
            content.append("Tax: ").append(currency).append(String.format("%.2f", data.getTaxAmount())).append("\n");
            if (data.getPaymentMethod() != null) {
                content.append("Payment: ").append(data.getPaymentMethod()).append("\n");
            }
            content.append("----\n");
        }
        
        content.append("\nGenerated by Quick Cash POS System\n");
        content.append("Extro Target SDN BHD\n");
        
        return content.toString();
    }
    
    private static String generateHourlyReportContent(ArrayList<DailyReportData> reportData, Date selectedDate) {
        StringBuilder content = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        
        content.append("QUICK CASH - HOURLY REPORT\n");
        content.append("==========================\n\n");
        content.append("Date: ").append(dateFormat.format(selectedDate)).append("\n\n");
        
        for (DailyReportData data : reportData) {
            content.append("Hour: ").append(data.getPeriod()).append("\n");
            content.append("Sales: ").append(String.format("%.2f", data.getTotalAmount())).append("\n");
            content.append("Orders: ").append(data.getOrderCount()).append("\n");
            content.append("----\n");
        }
        
        content.append("\nGenerated by Quick Cash POS System\n");
        return content.toString();
    }
    
    private static String generateMonthlyReportContent(ArrayList<DailyReportData> reportData, Date selectedDate) {
        StringBuilder content = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
        
        content.append("QUICK CASH - MONTHLY REPORT\n");
        content.append("===========================\n\n");
        content.append("Month: ").append(dateFormat.format(selectedDate)).append("\n\n");
        
        double totalSales = 0;
        int totalOrders = 0;
        
        for (DailyReportData data : reportData) {
            totalSales += data.getTotalAmount();
            totalOrders += data.getOrderCount();
        }
        
        String currency = Shared.read(Constants.KEY_SETTING_CURRENCY_SYMBOL, Constants.VAL_DEFAULT_CURRENCY_SYMBOL);
        content.append("Monthly Sales: ").append(currency).append(String.format("%.2f", totalSales)).append("\n");
        content.append("Monthly Orders: ").append(totalOrders).append("\n\n");
        
        content.append("DAILY BREAKDOWN\n");
        content.append("---------------\n");
        
        for (DailyReportData data : reportData) {
            content.append("Date: ").append(data.getPeriod()).append("\n");
            content.append("Sales: ").append(currency).append(String.format("%.2f", data.getTotalAmount())).append("\n");
            content.append("Orders: ").append(data.getOrderCount()).append("\n");
            content.append("----\n");
        }
        
        content.append("\nGenerated by Quick Cash POS System\n");
        return content.toString();
    }
}