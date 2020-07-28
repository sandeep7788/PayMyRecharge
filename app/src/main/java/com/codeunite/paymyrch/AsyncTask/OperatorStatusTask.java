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
import com.codeunite.paymyrch.adapter.OperatorStatusAdapter;
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

public class OperatorStatusTask extends AsyncTask<String, Void, String> {
    Activity activity;
    OperatorStatusAdapter adapter;
    Context context;
    SparseArray<GroupReport> groups = new SparseArray();
    ExpandableListView listView;
    ProgressDialog progressDialog;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.OperatorStatusTask$1 */
    class C04851 implements OnGroupExpandListener {
        int previousItem = -1;

        C04851() {
        }

        public void onGroupExpand(int groupPosition) {
            if (groupPosition != this.previousItem) {
                OperatorStatusTask.this.listView.collapseGroup(this.previousItem);
            }
            this.previousItem = groupPosition;
        }
    }

    public OperatorStatusTask(Context context, ProgressDialog progressDialog, OperatorStatusAdapter adapter, ExpandableListView listView, Activity activity) {
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.RetailerOperatorStatus + "MemberId=" + CommonUtils.userName);
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
        if (this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    GroupReport group = new GroupReport();
                    group.setOperatorName(jsonObject.getString("OperatorName"));
                    group.setOperatorCode(jsonObject.getString("OperatorCode"));
                    group.setCommision(jsonObject.getString("Commission"));
                    group.setOperatorStatus(jsonObject.getString("OperatorStatus"));
                    group.children.add("Operator Name:" + jsonObject.getString("OperatorName"));
                    group.children.add("Operator Type:" + jsonObject.getString("OperatorType"));
                    group.children.add("Blocked Time :" + jsonObject.getString("BlockTime"));
                    this.groups.append(i, group);
                }
                this.adapter = new OperatorStatusAdapter(this.activity, this.groups);
                this.listView.setAdapter(this.adapter);
                this.listView.setGroupIndicator(null);
                this.listView.setOnGroupExpandListener(new C04851());
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, Toast.LENGTH_SHORT).show();
    }
}
