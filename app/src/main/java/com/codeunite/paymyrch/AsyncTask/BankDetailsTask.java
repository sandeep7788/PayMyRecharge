package com.codeunite.paymyrch.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.BankdetailAdapter;
import com.codeunite.paymyrch.fragment.BankListFragment;
import com.codeunite.paymyrch.model.BankDetailHolder;
import com.codeunite.paymyrch.utils.ServerUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BankDetailsTask extends AsyncTask<String, Void, String> {
    public String AccountHolderName;
    public String AccountNumber;
    public String AccountType;
    public String BankAddress;
    public String BankName;
    public String BranchName;
    public String IfscCode;
    ArrayList<BankDetailHolder> bankDetailList;
    private BankListFragment bankListFragment;
    BankdetailAdapter bankdetailAdapter;
    Context context;
    ListView listView;

    public BankDetailsTask(Context context, ListView listView, BankListFragment bankListFragment) {
        this.context = context;
        this.listView = listView;
        this.bankListFragment = bankListFragment;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.bankDetailList = new ArrayList();
    }


    @Override
    protected String doInBackground(String... args) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(ServerUtils.BaseUrl + ServerUtils.BankDetailsUrl);
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    this.BankName = jsonObject.getString("banknm");
                    this.BranchName = jsonObject.getString("branch_nm");
                    this.IfscCode = jsonObject.getString("ifsccode");
                    this.AccountNumber = jsonObject.getString("acno");
                    this.AccountType = jsonObject.getString("actype");
                    this.AccountHolderName = jsonObject.getString("holdername");
                    this.BankAddress = jsonObject.getString("address");
                    this.bankDetailList.add(new BankDetailHolder(this.BankName, this.BranchName, this.IfscCode, this.AccountNumber, this.AccountType, this.AccountHolderName, this.BankAddress));
                }
                this.bankListFragment.setBankDetailList(this.bankDetailList);
                this.bankdetailAdapter = new BankdetailAdapter(this.context, this.bankDetailList);
                this.listView.setAdapter(this.bankdetailAdapter);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this.context, R.string.checkInternetMessage, 0).show();
    }
}
