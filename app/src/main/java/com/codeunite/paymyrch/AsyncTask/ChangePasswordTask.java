package com.codeunite.paymyrch.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.widget.Toast;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressFlower.Builder;
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

public class ChangePasswordTask extends AsyncTask<String, Void, String> {
    Context context;
    String newPass;
    String oldPass;
    ACProgressFlower progressDialog;
    String status;

    public ChangePasswordTask(Context context, String oldPass, String newPass, ACProgressFlower progressDialog) {
        this.context = context;
        this.oldPass = oldPass;
        this.newPass = newPass;
        this.progressDialog = progressDialog;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog = new Builder(this.context).direction(100).text("Please wait...").build();
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.ChangePasswordUrl + "oldpass=" + this.oldPass + "&newpassword=" + this.newPass + "&MemberId=" + CommonUtils.userName);
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
            this.progressDialog.dismiss();
            try {
                this.status = new JSONArray(result).getJSONObject(0).getString("Status");
                if (this.status.equalsIgnoreCase("Password change Successfully !!")) {
                    Editor editor = this.context.getSharedPreferences("LogDIN", 0).edit();
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(this.context, AfterLoginActivity.class);
                    i.addFlags(335577088);
                    this.context.startActivity(i);
                    return;
                }
                CommonUtils.showMessage(this.context, this.status);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        this.progressDialog.dismiss();
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
