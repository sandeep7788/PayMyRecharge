package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
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

public class JoinUsTask extends AsyncTask<String, Void, String> {
    Context context;
    String distCode;
    ProgressDialog progressDialog;
    String stateCode;
    String status;
    String strAddress;
    String strEmail;
    String strMobile;
    String strName;
    String strPin;
    String strRole;

    public JoinUsTask(Context context, String strName, String strMobile, String strEmail, String strRole, String stateCode, String distCode, String strAddress, String strPin, ProgressDialog progressDialog) {
        this.context = context;
        this.strName = strName;
        this.strMobile = strMobile;
        this.strEmail = strEmail;
        this.strAddress = strAddress;
        this.strRole = strRole;
        this.strPin = strPin;
        this.stateCode = stateCode;
        this.distCode = distCode;
        this.progressDialog = progressDialog;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... args) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.JoinUsUrl + "&Name=" + this.strName + "&Mobile=" + this.strMobile + "&Email=" + this.strEmail + "&Role=" + this.strRole + "&State=" + this.stateCode + "&District=" + this.distCode + "&Address=" + this.strAddress + "&Pin=" + this.strPin);
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

    protected void onPostExecute(String s) {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        if (s != null) {
            try {
                this.status = new JSONArray().getJSONObject(0).getString("status");
                CommonUtils.showMessage(this.context, this.status);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
