package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.codeunite.paymyrch.R;
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

public class FundRequestTask extends AsyncTask<String, Void, String> {
    Context context;
    EditText edtAmount;
    EditText edtChequeNumber;
    EditText edtRemark;
    ProgressDialog progressDialog;
    Spinner spBankFrom;
    Spinner sppaymentMode;
    Spinner sptoBank;
    String status;
    String str_amount;
    String str_bankFrom;
    String str_bankTo;
    String str_chequeNumber;
    String str_paymentMode;
    String str_remark;

    public FundRequestTask(Context context, ProgressDialog progressDialog, String str_bankFrom, String str_bankTo, String str_paymentMode, String str_amount, String str_chequeNumber, String str_remark, EditText edtAmount, EditText edtChequeNumber, EditText edtRemark, Spinner spBankFrom, Spinner sptoBank, Spinner sppaymentMode) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.str_bankFrom = str_bankFrom;
        this.str_bankTo = str_bankTo;
        this.str_paymentMode = str_paymentMode;
        this.str_amount = str_amount;
        this.str_chequeNumber = str_chequeNumber;
        this.str_remark = str_remark;
        this.edtAmount = edtAmount;
        this.edtChequeNumber = edtChequeNumber;
        this.edtRemark = edtRemark;
        this.spBankFrom = spBankFrom;
        this.sptoBank = sptoBank;
        this.sppaymentMode = sppaymentMode;
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
            this.str_bankFrom = this.str_bankFrom.replace(" ", "%20");
            this.str_bankTo = this.str_bankTo.replace(" ", "%20");
            this.str_paymentMode = this.str_paymentMode.replace(" ", "%20");
            this.str_amount = this.str_amount.replace(" ", "%20");
            this.str_chequeNumber = this.str_chequeNumber.replace(" ", "%20");
            this.str_remark = this.str_remark.replace(" ", "%20");
            url = new URL(ServerUtils.BaseUrl + ServerUtils.FundRequestUrl + "MemberId=" + CommonUtils.userName + "&FromBank=" + this.str_bankFrom + "&ToBank=" + this.str_bankTo + "&Amount=" + this.str_amount + "&PaymentMode=" + this.str_paymentMode + "&ChequeDate=&ChequeOrDDNumber=" + this.str_chequeNumber + "&Remark=" + this.str_remark);
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
                this.status = new JSONArray(result).getJSONObject(0).getString("Status");
                if (this.status.equalsIgnoreCase("True")) {
                    this.edtAmount.setText("");
                    this.edtChequeNumber.setText("");
                    this.edtRemark.setText("");
                    this.spBankFrom.setSelection(0);
                    this.sptoBank.setSelection(0);
                    this.sppaymentMode.setSelection(0);
                    new SweetAlertDialog(this.context, 2).setTitleText("Success!!").setContentText("Request Send successfully..").show();
                    return;
                }
                new SweetAlertDialog(this.context, 1).setContentText("Something wrong try later...").show();
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        this.progressDialog.dismiss();
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
