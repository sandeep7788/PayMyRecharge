package com.codeunite.paymyrch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.codeunite.paymyrch.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfflineImageSpinnerAdapter extends BaseAdapter {
    String[] OeratorCode;
    int[] OperatorImage;
    String[] OperatorName;
    Context context;

    public OfflineImageSpinnerAdapter(Context context, String[] opName, String[] opCode, int[] opImage) {
        this.context = context;
        this.OperatorName = opName;
        this.OeratorCode = opCode;
        this.OperatorImage = opImage;
    }

    public int getCount() {
        return this.OperatorImage.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.offline_spinner_single_item, viewGroup, false);
        TextView opName = (TextView) view.findViewById(R.id.off_op_name);
        TextView opCode = (TextView) view.findViewById(R.id.off_op_code);
        ((CircleImageView) view.findViewById(R.id.off_op_image)).setImageResource(this.OperatorImage[i]);
        opName.setText(this.OperatorName[i]);
        opCode.setText(this.OeratorCode[i]);
        return view;
    }
}
