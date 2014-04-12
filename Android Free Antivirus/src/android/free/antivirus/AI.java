package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.net.Uri;
import android.os.SystemClock;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;


public class AI extends BroadcastReceiver {
	
	public SharedPreferences settings;
	public boolean state = true;
	public static final String PREFS_NAME = "MainPrefs";
	String appFile;
	SiD db;
	H db1;
	int rCode;
	String message;
	
	Intent i;
	PendingIntent destIntent;
		
	List<StStruct> st1;
	List<StStruct> st2;


 @Override
 public void onReceive(Context context, Intent intent) {
	 
	  Uri app = intent.getData();
	  final String appName = app.toString();
	  final Context c = context;
	  String action = intent.getAction();
	  
	  if(action.equals("android.intent.action.PACKAGE_REMOVED"))
	  {
	        Intent localIntent1 = new Intent("android.free.antivirus.package_removed");
	        localIntent1.putExtra("package_name", appName.substring(appName.lastIndexOf(":")+1));
	        context.sendBroadcast(localIntent1);
	        
	        if(!ST.isUp)
	        {
	        	Intent localIntent2 = new Intent("android.free.antivirus.remove_package");
		        localIntent2.putExtra("package_name", appName.substring(appName.lastIndexOf(":")+1));
		        context.sendBroadcast(localIntent2);
	        }
	  }
	  
	  else if(action.equals("android.intent.action.PACKAGE_INSTALL") || action.equals("android.intent.action.PACKAGE_ADDED"))
	  {
			 settings = context.getSharedPreferences(PREFS_NAME, 0);
			 boolean b = settings.getBoolean("PROTECTION_STATE", true);
			 if(b)
			 {
	  
				 i = new Intent(context, M.class);

				 NotificationManager notiManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
     
				 Notification noti = new Notification(R.drawable.ic_launcher,"Scanning " + appName, System.currentTimeMillis());
 
  
				 destIntent = PendingIntent.getActivity(context, 0, i, 0);

				 noti.setLatestEventInfo(context, "Rinix", "Scannning " + appName, destIntent);
   
				 notiManager.notify(1, noti);
	  
				 db = new SiD(context);
		
				 try {
	        
					 db.createDataBase();
	        
				 } catch (IOException ioe) {
	         
					 throw new Error("Unable to create database");
	         
				 }
	    
				 try {
	        
					 db.openDataBase();
	         
				 }catch(SQLException sqle){
	         
					 throw sqle;
	         
				 }
				 
				 notiManager.cancel(1);
	    
	    		
		st1 = db.getStrings(0x0a786564);
		st2 = db.getStrings(0x464c457f);
		
		E.st1 = st1;
		E.st2 = st2;
	  
	  
	   try
		  {
			
			  PackageManager pm = c.getPackageManager();
			  String packageName = appName.substring(appName.lastIndexOf(":")+1);
			  ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
			  appFile = appInfo.sourceDir;
			  
			  E.i(c);
			  
			  rCode = E.scanApk(appFile);
			  db1 = new H(context);
			  if(rCode > 0)
			   {
				   String title = db.getVname(rCode);
				   db1.lastAppIsThreat(new In(title, packageName, "1.0.0", 0, appFile, null,null));
				   db1.newInfection(new In(title, packageName, "1.0.0", 0, appFile, null,null));
				   i = new Intent(context, LPT.class);
				   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				   context.startActivity(i);
		           Intent k = new Intent(Intent.ACTION_DELETE);
		           k.setData(Uri.parse("package:" + packageName));
				   destIntent = PendingIntent.getActivity(context, 0, k, 0);
				   message = appName + " is malicious. Click to remove.";
				   noti = new Notification(R.drawable.ic_launcher,message, System.currentTimeMillis());
				   noti.setLatestEventInfo(c, "Rinix", message, destIntent); 
				   noti.flags = Notification.FLAG_AUTO_CANCEL;
				   notiManager.notify(1, noti);
				   SystemClock.sleep(2000);
				   notiManager.cancel(1);
				   
			   }
			  else
			  {
				  db1.LastSafeApp(packageName);
				  i = new Intent(context, LPS.class);
				  destIntent = PendingIntent.getActivity(context, 0, i, 0);
				  message = "Scan completed. " + appName;
				  noti = new Notification(R.drawable.ic_launcher,message, System.currentTimeMillis());
				  noti.setLatestEventInfo(c, "Rinix", message, destIntent);   
				  notiManager.notify(1, noti);
				  notiManager.cancel(1);
			  }
			  
			  E.releaseAll();
			  db.close();
			  db1.close();
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	   
	 
	 }
	 }
   
 }

}
