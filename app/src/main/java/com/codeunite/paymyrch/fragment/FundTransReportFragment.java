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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import com.codeunite.paymyrch.AsyncTask.FundTransReportTask;
import com.codeunite.paymyrch.AsyncTask.RetailerListTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.FundReceiveExpandableListAdapter;
import tr.xip.errorview.ErrorView;

public class FundTransReportFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] retailer_id;
    FundReceiveExpandableListAdapter adapter;
    ArrayAdapter<String> adapter_days;
    Button btnShow;
    Context context;
    String[] day_spinnercode = new String[]{"0", "1", "3", "7", "15"};
    String[] day_spinnername = new String[]{"TODAY", "LAST DAY", "LAST 3 DAYS", "LAST 1 WEEK", "LAST 15 DAYS"};
    String daycode;
    ErrorView historyError;
    ExpandableListView listView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    String re;
    ArrayAdapter<String> retailerid;
    Spinner spDays;
    Spinner spRetailer;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.FundTransReportFragment$1 */
    class C05771 implements OnItemSelectedListener {
        C05771() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            String retailerData = RetailerListTask.retailer_id[position];
            if (position == 0) {
                FundTransReportFragment.this.re = "All";
            }
            if (retailerData.length() >= 4) {
                String[] temp = retailerData.split(":");
                FundTransReportFragment.this.re = temp[0];
                Log.d("DAY1", FundTransReportFragment.this.re);
            }
            FundTransReportFragment.this.showRetailer(FundTransReportFragment.this.daycode, FundTransReportFragment.this.re);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.FundTransReportFragment$2 */
    class C05782 implements OnItemSelectedListener {
        C05782() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            FundTransReportFragment.this.daycode = FundTransReportFragment.this.day_spinnercode[position];
            Log.d("DAY2", FundTransReportFragment.this.daycode);
            FundTransReportFragment.this.showRetailer(FundTransReportFragment.this.daycode, FundTransReportFragment.this.re);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static FundTransReportFragment newInstance(String param1, String param2) {
        FundTransReportFragment fragment = new FundTransReportFragment();
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
        View view = inflater.inflate(R.layout.fragment_fund_trans_report, container, false);
        this.spDays = (Spinner) view.findViewById(R.id.spDays);
        this.spRetailer = (Spinner) view.findViewById(R.id.spRetailer);
        this.listView = (ExpandableListView) view.findViewById(R.id.listFundReceive);
        this.btnShow = (Button) view.findViewById(R.id.btnTransfer);
        this.historyError = (ErrorView) view.findViewById(R.id.historyerror_view4);
        this.progressDialog = new ProgressDialog(getActivity());
        this.adapter_days = new ArrayAdapter(getActivity(), 17367048, this.day_spinnername);
        this.adapter_days.setDropDownViewResource(17367049);
        this.spDays.setAdapter(this.adapter_days);
        this.daycode = this.day_spinnercode[0];
        this.re = "All";
        new RetailerListTask(getActivity(), this.progressDialog, retailer_id, this.retailerid, this.spRetailer).execute(new String[0]);
        Log.d("DAY3", String.valueOf(retailer_id));
        showRetailer("0", "All");
        this.spRetailer.setOnItemSelectedListener(new C05771());
        this.spDays.setOnItemSelectedListener(new C05782());
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

    void showRetailer(String currentRetailerDay, String currentRetailerCode) {
        new FundTransReportTask(getActivity(), this.progressDialog, currentRetailerDay, currentRetailerCode, this.adapter, this.listView, getActivity(), this.historyError).execute(new String[0]);
    }
}
