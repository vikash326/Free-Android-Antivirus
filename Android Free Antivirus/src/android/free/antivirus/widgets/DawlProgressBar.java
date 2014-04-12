package android.free.antivirus.widgets;

import free.an.droid.antivirus.rinix.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class DawlProgressBar extends ProgressBar {

  public DawlProgressBar(Context context) {
    super(context);
  }
  
  public DawlProgressBar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  
  public DawlProgressBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }
  
  @Override
  protected synchronized void onDraw(Canvas canvas) {
    updateProgressBar();
    super.onDraw(canvas);
  }
  
  @Override
  public synchronized void setProgress(int progress) {
    super.setProgress(progress);
    invalidate();
  }
  
  private float getScale(int progress) {
    float scale = getMax() > 0 ? (float) progress / (float) getMax() : 0;
    
    return scale;
  }

  private void updateProgressBar() {
    Drawable progressDrawable = getProgressDrawable();
    
    if (progressDrawable != null && progressDrawable instanceof LayerDrawable) {
      LayerDrawable d = (LayerDrawable) progressDrawable;
      
      final float scale = getScale(getProgress());

      Drawable progressBar = d.findDrawableByLayerId(R.id.progress);
      
      final int width = d.getBounds().right - d.getBounds().left;
      
      if (progressBar != null) {
        Rect progressBarBounds = progressBar.getBounds();
        progressBarBounds.right = progressBarBounds.left + (int) (width * scale + 0.5f);
        progressBar.setBounds(progressBarBounds);
      }
      

      Drawable patternOverlay = d.findDrawableByLayerId(R.id.pattern);
      
      if (patternOverlay != null) {
        if (progressBar != null) {
          Rect patternOverlayBounds = progressBar.copyBounds();
          final int left = patternOverlayBounds.left;
          final int right = patternOverlayBounds.right;
          
          patternOverlayBounds.left = (left + 1 > right) ? left : left + 1;
          patternOverlayBounds.right = (right > 0) ? right - 1 : right;
          patternOverlay.setBounds(patternOverlayBounds);
        } else {
          Rect patternOverlayBounds = patternOverlay.getBounds();
          patternOverlayBounds.right = patternOverlayBounds.left + (int) (width * scale + 0.5f);
          patternOverlay.setBounds(patternOverlayBounds);
        }
      }
    }
  }
}
