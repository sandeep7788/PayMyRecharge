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

public class CustomDthAdapter extends BaseAdapter {
    public static int[] Dthimg = new int[]{R.drawable.dish, R.drawable.tata_sky, R.drawable.big_tv, R.drawable.videodth, R.drawable.sun_tv, R.drawable.airtel_tv};
    public ArrayList DthCodeList;
    ArrayList arrayList;
    Context context;
    GridOperatorModel gridOperatorModel;
    int imageId;
    public ArrayList localDtharrayList = new ArrayList();
    String operatorNmae;
    String operatorType;

    class viewholder {
        public ImageView imageView;
        public TextView textView;

        viewholder() {
        }
    }

    public CustomDthAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("Hi", "Conts.");
    }

    public int getCount() {
        LoadOperatrorModel loadOperatorModel = new LoadOperatrorModel();
        this.localDtharrayList = loadOperatorModel.getOperatorNames(this, this.arrayList);
        this.DthCodeList = loadOperatorModel.getOpcodeList();
        return this.localDtharrayList.size();
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
        this.gridOperatorModel = (GridOperatorModel) this.localDtharrayList.get(position);
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
        if (this.operatorNmae.equalsIgnoreCase("Dish TV")) {
            this.imageId = Dthimg[0];
        } else if (this.operatorNmae.equalsIgnoreCase("Tata Sky")) {
            this.imageId = Dthimg[1];
        } else if (this.operatorNmae.equalsIgnoreCase("Big Tv")) {
            this.imageId = Dthimg[2];
        } else if (this.operatorNmae.equalsIgnoreCase("Videocon DTH")) {
            this.imageId = Dthimg[3];
        } else if (this.operatorNmae.equalsIgnoreCase("Sun")) {
            this.imageId = Dthimg[4];
        } else if (this.operatorNmae.equalsIgnoreCase("Airtel DTH")) {
            this.imageId = Dthimg[5];
        }
        holder.textView.setText(this.operatorNmae);
        holder.imageView.setImageResource(this.imageId);
        return view;
    }
}
