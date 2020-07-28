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

public class CustomGridOPAdapter extends BaseAdapter {
    public static int[] opList = new int[]{R.drawable.airtel, R.drawable.vodafone, R.drawable.bsnl, R.drawable.bsnl, R.drawable.reliance, R.drawable.reliance, R.drawable.aircel, R.drawable.idea, R.drawable.indicom, R.drawable.loop, R.drawable.docomo, R.drawable.docomo, R.drawable.virgin, R.drawable.mts, R.drawable.virgin, R.drawable.telenor, R.drawable.telenor, R.drawable.videocon_mobile, R.drawable.videocon_mobile, R.drawable.mtnl};
    public ArrayList Opcode = new ArrayList();
    ArrayList arrayList;
    Context context;
    GridOperatorModel gridOperatorModel;
    int imageId;
    public ArrayList localarrayList = new ArrayList();
    String operatorName;
    String operatorType;

    class viewholder {
        public ImageView imageView;
        public TextView textView;

        viewholder() {
        }
    }

    public CustomGridOPAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("Hi", "Conts.");
    }

    public int getCount() {
        LoadOperatrorModel loadOperatorModel = new LoadOperatrorModel();
        this.localarrayList = loadOperatorModel.getOperatorNames(this, this.arrayList);
        this.Opcode = loadOperatorModel.getOpcodeList();
        return this.localarrayList.size();
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
        this.gridOperatorModel = (GridOperatorModel) this.localarrayList.get(position);
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
        this.operatorName = this.gridOperatorModel.getOperatorName1();
        Log.d("Joy", this.operatorName);
        Log.d("Roy", String.valueOf(this.gridOperatorModel));
        if (this.operatorName.equalsIgnoreCase("Airtel")) {
            this.imageId = opList[0];
        } else if (this.operatorName.equalsIgnoreCase("Vodafone")) {
            this.imageId = opList[1];
        } else if (this.operatorName.equalsIgnoreCase("BSNL TopUp")) {
            this.imageId = opList[2];
        } else if (this.operatorName.equalsIgnoreCase("BSNL Special")) {
            this.operatorName = "BSNL(STV)";
            this.imageId = opList[3];
        } else if (this.operatorName.equalsIgnoreCase("Reliance CDMA")) {
            this.operatorName = "Reli. CDMA";
            this.imageId = opList[4];
        } else if (this.operatorName.equalsIgnoreCase("Reliance GSM")) {
            this.imageId = opList[5];
        } else if (this.operatorName.equalsIgnoreCase("Aircel")) {
            this.imageId = opList[6];
        } else if (this.operatorName.equalsIgnoreCase("Idea")) {
            this.imageId = opList[7];
        } else if (this.operatorName.equalsIgnoreCase("Tata Indicom")) {
            this.imageId = opList[8];
        } else if (this.operatorName.equalsIgnoreCase("Loop Mobile")) {
            this.imageId = opList[9];
        } else if (this.operatorName.equalsIgnoreCase("Tata Docomo")) {
            this.imageId = opList[10];
        } else if (this.operatorName.equalsIgnoreCase("Tata Docomo Special")) {
            this.operatorName = "Docomo(STV)";
            this.imageId = opList[11];
        } else if (this.operatorName.equalsIgnoreCase("Virgin CDMA")) {
            this.imageId = opList[12];
        } else if (this.operatorName.equalsIgnoreCase("MTS")) {
            this.imageId = opList[13];
        } else if (this.operatorName.equalsIgnoreCase("Virgin GSM")) {
            this.imageId = opList[14];
        } else if (this.operatorName.equalsIgnoreCase("Uninor")) {
            this.imageId = opList[15];
        } else if (this.operatorName.equalsIgnoreCase("Uninor Special")) {
            this.imageId = opList[16];
        } else if (this.operatorName.equalsIgnoreCase("Videocon")) {
            this.imageId = opList[17];
        } else if (this.operatorName.equalsIgnoreCase("Videocon Special")) {
            this.operatorName = "Videocon(STV)";
            this.imageId = opList[18];
        } else if (this.operatorName.equalsIgnoreCase("MTNL Top Up")) {
            this.imageId = opList[19];
        }
        holder.textView.setText(this.operatorName);
        holder.imageView.setImageResource(this.imageId);
        return view;
    }
}
