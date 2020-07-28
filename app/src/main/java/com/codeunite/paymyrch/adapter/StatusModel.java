package com.codeunite.paymyrch.adapter;

import android.util.Log;

public class StatusModel {
    public String operatoeName2;
    public String operatorCode2;
    public String operatorStatus2;

    public StatusModel(String status, String operatorName, String opcode) {
        this.operatorStatus2 = status;
        this.operatoeName2 = operatorName;
        this.operatorCode2 = opcode;
        Log.d("DSE", this.operatoeName2);
        Log.d("DSE1", this.operatorCode2);
        Log.d("DSE2", this.operatorStatus2);
    }
}
