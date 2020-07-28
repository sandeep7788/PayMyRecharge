package com.codeunite.paymyrch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import com.codeunite.paymyrch.AsyncTask.FundRequestHistoryTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.FundRequestHistoryAdapter;
import tr.xip.errorview.ErrorView;

public class FundRequestHistoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FundRequestHistoryAdapter adapter;
    ArrayAdapter<String> adapter_days;
    Context context;
    String daycode;
    ErrorView historyError;
    ExpandableListView listView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static FundRequestHistoryFragment newInstance(String param1, String param2) {
        FundRequestHistoryFragment fragment = new FundRequestHistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_fund_request_history, container, false);
        this.listView = (ExpandableListView) view.findViewById(R.id.listFundReceive);
        this.historyError = (ErrorView) view.findViewById(R.id.historyerror_view1);
        this.progressDialog = new ProgressDialog(getActivity());
        new FundRequestHistoryTask(getActivity(), this.progressDialog, this.adapter, this.listView, getActivity(), this.historyError).execute(new String[0]);
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
