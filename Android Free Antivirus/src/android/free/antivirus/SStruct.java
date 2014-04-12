package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;

public class SStruct {
	
	int vid;
	int s1;
	int[] s2;
	
	public SStruct(int t1, int[] t2, int t3)
	{
		this.s1 = t1;
		this.s2 = t2;
		this.vid = t3;
		
	}
	
	public void setVid(int t)
	{
		this.vid = t;
	}
	
	public void setS1(int t)
	{
		this.s1 = t;
	}
	
	public void setS2(int[] t)
	{
		this.s2 = t;
	}
	
	public int getVid()
	{
		return this.vid;
	}
	
	public int getS1()
	{
		return this.s1;
	}
	
	public int getS2(int i)
	{
		return this.s2[i];
	}
	
	public int[] getS2All()
	{
		return this.s2;
	}

}
