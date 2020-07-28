package com.codeunite.paymyrch.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeunite.paymyrch.AsyncTask.ForgotPassTask;
import com.codeunite.paymyrch.AsyncTask.LoginTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.activity.ErrorActivity;
import com.codeunite.paymyrch.helper.ValidationChecker;
import com.codeunite.paymyrch.utils.CommonUtils;


import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_READ_PHONE_STATE = 111;
    private static final int REQ_CODE = 9001;
    public static Editor loginEditor;
    public static SharedPreferences loginPreference;
    private final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    Button btnlogin;
    private CheckBox chkRememberMe;
    Context context = getContext();
    TextView forgot;
    ImageView hide;
    ImageView img_show;
    TextView link;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    String name;
    EditText password;
    ProgressDialog progressDialog;
    private Boolean saveLogin;
    String strpassword;
    String strusername;
    TextView txt_firm;
    TextView txt_pass;
    TextView txt_username;
    /* renamed from: u */
    boolean f40u = false;
    EditText username;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.LoginFragment$1 */
    class C06081 implements OnClickListener {
        C06081() {
        }

        public void onClick(View v) {
            LoginFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://codunite.com/")));
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.LoginFragment$2 */
    class C06092 implements OnClickListener {
        C06092() {
        }

        public void onClick(View v) {
            LoginFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://paymyrecharge.in/")));
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.LoginFragment$3 */
    class C06103 implements OnClickListener {
        C06103() {
        }

        public void onClick(View view) {
            LoginFragment.this.hide.setVisibility(View.GONE);
            LoginFragment.this.img_show.setVisibility(View.VISIBLE);
            LoginFragment.this.password.setInputType(144);
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.LoginFragment$4 */
    class C06114 implements OnClickListener {
        C06114() {
        }

        public void onClick(View view) {
            LoginFragment.this.hide.setVisibility(View.VISIBLE);
            LoginFragment.this.img_show.setVisibility(View.GONE);
            LoginFragment.this.password.setInputType(129);
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.LoginFragment$5 */
    class C06125 implements OnClickListener {
        C06125() {
        }

        public void onClick(View view) {
            if (LoginFragment.this.isNetworkAvailable()) {
                LoginFragment.this.strusername = LoginFragment.this.username.getText().toString();
                LoginFragment.this.strpassword = LoginFragment.this.password.getText().toString();
                if (LoginFragment.this.username.length() == 0 || LoginFragment.this.password.length() != 0) {
                }
                if (LoginFragment.this.strusername.equalsIgnoreCase("")) {
                    LoginFragment.this.username.setError("Enter UserName");
                    return;
                } else if (LoginFragment.this.strpassword.equalsIgnoreCase("")) {
                    LoginFragment.this.password.setError("Enter Password");
                    return;
                } else {
                    if (LoginFragment.this.chkRememberMe.isChecked()) {
                        LoginFragment.loginEditor.putBoolean("saveLogin", true);
                        LoginFragment.loginEditor.putString("username", LoginFragment.this.strusername);
                        LoginFragment.loginEditor.putString("password", LoginFragment.this.strpassword);
                        LoginFragment.loginEditor.commit();
                    } else {
                        LoginFragment.loginEditor.clear();
                        LoginFragment.loginEditor.commit();
                    }
                    CommonUtils.uId = LoginFragment.this.strusername;
                    CommonUtils.pass = LoginFragment.this.strpassword;
                    CommonUtils.login_btn_status = "true";
                    new LoginTask(LoginFragment.this.getActivity(), LoginFragment.this.strusername, LoginFragment.this.strpassword, LoginFragment.this.progressDialog).execute(new String[0]);
                    CommonUtils.userName = LoginFragment.this.strusername;
                    return;
                }
            }
            LoginFragment.this.startActivity(new Intent(LoginFragment.this.getActivity(), ErrorActivity.class));
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.LoginFragment$6 */
    class C06146 implements OnClickListener {
        C06146() {
        }

        public void onClick(View view) {
            if (LoginFragment.this.isNetworkAvailable()) {
                final Dialog dialog1 = new Dialog(LoginFragment.this.getContext());
                dialog1.setContentView(R.layout.dialog_forgot_password);
                dialog1.getWindow().setLayout(-1, -2);
                dialog1.setTitle("Forgot Password");
                final EditText edtMobile = (EditText) dialog1.findViewById(R.id.edtMobile);
                ((Button) dialog1.findViewById(R.id.btnSend)).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        String mobileNo = edtMobile.getText().toString();
                        ValidationChecker validationChecker = new ValidationChecker();
                        if (mobileNo.equalsIgnoreCase("")) {
                            edtMobile.setError("Please enter your mobile number.");
                        } else if (validationChecker.isValidNumber(mobileNo).booleanValue()) {
                            new ForgotPassTask(LoginFragment.this.getContext(), mobileNo, LoginFragment.this.progressDialog).execute(new String[0]);
                            dialog1.dismiss();
                        } else {
                            edtMobile.setError("Please enter valid mobile number");
                        }
                    }
                });
                dialog1.show();
                return;
            }
            LoginFragment.this.startActivity(new Intent(LoginFragment.this.getContext(), ErrorActivity.class));
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        this.username = (EditText) view.findViewById(R.id.edtUsername);
        this.password = (EditText) view.findViewById(R.id.edtPassword);
        this.progressDialog = new ProgressDialog(getContext());
        this.forgot = (TextView) view.findViewById(R.id.tvForgotPassword);
        this.link = (TextView) view.findViewById(R.id.link_txt);
        this.txt_firm = (TextView) view.findViewById(R.id.tvfirmName);
//        this.txt_username = (TextView) view.findViewById(R.id.txt_userName);
//        this.txt_pass = (TextView) view.findViewById(R.id.txt_Pass);
        this.chkRememberMe = (CheckBox) view.findViewById(R.id.chkRememberMe);
        this.img_show = (ImageView) view.findViewById(R.id.img_show);
        this.hide = (ImageView) view.findViewById(R.id.img_hide);
        loadPage();
        this.link.setOnClickListener(new C06081());
        this.txt_firm.setOnClickListener(new C06092());
        this.btnlogin = (Button) view.findViewById(R.id.btnSignIn);
        this.hide.setOnClickListener(new C06103());
        this.img_show.setOnClickListener(new C06114());
        this.btnlogin.setOnClickListener(new C06125());
        this.forgot.setOnClickListener(new C06146());
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 111:
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_PHONE_STATE");
                int fineLocationPermissionCheck = ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION");
                int coarseLocationPermissionCheck = ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION");
                if ((permissionCheck != 0 || fineLocationPermissionCheck != 0 || coarseLocationPermissionCheck != 0) && !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.READ_PHONE_STATE")) {
                    return;
                }
                return;
            case 124:
                Map<String, Integer> perms = new HashMap();
                perms.put("android.permission.ACCESS_NETWORK_STATE", Integer.valueOf(0));
                perms.put("android.permission.INTERNET", Integer.valueOf(0));
                perms.put("android.permission.READ_CONTACTS", Integer.valueOf(0));
                perms.put("android.permission.CHANGE_NETWORK_STATE", Integer.valueOf(0));
                perms.put("android.permission.BIND_ACCESSIBILITY_SERVICE", Integer.valueOf(0));
                perms.put("android.permission.WAKE_LOCK", Integer.valueOf(0));
                perms.put("android.permission.READ_PHONE_STATE", Integer.valueOf(0));
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], Integer.valueOf(grantResults[i]));
                }
                if (((Integer) perms.get("android.permission.ACCESS_NETWORK_STATE")).intValue() != 0 || ((Integer) perms.get("android.permission.INTERNET")).intValue() != 0 || ((Integer) perms.get("android.permission.READ_CONTACTS")).intValue() != 0 || ((Integer) perms.get("android.permission.CHANGE_NETWORK_STATE")).intValue() != 0 || ((Integer) perms.get("android.permission.BIND_ACCESSIBILITY_SERVICE")).intValue() != 0 || ((Integer) perms.get("android.permission.WAKE_LOCK")).intValue() != 0 || ((Integer) perms.get("android.permission.READ_PHONE_STATE")).intValue() != 0) {
                    return;
                }
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }
    }

    public void loadPage() {
        loginPreference = getContext().getSharedPreferences("loginPrefs", 0);
        loginEditor = loginPreference.edit();
        this.saveLogin = Boolean.valueOf(loginPreference.getBoolean("saveLogin", false));
        if (this.saveLogin.booleanValue()) {
            this.username.setText(loginPreference.getString("username", ""));
            this.password.setText(loginPreference.getString("password", ""));
            this.chkRememberMe.setChecked(true);
        }
    }
}
