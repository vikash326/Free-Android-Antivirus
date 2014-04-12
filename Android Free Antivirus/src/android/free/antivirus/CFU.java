package android.free.antivirus;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

public class CFU extends IntentService {

	private String response;
	private String PREFS_NAME = "VX";

	public CFU() {
		super("CFU");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		try {

			if (CC.getConnectivityStatus(CFU.this) != 0) {

				URL url;
				HttpURLConnection conn;

				try {
					url = new URL(
							"http://rinixmobilesecurity.com/updates/getLatestVersion.php");

					String param = "who="
							+ URLEncoder.encode("rinix_user", "UTF-8");

					conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");

					conn.setFixedLengthStreamingMode(param.getBytes().length);
					conn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					PrintWriter out = new PrintWriter(conn.getOutputStream());
					out.print(param);
					out.close();

					Scanner inStream = new Scanner(conn.getInputStream());

					while (inStream.hasNextLine())
						response += (inStream.nextLine());
					uvc();

				} catch (MalformedURLException ex) {
					return;
				} catch (IOException ex) {
					return;
				}
			}

		} catch (Exception e) {

			return;

		}

	}

	private void uvc() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor saveSettings = settings.edit();
		saveSettings.putString("DEF_LATEST", response);
		saveSettings.commit();
	}

}
