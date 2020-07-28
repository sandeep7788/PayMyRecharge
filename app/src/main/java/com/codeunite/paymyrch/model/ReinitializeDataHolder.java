package com.codeunite.paymyrch.model;

public class ReinitializeDataHolder {
    public String reinitiateAccountNo;
    public String reinitiateAmount;
    public String transactionid;

    public ReinitializeDataHolder(String reinitiateAccountNo, String reinitiateAmount, String transactionid) {
        this.reinitiateAccountNo = reinitiateAccountNo;
        this.reinitiateAmount = reinitiateAmount;
        this.transactionid = transactionid;
    }
}
