package com.mawai.sfbarcodeapp.truckloading;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.abhaysapp.awesomeprogressdialog.AwesomeProgressDialog;
import com.mawai.sfbarcodeapp.R;
import com.mawai.sfbarcodeapp.databinding.FragmentInvoiceBinding;
import com.mawai.sfbarcodeapp.truckloading.adapter.InvoiceAdapter;
import com.mawai.sfbarcodeapp.truckloading.model.InvoiceModel;
import com.mawai.sfbarcodeapp.truckloading.response.InvoiceResponse;
import com.mawai.sfbarcodeapp.truckloading.viewmodel.InvoiceViewModel;
import com.mawai.sfbarcodeapp.utills.SessionManager;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class InvoiceFragment extends Fragment {


    public InvoiceFragment() {
        // Required empty public constructor
    }

    FragmentInvoiceBinding binding;
    SessionManager sessionManager;
    InvoiceViewModel invoiceViewModel;
    ProgressDialog dialog;
    InvoiceAdapter invoiceAdapter;

    List<InvoiceModel> invoiceModelList = new ArrayList<>();
    private long mLastClickTime = 0;
    SpinnerDialog spinnerDialogUnitNo;
    private Map<String, String> divisionMap = new HashMap<String, String>();
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> itemsFilter = new ArrayList<>();
    List<InvoiceModel> divisionsModelArrayList = new ArrayList<>();

    AlertDialog dialogs = null;
    ArrayAdapter<String> adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invoice, container, false);
        invoiceViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(InvoiceViewModel.class);
        binding.setLifecycleOwner(this);
        sessionManager = new SessionManager(getContext());
        binding.edtScanBarcode.requestFocus();
        disableSoftInputFromAppearing(binding.edtScanBarcode);
//

        binding.imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_invoiceFragment_to_homeFragment);
            }
        });

        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setUnit_code(sessionManager.getString("unit_code"));
        getInvoiceList(invoiceModel);

        spinnerDialogUnitNo = new SpinnerDialog(getActivity(), items, "Select or Search Invoice Name", "Close");// With No Animation
        final List<String> datas = new ArrayList<>();
        spinnerDialogUnitNo.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                DynamicToast.makeSuccess(getContext(), item, 5).show();

                binding.edtInvoiceNumber.setText(item);
                datas.add(divisionMap.get(divisionsModelArrayList.get(position).getInv_no()));
                Log.d("", "" + divisionsModelArrayList.get(position).getInv_no());
                binding.edtDate.setText(divisionsModelArrayList.get(position).getInv_no());
                InvoiceModel model = new InvoiceModel();
                model.setUnit_code(sessionManager.getString("unit_code"));
                model.setInv_no(divisionsModelArrayList.get(position).getInv_no());
                getInvoiceListData(model);
                binding.edtScanBarcode.requestFocus();
//                sessionManager.save(SessionManager.UNIT_CODE,divisionsModelArrayList.get(position).getCode());
            }
        });


        binding.edtInvoiceNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showDialog();
//                spinnerDialogUnitNo.showSpinerDialog();
            }
        });


        binding.edtScanBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    getCheckScanData(editable.toString().trim());
                }

            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!invoiceModelList.isEmpty()){
                    InvoiceModel stock_dt_Model1 = invoiceModelList.stream()
                            .filter(batch_model -> (!batch_model.isCheck())).findAny()
                            .orElse(null);

                    if (stock_dt_Model1==null){
                        InvoiceResponse invoiceResponse = new InvoiceResponse();
                        invoiceResponse.setData(invoiceModelList);

                        saveData(invoiceResponse);
//                        Toast.makeText(getContext(), "Check", Toast.LENGTH_SHORT).show();
                    }else {
                        DynamicToast.makeError(getContext(), "Please All Packing Code Should be Check", 10).show();
                    }
                } else {
                    DynamicToast.makeError(getContext(), "Data is Empty", 5).show();
                }

            }
        });

        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (!invoiceModelList.isEmpty()){
                    invoiceModelList.clear();
                    invoiceAdapter.notifyDataSetChanged();
                    binding.edtInvoiceNumber.setText("");
                    binding.edtScanBarcode.setText("");
                    binding.edtScanBarcode.requestFocus();
                } else {
                    binding.edtInvoiceNumber.setText("");
                    binding.edtScanBarcode.setText("");
                    binding.edtScanBarcode.requestFocus();
                    DynamicToast.makeError(getContext(), "Data is Empty", 5).show();
                }
            }
        });

