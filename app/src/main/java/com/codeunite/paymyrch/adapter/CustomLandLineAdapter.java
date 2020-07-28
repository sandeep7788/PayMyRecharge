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

public class CustomLandLineAdapter extends BaseAdapter {
    public static int[] LandLineImg = new int[]{R.drawable.bsnl, R.drawable.mtnl, R.drawable.airtel, R.drawable.docomo, R.drawable.reliance};
    public ArrayList LandlineOpCodeList;
    ArrayList arrayList;
    Context context;
    GridOperatorModel gridOperatorModel;
    int imageId;
    public ArrayList locaLandLinelarrayList = new ArrayList();
    String operatorNmae;
    String operatorType;

    class viewholder {
        public ImageView imageView;
        public TextView textView;

        viewholder() {
        }
    }

    public CustomLandLineAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("Hi", "Conts.");
    }

    public int getCount() {
        LoadOperatrorModel loadOperatorModel = new LoadOperatrorModel();
        this.locaLandLinelarrayList = loadOperatorModel.getOperatorNames(this, this.arrayList);
        this.LandlineOpCodeList = loadOperatorModel.getOpcodeList();
        return this.locaLandLinelarrayList.size();
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
        this.gridOperatorModel = (GridOperatorModel) this.locaLandLinelarrayList.get(position);
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
        if (this.operatorNmae.equalsIgnoreCase("BSNL Landline")) {
            this.operatorNmae = "Bsnl    LandLine";
            this.imageId = LandLineImg[0];
        } else if (this.operatorNmae.equalsIgnoreCase("MTNL Landline")) {
            this.imageId = LandLineImg[1];
        } else if (this.operatorNmae.equalsIgnoreCase("Airtel Landline")) {
            this.operatorNmae = "Airtel    LandLine";
            this.imageId = LandLineImg[2];
        } else if (this.operatorNmae.equalsIgnoreCase("DOCOMO Landline")) {
            this.operatorNmae = "Docomo LandLine";
            this.imageId = LandLineImg[3];
        } else if (this.operatorNmae.equalsIgnoreCase("Reliance")) {
            this.operatorNmae = "Reliance LandLine";
            this.imageId = LandLineImg[4];
        }
        holder.textView.setText(this.operatorNmae);
        holder.imageView.setImageResource(this.imageId);
        return view;
    }
}
