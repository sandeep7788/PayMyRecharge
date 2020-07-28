package com.codeunite.paymyrch.AsyncTask;

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
import com.codeunite.paymyrch.utils.ServerUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;

public class BankNameListTask extends AsyncTask<String, Void, String> {
    public static String BN;
    ArrayAdapter<String> BankName;
    String[] bank_name;
    Context context;
    ProgressDialog progressDialog;
    Spinner spState;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.BankNameListTask$1 */
    class C04731 implements OnItemSelectedListener {
        C04731() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            BankNameListTask.BN = BankNameListTask.this.bank_name[position];
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public BankNameListTask(Context context, String[] bank_name, ArrayAdapter<String> BankName, Spinner spState) {
        this.context = context;
        this.bank_name = bank_name;
        this.BankName = BankName;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.BankListUrl);
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
                JSONArray jsonArray = new JSONArray(result);
                this.bank_name = new String[(jsonArray.length() + 1)];
                this.bank_name[0] = "Select Bank";
                for (int i = 0; i < jsonArray.length(); i++) {
                    this.bank_name[i + 1] = jsonArray.getJSONObject(i).getString("BankerMasterName");
                }
                this.BankName = new ArrayAdapter(this.context, 17367048, this.bank_name);
                this.BankName.setDropDownViewResource(17367049);
                this.spState.setAdapter(this.BankName);
                this.spState.setOnItemSelectedListener(new C04731());
                return;
            } catch (JSONException e) {
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
