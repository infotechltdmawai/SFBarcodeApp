package com.mawai.sfbarcodeapp.packingslip;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mawai.sfbarcodeapp.R;
import com.mawai.sfbarcodeapp.databinding.FragmentPackingSlipBinding;
import com.mawai.sfbarcodeapp.packingslip.model.PackingSlipModel;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipResponse;
import com.mawai.sfbarcodeapp.packingslip.response.PackingSlipScanResponse;
import com.mawai.sfbarcodeapp.packingslip.viewmodel.PackingSlipViewModel;
import com.mawai.sfbarcodeapp.utills.SessionManager;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class PackingSlipFragment extends Fragment {

    public PackingSlipFragment() {
        // Required empty public constructor
    }
    FragmentPackingSlipBinding binding;
    SessionManager sessionManager;
    PackingSlipViewModel packingSlipViewModel;
    ProgressDialog dialog;
    private long mLastClickTime = 0;
    SpinnerDialog spinnerDialogUnitNo,spinnerDialogItem;
    private Map<String, String> divisionMap = new HashMap<String, String>();
    ArrayList<String> items = new ArrayList<>();
    List<PackingSlipModel> divisionsModelArrayList = new ArrayList<>();
    private Map<String, String> divisionMapItem = new HashMap<String, String>();
    ArrayList<String> itemsList = new ArrayList<>();
    List<PackingSlipModel> divisionsModelArrayListItem = new ArrayList<>();
    String doc_no,prod_cd,packing_cd,cust_code = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_packing_slip, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_packing_slip, container, false);
        packingSlipViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(PackingSlipViewModel.class);
        binding.setLifecycleOwner(this);
        sessionManager = new SessionManager(getContext());
        disableSoftInputFromAppearing(binding.edtScan);

        getPackList();

        binding.imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_packingSlipFragment_to_homeFragment);
            }
        });

        binding.edtPackingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogUnitNo.showSpinerDialog();
            }
        });

        binding.edtItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogItem.showSpinerDialog();
            }
        });

        spinnerDialogUnitNo = new SpinnerDialog(getActivity(), items, "Select or Search Pack Name", "Close");// With No Animation
        final List<String> datas = new ArrayList<>();
        spinnerDialogUnitNo.bindOnSpinerListener((item, position) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            DynamicToast.makeSuccess(getContext(), item, 5).show();
            binding.edtPackingList.setText(item);
            binding.edtItem.setText("");
            binding.edtPackQty.setText("");
            datas.add(divisionMap.get(divisionsModelArrayList.get(position).getPacking_cd()));
            Log.d("", "" + divisionsModelArrayList.get(position).getPacking_cd());
            packing_cd = divisionsModelArrayList.get(position).getPacking_cd();
            PackingSlipModel packingSlipModel = new PackingSlipModel();
            packingSlipModel.setPacking_cd(divisionsModelArrayList.get(position).getPacking_cd());
            packingSlipModel.setDoc_no(divisionsModelArrayList.get(position).getDoc_no());
            getItemList(packingSlipModel);
//                sessionManager.save(SessionManager.UNIT_CODE,divisionsModelArrayList.get(position).getPacking_cd());
        });

        spinnerDialogItem = new SpinnerDialog(getActivity(), itemsList, "Select or Search Item Name", "Close");// With No Animation
        final List<String> data = new ArrayList<>();
        spinnerDialogItem.bindOnSpinerListener((item, position) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            DynamicToast.makeSuccess(getContext(), item, 5).show();
            binding.edtItem.setText(item);
            data.add(divisionMapItem.get(divisionsModelArrayListItem.get(position).getProd_name()));
            Log.d("", "" + divisionsModelArrayListItem.get(position).getProd_name());
            binding.edtPackQty.setText(divisionsModelArrayListItem.get(position).getScan_qty());
            doc_no = divisionsModelArrayListItem.get(position).getDoc_no();
            prod_cd = divisionsModelArrayListItem.get(position).getProd_cd();
            cust_code = divisionsModelArrayListItem.get(position).getCust_code();
            binding.edtScan.requestFocus();
//          sessionManager.save(SessionManager.UNIT_CODE,divisionsModelArrayList.get(position).getPacking_cd());
        });

        binding.edtScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    PackingSlipModel model = new PackingSlipModel();
                    model.setPacking_cd(packing_cd);
                    model.setProd_cd(prod_cd);
                    model.setScan_qty(binding.edtPackQty.getText().toString().trim());
                    model.setDoc_no(doc_no);
                    model.setUnit_cd(sessionManager.getString("unit_code"));
                    model.setScan_cd(editable.toString().trim());
                    model.setUser_cd(sessionManager.getString("user_name"));
                    model.setCust_code(cust_code);
                    getScanItemCode(model);
                }
            }
        });

        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.edtScan.setText("");
                binding.edtItem.setText("");
                binding.edtPackQty.setText("");
                binding.edtPackingList.setText("");
                doc_no = "";
                prod_cd = "";
                packing_cd = "";
                cust_code = "";
            }
        });

        return binding.getRoot();
    }

    private void getPackList() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please wait");
        dialog.show();
        dialog.setCancelable(true);
        packingSlipViewModel.callGetPackList().observe(getViewLifecycleOwner(), packingSlipResponse -> {
            dialog.dismiss();
            if (packingSlipResponse != null) {
                if (packingSlipResponse.getStatus()) {
                    divisionsModelArrayList.clear();
                    items.clear();
                    divisionMap.clear();
                    if (packingSlipResponse.getData() != null) {
                        divisionsModelArrayList = packingSlipResponse.getData();
                        for (PackingSlipModel divisionsModel : divisionsModelArrayList) {
                            items.add(divisionsModel.getCust_name() + " ( " + divisionsModel.getPacking_cd() + " ) " +  " ( " + divisionsModel.getDoc_no() + " )");
                            divisionMap.put(divisionsModel.getPacking_cd(), divisionsModel.getPacking_cd());
                        }
                    }
                } else {
                    DynamicToast.makeError(getContext(), "Pack List Not found", 5).show();
                }

            } else {
                DynamicToast.makeError(getContext(), "Pack List Not Working From server Side", 5).show();
            }
        });
    }

    private void getItemList(PackingSlipModel model) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();
        packingSlipViewModel.callGetItemList(model).observe(getViewLifecycleOwner(), (PackingSlipResponse packingSlipResponse) -> {
            progressDialog.dismiss();
            if (packingSlipResponse!=null) {
                if (packingSlipResponse.getStatus()) {
                    divisionsModelArrayListItem.clear();
                    itemsList.clear();
                    divisionMapItem.clear();
                    if (packingSlipResponse.getData() != null) {
                        divisionsModelArrayListItem = packingSlipResponse.getData();
                        for (PackingSlipModel divisionsModel : divisionsModelArrayListItem) {
                            itemsList.add(divisionsModel.getProd_name());
                            divisionMapItem.put(divisionsModel.getProd_name(), divisionsModel.getProd_cd());
                        }
                    }
                } else {
                    DynamicToast.makeError(getContext(), packingSlipResponse.getMessage(), 5).show();
                }
            }else {
                DynamicToast.makeError(getContext(), "Server Not Respond", 5).show();
            }
        });
    }

    private void getScanItemCode(PackingSlipModel model) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();
        packingSlipViewModel.callGetScanItemCode(model).observe(getViewLifecycleOwner(), (PackingSlipScanResponse packingSlipScanResponse) -> {
            progressDialog.dismiss();
            if (packingSlipScanResponse!=null) {
                if (packingSlipScanResponse.getStatus()) {

                    binding.edtPackQty.setText(packingSlipScanResponse.getNew_scan_qty());
                    binding.edtScan.setText("");
                    binding.edtScan.requestFocus();
                    DynamicToast.makeSuccess(getContext(), packingSlipScanResponse.getMessage(), 5).show();
                } else {
                    showBottomSheetDialog(packingSlipScanResponse.getMessage(),packingSlipScanResponse.getClear());
                    binding.edtScan.setText("");
                    binding.edtScan.requestFocus();
                    DynamicToast.makeError(getContext(), packingSlipScanResponse.getMessage(), 5).show();
                }
            }else {
                binding.edtScan.setText("");
                binding.edtScan.requestFocus();
                DynamicToast.makeError(getContext(), "Server Not Respond", 5).show();
            }
        });
    }

    BottomSheetDialog bottomSheetDialog;
    private void showBottomSheetDialog(String data, String clear) {

        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.layout_dialog);
        bottomSheetDialog.setCancelable(false);

        Button no = bottomSheetDialog.findViewById(R.id.no_btn);
        Button yes = bottomSheetDialog.findViewById(R.id.yes_btn);
        TextView textView = bottomSheetDialog.findViewById(R.id.data);
        textView.setText(data);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopDialog();
            }
        });


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clear.equals("Y")){
                    binding.edtPackingList.setText("");
                    binding.edtItem.setText("");
                    binding.edtPackQty.setText("");
                    doc_no = "";
                    prod_cd = "";
                    packing_cd = "";
                    cust_code = "";
                } else if (clear.equals("N")) {
                    Log.e("Clear",clear);
                }
                stopDialog();

            }
        });

        bottomSheetDialog.show();
    }

    public void stopDialog() {
        bottomSheetDialog.dismiss();
    }

    public static void disableSoftInputFromAppearing(EditText editText) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(false);
        } else {
            try {
                final Method method = EditText.class.getMethod(
                        "setShowSoftInputOnFocus"
                        , new Class[]{boolean.class});
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // ignore
            }
        }
    }
}