package com.mawai.sfbarcodeapp.truckloading.repo;

import androidx.lifecycle.MutableLiveData;


import com.mawai.sfbarcodeapp.retrofit.APIClient;
import com.mawai.sfbarcodeapp.retrofit.ApiService;
import com.mawai.sfbarcodeapp.truckloading.model.InvoiceModel;
import com.mawai.sfbarcodeapp.truckloading.response.InvoiceResponse;
import com.mawai.sfbarcodeapp.truckloading.response.TempSaveResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceRepo {

    private static InvoiceRepo invoiceRepo;
    private final ApiService apiServiceLogin;

    public static InvoiceRepo getInstance() {
        if (invoiceRepo == null)
            invoiceRepo = new InvoiceRepo();
        return invoiceRepo;
    }

    private InvoiceRepo() {
        apiServiceLogin = APIClient.getClientBase().create(ApiService.class);
//        apiServiceAfterLogin = APIClient.getApiCLientAfterLogin().create(ApiService.class);
    }


    public MutableLiveData<InvoiceResponse> getInvoiceList(InvoiceModel model) {
        final MutableLiveData<InvoiceResponse> unitlist = new MutableLiveData<>();
        apiServiceLogin.getInvoiceLists(model).enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                unitlist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                unitlist.setValue(null);
            }
        });
        return unitlist;
    }
//
    public MutableLiveData<InvoiceResponse> getInvoiceListData(InvoiceModel loginModel) {
        final MutableLiveData<InvoiceResponse> loginresult = new MutableLiveData<>();
        apiServiceLogin.getInvoiceListData(loginModel).enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                loginresult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                loginresult.setValue(null);
            }
        });
        return loginresult;
    }
//
//    public MutableLiveData<InvoiceBarCodeResponse> getCheckInvBarCode(InvoiceModel loginModel) {
//        final MutableLiveData<InvoiceBarCodeResponse> loginresult = new MutableLiveData<>();
//        apiServiceLogin.getCheckInvBarCode(loginModel).enqueue(new Callback<InvoiceBarCodeResponse>() {
//            @Override
//            public void onResponse(Call<InvoiceBarCodeResponse> call, Response<InvoiceBarCodeResponse> response) {
//                loginresult.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<InvoiceBarCodeResponse> call, Throwable t) {
//                loginresult.setValue(null);
//            }
//        });
//        return loginresult;
//    }
//
    public MutableLiveData<TempSaveResponse> getTempSaveData(InvoiceResponse loginModel) {
        final MutableLiveData<TempSaveResponse> loginresult = new MutableLiveData<>();
        apiServiceLogin.getTempSaveData(loginModel).enqueue(new Callback<TempSaveResponse>() {
            @Override
            public void onResponse(Call<TempSaveResponse> call, Response<TempSaveResponse> response) {
                loginresult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TempSaveResponse> call, Throwable t) {
                loginresult.setValue(null);
            }
        });
        return loginresult;
    }

}
