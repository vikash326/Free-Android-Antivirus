package android.free.antivirus;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import free.an.droid.antivirus.rinix.R;

public class About extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about);
    }
}
