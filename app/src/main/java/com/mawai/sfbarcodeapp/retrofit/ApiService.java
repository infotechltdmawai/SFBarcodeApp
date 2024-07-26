package com.mawai.sfbarcodeapp.retrofit;


import com.mawai.sfbarcodeapp.login.model.LoginModel;
import com.mawai.sfbarcodeapp.login.response.LoginResponse;
import com.mawai.sfbarcodeapp.login.response.UnitResponse;
import com.mawai.sfbarcodeapp.packingslip.model.PackingSlipModel;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipResponse;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipScanResponse;
import com.mawai.sfbarcodeapp.truckloading.model.InvoiceModel;
import com.mawai.sfbarcodeapp.truckloading.response.InvoiceResponse;
import com.mawai.sfbarcodeapp.truckloading.response.TempSaveResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/unitlist")
    Call<UnitResponse> unitList();
//
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/login")
    Call<LoginResponse> getLogin(@Body LoginModel model);
//
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/packlist")
    Call<PackingSlipResponse> getPackList();

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/itemlist")
    Call<PackingSlipResponse> getItemList(@Body PackingSlipModel model);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/scanitemcode")
    Call<PackingSlipScanResponse> getScanItemCode(@Body PackingSlipModel model);
//
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/getinvoicelistsf")
    Call<InvoiceResponse> getInvoiceLists(@Body InvoiceModel model);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/getpackingbyinvoicesf")
    Call<InvoiceResponse> getInvoiceListData(@Body InvoiceModel model);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/updatepackingsf")
    Call<TempSaveResponse> getTempSaveData(@Body InvoiceResponse model);


}
