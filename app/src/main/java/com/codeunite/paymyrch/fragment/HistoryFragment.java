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
import com.codeunite.paymyrch.AsyncTask.HistoryTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.activity.ErrorActivity;
import com.codeunite.paymyrch.adapter.DealerExpandableListAdapter;
import com.codeunite.paymyrch.model.GroupReport;
import tr.xip.errorview.ErrorView;

public class HistoryFragment extends Fragment implements OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static String daycode;
    static String opcode;
    public static String[] retailer_name;
    static String statuscode;
    DealerExpandableListAdapter adapter;
    String[] all_spinnercode = new String[]{"All", "AC", "AT", "BR", "TD", "IC", "MT", "RG", "UN", "VF", "MTT", "VG", "VI", "JIO", "ATP", "IDP", "VFP", "RGP", "RCP", "BSP", "TDP", "TIP", "LMP", "AH", "DT", "RB", "SD", "TS", "VD", "BSL", "MTL", "ATL", "DOL", "MTZ", "MTW", "RN", "RNG", "RNC", "TPW", "TPP", "ILI", "TLI", "LP", "GMN", "GA", "GG", "GI", "MSEDC", "MKVE", "JOVVN", "JAVVN", "CSEB", "CESCWB", "BBE", "AVVN", "BE", "SBE", "NBE", "PSPC", "REE", "TDP", "BRP"};
    String[] all_spinnername = new String[]{"All", "Aircel", "Airtel", "Bsnl", "Docomo", "Idea", "Mts", "Reliance", "Telenor", "Vodafone", "Mtnl", "Virgin", "Videocon", "JIO", "Airtel Postpaid", "Idea Postpaid", "Vodafone Postpaid", "Reliance GSM Postpaid", "Reliance CDMA Postpaid", "Bsnl Postpaid", "Tata Docomo Postpaid", "Tata Indicom PostPaid", "Loop Mobile PostPaid", "Airtel Dth", "DishTv", "Big Tv", "SunTv", "TataSky", "Videocon Dth", "Bsnl Broadband", "MTNL Broadband", "Airtel Broadband", "Tata Docomo Broadband", "MTS Mblaze", "MTS Mbrowse", "Reliance NetConnect 1X", "Reliance NetConnect 3G", "Reliance NetConnect+", "Tata Photon Whiz", "Tata Photon+", "ICICI Prudential Insurance", "Tata AIA Insurance", "LIC Premium", "Mahanagar Gas", "Adani Gas", "Gujarat Gas", "Indraprastha Gas", "MSEDC - MAHARASHTRA", "Madhya Kshetra Vitaran - MADHYA PRADESH", "Jodhpur Vidyut Vitran Nigam - RAJASTHAN", "Jaipur Vidyut Vitran Nigam - RAJASTHAN", "CSEB - CHHATTISGARH", "CESC - WEST BENGAL", "BESCOM - BENGALURU", "Ajmer Vidyut Vitran Nigam - RAJASTHAN", "BEST Electricity", "South Bihar Electricity", "North Bihar Electricity", "PUNJAB STATE POWER CORPORATION LIMITED", "Reliance Energy Limited", "Tata Power Delhi Limited - Delhi", "BSES Rajdhani Power-DELHI"};
    Context context;
    String[] day_spinnercode = new String[]{"0", "1", "3", "7", "15"};
    String[] day_spinnername = new String[]{"TODAY", "LAST DAY", "LAST 3 DAYS", "LAST 1 WEEK", "LAST 15 DAYS"};
    SparseArray<GroupReport> groups = new SparseArray();
    ErrorView historyError;
    ExpandableListView listView;
    private DealerRechHistFragment.OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    String re;
    String[] retailer_id;
    ArrayAdapter<String> retailerid;
    ArrayAdapter<String> retailername;
    String retid = "All";
    Spinner spOperator;
    Spinner spRetailerId;
    Spinner sp_days;
    Spinner sp_status;
    String[] status_spinnercode = new String[]{"All", "Process", "Failed", "Success"};
    String[] status_spinnername = new String[]{"ALL ST", "PROCESS", "FAILED", "SUCCESS"};
    SwipeRefreshLayout swipeError;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvBalance;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.HistoryFragment$1 */
    class C05901 implements OnItemSelectedListener {
        C05901() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            HistoryFragment.opcode = HistoryFragment.this.all_spinnercode[arg2];
            Log.d("OPERATOR", HistoryFragment.opcode);
            new HistoryTask(HistoryFragment.this.context, HistoryFragment.this.getActivity(), HistoryFragment.daycode, HistoryFragment.opcode, HistoryFragment.statuscode, HistoryFragment.this.adapter, HistoryFragment.this.listView, HistoryFragment.this.progressDialog, HistoryFragment.this.historyError, HistoryFragment.this.swipeRefreshLayout, HistoryFragment.this.tvBalance).execute(new String[0]);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.HistoryFragment$2 */
    class C05912 implements OnItemSelectedListener {
        C05912() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            HistoryFragment.daycode = HistoryFragment.this.day_spinnercode[arg2];
            Log.d("DAYY", HistoryFragment.daycode);
            new HistoryTask(HistoryFragment.this.context, HistoryFragment.this.getActivity(), HistoryFragment.daycode, HistoryFragment.opcode, HistoryFragment.statuscode, HistoryFragment.this.adapter, HistoryFragment.this.listView, HistoryFragment.this.progressDialog, HistoryFragment.this.historyError, HistoryFragment.this.swipeRefreshLayout, HistoryFragment.this.tvBalance).execute(new String[0]);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.HistoryFragment$3 */
    class C05923 implements OnItemSelectedListener {
        C05923() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            HistoryFragment.statuscode = HistoryFragment.this.status_spinnercode[arg2];
            Log.d("STATUS", HistoryFragment.statuscode);
            new HistoryTask(HistoryFragment.this.context, HistoryFragment.this.getActivity(), HistoryFragment.daycode, HistoryFragment.opcode, HistoryFragment.statuscode, HistoryFragment.this.adapter, HistoryFragment.this.listView, HistoryFragment.this.progressDialog, HistoryFragment.this.historyError, HistoryFragment.this.swipeRefreshLayout, HistoryFragment.this.tvBalance).execute(new String[0]);
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        this.sp_days = (Spinner) view.findViewById(R.id.spDay1);
        this.spOperator = (Spinner) view.findViewById(R.id.spOperator1);
        this.sp_status = (Spinner) view.findViewById(R.id.spStatus1);
        this.historyError = (ErrorView) view.findViewById(R.id.historyerror_view);
        this.listView = (ExpandableListView) view.findViewById(R.id.listviewnew1);
        this.progressDialog = new ProgressDialog(getContext());
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.tvBalance = (TextView) getActivity().findViewById(R.id.tvBalance);
        ArrayAdapter<String> adapter_state = new ArrayAdapter(getActivity(), 17367048, this.day_spinnername);
        adapter_state.setDropDownViewResource(17367049);
        this.sp_days.setAdapter(adapter_state);
        ArrayAdapter<String> adapter_operator = new ArrayAdapter(getActivity(), 17367048, this.all_spinnername);
        adapter_operator.setDropDownViewResource(17367049);
        this.spOperator.setAdapter(adapter_operator);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getActivity(), 17367048, this.status_spinnername);
        arrayAdapter.setDropDownViewResource(17367049);
        opcode = this.all_spinnercode[0];
        daycode = this.day_spinnercode[0];
        statuscode = this.status_spinnercode[0];
        this.sp_status.setAdapter(arrayAdapter);
        this.spOperator.setOnItemSelectedListener(new C05901());
        this.sp_days.setOnItemSelectedListener(new C05912());
        this.sp_status.setOnItemSelectedListener(new C05923());
        new HistoryTask(this.context, getActivity(), "0", "All", "All", this.adapter, this.listView, this.progressDialog, this.historyError, this.swipeRefreshLayout, this.tvBalance).execute(new String[0]);
        return view;
    }

    public void onResume() {
        super.onResume();
        Log.d("HISTORY RESUME", "onResume: ");
        new HistoryTask(this.context, getActivity(), "0", "All", "All", this.adapter, this.listView, this.progressDialog, this.historyError, this.swipeRefreshLayout, this.tvBalance).execute(new String[0]);
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

    public void showDealerRechHistory() {
        new HistoryTask(this.context, getActivity(), daycode, opcode, statuscode, this.adapter, this.listView, this.progressDialog, this.historyError, this.swipeRefreshLayout, this.tvBalance).execute(new String[0]);
    }

    public void onRefresh() {
        if (!isNetworkAvailable()) {
            startActivity(new Intent(getContext(), ErrorActivity.class));
        } else if (this.historyError.getVisibility() == 0) {
            new HistoryTask(this.context, getActivity(), daycode, opcode, statuscode, this.adapter, this.listView, null, this.historyError, this.swipeError, this.tvBalance).execute(new String[0]);
        } else {
            new HistoryTask(this.context, getActivity(), daycode, opcode, statuscode, this.adapter, this.listView, null, this.historyError, this.swipeRefreshLayout, this.tvBalance).execute(new String[0]);
        }
    }

    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) getActivity().getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }
}
