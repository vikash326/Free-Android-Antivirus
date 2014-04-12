package android.free.antivirus;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import free.an.droid.antivirus.rinix.R;

public class SST extends IntentService {
	
    private NotificationManager nm;
	

  public SST() {
    super("SST");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
	  
	  try
	  {
	  
	  
      nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
      Notification notification;
      
      int b = intent.getIntExtra("state", 0);

      if(b == 0)
      {
      	notification = new Notification(R.drawable.ic_launcher, "Scan completed. Everything is ok.", System.currentTimeMillis());
      	notification.defaults |= Notification.DEFAULT_VIBRATE;
      	notification.defaults |= Notification.DEFAULT_SOUND;
      	notification.defaults |= Notification.DEFAULT_LIGHTS;
      	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, M.class), 0);
      	notification.setLatestEventInfo(this, "Rinix", "Everything is ok.", contentIntent);
      	notification.flags = Notification.FLAG_AUTO_CANCEL;
      	nm.notify(2, notification);
      	Thread.sleep(2000);
      	nm.cancel(2);
      }
      else
      {
      	notification = new Notification(R.drawable.ic_launcher, "Scan completed. Found security risks.", System.currentTimeMillis());
      	notification.defaults |= Notification.DEFAULT_VIBRATE;
      	notification.defaults |= Notification.DEFAULT_SOUND;
      	notification.defaults |= Notification.DEFAULT_LIGHTS;
      	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, ST.class), 0);
      	notification.setLatestEventInfo(this, "Rinix", b + " threats found. Click to remove", contentIntent);
      	notification.flags = Notification.FLAG_AUTO_CANCEL;
      	nm.notify(2, notification);
      }
                  
              } catch (java.lang.InterruptedException e) {
                  
              }

  }
} 

