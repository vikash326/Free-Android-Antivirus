package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.zip.CRC32;

public class FSE {
	
	private static int firstDword;
	private static FileInputStream toScan;
	private static int rCode;
	private static CRC32 crc;
	private static int cnt;
	
	public static int scanFile(String path)
	{
		rCode = 0;
		try
		{
			toScan = new FileInputStream(path);
		}
		catch(Exception e)
		{
			return 0;
		}
		
		try
		{
			
		BufferedInputStream bis = new BufferedInputStream(toScan);    
		bis.mark(4);
        byte buffer[] = new byte[4];
		bis.read(buffer, 0, 4);
		bis.reset();
		firstDword = ((0xFF & buffer[3]) << 24) | ((0xFF & buffer[2]) << 16) |((0xFF & buffer[1]) << 8) | (0xFF & buffer[0]);
		
		if(firstDword == 0x04034b50)
		{
			rCode = E.scanApk(path);
		}
		else if(firstDword == 0x0a786564)
		{
			crc = new CRC32();
			while ((cnt = bis.read()) != -1) {
			crc.update(cnt);
			}
			bis.reset();
			rCode = E.scanDex(bis,(int)crc.getValue());
		}
		else if(firstDword == 0x464c457f)
		{
			crc = new CRC32();
			while ((cnt = bis.read()) != -1) {
			crc.update(cnt);
			}
			bis.reset();
			rCode = E.scanELF(bis,(int)crc.getValue());
		}
		else if(firstDword == 0x214f3558)
		{
			rCode = E.scanDOS(bis);
		}
		else
		{
			rCode = 0;
		}
			bis.close();
		
		}
		catch (Exception e)
		{
			return 0;
			
		}
		
		return rCode;
	}

}