//        final Observer<String> nameObserver = new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable final String newName) {
//                // Update the UI, in this case, a TextView.
//                InputMethodManager inputManager = (InputMethodManager) binding.getRoot()
//                        .getContext()
//                        .getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                IBinder binder = binding.getRoot().getWindowToken();
//                inputManager.hideSoftInputFromWindow(binder,
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//                binding.edtInvoiceNumber.setText(newName);
//                Log.e("newName",newName);
//                DynamicToast.makeSuccess(getContext(), "" + newName, 5).show();
//            }
//        };

//        final Observer<Integer> number = new Observer<Integer>() {
//            @Override
//            public void onChanged(@Nullable final Integer newName) {
//                // Update the UI, in this case, a TextView.
////                DynamicToast.makeSuccess(getContext(), "" + newName, 5).show();
//            }
//        };
//
//        invoiceViewModel.edt_value.observe(getViewLifecycleOwner(),nameObserver);
//
//        invoiceViewModel.imageToBeShown.observe(getViewLifecycleOwner(),number);



        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getCheckScanData(String string) {
        if (!invoiceModelList.isEmpty()) {
            InvoiceModel stock_dt_Model1 = invoiceModelList.stream()
                    .filter(batch_model -> (string.equals(batch_model.getPacking_cd()))).findAny()
                    .orElse(null);

            if (stock_dt_Model1!=null) {
                stock_dt_Model1.setCheck(true);
                invoiceAdapter.notifyDataSetChanged();
            }else {
                DynamicToast.makeError(getContext(),"Scan Barcode Is Wrong",10);
            }
        }else {
            DynamicToast.makeError(getContext(),"Data is Empty",10);
        }

        binding.edtScanBarcode.setText("");
        binding.edtScanBarcode.requestFocus();

    }

