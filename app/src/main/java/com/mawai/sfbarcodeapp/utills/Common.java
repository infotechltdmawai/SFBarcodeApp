package com.mawai.sfbarcodeapp.utills;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {


    public static boolean isConnectedCodeToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            } else {
                Toast.makeText(context, "No Network connected", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }


    public static String  dateformate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat toformatter = new SimpleDateFormat("dd-MMM-yyyy");
        String output_valid = null;

        if (date!=null){
            Date date_valid = null;

            try {
                date_valid = formatter.parse(date);
                output_valid = toformatter.format(date_valid);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return output_valid;

    }

}
