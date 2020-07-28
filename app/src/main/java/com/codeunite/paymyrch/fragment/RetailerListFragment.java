package com.codeunite.paymyrch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.codeunite.paymyrch.AsyncTask.RetailerListNewTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.RetailerExpandableListAdapter;

public class RetailerListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RetailerExpandableListAdapter adapter;
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

    public static RetailerListFragment newInstance(String param1, String param2) {
        RetailerListFragment fragment = new RetailerListFragment();
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
        View view = inflater.inflate(R.layout.fragment_retailer_list, container, false);
        this.listView = (ExpandableListView) view.findViewById(R.id.listFundReceive);
        this.progressDialog = new ProgressDialog(getActivity());

        new RetailerListNewTask(getActivity(), this.progressDialog, this.adapter, this.listView, this).execute(new String[0]);
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
        new RetailerListNewTask(getActivity(), this.progressDialog, this.adapter, this.listView, this).execute(new String[0]);
    }
}
