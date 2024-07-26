package com.mawai.sfbarcodeapp.truckloading.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mawai.sfbarcodeapp.R;
import com.mawai.sfbarcodeapp.databinding.LayoutInvoiceBinding;
import com.mawai.sfbarcodeapp.truckloading.model.InvoiceModel;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceViewHolder> {

    private List<InvoiceModel> invoiceModels;
    Context context;

    public InvoiceAdapter(List<InvoiceModel> invoiceModels, Context context) {
        this.invoiceModels = invoiceModels;
        this.context = context;

    }


    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInvoiceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_invoice, parent, false);
        return new InvoiceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        InvoiceModel sampleuser = invoiceModels.get(position);
        holder.listBinding.setModel(sampleuser);
    }

    @Override
    public int getItemCount() {
        return invoiceModels.size();
    }
}
