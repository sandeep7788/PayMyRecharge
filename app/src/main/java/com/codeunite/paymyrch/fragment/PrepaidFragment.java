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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.codeunite.paymyrch.AsyncTask.BalanceTask;
import com.codeunite.paymyrch.AsyncTask.PlantypeTask;
import com.codeunite.paymyrch.AsyncTask.RechargeTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.OfflineImageSpinnerAdapter;
import com.codeunite.paymyrch.adapter.PlanAdapter;
import com.codeunite.paymyrch.utils.CommonUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PrepaidFragment extends Fragment implements OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] plan_type;
    public static String[] state_id;
    public static String[] state_name;
    String OperatorCode;
    String OperatorName;
    int OperatorPosition;
    private final int PICK_CONTACT_REQUEST = 1;
    String[] SPINNERCODES = new String[]{"AC", "AT", "BR", "TD", "IC", "MT", "RG", "UN", "VF", "MTT", "VG", "VI", "JIO"};
    String[] SPINNERVALUES = new String[]{"Aircel", "Airtel", "Bsnl", "Docomo", "Idea", "Mts", "Reliance", "Telenor", "Vodafone", "Mtnl", "Virgin", "Videocon", "JIO"};
    ACProgressFlower acProgressFlower;
    TextInputLayout amountlayout;
    final String billUnit = "";
    public Button btnRecharge;
    View change_Operator;
    Context context;
    Dialog dialog;
    public EditText edtAmount;
    public EditText edtMobile;
    ImageView ivPhoneBook;
    ExpandableListView listView;
    ListView lvSearchMobile;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    TextInputLayout moblayout;
    LinearLayout op;
    TextView op_change;
    Bundle opdetails;
    ImageView operatorImageView;
    PlanAdapter planAdapter;
    ArrayAdapter<String> planType;
    String plan_name_code;
    int[] prepaidImageId = new int[]{R.drawable.aircel, R.drawable.airtel, R.drawable.bsnl, R.drawable.docomo, R.drawable.idea, R.drawable.mts, R.drawable.reliance, R.drawable.telenor, R.drawable.vodafone, R.drawable.mtnl, R.drawable.virgin, R.drawable.videocon_mobile, R.drawable.jio};
    final String processingUnit = "";
    ProgressDialog progressDialog;
    RadioButton rbOperatorOne;
    RadioButton rbOperatorTwo;
    RadioGroup rgOperatorCode;
    TextView selected_op;
    TextView showPlan;
    Spinner spState;
    Spinner sp_plan;
    ArrayAdapter<String> stateName;
    TextView tvBalance;
    TextView tv_operator;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PrepaidFragment$1 */
    class C06801 implements OnClickListener {
        C06801() {
        }

        public void onClick(View v) {
            PrepaidFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PrepaidFragment$2 */
    class C06812 implements OnClickListener {
        C06812() {
        }

        public void onClick(View v) {
            PrepaidFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PrepaidFragment$3 */
    class C06823 implements TextWatcher {
        C06823() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (PrepaidFragment.this.tv_operator != null) {
                PrepaidFragment.this.showPlan.setVisibility(0);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PrepaidFragment$4 */
    class C06834 implements OnClickListener {
        C06834() {
        }

        public void onClick(View v) {
            PrepaidFragment.this.dialog = new Dialog(PrepaidFragment.this.getContext());
            PrepaidFragment.this.dialog.requestWindowFeature(1);
            PrepaidFragment.this.dialog.setContentView(R.layout.account_details_dialog);
            PrepaidFragment.this.dialog.getWindow().setLayout(-1, -2);
            PrepaidFragment.this.listView = (ExpandableListView) PrepaidFragment.this.dialog.findViewById(R.id.listFundReceive);
            PrepaidFragment.this.sp_plan = (Spinner) PrepaidFragment.this.dialog.findViewById(R.id.sp_plan);
            PrepaidFragment.this.dialog.show();
            String OperatorName1=PrepaidFragment.this.OperatorCode.toString();
            //Log.d("checkashok", ServerUtils.BaseUrl + ServerUtils.PlanTypeUrl + "operatorCode=" + OperatorName1);
            new PlantypeTask(PrepaidFragment.this.getContext(), PrepaidFragment.plan_type, PrepaidFragment.this.planType, PrepaidFragment.this.sp_plan, PrepaidFragment.this.OperatorCode, PrepaidFragment.this.acProgressFlower, PrepaidFragment.this.listView, PrepaidFragment.this.edtAmount, PrepaidFragment.this.dialog, PrepaidFragment.this.planAdapter, PrepaidFragment.this.getActivity()).execute(new String[0]);
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PrepaidFragment$5 */
    class C06845 implements TextWatcher {
        C06845() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (PrepaidFragment.this.edtAmount.getText().toString().matches("^0")) {
                PrepaidFragment.this.edtAmount.setText("");
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PrepaidFragment$6 */
    class C06856 implements DialogInterface.OnClickListener {
        C06856() {
        }

        public void onClick(DialogInterface dialog, int id) {
            if (PrepaidFragment.this.moblayout.isErrorEnabled()) {
                PrepaidFragment.this.moblayout.setErrorEnabled(false);
            } else if (PrepaidFragment.this.amountlayout.isErrorEnabled()) {
                PrepaidFragment.this.amountlayout.setErrorEnabled(false);
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PrepaidFragment$8 */
    class C06878 implements OnItemClickListener {
        C06878() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            PrepaidFragment.this.OperatorName = PrepaidFragment.this.SPINNERVALUES[i];
            PrepaidFragment.this.OperatorCode = PrepaidFragment.this.SPINNERCODES[i];
            int img = PrepaidFragment.this.prepaidImageId[i];
            if (PrepaidFragment.this.OperatorCode.equalsIgnoreCase("BR")) {
                PrepaidFragment.this.rgOperatorCode.setVisibility(0);
                PrepaidFragment.this.rbOperatorOne.setText("Topup");
                PrepaidFragment.this.rbOperatorTwo.setText("Special");
            } else if (PrepaidFragment.this.OperatorCode.equalsIgnoreCase("VI")) {
                PrepaidFragment.this.rgOperatorCode.setVisibility(0);
                PrepaidFragment.this.rbOperatorOne.setText("Topup");
                PrepaidFragment.this.rbOperatorTwo.setText("Special");
            } else if (PrepaidFragment.this.OperatorCode.equalsIgnoreCase("UN")) {
                PrepaidFragment.this.rgOperatorCode.setVisibility(0);
                PrepaidFragment.this.rbOperatorOne.setText("Topup");
                PrepaidFragment.this.rbOperatorTwo.setText("Special");
            } else {
                PrepaidFragment.this.rgOperatorCode.setVisibility(8);
            }
            PrepaidFragment.this.operatorImageView.setImageResource(img);
            PrepaidFragment.this.selected_op.setText(PrepaidFragment.this.OperatorName);
            PrepaidFragment.this.tv_operator.setText(PrepaidFragment.this.OperatorName);
            PrepaidFragment.this.dialog.dismiss();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.PrepaidFragment$9 */
    class C06889 implements OnClickListener {
        C06889() {
        }

        public void onClick(View view) {
            PrepaidFragment.this.dialog.dismiss();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static PrepaidFragment newInstance(String param1, String param2) {
        PrepaidFragment fragment = new PrepaidFragment();
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
        this.showPlan = (TextView) view.findViewById(R.id.txt_showPlan);
        this.rgOperatorCode = (RadioGroup) view.findViewById(R.id.rgOperatorCodes);
        this.rbOperatorOne = (RadioButton) view.findViewById(R.id.rbOperatorOne);
        this.rbOperatorTwo = (RadioButton) view.findViewById(R.id.rbOperatorTwo);
        this.rgOperatorCode.setVisibility(8);
        this.op.setOnClickListener(new C06801());
        this.tv_operator.setOnClickListener(new C06812());
        this.tv_operator.addTextChangedListener(new C06823());
        this.showPlan.setOnClickListener(new C06834());
        this.edtAmount.addTextChangedListener(new C06845());
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
                if (this.OperatorCode != null) {
                    if (this.OperatorCode.equalsIgnoreCase("BR")) {
                        if (this.rbOperatorOne.isChecked()) {
                            this.OperatorCode = "BR";
                        }
                        if (this.rbOperatorTwo.isChecked()) {
                            this.OperatorCode = "BV";
                        }
                    }
                    if (this.OperatorCode.equalsIgnoreCase("UN")) {
                        if (this.rbOperatorOne.isChecked()) {
                            this.OperatorCode = "UN";
                        }
                        if (this.rbOperatorTwo.isChecked()) {
                            this.OperatorCode = "UNS";
                        }
                    }
                    if (this.OperatorCode.equalsIgnoreCase("VI")) {
                        if (this.rbOperatorOne.isChecked()) {
                            this.OperatorCode = "VI";
                        }
                        if (this.rbOperatorTwo.isChecked()) {
                            this.OperatorCode = "VS";
                        }
                    }
                }
                try {
                    String mobNo = this.edtMobile.getText().toString().trim();
                    String operator_ok = this.tv_operator.getText().toString().trim();
                    if (this.edtMobile.getText().toString().trim().length() == 0) {
                        if (this.amountlayout.isErrorEnabled()) {
                            this.amountlayout.setErrorEnabled(false);
                        }
                        this.moblayout.setError("Please Enter Your Mobile Number");
                        this.edtMobile.requestFocus();
                        return;
                    } else if (this.edtMobile.getText().toString().trim().length() > 10) {
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
                                new RechargeTask(PrepaidFragment.this.context, CommonUtils.userName, PrepaidFragment.this.tvBalance, mobno, amount, PrepaidFragment.this.OperatorCode, PrepaidFragment.this.progressDialog).execute(new String[0]);
                                PrepaidFragment.this.edtMobile.setText("");
                                if (PrepaidFragment.this.moblayout.isErrorEnabled()) {
                                    PrepaidFragment.this.moblayout.setErrorEnabled(false);
                                } else if (PrepaidFragment.this.amountlayout.isErrorEnabled()) {
                                    PrepaidFragment.this.amountlayout.setErrorEnabled(false);
                                }
                                PrepaidFragment.this.edtAmount.setText("");
                                PrepaidFragment.this.tv_operator.setText("");
                                PrepaidFragment.this.operatorImageView.setImageResource(R.drawable.operator_status);
                                PrepaidFragment.this.edtMobile.requestFocus();
                                PrepaidFragment.this.showPlan.setVisibility(8);
                                if (PrepaidFragment.this.OperatorCode.equalsIgnoreCase("BT") || PrepaidFragment.this.OperatorCode.equalsIgnoreCase("DS") || PrepaidFragment.this.OperatorCode.equalsIgnoreCase("US") || PrepaidFragment.this.OperatorCode.equalsIgnoreCase("BR") || PrepaidFragment.this.OperatorCode.equalsIgnoreCase("D") || PrepaidFragment.this.OperatorCode.equalsIgnoreCase("U")) {
                                    PrepaidFragment.this.rgOperatorCode.setVisibility(8);
                                }
                            }
                        }).setNegativeButton("No", new C06856());
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

    private Boolean isValidNumber(String mob) {
        Matcher matcher = Pattern.compile("[7-9][0-9]{9}").matcher(mob);
        if (matcher.find() && matcher.group() == mob) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public void selectOperator() {
        this.dialog = new Dialog(getActivity(), R.style.DialogFragmentTheme);
        this.dialog.setContentView(R.layout.fragment_prepaid_list);
        this.lvSearchMobile = (ListView) this.dialog.findViewById(R.id.listview134);
        ImageView right_arrow = (ImageView) this.dialog.findViewById(R.id.back);
        this.op_change = (TextView) this.dialog.findViewById(R.id.change_op);
        this.selected_op = (TextView) this.dialog.findViewById(R.id.select_op);
        this.change_Operator = this.dialog.findViewById(R.id.op_change_layout);
        this.lvSearchMobile.setAdapter(new OfflineImageSpinnerAdapter(getActivity(), this.SPINNERVALUES, this.SPINNERCODES, this.prepaidImageId));
        this.lvSearchMobile.setOnItemClickListener(new C06878());
        right_arrow.setOnClickListener(new C06889());
        this.dialog.show();
    }
}
