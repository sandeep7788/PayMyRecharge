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
import android.widget.Spinner;
import android.widget.TextView;
import com.codeunite.paymyrch.AsyncTask.IncomeTask;
import com.codeunite.paymyrch.R;

public class IncomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayAdapter<String> adapter_days;
    Context context;
    String[] day_spinnercode = new String[]{"0", "1", "3", "7", "15"};
    String[] day_spinnername = new String[]{"TODAY", "LAST DAY", "LAST 3 DAYS", "LAST 1 WEEK", "LAST 15 DAYS"};
    String daycode;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    Spinner spDays;
    TextView tvTotalFaild;
    TextView tvTotalIncome;
    TextView tvTotalPending;
    TextView tvTotalSuccess;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.IncomeFragment$1 */
    class C05931 implements OnItemSelectedListener {
        C05931() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            IncomeFragment.this.daycode = IncomeFragment.this.day_spinnercode[position];
            new IncomeTask(IncomeFragment.this.context, IncomeFragment.this.progressDialog, IncomeFragment.this.daycode, IncomeFragment.this.tvTotalSuccess, IncomeFragment.this.tvTotalFaild, IncomeFragment.this.tvTotalPending, IncomeFragment.this.tvTotalIncome).execute(new String[0]);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static IncomeFragment newInstance(String param1, String param2) {
        IncomeFragment fragment = new IncomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        this.progressDialog = new ProgressDialog(getActivity());
        this.spDays = (Spinner) view.findViewById(R.id.sp_days_income);
        this.tvTotalSuccess = (TextView) view.findViewById(R.id.total_success);
        this.tvTotalFaild = (TextView) view.findViewById(R.id.total_failed);
        this.tvTotalPending = (TextView) view.findViewById(R.id.total_pending);
        this.tvTotalIncome = (TextView) view.findViewById(R.id.total_income);
        this.adapter_days = new ArrayAdapter(getActivity(), 17367048, this.day_spinnername);
        this.adapter_days.setDropDownViewResource(17367049);
        this.spDays.setAdapter(this.adapter_days);
        this.daycode = this.day_spinnercode[0];
        this.spDays.setOnItemSelectedListener(new C05931());
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
