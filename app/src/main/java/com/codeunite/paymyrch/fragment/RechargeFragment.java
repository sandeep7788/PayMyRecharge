package com.codeunite.paymyrch.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import java.util.ArrayList;
import java.util.List;

public class RechargeFragment extends Fragment implements OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Adapter adapter;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    TabLayout tabLayout;
    TextView tvBalance;
    View view;
    ViewPager viewPager;

    static class Adapter extends FragmentPagerAdapter {
        private final List<String> mFragmentTitles = new ArrayList();
        private final List<Fragment> mFragments = new ArrayList();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragments.add(fragment);
            this.mFragmentTitles.add(title);
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragments.get(position);
        }

        public int getCount() {
            return this.mFragments.size();
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) this.mFragmentTitles.get(position);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static RechargeFragment newInstance(String param1, String param2) {
        RechargeFragment fragment = new RechargeFragment();
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
        this.view = inflater.inflate(R.layout.fragment_recharge, container, false);
        super.onCreate(savedInstanceState);
        this.viewPager = (ViewPager) this.view.findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        this.tabLayout = (TabLayout) this.view.findViewById(R.id.tabs);
        this.tvBalance = (TextView) getActivity().findViewById(R.id.tvBalance);
        this.tabLayout.setupWithViewPager(this.viewPager);
        return this.view;
    }

    private void setupViewPager(ViewPager viewPager) {
        this.adapter = new Adapter(getChildFragmentManager());
        this.adapter.addFragment(new Swipe(), "              Welcome To PaymentWala.com");
        viewPager.setAdapter(this.adapter);
    }

    public void onClick(View v) {
        v.getId();
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    public void onResume() {
        super.onResume();
        this.tabLayout.setupWithViewPager(this.viewPager);
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
