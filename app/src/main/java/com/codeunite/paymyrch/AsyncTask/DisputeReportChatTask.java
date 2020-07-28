package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.ChatHistoryAdapter;
import com.codeunite.paymyrch.fragment.Dispute_Report;
import com.codeunite.paymyrch.helper.ChateMsg;
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

public class DisputeReportChatTask extends AsyncTask<String, Void, String> {
    Dispute_Report activity;
    ChatHistoryAdapter adapter;
    ArrayList<ChateMsg> chatHistory = new ArrayList();
    Context context;
    ListView listView;
    ProgressDialog progressDialog;
    String retailerId;
    String status;
    String tiketId;

    public DisputeReportChatTask(Context context, ProgressDialog progressDialog, ChatHistoryAdapter adapter, ListView listView, String tiketId) {
        this.context = context;
        this.adapter = adapter;
        this.listView = listView;
        this.activity = this.activity;
        this.progressDialog = progressDialog;
        this.tiketId = tiketId;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.DisputReportChatUrl + "&historyID=" + this.tiketId);
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
                    this.chatHistory.add(new ChateMsg(jsonObject.getString("message"), jsonObject.getString("isAdmin")));
                }
                this.adapter = new ChatHistoryAdapter(this.context, this.chatHistory);
                this.adapter.notifyDataSetChanged();
                this.listView.setAdapter(this.adapter);
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
