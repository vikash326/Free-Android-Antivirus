package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TA extends BaseAdapter {
   
   private LayoutInflater mInflater;

   private List<In> threats;
   private Map<String, Drawable> mIcons;
   private Drawable mStdImg;
   

   public TA(Context context) {

      mInflater = LayoutInflater.from(context);
      
      mStdImg = context.getResources().getDrawable(R.drawable.warnning);
      
   }
   
   @Override
   public int getCount() {
      return threats.size();
   }
   
   @Override
   public Object getItem(int position) {
      return threats.get(position);
   }
   
   @Override
   public long getItemId(int position) {
      return position;
   }
   
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      
      AppViewHolder holder;
      if(convertView == null) {
         convertView = mInflater.inflate(R.layout.st_singlerow, null);
         
         holder = new AppViewHolder();
         holder.mTitle = (TextView) convertView.findViewById(R.id.st_filename);
         holder.mIcon = (ImageView) convertView.findViewById(R.id.st_fileicon);
         convertView.setTag(holder);
      } else { 

         holder = (AppViewHolder) convertView.getTag();
      }
      
      In inf = threats.get(position);
      
      holder.setTitle(inf.getPackageName());
      if (mIcons == null || mIcons.get(inf.getPackageName()) == null) {
         holder.setIcon(mStdImg);
      } else {
         holder.setIcon(mIcons.get(inf.getPackageName()));
      }
      
      return convertView; 
   }
   

   public void setListItems(List<In> list) { 
      threats = list; 
   }
   

   public void setIcons(Map<String, Drawable> icons) {
      this.mIcons = icons;
   }
   

   public Map<String, Drawable> getIcons() {
      return mIcons;
   }
   

   public class AppViewHolder {
      
      private TextView mTitle;
      private ImageView mIcon;
      

      public void setTitle(String title) {
         mTitle.setText(title);
      }
      

      public void setIcon(Drawable img) {
         if (img != null) {
            mIcon.setImageDrawable(img);
         }
      }
   }
}
