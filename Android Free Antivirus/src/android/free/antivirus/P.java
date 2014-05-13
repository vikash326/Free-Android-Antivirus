package android.free.antivirus;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.free.antivirus.widgets.DawlProgressBar;
import free.an.droid.antivirus.rinix.R;

public class P extends Activity {

	private DawlProgressBar progressBar;
	private TextView fileScanned;
	private TextView totalScanned;
	private TextView totalThreats;
	private ImageView hideScan;
	private ImageView buyPremium;
	private PM pm;
	public static boolean scanSDCard;
	private boolean isFirstRun;
	private BroadcastReceiver receiver;
	private IntentFilter filter;
	public static boolean isVisible = false;
	private AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_p);

		isVisible = true;

		Intent caller = getIntent();
		scanSDCard = caller.getBooleanExtra("SDCARD", false);
		isFirstRun = caller.getBooleanExtra("FIRSTRUN", true);

		progressBar = (DawlProgressBar) findViewById(R.id.dawlProgressBar);
		fileScanned = (TextView) findViewById(R.id.p_scanfile);
		totalScanned = (TextView) findViewById(R.id.p_scan_filecount);
		totalThreats = (TextView) findViewById(R.id.p_scan_threatcount);
		hideScan = (ImageView) findViewById(R.id.p_hidescan);

		W w = new W(fileScanned, totalScanned, totalThreats, progressBar);
		pm = new PM(w, this);
		if (!VxR.isRunning())
			pm.s();
		else {
			pm.doBindService();
			pm.Progress();
		}

		hideScan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				state();
			}
		});

		filter = new IntentFilter("android.free.antivirus.completedscan");

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				die();

			}
		};
		registerReceiver(receiver, filter);

		if (isFirstRun) {
			SharedPreferences settings;
			String PREFS_NAME = "VX";
			settings = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("VS_FIRSTRUN", false);
			editor.putString("DEF_CURRENT", "2.2.3");
			editor.commit();
		}

		adView = new AdView(this, AdSize.BANNER, "a1530adf9a86b20");
		LinearLayout layout = (LinearLayout) findViewById(R.id.p_adContainer);
		layout.addView(adView);
		AdRequest adRequest = new AdRequest();
		adView.loadAd(adRequest);

	}

	@Override
	public void onBackPressed() {
		state();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
		isVisible = false;
	}

	private void state() {
		pm.State();
		finish();
	}

	private void die() {
		finish();
	}
}
