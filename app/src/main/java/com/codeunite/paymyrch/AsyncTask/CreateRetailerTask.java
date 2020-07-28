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

public class CreateRetailerTask extends AsyncTask<String, Void, String> {
    String aadharNumber;
    Context context;
    String distCode;
    String gstNumber;
    String panNumber;
    ProgressDialog progressDialog;
    String slabname;
    String stateCode;
    String status;
    String strAddress;
    String strEmail;
    String strFirmName;
    String strMobile;
    String strPinCode;
    String strRetailerName;

    public CreateRetailerTask(Context context, String strRetailerName, String strFirmName, String strMobile, String strPinCode, String strEmail, String strAddress, String stateCode, String distCode, String slabname, String gstNumber, String aadharNumber, String panNumber, ProgressDialog progressDialog) {
        this.context = context;
        this.strRetailerName = strRetailerName;
        this.strFirmName = strFirmName;
        this.strMobile = strMobile;
        this.strPinCode = strPinCode;
        this.strEmail = strEmail;
        this.strAddress = strAddress;
        this.strPinCode = strPinCode;
        this.stateCode = stateCode;
        this.distCode = distCode;
        this.slabname = slabname;
        this.gstNumber = gstNumber;
        this.aadharNumber = aadharNumber;
        this.panNumber = panNumber;
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

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.CreateRetailerUrl + "MemberId=" + CommonUtils.userName + "&rchname=" + this.strRetailerName + "&FarmName=" + this.strFirmName + "&Mobile=" + this.strMobile + "&Pincode=" + this.strPinCode + "&email=" + this.strEmail + "&State=" + this.stateCode + "&District=" + this.distCode + "&Address=" + this.strAddress + "&slabnm=" + this.slabname + "&Pancardnumber=" + this.panNumber + "&aadharnumber=" + this.aadharNumber + "&GSTNumber=" + this.gstNumber);
            System.out.println("khgasd" + "-" + url);
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
                this.status = new JSONArray(result).getJSONObject(0).getString("status");
                if (this.status.equalsIgnoreCase("Success")) {
                    Toast.makeText(context, "Retailer create successfully..",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(context, "Something wrong try later...",Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        this.progressDialog.dismiss();
        Toast.makeText(this.context, R.string.checkInternetMessage, Toast.LENGTH_SHORT).show();
    }
}
