package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.codeunite.paymyrch.model.OperatorBean;
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
import java.util.ArrayList;
import java.util.List;

public class OperatorTask extends AsyncTask<String, Void, String> {
    Activity activity;
    Adapter adapter;
    String amount;
    String billUnit;
    Context context;
    ListView listView;
    String mobile;
    String msg;
    String operator;
    List<OperatorBean> list= new ArrayList<>();
    String processingUnit;
    ProgressDialog progressDialog;
    String status,status1;
   TextView tvBalance;
   EditText edtMobile;
    String username;

   /* public OperatorTask(Context context, String username, TextView tvBalance, String mobile, String amount, String operator, ProgressDialog progressDialog) {
        this.context = context;
        this.username = username;
        this.tvBalance = tvBalance;
        this.mobile = mobile;
        this.amount = amount;
        this.operator = operator;
        this.progressDialog = progressDialog;
    }*/

    public OperatorTask(Context context, EditText edtMobile, String userName, TextView tvBalance, String operatorCode, ProgressDialog progressDialog) {
        this.context = context;
        this.username = userName;
        this.tvBalance = tvBalance;
        this.operator = operatorCode;
        this.edtMobile=edtMobile;
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
            url = new URL("https://paymyrecharge.in/APIMANAGER/bbps/" + ServerUtils.optask + "memberid=" + this.username +"&sp_key=" + this.operator);
            System.out.println("Request BBPS : " +url);
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


        //System.out.println("Response without split" +result);
        String[] separated = result.split("#");
        result=separated[1].trim();
        //System.out.println("Response ag after split" +result);

        if (result != null) {
           new BalanceTask(this.context, this.tvBalance, this.progressDialog).execute(new String[1]);
            try {
                JSONObject root = new JSONObject(result);
                JSONArray array= root.getJSONArray("data");
               for (int i=0;i<array.length();i++)
               {
                   JSONObject jsonObject = array.getJSONObject(i);
                   String a=jsonObject.getString("is_bbps_enabled");
                   String a1=jsonObject.getString("payment_amt_exactness");

                   String a2=jsonObject.getString("payment_mode");

                   String params=jsonObject.getString("params");
                   params= params.substring(1, params.length()-1);

                   Log.d("a",params);

                   //JSONArray arrayparams= root.getJSONArray(params);

                   JSONObject jsonObjectparams =new JSONObject(params);
                   String paramsname=jsonObjectparams.getString("name");
                   Log.d("Name",paramsname);
                   String paramsMinLength=jsonObjectparams.getString("MinLength");
                   Log.d("MaxLength",paramsMinLength);
                   this.edtMobile.setHint(paramsname);



               }


               return;




            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        this.progressDialog.dismiss();

    }

/*
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
*/
}
