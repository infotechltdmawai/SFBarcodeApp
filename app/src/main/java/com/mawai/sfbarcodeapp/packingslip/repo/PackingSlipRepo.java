package com.mawai.sfbarcodeapp.packingslip.repo;

import androidx.lifecycle.MutableLiveData;

import com.mawai.sfbarcodeapp.login.model.LoginModel;
import com.mawai.sfbarcodeapp.login.repo.LoginRepo;
import com.mawai.sfbarcodeapp.login.response.LoginResponse;
import com.mawai.sfbarcodeapp.login.response.UnitResponse;
import com.mawai.sfbarcodeapp.packingslip.model.PackingSlipModel;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipResponse;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipScanResponse;
import com.mawai.sfbarcodeapp.retrofit.APIClient;
import com.mawai.sfbarcodeapp.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackingSlipRepo {

    private static PackingSlipRepo packingSlipRepo;
    private final ApiService apiServiceLogin;

    public static PackingSlipRepo getInstance() {
        if (packingSlipRepo == null)
            packingSlipRepo = new PackingSlipRepo();
        return packingSlipRepo;
    }

    private PackingSlipRepo() {
        apiServiceLogin = APIClient.getClientBase().create(ApiService.class);
//        apiServiceAfterLogin = APIClient.getApiCLientAfterLogin().create(ApiService.class);
    }


    public MutableLiveData<PackingSlipResponse> getPackList() {
        final MutableLiveData<PackingSlipResponse> unitlist = new MutableLiveData<>();
        apiServiceLogin.getPackList().enqueue(new Callback<PackingSlipResponse>() {
            @Override
            public void onResponse(Call<PackingSlipResponse> call, Response<PackingSlipResponse> response) {
                unitlist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PackingSlipResponse> call, Throwable t) {
                unitlist.setValue(null);
            }
        });
        return unitlist;
    }

    public MutableLiveData<PackingSlipResponse> getItemList(PackingSlipModel model) {
        final MutableLiveData<PackingSlipResponse> loginresult = new MutableLiveData<>();
        apiServiceLogin.getItemList(model).enqueue(new Callback<PackingSlipResponse>() {
            @Override
            public void onResponse(Call<PackingSlipResponse> call, Response<PackingSlipResponse> response) {
                loginresult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PackingSlipResponse> call, Throwable t) {
                loginresult.setValue(null);
            }
        });
        return loginresult;
    }

    public MutableLiveData<PackingSlipScanResponse> getScanItemCode(PackingSlipModel model) {
        final MutableLiveData<PackingSlipScanResponse> loginresult = new MutableLiveData<>();
        apiServiceLogin.getScanItemCode(model).enqueue(new Callback<PackingSlipScanResponse>() {
            @Override
            public void onResponse(Call<PackingSlipScanResponse> call, Response<PackingSlipScanResponse> response) {
                loginresult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PackingSlipScanResponse> call, Throwable t) {
                loginresult.setValue(null);
            }
        });
        return loginresult;
    }
}
