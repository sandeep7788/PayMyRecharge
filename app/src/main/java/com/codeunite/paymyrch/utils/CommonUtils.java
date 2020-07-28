package com.codeunite.paymyrch.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.support.v4.view.ViewCompat;
import android.util.Log;

import com.codeunite.paymyrch.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
import net.steamcrafted.loadtoast.LoadToast;
import tr.xip.errorview.HttpStatusCodes;

public class CommonUtils extends BroadcastReceiver {
    public static String IMEI;
    public static boolean NotificationFlag = false;
    public static String[] all_spinnercode = null;
    public static String[] all_spinnername = null;
    public static String[] data_spinnercode = null;
    public static String[] data_spinnername = null;
    public static String deviceToken;
    public static String[] dth_spinnercode = null;
    public static String[] dth_spinnername = null;
    public static String[] electricity_spinnercode = null;
    public static String[] electricity_spinnername = null;
    public static String fundTransferStatus;
    public static String[] gas_spinnercode = null;
    public static String[] gas_spinnername = null;
    public static String[] insurance_spinnercode = null;
    public static String[] insurance_spinnername = null;
    public static boolean internet_status = false;
    public static String[] landline_spinnercode = null;
    public static String[] landline_spinnername = null;
    public static String login_btn_status;
    public static String[] mob_spinnercode = null;
    public static String[] mob_spinnername = null;
    public static String offline_status;
    public static String oldversion;
    public static boolean operator_status = false;
    public static String pass;
    public static String password;
    public static String[] post_spinnercode = null;
    public static String[] post_spinnername = null;
    public static String rch_status;
    public static String[] retailer_id = null;
    public static String role;
    public static String uId;
    public static String userName;

    /* renamed from: com.codeunite.PayMyRecharge.utils.CommonUtils$1 */
    static class C06921 implements OnClickListener {
        C06921() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    public static void checkInternetConenction(Context context) {
        internet_status = false;
        ConnectivityManager check = (ConnectivityManager) context.getSystemService("connectivity");
        if (check != null) {
            NetworkInfo[] info = check.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo state : info) {
                    if (state.getState() == State.CONNECTED) {
                        internet_status = true;
                    }
                }
            }
        }
    }

    public void onReceive(Context context, Intent intent) {
        Log.d("BROADCAST", "CALLED ");
        checkInternetConenction(context);
    }

    public static void showMessage(Context context, String msg) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setButton("OK", new C06921());
        alertDialog.show();
    }

    public static void showSuccessDialog(Context context, String status) {
        new SweetAlertDialog(context, 2).setTitleText("Success!!").setContentText(status).show();
    }

    public static void showFaliedDialog(Context context, String status) {
        new SweetAlertDialog(context, 1).setTitleText("Oops...").setContentText(status).show();
    }

    public static SweetAlertDialog getProgressDialog(Activity activity, String message) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, 5);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog.setTitleText(message + "...");
        sweetAlertDialog.setCancelable(false);
        return sweetAlertDialog;
    }

    public static LoadToast getLoadToast(Context context, String message) {
        LoadToast loadToast = new LoadToast(context);
        loadToast.setText(message + "...");
        loadToast.setTextColor(ViewCompat.MEASURED_STATE_MASK).setBackgroundColor(Color.parseColor("#C5CAE9")).setProgressColor(Color.parseColor("#03A9F4"));
        loadToast.setTranslationY(HttpStatusCodes.CODE_500);
        return loadToast;
    }
}
