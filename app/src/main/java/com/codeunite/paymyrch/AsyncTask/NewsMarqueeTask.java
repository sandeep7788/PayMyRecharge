package com.codeunite.paymyrch.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
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

public class NewsMarqueeTask extends AsyncTask<String, Void, String> {
    Context context;
    public String news;
    ProgressDialog progressDialog;
    TextView tvNews;

    public NewsMarqueeTask(Context context, TextView tvNews, ProgressDialog progressDialog) {
        this.context = context;
        this.tvNews = tvNews;
        this.progressDialog = progressDialog;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... args) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            if (CommonUtils.role.equalsIgnoreCase("Distributor")) {
                url = new URL(ServerUtils.BaseUrl + ServerUtils.DealerNewsMarqueeUrl);
            } else if (CommonUtils.role.equalsIgnoreCase("Retailer")) {
                url = new URL(ServerUtils.BaseUrl + ServerUtils.NewsMarqueeUrl);
            }            urlConnection = (HttpURLConnection) url.openConnection();
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
                this.news = new JSONArray(result).getJSONObject(0).getString("Message");
                if (this.news.equals("")) {
                    this.tvNews.setVisibility(8);
                } else if (this.news.equalsIgnoreCase("null")) {
                    this.tvNews.setText("Welcome to Pay My Recharge");
                } else {
                    this.tvNews.setVisibility(0);
                    this.tvNews.setText(this.news);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
