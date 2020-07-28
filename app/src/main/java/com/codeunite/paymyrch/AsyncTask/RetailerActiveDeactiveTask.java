package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import com.codeunite.paymyrch.adapter.RetailerExpandableListAdapter;
import com.codeunite.paymyrch.fragment.RetailerListFragment;
import com.codeunite.paymyrch.model.GroupReport;
import com.codeunite.paymyrch.utils.ServerUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetailerActiveDeactiveTask extends AsyncTask<String, Void, String> {
    Activity activity;
    RetailerExpandableListAdapter adapter1;
    Context context;
    SparseArray<GroupReport> groups = new SparseArray();
    ExpandableListView listView;
    ProgressDialog progressDialog;
    String retailerId;
    private RetailerListFragment retailerListFragment;
    String retailerStatus;

    public RetailerActiveDeactiveTask(Context context, ProgressDialog progressDialog, String retailerId, String retailerStatus, Activity activity, RetailerListFragment retailerListFragment) {
        Log.d("RETAILERID", retailerId);
        Log.d("STATUSRETAILER", retailerStatus);
        this.context = context;
        this.retailerId = retailerId;
        this.retailerStatus = retailerStatus;
        this.activity = activity;
        this.progressDialog = progressDialog;
        this.retailerListFragment = retailerListFragment;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.RetailerActivrDeactiveStatusUrl + "RetailerID=" + this.retailerId + "&Status=" + this.retailerStatus);
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
            this.retailerListFragment.retailer();
        }
    }
}
