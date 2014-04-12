package android.free.antivirus;

import android.widget.TextView;
import android.free.antivirus.widgets.DawlProgressBar;
import free.an.droid.antivirus.rinix.R;

public class W {
	
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private DawlProgressBar pb;
	
	public W(TextView t1, TextView t2, TextView t3, DawlProgressBar p)
	{
		this.tv1 = t1;
		this.tv2 = t2;
		this.tv3 = t3;
		this.pb = p;

	}
	
	public TextView getTV1()
	{
		return this.tv1;
	}
	
	public TextView getTV2()
	{
		return this.tv2;
	}
	
	public TextView getTV3()
	{
		return this.tv3;
	}
	
	public DawlProgressBar getPB()
	{
		return this.pb;
	}
	

}
