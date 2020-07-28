package com.codeunite.paymyrch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.codeunite.paymyrch.AsyncTask.FundReceiveTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.FundReceiveExpandableListAdapter;
import tr.xip.errorview.ErrorView;

public class FundReceiveFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FundReceiveExpandableListAdapter adapter;
    ArrayAdapter<String> adapter_days;
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
    Spinner spDays;
    TextView tvDate;
    TextView tvNewRemain;
    TextView tvOldRemain;
    TextView tvReceiveAmount;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.FundReceiveFragment$1 */
    class C05741 implements OnItemSelectedListener {
        C05741() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            FundReceiveFragment.this.daycode = FundReceiveFragment.this.day_spinnercode[position];
            new FundReceiveTask(FundReceiveFragment.this.getActivity(), FundReceiveFragment.this.progressDialog, FundReceiveFragment.this.daycode, FundReceiveFragment.this.adapter, FundReceiveFragment.this.listView, FundReceiveFragment.this.getActivity(), FundReceiveFragment.this.historyError).execute(new String[0]);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static FundReceiveFragment newInstance(String param1, String param2) {
        FundReceiveFragment fragment = new FundReceiveFragment();
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
        View view = inflater.inflate(R.layout.fragment_fund_receive, container, false);
        this.spDays = (Spinner) view.findViewById(R.id.spDays);
        this.listView = (ExpandableListView) view.findViewById(R.id.listFundReceive);
        this.historyError = (ErrorView) view.findViewById(R.id.historyerror_view1);
        this.progressDialog = new ProgressDialog(getActivity());
        this.adapter_days = new ArrayAdapter(getActivity(), 17367048, this.day_spinnername);
        this.adapter_days.setDropDownViewResource(17367049);
        this.spDays.setAdapter(this.adapter_days);
        this.daycode = this.day_spinnercode[0];
        this.spDays.setOnItemSelectedListener(new C05741());
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
}
