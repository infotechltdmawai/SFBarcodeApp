package com.mawai.sfbarcodeapp.truckloading.response;


import com.mawai.sfbarcodeapp.truckloading.model.InvoiceModel;

import java.util.List;

public class InvoiceBarCodeResponse {

    private Boolean status;
    private String message;
    private InvoiceModel data;

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

    public InvoiceModel getData() {
        return data;
    }

    public void setData(InvoiceModel data) {
        this.data = data;
    }
}
