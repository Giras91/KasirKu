package com.extropos.java;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.extropos.java.dummy.SettingContent;
import com.extropos.java.printer.DeviceListActivity;
import com.extropos.java.printer.EscPosPrinterService;
import com.extropos.java.printer.ThermalPrinterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A fragment representing a single Setting detail screen. This fragment is
 * either contained in a {@link SettingListActivity} in two-pane mode (on
 * tablets) or a {@link SettingDetailActivity} on handsets.
 */
public class SettingDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private SettingContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SettingDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = SettingContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		
		// Show different layouts based on setting type
		if (mItem != null) {
			String settingId = getArguments().getString(ARG_ITEM_ID);
			
			switch (settingId) {
				case "1": // Printer Selection
					rootView = inflater.inflate(R.layout.fragment_printer_settings, container, false);
					setupPrinterSettings(rootView);
					break;
				case "2": // User Management
					rootView = inflater.inflate(R.layout.fragment_user_management, container, false);
					setupUserManagement(rootView);
					break;
				case "3": // License Activation
					rootView = inflater.inflate(R.layout.fragment_license_settings, container, false);
					setupLicenseSettings(rootView);
					break;
				case "4": // Malaysian E-Invoice Setup
					rootView = inflater.inflate(R.layout.fragment_einvoice_settings, container, false);
					setupEInvoiceSettings(rootView);
					break;
				case "5": // Kitchen Printer Setup
					rootView = inflater.inflate(R.layout.fragment_kitchen_printer_settings, container, false);
					setupKitchenPrinterSettings(rootView);
					break;
				case "9": // Table Management
					rootView = inflater.inflate(R.layout.fragment_table_management, container, false);
					setupTableManagement(rootView);
					break;
				default:
					rootView = inflater.inflate(R.layout.fragment_setting_detail, container, false);
					((TextView) rootView.findViewById(R.id.setting_detail)).setText(mItem.content);
					break;
			}
		} else {
			rootView = inflater.inflate(R.layout.fragment_setting_detail, container, false);
		}

		return rootView;
	}
	
	private void setupPrinterSettings(View rootView) {
		// Initialize UI components
		RadioGroup rgPrinterLibrary = rootView.findViewById(R.id.rgPrinterLibrary);
		RadioGroup rgPrinterType = rootView.findViewById(R.id.rgPrinterType);
		LinearLayout llNetworkSettings = rootView.findViewById(R.id.llNetworkSettings);
		LinearLayout llBluetoothSettings = rootView.findViewById(R.id.llBluetoothSettings);
		EditText etNetworkIP = rootView.findViewById(R.id.etNetworkIP);
		EditText etNetworkPort = rootView.findViewById(R.id.etNetworkPort);
		Spinner spBluetoothDevices = rootView.findViewById(R.id.spBluetoothDevices);
		Button btnScanBluetooth = rootView.findViewById(R.id.btnScanBluetooth);
		Button btnTestPrint = rootView.findViewById(R.id.btnTestPrint);
		Button btnSave = rootView.findViewById(R.id.btnSave);

		// Load saved settings
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		String printerLibrary = prefs.getString("printer_library", "escpos_coffee");
		String printerType = prefs.getString("printer_type", "usb");
		String networkIP = prefs.getString("network_ip", "");
		String networkPort = prefs.getString("network_port", "9100");
		String bluetoothAddress = prefs.getString("bluetooth_address", "");

		// Set initial values
		etNetworkIP.setText(networkIP);
		etNetworkPort.setText(networkPort);

		// Set printer library radio button
		switch (printerLibrary) {
			case "thermal_printer":
				rgPrinterLibrary.check(R.id.rbThermalPrinter);
				break;
			default: // escpos_coffee
				rgPrinterLibrary.check(R.id.rbEscPosCoffee);
				break;
		}

		// Set printer type radio button
		switch (printerType) {
			case "network":
				rgPrinterType.check(R.id.rbNetwork);
				llNetworkSettings.setVisibility(View.VISIBLE);
				llBluetoothSettings.setVisibility(View.GONE);
				break;
			case "bluetooth":
				rgPrinterType.check(R.id.rbBluetooth);
				llNetworkSettings.setVisibility(View.GONE);
				llBluetoothSettings.setVisibility(View.VISIBLE);
				break;
			default: // usb
				rgPrinterType.check(R.id.rbUSB);
				llNetworkSettings.setVisibility(View.GONE);
				llBluetoothSettings.setVisibility(View.GONE);
				break;
		}

		// Setup printer library change listener
		rgPrinterLibrary.setOnCheckedChangeListener((group, checkedId) -> {
			// Could add library-specific UI changes here if needed
		});

		// Setup printer type change listener
		rgPrinterType.setOnCheckedChangeListener((group, checkedId) -> {
			if (checkedId == R.id.rbNetwork) {
				llNetworkSettings.setVisibility(View.VISIBLE);
				llBluetoothSettings.setVisibility(View.GONE);
			} else if (checkedId == R.id.rbBluetooth) {
				llNetworkSettings.setVisibility(View.GONE);
				llBluetoothSettings.setVisibility(View.VISIBLE);
				loadBluetoothDevices(spBluetoothDevices, bluetoothAddress);
			} else if (checkedId == R.id.rbUSB) {
				llNetworkSettings.setVisibility(View.GONE);
				llBluetoothSettings.setVisibility(View.GONE);
			}
		});

		// Setup Bluetooth scan button
		btnScanBluetooth.setOnClickListener(v -> {
			if (checkBluetoothPermissions()) {
				Intent intent = new Intent(getContext(), DeviceListActivity.class);
				startActivityForResult(intent, REQUEST_BLUETOOTH_DEVICE);
			}
		});

		// Setup test print button
		btnTestPrint.setOnClickListener(v -> testPrint());

		// Setup save button
		btnSave.setOnClickListener(v -> savePrinterSettings());

		// Load initial Bluetooth devices if Bluetooth is selected
		if ("bluetooth".equals(printerType)) {
			loadBluetoothDevices(spBluetoothDevices, bluetoothAddress);
		}
	}

	private static final int REQUEST_BLUETOOTH_DEVICE = 1;
	private Object printerService;

	private void loadBluetoothDevices(Spinner spinner, String selectedAddress) {
		if (!checkBluetoothPermissions()) {
			return;
		}

		try {
			BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (bluetoothAdapter == null) {
				Toast.makeText(getContext(), "Bluetooth not supported", Toast.LENGTH_SHORT).show();
				return;
			}

			Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
			List<String> deviceList = new ArrayList<>();
			List<String> addressList = new ArrayList<>();
			int selectedIndex = 0;

			deviceList.add("Select Bluetooth Device");
			addressList.add("");

			int index = 1;
			for (BluetoothDevice device : pairedDevices) {
				String deviceName = device.getName() + "\n" + device.getAddress();
				deviceList.add(deviceName);
				addressList.add(device.getAddress());
				if (device.getAddress().equals(selectedAddress)) {
					selectedIndex = index;
				}
				index++;
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
					android.R.layout.simple_spinner_item, deviceList);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			spinner.setSelection(selectedIndex);

		} catch (Exception e) {
			Log.e("PrinterSettings", "Error loading Bluetooth devices", e);
			Toast.makeText(getContext(), "Error loading Bluetooth devices", Toast.LENGTH_SHORT).show();
		}
	}

	private boolean checkBluetoothPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(getActivity(),
					new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT}, 1);
				return false;
			}
		}
		return true;
	}

	private void testPrint() {
		View rootView = getView();
		if (rootView == null) return;

		RadioGroup rgPrinterLibrary = rootView.findViewById(R.id.rgPrinterLibrary);
		int libraryCheckedId = rgPrinterLibrary.getCheckedRadioButtonId();
		boolean useThermalPrinter = (libraryCheckedId == R.id.rbThermalPrinter);

		RadioGroup rgPrinterType = rootView.findViewById(R.id.rgPrinterType);
		int checkedId = rgPrinterType.getCheckedRadioButtonId();

		// Instantiate the appropriate printer service
		if (useThermalPrinter) {
			printerService = new ThermalPrinterService(getContext());
		} else {
			printerService = new EscPosPrinterService(getContext());
		}

		boolean connected = false;

		try {
			if (checkedId == R.id.rbNetwork) {
				EditText etIP = rootView.findViewById(R.id.etNetworkIP);
				EditText etPort = rootView.findViewById(R.id.etNetworkPort);
				String ip = etIP.getText().toString().trim();
				String portStr = etPort.getText().toString().trim();
				int port = portStr.isEmpty() ? 9100 : Integer.parseInt(portStr);

				if (useThermalPrinter) {
					connected = ((ThermalPrinterService) printerService).connectNetwork(ip, port);
				} else {
					connected = ((EscPosPrinterService) printerService).connectNetwork(ip, port);
				}
			} else if (checkedId == R.id.rbBluetooth) {
				Spinner spDevices = rootView.findViewById(R.id.spBluetoothDevices);
				String selectedDevice = (String) spDevices.getSelectedItem();
				if (selectedDevice != null && !selectedDevice.equals("Select Bluetooth Device")) {
					String address = selectedDevice.substring(selectedDevice.lastIndexOf('\n') + 1);

					if (useThermalPrinter) {
						connected = ((ThermalPrinterService) printerService).connectBluetooth(address);
					} else {
						connected = ((EscPosPrinterService) printerService).connectBluetooth(address);
					}
				}
			} else if (checkedId == R.id.rbUSB) {
				if (useThermalPrinter) {
					// Use ThermalPrinterService for USB connection
					connected = ((ThermalPrinterService) printerService).connectUsb();
				} else {
					// EscPosPrinterService doesn't support USB yet
					Toast.makeText(getContext(), "USB printing not supported with EscPos Coffee library", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			if (connected) {
				boolean success;
				if (useThermalPrinter) {
					success = ((ThermalPrinterService) printerService).printTest();
				} else {
					success = ((EscPosPrinterService) printerService).printTest();
				}

				if (success) {
					Toast.makeText(getContext(), "Test print successful!", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getContext(), "Test print failed", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getContext(), "Failed to connect to printer", Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
			Log.e("PrinterSettings", "Error during test print", e);
			Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		} finally {
			if (printerService != null) {
				if (useThermalPrinter) {
					((ThermalPrinterService) printerService).disconnect();
				} else {
					((EscPosPrinterService) printerService).disconnect();
				}
			}
		}
	}

	private void savePrinterSettings() {
		View rootView = getView();
		if (rootView == null) return;

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		SharedPreferences.Editor editor = prefs.edit();

		// Save printer library selection
		RadioGroup rgPrinterLibrary = rootView.findViewById(R.id.rgPrinterLibrary);
		int libraryCheckedId = rgPrinterLibrary.getCheckedRadioButtonId();
		if (libraryCheckedId == R.id.rbThermalPrinter) {
			editor.putString("printer_library", "thermal_printer");
		} else {
			editor.putString("printer_library", "escpos_coffee");
		}

		RadioGroup rgPrinterType = rootView.findViewById(R.id.rgPrinterType);
		int checkedId = rgPrinterType.getCheckedRadioButtonId();

		if (checkedId == R.id.rbNetwork) {
			editor.putString("printer_type", "network");
			EditText etIP = rootView.findViewById(R.id.etNetworkIP);
			EditText etPort = rootView.findViewById(R.id.etNetworkPort);
			editor.putString("network_ip", etIP.getText().toString().trim());
			editor.putString("network_port", etPort.getText().toString().trim());
		} else if (checkedId == R.id.rbBluetooth) {
			editor.putString("printer_type", "bluetooth");
			Spinner spDevices = rootView.findViewById(R.id.spBluetoothDevices);
			String selectedDevice = (String) spDevices.getSelectedItem();
			if (selectedDevice != null && !selectedDevice.equals("Select Bluetooth Device")) {
				String address = selectedDevice.substring(selectedDevice.lastIndexOf('\n') + 1);
				editor.putString("bluetooth_address", address);
			}
		} else if (checkedId == R.id.rbUSB) {
			editor.putString("printer_type", "usb");
		}

		editor.apply();
		Toast.makeText(getContext(), "Printer settings saved", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_BLUETOOTH_DEVICE && resultCode == Activity.RESULT_OK) {
			String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
			if (address != null) {
				View rootView = getView();
				if (rootView != null) {
					Spinner spDevices = rootView.findViewById(R.id.spBluetoothDevices);
					loadBluetoothDevices(spDevices, address);
				}
			}
		}
	}
	
	private void setupUserManagement(View rootView) {
		// Basic setup for user management - detailed implementation would go here
		TextView title = rootView.findViewById(R.id.tvUserTitle);
		if (title != null) {
			title.setText("Manage system users and permissions");
		}
	}
	
	private void setupLicenseSettings(View rootView) {
		// Basic setup for license settings - detailed implementation would go here
		TextView title = rootView.findViewById(R.id.tvLicenseTitle);
		if (title != null) {
			title.setText("Activate and manage your software license");
		}
	}
	
	private void setupEInvoiceSettings(View rootView) {
		// Basic setup for e-invoice settings - detailed implementation would go here
		TextView title = rootView.findViewById(R.id.tvEInvoiceTitle);
		if (title != null) {
			title.setText("Configure Malaysian E-Invoice integration");
		}
	}
	
	private void setupKitchenPrinterSettings(View rootView) {
		// Basic setup for kitchen printer settings - detailed implementation would go here
		TextView title = rootView.findViewById(R.id.tvKitchenTitle);
		if (title != null) {
			title.setText("Setup kitchen order printing");
		}
	}
	
	private void setupTableManagement(View rootView) {
		// Setup table management interface
		TextView title = rootView.findViewById(R.id.tvTableTitle);
		if (title != null) {
			title.setText("Manage restaurant tables and seating");
		}
		
		// Add button to navigate to table management activity
		Button btnManageTables = rootView.findViewById(R.id.btnManageTables);
		if (btnManageTables != null) {
			btnManageTables.setOnClickListener(v -> {
				Intent intent = new Intent(getContext(), TableActivity.class);
				startActivity(intent);
			});
		}
	}
}
