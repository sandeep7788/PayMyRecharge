package com.codeunite.paymyrch.AsyncTask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog.OnSweetClickListener;
import com.codeunite.paymyrch.activity.AdminActivity;
import com.codeunite.paymyrch.activity.AfterLoginActivity;
import com.codeunite.paymyrch.activity.DealerActivity;
import com.codeunite.paymyrch.activity.MainActivity;
import com.codeunite.paymyrch.activity.SplashScreen;
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

public class LoginTask extends AsyncTask<String, Void, String> {
    boolean Is;
    Context context;
    String password;
    ProgressDialog progressDialog;
    String status;
    String username;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.LoginTask$1 */
    class C04841 implements OnSweetClickListener {
        C04841() {
        }

        public void onClick(SweetAlertDialog sweetAlertDialog) {
            Editor editor = LoginTask.this.context.getSharedPreferences("LogDIN", 0).edit();
            editor.clear();
            editor.commit();
            LoginTask.this.context.startActivity(new Intent(LoginTask.this.context, AfterLoginActivity.class));
           // LoginTask.this.context.finish();
        }
    }

    public LoginTask(Context context, String username, String password, ProgressDialog progressDialog) {
        this.context = context;
        this.username = username;
        this.password = password;
        this.progressDialog = progressDialog;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.Is = this.context.getSharedPreferences("LogDIN", 0).getBoolean("Is_Login", false);
        this.progressDialog.setMessage("Please wait..");
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }


    @Override
    protected String doInBackground(String... args) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.LoginUrl + "name=" + this.username + "&namepass=" + this.password);
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

    @SuppressLint("WrongConstant")
    protected void onPostExecute(String result) {
        if (result != null) {
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
            try {
                this.status = new JSONArray(result).getJSONObject(0).getString("status");
                CommonUtils.role = this.status;
                Intent intent;
                String uId;
                String pass;
                Editor editor;
                if (this.status.equalsIgnoreCase("Retailer")) {
                    intent = new Intent(this.context, MainActivity.class);
                    intent.addFlags(335544320);
                    this.context.startActivity(intent);
                    if (this.Is) {
                        ((SplashScreen) this.context).finish();
                        return;
                    }
                    uId = CommonUtils.uId;
                    pass = CommonUtils.pass;
                    editor = this.context.getSharedPreferences("LogDIN", 0).edit();
                    editor.putBoolean("Is_Login", true);
                    editor.putString("N", uId);
                    editor.putString("P", pass);
                    editor.commit();
                    ((AfterLoginActivity) this.context).finish();
                    return;
                } else if (this.status.equalsIgnoreCase("Distributor")) {
                    intent = new Intent(this.context, DealerActivity.class);
                    intent.addFlags(335544320);
                    this.context.startActivity(intent);
                    if (this.Is) {
                        ((SplashScreen) this.context).finish();
                        return;
                    }
                    uId = CommonUtils.uId;
                    pass = CommonUtils.pass;
                    editor = this.context.getSharedPreferences("LogDIN", 0).edit();
                    editor.putBoolean("Is_Login", true);
                    editor.putString("N", uId);
                    editor.putString("P", pass);
                    editor.commit();
                    ((AfterLoginActivity) this.context).finish();
                    return;
                } else if (this.status.equalsIgnoreCase("Admin")) {
                    intent = new Intent(this.context, AdminActivity.class);
                    intent.addFlags(335544320);
                    this.context.startActivity(intent);
                    if (this.Is) {
                        ((SplashScreen) this.context).finish();
                        return;
                    }
                    uId = CommonUtils.uId;
                    pass = CommonUtils.pass;
                    editor = this.context.getSharedPreferences("LogDIN", 0).edit();
                    editor.putBoolean("Is_Login", true);
                    editor.putString("N", uId);
                    editor.putString("P", pass);
                    editor.commit();
                    ((AfterLoginActivity) this.context).finish();
                    return;
                } else if (CommonUtils.login_btn_status.equalsIgnoreCase("true")) {
                    CommonUtils.showMessage(this.context, "Incorrect username or password");
                    return;
                } else {
                    new SweetAlertDialog(this.context, 1).setTitleText("Oops...").setContentText("Incorrect username or password").setConfirmClickListener(new C04841()).show();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        Toast.makeText(this.context, "Something went wrong.Please check your network connection", 0).show();
    }
}
