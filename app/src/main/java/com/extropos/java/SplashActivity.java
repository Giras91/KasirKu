package com.extropos.java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.extropos.java.sqlite.DatabaseHelper;
import com.extropos.java.sqlite.DatabaseManager;
import com.extropos.java.utils.Shared;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// Initialize Shared utilities (fonts, preferences, etc.)
		Shared.initialize(this);

		// Initialize databases
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		DatabaseManager.initializeInstance(dbHelper);
		DatabaseManager.initializeRoomInstance(this);

		// Simple delay then proceed to activation
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, ActivationActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish();
			}
		}, 3000);
	}
}
