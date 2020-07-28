package com.codeunite.paymyrch.model;

public class BankDetailHolder {
    public String AccountHolderName;
    public String AccountNumber;
    public String AccountType;
    public String BankAddress;
    public String BankName;
    public String BranchName;
    public String IfscCode;

    public BankDetailHolder(String BankName, String BranchName, String IfscCode, String AccountNumber, String AccountType, String AccountHolderName, String BankAddress) {
        this.BankName = BankName;
        this.BranchName = BranchName;
        this.IfscCode = IfscCode;
        this.AccountNumber = AccountNumber;
        this.AccountType = AccountType;
        this.AccountHolderName = AccountHolderName;
        this.BankAddress = BankAddress;
    }
}
