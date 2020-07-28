package com.codeunite.paymyrch.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.codeunite.paymyrch.AsyncTask.AccountDetailsTask;
import com.codeunite.paymyrch.AsyncTask.BankNameListTask;
import com.codeunite.paymyrch.AsyncTask.FundRequestTask;
import com.codeunite.paymyrch.R;

public class FundRequestFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] bank_name;
    public static String[] bank_name_to;
    public static String[] paymentMode = new String[]{"Cash", "Cheque", "Demand Draft(DD)", "Net Banking", "Online"};
    ArrayAdapter<String> bankName;
    ArrayAdapter<String> bankName_to;
    Button btn_send;
    EditText edtAmount;
    EditText edtChequeNumber;
    EditText edtRemark;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    Spinner spBankFrom;
    Spinner sppaymentMode;
    Spinner sptoBank;
    String str_ChequeDate;
    String str_ChequeNumber;
    String str_Remark;
    String str_amount;
    String str_paymentMode;
    TextInputLayout til_amount;
    TextInputLayout til_chequeNumber;
    TextInputLayout til_remark;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.FundRequestFragment$1 */
    class C05751 implements OnItemSelectedListener {
        C05751() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            FundRequestFragment.this.str_paymentMode = (String) FundRequestFragment.this.sppaymentMode.getItemAtPosition(position);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.FundRequestFragment$2 */
    class C05762 implements OnClickListener {
        C05762() {
        }

        public void onClick(View v) {
            FundRequestFragment.this.str_amount = FundRequestFragment.this.edtAmount.getText().toString();
            FundRequestFragment.this.str_ChequeNumber = FundRequestFragment.this.edtChequeNumber.getText().toString();
            FundRequestFragment.this.str_Remark = FundRequestFragment.this.edtRemark.getText().toString();
            if (BankNameListTask.BN.equalsIgnoreCase("Select Bank")) {
                FundRequestFragment.this.Dialog("Select From Bank Name.");
            } else if (AccountDetailsTask.BN_to.equalsIgnoreCase("Select Bank")) {
                FundRequestFragment.this.Dialog("Select To Bank Name.");
            } else if (FundRequestFragment.this.str_amount.equalsIgnoreCase("")) {
                FundRequestFragment.this.setError(FundRequestFragment.this.til_amount, "Please Enter Amount");
                if (FundRequestFragment.this.til_chequeNumber.isErrorEnabled()) {
                    FundRequestFragment.this.til_chequeNumber.setErrorEnabled(false);
                } else if (FundRequestFragment.this.til_remark.isErrorEnabled()) {
                    FundRequestFragment.this.til_remark.setErrorEnabled(false);
                }
            } else if (FundRequestFragment.this.str_ChequeNumber.equalsIgnoreCase("")) {
                FundRequestFragment.this.setError(FundRequestFragment.this.til_chequeNumber, "Please Enter Cheque Or DD Number.");
                if (FundRequestFragment.this.til_amount.isErrorEnabled()) {
                    FundRequestFragment.this.til_amount.setErrorEnabled(false);
                } else if (FundRequestFragment.this.til_remark.isErrorEnabled()) {
                    FundRequestFragment.this.til_remark.setErrorEnabled(false);
                }
            } else if (FundRequestFragment.this.str_Remark.equalsIgnoreCase("")) {
                FundRequestFragment.this.setError(FundRequestFragment.this.til_remark, "Please Enter Amount");
                if (FundRequestFragment.this.til_chequeNumber.isErrorEnabled()) {
                    FundRequestFragment.this.til_chequeNumber.setErrorEnabled(false);
                } else if (FundRequestFragment.this.til_amount.isErrorEnabled()) {
                    FundRequestFragment.this.til_amount.setErrorEnabled(false);
                }
            } else {
                FundRequestFragment.this.til_amount.setErrorEnabled(false);
                FundRequestFragment.this.til_chequeNumber.setErrorEnabled(false);
                FundRequestFragment.this.til_remark.setErrorEnabled(false);
                new FundRequestTask(FundRequestFragment.this.getContext(), FundRequestFragment.this.progressDialog, BankNameListTask.BN, AccountDetailsTask.BN_to, FundRequestFragment.this.str_paymentMode, FundRequestFragment.this.str_amount, FundRequestFragment.this.str_ChequeNumber, FundRequestFragment.this.str_Remark, FundRequestFragment.this.edtAmount, FundRequestFragment.this.edtChequeNumber, FundRequestFragment.this.edtRemark, FundRequestFragment.this.spBankFrom, FundRequestFragment.this.sptoBank, FundRequestFragment.this.sppaymentMode).execute(new String[0]);
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static FundRequestFragment newInstance(String param1, String param2) {
        FundRequestFragment fragment = new FundRequestFragment();
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
        View view = inflater.inflate(R.layout.fragment_fund_request, container, false);
        this.spBankFrom = (Spinner) view.findViewById(R.id.sp_fromBank);
        this.sptoBank = (Spinner) view.findViewById(R.id.sp_toBank);
        this.sppaymentMode = (Spinner) view.findViewById(R.id.sp_paymentMode);
        this.til_amount = (TextInputLayout) view.findViewById(R.id.amount_Wraper);
        this.til_chequeNumber = (TextInputLayout) view.findViewById(R.id.ChequeOrDDNumber_Wraper);
        this.til_remark = (TextInputLayout) view.findViewById(R.id.Remark_Wraper);
        this.til_amount.setHint("Amount");
        this.til_chequeNumber.setHint("Transcation Or DD Number");
        this.til_remark.setHint("Remark");
        this.edtAmount = (EditText) view.findViewById(R.id.edt_amount);
        this.edtChequeNumber = (EditText) view.findViewById(R.id.edt_ChequeOrDDNumber);
        this.edtRemark = (EditText) view.findViewById(R.id.edt_Remark);
        this.btn_send = (Button) view.findViewById(R.id.btn_sendFundRequest);
        this.progressDialog = new ProgressDialog(getContext());
        new BankNameListTask(getContext(), bank_name, this.bankName, this.spBankFrom).execute(new String[0]);
        new AccountDetailsTask(getContext(), bank_name_to, this.bankName_to, this.sptoBank).execute(new String[0]);
        ArrayAdapter<String> adapter_operator = new ArrayAdapter(getActivity(), 17367048, paymentMode);
        adapter_operator.setDropDownViewResource(17367049);
        this.sppaymentMode.setAdapter(adapter_operator);
        this.sppaymentMode.setOnItemSelectedListener(new C05751());
        this.btn_send.setOnClickListener(new C05762());
        return view;
    }

    public void Dialog(String msg) {
        new SweetAlertDialog(getContext(), 3).setTitleText("Something Wrong...").setContentText(msg).show();
    }

    void setError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
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
