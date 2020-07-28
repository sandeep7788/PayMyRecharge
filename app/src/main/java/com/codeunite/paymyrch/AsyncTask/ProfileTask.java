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
import org.json.JSONObject;

public class ProfileTask extends AsyncTask<String, Void, String> {
    public String address;
    Context context;
    public String joinDate;
    public String mobile;
    public String name;
    ProgressDialog progressDialog;
    TextView tvAddress;
    TextView tvJoinDate;
    TextView tvMobile;
    TextView tvName;
    TextView tv_userName_header;
    TextView tv_userNumber_header;

    public ProfileTask(Context context, ProgressDialog progressDialog, TextView tvName, TextView tvAddress, TextView tvMobile, TextView tvJoinDate, TextView tv_userName_header, TextView tv_userNumber_header) {
        this.context = context;
        this.tvName = tvName;
        this.tvAddress = tvAddress;
        this.tvMobile = tvMobile;
        this.tvJoinDate = tvJoinDate;
        this.tv_userName_header = tv_userName_header;
        this.tv_userNumber_header = tv_userNumber_header;
        this.progressDialog = progressDialog;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.progressDialog != null) {
            this.progressDialog.setMessage("Please wait..");
            this.progressDialog.setIndeterminate(true);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }
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
                url = new URL(ServerUtils.BaseUrl + ServerUtils.DealerProfileUrl + "MemberId=" + CommonUtils.userName);
            } else if (CommonUtils.role.equalsIgnoreCase("Retailer")) {
                url = new URL(ServerUtils.BaseUrl + ServerUtils.RetailerProfileUrl + "MemberId=" + CommonUtils.userName);
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
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONArray(result).getJSONObject(0);
                this.name = jsonObject.getString("Name");
                this.mobile = jsonObject.getString("Mobile");
                this.address = jsonObject.getString("Address");
                this.joinDate = jsonObject.getString("Join Date");
                if (this.tvAddress == null || this.tvMobile == null || this.tvJoinDate == null) {
                    this.tvName.setText(this.name);
                    this.tv_userName_header.setText(this.name);
                    this.tv_userNumber_header.setText(this.mobile);
                    return;
                }
                this.tvName.setText(this.name);
                this.tvAddress.setText(this.address);
                this.tvMobile.setText(this.mobile);
                this.tvJoinDate.setText(this.joinDate);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, Toast.LENGTH_SHORT).show();
    }
}
