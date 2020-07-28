package com.codeunite.paymyrch.fragment;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.codeunite.paymyrch.AsyncTask.CreateRetailerTask;
import com.codeunite.paymyrch.AsyncTask.DistListTask;
import com.codeunite.paymyrch.AsyncTask.SlabTask;
import com.codeunite.paymyrch.AsyncTask.StateListTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.helper.ValidationChecker;

public class CreateRetailerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] retailer_id;
    public static String[] state_id;
    public static String[] state_name;
    Button btnCreateNew;
    Context context;
    ArrayAdapter<String> distName;
    String[] dist_id;
    String[] dist_name;
    EditText edtAddress;
    EditText edtEmail;
    EditText edtFirmName;
    EditText edtMobile;
    EditText edtPinCode;
    EditText edtRetailerName;
    EditText edtaadharNumber;
    EditText edtgstNumber;
    EditText edtpanNumber;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    String packageName;
    ProgressDialog progressDialog;
    ArrayAdapter<String> retailerid;
    SlabTask slabTask;
    String slabname;
    Spinner spDistrict;
    Spinner spSlab;
    Spinner spState;
    ArrayAdapter<String> stateName;
    String strAadharNumber;
    String strAddress;
    String strEmail;
    String strFirmName;
    String strGstNumber;
    String strMobile;
    String strPanCardNumber;
    String strPinCode;
    String strRetailerName;
    TextInputLayout tilaadharNumber;
    TextInputLayout tiladdress;
    TextInputLayout tilemail;
    TextInputLayout tilfirmName;
    TextInputLayout tilgstNumber;
    TextInputLayout tilmobileNumber;
    TextInputLayout tilpancardNumber;
    TextInputLayout tilpinCode;
    TextInputLayout tilretailerName;
    ValidationChecker validationChecker;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.CreateRetailerFragment$1 */
    class C05341 implements OnClickListener {

        /* renamed from: com.codeunite.PayMyRecharge.fragment.CreateRetailerFragment$1$1 */
        class C05321 implements DialogInterface.OnClickListener {
            C05321() {
            }

            public void onClick(DialogInterface dialog, int id) {
            }
        }

        /* renamed from: com.codeunite.PayMyRecharge.fragment.CreateRetailerFragment$1$2 */
        class C05332 implements DialogInterface.OnClickListener {
            C05332() {
            }

            public void onClick(DialogInterface dialog, int id) {
                new CreateRetailerTask(CreateRetailerFragment.this.getActivity(), CreateRetailerFragment.this.strRetailerName, CreateRetailerFragment.this.strFirmName, CreateRetailerFragment.this.strMobile, CreateRetailerFragment.this.strPinCode, CreateRetailerFragment.this.strEmail, CreateRetailerFragment.this.strAddress, StateListTask.stateCode, DistListTask.distCode, CreateRetailerFragment.this.slabTask.retailerCode, CreateRetailerFragment.this.strGstNumber, CreateRetailerFragment.this.strAadharNumber, CreateRetailerFragment.this.strPanCardNumber, CreateRetailerFragment.this.progressDialog).execute(new String[0]);
                CreateRetailerFragment.this.edtRetailerName.setText("");
                CreateRetailerFragment.this.edtFirmName.setText("");
                CreateRetailerFragment.this.edtMobile.setText("");
                CreateRetailerFragment.this.edtPinCode.setText("");
                CreateRetailerFragment.this.edtAddress.setText("");
                CreateRetailerFragment.this.edtEmail.setText("");
                CreateRetailerFragment.this.edtRetailerName.requestFocus();
            }
        }

        C05341() {
        }

        public void onClick(View v) {
            CreateRetailerFragment.this.strRetailerName = CreateRetailerFragment.this.edtRetailerName.getText().toString();
            CreateRetailerFragment.this.strFirmName = CreateRetailerFragment.this.edtFirmName.getText().toString();
            CreateRetailerFragment.this.strMobile = CreateRetailerFragment.this.edtMobile.getText().toString();
            CreateRetailerFragment.this.strPinCode = CreateRetailerFragment.this.edtPinCode.getText().toString();
            CreateRetailerFragment.this.strEmail = CreateRetailerFragment.this.edtEmail.getText().toString();
            CreateRetailerFragment.this.strAddress = CreateRetailerFragment.this.edtAddress.getText().toString();
            CreateRetailerFragment.this.strGstNumber = CreateRetailerFragment.this.edtgstNumber.getText().toString();
            CreateRetailerFragment.this.strAadharNumber = CreateRetailerFragment.this.edtaadharNumber.getText().toString();
            CreateRetailerFragment.this.strPanCardNumber = CreateRetailerFragment.this.edtpanNumber.getText().toString();
            if (CreateRetailerFragment.this.strRetailerName.equalsIgnoreCase("")) {
                CreateRetailerFragment.this.setError(CreateRetailerFragment.this.tilretailerName, "Please Enter Retailer Name");
                if (CreateRetailerFragment.this.tilfirmName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilfirmName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilmobileNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilmobileNumber.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilpinCode.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilpinCode.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tiladdress.isErrorEnabled()) {
                    CreateRetailerFragment.this.tiladdress.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilaadharNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilaadharNumber.setErrorEnabled(false);
                }
            } else if (CreateRetailerFragment.this.strFirmName.equalsIgnoreCase("")) {
                CreateRetailerFragment.this.setError(CreateRetailerFragment.this.tilfirmName, "Please Enter Firm Name");
                if (CreateRetailerFragment.this.tilretailerName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilretailerName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilmobileNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilmobileNumber.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilpinCode.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilpinCode.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tiladdress.isErrorEnabled()) {
                    CreateRetailerFragment.this.tiladdress.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilaadharNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilaadharNumber.setErrorEnabled(false);
                }
            } else if (CreateRetailerFragment.this.strMobile.equalsIgnoreCase("")) {
                CreateRetailerFragment.this.setError(CreateRetailerFragment.this.tilmobileNumber, "Please Enter Valid Mobile Number");
                if (CreateRetailerFragment.this.tilretailerName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilretailerName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilfirmName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilfirmName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilpinCode.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilpinCode.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tiladdress.isErrorEnabled()) {
                    CreateRetailerFragment.this.tiladdress.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilaadharNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilaadharNumber.setErrorEnabled(false);
                }
            } else if (CreateRetailerFragment.this.strPinCode.equalsIgnoreCase("")) {
                CreateRetailerFragment.this.setError(CreateRetailerFragment.this.tilpinCode, "Please Enter Pin Code");
                if (CreateRetailerFragment.this.tilretailerName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilretailerName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilfirmName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilfirmName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilmobileNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilmobileNumber.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tiladdress.isErrorEnabled()) {
                    CreateRetailerFragment.this.tiladdress.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilaadharNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilaadharNumber.setErrorEnabled(false);
                }
            } else if (CreateRetailerFragment.this.strAddress.equalsIgnoreCase("")) {
                CreateRetailerFragment.this.setError(CreateRetailerFragment.this.tiladdress, "Please Enter Your Address");
                if (CreateRetailerFragment.this.tilretailerName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilretailerName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilfirmName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilfirmName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilmobileNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilmobileNumber.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilpinCode.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilpinCode.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilaadharNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilaadharNumber.setErrorEnabled(false);
                }
            } else if (CreateRetailerFragment.this.strAadharNumber.equalsIgnoreCase("") || CreateRetailerFragment.this.strAadharNumber.length() < 12) {
                CreateRetailerFragment.this.setError(CreateRetailerFragment.this.tilaadharNumber, "Please Enter aadhar number");
                if (CreateRetailerFragment.this.tilretailerName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilretailerName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilfirmName.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilfirmName.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilmobileNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilmobileNumber.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tilpinCode.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilpinCode.setErrorEnabled(false);
                } else if (CreateRetailerFragment.this.tiladdress.isErrorEnabled()) {
                    CreateRetailerFragment.this.tiladdress.setErrorEnabled(false);
                }
            } else if (CreateRetailerFragment.this.slabTask.retailerCode.equalsIgnoreCase("Select Package")) {
                new SweetAlertDialog(CreateRetailerFragment.this.getContext(), 1).setTitleText("Oops...").setContentText("Select Package Name.").show();
                CreateRetailerFragment.this.tilretailerName.setErrorEnabled(false);
                CreateRetailerFragment.this.tilmobileNumber.setErrorEnabled(false);
                CreateRetailerFragment.this.tilfirmName.setErrorEnabled(false);
                CreateRetailerFragment.this.tilpinCode.setErrorEnabled(false);
                CreateRetailerFragment.this.tiladdress.setErrorEnabled(false);
                CreateRetailerFragment.this.tilgstNumber.setErrorEnabled(false);
                CreateRetailerFragment.this.tilaadharNumber.setErrorEnabled(false);
                CreateRetailerFragment.this.tilpancardNumber.setErrorEnabled(false);
            } else if (CreateRetailerFragment.this.validationChecker.isValidNumber(CreateRetailerFragment.this.strMobile).booleanValue()) {
                if (CreateRetailerFragment.this.tilmobileNumber.isErrorEnabled()) {
                    CreateRetailerFragment.this.tilmobileNumber.setErrorEnabled(false);
                }
                CreateRetailerFragment.this.tilretailerName.setErrorEnabled(false);
                CreateRetailerFragment.this.tilfirmName.setErrorEnabled(false);
                CreateRetailerFragment.this.tilmobileNumber.setErrorEnabled(false);
                CreateRetailerFragment.this.tilpinCode.setErrorEnabled(false);
                CreateRetailerFragment.this.tiladdress.setErrorEnabled(false);
                Builder alertDialogBuilder = new Builder(CreateRetailerFragment.this.getActivity());
                alertDialogBuilder.setTitle("Please Confirm");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                Log.d("MYSLAB", CreateRetailerFragment.this.slabTask.retailerCode);
                alertDialogBuilder.setMessage("Do you want to create?").setCancelable(false).setNegativeButton("Yes", new C05332()).setPositiveButton("No", new C05321());
                alertDialogBuilder.create().show();
            } else {
                CreateRetailerFragment.this.setError(CreateRetailerFragment.this.tilmobileNumber, "Please enter your valid mobile number");
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static CreateRetailerFragment newInstance(String param1, String param2) {
        CreateRetailerFragment fragment = new CreateRetailerFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_new_retailer, container, false);
        this.spState = (Spinner) view.findViewById(R.id.spState);
        this.spDistrict = (Spinner) view.findViewById(R.id.spDistrict);
        this.spSlab = (Spinner) view.findViewById(R.id.spRetailer_slab);
        this.edtRetailerName = (EditText) view.findViewById(R.id.edtRetailerName);
        this.edtFirmName = (EditText) view.findViewById(R.id.edtFirmName);
        this.edtMobile = (EditText) view.findViewById(R.id.edtMobileNo);
        this.edtPinCode = (EditText) view.findViewById(R.id.edtPinCode);
        this.edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        this.edtAddress = (EditText) view.findViewById(R.id.edtAddress);
        this.edtgstNumber = (EditText) view.findViewById(R.id.edtgst);
        this.edtaadharNumber = (EditText) view.findViewById(R.id.edtaadhar);
        this.edtpanNumber = (EditText) view.findViewById(R.id.edtpan);
        this.tilretailerName = (TextInputLayout) view.findViewById(R.id.retailer_name_Wraper);
        this.tilfirmName = (TextInputLayout) view.findViewById(R.id.firm_name_Wraper);
        this.tilmobileNumber = (TextInputLayout) view.findViewById(R.id.mobile_number_Wraper);
        this.tilpinCode = (TextInputLayout) view.findViewById(R.id.pin_Wraper);
        this.tilemail = (TextInputLayout) view.findViewById(R.id.email_Wraper);
        this.tiladdress = (TextInputLayout) view.findViewById(R.id.address_Wraper);
        this.tilgstNumber = (TextInputLayout) view.findViewById(R.id.gst_Wraper);
        this.tilaadharNumber = (TextInputLayout) view.findViewById(R.id.aadhar_Wraper);
        this.tilpancardNumber = (TextInputLayout) view.findViewById(R.id.pan_Wraper);
        this.tilretailerName.setHint("Name");
        this.tilfirmName.setHint("Firm Name");
        this.tilmobileNumber.setHint("Mobile Number");
        this.tilpinCode.setHint("PIN Code");
        this.tilemail.setHint("Email - ID");
        this.tiladdress.setHint("Address");
        this.tilgstNumber.setHint("GST Number");
        this.tilaadharNumber.setHint("Aadhar Card Number");
        this.tilpancardNumber.setHint("Pan Card Number");
        this.validationChecker = new ValidationChecker();
        this.btnCreateNew = (Button) view.findViewById(R.id.btnCreateRetailer);
        this.progressDialog = new ProgressDialog(getActivity());
        new StateListTask(getActivity(), this.progressDialog, state_id, state_name, this.stateName, this.spState, this.dist_id, this.dist_name, this.distName, this.spDistrict).execute(new String[0]);
        this.slabTask = new SlabTask(getActivity(), this.progressDialog, this.retailerid, this.spSlab, this.tilretailerName);
        this.slabTask.execute(new String[0]);
        this.btnCreateNew.setOnClickListener(new C05341());
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

    void setError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
    }
}
