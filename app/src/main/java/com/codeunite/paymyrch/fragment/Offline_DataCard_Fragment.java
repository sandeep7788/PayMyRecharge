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
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.OfflineImageSpinnerAdapter;

public class Offline_DataCard_Fragment extends DialogFragment implements OnClickListener {
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

    /* renamed from: com.codeunite.PayMyRecharge.fragment.Offline_DataCard_Fragment$1 */
    class C06171 implements OnClickListener {
        C06171() {
        }

        public void onClick(View v) {
            Offline_DataCard_Fragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.Offline_DataCard_Fragment$2 */
    class C06182 implements OnClickListener {
        C06182() {
        }

        public void onClick(View v) {
            Offline_DataCard_Fragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.Offline_DataCard_Fragment$3 */
    class C06193 implements TextWatcher {
        C06193() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (Offline_DataCard_Fragment.this.edtAmount.getText().toString().matches("^0")) {
                Offline_DataCard_Fragment.this.edtAmount.setText("");
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.Offline_DataCard_Fragment$4 */
    class C06204 implements DialogInterface.OnClickListener {
        C06204() {
        }

        public void onClick(DialogInterface dialog, int id) {
            if (Offline_DataCard_Fragment.this.moblayout.isErrorEnabled()) {
                Offline_DataCard_Fragment.this.moblayout.setErrorEnabled(false);
            } else if (Offline_DataCard_Fragment.this.amountlayout.isErrorEnabled()) {
                Offline_DataCard_Fragment.this.amountlayout.setErrorEnabled(false);
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.Offline_DataCard_Fragment$6 */
    class C06226 implements OnItemClickListener {
        C06226() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Offline_DataCard_Fragment.this.OperatorName = Offline_DataCard_Fragment.this.SPINNERVALUES[i];
            Offline_DataCard_Fragment.this.OperatorCode = Offline_DataCard_Fragment.this.SPINNERCODES[i];
            Offline_DataCard_Fragment.this.operatorImageView.setImageResource(Offline_DataCard_Fragment.this.datacardImageId[i]);
            Offline_DataCard_Fragment.this.selected_op.setText(Offline_DataCard_Fragment.this.OperatorName);
            Offline_DataCard_Fragment.this.tv_operator.setText(Offline_DataCard_Fragment.this.OperatorName);
            Offline_DataCard_Fragment.this.dialog.dismiss();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.Offline_DataCard_Fragment$7 */
    class C06237 implements OnClickListener {
        C06237() {
        }

        public void onClick(View view) {
            Offline_DataCard_Fragment.this.dialog.dismiss();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static Offline_DataCard_Fragment newInstance(String param1, String param2) {
        Offline_DataCard_Fragment fragment = new Offline_DataCard_Fragment();
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

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_offline__data_card_, null);
        Builder builder = new Builder(getActivity());
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
        this.op.setOnClickListener(new C06171());
        this.tv_operator.setOnClickListener(new C06182());
        this.opdetails = getArguments();
        this.edtAmount.addTextChangedListener(new C06193());
        this.ivPhoneBook.setOnClickListener(this);
        this.btnRecharge.setOnClickListener(this);
        builder.setTitle("DataCard Recharge").setView(view);
        return builder.create();
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
                        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                        alertDialogBuilder.setMessage("MOB NO: " + mobno + "\nOPERATOR: " + this.OperatorName + "\nAMOUNT: " + amount).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent sendIntent = new Intent("android.intent.action.VIEW");
                                sendIntent.setType("vnd.android-dir/mms-sms");
                                sendIntent.putExtra("address", "+917619733777");
                                sendIntent.putExtra("sms_body", "PMR " + Offline_DataCard_Fragment.this.OperatorCode + " " + mobno + " " + amount + " 19");
                                Offline_DataCard_Fragment.this.startActivity(sendIntent);
                                Offline_DataCard_Fragment.this.edtMobile.setText("");
                                if (Offline_DataCard_Fragment.this.moblayout.isErrorEnabled()) {
                                    Offline_DataCard_Fragment.this.moblayout.setErrorEnabled(false);
                                } else if (Offline_DataCard_Fragment.this.amountlayout.isErrorEnabled()) {
                                    Offline_DataCard_Fragment.this.amountlayout.setErrorEnabled(false);
                                }
                                Offline_DataCard_Fragment.this.edtAmount.setText("");
                                Offline_DataCard_Fragment.this.tv_operator.setText("");
                                Offline_DataCard_Fragment.this.operatorImageView.setImageResource(R.drawable.operator_status);
                                Offline_DataCard_Fragment.this.edtMobile.requestFocus();
                                if (!Offline_DataCard_Fragment.this.OperatorCode.equalsIgnoreCase("BT") && !Offline_DataCard_Fragment.this.OperatorCode.equalsIgnoreCase("DS") && !Offline_DataCard_Fragment.this.OperatorCode.equalsIgnoreCase("US") && !Offline_DataCard_Fragment.this.OperatorCode.equalsIgnoreCase("BR") && !Offline_DataCard_Fragment.this.OperatorCode.equalsIgnoreCase("D") && !Offline_DataCard_Fragment.this.OperatorCode.equalsIgnoreCase("U")) {
                                }
                            }
                        }).setNegativeButton("No", new C06204());
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
        this.lvSearchMobile.setOnItemClickListener(new C06226());
        right_arrow.setOnClickListener(new C06237());
        this.dialog.show();
    }
}
