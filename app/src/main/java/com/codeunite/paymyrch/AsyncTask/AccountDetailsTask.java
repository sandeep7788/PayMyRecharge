package com.codeunite.paymyrch.AsyncTask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountDetailsTask extends AsyncTask<String, Void, String> {
    public static String BN_to;
    ArrayAdapter<String> BankName_to;
    String[] bank_name_to;
    Context context;
    ProgressDialog progressDialog;
    Spinner spState;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.AccountDetailsTask$1 */
    class C04711 implements OnItemSelectedListener {
        C04711() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            AccountDetailsTask.BN_to = AccountDetailsTask.this.bank_name_to[position];
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.AccountDetailsTask$2 */
    class C04722 implements OnItemSelectedListener {
        C04722() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            AccountDetailsTask.BN_to = AccountDetailsTask.this.bank_name_to[position];
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public AccountDetailsTask(Context context, String[] bank_name_to, ArrayAdapter<String> BankName_to, Spinner spState) {
        this.context = context;
        this.bank_name_to = bank_name_to;
        this.BankName_to = BankName_to;
        this.spState = spState;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... args) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.AccountDetailsUrl + "MemberId=" + CommonUtils.userName);
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

    @SuppressLint("ResourceType")
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                int i;
                if (jsonArray.getJSONObject(0).length() > 1) {
                    this.bank_name_to = new String[(jsonArray.length() + 1)];
                    this.bank_name_to[0] = "Select Bank";
                    for (i = 0; i < jsonArray.length(); i++) {
                        this.bank_name_to[i + 1] = jsonArray.getJSONObject(i).getString("FullName");
                    }
                    this.BankName_to = new ArrayAdapter(this.context, 17367048, this.bank_name_to);
                    this.BankName_to.setDropDownViewResource(17367049);
                    this.spState.setAdapter(this.BankName_to);
                    this.spState.setOnItemSelectedListener(new C04711());
                    return;
                }
                this.bank_name_to = new String[jsonArray.length()];
                for (i = 0; i < jsonArray.length(); i++) {
                    JSONObject jSONObject = jsonArray.getJSONObject(i);
                    this.bank_name_to[i] = "Select Bank";
                }
                this.BankName_to = new ArrayAdapter(this.context, 17367048, this.bank_name_to);
                this.BankName_to.setDropDownViewResource(17367049);
                this.spState.setAdapter(this.BankName_to);
                this.spState.setOnItemSelectedListener(new C04722());
                return;
            } catch (JSONException e) {
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, Toast.LENGTH_SHORT).show();
    }
}
