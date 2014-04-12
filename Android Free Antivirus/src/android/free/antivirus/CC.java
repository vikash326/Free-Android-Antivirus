package android.free.antivirus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class CC {

	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;

	public static int getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
				Log.d("CC",TYPE_WIFI+"");
				return TYPE_WIFI;
			}

			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
				Log.d("CC",TYPE_MOBILE+"");
				return TYPE_MOBILE;
			}
		}
		
		Log.d("CC",TYPE_NOT_CONNECTED+"");

		return TYPE_NOT_CONNECTED;
	}
}
