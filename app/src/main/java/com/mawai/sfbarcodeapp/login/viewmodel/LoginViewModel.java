package com.mawai.sfbarcodeapp.login.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mawai.sfbarcodeapp.login.model.LoginModel;
import com.mawai.sfbarcodeapp.login.repo.LoginRepo;
import com.mawai.sfbarcodeapp.login.response.LoginResponse;
import com.mawai.sfbarcodeapp.login.response.UnitResponse;

public class LoginViewModel extends ViewModel {

    private LoginRepo loginRepo;

    public LoginViewModel() {
        loginRepo = LoginRepo.getInstance();
//        loginModelLiveData.setValue(new LoginModel());
    }

    public LiveData<UnitResponse> callGetUnitList() {
        return loginRepo.getUnitList();
    }

    public LiveData<LoginResponse> callGetLoginDetail(LoginModel loginModel) {
        return loginRepo.getLoginDetail(loginModel);
    }


}
