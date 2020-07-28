package com.codeunite.paymyrch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupReport implements Serializable {
    private static final long serialVersionUID = 1;
    public String Address;
    public String accountNumber;
    public String amount;
    public final List<String> children = new ArrayList();
    public String commision;
    public String customerNo;
    public String date;
    public String details;
    public String district;
    public String join_date;
    public String mobile;
    public String name;
    public String newRemain;
    public String oldRemain;
    public String operatorCode;
    public String operatorName;
    public String operatorStatus;
    public String paymentMode;
    public String r_ret_id;
    public String receiveFrom;
    public String remain_value;
    public String requestId;
    public String retailerId;
    public String retailerstatus;
    public String senderNumber;
    public String state;
    public String status;
    public String talktime;

    public void setR_ret_id(String r_ret_id) {
        this.r_ret_id = r_ret_id;
    }

    public String getR_ret_id() {
        return this.r_ret_id;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentMode() {
        return this.paymentMode;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }

    public void setTalktime(String talktime) {
        this.talktime = talktime;
    }

    public String getTalktime() {
        return this.talktime;
    }

    public String getSenderNumber() {
        return this.senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRetailerstatus() {
        return this.retailerstatus;
    }

    public void setRetailerstatus(String retailerstatus) {
        this.retailerstatus = retailerstatus;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorCode() {
        return this.operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getCommision() {
        return this.commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    public String getOperatorStatus() {
        return this.operatorStatus;
    }

    public void setOperatorStatus(String operatorStatus) {
        this.operatorStatus = operatorStatus;
    }

    public String getRetailerId() {
        return this.retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemain_value() {
        return this.remain_value;
    }

    public void setRemain_value(String remain_value) {
        this.remain_value = remain_value;
    }

    public String getJoin_date() {
        return this.join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getReceiveFrom() {
        return this.receiveFrom;
    }

    public void setReceiveFrom(String receiveFrom) {
        this.receiveFrom = receiveFrom;
    }

    public String getOldRemain() {
        return this.oldRemain;
    }

    public void setOldRemain(String oldRemain) {
        this.oldRemain = oldRemain;
    }

    public String getNewRemain() {
        return this.newRemain;
    }

    public void setNewRemain(String newRemain) {
        this.newRemain = newRemain;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCustomerNo() {
        return this.customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return this.children.toString();
    }
}
