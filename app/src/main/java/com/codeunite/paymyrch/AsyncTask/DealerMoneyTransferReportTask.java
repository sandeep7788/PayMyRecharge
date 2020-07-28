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
import com.codeunite.paymyrch.adapter.DealerMoneyTransferReportAdapter;
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

public class DealerMoneyTransferReportTask extends AsyncTask<String, Void, String> {
    Activity activity;
    DealerMoneyTransferReportAdapter adapter;
    Context context;
    String daycode;
    private ErrorView errorView;
    SparseArray<GroupReport> groups = new SparseArray();
    ExpandableListView listView;
    ProgressDialog progressDialog;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.DealerMoneyTransferReportTask$1 */
    class C04751 implements OnGroupExpandListener {
        int previousItem = -1;

        C04751() {
        }

        public void onGroupExpand(int groupPosition) {
            if (groupPosition != this.previousItem) {
                DealerMoneyTransferReportTask.this.listView.collapseGroup(this.previousItem);
            }
            this.previousItem = groupPosition;
        }
    }

    public DealerMoneyTransferReportTask(Context context, ProgressDialog progressDialog, String daycode, DealerMoneyTransferReportAdapter adapter, ExpandableListView listView, ErrorView errorView) {
        this.context = context;
        this.daycode = daycode;
        this.adapter = adapter;
        this.listView = listView;
        this.errorView = errorView;
        this.progressDialog = progressDialog;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.DealerMoneyTransferReportUrl + "Id=" + CommonUtils.userName + "&day=" + this.daycode);
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
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("Sender").equalsIgnoreCase("")) {
                        if (this.errorView != null) {
                            this.errorView.setVisibility(0);
                        }
                        this.errorView.setVisibility(0);
                        this.adapter = new DealerMoneyTransferReportAdapter((Activity) this.context, this.groups);
                        this.listView.setAdapter(this.adapter);
                        this.listView.setGroupIndicator(null);
                        this.listView.setOnGroupExpandListener(new C04751());
                        return;
                    }
                    if (this.errorView.getVisibility() == 0) {
                        this.errorView.setVisibility(8);
                    }
                    GroupReport group = new GroupReport();
                    group.setSenderNumber(jsonObject.getString("Sender"));
                    group.setAccountNumber(jsonObject.getString("AccountNo"));
                    group.setAmount(jsonObject.getString("Amount"));
                    group.children.add("Status :" + jsonObject.getString("Status"));
                    group.children.add("Transcation ID:" + jsonObject.getString("TransactionId"));
                    group.children.add("Bank ID :" + jsonObject.getString("BankRefId"));
                    group.children.add("Bank Name :" + jsonObject.getString("BankName"));
                    group.children.add("Receiver Name :" + jsonObject.getString("Receiver"));
                    group.children.add("IFSC Code :" + jsonObject.getString("IFSC"));
                    group.children.add("Transcation Mode :" + jsonObject.getString("TransactionMode"));
                    group.children.add("Charge :" + jsonObject.getString("AmountDetect"));
                    group.children.add("Open Balance :" + jsonObject.getString("OpenBal"));
                    group.children.add("Close Balance :" + jsonObject.getString("CloseBal"));
                    group.children.add("Open Dlm Bal :" + jsonObject.getString("OpenBalDealer"));
                    group.children.add("Close Dlm Bal :" + jsonObject.getString("CloseBalDealer"));
                    group.children.add("Transcation Date :" + jsonObject.getString("M_Date"));
                    this.groups.append(i, group);
                }
                this.adapter = new DealerMoneyTransferReportAdapter((Activity) this.context, this.groups);
                this.listView.setAdapter(this.adapter);
                this.listView.setGroupIndicator(null);
                this.listView.setOnGroupExpandListener(new C04751());
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
