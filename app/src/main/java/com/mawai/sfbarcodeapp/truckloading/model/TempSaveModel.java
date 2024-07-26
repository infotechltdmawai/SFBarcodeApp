package com.mawai.sfbarcodeapp.truckloading.model;

public class TempSaveModel {

    private String barcode;
    private String invoice_no;
    private String inv_date;
    private String unit_code    ;
    private String prod_cd;
    private String qty;
    private String scan_qty;

    public TempSaveModel() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getInv_date() {
        return inv_date;
    }

    public void setInv_date(String inv_date) {
        this.inv_date = inv_date;
    }

    public String getUnit_code() {
        return unit_code;
    }

    public void setUnit_code(String unit_code) {
        this.unit_code = unit_code;
    }

    public String getProd_cd() {
        return prod_cd;
    }

    public void setProd_cd(String prod_cd) {
        this.prod_cd = prod_cd;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getScan_qty() {
        return scan_qty;
    }

    public void setScan_qty(String scan_qty) {
        this.scan_qty = scan_qty;
    }
}
