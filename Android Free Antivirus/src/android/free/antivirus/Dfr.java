package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.util.zip.InflaterInputStream;

public class Dfr
{
	public static void d(InputStream is, String path) throws Exception
	{
		InflaterInputStream iis = new InflaterInputStream(is);
		FileOutputStream fos2 = new FileOutputStream(path);
		doCopy(iis,fos2);		
	}
	
	private static void doCopy(InputStream is, OutputStream os) throws Exception
	{
		byte[] data = new byte[8192];
		int oneByte;
		BufferedInputStream bis = new BufferedInputStream(is);
		while((oneByte = bis.read(data, 0, 8192)) != -1)
		{
			os.write(data);
		}
		os.close();
		is.close();
	}

}

