package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;
import com.codeunite.paymyrch.adapter.DealerExpandableListAdapter;
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
import tr.xip.errorview.ErrorView;

public class HistoryTask extends AsyncTask<String, Void, String> {
    Activity activity;
    DealerExpandableListAdapter adapter;
    Context context;
    String days;
    SparseArray<GroupReport> groups = new SparseArray();
    ErrorView historyError;
    ExpandableListView listView;
    String opCode;
    ProgressDialog progressDialog;
    String status;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvBalance;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.HistoryTask$1 */
    class C04821 implements OnGroupExpandListener {
        int previousItem = -1;

        C04821() {
        }

        public void onGroupExpand(int groupPosition) {
            if (groupPosition != this.previousItem) {
                HistoryTask.this.listView.collapseGroup(this.previousItem);
            }
            this.previousItem = groupPosition;
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.HistoryTask$2 */
    class C04832 implements OnChildClickListener {
        C04832() {
        }

        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Toast.makeText(HistoryTask.this.activity, (String) HistoryTask.this.adapter.getChild(groupPosition, childPosition), 1).show();
            return true;
        }
    }

    public HistoryTask(Context context, Activity activity, String days, String opCode, String status, DealerExpandableListAdapter adapter, ExpandableListView listView, ProgressDialog progressDialog, ErrorView historyError, SwipeRefreshLayout swipeRefreshLayout, TextView tvBalance) {
        this.context = context;
        this.activity = activity;
        this.days = days;
        this.opCode = opCode;
        this.status = status;
        this.adapter = adapter;
        this.listView = listView;
        this.progressDialog = progressDialog;
        this.historyError = historyError;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.tvBalance = tvBalance;
        Log.d("HISTORY", days + " " + opCode + " " + status);
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

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.RechargeReportUrl + "MemberId=" + CommonUtils.userName + "&status=" + this.status + "&OperatorName=" + this.opCode + "&show=" + this.days);
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
                if (jsonArray.length() != 0) {
                    int i = 0;
                    while (i < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (!jsonObject.getString("Request_ID").equalsIgnoreCase("")) {
                            if (this.historyError.getVisibility() == 0) {
                                this.historyError.setVisibility(8);
                            }
                            GroupReport group = new GroupReport();
                            group.setRequestId(jsonObject.getString("Request_ID"));
                            group.setCustomerNo(jsonObject.getString("Recharge_number"));
                            group.setAmount(jsonObject.getString("Recharge_amount"));
                            group.setStatus(jsonObject.getString("Status"));
                            group.children.add("Operator Name : " + jsonObject.getString("Operator_name"));
                            group.children.add("Recharge Mode : " + jsonObject.getString("RechargeMode"));
                            group.children.add("Date Time : " + jsonObject.getString("Datetime"));
                            group.children.add("Commision : " + jsonObject.getString("Commision"));
                            group.children.add("Operator Id : " + jsonObject.getString("Operatorid"));
                            group.children.add("Txn Id : " + jsonObject.getString("Request_ID"));
                            group.children.add("Have Dispute ? Click Here!!");
                            this.groups.append(i, group);
                            i++;
                        } else if (this.historyError != null) {
                            this.historyError.setVisibility(0);
                        }
                    }
                } else if (this.historyError != null) {
                    this.historyError.setVisibility(0);
                }
                this.adapter = new DealerExpandableListAdapter(this.activity, this.groups, this.tvBalance, this.progressDialog);
                this.adapter.notifyDataSetChanged();
                this.listView.setAdapter(this.adapter);
                this.listView.setGroupIndicator(null);
                this.listView.setOnGroupExpandListener(new C04821());
                this.listView.setOnChildClickListener(new C04832());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
            this.historyError.setVisibility(0);
        }
        if (this.swipeRefreshLayout != null && this.swipeRefreshLayout.isRefreshing()) {
            this.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
