package com.codeunite.paymyrch.AsyncTask;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.fragment.Swipe;
import com.codeunite.paymyrch.utils.ServerUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ElectricityBillTask extends AsyncTask<String, Void, String> {
    Context context;
    String userName;
    String status;
    TextView tvBalance;
    String k_customer_number;
    String amount;
    String cnumber;
    String reffrence_idd;

    String operatorCode;
    ProgressDialog progressDialog;

    public ElectricityBillTask(Context context, String userName, TextView tvBalance, String k_customer_number, String amount, String cnumber, String reffrence_idd, String operatorCode, ProgressDialog progressDialog) {

        this.context=context;
        this.userName=userName;
        this.tvBalance=tvBalance;
        this.k_customer_number=k_customer_number;
        this.amount=amount;
        this.cnumber=cnumber;
        this.reffrence_idd=reffrence_idd;
        this.operatorCode=operatorCode;
        this.progressDialog=progressDialog;
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
            url = new URL(ServerUtils.BaseUrl2 + ServerUtils.recharegetask + "memberid=" + this.userName + "&number=" + this.k_customer_number + "&amount=" + this.amount + "&operator=" + this.operatorCode+ "&CustomerMobile=" + this.cnumber+ "&account=" + reffrence_idd);
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
        new BalanceTask(this.context, this.tvBalance, this.progressDialog).execute(new String[0]);

        String[] arrSplit = result.split(",");
        for (int i=0; i < arrSplit.length; i++)
        {
            System.out.println(arrSplit[i]);
             status=arrSplit[1];
            Log.d("status 0",arrSplit[1]);
            Log.d("message",arrSplit[4]);


        }

        if(this.status.toLowerCase().equalsIgnoreCase("Success"))
        {
            // ToastSuccess.show(this.context,jsonObject.optString("Message"),false);

            final Dialog dialog = new Dialog(this.context);
            dialog.setContentView(R.layout.toast_layout);
            CardView bb1= (CardView)dialog.findViewById(R.id.b1);
            TextView t1= (TextView)dialog.findViewById(R.id.toast_text);

            String msg="Success";
            t1.setText(msg);
            bb1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment= new Swipe();
                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.swipe,fragment);
                    fragmentTransaction.commit();
                    dialog.dismiss();

                }
            });


            dialog.show();



        }else if(status.equalsIgnoreCase("Pending"))
        {
            final Dialog dialog = new Dialog(this.context);
            dialog.setContentView(R.layout.toast_warning);
            CardView bb1= (CardView)dialog.findViewById(R.id.b1);
            TextView t1= (TextView)dialog.findViewById(R.id.toast_text);
            TextView tt1= (TextView)dialog.findViewById(R.id.title);


            String msg="Pending";
            tt1.setText(msg);
            t1.setText(arrSplit[4]);

            bb1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    fragmentManager.popBackStackImmediate();*/
                    Fragment fragment= new Swipe();
                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.swipe,fragment);
                    fragmentTransaction.commit();
                    dialog.dismiss();

                }
            });


            dialog.show();
        }else if(status.equalsIgnoreCase("FAILED"))
        {
            final Dialog dialog = new Dialog(this.context);
            dialog.setContentView(R.layout.toast_error);
            CardView bb1= (CardView)dialog.findViewById(R.id.b1);
            TextView t1= (TextView)dialog.findViewById(R.id.toast_text);

            String msg="Failed";
            TextView tt1= (TextView)dialog.findViewById(R.id.title);
            tt1.setText(msg);
            t1.setText(arrSplit[4]);
            bb1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment= new Swipe();
                    android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.swipe,fragment);
                    fragmentTransaction.commit();
                    dialog.dismiss();

                }
            });


            dialog.show();
        }
        this.progressDialog.dismiss();
      /*  String[] separated = result.split(",");
        result=separated[0].trim();
        Log.d("result0",result);

       String resultn= result.substring(1, result.length()-1);*/
/*
        String[] separated1 = resultn.split(",");
        resultn=separated1[1].trim();
        Log.d("result1",resultn);*/
/*
        if (result != null) {

*/
/*
            try {
              //  JSONObject jsonObject = new JSONArray(result).getJSONObject(0);

           *//*

*/
/*     if(jsonObject.getString("status").equals("Success"))
                {
                   // ToastSuccess.show(this.context,jsonObject.optString("Message"),false);

                    final Dialog dialog = new Dialog(this.context);
                    dialog.setContentView(R.layout.toast_layout);
                    CardView bb1= (CardView)dialog.findViewById(R.id.b1);
                    TextView t1= (TextView)dialog.findViewById(R.id.toast_text);

                    String msg=jsonObject.optString("Message");
                    t1.setText(msg);
                    bb1.setOnClickListener(new OnClickListener() {
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
                    bb1.setOnClickListener(new OnClickListener() {
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
                    bb1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });


                    dialog.show();
                }*//*
*/
/*


              //  new GlideToast.makeToast(this.activity,jsonObject.getString("Message"),GlideToast.LENGTHLONG,GlideToast.SUCCESSTOAST,GlideToast.TOP).show();

            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
*//*

        }
*/

       // Toast.makeText(this.context, R.string.checkInternetMessage, Toast.LENGTH_SHORT).show();
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
