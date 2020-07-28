package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
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

public class SearchMobileTask extends AsyncTask<String, Void, String> {
    Activity activity;
    DealerExpandableListAdapter adapter;
    SparseArray<GroupReport> groups = new SparseArray();
    ExpandableListView listView;
    String mobile;
    ProgressDialog progressDialog;
    private TableRow searchTableRow;
    TextView tvBalance;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.SearchMobileTask$1 */
    class C04911 implements OnGroupExpandListener {
        int previousItem = -1;

        C04911() {
        }

        public void onGroupExpand(int groupPosition) {
            if (groupPosition != this.previousItem) {
                SearchMobileTask.this.listView.collapseGroup(this.previousItem);
            }
            this.previousItem = groupPosition;
        }
    }

    public SearchMobileTask(Activity activity, String mobile, DealerExpandableListAdapter adapter, ExpandableListView listView, ProgressDialog progressDialog, TableRow searchTableRow, TextView tvBalance) {
        this.activity = activity;
        this.mobile = mobile;
        this.adapter = adapter;
        this.listView = listView;
        this.progressDialog = progressDialog;
        this.searchTableRow = searchTableRow;
        this.tvBalance = tvBalance;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        try {
            this.progressDialog.setMessage("Please wait..");
            this.progressDialog.setIndeterminate(true);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.SearchMobileUrl + "MemberId=" + CommonUtils.userName + "&mobile=" + this.mobile);
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
        try {
            this.progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    GroupReport group = new GroupReport();
                    group.setRequestId(jsonObject.getString("Request_ID"));
                    group.setCustomerNo(jsonObject.getString("Recharge_number"));
                    group.setAmount(jsonObject.getString("Recharge_amount"));
                    group.setStatus(jsonObject.getString("Status"));
                    group.children.add("Operator Name : " + jsonObject.getString("Operator_name"));
                    group.children.add("Recharge Mode : " + jsonObject.getString("RechargeMode"));
                    group.children.add("Date Time : " + jsonObject.getString("Datetime"));
                    group.children.add("Operator Id : " + jsonObject.getString("Operatorid"));
                    group.children.add("Remain Amount : " + jsonObject.getString("remainamount"));
                    this.groups.append(i, group);
                }
                if (this.searchTableRow.getVisibility() == 8) {
                    this.searchTableRow.setVisibility(0);
                }
                this.adapter = new DealerExpandableListAdapter(this.activity, this.groups, this.tvBalance, this.progressDialog);
                this.adapter.notifyDataSetChanged();
                this.listView.setAdapter(this.adapter);
                this.listView.setGroupIndicator(null);
                this.listView.setOnGroupExpandListener(new C04911());
                return;
            } catch (JSONException e2) {
                e2.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.activity, R.string.checkInternetMessage, 0).show();
    }
}
