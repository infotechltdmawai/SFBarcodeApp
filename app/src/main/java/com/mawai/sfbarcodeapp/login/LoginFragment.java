package com.mawai.sfbarcodeapp.login;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mawai.sfbarcodeapp.R;
import com.mawai.sfbarcodeapp.databinding.FragmentLoginBinding;
import com.mawai.sfbarcodeapp.login.model.LoginModel;
import com.mawai.sfbarcodeapp.login.response.LoginResponse;
import com.mawai.sfbarcodeapp.login.model.UnitModel;
import com.mawai.sfbarcodeapp.login.viewmodel.LoginViewModel;
import com.mawai.sfbarcodeapp.utills.SessionManager;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class LoginFragment extends Fragment {


    FragmentLoginBinding binding;
    LoginViewModel loginViewModel;
    SessionManager sessionManager;
    ProgressDialog dialog;
    private long mLastClickTime = 0;
    SpinnerDialog spinnerDialogUnitNo;
    private Map<String, String> divisionMap = new HashMap<String, String>();
    ArrayList<String> items = new ArrayList<>();
    List<UnitModel> divisionsModelArrayList = new ArrayList<>();

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_login, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        loginViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(LoginViewModel.class);
        binding.setLifecycleOwner(this);
        sessionManager = new SessionManager(getContext());

        getUnitList();

        binding.edtunitname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogUnitNo.showSpinerDialog();
            }
        });

//        if (sessionManager.getBoolean(SessionManager.CHKBOX)) {
//            binding.setModel(new LoginModel(sessionManager.getString(SessionManager.USER_NAME),sessionManager.getString(SessionManager.PASSWORD),sessionManager.getString(SessionManager.UNIT_NAME)));
//            binding.savedUsernamePassword.setChecked(true);
//        } else {
//            binding.setModel(new LoginModel());
//        }

        spinnerDialogUnitNo = new SpinnerDialog(getActivity(), items, "Select or Search Unit Name", "Close");// With No Animation
        final List<String> datas = new ArrayList<>();
        spinnerDialogUnitNo.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                DynamicToast.makeSuccess(getContext(), item, 5).show();

                binding.edtunitname.setText(item);
                datas.add(divisionMap.get(divisionsModelArrayList.get(position).getCode()));
                Log.d("", "" + divisionsModelArrayList.get(position).getCode());
                sessionManager.save(SessionManager.UNIT_CODE,divisionsModelArrayList.get(position).getCode());
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(binding.edtUserid.getText().toString().trim()) &&
                        !TextUtils.isEmpty(binding.edtPassword.getText().toString().trim()) &&
                        !TextUtils.isEmpty(binding.edtunitname.getText().toString().trim())) {

//                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_homeFragment);
                    if (binding.savedUsernamePassword.isChecked()) {
                        sessionManager.save(SessionManager.USER_NAME, binding.edtUserid.getText().toString().trim());
                        sessionManager.save(SessionManager.PASSWORD, binding.edtPassword.getText().toString().trim());
                        sessionManager.save(SessionManager.UNIT_NAME, binding.edtunitname.getText().toString().trim());
                        sessionManager.save(SessionManager.CHKBOX, Boolean.TRUE);
                    } else {
                        sessionManager.save(SessionManager.USER_NAME, "");
                        sessionManager.save(SessionManager.PASSWORD, "");
                        sessionManager.save(SessionManager.UNIT_NAME, "");
                        sessionManager.save(SessionManager.CHKBOX, Boolean.FALSE);
                    }

                    LoginModel unitModel = new LoginModel();
                    unitModel.setUnit(sessionManager.getString("unit_code"));
                    unitModel.setUser(binding.edtUserid.getText().toString().trim());
                    unitModel.setPassword(binding.edtPassword.getText().toString().trim());
                    if (unitModel != null) {
                        getLoginCall(unitModel);
                    } else {
                        DynamicToast.makeError(getContext(), "Please Enter UserId And Password And UnitName", 5).show();
                    }
                }
                else {
                    DynamicToast.makeError(getContext(), "Please Enter UserId And Password And UnitName", 5).show();
                }

            }
        });

//        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_homeFragment);
//
//            }
//        });


       return binding.getRoot();
    }

    private void getUnitList() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please wait");
        dialog.show();
        dialog.setCancelable(true);
        loginViewModel.callGetUnitList().observe(getViewLifecycleOwner(), unitResponse -> {
            dialog.dismiss();
            if (unitResponse != null) {
                if (unitResponse.getStatus()) {
                    divisionsModelArrayList.clear();
                    items.clear();
                    divisionMap.clear();
                    if (unitResponse.getData() != null) {
                        divisionsModelArrayList = unitResponse.getData();
                        for (UnitModel divisionsModel : divisionsModelArrayList) {
                            items.add(divisionsModel.getCode() + " (" + divisionsModel.getName() + ")");
                            divisionMap.put(divisionsModel.getCode(), divisionsModel.getName());
                        }
                    }
                } else {
                    DynamicToast.makeError(getContext(), "Unit List Not found", 5).show();
                }

            } else {
                DynamicToast.makeError(getContext(), "Unit List Not Working From server Side", 5).show();
            }
        });
    }
    private void getLoginCall(LoginModel loginModel) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();
        loginViewModel.callGetLoginDetail(loginModel).observe(getViewLifecycleOwner(), (LoginResponse loginResponse) -> {
            progressDialog.dismiss();
            if (loginResponse!=null) {
                if (loginResponse.getStatus()) {
                    sessionManager.save(SessionManager.USER_NAME,binding.edtUserid.getText().toString().trim());
                    sessionManager.save(SessionManager.UNIT_NAME, binding.edtunitname.getText().toString().trim());
                    sessionManager.save(SessionManager.ID, loginResponse.getData().getEmp_id());
//                    sessionManager.save(SessionManager.MACHINE_CODE,machine_code);
//                  sessionManager.save("unit_code",divisioCode_Pos);
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_homeFragment);
                    DynamicToast.makeSuccess(getContext(), loginResponse.getMessage(), 5).show();
                } else {
                    DynamicToast.makeError(getContext(), loginResponse.getMessage(), 5).show();
                }
            }else {
                DynamicToast.makeError(getContext(), "Server Not Respond", 5).show();
            }
        });
    }
}