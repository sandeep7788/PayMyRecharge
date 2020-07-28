package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.activity.AfterLoginActivity;
import com.codeunite.paymyrch.utils.CommonUtils;
import com.codeunite.paymyrch.utils.ServerUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;

public class UpdateAppVersionTask extends AsyncTask<String, Void, String> {
    private static int SPLASH_TIME_OUT = 3500;
    boolean Is;
    String Na;
    String Pa;
    Context context;
    public String newVersion;
    public String oldVersion;
    ProgressDialog progressDialog;
    String st;
    TextView tvBalance;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.UpdateAppVersionTask$1 */
    class C04941 implements OnClickListener {
        C04941() {
        }

        public void onClick(View view) {
            UpdateAppVersionTask.this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.codeunite.PayMyRecharge")));
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.UpdateAppVersionTask$2 */
    class C04952 implements OnClickListener {
        C04952() {
        }

        public void onClick(View view) {
            UpdateAppVersionTask.this.login();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.UpdateAppVersionTask$3 */
    class C04963 implements DialogInterface.OnClickListener {
        C04963() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ((Activity) UpdateAppVersionTask.this.context).finish();
            System.exit(0);
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.UpdateAppVersionTask$4 */
    class C04974 implements DialogInterface.OnClickListener {
        C04974() {
        }

        public void onClick(DialogInterface dialog, int which) {
            UpdateAppVersionTask.this.login();
        }
    }

    public UpdateAppVersionTask(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;
    }


    @Override
    protected String doInBackground(String... args) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.VesionUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result;
    }

    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                this.newVersion = new JSONArray(result).getJSONObject(0).getString("Newversion");
                this.oldVersion = CommonUtils.oldversion;
                if (Double.valueOf(Double.parseDouble(this.newVersion)).doubleValue() > Double.valueOf(Double.parseDouble(this.oldVersion)).doubleValue()) {
                    Dialog openDialog = new Dialog(this.context);
                    openDialog.setContentView(R.layout.customupdatedialog);
                    openDialog.setCancelable(false);
                    Button later_button = (Button) openDialog.findViewById(R.id.btn_later);
                    ((Button) openDialog.findViewById(R.id.btn_update)).setOnClickListener(new C04941());
                    later_button.setOnClickListener(new C04952());
                    openDialog.show();
                    return;
                }
                login();
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        login();
    }

    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    void showAlertDialog() {
        Builder alertDialog = new Builder(this.context);
        alertDialog.setTitle("No Internet").setMessage("Turn On Your Internet and Try Again").setPositiveButton("Try Again", new C04974()).setNegativeButton("Exit", new C04963());
        alertDialog.show();
    }

    void login() {
        if (isNetworkAvailable()) {
            CommonUtils.offline_status = "false";
            SharedPreferences sharedPreferences = this.context.getSharedPreferences("LogDIN", 0);
            this.Is = sharedPreferences.getBoolean("Is_Login", false);
            this.Na = sharedPreferences.getString("N", "");
            this.Pa = sharedPreferences.getString("P", "");
            CommonUtils.userName = this.Na;
            if (this.Is) {
                new LoginTask(this.context, this.Na, this.Pa, this.progressDialog).execute(new String[0]);
                return;
            }
            this.context.startActivity(new Intent(this.context, AfterLoginActivity.class));
            ((Activity) this.context).finish();
            return;
        }
        showAlertDialog();
    }
}
