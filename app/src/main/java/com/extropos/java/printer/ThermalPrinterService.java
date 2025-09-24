package com.extropos.java.printer;

import android.content.Context;
import android.util.Log;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.dantsu.escposprinter.connection.usb.UsbConnection;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;

/**
 * Enhanced printer service using ESCPOS-ThermalPrinter-Android library by DantSu.
 * This complements the existing EscPosPrinterService with better USB and Bluetooth support.
 * Supports Bluetooth, USB, and Network printing with advanced formatting capabilities.
 */
public class ThermalPrinterService {
    private static final String TAG = "ThermalPrinterService";

    private Context context;
    private EscPosPrinter printer;
    private DeviceConnection connection;
    private boolean isConnected = false;

    public ThermalPrinterService(Context context) {
        this.context = context;
    }

    /**
     * Connect to a Bluetooth printer
     * @param deviceAddress Bluetooth MAC address
     * @return true if connection successful
     */
    public boolean connectBluetooth(String deviceAddress) {
        try {
            Log.d(TAG, "Connecting to Bluetooth printer: " + deviceAddress);
            connection = new BluetoothConnection(deviceAddress);
            printer = new EscPosPrinter(connection, 203, 48f, 32);
            isConnected = true;
            Log.d(TAG, "Successfully connected to Bluetooth printer");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to connect to Bluetooth printer", e);
            isConnected = false;
            return false;
        }
    }

    /**
     * Connect to a USB printer
     * @return true if connection successful
     */
    public boolean connectUsb() {
        try {
            Log.d(TAG, "Connecting to USB printer");
            connection = new UsbConnection(context);
            printer = new EscPosPrinter(connection, 203, 48f, 32);
            isConnected = true;
            Log.d(TAG, "Successfully connected to USB printer");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to connect to USB printer", e);
            isConnected = false;
            return false;
        }
    }

    /**
     * Connect to a Network printer
     * @param ipAddress IP address of the printer
     * @param port Port number (usually 9100)
     * @return true if connection successful
     */
    public boolean connectNetwork(String ipAddress, int port) {
        try {
            Log.d(TAG, "Connecting to Network printer: " + ipAddress + ":" + port);
            connection = new TcpConnection(ipAddress, port);
            printer = new EscPosPrinter(connection, 203, 48f, 32);
            isConnected = true;
            Log.d(TAG, "Successfully connected to Network printer");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to connect to Network printer", e);
            isConnected = false;
            return false;
        }
    }

    /**
     * Disconnect from the printer
     */
    public void disconnect() {
        try {
            if (connection != null) {
                connection.disconnect();
            }
            isConnected = false;
            Log.d(TAG, "Disconnected from printer");
        } catch (Exception e) {
            Log.e(TAG, "Error disconnecting from printer", e);
        }
    }

    /**
     * Check if printer is connected
     * @return true if connected
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Print a test receipt
     * @return true if printing successful
     */
    public boolean printTest() {
        if (!isConnected || printer == null) {
            Log.e(TAG, "Printer not connected");
            return false;
        }

        try {
            printer.printFormattedText(
                "[C]<u><b>Test Print</b></u>\n" +
                "[L]\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]<b>Printer Test Successful!</b>\n" +
                "[L]\n" +
                "[L]Date: [DATE]\n" +
                "[L]Time: [TIME]\n" +
                "[L]\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n"
            );
            Log.d(TAG, "Test print completed successfully");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to print test", e);
            return false;
        }
    }

    /**
     * Print a formatted receipt
     * @param receiptData The receipt data to print
     * @return true if printing successful
     */
    public boolean printReceipt(String receiptData) {
        if (!isConnected || printer == null) {
            Log.e(TAG, "Printer not connected");
            return false;
        }

        try {
            printer.printFormattedText(receiptData);
            Log.d(TAG, "Receipt printed successfully");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to print receipt", e);
            return false;
        }
    }

    /**
     * Print raw text
     * @param text The text to print
     * @return true if printing successful
     */
    public boolean printText(String text) {
        if (!isConnected || printer == null) {
            Log.e(TAG, "Printer not connected");
            return false;
        }

        try {
            printer.printFormattedText("[L]" + text + "\n");
            Log.d(TAG, "Text printed successfully");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to print text", e);
            return false;
        }
    }

    /**
     * Get printer status
     * @return status message
     */
    public String getPrinterStatus() {
        if (!isConnected) {
            return "Not Connected";
        }

        try {
            // Try to get printer status if available
            return "Connected - Ready";
        } catch (Exception e) {
            return "Connected - Status Unknown";
        }
    }
}