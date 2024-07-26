package com.mawai.sfbarcodeapp.truckloading.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mawai.sfbarcodeapp.truckloading.model.InvoiceModel;
import com.mawai.sfbarcodeapp.truckloading.repo.InvoiceRepo;
import com.mawai.sfbarcodeapp.truckloading.response.InvoiceResponse;
import com.mawai.sfbarcodeapp.truckloading.response.TempSaveResponse;

import java.util.List;


public class InvoiceViewModel extends ViewModel {

    private InvoiceRepo invoiceRepo;
    public MutableLiveData<Integer> imageToBeShown = new MutableLiveData<Integer>();
    public MutableLiveData<String> edt_value = new MutableLiveData<String>();;

    Integer positiveButtonPressed = 0;


    public InvoiceViewModel() {
        invoiceRepo = InvoiceRepo.getInstance();
    }

    public LiveData<InvoiceResponse> callGetInvoiceList(InvoiceModel model) {
        return invoiceRepo.getInvoiceList(model);
    }

    public LiveData<InvoiceResponse> callGetInvoiceListData(InvoiceModel model) {
        return invoiceRepo.getInvoiceListData(model);
    }
//
//    public LiveData<InvoiceBarCodeResponse> callGetCheckInvBarCode(InvoiceModel model) {
//        return invoiceRepo.getCheckInvBarCode(model);
//    }
//
    public LiveData<TempSaveResponse> callGetTempSaveData(InvoiceResponse model) {
        return invoiceRepo.getTempSaveData(model);
    }


    public void setButtonPressed(int buttonPressed, String trim){
        if (buttonPressed==positiveButtonPressed){
            imageToBeShown.setValue(buttonPressed);
            edt_value.setValue(trim);
        }
        else{
            imageToBeShown.setValue(1);
            edt_value.setValue("");
        }
    }
}
