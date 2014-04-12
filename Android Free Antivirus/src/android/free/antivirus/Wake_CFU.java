package android.free.antivirus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Wake_CFU extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Intent i = new Intent(context, CFU.class);
		context.startService(i);
	}

}
