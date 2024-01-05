package com.mawai.sfbarcodeapp.packingslip.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mawai.sfbarcodeapp.packingslip.model.PackingSlipModel;
import com.mawai.sfbarcodeapp.packingslip.repo.PackingSlipRepo;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipResponse;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipScanResponse;

public class PackingSlipViewModel extends ViewModel {

    private PackingSlipRepo packingSlipRepo;

    public PackingSlipViewModel() {
        packingSlipRepo = PackingSlipRepo.getInstance();
//        loginModelLiveData.setValue(new LoginModel());
    }

    public LiveData<PackingSlipResponse> callGetPackList() {
        return packingSlipRepo.getPackList();
    }

    public LiveData<PackingSlipResponse> callGetItemList(PackingSlipModel model) {
        return packingSlipRepo.getItemList(model);
    }

    public LiveData<PackingSlipScanResponse> callGetScanItemCode(PackingSlipModel model) {
        return packingSlipRepo.getScanItemCode(model);
    }
}
