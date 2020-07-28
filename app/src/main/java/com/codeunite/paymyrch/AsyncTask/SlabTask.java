package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
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

public class SlabTask extends AsyncTask<String, Void, String> {
    Context context;
    ProgressDialog progressDialog;
    public String retailerCode;
    ArrayAdapter<String> retailerid;
    public String[] slabcode;
    public String[] slablist;
    Spinner spRetailer;
    TextInputLayout tilretailerName;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.SlabTask$1 */
    class C04921 implements OnItemSelectedListener {
        C04921() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            SlabTask.this.retailerCode = SlabTask.this.slablist[position];
            if (SlabTask.this.retailerCode.equalsIgnoreCase("Retailer")) {
                SlabTask.this.tilretailerName.setHint("Retailer Name");
            } else if (SlabTask.this.retailerCode.equalsIgnoreCase("Distributor")) {
                SlabTask.this.tilretailerName.setHint("Distributor Name");
            } else {
                SlabTask.this.tilretailerName.setHint("Name");
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public SlabTask(Context context, ProgressDialog progressDialog, ArrayAdapter<String> retailerid, Spinner spRetailer, TextInputLayout tilretailerName) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.retailerid = retailerid;
        this.spRetailer = spRetailer;
        this.tilretailerName = tilretailerName;
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.DealerRetaikerSlabListUrl + "&mobile=" + CommonUtils.userName);
            System.out.println("aksjdghakd"+"-"+url);
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
                this.slablist = new String[jsonArray.length()];
                this.slabcode = new String[jsonArray.length()];
                this.slablist = new String[(jsonArray.length() + 1)];
                this.slablist[0] = "Select Package";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String slabName = jo.getString("PackageName");
                    this.slablist[i + 1] = jo.getString("PackageName");
                }
                this.retailerid = new ArrayAdapter(this.context, 17367048, this.slablist);
                this.retailerid.setDropDownViewResource(17367049);
                this.spRetailer.setAdapter(this.retailerid);
                this.spRetailer.setOnItemSelectedListener(new C04921());
                return;
            } catch (JSONException e) {
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 1).show();
    }
}
