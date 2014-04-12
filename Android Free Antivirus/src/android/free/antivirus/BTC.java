package android.free.antivirus;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import free.an.droid.antivirus.rinix.R;


public class BTC extends BroadcastReceiver {
	
	private H db;
	private String message ="";
	private Intent i;

 @Override
 public void onReceive(Context context, Intent intent) {
	 
   final Context c = context;

   NotificationManager notifyManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
     
   Notification notify = new Notification(R.drawable.ic_launcher,"Protected by Rinix Mobile Security.", System.currentTimeMillis());
   
   i = new Intent(context, M.class);
   
   PendingIntent destIntent = PendingIntent.getActivity(context, 0, i, 0);

   notify.setLatestEventInfo(context, "Rinix", message, destIntent);
   
   notifyManager.notify(2, notify); 
   
   notifyManager.cancel(2);
   
   db = new H(c);
   
   int count = db.getInfectionsCount();
    
   if(count > 0)
   {
	   message = count + " infections found. Click to remove.";
	   i = new Intent(context, ST.class);
	      
	   notify = new Notification(R.drawable.ic_launcher,message, System.currentTimeMillis());
	   
     	notify.defaults |= Notification.DEFAULT_VIBRATE;
     	notify.defaults |= Notification.DEFAULT_SOUND;
     	notify.defaults |= Notification.DEFAULT_LIGHTS;
	   
	   destIntent = PendingIntent.getActivity(context, 0, i, 0);

	   notify.setLatestEventInfo(context, "Rinix", message, destIntent);
	   
	   notify.flags = Notification.FLAG_AUTO_CANCEL;
	   	   
	   notifyManager.notify(2, notify); 
	   
   }
   else
   {
	   message = "Your device is protected.";
	   i = new Intent(context, M.class);
	   
	   notify = new Notification(R.drawable.ic_launcher,message, System.currentTimeMillis());
	   
    	notify.defaults |= Notification.DEFAULT_VIBRATE;
    	notify.defaults |= Notification.DEFAULT_SOUND;
    	notify.defaults |= Notification.DEFAULT_LIGHTS;
	   
	   destIntent = PendingIntent.getActivity(context, 0, i, 0);

	   notify.setLatestEventInfo(context, "Rinix", message, destIntent);
	   
	   
	   notifyManager.notify(2, notify); 
	   
	   notifyManager.cancel(2);
	   
   }
   
	 db.close();
   
 }

}
