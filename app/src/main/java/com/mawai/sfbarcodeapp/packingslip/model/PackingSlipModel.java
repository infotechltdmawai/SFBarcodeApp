package com.mawai.sfbarcodeapp.packingslip.model;

public class PackingSlipModel {

    String packing_cd;
    String prod_cd;
    String prod_name;
    String scan_qty;
    String doc_no;
    String cust_name;
    String scan_cd;
    String unit_cd;
    String new_scan_qty;
    String user_cd;
    String cust_code;

    public PackingSlipModel() {
    }

    public String getPacking_cd() {
        return packing_cd;
    }

    public void setPacking_cd(String packing_cd) {
        this.packing_cd = packing_cd;
    }

    public String getProd_cd() {
        return prod_cd;
    }

    public void setProd_cd(String prod_cd) {
        this.prod_cd = prod_cd;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getScan_qty() {
        return scan_qty;
    }

    public void setScan_qty(String scan_qty) {
        this.scan_qty = scan_qty;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getScan_cd() {
        return scan_cd;
    }

    public void setScan_cd(String scan_cd) {
        this.scan_cd = scan_cd;
    }

    public String getUnit_cd() {
        return unit_cd;
    }

    public void setUnit_cd(String unit_cd) {
        this.unit_cd = unit_cd;
    }

    public String getNew_scan_qty() {
        return new_scan_qty;
    }

    public void setNew_scan_qty(String new_scan_qty) {
        this.new_scan_qty = new_scan_qty;
    }

    public String getUser_cd() {
        return user_cd;
    }

    public void setUser_cd(String user_cd) {
        this.user_cd = user_cd;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_code() {
        return cust_code;
    }

    public void setCust_code(String cust_code) {
        this.cust_code = cust_code;
    }
}
