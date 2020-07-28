package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
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
import org.json.JSONException;
import org.json.JSONObject;

public class IncomeTask extends AsyncTask<String, Void, String> {
    public String TotalFaild;
    public String TotalIncome;
    public String TotalPending;
    public String TotalSuccess;
    Context context;
    String daycode;
    ProgressDialog progressDialog;
    TextView tvTotalFaild;
    TextView tvTotalIncome;
    TextView tvTotalPending;
    TextView tvTotalSuccess;

    public IncomeTask(Context context, ProgressDialog progressDialog, String daycode, TextView tvTotalSuccess, TextView tvTotalFaild, TextView tvTotalPending, TextView tvTotalIncome) {
        this.context = context;
        this.daycode = daycode;
        this.tvTotalSuccess = tvTotalSuccess;
        this.tvTotalFaild = tvTotalFaild;
        this.tvTotalPending = tvTotalPending;
        this.tvTotalIncome = tvTotalIncome;
        this.progressDialog = progressDialog;
    }

    protected void onPreExecute() {
        super.onPreExecute();
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

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            if (CommonUtils.role.equalsIgnoreCase("Distributor")) {
                url = new URL(ServerUtils.BaseUrl + ServerUtils.IncomeUrl + "MemberId=" + CommonUtils.userName + "&Days=" + this.daycode);
            } else if (CommonUtils.role.equalsIgnoreCase("Retailer")) {
                url = new URL(ServerUtils.BaseUrl + ServerUtils.IncomeUrl + "MemberId=" + CommonUtils.userName + "&Days=" + this.daycode);
            }            urlConnection = (HttpURLConnection) url.openConnection();
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
        if (this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                this.TotalSuccess = jsonObject.getString("Success");
                this.TotalFaild = jsonObject.getString("Failed");
                this.TotalPending = jsonObject.getString("Pending");
                this.TotalIncome = jsonObject.getString("Income");
                this.tvTotalSuccess.setText(this.TotalSuccess);
                this.tvTotalFaild.setText(this.TotalFaild);
                this.tvTotalPending.setText(this.TotalPending);
                this.tvTotalIncome.setText(this.TotalIncome);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
