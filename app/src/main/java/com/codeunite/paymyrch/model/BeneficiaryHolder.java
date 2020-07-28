package com.codeunite.paymyrch.model;

public class BeneficiaryHolder {
    public String accountNo;
    public String beneficiaryName;
    public String ifscCode;

    public BeneficiaryHolder(String beneficiaryName, String accountNo, String ifscCode) {
        this.beneficiaryName = beneficiaryName;
        this.accountNo = accountNo;
        this.ifscCode = ifscCode;
    }
}
