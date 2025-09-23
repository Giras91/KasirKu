package com.extropos.java.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.widget.Toast;

import com.extropos.java.ActivationActivity;
import com.extropos.java.R;
import com.extropos.java.utils.Constants;
import com.extropos.java.utils.Shared;

public class Checker {
	private Activity context;

	public Checker(Activity context) {
		this.context = context;
	}

	public boolean cek() {
		return this.cek(false);
	}

	public boolean cek(boolean isRedirect) {

		String imei = "";
		String device_id = "";
		
		// Get ANDROID_ID (no permission required)
		device_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		
		// Use ANDROID_ID as device identifier (IMEI requires system permissions not available to regular apps)
		imei = device_id;

		boolean hasActive = false;
		if(!Shared.read(Constants.KEY_SETTING_CASHIER_SN, "").equals(""))
		{
			if(Shared.read(Constants.KEY_SETTING_DEVICE_ID, "").equals(device_id) && Shared.read(Constants.KEY_SETTING_IME, "").equals(imei))
			{
				hasActive = true;
			}
		}

		if(isRedirect)
		{
			Intent intent = new Intent(context, ActivationActivity.class);
			context.startActivity(intent);
			context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			context.finish();
			Toast.makeText(context.getApplicationContext(), context.getString(R.string.activation_message), Toast.LENGTH_LONG).show();
		}
		
		return hasActive;
		
	}
}
