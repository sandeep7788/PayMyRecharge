package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.Dispute_Report_Adapter;
import com.codeunite.paymyrch.fragment.Dispute_Report;
import com.codeunite.paymyrch.model.GroupReport;
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

public class Dispute_Report_Task extends AsyncTask<String, Void, String> {
    Dispute_Report activity;
    Dispute_Report_Adapter adapter;
    Context context;
    SparseArray<GroupReport> groups = new SparseArray();
    ExpandableListView listView;
    ProgressDialog progressDialog;
    String retailerId;
    String status;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.Dispute_Report_Task$1 */
    class C04771 implements OnGroupExpandListener {
        int previousItem = -1;

        C04771() {
        }

        public void onGroupExpand(int groupPosition) {
            if (groupPosition != this.previousItem) {
                Dispute_Report_Task.this.listView.collapseGroup(this.previousItem);
            }
            this.previousItem = groupPosition;
        }
    }

    public Dispute_Report_Task(Context context, ProgressDialog progressDialog, Dispute_Report_Adapter adapter, ExpandableListView listView, Dispute_Report activity) {
        this.context = context;
        this.adapter = adapter;
        this.listView = listView;
        this.activity = activity;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.DisputReportUrl + "&MemberId=" + CommonUtils.userName);
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
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    GroupReport group = new GroupReport();
                    group.setRetailerId(jsonObject.getString("HistoryID"));
                    group.setMobile(jsonObject.getString("mobileno"));
                    group.setStatus(jsonObject.getString("isactive"));
                    group.setTalktime(jsonObject.getString("TransID"));
                    group.children.add("Transaction ID : " + jsonObject.getString("TransID"));
                    this.groups.append(i, group);
                }
                this.adapter = new Dispute_Report_Adapter((Activity) this.context, this.groups, this.activity);
                this.listView.setAdapter(this.adapter);
                this.listView.setGroupIndicator(null);
                this.listView.setOnGroupExpandListener(new C04771());
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
