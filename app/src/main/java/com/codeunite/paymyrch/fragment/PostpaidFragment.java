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

public class PostpaidFragment extends Fragment implements OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] state_id;
    public static String[] state_name;
    String OperatorCode;
    String OperatorName;
    int OperatorPosition;
    private final int PICK_CONTACT_REQUEST = 1;
    String[] SPINNERCODES = new String[]{"ATP", "IDP", "VFP", "RGP", "RCP", "BSP", "TDP", "TIP", "LMP"};
    String[] SPINNERVALUES = new String[]{"Airtel Postpaid", "Idea Postpaid", "Vodafone Postpaid", "Reliance GSM Postpaid", "Reliance CDMA Postpaid", "Bsnl Postpaid", "Tata Docomo Postpaid", "Tata Indicom PostPaid", "Loop Mobile PostPaid"};
    TextInputLayout amountlayout;
    final String billUnit = "";
    public Button btnRecharge;
    View change_Operator;
    Context context;
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
    int[] postpaidImageId = new int[]{R.drawable.airtel, R.drawable.idea, R.drawable.vodafone, R.drawable.reliance, R.drawable.reliance, R.drawable.bsnl, R.drawable.docomo, R.drawable.indicom, R.drawable.loop};
    final String processingUnit = "";
    ProgressDialog progressDialog;
    TextView selected_op;
    ArrayAdapter<String> stateName;
    TextView tvBalance;
    TextView tv_operator;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PostpaidFragment$1 */
    class C06731 implements OnClickListener {
        C06731() {
        }

        public void onClick(View v) {
            PostpaidFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PostpaidFragment$2 */
    class C06742 implements OnClickListener {
        C06742() {
        }

        public void onClick(View v) {
            PostpaidFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PostpaidFragment$3 */
    class C06753 implements TextWatcher {
        C06753() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (PostpaidFragment.this.edtAmount.getText().toString().matches("^0")) {
                PostpaidFragment.this.edtAmount.setText("");
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PostpaidFragment$4 */
    class C06764 implements DialogInterface.OnClickListener {
        C06764() {
        }

        public void onClick(DialogInterface dialog, int id) {
            if (PostpaidFragment.this.moblayout.isErrorEnabled()) {
                PostpaidFragment.this.moblayout.setErrorEnabled(false);
            } else if (PostpaidFragment.this.amountlayout.isErrorEnabled()) {
                PostpaidFragment.this.amountlayout.setErrorEnabled(false);
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PostpaidFragment$6 */
    class C06786 implements OnItemClickListener {
        C06786() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            PostpaidFragment.this.OperatorName = PostpaidFragment.this.SPINNERVALUES[i];
            PostpaidFragment.this.OperatorCode = PostpaidFragment.this.SPINNERCODES[i];
            PostpaidFragment.this.operatorImageView.setImageResource(PostpaidFragment.this.postpaidImageId[i]);
            PostpaidFragment.this.selected_op.setText(PostpaidFragment.this.OperatorName);
            PostpaidFragment.this.tv_operator.setText(PostpaidFragment.this.OperatorName);
            PostpaidFragment.this.dialog.dismiss();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PostpaidFragment$7 */
    class C06797 implements OnClickListener {
        C06797() {
        }

        public void onClick(View view) {
            PostpaidFragment.this.dialog.dismiss();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static PostpaidFragment newInstance(String param1, String param2) {
        PostpaidFragment fragment = new PostpaidFragment();
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
        View view = inflater.inflate(R.layout.fragment_prepaid, container, false);
        this.context = getActivity();
        this.progressDialog = new ProgressDialog(getActivity());
        this.moblayout = (TextInputLayout) view.findViewById(R.id.moblayout);
        this.amountlayout = (TextInputLayout) view.findViewById(R.id.amountlayout);
        this.amountlayout.setHint("Amount");
        this.moblayout.setHint("Mobile No.");
        this.op = (LinearLayout) view.findViewById(R.id.view_op);
        this.operatorImageView = (ImageView) view.findViewById(R.id.operatorimg);
        this.edtMobile = (EditText) view.findViewById(R.id.edtMobile);
        this.edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        this.ivPhoneBook = (ImageView) view.findViewById(R.id.ivPhoneBook);
        this.btnRecharge = (Button) view.findViewById(R.id.btnRecharge);
        this.tvBalance = (TextView) getActivity().findViewById(R.id.tvBalance);
        this.tv_operator = (TextView) view.findViewById(R.id.operator);
        this.op.setOnClickListener(new C06731());
        this.tv_operator.setOnClickListener(new C06742());
        this.opdetails = getArguments();
        this.edtAmount.addTextChangedListener(new C06753());
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
                        this.moblayout.setError("Please Enter Your Mobile Number");
                        this.edtMobile.requestFocus();
                        return;
                    } else if (mobNo.length() > 10) {
                        if (this.amountlayout.isErrorEnabled()) {
                            this.amountlayout.setErrorEnabled(false);
                        }
                        this.moblayout.setError("Please Enter Only 10 digits Of Your Mobile Number");
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
                                new RechargeTask(PostpaidFragment.this.context, CommonUtils.userName, PostpaidFragment.this.tvBalance, mobno, amount, PostpaidFragment.this.OperatorCode, PostpaidFragment.this.progressDialog).execute(new String[0]);
                                PostpaidFragment.this.edtMobile.setText("");
                                if (PostpaidFragment.this.moblayout.isErrorEnabled()) {
                                    PostpaidFragment.this.moblayout.setErrorEnabled(false);
                                } else if (PostpaidFragment.this.amountlayout.isErrorEnabled()) {
                                    PostpaidFragment.this.amountlayout.setErrorEnabled(false);
                                }
                                PostpaidFragment.this.edtAmount.setText("");
                                PostpaidFragment.this.tv_operator.setText("");
                                PostpaidFragment.this.operatorImageView.setImageResource(R.drawable.operator_status);
                                PostpaidFragment.this.edtMobile.requestFocus();
                                if (!PostpaidFragment.this.OperatorCode.equalsIgnoreCase("BT") && !PostpaidFragment.this.OperatorCode.equalsIgnoreCase("DS") && !PostpaidFragment.this.OperatorCode.equalsIgnoreCase("US") && !PostpaidFragment.this.OperatorCode.equalsIgnoreCase("BR") && !PostpaidFragment.this.OperatorCode.equalsIgnoreCase("D") && !PostpaidFragment.this.OperatorCode.equalsIgnoreCase("U")) {
                                }
                            }
                        }).setNegativeButton("No", new C06764());
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
                String moNumber = number.substring(number.length() - 10);
                System.out.println("Test " + moNumber);
                this.edtMobile.setText(moNumber);
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
        this.lvSearchMobile.setAdapter(new OfflineImageSpinnerAdapter(getActivity(), this.SPINNERVALUES, this.SPINNERCODES, this.postpaidImageId));
        this.lvSearchMobile.setOnItemClickListener(new C06786());
        right_arrow.setOnClickListener(new C06797());
        this.dialog.show();
    }
}
