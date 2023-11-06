package com.mawai.sfbarcodeapp.login.response;

import com.mawai.sfbarcodeapp.login.model.LoginModel;

public class LoginResponse {

    public Boolean status;
    public String message;
    public LoginModel data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginModel getData() {
        return data;
    }

    public void setData(LoginModel data) {
        this.data = data;
    }
}
