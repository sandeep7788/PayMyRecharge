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
import android.widget.ExpandableListView;
import android.widget.Spinner;
import com.codeunite.paymyrch.AsyncTask.DealerMoneyTransferReportTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.DealerMoneyTransferReportAdapter;
import tr.xip.errorview.ErrorView;

public class DealerMoneyTransferReport extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DealerMoneyTransferReportAdapter adapter;
    ArrayAdapter<String> adapter_days;
    Context context;
    String[] day_spinnercode = new String[]{"0", "1", "3", "7", "15"};
    String[] day_spinnername = new String[]{"TODAY", "LAST DAY", "LAST 3 DAYS", "LAST 1 WEEK", "LAST 15 DAYS"};
    String daycode;
    ErrorView errorView;
    ExpandableListView listView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    Spinner spDays;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DealerMoneyTransferReport$1 */
    class C05531 implements OnItemSelectedListener {
        C05531() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            DealerMoneyTransferReport.this.daycode = DealerMoneyTransferReport.this.day_spinnercode[position];
            Log.d("DAY2", DealerMoneyTransferReport.this.daycode);
            new DealerMoneyTransferReportTask(DealerMoneyTransferReport.this.getActivity(), DealerMoneyTransferReport.this.progressDialog, DealerMoneyTransferReport.this.daycode, DealerMoneyTransferReport.this.adapter, DealerMoneyTransferReport.this.listView, DealerMoneyTransferReport.this.errorView).execute(new String[0]);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static DealerMoneyTransferReport newInstance(String param1, String param2) {
        DealerMoneyTransferReport fragment = new DealerMoneyTransferReport();
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
        View view = inflater.inflate(R.layout.fragment_dealer_money_transfer_report, container, false);
        this.listView = (ExpandableListView) view.findViewById(R.id.list_dealer_money_transfer_report);
        this.progressDialog = new ProgressDialog(getActivity());
        this.spDays = (Spinner) view.findViewById(R.id.spDays1);
        this.adapter_days = new ArrayAdapter(getActivity(), 17367048, this.day_spinnername);
        this.adapter_days.setDropDownViewResource(17367049);
        this.spDays.setAdapter(this.adapter_days);
        this.daycode = this.day_spinnercode[0];
        this.errorView = (ErrorView) view.findViewById(R.id.impshistoryerror_view);
        this.spDays.setOnItemSelectedListener(new C05531());
        return view;
    }

    public void onResume() {
        super.onResume();
        new DealerMoneyTransferReportTask(getActivity(), this.progressDialog, this.daycode, this.adapter, this.listView, this.errorView).execute(new String[0]);
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
