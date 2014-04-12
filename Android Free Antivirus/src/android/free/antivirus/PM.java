package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.io.File;
import java.util.List;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.TextView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;

import android.free.antivirus.widgets.DawlProgressBar;

public class PM {
	
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private DawlProgressBar pb;
	
	private Context cx;
	private List<App> a;
	private A tA;
	private List <String>fP;
	private File file[];
	
	private Messenger mService = null;
	private boolean mIsBound;
	
	
	public PM(W w, Context c)
	{
		this.tv1 = w.getTV1();
		this.tv2 = w.getTV2();
		this.tv3 = w.getTV3();
		this.pb = w.getPB();
		
		this.cx = c;
		
	}
	
	
	public void s()
	{
		new S().execute();

	}
	
	
	  
	class S extends AsyncTask<String, String, String> {
		
		
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		

	}


	@Override
	protected String doInBackground(String... u) {
		
		tA = new A(cx);
		a = tA.lIA(false);
		VxR.a = a;
		
		if(P.scanSDCard)
		{
			file = Environment.getExternalStorageDirectory().listFiles();
			tA.rFF(file);
			fP = tA.lES();
			VxR.fP = fP;
		}
		
    	return null;
	}
	
	@Override
	protected void onPostExecute(String s) {
	
		cx.startService(new Intent(cx, VxR.class));
		doBindService();
		
	}

}
	
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case VxR.MSG_SET_STRING_VALUE:
            	progress(msg.getData().getInt("i"),msg.getData().getString("f"),msg.getData().getString("cnt"),msg.getData().getInt("in"),msg.getData().getBoolean("finished"));	
                break;
            default:
                super.handleMessage(msg);
            }
        }
    }
	
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            try {
                Message msg = Message.obtain(null, VxR.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);
            } catch (RemoteException e) {
                
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            
            mService = null;
        }
    };
	
    public void doBindService() {

        cx.bindService(new Intent(cx, VxR.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
	
    void doUnbindService() {
        if (mIsBound) {
            
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null, VxR.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                } catch (RemoteException e) {
                    
                }
            }
            
            cx.unbindService(mConnection);
            mIsBound = false;
        }
    }
	
	
	private void progress(int arg1,String arg2,String arg3,int arg4,boolean st)
	{
		if(!st)
		{
		pb.setProgress(arg1);
        tv1.setText(arg2);
        tv2.setText(arg3);
        tv3.setText(arg4+"");
		}
		else
		{
			if(P.isVisible)
			{
				doUnbindService();
	            cx.stopService(new Intent(cx, VxR.class));	
			}
            
            Intent localIntent1 = new Intent(cx,M.class);
            Intent localIntent2 = new Intent("android.free.antivirus.completedscan");
			if(arg4 > 0)
			{
				localIntent1.putExtra("STAT", false);
				localIntent1.putExtra("SCANRESULT", true);
				localIntent1.putExtra("CNT", arg4);
			}
			else
			{
				localIntent1.putExtra("STAT", true);
				localIntent1.putExtra("SCANRESULT", true);
			}
			
			
			
			if(P.isVisible)
			{
				cx.sendBroadcast(localIntent2);
				cx.startActivity(localIntent1);
			}
			else
			{
				SharedPreferences settings;
				String PREFS_NAME = "VX";
				settings = cx.getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor saveSettings = settings.edit();
				saveSettings.putBoolean("STAT", false);
				saveSettings.putInt("CNT", arg4);
				saveSettings.commit();
			}
		}
	}
	
	public void Progress()
	{
		SharedPreferences settings;
		String PREFS_NAME = "FSPrefs";
		settings = cx.getSharedPreferences(PREFS_NAME, 0);
		pb.setProgress(settings.getInt("progress",0));
		tv1.setText(settings.getString("file",""));
        tv2.setText(settings.getString("count",""));
        tv3.setText(settings.getString("in",""));
	}
	
	public void State()
	{
		SharedPreferences settings;
		String PREFS_NAME = "FSPrefs";
		settings = cx.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor saveSettings = settings.edit();
		saveSettings.putInt("progress", pb.getProgress());
		saveSettings.putString("file", tv1.getText().toString());
		saveSettings.putString("count", tv2.getText().toString());
		saveSettings.putString("in", tv3.getText().toString());
		saveSettings.commit();
		
	}
	
}
