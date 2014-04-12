package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;

public class App {

   private String title;
   private String packageName;
   private String versionName;
   private int versionCode;
   private String sourcePath;
   
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
   public String getsourcePath() {
	      return sourcePath;
	   }

	   public void setsourcePath(String path) {
	      this.sourcePath = path;
	   }
}
