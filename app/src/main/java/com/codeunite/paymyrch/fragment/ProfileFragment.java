package com.codeunite.paymyrch.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.codeunite.paymyrch.AsyncTask.ProfileTask;
import com.codeunite.paymyrch.R;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    TextView tvAddress;
    TextView tvJoinDate;
    TextView tvMobile;
    TextView tvName;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.tvName = (TextView) view.findViewById(R.id.tvName);
        this.tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        this.tvMobile = (TextView) view.findViewById(R.id.tvMobile);
        this.tvJoinDate = (TextView) view.findViewById(R.id.tvJoinDate);
        this.tvName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.tvAddress.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.tvMobile.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.tvJoinDate.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontregular.ttf"));
        this.progressDialog = new ProgressDialog(getActivity());
        Activity activity = getActivity();
        new ProfileTask(getActivity(), this.progressDialog, this.tvName, this.tvAddress, this.tvMobile, this.tvJoinDate, null, null).execute(new String[0]);
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
