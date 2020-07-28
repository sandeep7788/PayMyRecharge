package com.codeunite.paymyrch.fragment;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.codeunite.paymyrch.AsyncTask.FundTransferTask;
import com.codeunite.paymyrch.AsyncTask.RetailerListTask;
import com.codeunite.paymyrch.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class FundTransferFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] retailer_id;
    String T_mode;
    String amount;
    AutoCompleteTextView autoCompleteTextView;
    Button btnTransfer;
    Context context;
    EditText edtAmount;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    String re;
    String re1;
    ArrayAdapter<String> retailerid;
    SearchableSpinner searchableSpinner;
    TextInputLayout tilamount;
    TextView tvBalance;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.FundTransferFragment$1 */
    class C05791 implements OnItemSelectedListener {
        C05791() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            String retailerData = RetailerListTask.retailer_id[position];
            FundTransferFragment.this.re1 = FundTransferFragment.this.searchableSpinner.getItemAtPosition(position).toString();
            if (retailerData.length() >= 4) {
                String[] temp = retailerData.split(":");
                FundTransferFragment.this.re = temp[0];
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.FundTransferFragment$2 */
    class C05822 implements OnClickListener {

        /* renamed from: com.codeunite.PayMyRecharge.fragment.FundTransferFragment$2$1 */
        class C05801 implements DialogInterface.OnClickListener {
            C05801() {
            }

            public void onClick(DialogInterface dialog, int id) {
            }
        }

        /* renamed from: com.codeunite.PayMyRecharge.fragment.FundTransferFragment$2$2 */
        class C05812 implements DialogInterface.OnClickListener {
            C05812() {
            }

            public void onClick(DialogInterface dialog, int id) {
                new FundTransferTask(FundTransferFragment.this.getContext(), FundTransferFragment.this.re, FundTransferFragment.this.amount, FundTransferFragment.this.progressDialog, FundTransferFragment.this.tvBalance).execute(new String[0]);
                FundTransferFragment.this.searchableSpinner.setSelection(0);
                FundTransferFragment.this.edtAmount.setText("");
            }
        }

        C05822() {
        }

        public void onClick(View v) {
            FundTransferFragment.this.amount = FundTransferFragment.this.edtAmount.getText().toString();
            if (FundTransferFragment.this.amount.equalsIgnoreCase("")) {
                FundTransferFragment.this.setError(FundTransferFragment.this.tilamount);
            } else if (FundTransferFragment.this.re1.equalsIgnoreCase("Select Member Id")) {
                new SweetAlertDialog(FundTransferFragment.this.getActivity(), 3).setTitleText("Oops...").setContentText("Please Select Member ID.").show();
            } else {
                FundTransferFragment.this.tilamount.setErrorEnabled(false);
                Builder alertDialogBuilder = new Builder(FundTransferFragment.this.getActivity());
                alertDialogBuilder.setTitle("Please Confirm");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setMessage("Member: " + FundTransferFragment.this.re + "\nAMOUNT: " + FundTransferFragment.this.amount).setCancelable(false).setPositiveButton("Yes", new C05812()).setNegativeButton("No", new C05801());
                alertDialogBuilder.create().show();
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static FundTransferFragment newInstance(String param1, String param2) {
        FundTransferFragment fragment = new FundTransferFragment();
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
        View view = inflater.inflate(R.layout.fragment_fund_transfer, container, false);
        this.edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        this.btnTransfer = (Button) view.findViewById(R.id.btnTransfer);
        this.tilamount = (TextInputLayout) view.findViewById(R.id.amount_wraper);
        this.progressDialog = new ProgressDialog(getActivity());
        this.tvBalance = (TextView) getActivity().findViewById(R.id.tvBalance);
        this.searchableSpinner = (SearchableSpinner) view.findViewById(R.id.retailer_auto);
        new RetailerListTask(getActivity(), this.progressDialog, retailer_id, this.retailerid, this.searchableSpinner).execute(new String[0]);
        this.searchableSpinner.setOnItemSelectedListener(new C05791());
        this.btnTransfer.setOnClickListener(new C05822());
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

    void setError(TextInputLayout textInputLayout) {
        textInputLayout.setError("Please Enter Amount");
    }
}
