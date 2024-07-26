package com.mawai.sfbarcodeapp.home;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mawai.sfbarcodeapp.R;
import com.mawai.sfbarcodeapp.utills.SessionManager;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    TextView text_unit_name,text_user_code,text_username;
    CardView rack_loading,truck_loading,material_issue,wip_trolly_receive,wip_trolly_issue;
    SessionManager sessionManager;
    ImageView img_arrow,img_logout;
    ArrayList<ModelList>  modelLists = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        sessionManager = new SessionManager(getContext());

       text_unit_name = view.findViewById(R.id.text_unit_name);
        text_user_code = view.findViewById(R.id.text_user_code);
        text_username = view.findViewById(R.id.text_username);
        img_arrow = view.findViewById(R.id.img_arrow);
        img_logout = view.findViewById(R.id.img_logout);

        rack_loading = view.findViewById(R.id.rack_loading);
        truck_loading = view.findViewById(R.id.truck_loading);
        material_issue = view.findViewById(R.id.material_issue);
        wip_trolly_issue = view.findViewById(R.id.wip_trolly_issue);
        wip_trolly_receive = view.findViewById(R.id.wip_trolly_receive);

        text_unit_name.setText(sessionManager.getString("unit_name"));
        text_user_code.setText(sessionManager.getString("unit_code"));
        text_username.setText(sessionManager.getString("user_name"));


        modelLists.add(new ModelList(1,1,"Rack Loading",R.id.img_arrow,R.id.action_homeFragment_to_packingSlipFragment));
        modelLists.add(new ModelList(2,1,"Rack Transfer",R.id.img_logout,R.id.action_homeFragment_to_packingSlipFragment));
        modelLists.add(new ModelList(3,1,"Material Issue",R.id.img_logout,R.id.action_homeFragment_to_packingSlipFragment));
        modelLists.add(new ModelList(4,2,"Material Receive",R.id.img_logout,R.id.action_homeFragment_to_packingSlipFragment));
        modelLists.add(new ModelList(5,2,"Wip Trolly Receive",R.id.img_arrow,R.id.action_homeFragment_to_packingSlipFragment));
        modelLists.add(new ModelList(6,3,"Wip Trolly Issue",R.id.img_arrow,R.id.action_homeFragment_to_packingSlipFragment));
        modelLists.add(new ModelList(7,1,"Packing Slip",R.id.img_logout,R.id.action_homeFragment_to_packingSlipFragment));
        modelLists.add(new ModelList(8,2,"Packing Issue",R.id.img_arrow,R.id.action_homeFragment_to_packingSlipFragment));


        for (int i=0;i<modelLists.size();i++){
            if (modelLists.get(i).userid == 1){
                Log.e("Url",modelLists.get(i).getName() + " " + modelLists.get(i).getUrl());
            }
        }


        rack_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_packingSlipFragment);
            }
        });

        truck_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_invoiceFragment);
            }
        });
//
//        material_issue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_materialIssueFragment);
//            }
//        });
//
//        wip_trolly_issue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_WIPTrollyIssueFragment);
//            }
//        });
//
//        wip_trolly_receive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_WIPTrollyReceiveFragment);
//            }
//        });
//
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment);
            }
        });
//
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        onBackPress();

        return view;

    }

    private void onBackPress() {
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                showAlertDialog();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
        alertDialog.setTitle("Sign out Confirmation");
        alertDialog.setMessage("Are you sure you want to sign out ?");
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
//        alertDialog.setIcon(R.drawable.eloficone);

        alertDialog.setPositiveButton("YES", (dialogInterface, i) -> {
//            Intent intent = new Intent(getContext(), LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_loginFragment);

        });

        alertDialog.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());

        alertDialog.show();
    }
}