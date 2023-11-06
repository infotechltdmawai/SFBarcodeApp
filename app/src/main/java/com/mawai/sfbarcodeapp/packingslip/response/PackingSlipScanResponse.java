package com.mawai.sfbarcodeapp.packingslip.response;

import com.mawai.sfbarcodeapp.packingslip.model.PackingSlipModel;

import java.util.List;

public class PackingSlipScanResponse {

    public Boolean status;
    public String message;
    public String clear;
    public String scan_cd;
    public String new_scan_qty;

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

    public String getScan_cd() {
        return scan_cd;
    }

    public void setScan_cd(String scan_cd) {
        this.scan_cd = scan_cd;
    }

    public String getNew_scan_qty() {
        return new_scan_qty;
    }

    public void setNew_scan_qty(String new_scan_qty) {
        this.new_scan_qty = new_scan_qty;
    }

    public String getClear() {
        return clear;
    }

    public void setClear(String clear) {
        this.clear = clear;
    }
}
