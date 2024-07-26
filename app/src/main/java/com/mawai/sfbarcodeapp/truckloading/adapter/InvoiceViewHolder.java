package com.mawai.sfbarcodeapp.truckloading.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mawai.sfbarcodeapp.databinding.LayoutInvoiceBinding;


public class InvoiceViewHolder extends RecyclerView.ViewHolder {

    LayoutInvoiceBinding listBinding;
    public InvoiceViewHolder(@NonNull LayoutInvoiceBinding listBinding) {
        super(listBinding.getRoot());
        this.listBinding = listBinding;
    }
}
