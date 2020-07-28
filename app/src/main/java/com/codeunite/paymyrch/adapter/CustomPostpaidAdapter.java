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

public class CustomPostpaidAdapter extends BaseAdapter {
    public static int[] PostpaidopList = new int[]{R.drawable.airtel, R.drawable.idea, R.drawable.vodafone, R.drawable.reliance, R.drawable.reliance, R.drawable.bsnl, R.drawable.docomo, R.drawable.indicom, R.drawable.loop};
    public ArrayList PostpaidOpcode;
    ArrayList arrayList;
    Context context;
    GridOperatorModel gridOperatorModel;
    int imageId;
    public ArrayList localPostpaidarrayList = new ArrayList();
    String operatorNmae;
    String operatorType;

    class viewholder {
        public ImageView imageView;
        public TextView textView;

        viewholder() {
        }
    }

    public CustomPostpaidAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("Hi", "Conts.");
    }

    public int getCount() {
        LoadOperatrorModel loadOperatorModel = new LoadOperatrorModel();
        this.localPostpaidarrayList = loadOperatorModel.getOperatorNames(this, this.arrayList);
        this.PostpaidOpcode = loadOperatorModel.getOpcodeList();
        Log.d("Con", String.valueOf(this.localPostpaidarrayList.size()));
        Log.d("ConOP", String.valueOf(this.PostpaidOpcode.size()));
        return this.localPostpaidarrayList.size();
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
        this.gridOperatorModel = (GridOperatorModel) this.localPostpaidarrayList.get(position);
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
        if (this.operatorNmae.equalsIgnoreCase("Airtel")) {
            this.operatorNmae = "Airtel    PostPaid";
            this.imageId = PostpaidopList[0];
        } else if (this.operatorNmae.equalsIgnoreCase("Idea")) {
            this.operatorNmae = "Idea    PostPaid";
            this.imageId = PostpaidopList[1];
        } else if (this.operatorNmae.equalsIgnoreCase("Vodafone bill")) {
            this.operatorNmae = "Vodafone    PostPaid";
            this.imageId = PostpaidopList[2];
        } else if (this.operatorNmae.equalsIgnoreCase("Reliance GSM")) {
            this.operatorNmae = "Reliance    GSM";
            this.imageId = PostpaidopList[3];
        } else if (this.operatorNmae.equalsIgnoreCase("Reliance CDMA")) {
            this.imageId = PostpaidopList[4];
        } else if (this.operatorNmae.equalsIgnoreCase("BSNL Mobile")) {
            this.operatorNmae = "BSNL    PostPaid";
            this.imageId = PostpaidopList[5];
        } else if (this.operatorNmae.equalsIgnoreCase("Tata Docomo GSM")) {
            this.imageId = PostpaidopList[6];
        } else if (this.operatorNmae.equalsIgnoreCase("Tata Indicom")) {
            this.operatorNmae = "TATA    Indicom";
            this.imageId = PostpaidopList[7];
        } else if (this.operatorNmae.equalsIgnoreCase("LOOP Mobile")) {
            this.operatorNmae = "LOOP    PostPaid";
            this.imageId = PostpaidopList[8];
        }
        holder.textView.setText(this.operatorNmae);
        holder.imageView.setImageResource(this.imageId);
        return view;
    }
}
