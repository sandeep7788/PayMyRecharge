package com.codeunite.paymyrch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.codeunite.paymyrch.R;

public class ApiBalanceAdapter extends ArrayAdapter<String> {
    private final String[] balance;
    private final Context context;
    private final String[] operator;

    public ApiBalanceAdapter(Context context, String[] operator, String[] balance) {
        super(context, R.layout.custom_apibal_row, operator);
        this.context = context;
        this.operator = operator;
        this.balance = balance;
    }

    @SuppressLint({"ViewHolder"})
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.custom_apibal_row, parent, false);
        TextView message = (TextView) rowView.findViewById(R.id.tvOperator);
        TextView datetime = (TextView) rowView.findViewById(R.id.tvBalance);
        if (this.operator[position] != null) {
            message.setText(this.operator[position]);
            datetime.setText(this.balance[position]);
        }
        return rowView;
    }
}
