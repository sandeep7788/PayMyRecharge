package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import org.json.JSONObject;

public class RetailerListTask extends AsyncTask<String, Void, String> {
    public static String retailerCode;
    public static String[] retailer_id;
    public static String[] retailer_name;
    Context context;
    ProgressDialog progressDialog;
    ArrayAdapter<String> retailerid;
    ArrayAdapter<String> retailername;
    Spinner spRetailer;

    public RetailerListTask(Context context, ProgressDialog progressDialog, String[] retailer_id, ArrayAdapter<String> retailerid, Spinner spRetailer) {
        this.context = context;
        this.progressDialog = progressDialog;
        retailer_id = retailer_id;
        this.retailerid = retailerid;
        this.spRetailer = spRetailer;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.RetailerListUrl + "&MemberId=" + CommonUtils.userName);
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
        this.progressDialog.dismiss();
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                retailer_id = new String[(jsonArray.length() + 1)];
                String fundStatus = CommonUtils.fundTransferStatus;
                String rch_history = CommonUtils.rch_status;
                if (fundStatus.equalsIgnoreCase("true")) {
                    retailer_id[0] = "Select Member Id";
                } else if (rch_history.equalsIgnoreCase("true")) {
                    retailer_id[0] = "SELF";
                } else {
                    retailer_id[0] = "All";
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    retailer_id[i + 1] = jo.getString("Retailer ID") + ": " + jo.getString("Name");
                }
                this.retailerid = new ArrayAdapter(this.context, 17367048, retailer_id);
                this.retailerid.setDropDownViewResource(17367049);
                this.spRetailer.setAdapter(this.retailerid);
                return;
            } catch (JSONException e) {
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
