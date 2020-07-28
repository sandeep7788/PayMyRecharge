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
import android.widget.Spinner;
import android.widget.TextView;
import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.codeunite.paymyrch.AsyncTask.BalanceTask;
import com.codeunite.paymyrch.AsyncTask.PlantypeTask;
import com.codeunite.paymyrch.AsyncTask.RechargeTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.OfflineImageSpinnerAdapter;
import com.codeunite.paymyrch.adapter.PlanAdapter;
import com.codeunite.paymyrch.utils.CommonUtils;

public class DTHFragment extends Fragment implements OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] plan_type;
    public static String[] state_id;
    public static String[] state_name;
    String OperatorCode;
    String OperatorName;
    int OperatorPosition;
    private final int PICK_CONTACT_REQUEST = 1;
    String[] SPINNERCODES = new String[]{"AH", "DT", "RB", "SD", "TS", "VD"};
    String[] SPINNERVALUES = new String[]{"Airtel Dth", "DishTv", "Big Tv", "SunTv", "TataSky", "Videocon Dth"};
    ACProgressFlower acProgressFlower;
    TextInputLayout amountlayout;
    public Button btnRecharge;
    View change_Operator;
    Context context;
    Dialog dialog;
    int[] dthImageId = new int[]{R.drawable.airtel, R.drawable.dish, R.drawable.big_tv, R.drawable.sun_tv, R.drawable.tata_sky, R.drawable.videodth};
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
    ProgressDialog progressDialog;
    TextView selected_op;
    TextView showPlan;
    Spinner sp_plan;
    ArrayAdapter<String> stateName;
    TextView tvBalance;
    TextView tv_operator;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DTHFragment$1 */
    class C05371 implements OnClickListener {
        C05371() {
        }

        public void onClick(View v) {
            DTHFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DTHFragment$2 */
    class C05382 implements OnClickListener {
        C05382() {
        }

        public void onClick(View v) {
            DTHFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DTHFragment$3 */
    class C05393 implements TextWatcher {
        C05393() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (DTHFragment.this.tv_operator != null) {
                DTHFragment.this.showPlan.setVisibility(0);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DTHFragment$4 */
    class C05404 implements OnClickListener {
        C05404() {
        }

        public void onClick(View v) {
            DTHFragment.this.dialog = new Dialog(DTHFragment.this.getContext());
            DTHFragment.this.dialog.requestWindowFeature(1);
            DTHFragment.this.dialog.setContentView(R.layout.account_details_dialog);
            DTHFragment.this.dialog.getWindow().setLayout(-1, -2);
            DTHFragment.this.listView = (ExpandableListView) DTHFragment.this.dialog.findViewById(R.id.listFundReceive);
            DTHFragment.this.sp_plan = (Spinner) DTHFragment.this.dialog.findViewById(R.id.sp_plan);
            DTHFragment.this.dialog.show();
            new PlantypeTask(DTHFragment.this.getContext(), DTHFragment.plan_type, DTHFragment.this.planType, DTHFragment.this.sp_plan, DTHFragment.this.OperatorCode, DTHFragment.this.acProgressFlower, DTHFragment.this.listView, DTHFragment.this.edtAmount, DTHFragment.this.dialog, DTHFragment.this.planAdapter, DTHFragment.this.getActivity()).execute(new String[0]);
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DTHFragment$5 */
    class C05415 implements TextWatcher {
        C05415() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (DTHFragment.this.edtAmount.getText().toString().matches("^0")) {
                DTHFragment.this.edtAmount.setText("");
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DTHFragment$6 */
    class C05426 implements DialogInterface.OnClickListener {
        C05426() {
        }

        public void onClick(DialogInterface dialog, int id) {
            if (DTHFragment.this.moblayout.isErrorEnabled()) {
                DTHFragment.this.moblayout.setErrorEnabled(false);
            } else if (DTHFragment.this.amountlayout.isErrorEnabled()) {
                DTHFragment.this.amountlayout.setErrorEnabled(false);
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DTHFragment$8 */
    class C05448 implements OnItemClickListener {
        C05448() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            DTHFragment.this.OperatorName = DTHFragment.this.SPINNERVALUES[i];
            DTHFragment.this.OperatorCode = DTHFragment.this.SPINNERCODES[i];
            DTHFragment.this.operatorImageView.setImageResource(DTHFragment.this.dthImageId[i]);
            DTHFragment.this.selected_op.setText(DTHFragment.this.OperatorName);
            DTHFragment.this.tv_operator.setText(DTHFragment.this.OperatorName);
            DTHFragment.this.dialog.dismiss();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DTHFragment$9 */
    class C05459 implements OnClickListener {
        C05459() {
        }

        public void onClick(View view) {
            DTHFragment.this.dialog.dismiss();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static DTHFragment newInstance(String param1, String param2) {
        DTHFragment fragment = new DTHFragment();
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
        View view = inflater.inflate(R.layout.fragment_dth, container, false);
        this.context = getActivity();
        this.progressDialog = new ProgressDialog(getActivity());
        this.moblayout = (TextInputLayout) view.findViewById(R.id.moblayout);
        this.amountlayout = (TextInputLayout) view.findViewById(R.id.amountlayout);
        this.amountlayout.setHint("Amount");
        this.moblayout.setHint("Customer Id");
        this.op = (LinearLayout) view.findViewById(R.id.view_op);
        this.operatorImageView = (ImageView) view.findViewById(R.id.operatorimg);
        this.edtMobile = (EditText) view.findViewById(R.id.edtMobile);
        this.edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        this.ivPhoneBook = (ImageView) view.findViewById(R.id.ivPhoneBook);
        this.btnRecharge = (Button) view.findViewById(R.id.btnRecharge);
        this.tvBalance = (TextView) getActivity().findViewById(R.id.tvBalance);
        this.tv_operator = (TextView) view.findViewById(R.id.operator);
        this.showPlan = (TextView) view.findViewById(R.id.txt_showPlan_dth);
        this.op.setOnClickListener(new C05371());
        this.tv_operator.setOnClickListener(new C05382());
        this.opdetails = getArguments();
        this.tv_operator.addTextChangedListener(new C05393());
        this.showPlan.setOnClickListener(new C05404());
        this.edtAmount.addTextChangedListener(new C05415());
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
                        this.moblayout.setError("Please Enter Your Customer ID");
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
                        new SweetAlertDialog(this.context, 3).setContentText("Please select any Operator").show();
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
                                new RechargeTask(DTHFragment.this.context, CommonUtils.userName, DTHFragment.this.tvBalance, mobno, amount, DTHFragment.this.OperatorCode, DTHFragment.this.progressDialog).execute(new String[0]);
                                DTHFragment.this.edtMobile.setText("");
                                if (DTHFragment.this.moblayout.isErrorEnabled()) {
                                    DTHFragment.this.moblayout.setErrorEnabled(false);
                                } else if (DTHFragment.this.amountlayout.isErrorEnabled()) {
                                    DTHFragment.this.amountlayout.setErrorEnabled(false);
                                }
                                DTHFragment.this.edtAmount.setText("");
                                DTHFragment.this.tv_operator.setText("");
                                DTHFragment.this.operatorImageView.setImageResource(R.drawable.operator_status);
                                DTHFragment.this.edtMobile.requestFocus();
                                if (!DTHFragment.this.OperatorCode.equalsIgnoreCase("BT") && !DTHFragment.this.OperatorCode.equalsIgnoreCase("DS") && !DTHFragment.this.OperatorCode.equalsIgnoreCase("US") && !DTHFragment.this.OperatorCode.equalsIgnoreCase("BR") && !DTHFragment.this.OperatorCode.equalsIgnoreCase("D") && !DTHFragment.this.OperatorCode.equalsIgnoreCase("U")) {
                                }
                            }
                        }).setNegativeButton("No", new C05426());
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
        this.lvSearchMobile.setAdapter(new OfflineImageSpinnerAdapter(getActivity(), this.SPINNERVALUES, this.SPINNERCODES, this.dthImageId));
        this.lvSearchMobile.setOnItemClickListener(new C05448());
        right_arrow.setOnClickListener(new C05459());
        this.dialog.show();
    }
}
