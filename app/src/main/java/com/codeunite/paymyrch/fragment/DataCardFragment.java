package com.codeunite.paymyrch.fragment;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.codeunite.paymyrch.AsyncTask.BalanceTask;
import com.codeunite.paymyrch.AsyncTask.RechargeTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.OfflineImageSpinnerAdapter;
import com.codeunite.paymyrch.utils.CommonUtils;

public class DataCardFragment extends Fragment implements OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] state_id;
    public static String[] state_name;
    String OperatorCode;
    String OperatorName;
    int OperatorPosition;
    private final int PICK_CONTACT_REQUEST = 1;
    String[] SPINNERCODES = new String[]{"MTZ", "MTW", "RN", "RNG", "RNC", "TPW", "TPP"};
    String[] SPINNERVALUES = new String[]{"MTS Mblaze", "MTS Mbrowse", "Reliance NetConnect 1X", "Reliance NetConnect 3G", "Reliance NetConnect+", "Tata Photon Whiz", "Tata Photon+"};
    TextInputLayout amountlayout;
    public Button btnRecharge;
    View change_Operator;
    Context context;
    int[] datacardImageId = new int[]{R.drawable.mts, R.drawable.mts, R.drawable.reliance, R.drawable.reliance, R.drawable.reliance, R.drawable.tata_photon, R.drawable.tata_photon};
    Dialog dialog;
    public EditText edtAmount;
    public EditText edtMobile;
    ImageView ivPhoneBook;
    ListView lvSearchMobile;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    TextInputLayout moblayout;
    LinearLayout op;
    TextView op_change;
    Bundle opdetails;
    ImageView operatorImageView;
    ProgressDialog progressDialog;
    TextView selected_op;
    ArrayAdapter<String> stateName;
    TextView tvBalance;
    TextView tv_operator;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DataCardFragment$1 */
    class C05461 implements OnClickListener {
        C05461() {
        }

        public void onClick(View v) {
            DataCardFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DataCardFragment$2 */
    class C05472 implements OnClickListener {
        C05472() {
        }

        public void onClick(View v) {
            DataCardFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DataCardFragment$3 */
    class C05483 implements TextWatcher {
        C05483() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (DataCardFragment.this.edtAmount.getText().toString().matches("^0")) {
                DataCardFragment.this.edtAmount.setText("");
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DataCardFragment$4 */
    class C05494 implements DialogInterface.OnClickListener {
        C05494() {
        }

        public void onClick(DialogInterface dialog, int id) {
            if (DataCardFragment.this.moblayout.isErrorEnabled()) {
                DataCardFragment.this.moblayout.setErrorEnabled(false);
            } else if (DataCardFragment.this.amountlayout.isErrorEnabled()) {
                DataCardFragment.this.amountlayout.setErrorEnabled(false);
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DataCardFragment$6 */
    class C05516 implements OnItemClickListener {
        C05516() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            DataCardFragment.this.OperatorName = DataCardFragment.this.SPINNERVALUES[i];
            DataCardFragment.this.OperatorCode = DataCardFragment.this.SPINNERCODES[i];
            DataCardFragment.this.operatorImageView.setImageResource(DataCardFragment.this.datacardImageId[i]);
            DataCardFragment.this.selected_op.setText(DataCardFragment.this.OperatorName);
            DataCardFragment.this.tv_operator.setText(DataCardFragment.this.OperatorName);
            DataCardFragment.this.dialog.dismiss();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DataCardFragment$7 */
    class C05527 implements OnClickListener {
        C05527() {
        }

        public void onClick(View view) {
            DataCardFragment.this.dialog.dismiss();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static DataCardFragment newInstance(String param1, String param2) {
        DataCardFragment fragment = new DataCardFragment();
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
        View view = inflater.inflate(R.layout.fragment_data_card, container, false);
        this.context = getActivity();
        this.progressDialog = new ProgressDialog(getActivity());
        this.moblayout = (TextInputLayout) view.findViewById(R.id.moblayout);
        this.amountlayout = (TextInputLayout) view.findViewById(R.id.amountlayout);
        this.amountlayout.setHint("Amount");
        this.moblayout.setHint("Data card No.");
        this.op = (LinearLayout) view.findViewById(R.id.view_op);
        this.operatorImageView = (ImageView) view.findViewById(R.id.operatorimg);
        this.edtMobile = (EditText) view.findViewById(R.id.edtMobile);
        this.edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        this.ivPhoneBook = (ImageView) view.findViewById(R.id.ivPhoneBook);
        this.btnRecharge = (Button) view.findViewById(R.id.btnRecharge);
        this.tvBalance = (TextView) getActivity().findViewById(R.id.tvBalance);
        this.tv_operator = (TextView) view.findViewById(R.id.operator);
        this.op.setOnClickListener(new C05461());
        this.tv_operator.setOnClickListener(new C05472());
        this.opdetails = getArguments();
        this.edtAmount.addTextChangedListener(new C05483());
        this.ivPhoneBook.setOnClickListener(this);
        this.btnRecharge.setOnClickListener(this);
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPhoneBook:
                browseContacts();
                return;
            case R.id.btnRecharge:
                new BalanceTask(this.context, this.tvBalance, this.progressDialog).execute(new String[0]);
                try {
                    String mobNo = this.edtMobile.getText().toString().trim();
                    String operator_ok = this.tv_operator.getText().toString().trim();
                    if (mobNo.length() == 0) {
                        if (this.amountlayout.isErrorEnabled()) {
                            this.amountlayout.setErrorEnabled(false);
                        }
                        this.moblayout.setError("Please Enter Your Data Card Number");
                        this.edtMobile.requestFocus();
                        return;
                    } else if (this.edtAmount.getText().toString().trim().length() == 0) {
                        if (this.moblayout.isErrorEnabled()) {
                            this.moblayout.setErrorEnabled(false);
                        }
                        this.amountlayout.setError("Please Enter Amount");
                        this.edtAmount.requestFocus();
                        return;
                    } else if (operator_ok.equals("")) {
                        new SweetAlertDialog(getContext(), 3).setContentText("Please select any Operator").show();
                        return;
                    } else {
                        if (this.amountlayout.isErrorEnabled()) {
                            this.amountlayout.setErrorEnabled(false);
                        }
                        final String mobno = this.edtMobile.getText().toString().trim();
                        final String amount = this.edtAmount.getText().toString().trim();
                        Builder alertDialogBuilder = new Builder(getActivity());
                        alertDialogBuilder.setTitle("Please Confirm");
                        alertDialogBuilder.setIcon(R.drawable.confirmm);
                        alertDialogBuilder.setMessage("MOB NO: " + mobno + "\nOPERATOR: " + this.OperatorName + "\nAMOUNT: " + amount).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new RechargeTask(DataCardFragment.this.context, CommonUtils.userName, DataCardFragment.this.tvBalance, mobno, amount, DataCardFragment.this.OperatorCode, DataCardFragment.this.progressDialog).execute(new String[0]);
                                DataCardFragment.this.edtMobile.setText("");
                                if (DataCardFragment.this.moblayout.isErrorEnabled()) {
                                    DataCardFragment.this.moblayout.setErrorEnabled(false);
                                } else if (DataCardFragment.this.amountlayout.isErrorEnabled()) {
                                    DataCardFragment.this.amountlayout.setErrorEnabled(false);
                                }
                                DataCardFragment.this.edtAmount.setText("");
                                DataCardFragment.this.tv_operator.setText("");
                                DataCardFragment.this.operatorImageView.setImageResource(R.drawable.operator_status);
                                DataCardFragment.this.edtMobile.requestFocus();
                                if (!DataCardFragment.this.OperatorCode.equalsIgnoreCase("BT") && !DataCardFragment.this.OperatorCode.equalsIgnoreCase("DS") && !DataCardFragment.this.OperatorCode.equalsIgnoreCase("US") && !DataCardFragment.this.OperatorCode.equalsIgnoreCase("BR") && !DataCardFragment.this.OperatorCode.equalsIgnoreCase("D") && !DataCardFragment.this.OperatorCode.equalsIgnoreCase("U")) {
                                }
                            }
                        }).setNegativeButton("No", new C05494());
                        alertDialogBuilder.create().show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }

    private void browseContacts() {
        Intent pickContactIntent = new Intent("android.intent.action.PICK", Uri.parse("content://contacts"));
        pickContactIntent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(pickContactIntent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            getActivity();
            if (resultCode == -1) {
                Cursor cursor = getActivity().getContentResolver().query(data.getData(), new String[]{"data1"}, null, null, null);
                cursor.moveToFirst();
                String number = cursor.getString(cursor.getColumnIndex("data1")).replace(" ", "");
                this.edtMobile.setText(number.substring(number.length() - 10));
            }
        }
    }

    public void selectOperator() {
        this.dialog = new Dialog(getActivity(), R.style.DialogFragmentTheme);
        this.dialog.setContentView(R.layout.fragment_prepaid_list);
        this.lvSearchMobile = (ListView) this.dialog.findViewById(R.id.listview134);
        ImageView right_arrow = (ImageView) this.dialog.findViewById(R.id.back);
        this.op_change = (TextView) this.dialog.findViewById(R.id.change_op);
        this.selected_op = (TextView) this.dialog.findViewById(R.id.select_op);
        this.change_Operator = this.dialog.findViewById(R.id.op_change_layout);
        this.lvSearchMobile.setAdapter(new OfflineImageSpinnerAdapter(getActivity(), this.SPINNERVALUES, this.SPINNERCODES, this.datacardImageId));
        this.lvSearchMobile.setOnItemClickListener(new C05516());
        right_arrow.setOnClickListener(new C05527());
        this.dialog.show();
    }
}
