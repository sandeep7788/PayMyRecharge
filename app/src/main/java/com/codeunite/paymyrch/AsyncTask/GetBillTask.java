package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codeunite.paymyrch.model.OperatorBean;
import com.codeunite.paymyrch.utils.ServerUtils;

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

public class GetBillTask extends AsyncTask<String, Void, String> {
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


    String userName;

    String agentid;
    String customer_mobile;
    String servicenumber;
    TextView date;
    TextView customer_name;
    TextView due_amount;
    TextView due_date;
    TextView reference_id ;
    String operatorCode;
    RelativeLayout billlayout;
    EditText edtAmount;

    public GetBillTask(Context context, String userName, TextView tvBalance, String agentid, String customer_mobile, String servicenumber, EditText edtAmount, TextView date, TextView customer_name, TextView due_amount, TextView due_date, RelativeLayout billlayout, TextView reference_id, String operatorCode, ProgressDialog progressDialog) {
 this.context=context;
 this.userName=userName;
 this.tvBalance=tvBalance;
 this.agentid=agentid;
 this.customer_name=customer_name;
 this.customer_mobile=customer_mobile;
 this.servicenumber=servicenumber;
 this.date=date;
 this.due_amount=due_amount;
 this.due_date=due_date;
 this.operatorCode=operatorCode;
 this.reference_id=reference_id;
 this.progressDialog=progressDialog;
 this.billlayout=billlayout;
 this.edtAmount=edtAmount;


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
            Log.d("memberid",userName);
            Log.d("sp_key",operatorCode);
            Log.d("agentid",agentid);
            Log.d("customer_mobile",customer_mobile);
            Log.d("servicenum",servicenumber);


            url = new URL("https://paymyrecharge.in/APIMANAGER/bbps/" + ServerUtils.biltask + "memberid=" + this.userName +"&sp_key=" + this.operatorCode +"&agentid=" + this.agentid+"&customer_mobile=" + this.customer_mobile+"&servicenum=" + this.servicenumber);
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


        System.out.println("Response without split" +result);
        String[] separated = result.split("#");
        result=separated[1].trim();
        System.out.println("Response ag after split" +result);

        if (result != null) {
           new BalanceTask(this.context, this.tvBalance, this.progressDialog).execute(new String[0]);
            try {
                JSONObject root = new JSONObject(result);
                JSONObject array= root.getJSONObject("data");
                String  a= array.getString("dueamount");

                if (!a.isEmpty())
                {
                    billlayout.setVisibility(View.VISIBLE);
                    this.due_amount.setText(array.getString("dueamount"));
                    this.due_date.setText(array.getString("duedate"));
                    this.customer_name.setText(array.getString("customername"));
                    this.date.setText(array.getString("billdate"));
                    this.reference_id.setText(array.getString("reference_id"));
                    this.edtAmount.setText(array.getString("dueamount"));
                }else {
                    billlayout.setVisibility(View.GONE);

                }






/*
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
*/


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
