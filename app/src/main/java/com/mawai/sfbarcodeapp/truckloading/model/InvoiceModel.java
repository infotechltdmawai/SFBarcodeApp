package com.mawai.sfbarcodeapp.truckloading.model;

public class InvoiceModel {

    private String unit_code;
    private String inv_no;
    private String packing_cd;
    private String da_no;
    private String cust_code;
    private String cust_name;
    private String prod_name;
    private String prod_cd;
    private String unit_cd;
    private String doc_qty;
    private int scan_qty;
    private String barcode;
    private boolean isCheck = false;

    public InvoiceModel() {
    }

    public String getUnit_code() {
        return unit_code;
    }

    public void setUnit_code(String unit_code) {
        this.unit_code = unit_code;
    }

    public String getInv_no() {
        return inv_no;
    }

    public void setInv_no(String inv_no) {
        this.inv_no = inv_no;
    }

    public String getPacking_cd() {
        return packing_cd;
    }

    public void setPacking_cd(String packing_cd) {
        this.packing_cd = packing_cd;
    }

    public String getDa_no() {
        return da_no;
    }

    public void setDa_no(String da_no) {
        this.da_no = da_no;
    }

    public String getCust_code() {
        return cust_code;
    }

    public void setCust_code(String cust_code) {
        this.cust_code = cust_code;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_cd() {
        return prod_cd;
    }

    public void setProd_cd(String prod_cd) {
        this.prod_cd = prod_cd;
    }

    public String getUnit_cd() {
        return unit_cd;
    }

    public void setUnit_cd(String unit_cd) {
        this.unit_cd = unit_cd;
    }

    public String getDoc_qty() {
        return doc_qty;
    }

    public void setDoc_qty(String doc_qty) {
        this.doc_qty = doc_qty;
    }

    public int getScan_qty() {
        return scan_qty;
    }

    public void setScan_qty(int scan_qty) {
        this.scan_qty = scan_qty;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
