package com.codeunite.paymyrch.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.codeunite.paymyrch.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ErrorActivity extends AppCompatActivity {
    Button exit;

    /* renamed from: com.codeunite.PayMyRecharge.activity.ErrorActivity$1 */
    class C05081 implements OnClickListener {
        C05081() {
        }

        public void onClick(View view) {
            if (ErrorActivity.this.isNetworkAvailable()) {
                ErrorActivity.this.finish();
                return;
            }
            ErrorActivity.this.finish();
            ErrorActivity.this.startActivity(new Intent(ErrorActivity.this, ErrorActivity.class));
        }
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new Builder().setDefaultFontPath("fonts/fontregular.ttf").setFontAttrId(R.attr.fontPath).build());
        setContentView((int) R.layout.activity_error);
        this.exit = (Button) findViewById(R.id.exit);
        this.exit.setOnClickListener(new C05081());
    }

    public boolean isNetworkAvailable() {
        return ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }
}
