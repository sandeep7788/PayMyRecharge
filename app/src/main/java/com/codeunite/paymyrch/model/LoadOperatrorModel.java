package com.codeunite.paymyrch.model;

import android.util.Log;
import com.codeunite.paymyrch.adapter.CustomDataCardAdapter;
import com.codeunite.paymyrch.adapter.CustomDthAdapter;
import com.codeunite.paymyrch.adapter.CustomElectricityAdapter;
import com.codeunite.paymyrch.adapter.CustomGasAdapter;
import com.codeunite.paymyrch.adapter.CustomGridOPAdapter;
import com.codeunite.paymyrch.adapter.CustomInsuranceAdapter;
import com.codeunite.paymyrch.adapter.CustomLandLineAdapter;
import com.codeunite.paymyrch.adapter.CustomPostpaidAdapter;
import java.util.ArrayList;

public class LoadOperatrorModel {
    ArrayList OpcodeList = new ArrayList();
    ArrayList OpnameList = new ArrayList();
    GridOperatorModel gridOperatorModel;
    String localoperatorType;

    public ArrayList getOperatorNames(Object ob, ArrayList arrayList) {
        int i;
        if (ob instanceof CustomGridOPAdapter) {
            for (i = 0; i < arrayList.size(); i++) {
                this.gridOperatorModel = (GridOperatorModel) arrayList.get(i);
                this.localoperatorType = this.gridOperatorModel.getOperatorType1();
                Log.d("OP", this.localoperatorType);
                if (this.localoperatorType.equalsIgnoreCase("PrePaid") && !this.OpnameList.contains(this.gridOperatorModel)) {
                    this.OpnameList.add(arrayList.get(i));
                    this.OpcodeList.add(this.gridOperatorModel.getOperatorCode1());
                    Log.d("OPNAME", this.gridOperatorModel.getOperatorName1());
                    Log.d("OPCODE", this.gridOperatorModel.getOperatorCode1());
                }
            }
        } else if (ob instanceof CustomPostpaidAdapter) {
            for (i = 0; i < arrayList.size(); i++) {
                this.gridOperatorModel = (GridOperatorModel) arrayList.get(i);
                this.localoperatorType = this.gridOperatorModel.getOperatorType1();
                Log.d("OP", this.localoperatorType);
                if (this.localoperatorType.equalsIgnoreCase("PostPaid") && !this.OpnameList.contains(this.gridOperatorModel)) {
                    this.OpnameList.add(arrayList.get(i));
                    this.OpcodeList.add(this.gridOperatorModel.getOperatorCode1());
                    Log.d("OPNAME", this.gridOperatorModel.getOperatorName1());
                    Log.d("OPCODE", this.gridOperatorModel.getOperatorCode1());
                }
            }
        } else if (ob instanceof CustomElectricityAdapter) {
            for (i = 0; i < arrayList.size(); i++) {
                this.gridOperatorModel = (GridOperatorModel) arrayList.get(i);
                this.localoperatorType = this.gridOperatorModel.getOperatorType1();
                Log.d("OP", this.localoperatorType);
                if (this.localoperatorType.equalsIgnoreCase("Electricity") && !this.OpnameList.contains(this.gridOperatorModel)) {
                    this.OpnameList.add(arrayList.get(i));
                    this.OpcodeList.add(this.gridOperatorModel.getOperatorCode1());
                    Log.d("OPNAME", this.gridOperatorModel.getOperatorName1());
                    Log.d("OPCODE", this.gridOperatorModel.getOperatorCode1());
                }
            }
        } else if (ob instanceof CustomGasAdapter) {
            for (i = 0; i < arrayList.size(); i++) {
                this.gridOperatorModel = (GridOperatorModel) arrayList.get(i);
                this.localoperatorType = this.gridOperatorModel.getOperatorType1();
                Log.d("OP", this.localoperatorType);
                if (this.localoperatorType.equalsIgnoreCase("Gas") && !this.OpnameList.contains(this.gridOperatorModel)) {
                    this.OpnameList.add(arrayList.get(i));
                    this.OpcodeList.add(this.gridOperatorModel.getOperatorCode1());
                    Log.d("OPNAME", this.gridOperatorModel.getOperatorName1());
                    Log.d("OPCODE", this.gridOperatorModel.getOperatorCode1());
                }
            }
        } else if (ob instanceof CustomInsuranceAdapter) {
            for (i = 0; i < arrayList.size(); i++) {
                this.gridOperatorModel = (GridOperatorModel) arrayList.get(i);
                this.localoperatorType = this.gridOperatorModel.getOperatorType1();
                Log.d("OP", this.localoperatorType);
                if (this.localoperatorType.equalsIgnoreCase("Insurance") && !this.OpnameList.contains(this.gridOperatorModel)) {
                    this.OpnameList.add(arrayList.get(i));
                    this.OpcodeList.add(this.gridOperatorModel.getOperatorCode1());
                    Log.d("OPNAME", this.gridOperatorModel.getOperatorName1());
                    Log.d("OPCODE", this.gridOperatorModel.getOperatorCode1());
                }
            }
        } else if (ob instanceof CustomLandLineAdapter) {
            for (i = 0; i < arrayList.size(); i++) {
                this.gridOperatorModel = (GridOperatorModel) arrayList.get(i);
                this.localoperatorType = this.gridOperatorModel.getOperatorType1();
                Log.d("OP", this.localoperatorType);
                if (this.localoperatorType.equalsIgnoreCase("Landline") && !this.OpnameList.contains(this.gridOperatorModel)) {
                    this.OpnameList.add(arrayList.get(i));
                    this.OpcodeList.add(this.gridOperatorModel.getOperatorCode1());
                    Log.d("OPNAME", this.gridOperatorModel.getOperatorName1());
                    Log.d("OPCODE", this.gridOperatorModel.getOperatorCode1());
                }
            }
        } else if (ob instanceof CustomDthAdapter) {
            for (i = 0; i < arrayList.size(); i++) {
                this.gridOperatorModel = (GridOperatorModel) arrayList.get(i);
                this.localoperatorType = this.gridOperatorModel.getOperatorType1();
                Log.d("OP", this.localoperatorType);
                if (this.localoperatorType.equalsIgnoreCase("DTH") && !this.OpnameList.contains(this.gridOperatorModel)) {
                    this.OpnameList.add(arrayList.get(i));
                    this.OpcodeList.add(this.gridOperatorModel.getOperatorCode1());
                    Log.d("OPNAME", this.gridOperatorModel.getOperatorName1());
                    Log.d("OPCODE", this.gridOperatorModel.getOperatorCode1());
                }
            }
        } else if (ob instanceof CustomDataCardAdapter) {
            for (i = 0; i < arrayList.size(); i++) {
                this.gridOperatorModel = (GridOperatorModel) arrayList.get(i);
                this.localoperatorType = this.gridOperatorModel.getOperatorType1();
                Log.d("sdgsgs", this.localoperatorType);
                if (this.localoperatorType.equalsIgnoreCase("Datacard") && !this.OpnameList.contains(this.gridOperatorModel)) {
                    this.OpnameList.add(arrayList.get(i));
                    this.OpcodeList.add(this.gridOperatorModel.getOperatorCode1());
                    Log.d("OPNAME", this.gridOperatorModel.getOperatorName1());
                    Log.d("OPCODE", this.gridOperatorModel.getOperatorCode1());
                }
            }
        }
        return this.OpnameList;
    }

    public ArrayList getOpcodeList() {
        return this.OpcodeList;
    }
}
