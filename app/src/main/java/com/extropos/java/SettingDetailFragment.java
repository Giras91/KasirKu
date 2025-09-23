package com.extropos.java;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.extropos.java.dummy.SettingContent;

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
		// Basic setup for printer settings - detailed implementation would go here
		TextView title = rootView.findViewById(R.id.tvPrinterTitle);
		if (title != null) {
			title.setText("Configure your receipt printer settings");
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
}
