package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.view.ViewCompat;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressFlower.Builder;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.PlanAdapter;
import com.codeunite.paymyrch.model.GroupReport;
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

public class PlanTask extends AsyncTask<String, Void, String> {
    String account;
    Activity activity;
    PlanAdapter adapter;
    Context context;
    Dialog dialog;
    EditText edtAmount;
    SparseArray<GroupReport> groups = new SparseArray();
    ExpandableListView listView;
    String op_code;
    String plan_type;
    ACProgressFlower progressDialog;
    String status;
    String type;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.PlanTask$1 */
    class C04861 implements OnGroupExpandListener {
        int previousItem = -1;

        C04861() {
        }

        public void onGroupExpand(int groupPosition) {
            if (groupPosition != this.previousItem) {
                PlanTask.this.listView.collapseGroup(this.previousItem);
            }
            this.previousItem = groupPosition;
        }
    }

    public PlanTask(Context context, ACProgressFlower progressDialog, PlanAdapter adapter, ExpandableListView listView, Activity activity, String op_code, EditText edtAmount, Dialog dialog, String plan_type) {
        this.context = context;
        this.adapter = adapter;
        this.listView = listView;
        this.activity = activity;
        this.op_code = op_code;
        this.edtAmount = edtAmount;
        this.dialog = dialog;
        this.progressDialog = progressDialog;
        this.plan_type = plan_type;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog = new Builder(this.context).direction(100).themeColor(-1).bgColor(Color.parseColor("#666666")).fadeColor(ViewCompat.MEASURED_STATE_MASK).build();
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.PlanUrl + "&category=" + this.plan_type.replace(" ", "%20") + "&operatorCode=" + this.op_code);
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
            this.progressDialog.dismiss();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    GroupReport group = new GroupReport();
                    group.setAmount(jsonObject.getString("price"));
                    group.setTalktime(jsonObject.getString("talktime"));
                    group.setDetails(jsonObject.getString("Description"));
                    this.groups.append(i, group);
                }
                this.adapter = new PlanAdapter((Activity) this.context, this.groups, this.activity, this.edtAmount, this.dialog);
                this.listView.setAdapter(this.adapter);
                this.listView.setGroupIndicator(null);
                this.listView.setOnGroupExpandListener(new C04861());
                return;
            } catch (JSONException e) {
                this.progressDialog.dismiss();
                e.printStackTrace();
                return;
            }
        }
        this.progressDialog.dismiss();
        Toast.makeText(this.context, R.string.checkInternetMessage, Toast.LENGTH_SHORT).show();
    }
}
