package com.codeunite.paymyrch.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import cc.cloudist.acplibrary.ACProgressFlower;
import com.codeunite.paymyrch.AsyncTask.ChangePasswordTask;
import com.codeunite.paymyrch.R;

public class ChangePasswordFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnChangePass;
    String confirmPass;
    TextInputLayout confirmpassLayout;
    Context context;
    EditText edtConfirmPass;
    EditText edtNewPass;
    EditText edtOldPass;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    TextInputLayout newPassLayout;
    TextInputLayout oldpassLayout;
    ACProgressFlower progressDialog;
    String strNewPass;
    String strOldPass;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.ChangePasswordFragment$1 */
    class C05311 implements OnClickListener {
        C05311() {
        }

        public void onClick(View v) {
            ChangePasswordFragment.this.strOldPass = ChangePasswordFragment.this.edtOldPass.getText().toString().trim();
            ChangePasswordFragment.this.strNewPass = ChangePasswordFragment.this.edtNewPass.getText().toString().trim();
            ChangePasswordFragment.this.confirmPass = ChangePasswordFragment.this.edtConfirmPass.getText().toString().trim();
            if (ChangePasswordFragment.this.strOldPass.equals("")) {
                if (ChangePasswordFragment.this.newPassLayout.isErrorEnabled()) {
                    ChangePasswordFragment.this.newPassLayout.setErrorEnabled(false);
                }
                if (ChangePasswordFragment.this.confirmpassLayout.isErrorEnabled()) {
                    ChangePasswordFragment.this.confirmpassLayout.setErrorEnabled(false);
                }
                ChangePasswordFragment.this.oldpassLayout.setError("Enter old password");
                ChangePasswordFragment.this.oldpassLayout.requestFocus();
            } else if (ChangePasswordFragment.this.strNewPass.equals("")) {
                if (ChangePasswordFragment.this.oldpassLayout.isErrorEnabled()) {
                    ChangePasswordFragment.this.oldpassLayout.setErrorEnabled(false);
                }
                if (ChangePasswordFragment.this.confirmpassLayout.isErrorEnabled()) {
                    ChangePasswordFragment.this.confirmpassLayout.setErrorEnabled(false);
                }
                ChangePasswordFragment.this.newPassLayout.setError("Enter new password");
                ChangePasswordFragment.this.edtNewPass.requestFocus();
            } else if (ChangePasswordFragment.this.strNewPass.equals(ChangePasswordFragment.this.confirmPass)) {
                if (ChangePasswordFragment.this.oldpassLayout.isErrorEnabled()) {
                    ChangePasswordFragment.this.oldpassLayout.setErrorEnabled(false);
                }
                if (ChangePasswordFragment.this.confirmpassLayout.isErrorEnabled()) {
                    ChangePasswordFragment.this.confirmpassLayout.setErrorEnabled(false);
                }
                if (ChangePasswordFragment.this.newPassLayout.isErrorEnabled()) {
                    ChangePasswordFragment.this.newPassLayout.setErrorEnabled(false);
                }
                new ChangePasswordTask(ChangePasswordFragment.this.getActivity(), ChangePasswordFragment.this.strOldPass, ChangePasswordFragment.this.strNewPass, ChangePasswordFragment.this.progressDialog).execute(new String[0]);
                ChangePasswordFragment.this.edtOldPass.setText("");
                ChangePasswordFragment.this.edtNewPass.setText("");
                ChangePasswordFragment.this.edtConfirmPass.setText("");
                ChangePasswordFragment.this.edtOldPass.requestFocus();
            } else {
                if (ChangePasswordFragment.this.oldpassLayout.isErrorEnabled()) {
                    ChangePasswordFragment.this.oldpassLayout.setErrorEnabled(false);
                }
                if (ChangePasswordFragment.this.newPassLayout.isErrorEnabled()) {
                    ChangePasswordFragment.this.newPassLayout.setErrorEnabled(false);
                }
                ChangePasswordFragment.this.confirmpassLayout.setError("Password doesn't match.");
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        this.edtOldPass = (EditText) view.findViewById(R.id.edtOldPass);
        this.edtNewPass = (EditText) view.findViewById(R.id.edtNewPass);
        this.btnChangePass = (Button) view.findViewById(R.id.btnChangePass);
        this.edtConfirmPass = (EditText) view.findViewById(R.id.edtRepeatNewPass);
        this.edtOldPass.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.edtNewPass.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.edtConfirmPass.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.oldpassLayout = (TextInputLayout) view.findViewById(R.id.oldPassLayout);
        this.newPassLayout = (TextInputLayout) view.findViewById(R.id.newPassLayout);
        this.confirmpassLayout = (TextInputLayout) view.findViewById(R.id.confirmPassLayout);
        this.oldpassLayout.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.newPassLayout.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.confirmpassLayout.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.btnChangePass.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.btnChangePass.setOnClickListener(new C05311());
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
}
