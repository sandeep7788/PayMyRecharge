package com.codeunite.paymyrch.fragment;

import android.annotation.SuppressLint;
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
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.codeunite.paymyrch.AsyncTask.BalanceTask;
import com.codeunite.paymyrch.AsyncTask.ElectricityBillTask;
import com.codeunite.paymyrch.AsyncTask.GetBillTask;
import com.codeunite.paymyrch.AsyncTask.OperatorTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.OfflineImageSpinnerAdapter;
import com.codeunite.paymyrch.utils.CommonUtils;

public class ElectricityFragment extends Fragment implements OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] state_id;
    public static String[] state_name;
    String OperatorCode;
    Button getbill;

    EditText mobileNumber;
    RelativeLayout billlayout;
    String OperatorName;
    int OperatorPosition;
    private final int PICK_CONTACT_REQUEST = 1;
  //  String[] SPINNERCODES = new String[]{"MSEDC", "MKVE", "JOVVN", "JAVVN", "CSEB", "CESCWB", "BBE", "AVVN", "BE", "SBE", "NBE", "PSPC", "REE", "TDP", "BRP", "TCE"};
   // String[] SPINNERVALUES = new String[]{"MSEDC - MAHARASHTRA", "Madhya Kshetra Vitaran - MADHYA PRADESH", "Jodhpur Vidyut Vitran Nigam - RAJASTHAN", "Jaipur Vidyut Vitran Nigam - RAJASTHAN", "CSEB - CHHATTISGARH", "CESC - WEST BENGAL", "BESCOM - BENGALURU", "Ajmer Vidyut Vitran Nigam - RAJASTHAN", "BEST Electricity", "South Bihar Electricity", "North Bihar Electricity", "PUNJAB STATE POWER CORPORATION LIMITED", "Reliance Energy Limited", "Tata Power Delhi Limited - Delhi", "BSES Rajdhani Power-DELHI", "Telangana Electricity Bill"};

    String [] SPINNERVALUES= new String[]{"Ajmer Vidyut Vitran Nigam - Rajasthan",
            "APDCL - ASSAM",
            "APEPDCL - ANDHRA PRADESH",
            "APSPDCL - ANDHRA PRADESH",
            "BESCOM - BENGALURU",
            "BESL - BHARATPUR",
            "BEST Undertaking - MUMBAI",
            "BKESL - BIKANER",
            "BSES Rajdhani - DELHI",
            "BSES Yamuna - DELHI",
            "CESC - WEST BENGAL",
            "CESCOM - KARNATAKA",
            "CSPDCL - CHHATTISGARH",
            "Daman and Diu Electricity",
            "DGVCL - GUJARAT",
            "DHBVN - HARYANA",
            "DNHPDCL - DADRA & NAGAR HAVELI",
            "GESCOM - KARNATAKA",
            "HESCOM - KARNATAKA",
            "Himachal Pradesh State Electricity Board",
            "India Power - BIHAR",
            "Jaipur Vidyut Vitran Nigam - RAJASTHAN",
            "JBVNL - JHARKHAND",
            "Jodhpur Vidyut Vitran Nigam - RAJASTHAN",
            "JUSCO - JAMSHEDPUR",
            "Kota Electricity Distribution - RAJASTHAN",
            "Madhya Kshetra Vitaran - MADHYA PRADESH",
            "Madhya Kshetra Vitaran - MADHYA PRADESH",
            "MEPDCL - MEGHALAYA",
            "MGVCL - GUJARAT",
            "MSEDC - MAHARASHTRA",
            "Muzaffarpur Vidyut Vitran",
            "NBPDCL - BIHAR",
            "NESCO - ODISHA",
            "Noida Power - NOIDA",
            "Paschim Kshetra Vitaran - MADHYA PRADESH",
            "PGVCL - GUJARAT",
            "PSPCL - PUNJAB",
            "Reliance Energy",
            "SBPDCL - BIHAR",
            "SNDL Power - NAGPUR",
            "SOUTHCO - ODISHA",
            "Tata Power - DELHI",
            "Tata Power - MUMBAI",
            "TNEB - TAMIL NADU",
            "Torrent Power",
            "TPADL - AJMER",
            "TSECL - TRIPURA",
            "UGVCL - GUJARAT",
            "UHBVN - HARYANA",
            "UPCL - UTTARAKHAND",
            "UPPCL (RURAL) - UTTAR PRADESH",
            "UPPCL (URBAN) - UTTAR PRADESH",
            "WBSEDCL - WEST BENGAL",
            "WESCO - ODISHA",
    };
    String [] SPINNERCODES= new String[]{"ARE",
            "AAE",
            "AEE",
            "ASE",
            "BBE",
            "BPE",
            "BME",
            "BKE",
            "BRE",
            "BYE",
            "CWE",
            "CKE",
            "CCE",
            "DDE",
            "DGE",
            "DHE",
            "DNE",
            "GKE",
            "HKE",
            "HPE",
            "IBE",
            "IPE",
            "JRE",
            "JBE",
            "DRE",
            "JUE",
            "KRE",
            "MME",
            "MMEE",
            "MEE",
            "MGE",
            "MDE",
            "MBE",
            "NBE",
            "NOE",
            "NUE",
            "MPE",
            "PGE",
            "PPE",
            "REE",
            "SBE",
            "SNE",
            "SOE",
            "NDE",
            "TME",
            "TNE",
            "TPE",
            "AJE",
            "TTE",
            "UGE",
            "UHE",
            "UKE",
            "URE",
            "UPE",
            "WWE",
            "WOE",
    };
    int[] electricityImageId = new int[]{
            R.drawable.avvn,
            R.drawable.apdcl,
            R.drawable.apepdcl,
            R.drawable.apspdcl,
            R.drawable.bangalore_electricity_supply_company,
            R.drawable.besl,
            R.drawable.best,
            R.drawable.besl,
            R.drawable.bese1,
            R.drawable.bese1,
            R.drawable.besl,
            R.drawable.cescom1,
            R.drawable.cspdcl,
            R.drawable.dde,
            R.drawable.dgvcl,
            R.drawable.dakshin_haryana_bijli_vitran_nigam,
            R.drawable.dnhpdcll,
            R.drawable.gescom,
            R.drawable.hescom,
            R.drawable.hpe,
            R.drawable.india_power_corporation_limited,
            R.drawable.jaipur,
            R.drawable.jbe,
            R.drawable.jodhpur_vidyut_vitran_nigam_limited,
            R.drawable.jusco,
            R.drawable.besl,
            R.drawable.madhya_pradesh_madhya_kshetra_vidyut_vitaran_company_limited_bhopal,
            R.drawable.madhya_pradesh_madhya_kshetra_vidyut_vitaran_company_limited_bhopal,
            R.drawable.mepdcl,
            R.drawable.mgvcl,
            R.drawable.msedc,
            R.drawable.muza,
            R.drawable.nbpdcll,
            R.drawable.nesco1,
            R.drawable.noida,
            R.drawable.madhya_pradesh_paschim_kshetra_vidyut_vitaran,
            R.drawable.pgvcl,
            R.drawable.pspcl,
            R.drawable.reliance_energy,
            R.drawable.sbpdcll,
            R.drawable.sndl1,
            R.drawable.southco1,
            R.drawable.tata,
            R.drawable.tata_power_ddl,
            R.drawable.tneb,
            R.drawable.torrent_power,
            R.drawable.tpadl,
            R.drawable.tripuraelectricity,
            R.drawable.ugvcl,
            R.drawable.uhbvn,
            R.drawable.uttarakhandpower,
            R.drawable.uppcl,
            R.drawable.uppcl1,
            R.drawable.wbsedcl,
            R.drawable.wesco
    };
    TextInputLayout amountlayout;
    public Button btnRecharge,bill;
    View change_Operator;
    Context context;
    Dialog dialog;
    public EditText edtAmount;
    public EditText edtMobile;
   // int[] electricityImageId = new int[]{R.drawable.msede_limited, R.drawable.madhya_pradesh_madhya_kshetra_vidyut_vitaran_company_limited_bhopal, R.drawable.jodhpur_vidyut_vitran_nigam_limited, R.drawable.jaipur, R.drawable.cseb, R.drawable.cesc, R.drawable.bangalore_electricity_supply_company, R.drawable.ajmer_vidyut_vitran_nigam_limited, R.drawable.brihan_mumbai_electric_supply_and_transport_undertaking, R.drawable.south_bihar, R.drawable.north_bihar, R.drawable.panjab, R.drawable.reliance_energy, R.drawable.tata_power_ddl, R.drawable.bses_rajdhani_power_limited, R.drawable.telangana};
    ImageView ivPhoneBook;
    ListView lvSearchMobile;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    TextInputLayout moblayout,moblayout1;
    LinearLayout op;
    TextView op_change;
    Bundle opdetails;
    ImageView operatorImageView;
    ProgressDialog progressDialog;
    TextView selected_op;
    ArrayAdapter<String> stateName;
    TextView tvBalance;
    TextView tv_operator;
    TextView date,customer_name,due_amount,due_date,reference_id;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.ElectricityFragment$1 */
    class C05671 implements OnClickListener {
        C05671() {
        }

        public void onClick(View v) {
            ElectricityFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.ElectricityFragment$2 */
    class C05682 implements OnClickListener {
        C05682() {
        }

        public void onClick(View v) {
            ElectricityFragment.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.ElectricityFragment$3 */
    class C05693 implements TextWatcher {
        C05693() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (ElectricityFragment.this.edtAmount.getText().toString().matches("^0")) {
                ElectricityFragment.this.edtAmount.setText("");
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.ElectricityFragment$4 */
    class C05704 implements DialogInterface.OnClickListener {
        C05704() {
        }

        public void onClick(DialogInterface dialog, int id) {
            if (ElectricityFragment.this.moblayout.isErrorEnabled()) {
                ElectricityFragment.this.moblayout.setErrorEnabled(false);
            } else if (ElectricityFragment.this.amountlayout.isErrorEnabled()) {
                ElectricityFragment.this.amountlayout.setErrorEnabled(false);
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.ElectricityFragment$6 */
    class C05726 implements OnItemClickListener {
        C05726() {



        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ElectricityFragment.this.OperatorName = ElectricityFragment.this.SPINNERVALUES[i];
            ElectricityFragment.this.OperatorCode = ElectricityFragment.this.SPINNERCODES[i];
            ElectricityFragment.this.operatorImageView.setImageResource(ElectricityFragment.this.electricityImageId[i]);
            ElectricityFragment.this.selected_op.setText(ElectricityFragment.this.OperatorName);
            ElectricityFragment.this.tv_operator.setText(ElectricityFragment.this.OperatorName);
            ElectricityFragment.this.dialog.dismiss();
            //Log.d("username",CommonUtils.userName);
            //Log.d("op code",ElectricityFragment.this.OperatorCode);





            new OperatorTask(ElectricityFragment.this.context, edtMobile, CommonUtils.userName, ElectricityFragment.this.tvBalance, ElectricityFragment.this.OperatorCode, ElectricityFragment.this.progressDialog).execute(new String[0]);
            {

            }


        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.ElectricityFragment$7 */
    class C05737 implements OnClickListener {
        C05737() {
        }

        public void onClick(View view) {
            ElectricityFragment.this.dialog.dismiss();
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

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electricity, container, false);
        this.context = getActivity();
        this.progressDialog = new ProgressDialog(getActivity());
        this.moblayout = (TextInputLayout) view.findViewById(R.id.moblayout);
        this.amountlayout = (TextInputLayout) view.findViewById(R.id.amountlayout);
        this.amountlayout.setHint("Amount");
        this.op = (LinearLayout) view.findViewById(R.id.view_op);
        this.operatorImageView = (ImageView) view.findViewById(R.id.operatorimg);
        this.edtMobile = (EditText) view.findViewById(R.id.edtMobile);
        this.getbill=(Button)view.findViewById(R.id.bill);
        this.edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        this.ivPhoneBook = (ImageView) view.findViewById(R.id.ivPhoneBook);
        this.btnRecharge = (Button) view.findViewById(R.id.btnRecharge);
        this.mobileNumber = (EditText) view.findViewById(R.id.edtMobile1);
        this.tvBalance = (TextView) getActivity().findViewById(R.id.tvBalance);
        this.tv_operator = (TextView) view.findViewById(R.id.operator);
        this.billlayout=(RelativeLayout)view.findViewById(R.id.bill_layout);
        this.date = (TextView) view.findViewById(R.id.t1);
        this.customer_name = (TextView) view.findViewById(R.id.t2);
        this.due_amount = (TextView) view.findViewById(R.id.t3);
        this.due_date = (TextView) view.findViewById(R.id.t4);
        this.reference_id = (TextView) view.findViewById(R.id.t5);
        this.op.setOnClickListener(new C05671());
        this.tv_operator.setOnClickListener(new C05682());
        this.opdetails = getArguments();
        this.edtAmount.addTextChangedListener(new C05693());
        this.ivPhoneBook.setOnClickListener(this);
        this.btnRecharge.setOnClickListener(this);
        this.getbill.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobNo = edtMobile.getText().toString().trim();
                if (OperatorCode.equals("")) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.toast_warning);
                    CardView bb1= (CardView)dialog.findViewById(R.id.b1);
                    TextView t1= (TextView)dialog.findViewById(R.id.toast_text);

                    String msg="Please select any Operator";
                    t1.setText(msg);
                    bb1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }else if (mobNo.length() == 0) {
                    if (moblayout.isErrorEnabled()) {
                        String aa = edtMobile.getHint().toString();

                        String error = "Please enter your " + aa;

                        moblayout.setError(error);
                        edtMobile.requestFocus();
                    }

                } else if (mobileNumber.getText().toString().trim().length() == 0) {
                    if (moblayout1.isErrorEnabled()) {
                        String aa1=mobileNumber.getHint().toString();

                        String error="Please enter your "+aa1;

                        moblayout1.setError(error);
                        mobileNumber.requestFocus();
                       moblayout.setErrorEnabled(false);
                    }

                } else {

                    long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                    Log.d("number", String.valueOf(number));
                    String agentid=String.valueOf(number);
                    String customer_mobile = mobileNumber.getText().toString().trim();
                    String servicenumber = edtMobile.getText().toString().trim();
                    new GetBillTask(ElectricityFragment.this.context, CommonUtils.userName, ElectricityFragment.this.tvBalance,agentid, customer_mobile,servicenumber ,edtAmount,date,customer_name,due_amount,due_date,billlayout,reference_id, ElectricityFragment.this.OperatorCode, ElectricityFragment.this.progressDialog).execute(new String[0]);
                    new BalanceTask(context, tvBalance, progressDialog).execute(new String[0]);
                }
            }
        });

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
            case R.id.bill:

            case R.id.btnRecharge:
                new BalanceTask(this.context, this.tvBalance, this.progressDialog).execute(new String[0]);
                try {
                    String mobNo1 = this.edtMobile.getText().toString().trim();
                    String operator_ok1 = this.tv_operator.getText().toString().trim();
                    if (mobNo1.length() == 0) {
                        if (this.amountlayout.isErrorEnabled()) {
                            this.amountlayout.setErrorEnabled(false);
                        }
                        String aa=edtMobile.getHint().toString();

                        String error="Please enter your "+aa;
                        this.moblayout.setError(error);
                        this.edtMobile.requestFocus();
                        return;
                    } else if (this.edtAmount.getText().toString().trim().length() == 0) {
                        if (this.moblayout.isErrorEnabled()) {
                            this.moblayout.setErrorEnabled(false);
                        }
                        this.amountlayout.setError("Please Enter Amount");
                        this.edtAmount.requestFocus();
                        return;
                    } else if (operator_ok1.equals("")) {
                        new SweetAlertDialog(getContext(), 3).setContentText("Please select any Operator").show();
                        return;
                    } else {
                        if (this.amountlayout.isErrorEnabled()) {
                            this.amountlayout.setErrorEnabled(false);
                        }
                        final String k_customer_number = this.edtMobile.getText().toString().trim();
                        final String cnumber = this.mobileNumber.getText().toString().trim();
                        final String amount = this.edtAmount.getText().toString().trim();
                        final String reffrence_idd = this.reference_id.getText().toString().trim();
                        Builder alertDialogBuilder = new Builder(getActivity());
                        alertDialogBuilder.setTitle("Please Confirm");
                        alertDialogBuilder.setIcon(R.drawable.confirmm);
                        String ed=this.edtMobile.getHint().toString();
                        alertDialogBuilder.setMessage("Member Id: " + CommonUtils.userName +
                                "\n"+ed +":" + k_customer_number +
                                "\nOperator: " + this.OperatorName +
                                "\nAmount: " + amount+
                                "\nCustomer Mobile: " + cnumber+
                                "\nReference No.: " + reffrence_idd
                        ).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new ElectricityBillTask(ElectricityFragment.this.context, CommonUtils.userName, ElectricityFragment.this.tvBalance, k_customer_number,amount,cnumber,reffrence_idd, ElectricityFragment.this.OperatorCode, ElectricityFragment.this.progressDialog).execute(new String[0]);
                                ElectricityFragment.this.edtMobile.setText("");
                                if (ElectricityFragment.this.moblayout.isErrorEnabled()) {
                                    ElectricityFragment.this.moblayout.setErrorEnabled(false);
                                } else if (ElectricityFragment.this.amountlayout.isErrorEnabled()) {
                                    ElectricityFragment.this.amountlayout.setErrorEnabled(false);
                                }
                                ElectricityFragment.this.edtAmount.setText("");
                                ElectricityFragment.this.tv_operator.setText("");
                                ElectricityFragment.this.operatorImageView.setImageResource(R.drawable.operator_status);
                                ElectricityFragment.this.edtMobile.requestFocus();
                                if (!ElectricityFragment.this.OperatorCode.equalsIgnoreCase("BT") && !ElectricityFragment.this.OperatorCode.equalsIgnoreCase("DS") && !ElectricityFragment.this.OperatorCode.equalsIgnoreCase("US") && !ElectricityFragment.this.OperatorCode.equalsIgnoreCase("BR") && !ElectricityFragment.this.OperatorCode.equalsIgnoreCase("D") && !ElectricityFragment.this.OperatorCode.equalsIgnoreCase("U")) {
                                }
                            }
                        }).setNegativeButton("No", new C05704());
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
        this.lvSearchMobile.setAdapter(new OfflineImageSpinnerAdapter(getActivity(), this.SPINNERVALUES, this.SPINNERCODES, this.electricityImageId));
        this.lvSearchMobile.setOnItemClickListener(new C05726());
        right_arrow.setOnClickListener(new C05737());
        this.dialog.show();
        /*ElectricityFragment.this.edtMobile.setText("");
        if (ElectricityFragment.this.moblayout.isErrorEnabled()) {
            ElectricityFragment.this.moblayout.setErrorEnabled(false);
        } else if (ElectricityFragment.this.amountlayout.isErrorEnabled()) {
            ElectricityFragment.this.amountlayout.setErrorEnabled(false);
        }
        ElectricityFragment.this.edtAmount.setText("");
        ElectricityFragment.this.tv_operator.setText("");
        ElectricityFragment.this.operatorImageView.setImageResource(R.drawable.operator_status);
        ElectricityFragment.this.edtMobile.requestFocus();*/


    }
}
