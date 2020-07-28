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
import com.codeunite.paymyrch.adapter.FundReceiveExpandableListAdapter;
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

public class FundReceiveTask extends AsyncTask<String, Void, String> {
    Activity activity;
    FundReceiveExpandableListAdapter adapter;
    Context context;
    String daycode;
    SparseArray<GroupReport> groups = new SparseArray();
    ErrorView historyError;
    ExpandableListView listView;
    ProgressDialog progressDialog;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.FundReceiveTask$1 */
    class C04791 implements OnGroupExpandListener {
        int previousItem = -1;

        C04791() {
        }

        public void onGroupExpand(int groupPosition) {
            if (groupPosition != this.previousItem) {
                FundReceiveTask.this.listView.collapseGroup(this.previousItem);
            }
            this.previousItem = groupPosition;
        }
    }

    public FundReceiveTask(Context context, ProgressDialog progressDialog, String daycode, FundReceiveExpandableListAdapter adapter, ExpandableListView listView, Activity activity, ErrorView historyError) {
        this.context = context;
        this.daycode = daycode;
        this.adapter = adapter;
        this.listView = listView;
        this.activity = activity;
        this.historyError = historyError;
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

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            if (CommonUtils.role.equalsIgnoreCase("Distributor")) {
                url = new URL(ServerUtils.BaseUrl + ServerUtils.DealerBalanceReceiveReportUrl + "MemberId=" + CommonUtils.userName + "&val=" + this.daycode);
            } else if (CommonUtils.role.equalsIgnoreCase("Retailer")) {
                url = new URL(ServerUtils.BaseUrl + ServerUtils.BalanceReceiveReportUrl + "MemberId=" + CommonUtils.userName + "&val=" + this.daycode);
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
                        if (!jsonObject.getString("New Remain").equalsIgnoreCase("")) {
                            if (this.historyError.getVisibility() == 0) {
                                this.historyError.setVisibility(8);
                            }
                            GroupReport group = new GroupReport();
                            group.setOldRemain(jsonObject.getString("Old Remain"));
                            group.setReceiveFrom(jsonObject.getString("Recive From"));
                            group.setNewRemain(jsonObject.getString("New Remain"));
                            group.setAmount(jsonObject.getString("Value"));
                            group.children.add("Date Time : " + jsonObject.getString("Date"));
                            this.groups.append(i, group);
                            i++;
                        } else if (this.historyError != null) {
                            this.historyError.setVisibility(0);
                        }
                    }
                } else if (this.historyError != null) {
                    this.historyError.setVisibility(0);
                }
                this.adapter = new FundReceiveExpandableListAdapter(this.activity, this.groups);
                this.listView.setAdapter(this.adapter);
                this.listView.setGroupIndicator(null);
                this.listView.setOnGroupExpandListener(new C04791());
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
