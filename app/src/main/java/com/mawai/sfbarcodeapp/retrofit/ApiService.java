package com.mawai.sfbarcodeapp.retrofit;


import com.mawai.sfbarcodeapp.login.model.LoginModel;
import com.mawai.sfbarcodeapp.login.response.LoginResponse;
import com.mawai.sfbarcodeapp.login.response.UnitResponse;
import com.mawai.sfbarcodeapp.packingslip.model.PackingSlipModel;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipResponse;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipScanResponse;

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
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIMaterial/scanrack")
//    Call<RackLoadingResponse> getScanRack(@Body RackLoadingModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIMaterial/scanrack")
//    Call<RackTransferResponse> getScanRackTransfer(@Body RackTransferModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIMaterial/check-rack-transfer-data")
//    Call<RackTransferResponse> getCheckRackTransferData(@Body RackTransferModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIMaterial/update-rack-transfer")
//    Call<RackTransferResponse> getUpdateRackTransfer(@Body RackTransferModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIMaterial/update-rack-transfer")
//    Call<RackLoadingResponse> getUpdateRackLoadingTransfer(@Body RackLoadingModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("api/SAIMaterial/issuetypelist")
//    Call<IssueTypeResponse> getIssueTypeList();
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("api/SAIMaterial/issuefor")
//    Call<IssueForResponse> getIssueFor();
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("api/SAIMaterial/fromlocation?")
//    Call<FromLocationResponse> getFromLocation(@Query("unitcode") String unitCode);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIMaterial/tolocation")
//    Call<RefAndToLocationResponse> getToLocation(@Body RefAndToLocationModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIMaterial/refdocdatalist")
//    Call<RefAndToLocationResponse> getRefDocDataList(@Body RefAndToLocationModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIMaterial/scanissuesliplot")
//    Call<ScanIssueSlipLotResponse> getScanIssueSlipLot(@Body ScanIssueSlipLotModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIMaterial/saveissueslip")
//    Call<ScanIssueSlipLotResponse> getSaveIssueSlip(@Body ScanIssueSlipLotModel model);
//
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIWIP_Trolly/scan-wiptrolly-barcode")
//    Call<WIPTrollyIssueResponse> getScanWipTrollyBarcode(@Body WIPTrollyIssueModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIWIP_Trolly/scan-rack")
//    Call<WIPTrollyIssueResponse> getScanWipTrollyScanRack(@Body WIPTrollyIssueModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIWIP_Trolly/location-list")
//    Call<WIPLocationResponse> getWIPLocationList(@Body WIPTrollyIssueModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIWIP_Trolly/rack-list")
//    Call<WIPRackCodeResponse> getWIPRackList(@Body WIPTrollyIssueModel model);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("api/SAIWIP_Trolly/save-wiptrolly-barcode")
//    Call<WIPTrollyIssueResponse> getSaveWipTrollyBarcode(@Body WIPTrollyIssueModel model);



}
