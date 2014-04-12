package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.database.SQLException;

public class L {
	
	private SiD d;
	
	private List<StStruct> st1;
	private List<StStruct> st2;
	
	public L(Context t)
	{
		d = new SiD(t);
		ret();
	}
	
	private void ret()
	{
		
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
	    		
		st1 = d.getStrings(0x0a786564);
		st2 = d.getStrings(0x464c457f);
		
		map();
		
	}
	
	private void map()
	{
		
		E.st1 = st1;
		E.st2 = st2;
		d.close();
		
	}
	
	public void rel()
	{
		st1.clear();
		st2.clear();
		
	}

}
