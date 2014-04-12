package android.free.antivirus;

import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Window;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import free.an.droid.antivirus.rinix.R;

public class LPT extends Activity {
	
	H db;
	BroadcastReceiver receiver;
	IntentFilter filter;
	AlertDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        db = new H(this);
        final In inf = db.getLastAppThreat();
        PackageManager manager = getApplicationContext().getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(inf.getPackageName());
        Drawable ico = null;
        try
        {
         ico = manager.getActivityIcon(i);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        ContextThemeWrapper ctw = new ContextThemeWrapper( LPT.this, R.style.MyTheme );
		RD builder = new RD( ctw );
        
        String msg = "Malware type : " + inf.getTitle() + "\n\n" + "Location : " + inf.getInstallDir();
        builder.setTitle(inf.getPackageName());
        
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setIcon(ico);

        
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
          	 
              Intent intent = new Intent(Intent.ACTION_DELETE);
              intent.setData(Uri.parse("package:" + inf.getPackageName()));
              startActivity(intent); 	 
           }
        });
        
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
              dialog.cancel();
              Toast.makeText(LPT.this, inf.getPackageName()+ " ignored", Toast.LENGTH_SHORT).show();
              finish();
           }
        });

        dialog = builder.create();
        dialog.show();
        
        
        filter = new IntentFilter("com.dawl.rinix.package_removed");

      	receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	
        	String lastThreatRemoved = intent.getStringExtra("package_name");
        	
        	if(lastThreatRemoved.equals(inf.getPackageName()))
        	{
        			
           		 	db.threatDeleted(inf.getPackageName());		
           		 	finish();
        	}
        	
        }
      };
      registerReceiver(receiver, filter); 
        
    }
    
    @Override
    protected void onDestroy() {
 	   
 	  super.onDestroy();
      unregisterReceiver(receiver);
      db.close();
      
    }
    
    @Override
    protected void onResume() {
 	   
 	   super.onResume();
 	   if(!dialog.isShowing())
 	   {
 		   dialog.show();
 	   }
 	   registerReceiver(receiver,filter);
      
    }


    
}
