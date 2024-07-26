package com.mawai.sfbarcodeapp.truckloading.response;



import com.mawai.sfbarcodeapp.truckloading.model.InvoiceModel;

import java.util.List;

public class InvoiceResponse {

    private Boolean status;
    private String message;
    private List<InvoiceModel> data;

    public InvoiceResponse() {
    }

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

    public List<InvoiceModel> getData() {
        return data;
    }

    public void setData(List<InvoiceModel> data) {
        this.data = data;
    }
}
