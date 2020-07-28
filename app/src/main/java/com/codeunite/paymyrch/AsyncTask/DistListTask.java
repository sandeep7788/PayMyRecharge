package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class DistListTask extends AsyncTask<String, Void, String> {
    public static String distCode;
    Context context;
    ArrayAdapter<String> distName;
    String[] dist_id;
    String[] dist_name;
    ProgressDialog progressDialog;
    Spinner spDist;
    String stateCode;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.DistListTask$1 */
    class C04781 implements OnItemSelectedListener {
        C04781() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            DistListTask.distCode = DistListTask.this.dist_id[position];
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public DistListTask(Context context, ProgressDialog progressDialog, String[] dist_id, String[] dist_name, ArrayAdapter<String> distName, Spinner spDist, String stateCode) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.dist_id = dist_id;
        this.dist_name = dist_name;
        this.distName = distName;
        this.spDist = spDist;
        this.stateCode = stateCode;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.DistListUrl + "stateid=" + this.stateCode);
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
                this.dist_id = new String[jsonArray.length()];
                this.dist_name = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    this.dist_id[i] = jo.getString("Dist Id");
                    this.dist_name[i] = jo.getString("Dist Name");
                }
                this.distName = new ArrayAdapter(this.context, 17367048, this.dist_name);
                this.distName.setDropDownViewResource(17367049);
                this.spDist.setAdapter(this.distName);
                this.spDist.setOnItemSelectedListener(new C04781());
            } catch (JSONException e) {
            }
        }
    }
}
