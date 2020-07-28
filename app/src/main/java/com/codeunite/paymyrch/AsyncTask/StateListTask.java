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

public class StateListTask extends AsyncTask<String, Void, String> {
    public static String stateCode;
    Context context;
    ArrayAdapter<String> distName;
    String[] dist_id;
    String[] dist_name;
    ProgressDialog progressDialog;
    Spinner spDist;
    Spinner spState;
    ArrayAdapter<String> stateName;
    String[] state_id;
    String[] state_name;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.StateListTask$1 */
    class C04931 implements OnItemSelectedListener {
        C04931() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            StateListTask.stateCode = StateListTask.this.state_id[position];
            new DistListTask(StateListTask.this.context, StateListTask.this.progressDialog, StateListTask.this.dist_id, StateListTask.this.dist_name, StateListTask.this.distName, StateListTask.this.spDist, StateListTask.stateCode).execute(new String[0]);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public StateListTask(Context context, ProgressDialog progressDialog, String[] state_id, String[] state_name, ArrayAdapter<String> stateName, Spinner spState, String[] dist_id, String[] dist_name, ArrayAdapter<String> distName, Spinner spDist) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.state_id = state_id;
        this.state_name = state_name;
        this.stateName = stateName;
        this.spState = spState;
        this.dist_id = dist_id;
        this.dist_name = dist_name;
        this.distName = distName;
        this.spDist = spDist;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.StasteListUrl);
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
                this.spState.setOnItemSelectedListener(new C04931());
                return;
            } catch (JSONException e) {
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
