package android.free.antivirus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import free.an.droid.antivirus.rinix.R;

public class M extends Activity {

	private SharedPreferences settings;
	private static final String PREFS_NAME = "VX";
	private static boolean includeSDCard;
	private boolean isFirstRun;
	private H db;
	private int infCount;

	private ImageView ok;
	private TextView dashBoardTxt;
	private int ok_width;

	private String c_v;
	private String a_v;

	private TextView tv1;
	private TextView tv2;
	private ImageView iv;
	private ProgressBar pb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_m);

		includeSDCard = true;

		if (VxR.isRunning()) {
			Intent i;
			i = new Intent(M.this, P.class);
			startActivity(i);
			finish();
		}

		db = new H(this);

		dashBoardTxt = (TextView) findViewById(R.id.m_protectionStatTxt);
		TextView scanBtnTxt = (TextView) findViewById(R.id.m_scanBtnTxt);
		TextView scanSDCardTxt = (TextView) findViewById(R.id.m_scanSDCardTxt1);
		ok = (ImageView) findViewById(R.id.m_protectionStatImg);
		ImageView scanNow = (ImageView) findViewById(R.id.m_scan);
		ImageView showThreats = (ImageView) findViewById(R.id.m_imageView1);
		ImageView scanSDCard = (ImageView) findViewById(R.id.m_includeSDCardBg1);
		final ImageView right = (ImageView) findViewById(R.id.m_right1);
		ImageView buyPremium = (ImageView) findViewById(R.id.m_buy_premium);
		tv1 = (TextView) findViewById(R.id.m_stat_msg);
		tv2 = (TextView) findViewById(R.id.m_info_txt);
		iv = (ImageView) findViewById(R.id.m_info_btn);
		pb = (ProgressBar) findViewById(R.id.m_stat_progress);

		settings = getSharedPreferences(PREFS_NAME, 0);
		isFirstRun = settings.getBoolean("VS_FIRSTRUN", true);

		final ViewTreeObserver observer = ok.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				ok_width = ok.getHeight();

				if (ok_width > 0)
					adjustPadding();
			}
		});

		if (!isFirstRun) {

			LScan ls = db.getLastScan();
			infCount = db.getInfectionsCount();

			if (infCount <= 0) {

				ok.setVisibility(View.VISIBLE);

				if (getIntent().getBooleanExtra("SCANRESULT", false))
					dashBoardTxt.setText(getResources().getString(
							R.string.dash_Scanned)
							+ " "
							+ ls.getFiles()
							+ " "
							+ getResources().getString(R.string.dash_apps)
							+ "\n"
							+ getResources().getString(
									R.string.dash_NoThreatFound));
				else
					dashBoardTxt.setText(getResources().getString(
							R.string.dash_LastScan)
							+ " "
							+ ls.getWhen()
							+ "\n"
							+ getResources().getString(R.string.dash_Scanned)
							+ " "
							+ ls.getFiles()
							+ " "
							+ getResources().getString(R.string.dash_apps)
							+ "\n"
							+ getResources().getString(
									R.string.dash_NoThreatFound));
				scanSDCardTxt.setVisibility(View.VISIBLE);
				scanSDCard.setVisibility(View.VISIBLE);
				right.setVisibility(View.VISIBLE);

			} else {
				ok.setImageResource(R.drawable.protection_threat);
				ok.setVisibility(View.VISIBLE);

				if (getIntent().getBooleanExtra("SCANRESULT", false)) {
					if (infCount == 1)
						dashBoardTxt.setText(getResources().getString(
								R.string.dash_Scanned)
								+ " "
								+ ls.getFiles()
								+ " "
								+ getResources().getString(R.string.dash_apps)
								+ "\n"
								+ infCount
								+ " "
								+ getResources().getString(
										R.string.dash_ThreatFound));
					else
						dashBoardTxt.setText(getResources().getString(
								R.string.dash_Scanned)
								+ " "
								+ ls.getFiles()
								+ " "
								+ getResources().getString(R.string.dash_apps)
								+ "\n"
								+ infCount
								+ " "
								+ getResources().getString(
										R.string.dash_ThreatFound));
				} else {
					if (infCount == 1)
						dashBoardTxt.setText(getResources().getString(
								R.string.dash_LastScan)
								+ " "
								+ ls.getWhen()
								+ "\n"
								+ getResources().getString(
										R.string.dash_Scanned)
								+ " "
								+ ls.getFiles()
								+ " "
								+ getResources().getString(R.string.dash_apps)
								+ "\n"
								+ infCount
								+ " "
								+ getResources().getString(
										R.string.dash_ThreatFound));
					else
						dashBoardTxt.setText(getResources().getString(
								R.string.dash_LastScan)
								+ " "
								+ ls.getWhen()
								+ "\n"
								+ getResources().getString(
										R.string.dash_Scanned)
								+ " "
								+ ls.getFiles()
								+ " "
								+ getResources().getString(R.string.dash_apps)
								+ "\n"
								+ infCount
								+ " "
								+ getResources().getString(
										R.string.dash_ThreatFound));
				}

			}

		}

		scanNow.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent i;

				i = new Intent(M.this, P.class);
				i.putExtra("SDCARD", includeSDCard);
				i.putExtra("FIRSTRUN", isFirstRun);
				startActivity(i);
				finish();
			}

		});

		showThreats.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (infCount > 0) {
					startActivity(new Intent(M.this, ST.class));
					finish();
				}
			}

		});

		scanSDCard.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (includeSDCard) {
					right.setVisibility(View.GONE);
					includeSDCard = false;
				} else {
					right.setVisibility(View.VISIBLE);
					includeSDCard = true;
				}
			}

		});

		buyPremium.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("market://details?id=antivirus.security.android.rinix"));
				startActivity(marketIntent);
			}

		});

		c_v = settings.getString("DEF_CURRENT", "");
		a_v = settings.getString("DEF_LATEST", "");

		// if (c_v.length() > 1 && a_v.length() > 1) {
		// if (!c_v.equals(a_v)) {
		iv.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//new dS().execute();
			}

		});

		// }
		// }

	}

	private int getPixels(Context c, int dp) {
		Resources resources = c.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	private int getDP(Context c, int px) {
		Resources resources = c.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = (px * 160f) / metrics.densityDpi;
		return (int) dp;
	}

	private float getSP(Context c, float px) {
		float scaledDensity = c.getResources().getDisplayMetrics().scaledDensity;
		return px / scaledDensity;
	}

	private void adjustPadding() {
		int currentPadding = getDP(this, dashBoardTxt.getPaddingLeft());
		if (currentPadding <= 10) {
			dashBoardTxt.setPadding(currentPadding + ok_width - 5, 0, 10, 0);
			float currentTextSize = getSP(M.this, dashBoardTxt.getTextSize());
			dashBoardTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,
					currentTextSize - 2);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_m, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_main_about:

			startActivity(new Intent(getApplicationContext(), About.class));
			break;

		}

		return true;
	}

	class dS extends AsyncTask<String, String, String> {

		long percentDownloaded = 0;
		private String file_url = "http://vicatec.com/rinix/android/vxoid.bin";
		private PowerManager.WakeLock wl;
		private PowerManager pm;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
					"Rinix WakeLock");
			wl.acquire();
			tv1.setVisibility(View.VISIBLE);
			pb.setVisibility(View.VISIBLE);
			tv1.setText(getResources().getString(R.string.up_def_init));

		}

		@Override
		protected String doInBackground(String... u) {

			Log.d("M", "Starting..");

			try {
				if (CC.getConnectivityStatus(M.this) != 0) {
					int count;
					URL url = new URL(file_url);
					URLConnection conection = url.openConnection();
					conection.connect();

					int lenghtOfFile = conection.getContentLength();

					InputStream input = new BufferedInputStream(
							url.openStream(), lenghtOfFile);

					OutputStream output = new FileOutputStream(
							Environment.getExternalStorageDirectory()
									+ "/rinix/temp-f/vxoid.bin");

					byte data[] = new byte[1024];

					long total = 0;

					while ((count = input.read(data)) != -1) {
						total += count;
						output.write(data, 0, count);
						percentDownloaded = (total / lenghtOfFile) * 100;

						if (total < lenghtOfFile) {
							tv1.setText(percentDownloaded
									+ getResources().getString(
											R.string.up_def_percent));
						} else {

							tv1.setText(getResources().getString(
									R.string.up_def_success));
						}
					}

					H db = new H(M.this);
					db.updateStat_date(System.currentTimeMillis() + "");
					db.close();

					output.flush();
					output.close();
					input.close();
					pb.setVisibility(View.GONE);
					tv1.setVisibility(View.GONE);
					tv2.setVisibility(View.GONE);
					iv.setVisibility(View.GONE);
				} else {
					tv1.setText(getResources().getString(R.string.m_def_no_net));
				}

			} catch (Exception e) {
				//tv1.setText(getResources().getString(R.string.up_def_error));
				Log.d("DLS", "Suiciding..");
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String s) {
			Log.d("M", "Stopping..");
			try {
				copyBin();
			} catch (Exception e) {
				return;
			}
			wl.release();
			uFlag();
		}

		private void copyBin() throws IOException {

			try {
				InputStream in = new FileInputStream(
						Environment.getExternalStorageDirectory()
								+ "/rinix/temp-f/vxoid.bin");

				Dfr.d(in, "/data/data/" + getPackageName()
						+ "/databases/vxoid.bin");
				in.close();

				File d = new File(Environment.getExternalStorageDirectory()
						+ "/rinix/temp-f/vxoid.bin");
				//d.delete();

			} catch (Exception e) {
				return;
			}

		}

		private void uFlag() {
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			String defLatest = settings.getString("DEF_LATEST", "");
			SharedPreferences.Editor saveSettings = settings.edit();
			saveSettings.putString("DEF_CURRENT", defLatest);
			saveSettings.commit();
		}

	}

}