//    private String removeSpecialCharacters(String text) {
//        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
//        Matcher matcher = pattern.matcher(text);
//        return matcher.replaceAll(""); // Remove special characters
//    }

    private void showDialog(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
        alertdialog.setTitle("Select And Search Invoice Number");
        alertdialog.setCancelable(false);
        LayoutInflater inflaterr = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  viewtemplelayout= inflaterr.inflate(R.layout.layout_list, null);

        EditText edt_search = viewtemplelayout.findViewById(R.id.edt_search);
        ListView list = viewtemplelayout.findViewById(R.id.list);
        Button btn_cancel = viewtemplelayout.findViewById(R.id.btn_cancel);

        if (items.isEmpty()){
            items.addAll(itemsFilter);
        }else {
            if (items.size()==1){
                items.clear();
                items.addAll(itemsFilter);
            }
        }

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    String filteredText = editable.toString().trim();
                    String replaceword = filteredText.replace(" ", ".*");
                    items.clear();
                    for (String s : itemsFilter) {
                        if (s!=null){
                            if ((s).toUpperCase().matches(".*" + replaceword + ".*".toUpperCase())||(s).toLowerCase().matches(".*" + replaceword + ".*".toLowerCase())) {
                                items.add(s);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    items.clear();
                    items.addAll(itemsFilter);
                    adapter.notifyDataSetChanged();
                }

            }
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        alertdialog.setNegativeButton("Cancel", (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                binding.edtInvoiceNumber.setText(items.get(i));
//                String s = items.get(i);
                edt_search.setText(items.get(i));
                InvoiceModel model = new InvoiceModel();
                model.setUnit_code(sessionManager.getString("unit_code"));
                model.setInv_no(binding.edtInvoiceNumber.getText().toString().trim());
                getInvoiceListData(model);
                binding.edtScanBarcode.requestFocus();
                dialogs.dismiss();
//                viewModel.setButtonPressed(0,edt_search.getText().toString().trim());
            }
        });

        alertdialog.setView(viewtemplelayout);
        dialogs = alertdialog.create();
        dialogs.show();


//        if (items.isEmpty()){
//            InvoiceModel invoiceModel = new InvoiceModel();
//            invoiceModel.setUnit_code(sessionManager.getString("unit_code"));
//            getInvoiceList(invoiceModel);
//            CustomDialog customDialog = new CustomDialog(getContext(),items);
//            customDialog.show();
//            customDialog.setCancelable(false);
//        }else {
//            binding.edtInvoiceNumber.setText("");
//            CustomDialog customDialog = new CustomDialog(getContext(),items,invoiceViewModel,binding.getLifecycleOwner());
//            customDialog.show();
//            customDialog.setCancelable(false);
//        }



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


    private void getInvoiceList(InvoiceModel model) {
        AwesomeProgressDialog progressDialog = new AwesomeProgressDialog(getContext());
        progressDialog.addTitle("Loading..."); // add your title here.
        progressDialog.setStyle(AwesomeProgressDialog.STYLE_LOADING_ROCKET);
        progressDialog.isCancelable(false);
        progressDialog.showDialog();
        invoiceViewModel.callGetInvoiceList(model).observe(getViewLifecycleOwner(), invoiceResponse -> {
            progressDialog.dismissDialog();
            if (invoiceResponse != null) {
                if (invoiceResponse.getStatus()) {
                    divisionsModelArrayList.clear();
                    items.clear();
                    divisionMap.clear();
                    if (invoiceResponse.getData() != null) {
                        divisionsModelArrayList = invoiceResponse.getData();
                        for (InvoiceModel divisionsModel : divisionsModelArrayList) {
                            if (divisionsModel.getInv_no()!=null){
                                items.add(divisionsModel.getInv_no());
                                divisionMap.put(divisionsModel.getInv_no(), divisionsModel.getPacking_cd());
                            }
                        }
                        itemsFilter.addAll(items);
//                        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
                    }
                } else {
                    DynamicToast.makeError(getContext(), "Invoice List Not found", 5).show();
                }

            } else {
                DynamicToast.makeError(getContext(), "Invoice List Not Working From server Side", 5).show();
            }
        });
    }

    private void getInvoiceListData(InvoiceModel model) {
        AwesomeProgressDialog progressDialog = new AwesomeProgressDialog(getContext());
        progressDialog.addTitle("Loading..."); // add your title here.
        progressDialog.setStyle(AwesomeProgressDialog.STYLE_LOADING_ROCKET);
        progressDialog.isCancelable(false);
        progressDialog.showDialog();
        invoiceViewModel.callGetInvoiceListData(model).observe(getViewLifecycleOwner(), invoiceResponse -> {
            progressDialog.dismissDialog();
            if (invoiceResponse != null) {
                if (invoiceResponse.getStatus()) {
                    invoiceModelList = invoiceResponse.getData();
                    invoiceAdapter = new InvoiceAdapter(invoiceModelList, requireContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    binding.fgReceiveRecyclerView.setLayoutManager(linearLayoutManager);
                    binding.fgReceiveRecyclerView.setHasFixedSize(true);
                    binding.fgReceiveRecyclerView.setAdapter(invoiceAdapter);
                    binding.edtScanBarcode.setText("");
                    binding.edtScanBarcode.requestFocus();
                } else {
                    binding.edtScanBarcode.setText("");
                    binding.edtScanBarcode.requestFocus();
                    DynamicToast.makeError(getContext(), "Invoice Against Data Not found", 5).show();
                }
            } else {
                binding.edtScanBarcode.setText("");
                binding.edtScanBarcode.requestFocus();
                DynamicToast.makeError(getContext(), "Invoice Against Data Not Working From server Side", 5).show();
            }
        });
    }

//    @SuppressLint("NotifyDataSetChanged")
//    private void getCheckInvBarCode(InvoiceModel model) {
//        AwesomeProgressDialog progressDialog = new AwesomeProgressDialog(getContext());
//        progressDialog.addTitle("Loading..."); // add your title here.
//        progressDialog.setStyle(AwesomeProgressDialog.STYLE_LOADING_GEARS);
//        progressDialog.isCancelable(false);
//        progressDialog.showDialog();
//        invoiceViewModel.callGetCheckInvBarCode(model).observe(getViewLifecycleOwner(), invoiceResponse -> {
//            progressDialog.dismissDialog();
//            if (invoiceResponse != null) {
//                if (invoiceResponse.getStatus()) {
//                    InvoiceModel invoiceModel = invoiceResponse.getData();
////                    for (int i=0;i<=invoiceModelList.size();i++){
////                        if (invoiceModelList.get(i).getProd_code().equals(invoiceModel.getProd_cd())){
////                            if (invoiceModel.getQty()!=null && Integer.parseInt(invoiceModelList.get(i).getQuantity()) >= Integer.parseInt(invoiceModel.getQty()) && Integer.parseInt(invoiceModel.getQty())>0){
////                               invoiceModelList.get(i).setQty(invoiceModel.getQty());
////                            }else {
////                                DynamicToast.makeError(getContext(), "Scan Qty can't be 0", 5).show();
////                            }
////                        }
////                    }
//                    InvoiceModel stock_dt_Model1 = invoiceModelList.stream()
//                            .filter(batch_model -> (batch_model.getProd_code().equals(invoiceModel.getProd_cd()) && (batch_model.getQty() != batch_model.getScan_qty()))).findAny()
//                            .orElse(null);
//
//                    if (stock_dt_Model1 != null) {
//                        if (stock_dt_Model1.getQty() >= invoiceModel.getQty() && invoiceModel.getQty() > 0) {
//                            stock_dt_Model1.setScan_qty(invoiceModel.getQty() + stock_dt_Model1.getScan_qty());
//                        } else {
//                            DynamicToast.makeError(getContext(), "Scan Qty can't be 0", 5).show();
//                        }
//                    } else {
//                        DynamicToast.makeError(getContext(), "Product Quantity Is Fully Packed Please Scan Other Barcode", 5).show();
//                    }
//                    Log.e("Invoice List", invoiceModelList.toString());
//                    invoiceAdapter.notifyDataSetChanged();
//
//                    InvoiceModel modelfilter = invoiceModelList.stream()
//                            .filter(batch_model -> (batch_model.getProd_code().equals(invoiceModel.getProd_cd()) && (batch_model.getQty() == batch_model.getScan_qty()))).findAny()
//                            .orElse(null);
//
//                    if (modelfilter != null) {
//                        if (modelfilter.getQty() == modelfilter.getScan_qty()) {
//                            TempSaveModel tempSaveModel = new TempSaveModel();
//                            tempSaveModel.setBarcode(binding.edtScanBarcode.getText().toString().trim());
//                            tempSaveModel.setInvoice_no(binding.edtInvoiceNumber.getText().toString().trim());
//                            tempSaveModel.setInv_date(binding.edtDate.getText().toString().trim());
//                            tempSaveModel.setUnit_code(sessionManager.getString("unit_code"));
//                            tempSaveModel.setProd_cd(modelfilter.getProd_code());
//                            tempSaveModel.setQty(String.valueOf(modelfilter.getQty()));
//                            tempSaveModel.setScan_qty(String.valueOf(modelfilter.getScan_qty()));
//                            getTempSaveData(tempSaveModel);
//                        }
//                    }
//
//                    binding.edtScanBarcode.setText("");
////                    invoiceAdapter = new InvoiceAdapter(invoiceModelList, requireContext());
////                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
////                    binding.fgReceiveRecyclerView.setLayoutManager(linearLayoutManager);
////                    binding.fgReceiveRecyclerView.setHasFixedSize(true);
////                    binding.fgReceiveRecyclerView.setAdapter(invoiceAdapter);
//                } else {
//                    binding.edtScanBarcode.setText("");
//                    binding.edtScanBarcode.requestFocus();
//                    DynamicToast.makeError(getContext(), "Invoice Against Data Not found", 5).show();
//                }
//            } else {
//                binding.edtScanBarcode.setText("");
//                binding.edtScanBarcode.requestFocus();
//                DynamicToast.makeError(getContext(), "Invoice Against Data Not Working From server Side", 5).show();
//            }
//        });
//    }
//
    @SuppressLint("NotifyDataSetChanged")
    private void saveData(InvoiceResponse model) {
        AwesomeProgressDialog progressDialog = new AwesomeProgressDialog(getContext());
        progressDialog.addTitle("Loading..."); // add your title here.
        progressDialog.setStyle(AwesomeProgressDialog.STYLE_LOADING_GEARS);
        progressDialog.isCancelable(false);
        progressDialog.showDialog();
        invoiceViewModel.callGetTempSaveData(model).observe(getViewLifecycleOwner(), tempSaveResponse -> {
            progressDialog.dismissDialog();
            if (tempSaveResponse != null) {
                if (tempSaveResponse.getStatus()) {
                    String invoiceModel = tempSaveResponse.getMessage();

                    DynamicToast.makeSuccess(getContext(), "" + invoiceModel, 5).show();
                    invoiceModelList.clear();
                    invoiceAdapter.notifyDataSetChanged();
                    binding.edtInvoiceNumber.setText("");
                    binding.edtScanBarcode.setText("");
                    binding.edtScanBarcode.requestFocus();
                } else {
                    binding.edtScanBarcode.setText("");
                    binding.edtScanBarcode.requestFocus();
                    DynamicToast.makeError(getContext(), "" + tempSaveResponse.getMessage(), 5).show();
                }
            } else {
                binding.edtScanBarcode.setText("");
                binding.edtScanBarcode.requestFocus();
                DynamicToast.makeError(getContext(), "Data Not Submit not Working From server Side", 5).show();
            }
        });
    }
}