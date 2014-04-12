package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;

public class VxR extends Service {
    private NotificationManager nm;
    private static boolean isRunning = false;
	private int i;
	private int in;
	public static List<App> a;
	public static List <String>fP;
	private int appSize;
	private int indices[][];

    ArrayList<Messenger> mClients = new ArrayList<Messenger>(); 
    int mValue = 0;
    static final int MSG_REGISTER_CLIENT = 1;
    static final int MSG_UNREGISTER_CLIENT = 2;
    static final int MSG_SET_INT_VALUE = 3;
    static final int MSG_SET_STRING_VALUE = 4;
    final Messenger mMessenger = new Messenger(new IncomingHandler()); 
    
    private PowerManager.WakeLock wl;
    private PowerManager pm;


    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
    class IncomingHandler extends Handler { 
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_REGISTER_CLIENT:
                mClients.add(msg.replyTo);
                break;
            case MSG_UNREGISTER_CLIENT:
                mClients.remove(msg.replyTo);
                break;
            default:
                super.handleMessage(msg);
            }
        }
    }
    private void sendStrMessageToUI(boolean st) {
        for (int j=mClients.size()-1; j>=0; j--) {
            try {

                Bundle b = new Bundle();
				b.putBoolean("c",st);
				b.putInt("i",(int)((i+1)*100)/appSize);
				b.putInt("in",in);
				if(i < a.size())
		        {
					b.putString("f",a.get(i).getTitle());
		        }
		        else if(i >= a.size())
		        {
		        	b.putString("f",fP.get(i+fP.size()-appSize));
		        }				
				b.putString("cnt",i+1 + " / " + appSize);
				b.putBoolean("finished", st);
                Message msg = Message.obtain(null, MSG_SET_STRING_VALUE);
                msg.setData(b);
                mClients.get(j).send(msg);
                

            } catch (RemoteException e) {
               
                mClients.remove(j);
            }
        }
    }
	

    @Override
    public void onCreate() {
        super.onCreate();
        
        L load = new L(this);
        
        E.i(this);
         
        showNotification();
        new qS().execute();
        isRunning = true;
    }
    private void showNotification() {
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, "Scanning your phone...", System.currentTimeMillis());
        Intent i = new Intent(this, P.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, 0);
        notification.setLatestEventInfo(this, "Rinix", "Scanning for threats", contentIntent);
        nm.notify(1, notification);
    }
    
    private void showResult(boolean b) {
    	nm.cancel(1);
        
    	Intent localIntent = new Intent(this,SST.class);
    	localIntent.putExtra("state", in);
    	startService(localIntent);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY; 
    }

    public static boolean isRunning()
    {
        return isRunning;
    }
	
		  
	class qS extends AsyncTask<String, String, String> {
		
		private int rCode;
		
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Rinix WakeLock");
		wl.acquire();
		
		appSize = a.size();
		if(P.scanSDCard)
			appSize += fP.size();
				
		i = 0;
		in = 0;
		indices = new int[appSize][3];
	}


	@Override
	protected String doInBackground(String... u) {
		
		while(i<appSize)
		{	
			if(isCancelled())
			{
				break;
			}
		sendStrMessageToUI(false);
		if(i < a.size())
		{
				rCode = FSE.scanFile(a.get(i).getsourcePath());

			if(rCode > 0)
			{
				indices[in][0] = i;
				indices[in][1] = rCode;
				indices[in][2] = 0;
				in++;
			}
		}
		else if(i >= a.size())
		{
				rCode = FSE.scanFile(fP.get(i+fP.size()-appSize));	

			if(rCode > 0)
			{
				indices[in][0] = i+fP.size()-appSize;
				indices[in][1] = rCode;
				indices[in][2] = 1;
				in++;
			}
		}
        i++;		
		}
	return null;
	}
	

	@Override
	protected void onPostExecute(String s) {
		i--;
		u();
		sendStrMessageToUI(true);
		if(in > 0)
			showResult(false);
		else
			showResult(true);
		wl.release();
		stopSelf();
	}
	
}
	
    private void u()
    {
    	SiD d = new SiD(this);
    	
	    try {
	        
	        d.createDataBase();
	         
	        } catch (IOException ioe) {
	         
	        throw new Error("Unable to create database");
	         
	        }
	    
	    try {
	        
	        d.openDataBase();
	         
	        }catch(SQLException sqle){
	         
	        throw sqle;
	         
	        }
	    
	    H h = new H(this);
    	
    	for(int i=0;i<in;i++)
    	{
    		String title;
    		
    		if(indices[i][2] == 0)
    		{
    			title = d.getVname(indices[i][1]);
        		h.newInfection(new In(title,a.get(indices[i][0]).getPackageName(),a.get(indices[i][0]).getVersionName(),0,a.get(indices[i][0]).getsourcePath(),null,null));
    		}
    		else if(indices[i][2] == 1)
    		{
        		title = d.getVname(indices[i][1]);
        		String name = fP.get(indices[i][0]).substring(fP.get(indices[i][0]).lastIndexOf("/")+1);
        		h.newInfection(new In(title,name,null,1,fP.get(indices[i][0]),null,null));   			
    		}

    	}
    	
    	Date now = new Date();
    	h.updateLastScan(i+1, in, 0, "0", now.toLocaleString());
    	
    	d.close();
    	h.close();

    }
	
	
    @Override
    public void onDestroy() {
        super.onDestroy();
        E.releaseAll();
        isRunning = false;
    }
}