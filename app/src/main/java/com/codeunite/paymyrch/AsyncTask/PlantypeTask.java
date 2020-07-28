package com.codeunite.paymyrch.AsyncTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.PlanAdapter;
import com.codeunite.paymyrch.utils.ServerUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cc.cloudist.acplibrary.ACProgressFlower;

public class PlantypeTask extends AsyncTask<String, Void, String> {
    public static String op_code;
    public static String planType;
    ACProgressFlower acProgressFlower;
    Activity activity;
    Context context;
    Dialog dialog;
    EditText edtamount;
    ExpandableListView listView;
    PlanAdapter planAdapter;
    ArrayAdapter<String> planName;
    String[] plan_name;
    Spinner sp_Plan;

    /* renamed from: com.codeunite.PayMyRecharge.AsyncTask.PlantypeTask$1 */
    class C04871 implements OnItemSelectedListener {
        C04871() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            PlantypeTask.planType = PlantypeTask.this.plan_name[position];
            new PlanTask(PlantypeTask.this.context, PlantypeTask.this.acProgressFlower, PlantypeTask.this.planAdapter, PlantypeTask.this.listView, PlantypeTask.this.activity, PlantypeTask.op_code, PlantypeTask.this.edtamount, PlantypeTask.this.dialog, PlantypeTask.planType).execute(new String[0]);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public PlantypeTask(Context context, String[] plan_name, ArrayAdapter<String> planName, Spinner sp_Plan, String opcode, ACProgressFlower acProgressFlower, ExpandableListView listView, EditText edtamount, Dialog dialog, PlanAdapter planAdapter, Activity activity) {

        this.context = context;
        this.plan_name = plan_name;
        this.planName = planName;
        this.sp_Plan = sp_Plan;
        op_code = opcode;
        this.acProgressFlower = acProgressFlower;
        this.listView = listView;
        this.edtamount = edtamount;
        this.dialog = dialog;
        this.planAdapter = planAdapter;
        this.activity = activity;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... args) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;
        //Log.d("checkashok", ServerUtils.BaseUrl + ServerUtils.PlanTypeUrl + "operatorCode="+op_code );
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.PlanTypeUrl + "operatorCode=" + op_code);

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
                //Log.d("checkashok",result);
                JSONArray jsonArray = new JSONArray(result);
                //Log.d("Error_Ashok_Check",result);

                this.plan_name = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    this.plan_name[i] = jsonArray.getJSONObject(i).getString("Category");
                }
                this.planName = new ArrayAdapter(this.context, 17367048, this.plan_name);
                this.planName.setDropDownViewResource(17367049);
                this.sp_Plan.setAdapter(this.planName);
                this.sp_Plan.setOnItemSelectedListener(new C04871());
                return;
            } catch (JSONException e) {
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
