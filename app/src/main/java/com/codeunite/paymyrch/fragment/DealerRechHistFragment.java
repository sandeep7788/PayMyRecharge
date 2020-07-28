package com.codeunite.paymyrch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.codeunite.paymyrch.AsyncTask.DealerRechHistTask;
import com.codeunite.paymyrch.AsyncTask.RetailerListTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.activity.ErrorActivity;
import com.codeunite.paymyrch.adapter.DealerExpandableListAdapter;
import com.codeunite.paymyrch.model.GroupReport;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import tr.xip.errorview.ErrorView;

public class DealerRechHistFragment extends Fragment implements OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String daycode;
    static String opcode;
    public static String[] retailer_name;
    static String statuscode;
    DealerExpandableListAdapter adapter;
    String[] all_spinnercode = new String[]{"ALL", "AC", "AT", "BR", "TD", "IC", "MT", "RG", "UN", "VF", "MTT", "VG", "VI", "JIO", "ATP", "IDP", "VFP", "RGP", "RCP", "BSP", "TDP", "TIP", "LMP", "AH", "DT", "RB", "SD", "TS", "VD", "BSL", "MTL", "ATL", "DOL", "MTZ", "MTW", "RN", "RNG", "RNC", "TPW", "TPP", "ILI", "TLI", "LP", "GMN", "GA", "GG", "GI", "MSEDC", "MKVE", "JOVVN", "JAVVN", "CSEB", "CESCWB", "BBE", "AVVN", "BE", "SBE", "NBE", "PSPC", "REE", "TDP", "BRP"};
    String[] all_spinnername = new String[]{"All", "Aircel", "Airtel", "Bsnl", "Docomo", "Idea", "Mts", "Reliance", "Telenor", "Vodafone", "Mtnl", "Virgin", "Videocon", "JIO", "Airtel Postpaid", "Idea Postpaid", "Vodafone Postpaid", "Reliance GSM Postpaid", "Reliance CDMA Postpaid", "Bsnl Postpaid", "Tata Docomo Postpaid", "Tata Indicom PostPaid", "Loop Mobile PostPaid", "Airtel Dth", "DishTv", "Big Tv", "SunTv", "TataSky", "Videocon Dth", "Bsnl Broadband", "MTNL Broadband", "Airtel Broadband", "Tata Docomo Broadband", "MTS Mblaze", "MTS Mbrowse", "Reliance NetConnect 1X", "Reliance NetConnect 3G", "Reliance NetConnect+", "Tata Photon Whiz", "Tata Photon+", "ICICI Prudential Insurance", "Tata AIA Insurance", "LIC Premium", "Mahanagar Gas", "Adani Gas", "Gujarat Gas", "Indraprastha Gas", "MSEDC - MAHARASHTRA", "Madhya Kshetra Vitaran - MADHYA PRADESH", "Jodhpur Vidyut Vitran Nigam - RAJASTHAN", "Jaipur Vidyut Vitran Nigam - RAJASTHAN", "CSEB - CHHATTISGARH", "CESC - WEST BENGAL", "BESCOM - BENGALURU", "Ajmer Vidyut Vitran Nigam - RAJASTHAN", "BEST Electricity", "South Bihar Electricity", "North Bihar Electricity", "PUNJAB STATE POWER CORPORATION LIMITED", "Reliance Energy Limited", "Tata Power Delhi Limited - Delhi", "BSES Rajdhani Power-DELHI"};
    Context context;
    String[] day_spinnercode = new String[]{"0", "1", "3", "7", "15"};
    String[] day_spinnername = new String[]{"TODAY", "LAST DAY", "LAST 3 DAYS", "LAST 1 WEEK", "LAST 15 DAYS"};
    ErrorView dealerHistoryErrorView;
    SparseArray<GroupReport> groups = new SparseArray();
    ErrorView historyError;
    ExpandableListView listView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    String re;
    public String[] retailer_Id;
    ArrayAdapter<String> retailerid;
    ArrayAdapter<String> retailername;
    String retid = "All";
    SearchableSpinner searchableSpinner;
    Spinner spOperator;
    Spinner sp_days;
    Spinner sp_status;
    String[] status_spinnercode = new String[]{"All", "Process", "Failed", "Success"};
    String[] status_spinnername = new String[]{"ALL ST", "PROCESS", "FAILED", "SUCCESS"};
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvBalance;
    TextView tvDate;
    TextView tvNewRemain;
    TextView tvOldRemain;
    TextView tvReceiveAmount;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DealerRechHistFragment$1 */
    class C05541 implements OnItemSelectedListener {
        C05541() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            String retailerData = RetailerListTask.retailer_id[position];
            if (position == 0) {
                DealerRechHistFragment.this.re = "All";
            }
            if (retailerData.length() >= 4) {
                String[] temp = retailerData.split(":");
                DealerRechHistFragment.this.re = temp[0];
                Log.d("DAY1", DealerRechHistFragment.this.re);
            }
            DealerRechHistFragment.this.history(DealerRechHistFragment.daycode, DealerRechHistFragment.opcode, DealerRechHistFragment.statuscode, DealerRechHistFragment.this.re);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DealerRechHistFragment$2 */
    class C05552 implements OnItemSelectedListener {
        C05552() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            DealerRechHistFragment.opcode = DealerRechHistFragment.this.all_spinnercode[arg2];
            Log.d("OPERATOR", DealerRechHistFragment.opcode);
            DealerRechHistFragment.this.history(DealerRechHistFragment.daycode, DealerRechHistFragment.opcode, DealerRechHistFragment.statuscode, DealerRechHistFragment.this.re);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DealerRechHistFragment$3 */
    class C05563 implements OnItemSelectedListener {
        C05563() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            DealerRechHistFragment.daycode = DealerRechHistFragment.this.day_spinnercode[arg2];
            Log.d("DAYY", DealerRechHistFragment.daycode);
            DealerRechHistFragment.this.history(DealerRechHistFragment.daycode, DealerRechHistFragment.opcode, DealerRechHistFragment.statuscode, DealerRechHistFragment.this.re);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DealerRechHistFragment$4 */
    class C05574 implements OnItemSelectedListener {
        C05574() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            DealerRechHistFragment.statuscode = DealerRechHistFragment.this.status_spinnercode[arg2];
            Log.d("STATUS", DealerRechHistFragment.statuscode);
            DealerRechHistFragment.this.history(DealerRechHistFragment.daycode, DealerRechHistFragment.opcode, DealerRechHistFragment.statuscode, DealerRechHistFragment.this.re);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static DealerRechHistFragment newInstance(String param1, String param2) {
        DealerRechHistFragment fragment = new DealerRechHistFragment();
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
        View view = inflater.inflate(R.layout.fragment_dealer_rech_hist, container, false);
        this.sp_days = (Spinner) view.findViewById(R.id.sp_days);
        this.spOperator = (Spinner) view.findViewById(R.id.sp_operator);
        this.sp_status = (Spinner) view.findViewById(R.id.sp_status);
        this.dealerHistoryErrorView = (ErrorView) view.findViewById(R.id.dealerHistory_error_view);
        this.listView = (ExpandableListView) view.findViewById(R.id.listviewnew);
        this.searchableSpinner = (SearchableSpinner) view.findViewById(R.id.member_auto);
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_dealer);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.tvBalance = (TextView) getActivity().findViewById(R.id.tvBalance);
        this.progressDialog = new ProgressDialog(getActivity());
        this.re = "All";
        ArrayAdapter<String> adapter_state = new ArrayAdapter(getActivity(), 17367048, this.day_spinnername);
        adapter_state.setDropDownViewResource(17367049);
        this.sp_days.setAdapter(adapter_state);
        ArrayAdapter<String> adapter_operator = new ArrayAdapter(getActivity(), 17367048, this.all_spinnername);
        adapter_operator.setDropDownViewResource(17367049);
        this.spOperator.setAdapter(adapter_operator);
        ArrayAdapter<String> adapter_status = new ArrayAdapter(getActivity(), 17367048, this.status_spinnername);
        adapter_status.setDropDownViewResource(17367049);
        opcode = this.all_spinnercode[0];
        daycode = this.day_spinnercode[0];
        statuscode = this.status_spinnercode[0];
        this.sp_status.setAdapter(adapter_status);
        new RetailerListTask(getActivity(), this.progressDialog, this.retailer_Id, this.retailerid, this.searchableSpinner).execute(new String[0]);
        history("0", "All", "All", "All");
        this.searchableSpinner.setOnItemSelectedListener(new C05541());
        this.spOperator.setOnItemSelectedListener(new C05552());
        this.sp_days.setOnItemSelectedListener(new C05563());
        this.sp_status.setOnItemSelectedListener(new C05574());
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

    void history(String currentDay, String currentOperator, String currentStatus, String currentRetailer) {
        new DealerRechHistTask(getActivity(), this.progressDialog, currentDay, currentOperator, currentStatus, currentRetailer, this.adapter, this.listView, getActivity(), this.dealerHistoryErrorView, this.swipeRefreshLayout, this.tvBalance).execute(new String[0]);
    }

    public void onRefresh() {
        if (isNetworkAvailable()) {
            new DealerRechHistTask(getActivity(), this.progressDialog, daycode, opcode, statuscode, this.re, this.adapter, this.listView, getActivity(), this.dealerHistoryErrorView, this.swipeRefreshLayout, this.tvBalance).execute(new String[0]);
        } else {
            startActivity(new Intent(getContext(), ErrorActivity.class));
        }
    }

    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) getActivity().getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }
}
