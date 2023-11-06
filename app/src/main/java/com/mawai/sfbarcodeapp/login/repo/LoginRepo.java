package com.mawai.sfbarcodeapp.login.repo;

import androidx.lifecycle.MutableLiveData;

import com.mawai.sfbarcodeapp.login.model.LoginModel;
import com.mawai.sfbarcodeapp.login.response.LoginResponse;
import com.mawai.sfbarcodeapp.login.response.UnitResponse;
import com.mawai.sfbarcodeapp.retrofit.APIClient;
import com.mawai.sfbarcodeapp.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {

    private static LoginRepo loginRepo;
    private final ApiService apiServiceLogin;

    public static LoginRepo getInstance() {
        if (loginRepo == null)
            loginRepo = new LoginRepo();
        return loginRepo;
    }

    private LoginRepo() {
        apiServiceLogin = APIClient.getClientBase().create(ApiService.class);
//        apiServiceAfterLogin = APIClient.getApiCLientAfterLogin().create(ApiService.class);
    }


    public MutableLiveData<UnitResponse> getUnitList() {
        final MutableLiveData<UnitResponse> unitlist = new MutableLiveData<>();
        apiServiceLogin.unitList().enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                unitlist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {
                unitlist.setValue(null);
            }
        });
        return unitlist;
    }

    public MutableLiveData<LoginResponse> getLoginDetail(LoginModel loginModel) {
        final MutableLiveData<LoginResponse> loginresult = new MutableLiveData<>();
        apiServiceLogin.getLogin(loginModel).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loginresult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginresult.setValue(null);
            }
        });
        return loginresult;
    }
}
