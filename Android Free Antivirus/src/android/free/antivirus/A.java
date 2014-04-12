package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Context;

public class A {
	
	private Context cx;
	List <String>filePaths;
	
	public A(Context c)
	{
    	this.filePaths = new ArrayList<String> ();
		this.cx = c;
	}
	
	
    public  List<App> lIA(boolean includeSysApps) {
	      List<App> apps = new ArrayList<App>();
	      
	      PackageManager packageManager = cx.getPackageManager();
	      
	      List<PackageInfo> packs = packageManager.getInstalledPackages(0); 
	      
	      for(int i=0; i < packs.size(); i++) {
	         PackageInfo p = packs.get(i);
	         ApplicationInfo a = p.applicationInfo;

	         if ((!includeSysApps) && ((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1)) {
	            continue;
	         }
	         App app = new App();
	         app.setTitle(p.applicationInfo.loadLabel(packageManager).toString());
	         app.setPackageName(p.packageName);
	         app.setVersionName(p.versionName);
	         app.setVersionCode(p.versionCode);
	         app.setsourcePath(a.sourceDir);
	         apps.add(app);
	      }
	      return apps;
	   }
    
    public List<String> lES()
    {
    	return filePaths;
    }
    
    public void rFF(File[] file1){
    int n = 0;
    String filePath="";
         if(file1!=null){
        while(n!=file1.length){
            filePath = file1[n].getAbsolutePath();
        	if(file1[n].isDirectory()){
        		File file[] = file1[n].listFiles();
                rFF(file);
        	}
        	else
        	{
        		filePaths.add(filePath);
        	}
            n++;
        }
      }
    }

}
