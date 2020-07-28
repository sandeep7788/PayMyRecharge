package com.codeunite.paymyrch.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.codeunite.paymyrch.AsyncTask.BankDetailsTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.BankDetailHolder;
import com.codeunite.paymyrch.utils.CommonUtils;
import java.util.ArrayList;

public class BankListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    BankDetailsTask bankDetailsTask;
    Context context;
    int[] imageid = new int[0];
    ListView listView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    ArrayList<BankDetailHolder> mybankDetailList;
    String[] title = new String[0];

    /* renamed from: com.codeunite.PayMyRecharge.fragment.BankListFragment$1 */
    class C05291 implements OnItemClickListener {
        C05291() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            BankDetailHolder bankDetailobj = (BankDetailHolder) BankListFragment.this.mybankDetailList.get(position);
            BankDetailsFragment bankDetailsFragment = new BankDetailsFragment();
            FragmentTransaction fragmentTransaction = BankListFragment.this.getFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("BankName", bankDetailobj.BankName);
            bundle.putString("BranchName", bankDetailobj.BranchName);
            bundle.putString("IfscCode", bankDetailobj.IfscCode);
            bundle.putString("AccountNo", bankDetailobj.AccountNumber);
            bundle.putString("AccountType", bankDetailobj.AccountType);
            bundle.putString("AccountHolderName", bankDetailobj.AccountHolderName);
            bundle.putString("BankAddress", bankDetailobj.BankAddress);
            bankDetailsFragment.setArguments(bundle);
            if (CommonUtils.role.equalsIgnoreCase("Retailer")) {
                fragmentTransaction.replace(R.id.swipe, bankDetailsFragment, "BankDetail").addToBackStack("BankDetail");
                fragmentTransaction.commit();
            } else if (CommonUtils.role.equalsIgnoreCase("Distributor")) {
                fragmentTransaction.replace(R.id.swipe, bankDetailsFragment, "BankDetail").addToBackStack("BankDetail");
                fragmentTransaction.commit();
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static BankListFragment newInstance(String param1, String param2) {
        BankListFragment fragment = new BankListFragment();
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
        View view = inflater.inflate(R.layout.fragment_bank_list, container, false);
        this.context = getActivity();
        this.listView = (ListView) view.findViewById(R.id.bank_listImage);
        this.bankDetailsTask = new BankDetailsTask(getActivity(), this.listView, this);
        this.bankDetailsTask.execute(new String[0]);
        this.listView.setOnItemClickListener(new C05291());
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

    public void setBankDetailList(ArrayList mybankDetailList) {
        this.mybankDetailList = mybankDetailList;
    }
}
