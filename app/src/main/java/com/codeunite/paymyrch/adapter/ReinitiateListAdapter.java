package com.codeunite.paymyrch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.ReinitializeDataHolder;
import java.util.ArrayList;

public class ReinitiateListAdapter extends BaseAdapter {
    private String accountNo;
    private String amount;
    private Button btn_ok;
    private Context context;
    private ArrayList<ReinitializeDataHolder> reinitializeDataList;
    Integer selected_position = Integer.valueOf(-1);

    private class MyviewHolder {
        public TextView accountNoTv;
        public TextView amountTv;
        public CheckBox checkBox;

        private MyviewHolder() {
        }
    }

    public ReinitiateListAdapter(Context context, ArrayList<ReinitializeDataHolder> reinitializeDataList, Button btn_ok) {
        this.context = context;
        this.reinitializeDataList = reinitializeDataList;
        this.btn_ok = btn_ok;
    }

    public int getCount() {
        return this.reinitializeDataList.size();
    }

    public Object getItem(int position) {
        return this.reinitializeDataList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyviewHolder myviewHolder;
        View v = convertView;
        if (v == null) {
            myviewHolder = new MyviewHolder();
            v = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.single_row_reinitiatelist, parent, false);
            myviewHolder.accountNoTv = (TextView) v.findViewById(R.id.text_accountNo);
            myviewHolder.amountTv = (TextView) v.findViewById(R.id.text_amount);
            myviewHolder.checkBox = (CheckBox) v.findViewById(R.id.checkBox1);
            v.setTag(myviewHolder);
        } else {
            myviewHolder = (MyviewHolder) v.getTag();
        }
        ReinitializeDataHolder tempHolder = (ReinitializeDataHolder) this.reinitializeDataList.get(position);
        myviewHolder.accountNoTv.setText("Account No: " + tempHolder.reinitiateAccountNo);
        myviewHolder.amountTv.setText("Amount: " + tempHolder.reinitiateAmount);
        return v;
    }
}
