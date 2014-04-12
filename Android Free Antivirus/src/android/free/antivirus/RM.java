package android.free.antivirus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import free.an.droid.antivirus.rinix.R;

public class RM extends  BroadcastReceiver {
	
	private H db;	
	
	  @Override
	    public void onReceive(Context context, Intent paramIntent) {
		  
		  
		  
		  String action = paramIntent.getAction();
		  
		  if(action.equals("android.free.antivirus.remove_package"));
		  {	  
			  String packageName = paramIntent.getStringExtra("package_name");
			  db = new H(context);
			  db.threatDeleted(packageName);
			  
			  int count = db.getInfectionsCount();
			  
			  if(count < 1)
			  {
				   SharedPreferences settings;
				   String PREFS_NAME = "VX";
				   settings = context.getSharedPreferences(PREFS_NAME, 0);
				   SharedPreferences.Editor edit = settings.edit();
				   edit.putBoolean("STAT", true);
				   edit.commit();
			  }
			  
			  db.close();
		  }
	         	      	  
	  }
	  

}
