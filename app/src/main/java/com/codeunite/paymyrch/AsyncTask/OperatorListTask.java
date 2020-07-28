package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import com.codeunite.paymyrch.activity.AfterLoginActivity;
import com.codeunite.paymyrch.helper.DatabaseHelper;
import com.codeunite.paymyrch.helper.Sqlhandler;
import com.codeunite.paymyrch.model.OperatorModel;
import com.codeunite.paymyrch.utils.CommonUtils;
import com.codeunite.paymyrch.utils.ServerUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OperatorListTask extends AsyncTask<String, Void, String> {
    public final OperatorListTask CurrentOperatorListObject;
    public String OperatorsResult;
    Context context;
    DatabaseHelper db;
    AfterLoginActivity loginActivity = new AfterLoginActivity();
    private String operatorCode;
    private String operatorName;
    private String operatorType;
    ProgressDialog progressDialog;
    Sqlhandler sqlhandler;

    public OperatorListTask(Context context, DatabaseHelper db) {
        this.context = context;
        this.db = db;
        this.CurrentOperatorListObject = this;
        this.sqlhandler = new Sqlhandler(context);
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
            url = new URL(ServerUtils.BaseUrl + ServerUtils.OperatorListUrl);
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
        this.OperatorsResult = result;
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                OperatorModel operatorModel = new OperatorModel();
                this.sqlhandler.executeQuery("DELETE FROM Operator");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    this.operatorName = jsonObject.getString("Operator_Name");
                    this.operatorCode = jsonObject.getString("Operator_code");
                    this.operatorType = jsonObject.getString("Operator_type");
                    operatorModel.setOperatorName(this.operatorName);
                    operatorModel.setOperatorCode(this.operatorCode);
                    operatorModel.setOperatorType(this.operatorType);
                    this.db.createOperatorList(operatorModel);
                }
                for (OperatorModel operatorModel1 : this.db.getAllOperators()) {
                    Log.d("Operator_Name", operatorModel1.getOperatorName());
                }
                load_oprator();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void load_oprator() {
        try {
            Cursor c1 = this.sqlhandler.selectQuery("SELECT * FROM Operator");
            CommonUtils.mob_spinnername = new String[1];
            CommonUtils.mob_spinnercode = new String[1];
            CommonUtils.dth_spinnername = new String[1];
            CommonUtils.dth_spinnercode = new String[1];
            CommonUtils.data_spinnername = new String[1];
            CommonUtils.data_spinnercode = new String[1];
            CommonUtils.post_spinnername = new String[1];
            CommonUtils.post_spinnercode = new String[1];
            CommonUtils.landline_spinnername = new String[1];
            CommonUtils.landline_spinnercode = new String[1];
            CommonUtils.electricity_spinnername = new String[1];
            CommonUtils.electricity_spinnercode = new String[1];
            CommonUtils.gas_spinnername = new String[1];
            CommonUtils.gas_spinnercode = new String[1];
            CommonUtils.insurance_spinnername = new String[1];
            CommonUtils.insurance_spinnercode = new String[1];
            CommonUtils.all_spinnername = new String[1];
            CommonUtils.all_spinnercode = new String[1];
            CommonUtils.mob_spinnername[0] = "Select Operator";
            CommonUtils.mob_spinnercode[0] = "0";
            CommonUtils.dth_spinnername[0] = "Select Operator";
            CommonUtils.dth_spinnercode[0] = "0";
            CommonUtils.data_spinnername[0] = "Select Operator";
            CommonUtils.data_spinnercode[0] = "0";
            CommonUtils.post_spinnername[0] = "Select Operator";
            CommonUtils.post_spinnercode[0] = "0";
            CommonUtils.landline_spinnername[0] = "Select Operator";
            CommonUtils.landline_spinnercode[0] = "0";
            CommonUtils.electricity_spinnername[0] = "Select Operator";
            CommonUtils.electricity_spinnercode[0] = "0";
            CommonUtils.gas_spinnername[0] = "Select Operator";
            CommonUtils.gas_spinnercode[0] = "0";
            CommonUtils.insurance_spinnername[0] = "Select Operator";
            CommonUtils.insurance_spinnercode[0] = "0";
            CommonUtils.all_spinnername[0] = "Select Operator";
            CommonUtils.all_spinnercode[0] = "0";
            if (c1 == null || c1.getCount() == 0) {
                new OperatorListTask(this.context, this.db).execute(new String[0]);
            } else if (c1.moveToFirst()) {
                CommonUtils.operator_status = true;
                int m = 1;
                int d = 1;
                int dc = 1;
                int p = 1;
                int l = 1;
                int a = 1;
                int e = 1;
                int g = 1;
                int i = 1;
                do {
                    String type = c1.getString(c1.getColumnIndex(DatabaseHelper.KEY_OPERATOR_TYPE));
                    String name = c1.getString(c1.getColumnIndex(DatabaseHelper.KEY_OPERATOR_NAME));
                    String code = c1.getString(c1.getColumnIndex(DatabaseHelper.KEY_OPERATOR_CODE));
                    CommonUtils.all_spinnername = (String[]) Arrays.copyOf(CommonUtils.all_spinnername, CommonUtils.all_spinnername.length + 1);
                    CommonUtils.all_spinnercode = (String[]) Arrays.copyOf(CommonUtils.all_spinnercode, CommonUtils.all_spinnercode.length + 1);
                    CommonUtils.all_spinnername[a] = name;
                    CommonUtils.all_spinnercode[a] = code;
                    a++;
                    if (type.equalsIgnoreCase("Prepaid")) {
                        CommonUtils.mob_spinnername = (String[]) Arrays.copyOf(CommonUtils.mob_spinnername, CommonUtils.mob_spinnername.length + 1);
                        CommonUtils.mob_spinnercode = (String[]) Arrays.copyOf(CommonUtils.mob_spinnercode, CommonUtils.mob_spinnercode.length + 1);
                        CommonUtils.mob_spinnername[m] = name;
                        CommonUtils.mob_spinnercode[m] = code;
                        m++;
                    } else if (type.equalsIgnoreCase("DTH")) {
                        CommonUtils.dth_spinnername = (String[]) Arrays.copyOf(CommonUtils.dth_spinnername, CommonUtils.dth_spinnername.length + 1);
                        CommonUtils.dth_spinnercode = (String[]) Arrays.copyOf(CommonUtils.dth_spinnercode, CommonUtils.dth_spinnercode.length + 1);
                        CommonUtils.dth_spinnername[d] = name;
                        CommonUtils.dth_spinnercode[d] = code;
                        d++;
                    } else if (type.equalsIgnoreCase("Postpaid")) {
                        CommonUtils.post_spinnername = (String[]) Arrays.copyOf(CommonUtils.post_spinnername, CommonUtils.post_spinnername.length + 1);
                        CommonUtils.post_spinnercode = (String[]) Arrays.copyOf(CommonUtils.post_spinnercode, CommonUtils.post_spinnercode.length + 1);
                        CommonUtils.post_spinnername[p] = name;
                        CommonUtils.post_spinnercode[p] = code;
                        p++;
                    } else if (type.equalsIgnoreCase("Landline")) {
                        CommonUtils.landline_spinnername = (String[]) Arrays.copyOf(CommonUtils.landline_spinnername, CommonUtils.landline_spinnername.length + 1);
                        CommonUtils.landline_spinnercode = (String[]) Arrays.copyOf(CommonUtils.landline_spinnercode, CommonUtils.landline_spinnercode.length + 1);
                        CommonUtils.landline_spinnername[l] = name;
                        CommonUtils.landline_spinnercode[l] = code;
                        l++;
                    } else if (type.equalsIgnoreCase("Electricity")) {
                        CommonUtils.electricity_spinnername = (String[]) Arrays.copyOf(CommonUtils.electricity_spinnername, CommonUtils.electricity_spinnername.length + 1);
                        CommonUtils.electricity_spinnercode = (String[]) Arrays.copyOf(CommonUtils.electricity_spinnercode, CommonUtils.electricity_spinnercode.length + 1);
                        CommonUtils.electricity_spinnername[e] = name;
                        CommonUtils.electricity_spinnercode[e] = code;
                        e++;
                    } else if (type.equalsIgnoreCase("GAS")) {
                        CommonUtils.gas_spinnername = (String[]) Arrays.copyOf(CommonUtils.gas_spinnername, CommonUtils.gas_spinnername.length + 1);
                        CommonUtils.gas_spinnercode = (String[]) Arrays.copyOf(CommonUtils.gas_spinnercode, CommonUtils.gas_spinnercode.length + 1);
                        CommonUtils.gas_spinnername[g] = name;
                        CommonUtils.gas_spinnercode[g] = code;
                        g++;
                    } else if (type.equalsIgnoreCase("Insurance")) {
                        CommonUtils.insurance_spinnername = (String[]) Arrays.copyOf(CommonUtils.insurance_spinnername, CommonUtils.insurance_spinnername.length + 1);
                        CommonUtils.insurance_spinnercode = (String[]) Arrays.copyOf(CommonUtils.insurance_spinnercode, CommonUtils.insurance_spinnercode.length + 1);
                        CommonUtils.insurance_spinnername[i] = name;
                        CommonUtils.insurance_spinnercode[i] = code;
                        i++;
                    } else if (type.equalsIgnoreCase("Datacard")) {
                        CommonUtils.data_spinnername = (String[]) Arrays.copyOf(CommonUtils.data_spinnername, CommonUtils.data_spinnername.length + 1);
                        CommonUtils.data_spinnercode = (String[]) Arrays.copyOf(CommonUtils.data_spinnercode, CommonUtils.data_spinnercode.length + 1);
                        CommonUtils.data_spinnername[dc] = name;
                        CommonUtils.data_spinnercode[dc] = code;
                        dc++;
                    }
                } while (c1.moveToNext());
            }
            c1.close();
        } catch (Exception e2) {
        }
    }
}
