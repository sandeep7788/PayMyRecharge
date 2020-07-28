package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.fragment.DispteReportHistoryDialog;
import com.codeunite.paymyrch.utils.ServerUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;

public class DisputeRepleyTask extends AsyncTask<String, Void, String> {
    Context context;
    DispteReportHistoryDialog dispteReportHistoryDialog;
    EditText edt_repley_message;
    ProgressDialog progressDialog;
    String status;
    String strMessage;
    String strtransId;

    public DisputeRepleyTask(Context context, String strtransId, String strMessage, ProgressDialog progressDialog, DispteReportHistoryDialog dispteReportHistoryDialog, EditText edt_repley_message) {
        this.context = context;
        this.strtransId = strtransId;
        this.strMessage = strMessage;
        this.progressDialog = progressDialog;
        this.dispteReportHistoryDialog = dispteReportHistoryDialog;
        this.edt_repley_message = edt_repley_message;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.DisputRepleyUrl + "rechargeid=" + this.strtransId + "&Message=" + this.strMessage);
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
            try {
                this.status = new JSONArray(result).getJSONObject(0).getString("Status");
                if (this.status.equalsIgnoreCase("Dispute raised successfully !!")) {
                    this.dispteReportHistoryDialog.chat_history();
                    this.edt_repley_message.setText("");
                    return;
                }
                this.progressDialog.dismiss();
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
