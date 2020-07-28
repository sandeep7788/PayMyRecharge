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
import org.json.JSONObject;

public class Circle extends AsyncTask<String, Void, String> {
    public static String stateCode;
    Context context;
    ProgressDialog progressDialog;
    Spinner spState;
    ArrayAdapter<String> stateName;
    String[] state_id;
    String[] state_name;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.Circle$1 */
    class C04741 implements OnItemSelectedListener {
        C04741() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            Circle.stateCode = Circle.this.state_id[position];
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public Circle(Context context, ProgressDialog progressDialog, String[] state_id, String[] state_name, ArrayAdapter<String> stateName, Spinner spState) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.state_id = state_id;
        this.state_name = state_name;
        this.stateName = stateName;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.StasteListUrl + "stateid=" + stateCode);
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
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                this.state_id = new String[jsonArray.length()];
                this.state_name = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    this.state_id[i] = jo.getString("Sate Id");
                    this.state_name[i] = jo.getString("State Name");
                }
                this.stateName = new ArrayAdapter(this.context, 17367048, this.state_name);
                this.stateName.setDropDownViewResource(17367049);
                this.spState.setAdapter(this.stateName);
                this.spState.setOnItemSelectedListener(new C04741());
                return;
            } catch (JSONException e) {
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
