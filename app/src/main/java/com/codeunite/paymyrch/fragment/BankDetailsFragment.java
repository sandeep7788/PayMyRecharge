package com.codeunite.paymyrch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.codeunite.paymyrch.R;

public class BankDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int[] imageId = new int[]{R.drawable.airtel_payment_bank, R.drawable.allahbad_bank, R.drawable.andra_bank, R.drawable.axis_bank, R.drawable.bandhan_bank, R.drawable.bank_of_baroda, R.drawable.bank_of_india, R.drawable.bank_of_maharastra, R.drawable.bmb_bank, R.drawable.canara_bank, R.drawable.catholic_syrian_bank, R.drawable.central_bank, R.drawable.citi_bank, R.drawable.city_union_bank, R.drawable.corporation_bank, R.drawable.dcb_bank, R.drawable.dena_bank, R.drawable.dhanlaxmi_bank, R.drawable.federal_bank, R.drawable.hdfc_bank, R.drawable.icici_bank, R.drawable.idbi_bank, R.drawable.idfc_bank, R.drawable.indian_bank, R.drawable.indian_overseas_bank, R.drawable.induslnd_bank, R.drawable.j_and_k_bank, R.drawable.karnataka_bank, R.drawable.kvg_bank, R.drawable.karur_vaysya_bank, R.drawable.kerala_gramin_bank, R.drawable.kotak_bank, R.drawable.lakshmi_vilas_bank, R.drawable.maharashtra_gramin_bank, R.drawable.oriental_bank, R.drawable.pragathi_krishna_gramin_bank, R.drawable.prathama_bank, R.drawable.punjab_and_sind_bank, R.drawable.pnbe_bank, R.drawable.rbl_bank, R.drawable.south_indian_bank, R.drawable.standred_charted_bank, R.drawable.sbbj_bank, R.drawable.sbh_bank, R.drawable.state_bank_of_india, R.drawable.state_bank_of_mysore, R.drawable.state_bank_of_patiala, R.drawable.syndicate_bank, R.drawable.tamilnad_mercantile_bank, R.drawable.nainital_bank, R.drawable.uco_bank, R.drawable.union_bank, R.drawable.untitled_bank_of_india, R.drawable.vijaya_bank, R.drawable.yes_bank, R.drawable.other_bank_account};
    String accountHolderName;
    String accountNumber;
    String accountType;
    String bankAddress;
    ImageView bankImageView;
    String bankName;
    private int bankimage;
    String branchName;
    Context context;
    String ifscCode;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    TextView tvAccountHolderName;
    TextView tvAccountNumber;
    TextView tvAccountType;
    TextView tvBankAddress;
    TextView tvBankName;
    TextView tvBranchName;
    TextView tvIfscCode;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static BankDetailsFragment newInstance(String param1, String param2) {
        BankDetailsFragment fragment = new BankDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_bank_details, container, false);
        this.bankImageView = (ImageView) view.findViewById(R.id.bank_img);
        this.tvBankName = (TextView) view.findViewById(R.id.bank_name);
        this.tvBranchName = (TextView) view.findViewById(R.id.branch_name);
        this.tvIfscCode = (TextView) view.findViewById(R.id.ifsc_code);
        this.tvAccountNumber = (TextView) view.findViewById(R.id.account_number);
        this.tvAccountType = (TextView) view.findViewById(R.id.account_type);
        this.tvAccountHolderName = (TextView) view.findViewById(R.id.account_holder_name);
        this.tvBankAddress = (TextView) view.findViewById(R.id.bank_address);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.bankName = bundle.getString("BankName");
            this.branchName = bundle.getString("BranchName");
            this.ifscCode = bundle.getString("IfscCode");
            this.accountNumber = bundle.getString("AccountNo");
            this.accountType = bundle.getString("AccountType");
            this.accountHolderName = bundle.getString("AccountHolderName");
            this.bankAddress = bundle.getString("BankAddress");
            Log.d("MYBANK", this.bankName + this.branchName + this.ifscCode + this.accountNumber);
        }
        setBankImage(this.bankName, this.bankImageView);
        this.tvBankName.setText(this.bankName);
        this.tvBranchName.setText(this.branchName);
        this.tvIfscCode.setText(this.ifscCode);
        this.tvAccountNumber.setText(this.accountNumber);
        this.tvAccountType.setText(this.accountType);
        this.tvAccountHolderName.setText(this.accountHolderName);
        this.tvBankAddress.setText(this.bankAddress);
        this.progressDialog = new ProgressDialog(getActivity());
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

    void setBankImage(String bankName, ImageView imageView) {
        if (bankName.equalsIgnoreCase("AIRTEL PAYMENTS BANK LIMITED")) {
            this.bankimage = imageId[0];
        } else if (bankName.equalsIgnoreCase("ALLAHABAD BANK")) {
            this.bankimage = imageId[1];
        } else if (bankName.equalsIgnoreCase("ANDHRA BANK")) {
            this.bankimage = imageId[2];
        } else if (bankName.equalsIgnoreCase("AXIS BANK")) {
            this.bankimage = imageId[3];
        } else if (bankName.equalsIgnoreCase("BANDHAN BANK LIMITED")) {
            this.bankimage = imageId[4];
        } else if (bankName.equalsIgnoreCase("BANK OF BARODA")) {
            this.bankimage = imageId[5];
        } else if (bankName.equalsIgnoreCase("BANK OF INDIA")) {
            this.bankimage = imageId[6];
        } else if (bankName.equalsIgnoreCase("BANK OF MAHARASHTRA")) {
            this.bankimage = imageId[7];
        } else if (bankName.equalsIgnoreCase("BHARATIYA MAHILA BANK LIMITED")) {
            this.bankimage = imageId[8];
        } else if (bankName.equalsIgnoreCase("CANARA BANK")) {
            this.bankimage = imageId[9];
        } else if (bankName.equalsIgnoreCase("CATHOLIC SYRIAN BANK LIMITED")) {
            this.bankimage = imageId[10];
        } else if (bankName.equalsIgnoreCase("CENTRAL BANK OF INDIA")) {
            this.bankimage = imageId[11];
        } else if (bankName.equalsIgnoreCase("CITI BANK")) {
            this.bankimage = imageId[12];
        } else if (bankName.equalsIgnoreCase("CITY UNION BANK LIMITED")) {
            this.bankimage = imageId[13];
        } else if (bankName.equalsIgnoreCase("CORPORATION BANK")) {
            this.bankimage = imageId[14];
        } else if (bankName.equalsIgnoreCase("DCB BANK LIMITED")) {
            this.bankimage = imageId[15];
        } else if (bankName.equalsIgnoreCase("DENA BANK")) {
            this.bankimage = imageId[16];
        } else if (bankName.equalsIgnoreCase("DHANALAKSHMI BANK")) {
            this.bankimage = imageId[17];
        } else if (bankName.equalsIgnoreCase("FEDERAL BANK")) {
            this.bankimage = imageId[18];
        } else if (bankName.equalsIgnoreCase("HDFC BANK")) {
            this.bankimage = imageId[19];
        } else if (bankName.equalsIgnoreCase("ICICI BANK LIMITED")) {
            this.bankimage = imageId[20];
        } else if (bankName.equalsIgnoreCase("IDBI BANK")) {
            this.bankimage = imageId[21];
        } else if (bankName.equalsIgnoreCase("IDFC BANK LIMITED")) {
            this.bankimage = imageId[22];
        } else if (bankName.equalsIgnoreCase("INDIAN BANK")) {
            this.bankimage = imageId[23];
        } else if (bankName.equalsIgnoreCase("INDIAN OVERSEAS BANK")) {
            this.bankimage = imageId[24];
        } else if (bankName.equalsIgnoreCase("INDUSIND BANK")) {
            this.bankimage = imageId[25];
        } else if (bankName.equalsIgnoreCase("JAMMU AND KASHMIR BANK LIMITED")) {
            this.bankimage = imageId[26];
        } else if (bankName.equalsIgnoreCase("KARNATAKA BANK LIMITED")) {
            this.bankimage = imageId[27];
        } else if (bankName.equalsIgnoreCase("KARNATAKA VIKAS GRAMEENA BANK")) {
            this.bankimage = imageId[28];
        } else if (bankName.equalsIgnoreCase("KARUR VYSYA BANK")) {
            this.bankimage = imageId[29];
        } else if (bankName.equalsIgnoreCase("KERALA GRAMIN BANK")) {
            this.bankimage = imageId[30];
        } else if (bankName.equalsIgnoreCase("KOTAK MAHINDRA BANK LIMITED")) {
            this.bankimage = imageId[31];
        } else if (bankName.equalsIgnoreCase("LAXMI VILAS BANK")) {
            this.bankimage = imageId[32];
        } else if (bankName.equalsIgnoreCase("Maharashtra Gramin Bank")) {
            this.bankimage = imageId[33];
        } else if (bankName.equalsIgnoreCase("ORIENTAL BANK OF COMMERCE")) {
            this.bankimage = imageId[34];
        } else if (bankName.equalsIgnoreCase("PRAGATHI KRISHNA GRAMIN BANK")) {
            this.bankimage = imageId[35];
        } else if (bankName.equalsIgnoreCase("PRATHAMA BANK")) {
            this.bankimage = imageId[36];
        } else if (bankName.equalsIgnoreCase("PUNJAB AND SIND BANK")) {
            this.bankimage = imageId[37];
        } else if (bankName.equalsIgnoreCase("PUNJAB NATIONAL BANK")) {
            this.bankimage = imageId[38];
        } else if (bankName.equalsIgnoreCase("RBL Bank Limited")) {
            this.bankimage = imageId[39];
        } else if (bankName.equalsIgnoreCase("SOUTH INDIAN BANK")) {
            this.bankimage = imageId[40];
        } else if (bankName.equalsIgnoreCase("STANDARD CHARTERED BANK")) {
            this.bankimage = imageId[41];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF BIKANER AND JAIPUR")) {
            this.bankimage = imageId[42];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF HYDERABAD")) {
            this.bankimage = imageId[43];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF INDIA")) {
            this.bankimage = imageId[44];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF MYSORE")) {
            this.bankimage = imageId[45];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF PATIALA")) {
            this.bankimage = imageId[46];
        } else if (bankName.equalsIgnoreCase("SYNDICATE BANK")) {
            this.bankimage = imageId[47];
        } else if (bankName.equalsIgnoreCase("TAMILNAD MERCANTILE BANK LIMITED")) {
            this.bankimage = imageId[48];
        } else if (bankName.equalsIgnoreCase("THE NAINITAL BANK LIMITED")) {
            this.bankimage = imageId[49];
        } else if (bankName.equalsIgnoreCase("UCO BANK")) {
            this.bankimage = imageId[50];
        } else if (bankName.equalsIgnoreCase("UNION BANK OF INDIA")) {
            this.bankimage = imageId[51];
        } else if (bankName.equalsIgnoreCase("UNITED BANK OF INDIA")) {
            this.bankimage = imageId[52];
        } else if (bankName.equalsIgnoreCase("VIJAYA BANK")) {
            this.bankimage = imageId[53];
        } else if (bankName.equalsIgnoreCase("YES BANK")) {
            this.bankimage = imageId[54];
        } else {
            this.bankimage = imageId[55];
        }
        imageView.setImageResource(this.bankimage);
    }
}
