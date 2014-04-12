package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;

public class StStruct {
	
	byte[] s;
	int vid;
	
	public StStruct(byte[] t1, int t2)
	{
		this.s = t1;
		this.vid = t2;
	}
	
	public void setS(byte[] s)
	{
		this.s = s;
	}
	
	public void setVid(int t)
	{
		this.vid = t;
	}
	
	public byte[] getS()
	{
		return this.s;
	}
	
	public int getVid()
	{
		return this.vid;
	}
	

}
