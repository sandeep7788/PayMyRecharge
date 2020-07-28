package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.utils.ServerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RechargeTask extends AsyncTask<String, Void, String> {
    Activity activity;
    Adapter adapter;
    String amount;
    String billUnit;
    Context context;
    ListView listView;
    String mobile;
    String msg;
    String operator;
    String processingUnit;
    ProgressDialog progressDialog;
    String status;
    TextView tvBalance;
    String username;

    public RechargeTask(Context context, String username, TextView tvBalance, String mobile, String amount, String operator, ProgressDialog progressDialog) {
        this.context = context;
        this.username = username;
        this.tvBalance = tvBalance;
        this.mobile = mobile;
        this.amount = amount;
        this.operator = operator;
        this.progressDialog = progressDialog;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (this.progressDialog != null) {
            this.progressDialog.setMessage("Please wait..");
            this.progressDialog.setIndeterminate(true);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.RechargeUrl + "remid=" + this.username + "&rchno=" + this.mobile + "&opt=" + this.operator + "&amount=" + this.amount);
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
        System.out.println("skjcd-" + result);
        if (result != null) {
            new BalanceTask(this.context, this.tvBalance, this.progressDialog).execute(new String[0]);
            try {
                JSONObject jsonObject = new JSONArray(result).getJSONObject(0);
                if (jsonObject.length() > 2) {
                    this.status = jsonObject.getString("status");
                    //Log.d("Status yo",status);
                    UpdateDialog(jsonObject.getString("TransID"), jsonObject.getString("MobileNo"), jsonObject.getString("OperatorName"), jsonObject.getString("RechargeAmount"), this.status, this.progressDialog);
                    return;
                }

                if(jsonObject.getString("status").equals("Success"))
                {
                   // ToastSuccess.show(this.context,jsonObject.optString("Message"),false);

                    final Dialog dialog = new Dialog(this.context);
                    dialog.setContentView(R.layout.toast_layout);
                    CardView bb1= (CardView)dialog.findViewById(R.id.b1);
                    TextView t1= (TextView)dialog.findViewById(R.id.toast_text);

                    String msg=jsonObject.optString("Message");
                    t1.setText(msg);
                    bb1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });


                    dialog.show();



                }else  if(jsonObject.getString("status").equals("Pending"))
                {
                    final Dialog dialog = new Dialog(this.context);
                    dialog.setContentView(R.layout.toast_warning);
                    CardView bb1= (CardView)dialog.findViewById(R.id.b1);
                    TextView t1= (TextView)dialog.findViewById(R.id.toast_text);

                    String msg=jsonObject.optString("Message");
                    t1.setText(msg);
                    bb1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });


                    dialog.show();
                }else  if(jsonObject.getString("status").equals("FAILURE"))
                {
                    final Dialog dialog = new Dialog(this.context);
                    dialog.setContentView(R.layout.toast_error);
                    CardView bb1= (CardView)dialog.findViewById(R.id.b1);
                    TextView t1= (TextView)dialog.findViewById(R.id.toast_text);

                    String msg=jsonObject.optString("Message");
                    t1.setText(msg);
                    bb1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });


                    dialog.show();
                }

              //  new GlideToast.makeToast(this.activity,jsonObject.getString("Message"),GlideToast.LENGTHLONG,GlideToast.SUCCESSTOAST,GlideToast.TOP).show();

            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        this.progressDialog.dismiss();
       // Toast.makeText(this.context, R.string.checkInternetMessage, Toast.LENGTH_SHORT).show();
    }

    private void UpdateDialog(String transId, String mobile_number, String op_Name, String recharge_amount, String status, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        final Dialog dialog = new Dialog(this.context);
        dialog.setContentView(R.layout.rechargestatus);
        dialog.setTitle("Status");
        dialog.getWindow().setLayout(-1, -2);
        TextView edtmobile = (TextView) dialog.findViewById(R.id.mobile_number_rechargestatus);
        TextView edtamount = (TextView) dialog.findViewById(R.id.amount_rechargestatus);
        TextView edtoperator = (TextView) dialog.findViewById(R.id.operator_rechargestatus);
        TextView edtstatus = (TextView) dialog.findViewById(R.id.status_rechargestatus);
        Button button = (Button) dialog.findViewById(R.id.btn_exit);
        ((TextView) dialog.findViewById(R.id.edt_requestId_rechargestatus)).setText(transId);
        edtmobile.setText(mobile_number);
        edtamount.setText(recharge_amount);
        edtoperator.setText(op_Name);
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
