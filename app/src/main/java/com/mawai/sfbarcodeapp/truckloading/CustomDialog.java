package com.mawai.sfbarcodeapp.truckloading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.lifecycle.LifecycleOwner;

import com.mawai.sfbarcodeapp.R;
import com.mawai.sfbarcodeapp.truckloading.model.InvoiceModel;
import com.mawai.sfbarcodeapp.truckloading.viewmodel.InvoiceViewModel;
import com.mawai.sfbarcodeapp.utills.SessionManager;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDialog extends Dialog {

    ArrayList<String> stringArrayList;
    ArrayList<String> itemsFilter = new ArrayList<>();
    ListView lv ;
    EditText edt_search;
    public InvoiceViewModel viewModel;
    LifecycleOwner lifecycleOwner;
    Button cancel;
    private Map<String, String> divisionMap = new HashMap<String, String>();
    ArrayList<String> items = new ArrayList<>();
    List<InvoiceModel> divisionsModelArrayList = new ArrayList<>();
    SessionManager sessionManager;

    public CustomDialog(Context context, ArrayList<String> items, InvoiceViewModel viewModel, LifecycleOwner lifecycleOwner) {
        super(context);
        this.stringArrayList = items;
        this.itemsFilter.addAll(stringArrayList);
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
    }
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);
        sessionManager = new SessionManager(getContext());

         lv = (ListView) findViewById(R.id.list);
         edt_search = (EditText) findViewById(R.id.edt_search);
         cancel = (Button) findViewById(R.id.btn_cancel);

         if (stringArrayList.isEmpty()){
             InvoiceModel invoiceModel = new InvoiceModel();
             invoiceModel.setUnit_code(sessionManager.getString("unit_code"));
//             getInvoiceList(invoiceModel);
         }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edt_search.setText(stringArrayList.get(i));
                dismiss();
                viewModel.setButtonPressed(0,edt_search.getText().toString().trim());
            }
        });

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
                    stringArrayList.clear();
                    for (String s : itemsFilter) {
                        if ((s).toLowerCase().matches(".*" + replaceword + ".*".toLowerCase())) {
                            stringArrayList.add(s);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    stringArrayList.clear();
                    stringArrayList.addAll(itemsFilter);
                    adapter.notifyDataSetChanged();
                }

            }
        });

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stringArrayList);
        lv.setAdapter(adapter);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Dismiss the dialog when the button is clicked
                viewModel.setButtonPressed(1, edt_search.getText().toString().trim());
            }
        });

    }

//    private void getInvoiceList(InvoiceModel model) {
//        AwesomeProgressDialog progressDialog = new AwesomeProgressDialog(getContext());
//        progressDialog.addTitle("Loading..."); // add your title here.
//        progressDialog.setStyle(AwesomeProgressDialog.STYLE_LOADING_GEARS);
//        progressDialog.isCancelable(false);
//        progressDialog.showDialog();
//        viewModel.callGetInvoiceList(model).observe(lifecycleOwner, invoiceResponse -> {
//            progressDialog.dismissDialog();
//            if (invoiceResponse != null) {
//                if (invoiceResponse.getStatus()) {
//                    divisionsModelArrayList.clear();
//                    items.clear();
//                    divisionMap.clear();
//                    if (invoiceResponse.getData() != null) {
//                        divisionsModelArrayList = invoiceResponse.getData();
//                        for (InvoiceModel divisionsModel : divisionsModelArrayList) {
//                            items.add(divisionsModel.getIdentifier());
//                            divisionMap.put(divisionsModel.getIdentifier(), divisionsModel.getDates());
//                        }
//
//                    }
//
//                    stringArrayList.addAll(items);
//                    if (itemsFilter.isEmpty()){
//                        itemsFilter.addAll(stringArrayList);
//                    }
//                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stringArrayList);
//                    lv.setAdapter(adapter);
//                } else {
//                    DynamicToast.makeError(getContext(), "Invoice List Not found", 5).show();
//                }
//
//            } else {
//                DynamicToast.makeError(getContext(), "Invoice List Not Working From server Side", 5).show();
//            }
//        });
//    }

}
