package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ListView;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.DistributerOPStatusAdapter;
import com.codeunite.paymyrch.adapter.StatusModel;
import com.codeunite.paymyrch.fragment.DistributerOperatorStatus;
import com.codeunite.paymyrch.model.GroupReport;
import com.codeunite.paymyrch.utils.ServerUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DealerOperatorStatusTask extends AsyncTask<String, Void, String> {
    Activity activity;
    DistributerOPStatusAdapter adapter;
    Context context;
    private DistributerOperatorStatus distributerOperatorStatus = this.distributerOperatorStatus;
    SparseArray<GroupReport> groups = new SparseArray();
    ListView listView;
    public String opcode;
    public ArrayList operatorCode = new ArrayList();
    public String operatorName;
    ProgressDialog progressDialog;
    public String retailerId;
    public String status;
    ArrayList<StatusModel> statusModels = new ArrayList();

    public DealerOperatorStatusTask(Context context, ProgressDialog progressDialog, String retailerId, DistributerOPStatusAdapter adapter, ListView listView) {
        Log.d("RETAILER123", String.valueOf(this.operatorCode.size()));
        this.context = context;
        this.retailerId = retailerId;
        this.adapter = adapter;
        this.listView = listView;
        this.activity = this.activity;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.DealerOperatorStatusUrl + "MemberId=" + this.retailerId);
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
        this.progressDialog.dismiss();
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    this.status = jsonObject.getString("OperatorStatus");
                    if (this.status.equalsIgnoreCase("true")) {
                        this.status = "Activated";
                    } else {
                        this.status = "Deactivated";
                    }
                    this.operatorName = jsonObject.getString("OperatorName");
                    this.opcode = jsonObject.getString("OperatorCode");
                    Log.d("DSTAUS", this.status);
                    Log.d("DSTAUS1", this.operatorName);
                    Log.d("DSTAUS2", this.opcode);
                    this.statusModels.add(new StatusModel(this.status, this.operatorName, String.valueOf(this.opcode)));
                    Log.d("HOLDER", String.valueOf(this.statusModels));
                }
                this.adapter = new DistributerOPStatusAdapter(this.context, this.statusModels);
                this.adapter.notifyDataSetChanged();
                this.listView.setAdapter(this.adapter);
                Log.d("LISTVIEW", String.valueOf(this.listView));
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
