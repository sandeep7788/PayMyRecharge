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

public class CustomElectricityAdapter extends BaseAdapter {
    public static int[] ElectImg = new int[]{R.drawable.bses_rajdhani_power_limited, R.drawable.bses_yamuna_power_limited, R.drawable.tata_power_ddl, R.drawable.reliance_energy, R.drawable.ic_password, R.drawable.north_bihar, R.drawable.south_bihar, R.drawable.brihan_mumbai_electric_supply_and_transport_undertaking, R.drawable.ajmer_vidyut_vitran_nigam_limited, R.drawable.bangalore_electricity_supply_company, R.drawable.cesc, R.drawable.cseb, R.drawable.jaipur, R.drawable.jodhpur_vidyut_vitran_nigam_limited, R.drawable.madhya_pradesh_madhya_kshetra_vidyut_vitaran_company_limited_bhopal, R.drawable.msede_limited};
    public ArrayList ElecOpcodeList;
    ArrayList arrayList;
    Context context;
    GridOperatorModel gridOperatorModel;
    int imageId;
    public ArrayList locaElectricitylarrayList = new ArrayList();
    String operatorNmae;
    String operatorType;

    class viewholder {
        public ImageView imageView;
        public TextView textView;

        viewholder() {
        }
    }

    public CustomElectricityAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.d("Hi", "Conts.");
    }

    public int getCount() {
        LoadOperatrorModel loadOperatorModel = new LoadOperatrorModel();
        this.locaElectricitylarrayList = loadOperatorModel.getOperatorNames(this, this.arrayList);
        this.ElecOpcodeList = loadOperatorModel.getOpcodeList();
        return this.locaElectricitylarrayList.size();
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
        this.gridOperatorModel = (GridOperatorModel) this.locaElectricitylarrayList.get(position);
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
        if (this.operatorNmae.equalsIgnoreCase("BSES Rajdhani Power-DELHI")) {
            this.operatorNmae = "BSES Rajdhani";
            this.imageId = ElectImg[0];
        } else if (this.operatorNmae.equalsIgnoreCase("BSES Yamuna Power-DELHI")) {
            this.operatorNmae = "BSES Yamuna";
            this.imageId = ElectImg[1];
        } else if (this.operatorNmae.equalsIgnoreCase("Tata Power Delhi Limited - Delhi")) {
            this.operatorNmae = "Tata Power";
            this.imageId = ElectImg[2];
        } else if (this.operatorNmae.equalsIgnoreCase("Reliance Energy Limited")) {
            this.operatorNmae = "Reliance Energy";
            this.imageId = ElectImg[3];
        } else if (this.operatorNmae.equalsIgnoreCase("PUNJAB STATE POWER CORPORATION LIMITED")) {
            this.operatorNmae = "PSPCL";
            this.imageId = ElectImg[4];
        } else if (this.operatorNmae.equalsIgnoreCase("North Bihar Electricity")) {
            this.operatorNmae = "North Bihar";
            this.imageId = ElectImg[5];
        } else if (this.operatorNmae.equalsIgnoreCase("South Bihar Electricity")) {
            this.operatorNmae = "South Bihar";
            this.imageId = ElectImg[6];
        } else if (this.operatorNmae.equalsIgnoreCase("BEST Electricity")) {
            this.operatorNmae = "BEST";
            this.imageId = ElectImg[7];
        } else if (this.operatorNmae.equalsIgnoreCase("Ajmer Vidyut Vitran Nigam - RAJASTHAN")) {
            this.operatorNmae = "Ajmer Vidyut";
            this.imageId = ElectImg[8];
        } else if (this.operatorNmae.equalsIgnoreCase("BESCOM - BENGALURU")) {
            this.operatorNmae = "BESCOM";
            this.imageId = ElectImg[9];
        } else if (this.operatorNmae.equalsIgnoreCase("CESC - WEST BENGAL")) {
            this.operatorNmae = "CESC";
            this.imageId = ElectImg[10];
        } else if (this.operatorNmae.equalsIgnoreCase("CSEB - CHHATTISGARH")) {
            this.operatorNmae = "CSEB";
            this.imageId = ElectImg[11];
        } else if (this.operatorNmae.equalsIgnoreCase("Jaipur Vidyut Vitran Nigam - RAJASTHAN")) {
            this.operatorNmae = "Jaipur Vidyut";
            this.imageId = ElectImg[12];
        } else if (this.operatorNmae.equalsIgnoreCase("Jodhpur Vidyut Vitran Nigam - RAJASTHAN")) {
            this.operatorNmae = "Jodhpur Vidyut";
            this.imageId = ElectImg[13];
        } else if (this.operatorNmae.equalsIgnoreCase("Madhya Kshetra Vitaran - MADHYA PRADESH")) {
            this.operatorNmae = "Madhya Kshetra";
            this.imageId = ElectImg[14];
        } else if (this.operatorNmae.equalsIgnoreCase("MSEDC - MAHARASHTRA")) {
            this.operatorNmae = "MSEDC";
            this.imageId = ElectImg[15];
        }
        holder.textView.setText(this.operatorNmae);
        holder.imageView.setImageResource(this.imageId);
        return view;
    }
}
