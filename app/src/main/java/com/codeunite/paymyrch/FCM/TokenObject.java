package com.codeunite.paymyrch.FCM;

import com.google.gson.annotations.SerializedName;

public class TokenObject {
    @SerializedName("token")
    private String token;

    public TokenObject(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
