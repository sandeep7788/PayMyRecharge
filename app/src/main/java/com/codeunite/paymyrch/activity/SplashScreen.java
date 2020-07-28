package com.codeunite.paymyrch.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.codeunite.paymyrch.AsyncTask.UpdateAppVersionTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.utils.CommonUtils;
import com.robohorse.gpversionchecker.GPVersionChecker;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 3500;
    boolean Is;
    String Na;
    String Pa;
    ProgressDialog progressDialog;
    String st;
    /* renamed from: t */
    TextView f39t;
    TextView t1;
    String version;

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new Builder().setDefaultFontPath("fonts/fontregular.ttf").setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_splash_screen);
        this.f39t = (TextView) findViewById(R.id.txt);
        this.t1 = (TextView) findViewById(R.id.txt1);
        this.progressDialog = new ProgressDialog(this);
        this.f39t.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fontregular.ttf"));
        this.t1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fontregular.ttf"));
        StartAnimations();
        CommonUtils.login_btn_status = "false";
        try {
            this.version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        CommonUtils.oldversion = this.version;
        new GPVersionChecker.Builder(this).create();
        if (isNetworkAvailable()) {
            new UpdateAppVersionTask(this, this.progressDialog).execute(new String[0]);
            return;
        }
        CommonUtils.offline_status = "true";
        startActivity(new Intent(this, AfterLoginActivity.class));
        Toast.makeText(this, "No Internet. You are offline Mode...", Toast.LENGTH_LONG).show();
        finish();
    }

    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l = (RelativeLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.img);
        iv.clearAnimation();
        iv.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.a);
        anim.reset();
        this.f39t.clearAnimation();
        this.f39t.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.b);
        anim.reset();
        this.t1.clearAnimation();
        this.t1.startAnimation(anim);
    }
}
