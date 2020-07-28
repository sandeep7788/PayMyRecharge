package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.utils.CommonUtils;
import com.codeunite.paymyrch.utils.ServerUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class RechargeStatusTask extends AsyncTask<String, Void, String> {
    Activity activity;
    String amount;
    Context context;
    ListView listView;
    String mobile;
    String operator;
    ProgressDialog progressDialog;
    String requestId;
    String status;
    TextView tvBalance;
    TextView tvamount;
    TextView tvmobile;
    TextView tvoperator;
    TextView tvrequestId;
    TextView tvstatus;

    public RechargeStatusTask(Context context, ProgressDialog progressDialog) {
        this.context = context;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.RechargeStatusUrl + "UserId=" + CommonUtils.userName);
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
                JSONObject jsonObject = new JSONObject(result);
                this.requestId = jsonObject.getString("ReqID");
                this.mobile = jsonObject.getString("RCHNO");
                this.amount = jsonObject.getString("RCHAmt");
                this.operator = jsonObject.getString("Operator");
                this.status = jsonObject.getString("Status");
                UpdateDialog(this.requestId, this.mobile, this.amount, this.operator, this.status);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }

    private void UpdateDialog(String requestId, String mobile, String amount, String operator, String status) {
        final Dialog dialog = new Dialog(this.context);
        dialog.setContentView(R.layout.rechargestatus);
        dialog.setTitle("Status");
        TextView edtmobile = (TextView) dialog.findViewById(R.id.mobile_number_rechargestatus);
        TextView edtamount = (TextView) dialog.findViewById(R.id.amount_rechargestatus);
        TextView edtoperator = (TextView) dialog.findViewById(R.id.operator_rechargestatus);
        TextView edtstatus = (TextView) dialog.findViewById(R.id.status_rechargestatus);
        Button button = (Button) dialog.findViewById(R.id.btn_exit);
        ((TextView) dialog.findViewById(R.id.edt_requestId_rechargestatus)).setText(requestId);
        edtmobile.setText(mobile);
        edtamount.setText(amount);
        edtoperator.setText(operator);
        edtstatus.setText(status);
        dialog.show();
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
}
