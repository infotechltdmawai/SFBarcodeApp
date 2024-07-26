package com.mawai.sfbarcodeapp.truckloading.response;



import java.util.List;

public class TempSaveResponse {

    private Boolean status;
    private String message;
//    private TempSaveModel data;

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

//    public TempSaveModel getData() {
//        return data;
//    }
//
//    public void setData(TempSaveModel data) {
//        this.data = data;
//    }
}
