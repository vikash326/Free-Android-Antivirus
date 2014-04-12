package android.free.antivirus;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Window;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import free.an.droid.antivirus.rinix.R;

public class LPS extends Activity {
	
	H db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        db = new H(this);
        final String packageName = db.getLastSafeApp();
        PackageManager manager = getApplicationContext().getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        Drawable ico = null;
        try
        {
         ico = manager.getActivityIcon(i);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        ContextThemeWrapper ctw = new ContextThemeWrapper( LPS.this, R.style.MyTheme );
		RD builder = new RD( ctw );
        
        String msg = "No threat found in this app";
        builder.setTitle(packageName);
        
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setIcon(ico);

        
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
          	 
        	  dialog.cancel();
              finish();
          	 
           }
        });
        
        Dialog dialog = builder.create();
        dialog.show();
        
    }

    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	db.close();
    }
    
}
