package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;

public class AVStat {
	
	int files;
	int inf;
	int del;
	String date_time;
	
	public AVStat(int t1, int t2, int t3, String t4)
	{
		this.files = t1;
		this.inf = t2;
		this.del = t3;
		this.date_time = t4;
	}
	
	public int getFiles()
	{
		return this.files;
	}
	
	public int getInf()
	{
		return this.inf;
	}
	
	public int getDel()
	{
		return this.del;
	}
	
	public String getDate()
	{
		return this.date_time;
	}

}
