package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.fragment.DistributerOperatorStatus;
import com.codeunite.paymyrch.model.GroupReport;
import com.codeunite.paymyrch.utils.ServerUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OperatorActiveDeactiveTask extends AsyncTask<String, Void, String> {
    Activity activity;
    Context context;
    SparseArray<GroupReport> groups = new SparseArray();
    ExpandableListView listView;
    public DistributerOperatorStatus opStatusFrag;
    String operatorCodeId;
    String operatorStatus;
    String paramStatus;
    ProgressDialog progressDialog;
    String retailerId;

    public OperatorActiveDeactiveTask(Context context, String re, String opCode, DistributerOperatorStatus Frag, String opStatus) {
        this.context = context;
        this.retailerId = re;
        this.operatorCodeId = opCode;
        this.operatorStatus = opStatus;
        this.opStatusFrag = Frag;
        Log.d("ACTIVATE", re + " " + this.operatorCodeId + " " + this.operatorStatus);
    }

    protected void onPreExecute() {
        if (this.operatorStatus.equalsIgnoreCase("Activated")) {
            this.paramStatus = "true";
        } else {
            this.paramStatus = "false";
        }
        Log.d("ACTIVATE", this.paramStatus);
    }


    @Override
    protected String doInBackground(String... args) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.OperatorActiveDeactiveStatusUrl + "MemberId=" + this.retailerId + "&&OperatorCodeID=" + this.operatorCodeId + "&Status=" + this.operatorStatus);
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
            this.opStatusFrag.UpdateOpStatusList();
        } else {
            Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
        }
    }
}
