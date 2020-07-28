package com.codeunite.paymyrch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.GridOperatorModel;
import com.codeunite.paymyrch.model.LoadOperatrorModel;
import java.util.ArrayList;

public class CustomInsuranceAdapter extends BaseAdapter {
    public static int[] InsuranceImg = new int[]{R.drawable.tata_aia_life_nsurance_company, R.drawable.icici_prudential_life_insurance_ltd, R.drawable.icici_prudential_life_insurance_ltd};
    public ArrayList InsuranceOpCodeList;
    ArrayList arrayList;
    Context context;
    GridOperatorModel gridOperatorModel;
    int imageId;
    public ArrayList localInsurancearrayList = new ArrayList();
    String operatorNmae;
    String operatorType;

    class viewholder {
        public ImageView imageView;
        public TextView textView;

        viewholder() {
        }
    }

    public CustomInsuranceAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("Hi", "Conts.");
    }

    public int getCount() {
        LoadOperatrorModel loadOperatorModel = new LoadOperatrorModel();
        this.localInsurancearrayList = loadOperatorModel.getOperatorNames(this, this.arrayList);
        this.InsuranceOpCodeList = loadOperatorModel.getOpcodeList();
        return this.localInsurancearrayList.size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        viewholder holder;
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        this.gridOperatorModel = (GridOperatorModel) this.localInsurancearrayList.get(position);
        if (view == null) {
            holder = new viewholder();
            Log.d("Size", String.valueOf(this.arrayList.size()));
            view = inflater.inflate(R.layout.custom_op_grid_view, parent, false);
            holder.imageView = (ImageView) view.findViewById(R.id.grid_image);
            holder.textView = (TextView) view.findViewById(R.id.grid_text);
            view.setTag(holder);
        } else {
            holder = (viewholder) view.getTag();
        }
        this.operatorNmae = this.gridOperatorModel.getOperatorName1();
        Log.d("Joy", this.operatorNmae);
        Log.d("Roy", String.valueOf(this.gridOperatorModel));
        if (this.operatorNmae.equalsIgnoreCase("Tata AIA Life Insurance")) {
            this.imageId = InsuranceImg[0];
        } else if (this.operatorNmae.equalsIgnoreCase("ICICI Prudential Life Ins")) {
            this.imageId = InsuranceImg[1];
        } else if (this.operatorNmae.equalsIgnoreCase("LIC Premium")) {
            this.imageId = InsuranceImg[2];
        }
        holder.textView.setText(this.operatorNmae);
        holder.imageView.setImageResource(this.imageId);
        return view;
    }
}
