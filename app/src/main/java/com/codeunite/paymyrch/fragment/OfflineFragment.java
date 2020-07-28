package com.codeunite.paymyrch.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.OfflineCustomGrid;

public class OfflineFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;
    int currentPosition;
    Dialog dialog;
    GridView gridView;
    int[] imageId = new int[]{R.drawable.prepaid_grid_white, R.drawable.postpaid_grid_white, R.drawable.dth_grid_white, R.drawable.broadband_grid_white, R.drawable.datacard_grid_white, R.drawable.insurance_grid_white, R.drawable.gas_grid_white, R.drawable.electricity_grid_white, R.drawable.history_grid_white, R.drawable.history_grid_white, R.drawable.balance_grid_white, R.drawable.money_transfer_grid_white};
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    String[] titleGrid = new String[]{"PREPAID", "POSTPAID", "DTH", "LANDLINE", "DATACARD", "INSURANCE", "GAS", "ELECTRICITY", "TRANSACTION", "FUND TRANSFER", "MY BALANCE", "MONEY TRANSFER"};
    TextView txt_wlc;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.OfflineFragment$1 */
    class C06151 implements OnItemClickListener {
        C06151() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            FragmentManager fragmentManager = OfflineFragment.this.getFragmentManager();
            CustomGridOperator customGridOperator = new CustomGridOperator();
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    OfflineFragment.this.Dialog("PrePaid Recharge");
                    return;
                case 1:
                    OfflineFragment.this.Dialog("PostPaid Recharge");
                    return;
                case 2:
                    OfflineFragment.this.Dialog("DTH Recharge");
                    return;
                case 3:
                    OfflineFragment.this.Dialog("LandLine Recharge");
                    return;
                case 4:
                    OfflineFragment.this.Dialog("DataCard Recharge");
                    return;
                case 5:
                    OfflineFragment.this.Dialog("Insurance");
                    return;
                case 6:
                    OfflineFragment.this.Dialog("Gas");
                    return;
                case 7:
                    OfflineFragment.this.Dialog("Electricity");
                    return;
                case 8:
                    OfflineFragment.this.Dialog("Transcation History");
                    return;
                case 9:
                    OfflineFragment.this.Dialog("Fund Transfer");
                    return;
                case 10:
                    OfflineFragment.this.Dialog("My Balance");
                    return;
                case 11:
                    OfflineFragment.this.Dialog("Money Transfer");
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.OfflineFragment$2 */
    class C06162 implements OnClickListener {
        C06162() {
        }

        public void onClick(DialogInterface dialog, int id) {
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static OfflineFragment newInstance(String param1, String param2) {
        OfflineFragment fragment = new OfflineFragment();
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
        View view = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.fragment_offline, container, false);
        String tag = "PREPAID";
        this.context = getActivity();
        this.progressDialog = new ProgressDialog(getActivity());
        this.gridView = (GridView) view.findViewById(R.id.offLine_gridView);
        this.gridView.setAdapter(new OfflineCustomGrid(this.context, this.imageId, this.titleGrid));
        this.gridView.setOnItemClickListener(new C06151());
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

    public AlertDialog Dialog(String title) {
        Builder builder = new Builder(getActivity());
        builder.setTitle((CharSequence) title);
        builder.setMessage((CharSequence) "Comming soon...");
        builder.setIcon((int) R.drawable.pmr_logo_icon);
        builder.setPositiveButton((CharSequence) "Ok", new C06162());
        builder.create().show();
        return builder.create();
    }
}
