package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;

public class In {

   private String title;
   private String packageName;
   private String versionName;
   private int versionCode;
   private String description;
   private String installDir;
   private String installSize;
   
   
   public In(String t1, String t2, String t3, int t4, String t5, String t6, String t7)
   {
	   this.title = t1;
	   this.packageName = t2;
	   this.versionName = t3;
	   this.versionCode = t4;
	   this.installDir = t5;
	   this.description = t6;
	   this.installSize = t7;
   }

  
   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getPackageName() {
      return packageName;
   }

   public void setPackageName(String packageName) {
      this.packageName = packageName;
   }

   public String getVersionName() {
      return versionName;
   }

   public void setVersionName(String versionName) {
      this.versionName = versionName;
   }

   public int getVersionCode() {
      return versionCode;
   }

   public void setVersionCode(int versionCode) {
      this.versionCode = versionCode;
   }
   
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getInstallDir() {
      return installDir;
   }

   public void setInstallDir(String installDir) {
      this.installDir = installDir;
   }

   public String getInstallSize() {
      return installSize;
   }

   public void setInstallSize(String installSize) {
      this.installSize = installSize;
   }

}
