package com.codeunite.paymyrch.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.internal.view.SupportMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import java.util.ArrayList;

public class DistributerOPStatusAdapter extends BaseAdapter {
    Context context;
    StatusModel model;
    String operatorName;
    String operatorStatus;
    ArrayList<StatusModel> statusModels;

    private class MyviewHolder {
        public TextView textView;
        public TextView textView1;

        private MyviewHolder() {
        }
    }

    public DistributerOPStatusAdapter(Context context, ArrayList<StatusModel> statusModels) {
        this.context = context;
        this.statusModels = statusModels;
    }

    public int getCount() {
        return this.statusModels.size();
    }

    public Object getItem(int position) {
        return this.statusModels.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyviewHolder myviewHolder;
        View v = convertView;
        if (v == null) {
            myviewHolder = new MyviewHolder();
            v = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.dealer_single_operator_status, parent, false);
            myviewHolder.textView = (TextView) v.findViewById(R.id.txt_operator_name1);
            myviewHolder.textView1 = (TextView) v.findViewById(R.id.txt_operator_status1);
            v.setTag(myviewHolder);
        } else {
            myviewHolder = (MyviewHolder) v.getTag();
        }
        StatusModel tempHolder = (StatusModel) this.statusModels.get(position);
        String name = tempHolder.operatoeName2;
        String status = tempHolder.operatorStatus2;
        myviewHolder.textView.setText(name);
        if (status.equalsIgnoreCase("Activated")) {
            myviewHolder.textView1.setText(status);
            myviewHolder.textView1.setTextColor(Color.parseColor("#1B5E20"));
        } else if (status.equalsIgnoreCase("Deactivated")) {
            myviewHolder.textView1.setTextColor(SupportMenu.CATEGORY_MASK);
            myviewHolder.textView1.setText(status);
        }
        return v;
    }
}
