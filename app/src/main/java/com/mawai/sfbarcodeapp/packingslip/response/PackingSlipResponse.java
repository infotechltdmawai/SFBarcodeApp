package com.mawai.sfbarcodeapp.packingslip.response;

import com.mawai.sfbarcodeapp.login.model.LoginModel;
import com.mawai.sfbarcodeapp.packingslip.model.PackingSlipModel;

import java.util.List;

public class PackingSlipResponse {

    public Boolean status;
    public String message;
    public int count;
    public List<PackingSlipModel> data;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PackingSlipModel> getData() {
        return data;
    }

    public void setData(List<PackingSlipModel> data) {
        this.data = data;
    }
}
