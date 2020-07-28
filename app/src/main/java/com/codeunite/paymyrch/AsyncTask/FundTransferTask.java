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
import org.json.JSONArray;
import org.json.JSONException;

public class FundTransferTask extends AsyncTask<String, Void, String> {
    String amount;
    Context context;
    ProgressDialog progressDialog;
    String retailer;
    String status;
    TextView tvBalance;

    public FundTransferTask(Context context, String retailer, String amount, ProgressDialog progressDialog, TextView tvBalance) {
        this.context = context;
        this.retailer = retailer;
        this.amount = amount;
        this.progressDialog = progressDialog;
        this.tvBalance = tvBalance;
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

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.FundTransferUrl + "SenderId=" + CommonUtils.userName + "&ReceiverId=" + this.retailer + "&amount=" + this.amount);
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
            new BalanceTask(this.context, this.tvBalance, this.progressDialog).execute(new String[0]);
            try {
                this.status = new JSONArray(result).getJSONObject(0).getString("status");
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
