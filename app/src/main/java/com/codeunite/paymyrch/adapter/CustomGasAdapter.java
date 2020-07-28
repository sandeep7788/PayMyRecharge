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

public class CustomGasAdapter extends BaseAdapter {
    public static int[] GasImg = new int[]{R.drawable.mahanagar_gas_limited, R.drawable.adani_gas, R.drawable.gujarat_gas_company_limited, R.drawable.indraprasth_gas};
    public ArrayList GasOpCodeList;
    ArrayList arrayList;
    Context context;
    GridOperatorModel gridOperatorModel;
    int imageId;
    public ArrayList localGasarrayList = new ArrayList();
    String operatorNmae;
    String operatorType;

    class viewholder {
        public ImageView imageView;
        public TextView textView;

        viewholder() {
        }
    }

    public CustomGasAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("Hi", "Conts.");
    }

    public int getCount() {
        LoadOperatrorModel loadOperatorModel = new LoadOperatrorModel();
        this.localGasarrayList = loadOperatorModel.getOperatorNames(this, this.arrayList);
        this.GasOpCodeList = loadOperatorModel.getOpcodeList();
        return this.localGasarrayList.size();
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
        this.gridOperatorModel = (GridOperatorModel) this.localGasarrayList.get(position);
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
        if (this.operatorNmae.equalsIgnoreCase("Mahanagar Gas")) {
            this.imageId = GasImg[0];
        } else if (this.operatorNmae.equalsIgnoreCase("Adani Gas")) {
            this.operatorNmae = "Adani             Gas";
            this.imageId = GasImg[1];
        } else if (this.operatorNmae.equalsIgnoreCase("Gujarat Gas")) {
            this.operatorNmae = "Gujarat              Gas";
            this.imageId = GasImg[2];
        } else if (this.operatorNmae.equalsIgnoreCase("Indraprastha Gas")) {
            this.imageId = GasImg[3];
        }
        holder.textView.setText(this.operatorNmae);
        holder.imageView.setImageResource(this.imageId);
        return view;
    }
}
