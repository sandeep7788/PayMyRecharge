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

public class CustomDataCardAdapter extends BaseAdapter {
    public static int[] DataCardList = new int[]{R.drawable.reliance, R.drawable.reliance, R.drawable.reliance, R.drawable.indicom, R.drawable.indicom, R.drawable.mts, R.drawable.mts};
    public ArrayList DataCardOpcode;
    ArrayList arrayList;
    Context context;
    GridOperatorModel gridOperatorModel;
    int imageId;
    public ArrayList localDatACardarrayList = new ArrayList();
    String operatorNmae;
    String operatorType;

    class viewholder {
        public ImageView imageView;
        public TextView textView;

        viewholder() {
        }
    }

    public CustomDataCardAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("Hi", "Conts.");
    }

    public int getCount() {
        LoadOperatrorModel loadOperatorModel = new LoadOperatrorModel();
        this.localDatACardarrayList = loadOperatorModel.getOperatorNames(this, this.arrayList);
        this.DataCardOpcode = loadOperatorModel.getOpcodeList();
        Log.d("Con123", String.valueOf(this.localDatACardarrayList.size()));
        Log.d("ConOP", String.valueOf(this.DataCardOpcode.size()));
        return this.localDatACardarrayList.size();
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
        this.gridOperatorModel = (GridOperatorModel) this.localDatACardarrayList.get(position);
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
        if (this.operatorNmae.equalsIgnoreCase("Reliance NetConnect 1X")) {
            this.imageId = DataCardList[0];
        } else if (this.operatorNmae.equalsIgnoreCase("Reliance NetConnect 3G")) {
            this.imageId = DataCardList[1];
        } else if (this.operatorNmae.equalsIgnoreCase("Reliance NetConnect+")) {
            this.imageId = DataCardList[2];
        } else if (this.operatorNmae.equalsIgnoreCase("Tata Photon Whiz")) {
            this.imageId = DataCardList[3];
        } else if (this.operatorNmae.equalsIgnoreCase("Tata Photon+")) {
            this.imageId = DataCardList[4];
        } else if (this.operatorNmae.equalsIgnoreCase("MTS Mblaze")) {
            this.imageId = DataCardList[5];
        } else if (this.operatorNmae.equalsIgnoreCase("MTS Mbrowse")) {
            this.imageId = DataCardList[6];
        }
        holder.textView.setText(this.operatorNmae);
        holder.imageView.setImageResource(this.imageId);
        return view;
    }
}
