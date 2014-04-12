package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class Traces {
	
	public static String S(String args) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("SHA1");
		
		FileInputStream fis = new FileInputStream(args);
		
		byte[] dataBytes = new byte[8192];
		int nRead = 0;
		
		while((nRead = fis.read(dataBytes)) != -1)
		{
			md.update(dataBytes, 0, nRead);
		};
		
		byte[] mdBytes = md.digest();
		
		StringBuffer sb = new StringBuffer("");
		
		for(int i=0;i<mdBytes.length;i++)
		{
			sb.append(Integer.toString((mdBytes[i]&0xff) + 0x100,16).substring(1));
		}
		
		return sb.toString();
	}

}

