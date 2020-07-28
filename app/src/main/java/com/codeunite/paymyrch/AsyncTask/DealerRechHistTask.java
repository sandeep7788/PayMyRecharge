package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.DealerExpandableListAdapter;
import com.codeunite.paymyrch.fragment.DealerRechHistFragment;
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

public class DealerRechHistTask extends AsyncTask<String, Void, String> {
    Activity activity;
    DealerExpandableListAdapter adapter;
    Context context;
    String daycode;
    private DealerRechHistFragment dealerRechHistFragment;
    SparseArray<GroupReport> groups = new SparseArray();
    private ErrorView historyError;
    ExpandableListView listView;
    String opcode;
    ProgressDialog progressDialog;
    String retailerId;
    String statuscode;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvBalance;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.DealerRechHistTask$1 */
    class C04761 implements OnGroupExpandListener {
        int previousItem = -1;

        C04761() {
        }

        public void onGroupExpand(int groupPosition) {
            if (groupPosition != this.previousItem) {
                DealerRechHistTask.this.listView.collapseGroup(this.previousItem);
            }
            this.previousItem = groupPosition;
        }
    }

    public DealerRechHistTask(Context context, ProgressDialog progressDialog, String daycode, String opcode, String statuscode, String retailerId, DealerExpandableListAdapter adapter, ExpandableListView listView, Activity activity, ErrorView dealerHistoryErrorView, SwipeRefreshLayout swipeRefreshLayout, TextView tvBalance) {
        this.context = context;
        this.daycode = daycode;
        this.adapter = adapter;
        this.listView = listView;
        this.activity = activity;
        this.opcode = opcode;
        this.statuscode = statuscode;
        this.retailerId = retailerId;
        this.progressDialog = progressDialog;
        this.historyError = dealerHistoryErrorView;
        this.swipeRefreshLayout = swipeRefreshLayout;
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
            if (this.retailerId.equalsIgnoreCase("SELF")) {
                this.retailerId = "All";
            }
            if (this.retailerId.equalsIgnoreCase("ALL")) {
                url = new URL(ServerUtils.BaseUrl + ServerUtils.DealerRechHistUrl + "MemberId=" + CommonUtils.userName + "&retailerid=" + this.retailerId + "&status=" + this.statuscode + "&OperatorName=" + this.opcode + "&show=" + this.daycode);
            } else {
                url = new URL(ServerUtils.BaseUrl + ServerUtils.DealerRechHistUrl + "dealerid=&MemberId=" + this.retailerId + "&status=" + this.statuscode + "&OperatorName=" + this.opcode + "&show=" + this.daycode);
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
                            group.setStatus(jsonObject.getString("Status"));
                            group.setAmount(jsonObject.getString("Recharge_amount"));
                            group.setTalktime(jsonObject.getString("ServiceType"));
                            group.children.add("Operator : " + jsonObject.getString("Operator_name"));
                            group.children.add("Name : " + jsonObject.getString("Name"));
                            group.children.add("Type : " + jsonObject.getString("ServiceType"));
                            group.children.add("Date Time : " + jsonObject.getString("Datetime"));
                            group.children.add("OPT. ID : " + jsonObject.getString("Operatorid"));
                            group.children.add("Recharge Mode : " + jsonObject.getString("RechargeMode"));
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
                this.listView.setAdapter(this.adapter);
                this.listView.setGroupIndicator(null);
                this.listView.setOnGroupExpandListener(new C04761());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
            Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
        }
        if (this.swipeRefreshLayout != null && this.swipeRefreshLayout.isRefreshing()) {
            this.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
