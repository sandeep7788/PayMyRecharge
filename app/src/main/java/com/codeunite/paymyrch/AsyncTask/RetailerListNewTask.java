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
import com.codeunite.paymyrch.adapter.RetailerExpandableListAdapter;
import com.codeunite.paymyrch.fragment.RetailerListFragment;
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

public class RetailerListNewTask extends AsyncTask<String, Void, String> {
    RetailerListFragment activity;
    RetailerExpandableListAdapter adapter;
    Context context;
    SparseArray<GroupReport> groups = new SparseArray();
    ExpandableListView listView;
    ProgressDialog progressDialog;
    String retailerId;
    String status;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.RetailerListNewTask$1 */
    class C04901 implements OnGroupExpandListener {
        int previousItem = -1;

        C04901() {
        }

        public void onGroupExpand(int groupPosition) {
            if (groupPosition != this.previousItem) {
                RetailerListNewTask.this.listView.collapseGroup(this.previousItem);
            }
            this.previousItem = groupPosition;
        }
    }

    public RetailerListNewTask(Context context, ProgressDialog progressDialog, RetailerExpandableListAdapter adapter, ExpandableListView listView, RetailerListFragment activity) {
        this.context = context;
        this.adapter = adapter;
        this.listView = listView;
        this.activity = activity;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.RetailerListUrl + "&MemberId=" + CommonUtils.userName);
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
                    GroupReport group = new GroupReport();
                    group.setRetailerId(jsonObject.getString("Remain Value"));
                    group.setName(jsonObject.getString("Name"));
                    group.setMobile(jsonObject.getString("Mobile"));
                    group.setStatus(jsonObject.getString("Status"));
                    group.setR_ret_id(jsonObject.getString("Retailer ID"));
                    group.children.add("Name : " + jsonObject.getString("Name"));
                    group.children.add("Retailer ID : " + jsonObject.getString("Retailer ID"));
                    group.children.add("State : " + jsonObject.getString("State"));
                    group.children.add("District : " + jsonObject.getString("District"));
                    group.children.add("Address : " + jsonObject.getString("Address"));
                    group.children.add("Join Date : " + jsonObject.getString("Join Date"));
                    this.groups.append(i, group);
                }
                this.adapter = new RetailerExpandableListAdapter((Activity) this.context, this.groups, this.activity);
                this.listView.setAdapter(this.adapter);
                this.listView.setGroupIndicator(null);
                this.listView.setOnGroupExpandListener(new C04901());
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
