package com.codeunite.paymyrch.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.activity.AfterLoginActivity;
import com.codeunite.paymyrch.adapter.AdminCustomGrid;
import com.codeunite.paymyrch.adapter.CustomGrid;
import com.codeunite.paymyrch.utils.CommonUtils;

public class Swipe extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int[] admin_imageId = new int[]{R.drawable.api_history_grid_online, R.drawable.change_api_grid_online, R.drawable.history_admin_grid_online, R.drawable.pending_grid_online, R.drawable.fund_trabsfer_admin_grid_online, R.drawable.logout_grid_online};
    String[] admin_titleGrid = new String[]{"API HISTORY", "CHANGE API", "MY HISTORY", "MY PENDING", "FUND TRANSFER", "LOGOUT"};
    Context context;
    int currentPosition;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    GridView gridView;
    GridView gridViewAdmin;
        int[] imageId = new int[]{R.drawable.prepaid_grid_online, R.drawable.postpaid_grid_online, R.drawable.dth_grid_online, R.drawable.landline_grid_online, R.drawable.datacard_grid_online, R.drawable.insurance_grid_online, R.drawable.gas_grid_online, R.drawable.electricity_grid_online, R.drawable.fund_receive_grid_online, R.drawable.money_transfer_online, R.drawable.money_transfer_grid_online, R.drawable.history_grid_online};
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    int[] re_imageId = new int[]{R.drawable.prepaid_grid_online, R.drawable.postpaid_grid_online, R.drawable.dth_grid_online, R.drawable.landline_grid_online, R.drawable.datacard_grid_online, R.drawable.insurance_grid_online, R.drawable.gas_grid_online, R.drawable.electricity_grid_online, R.drawable.fund_receive_grid_online, R.drawable.money_transfer_online, R.drawable.fund_request_grid_pnline, R.drawable.history_grid_online};
    String[] re_titleGrid = new String[]{"PREPAID", "POSTPAID", "DTH", "LANDLINE", "DATACARD", "INSURANCE", "GAS", "ELECTRICITY", "FUND RECEIVE", "MONEY TRANSFER", "FUND REQUEST", "RECHARGE HISTORY"};
    String role;
    TabLayout tabLayout;
    String tag = "PREPAID";
    String[] titleGrid = new String[]{"PREPAID", "POSTPAID", "DTH", "LANDLINE", "DATACARD", "INSURANCE", "GAS", "ELECTRICITY", "FUND RECEIVE", "MONEY TRANSFER", "FUND TRANSFER", "RECHARGE HISTORY"};

    /* renamed from: com.codeunite.PayMyRecharge.fragment.Swipe$1 */
    class C06891 implements OnItemClickListener {
        C06891() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            FragmentManager fragmentManager = Swipe.this.getFragmentManager();
            FragmentTransaction fragmentTransaction;
            switch (position) {
                case 0:
                    PrepaidFragment prepaidFragment = new PrepaidFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, prepaidFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("PREPAID");
                    fragmentTransaction.commit();
                    return;
                case 1:
                    PostpaidFragment postpaidFragment = new PostpaidFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, postpaidFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("PREPAID");
                    fragmentTransaction.commit();
                    return;
                case 2:
                    DTHFragment dthFragment = new DTHFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, dthFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 3:
                    LandLineFragment landLineFragment = new LandLineFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, landLineFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 4:
                    DataCardFragment dataCardFragment = new DataCardFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, dataCardFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 5:
                    InsuranceFragment insuranceFragment = new InsuranceFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, insuranceFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 6:
                    GasFragment gasFragment = new GasFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, gasFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 7:
                    ElectricityFragment electricityFragment = new ElectricityFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, electricityFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 8:
                    FundReceiveFragment fundReceiveFragment = new FundReceiveFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, fundReceiveFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 9:
                /*    new SweetAlertDialog(Swipe.this.context, 0).setTitleText("Money Transfer").setContentText("Comming soon...").show();
                    return;*/
                    final Dialog dialog = new Dialog(Swipe.this.context);
                    dialog.setContentView(R.layout.toast_warning);
                    CardView bb1= (CardView)dialog.findViewById(R.id.b1);
                    TextView t1= (TextView)dialog.findViewById(R.id.toast_text);
                    TextView t01= (TextView)dialog.findViewById(R.id.title);
                    ImageView imageView=(ImageView)dialog.findViewById(R.id.im);
                    imageView.setImageResource(R.drawable.info);
                    String warning="Money Transfer";
                    t01.setText(warning);
                    String message="Coming Soon...";
                    t1.setText(message);
                    bb1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                    return;
                case 10:
                    FundRequestFragment fundRequestFragment1 = new FundRequestFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, fundRequestFragment1, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 11:
                    HistoryFragment historyFragment1 = new HistoryFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, historyFragment1, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.Swipe$2 */
    class C06902 implements OnItemClickListener {
        C06902() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            FragmentManager fragmentManager = Swipe.this.getFragmentManager();
            FragmentTransaction fragmentTransaction;
            switch (position) {
                case 0:
                    PrepaidFragment prepaidFragment = new PrepaidFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, prepaidFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("PREPAID");
                    fragmentTransaction.commit();
                    return;
                case 1:
                    PostpaidFragment postpaidFragment = new PostpaidFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, postpaidFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("PREPAID");
                    fragmentTransaction.commit();
                    return;
                case 2:
                    DTHFragment dthFragment = new DTHFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, dthFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 3:
                    LandLineFragment landLineFragment = new LandLineFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, landLineFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 4:
                    DataCardFragment dataCardFragment = new DataCardFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, dataCardFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 5:
                    InsuranceFragment insuranceFragment = new InsuranceFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, insuranceFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 6:
                    GasFragment gasFragment = new GasFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, gasFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 7:
                    ElectricityFragment electricityFragment = new ElectricityFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, electricityFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 8:
                    FundReceiveFragment fundReceiveFragment = new FundReceiveFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, fundReceiveFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 9:
                    final Dialog dialog = new Dialog(Swipe.this.context);
                    dialog.setContentView(R.layout.toast_warning);
                    CardView bb1= (CardView)dialog.findViewById(R.id.b1);
                    TextView t1= (TextView)dialog.findViewById(R.id.toast_text);
                    TextView t01= (TextView)dialog.findViewById(R.id.title);
                    ImageView imageView=(ImageView)dialog.findViewById(R.id.im);
                    imageView.setImageResource(R.drawable.info);
                    String warning="Money Transfer";
                    t01.setText(warning);
                    String message="Coming Soon...";
                    t1.setText(message);
                    bb1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                    return;
                case 10:
                    CommonUtils.fundTransferStatus = "true";
                    FundTransferFragment fundTransferFragment = new FundTransferFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, fundTransferFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 11:
                    CommonUtils.rch_status = "true";
                    DealerRechHistFragment dealerRechHistFragment = new DealerRechHistFragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, dealerRechHistFragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.Swipe$3 */
    class C06913 implements OnItemClickListener {
        C06913() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            FragmentManager fragmentManager = Swipe.this.getFragmentManager();
            FragmentTransaction fragmentTransaction;
            switch (position) {
                case 0:
                    ApiHistory_admin_Fragment apiHistory_admin_fragment = new ApiHistory_admin_Fragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridViewAdmin.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, apiHistory_admin_fragment, "PREPAID");
                    fragmentTransaction.addToBackStack("PREPAID");
                    fragmentTransaction.commit();
                    return;
                case 1:
                    ChangeApi_Admin_Fragment changeApi_admin_fragment = new ChangeApi_Admin_Fragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridViewAdmin.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, changeApi_admin_fragment, "PREPAID");
                    fragmentTransaction.addToBackStack("PREPAID");
                    fragmentTransaction.commit();
                    return;
                case 2:
                    History_Admin_Fragment history_admin_fragment = new History_Admin_Fragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridViewAdmin.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, history_admin_fragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 3:
                    Pending_Admin_Fragment pending_admin_fragment = new Pending_Admin_Fragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridViewAdmin.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, pending_admin_fragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 4:
                    FundTransfer_Admin_Fragment fundTransfer_admin_fragment = new FundTransfer_Admin_Fragment();
                    Swipe.this.currentPosition = position;
                    Swipe.this.gridViewAdmin.setVisibility(View.INVISIBLE);
                    fragmentTransaction = Swipe.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.swipe, fundTransfer_admin_fragment, "PREPAID");
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    return;
                case 5:
                    if (VERSION.SDK_INT >= 16) {
                        Editor editor = Swipe.this.getContext().getSharedPreferences("LogDIN", 0).edit();
                        editor.clear();
                        editor.commit();
                        Swipe.this.startActivity(new Intent(Swipe.this.context, AfterLoginActivity.class));
                        Swipe.this.getActivity().finishAffinity();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static Swipe newInstance(String param1, String param2) {
        Swipe fragment = new Swipe();
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

    @SuppressLint("WrongConstant")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("WrongConstant") View view = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.fragment_swipe, container, false);
        String tag = "PREPAID";
        this.context = getActivity();
        this.progressDialog = new ProgressDialog(getActivity());
        CommonUtils.fundTransferStatus = "false";
        CommonUtils.rch_status = "false";
        this.role = CommonUtils.role;
        if (this.role.equalsIgnoreCase("Retailer")) {
            this.gridView = (GridView) view.findViewById(R.id.gridView);
            this.gridView.setAdapter(new CustomGrid(this.context, this.re_imageId, this.re_titleGrid));
            this.gridView.setOnItemClickListener(new C06891());
        } else if (this.role.equalsIgnoreCase("Distributor")) {
            this.gridView = (GridView) view.findViewById(R.id.gridView);
            this.gridView.setAdapter(new CustomGrid(this.context, this.imageId, this.titleGrid));
            this.gridView.setOnItemClickListener(new C06902());

        } else if (this.role.equalsIgnoreCase("Admin")) {
            this.gridViewAdmin = (GridView) view.findViewById(R.id.gridView_admin);
            this.gridViewAdmin.setVisibility(0);
            this.gridViewAdmin.setAdapter(new AdminCustomGrid(this.context, this.admin_imageId, this.admin_titleGrid));
            this.gridViewAdmin.setOnItemClickListener(new C06913());

        }
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public void setPage(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, CustomGridOperator customGridOperator, Bundle bundle, int position) {
        bundle.putInt("Position", position);
        customGridOperator.setArguments(bundle);
        fragmentTransaction.replace(R.id.swipe, customGridOperator, "GRID");
        fragmentTransaction.commit();
    }
}
