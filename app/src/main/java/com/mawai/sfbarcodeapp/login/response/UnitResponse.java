package com.mawai.sfbarcodeapp.login.response;

import com.mawai.sfbarcodeapp.login.model.UnitModel;

import java.util.List;

public class UnitResponse {

    public Boolean status;
    public String message;
    public List<UnitModel> data;

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

    public List<UnitModel> getData() {
        return data;
    }

    public void setData(List<UnitModel> data) {
        this.data = data;
    }
}
