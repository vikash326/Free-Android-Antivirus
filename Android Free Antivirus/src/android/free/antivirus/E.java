	package android.free.antivirus;  
    import free.an.droid.antivirus.rinix.R;
import java.io.BufferedInputStream;
    import java.io.BufferedOutputStream;
    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.util.Enumeration;
    import java.util.zip.ZipEntry;
    import java.util.zip.ZipFile;
	import java.util.zip.CRC32;
	import java.util.List;
	import android.content.Context;
	import android.database.SQLException;
import android.os.Environment;
     
    public class E {
			
	public static List<StStruct> st1;
	public static List<StStruct> st2;
		
	private static int rCode;
	
	private static SiD d;
	
	public static String eP = Environment.getExternalStorageDirectory() + "/rinix-f/temp";
	private static String a = U1.R();
	
	
           
            public static int scanApk(String arg)
            {
            		 rCode = 0;
                                     
                     if(arg == null || arg.equals(""))
                     {
                            System.exit(0);
                     }
                                          
                    if(isApk(arg))
                    {
                    	recursiveScan(arg);
                    		
        				try
        				{      					
        					
        					File toDelete = new File(eP);
        					
        					deleteDir(toDelete);
        					
        				}
        				catch(IOException ioe)
                        {
        					return 0;
                        }
                    }
                    else
                    {
                    	rCode = 0;
                    }
					
					
					return rCode;
            }
            
            
            private static boolean isApk(String path)
            {
            	
            	try
            	{
            		
            		File fSourceZip = new File(path);
            		ZipFile zipFile = new ZipFile(fSourceZip);
            		ZipEntry e1 = zipFile.getEntry(a);
            		
            		if(e1 != null)
            		{
            			return true;
            		}
            		else
            		{
            			return false;
            		}
            		
            	}
            	catch(Exception e)
            	{
            		return false;
            	}
            	
            }
                      
    
            private static void recursiveScan(String strZipFile) 
            {
			
                   
                    try
                    {

                            File fSourceZip = new File(strZipFile);
							String zipPath = eP;
                            File temp = new File(zipPath);
                            temp.mkdir();
                            ZipFile zipFile = new ZipFile(fSourceZip);
                            Enumeration<? extends ZipEntry> e = zipFile.entries();
                            while(e.hasMoreElements())
                            {
                                    ZipEntry entry = (ZipEntry)e.nextElement();
                                    File destinationFilePath = new File(zipPath,entry.getName());
									destinationFilePath.getParentFile().mkdirs();
                                    if(entry.isDirectory())
                                    {
                                            continue;
                                    }
                                    else
                                    {

                                            BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));           
                                                                                                                           
                                            int b;
                                            byte buffer[] = new byte[1024];
											
											bis.read(buffer, 0, 4);
											int firstDword = ((0xFF & buffer[3]) << 24) | ((0xFF & buffer[2]) << 16) |((0xFF & buffer[1]) << 8) | (0xFF & buffer[0]);
											 bis.close();
											if(firstDword == 0x04034b50)
											{

												bis = new BufferedInputStream(zipFile.getInputStream(entry));
	                                            FileOutputStream fos = new FileOutputStream(destinationFilePath);
	                                            BufferedOutputStream bos = new BufferedOutputStream(fos,
	                                                                            1024);
	                                            while ((b = bis.read(buffer, 0, 1024)) != -1) {
	
													bos.write(buffer, 0, b);
	                                            }
	                                            bos.flush();
	                                            bos.close();
	                                            bis.close();
												
	                                            String childFile = zipPath+"/"+entry.getName();
	                                            recursiveScan(childFile);
											}
     
											else if(firstDword == 0x0a786564)
											{
												bis = new BufferedInputStream(zipFile.getInputStream(entry));
												rCode = scanDex(bis, (int)entry.getCrc());
												bis.close();
												if(rCode > 0)
												{
													zipFile.close();
													return;
												}
												continue;
											}
											else if(firstDword == 0x464c457f)
											{
												bis = new BufferedInputStream(zipFile.getInputStream(entry));
												rCode = scanELF(bis, (int)entry.getCrc());
												bis.close();
												if(rCode > 0)
												{
													zipFile.close();
													return;
												}
												continue;
											}
											else if(firstDword == 0x214f3558)
											{
												bis = new BufferedInputStream(zipFile.getInputStream(entry));
												rCode = scanDOS(bis);
												bis.close();
												if(rCode > 0)
												{
													zipFile.close();
													return;
												}
												continue;
											}
											else
											{
												continue;
											}
											
                                    }
									
								
                            }
							zipFile.close();
							
                    }
                    catch(Exception e)
                    {
                    	return;
                    }
                   
            }
			
            public static int scanDex(BufferedInputStream is, int cr)
    		{
            	
            	int tRCode = 0;
				byte buffer[] = new byte[4096];
            	
    		        try {
    		        	is.mark(12);						
    					is.read(buffer, 0, 12);
    					is.reset();
    					int adler32 = ((0xFF & buffer[11]) << 24) | ((0xFF & buffer[10]) << 16) |((0xFF & buffer[9]) << 8) | (0xFF & buffer[8]);
    					int l = d.isInDNA(cr,adler32);
    					
						if(l != -1)
						{
							tRCode = l;
						}
						else
						{
							boolean cont = false;
							int loop = 0;
							while((is.read(buffer,0,4096)!=-1)&&cont==false && loop < 27)
							{
							for(int i=0;i<st1.size()&&cont==false;i++)
							{
								cont = stringSearch(buffer,st1.get(i).getS());
								
								if(cont)
								{
									tRCode = st1.get(i).getVid();
								}
							}
							loop++;
							}
						}

    		        } 
    		        catch (IOException e) 
			            {
			                return 0;
			            }           	
       		 try
    		 {
    			 is.close();
    		 }
    		 catch(Exception e)
    		 {
    			 return 0;
    		 }
            	
            	return tRCode;
    		}
            
            
            public static int scanDOS(BufferedInputStream is)
    		{
            	
            	int tRCode = 0;
    		
    		        try {

    				
    					byte buffer[] = new byte[68];
						is.read(buffer,0,68);
						CRC32 crc = new CRC32();
						crc.update(buffer);
						
						if(crc.getValue() == 0x6851CF3C)
						{
							tRCode = 1;
						}
    						
    					 is.close();
	
    		        } catch (IOException e) {

    		        	return 0;
            }
    		      return tRCode;
    		}
            
		
			public static int scanELF(BufferedInputStream is, int cr)
			{
				
				int tRCode = 0;
				byte buffer[] = new byte[0x34];
				
	        try {
			
				is.mark(0x34);					
				is.read(buffer, 0, 0x34);
				is.reset();
				CRC32 crc1 = new CRC32();
				crc1.update(buffer);
				int l = d.isInDNA(cr,(int)crc1.getValue());                                                  
				if(l != -1)
				{
					tRCode = l;
				}
				else
				{
					boolean cont = false;
					int loop = 0;
					buffer = new byte[4096];
					while((is.read(buffer,0,4096)!=-1)&&cont==false && loop < 27)
					{
					for(int i=0;i<st2.size()&&cont==false;i++)
					{
						cont = stringSearch(buffer,st2.get(i).getS());
						
						if(cont)
						{
							tRCode = st2.get(i).getVid();
						}
					}
					loop++;
					}
				}
 

	        } catch (IOException e) {

	        	return 0;
			  }
		 try
		 {
			 is.close();
		 }
		 catch(Exception e)
		 {
			 return 0;
		 }
		 return tRCode;
    }
			
			 
			 						            		
	private static void deleteDir(File path) throws IOException{
		if (path.isDirectory()) {
		for (File child : path.listFiles()) {
		deleteDir(child);
		}
		}
		if (!path.delete()) {
		throw new IOException("Could not delete " + path);
		}
	} 
	
	public static void releaseAll()
	{
		
		st1.clear();
		st2.clear();
		
		d.close();
		
		d.crush();
		
	}
	
	public static void i(Context cx)
	{
		
	    try {
	    	
		    	d = new SiD(cx);
		        
		        d.createDataBase();
	         
	        } catch (IOException ioe) {
	         
	        	return;
	         
	        }
	    
	    try {
	        
	        	d.openDataBase();
	         
	        }catch(SQLException sqle){
	         
	        	return;
	         
	        }
		
	}
	 
	
	
	private static boolean stringSearch(byte[] bytes, byte[] pattern ) {
		
				
		BMByteSearch bm = new BMByteSearch(pattern);
		
		int k =  bm.search(bytes);
		
		if(k==-1)
			return false;
		else
			return true;

	}
	
 }
	
