package com.codeunite.paymyrch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.codeunite.paymyrch.AsyncTask.Dispute_Report_Task;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.activity.ErrorActivity;
import com.codeunite.paymyrch.adapter.Dispute_Report_Adapter;

public class Dispute_Report extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Dispute_Report_Adapter adapter;
    Context context;
    ExpandableListView listView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    String retailerId;
    String status;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static Dispute_Report newInstance(String param1, String param2) {
        Dispute_Report fragment = new Dispute_Report();
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
        View view = inflater.inflate(R.layout.fragment_dispute__report, container, false);
        this.listView = (ExpandableListView) view.findViewById(R.id.listFundReceive);
        this.progressDialog = new ProgressDialog(getActivity());
        if (isNetworkAvailable()) {
            new Dispute_Report_Task(getActivity(), this.progressDialog, this.adapter, this.listView, this).execute(new String[0]);
        } else {
            startActivity(new Intent(getContext(), ErrorActivity.class));
        }
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

    public void retailer() {
        if (isNetworkAvailable()) {
            new Dispute_Report_Task(getActivity(), this.progressDialog, this.adapter, this.listView, this).execute(new String[0]);
        } else {
            startActivity(new Intent(getContext(), ErrorActivity.class));
        }
    }

    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) getActivity().getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }
}
