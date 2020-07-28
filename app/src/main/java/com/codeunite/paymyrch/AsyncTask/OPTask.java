package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;
import com.codeunite.paymyrch.adapter.CustomDataCardAdapter;
import com.codeunite.paymyrch.adapter.CustomDthAdapter;
import com.codeunite.paymyrch.adapter.CustomElectricityAdapter;
import com.codeunite.paymyrch.adapter.CustomGasAdapter;
import com.codeunite.paymyrch.adapter.CustomGridOPAdapter;
import com.codeunite.paymyrch.adapter.CustomInsuranceAdapter;
import com.codeunite.paymyrch.adapter.CustomLandLineAdapter;
import com.codeunite.paymyrch.adapter.CustomPostpaidAdapter;
import com.codeunite.paymyrch.model.GridOperatorModel;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OPTask extends AsyncTask<String, Void, ArrayList> {
    Context context;
    private int currentPosition;
    private CustomDataCardAdapter customDataCardAdapter;
    private CustomDthAdapter customDthAdapter;
    private CustomElectricityAdapter customElectricityAdapter;
    private CustomGasAdapter customGasAdapter;
    private CustomGridOPAdapter customGridOPAdapter;
    private CustomInsuranceAdapter customInsuranceAdapter;
    private CustomLandLineAdapter customLandLineAdapter;
    private CustomPostpaidAdapter customPostpaidAdapter;
    boolean exception = false;
    private GridView gridView;
    ArrayList<GridOperatorModel> operatorArrayList = new ArrayList();
    private String operatorCode;
    private String operatorName;
    private String operatorType;
    ProgressDialog progressDialog;

    public OPTask(Context context, ProgressDialog progressDialog, GridView gridView, int currentPosition) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.gridView = gridView;
        this.currentPosition = currentPosition;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (!this.exception) {
            this.progressDialog.setMessage("Please wait..");
            this.progressDialog.setIndeterminate(true);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }
    }

    protected ArrayList doInBackground(String... args) {
        String CurrentResult = args[0];
        if (CurrentResult != null) {
            Log.d("CURRENT RESULT", CurrentResult);
            try {
                JSONArray jsonArray = new JSONArray(CurrentResult);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    this.operatorName = jsonObject.getString("Operator_Name");
                    this.operatorCode = jsonObject.getString("Operator_code");
                    this.operatorType = jsonObject.getString("Operator_type");
                    Log.d("OP", this.operatorName);
                    GridOperatorModel gridOperatorModel = new GridOperatorModel();
                    gridOperatorModel.setOperatorName1(this.operatorName);
                    gridOperatorModel.setOperatorCode1(this.operatorCode);
                    gridOperatorModel.setOperatorType1(this.operatorType);
                    this.operatorArrayList.add(gridOperatorModel);
                    Log.d("Operator", String.valueOf(this.operatorArrayList));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this.operatorArrayList;
    }

    protected void onPostExecute(ArrayList operatorArrayList) {
        if (!this.exception) {
            this.progressDialog.dismiss();
        }
        if (operatorArrayList == null) {
            Toast.makeText(this.context, "Please try again later!!", 0).show();
        } else if (this.currentPosition == 0) {
            this.customGridOPAdapter = new CustomGridOPAdapter(this.context, operatorArrayList);
            this.gridView.setAdapter(this.customGridOPAdapter);
        } else if (this.currentPosition == 1) {
            this.customPostpaidAdapter = new CustomPostpaidAdapter(this.context, operatorArrayList);
            this.gridView.setAdapter(this.customPostpaidAdapter);
        } else if (this.currentPosition == 2) {
            this.customDthAdapter = new CustomDthAdapter(this.context, operatorArrayList);
            this.gridView.setAdapter(this.customDthAdapter);
        } else if (this.currentPosition == 3) {
            this.customLandLineAdapter = new CustomLandLineAdapter(this.context, operatorArrayList);
            this.gridView.setAdapter(this.customLandLineAdapter);
        } else if (this.currentPosition == 4) {
            this.customDataCardAdapter = new CustomDataCardAdapter(this.context, operatorArrayList);
            this.gridView.setAdapter(this.customDataCardAdapter);
        } else if (this.currentPosition == 5) {
            this.customInsuranceAdapter = new CustomInsuranceAdapter(this.context, operatorArrayList);
            this.gridView.setAdapter(this.customInsuranceAdapter);
        } else if (this.currentPosition == 6) {
            this.customGasAdapter = new CustomGasAdapter(this.context, operatorArrayList);
            this.gridView.setAdapter(this.customGasAdapter);
        } else if (this.currentPosition == 7) {
            this.customElectricityAdapter = new CustomElectricityAdapter(this.context, operatorArrayList);
            this.gridView.setAdapter(this.customElectricityAdapter);
        }
    }

    public Object getinstance() {
        if (this.currentPosition == 0) {
            return this.customGridOPAdapter;
        }
        if (this.currentPosition == 1) {
            return this.customPostpaidAdapter;
        }
        if (this.currentPosition == 2) {
            return this.customDthAdapter;
        }
        if (this.currentPosition == 3) {
            return this.customLandLineAdapter;
        }
        if (this.currentPosition == 4) {
            return this.customDataCardAdapter;
        }
        if (this.currentPosition == 5) {
            return this.customInsuranceAdapter;
        }
        if (this.currentPosition == 6) {
            return this.customGasAdapter;
        }
        if (this.currentPosition == 7) {
            return this.customElectricityAdapter;
        }
        return null;
    }
}
