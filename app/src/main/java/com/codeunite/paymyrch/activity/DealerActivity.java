package com.codeunite.paymyrch.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.codeunite.paymyrch.AsyncTask.BalanceTask;
import com.codeunite.paymyrch.AsyncTask.NewsMarqueeTask;
import com.codeunite.paymyrch.AsyncTask.ProfileTask;
import com.codeunite.paymyrch.AsyncTask.SearchMobileTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.DealerExpandableListAdapter;
import com.codeunite.paymyrch.adapter.ExpandableListAdapter;
import com.codeunite.paymyrch.fragment.BankDetailsFragment;
import com.codeunite.paymyrch.fragment.BankListFragment;
import com.codeunite.paymyrch.fragment.ChangePasswordFragment;
import com.codeunite.paymyrch.fragment.CreateRetailerFragment;
import com.codeunite.paymyrch.fragment.DTHFragment;
import com.codeunite.paymyrch.fragment.DataCardFragment;
import com.codeunite.paymyrch.fragment.DealerRechHistFragment;
import com.codeunite.paymyrch.fragment.Dispute_Report;
import com.codeunite.paymyrch.fragment.DistributerOperatorStatus;
import com.codeunite.paymyrch.fragment.ElectricityFragment;
import com.codeunite.paymyrch.fragment.FundReceiveFragment;
import com.codeunite.paymyrch.fragment.FundRequestFragment;
import com.codeunite.paymyrch.fragment.FundRequestHistoryFragment;
import com.codeunite.paymyrch.fragment.FundTransReportFragment;
import com.codeunite.paymyrch.fragment.FundTransferFragment;
import com.codeunite.paymyrch.fragment.GasFragment;
import com.codeunite.paymyrch.fragment.HistoryFragment;
import com.codeunite.paymyrch.fragment.IncomeFragment;
import com.codeunite.paymyrch.fragment.InsuranceFragment;
import com.codeunite.paymyrch.fragment.LandLineFragment;
import com.codeunite.paymyrch.fragment.PostpaidFragment;
import com.codeunite.paymyrch.fragment.PrepaidFragment;
import com.codeunite.paymyrch.fragment.ProfileFragment;
import com.codeunite.paymyrch.fragment.RetailerListFragment;
import com.codeunite.paymyrch.fragment.Swipe;
import com.codeunite.paymyrch.helper.ValidationChecker;
import com.codeunite.paymyrch.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DealerActivity extends AppCompatActivity {
    public static String walletBalance;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ExpandableListAdapter adapter;
    DealerExpandableListAdapter adapter1;
    BalanceTask balanceTask;
    FrameLayout container;
    Context context;
    MenuItem currentMenuItem;
    private DrawerLayout drawerLayout;
    private Boolean exit = Boolean.valueOf(false);
    FragmentTransaction fragmentTransaction;
    HistoryFragment historyFragment;
    private boolean isDrawerOpen = false;
    ImageView ivSearch;
    private NavigationView navigationView;
    ProgressDialog progressDialog;
    Swipe swipe;
    public Tab tab;
    public TabLayout tabLayout;
    private Toolbar toolbar;
    TextView tvBalance;
    TextView tvNews;
    RelativeLayout userProfile_header;
    TextView user_name;
    TextView user_name_header;
    TextView user_number_header;
    ValidationChecker validationChecker;
    ViewPager viewPager;
    ViewPagerAdapter viewpagerAdapter;

    /* renamed from: com.codeunite.PayMyRecharge.activity.DealerActivity$1 */
    class C05021 implements OnClickListener {
        C05021() {
        }

        public void onClick(View view) {
            DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
            DealerActivity.this.balanceTask.execute(new String[0]);
            Fragment profileFragment = new ProfileFragment();
            FragmentTransaction fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
            fragmentTransactionProfile.add((int) R.id.swipe, profileFragment);
            fragmentTransactionProfile.commit();
            DealerActivity.this.drawerLayout.closeDrawers();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.activity.DealerActivity$2 */
    class C05042 implements OnClickListener {
        C05042() {
        }

        public void onClick(View v) {
            Dialog dialog = new Dialog(DealerActivity.this.context);
            dialog.setContentView(R.layout.dialog_search_mobile);
            dialog.setTitle("Search Number");
            final EditText edtMobile = (EditText) dialog.findViewById(R.id.edtMobile);
            final ExpandableListView lvSearchMobile = (ExpandableListView) dialog.findViewById(R.id.listviewnew);
            final TableRow searchTableRow = (TableRow) dialog.findViewById(R.id.searchtablerow);
            ((Button) dialog.findViewById(R.id.btnSend)).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String mobileNo = edtMobile.getText().toString();
                    if (mobileNo.isEmpty()) {
                        edtMobile.setError("Please enter valid  number.");
                        return;
                    }
                    try {
                        new SearchMobileTask(DealerActivity.this, mobileNo, DealerActivity.this.adapter1, lvSearchMobile, DealerActivity.this.progressDialog, searchTableRow, DealerActivity.this.tvBalance).execute(new String[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            dialog.show();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.activity.DealerActivity$3 */
    class C05053 implements OnNavigationItemSelectedListener {
        C05053() {
        }

        public boolean onNavigationItemSelected(MenuItem menuItem) {
            if (menuItem.isChecked()) {
                menuItem.setChecked(false);
            } else {
                menuItem.setChecked(true);
            }
            DealerActivity.this.drawerLayout.closeDrawers();
            FragmentTransaction fragmentTransactionProfile;
            switch (menuItem.getItemId()) {
                case R.id.home:
                    DealerActivity.this.tabLayout.setVisibility(View.GONE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    FragmentTransaction fragmentTransaction = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    if (DealerActivity.this.container != null) {
                        DealerActivity.this.container.removeAllViewsInLayout();
                        DealerActivity.this.container.removeAllViews();
                    }
                    fragmentTransaction.replace(R.id.swipe, DealerActivity.this.swipe);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    if (DealerActivity.this.tab != null) {
                        DealerActivity.this.tab.setText((CharSequence) "Home");
                    }
                    fragmentTransaction.commit();
                    return true;
                case R.id.change_pass:
                    DealerActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    Fragment changePasswordFragment = new ChangePasswordFragment();
                    fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionProfile.add(R.id.swipe, changePasswordFragment);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    DealerActivity.this.tab.setText((CharSequence) "Change Password");
                    fragmentTransactionProfile.commit();
                    return true;
                case R.id.logout:
                    if (VERSION.SDK_INT >= 16) {
                        Editor editor = DealerActivity.this.getSharedPreferences("LogDIN", 0).edit();
                        editor.clear();
                        editor.commit();
                        DealerActivity.this.startActivity(new Intent(DealerActivity.this, AfterLoginActivity.class));
                        DealerActivity.this.finishAffinity();
                    }
                    return true;
                case R.id.bank_details:
                    DealerActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    Fragment bankDetailsFragment = new BankListFragment();
                    fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionProfile.add(R.id.swipe, bankDetailsFragment);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    DealerActivity.this.tab.setText((CharSequence) "Bank Details");
                    fragmentTransactionProfile.commit();
                    return true;
                case R.id.create_retailer:
                    DealerActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    Fragment createRetailerFragment = new CreateRetailerFragment();
                    fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionProfile.add(R.id.swipe, createRetailerFragment);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    DealerActivity.this.tab.setText((CharSequence) "Create User");
                    fragmentTransactionProfile.commit();
                    return true;
                case R.id.retailer_list:
                    DealerActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    Fragment retailerListFragment = new RetailerListFragment();
                    fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionProfile.add(R.id.swipe, retailerListFragment);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    DealerActivity.this.tab.setText((CharSequence) "Retailer List");
                    fragmentTransactionProfile.commit();
                    return true;
                case R.id.fund_request:
                    DealerActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    Fragment fundRequestFragment = new FundRequestFragment();
                    fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionProfile.add(R.id.swipe, fundRequestFragment);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    DealerActivity.this.tab.setText((CharSequence) "Fund Request");
                    fragmentTransactionProfile.commit();
                    return true;
                case R.id.dispute_history_dealer:
                    DealerActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    Fragment disputeHistoryFragment = new Dispute_Report();
                    fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionProfile.add(R.id.swipe, disputeHistoryFragment);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    DealerActivity.this.tab.setText((CharSequence) "Dispute Report");
                    fragmentTransactionProfile.commit();
                    return true;
                case R.id.fund_transfer_report:
                    DealerActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    Fragment fundTransReportFragment = new FundTransReportFragment();
                    fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionProfile.add(R.id.swipe, fundTransReportFragment);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    DealerActivity.this.tab.setText((CharSequence) "Fund Trans. Report");
                    fragmentTransactionProfile.commit();
                    return true;
                case R.id.fund_request_report_dealer:
                    DealerActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    Fragment fundRequestHistoryFragment = new FundRequestHistoryFragment();
                    fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionProfile.add(R.id.swipe, fundRequestHistoryFragment);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    DealerActivity.this.tab.setText((CharSequence) "Fund Request Report");
                    fragmentTransactionProfile.commit();
                    return true;
                case R.id.income:
                    DealerActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    DealerActivity.this.balanceTask = new BalanceTask(DealerActivity.this.context, DealerActivity.this.tvBalance, DealerActivity.this.progressDialog);
                    DealerActivity.this.balanceTask.execute(new String[0]);
                    Fragment incomeFragment = new IncomeFragment();
                    fragmentTransactionProfile = DealerActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) DealerActivity.this.viewPager.getCurrentItem()) == DealerActivity.this.viewpagerAdapter.getItemId(1)) {
                        DealerActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionProfile.add(R.id.swipe, incomeFragment);
                    DealerActivity.this.tab = DealerActivity.this.tabLayout.getTabAt(0);
                    DealerActivity.this.tab.setText((CharSequence) "Income");
                    fragmentTransactionProfile.commit();
                    return true;
                default:
                    Toast.makeText(DealerActivity.this.getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                    return true;
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.activity.DealerActivity$5 */
    class C05075 implements Runnable {
        C05075() {
        }

        public void run() {
            DealerActivity.this.exit = Boolean.valueOf(false);
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            if (position == 0 && DealerActivity.this.viewpagerAdapter.getItem(0).equals(new ProfileFragment())) {
                return "Profile";
            }
            return (CharSequence) this.mFragmentTitleList.get(position);
        }

        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            new BalanceTask(this.context, this.tvBalance, this.progressDialog).execute(new String[0]);
            new NewsMarqueeTask(this.context, this.tvNews, this.progressDialog).execute(new String[0]);
            new ProfileTask(this.context, null, this.user_name, null, null, null, this.user_name_header, this.user_number_header).execute(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        CalligraphyConfig.initDefault(new Builder().setDefaultFontPath("fonts/fontregular.ttf").setFontAttrId(R.attr.fontPath).build());
        setContentView((int) R.layout.activity_dealer);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager1);
        this.validationChecker = new ValidationChecker();
        this.container = (FrameLayout) findViewById(R.id.frame);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs1);
        this.tabLayout.setupWithViewPager(this.viewPager);
        setupViewPager(this.viewPager);
        this.tabLayout.setVisibility(View.GONE);
        this.context = this;
        this.progressDialog = new ProgressDialog(this);
        this.ivSearch = (ImageView) findViewById(R.id.ivSearch);
        this.tvBalance = (TextView) findViewById(R.id.tvBalance);
        this.user_name = (TextView) findViewById(R.id.txt_note);
        this.tvNews = (TextView) findViewById(R.id.tvNews);
        this.tvNews.setSelected(true);
        this.swipe = new Swipe();
        this.navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header = this.navigationView.getHeaderView(0);
        this.user_name_header = (TextView) header.findViewById(R.id.user_name_text);
        this.user_number_header = (TextView) header.findViewById(R.id.user_number_text);
        this.userProfile_header = (RelativeLayout) header.findViewById(R.id.userProfile_header);
        new ProfileTask(this.context, null, this.user_name, null, null, null, this.user_name_header, this.user_number_header).execute(new String[0]);
        this.userProfile_header.setOnClickListener(new C05021());
        this.ivSearch.setOnClickListener(new C05042());
        this.navigationView.setNavigationItemSelectedListener(new C05053());
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar, R.string.openDrawer, R.string.closeDrawer) {
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                DealerActivity.this.isDrawerOpen = false;
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                DealerActivity.this.isDrawerOpen = true;
            }
        };

        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);
        this.actionBarDrawerToggle.syncState();

        /*ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager1);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new FragmentOne(), "FRAG1");
        adapter.addFrag(new Dispute_Report(), "FRAG2");
//        adapter.addFragment(new FragmentThree(), "FRAG3");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/
    }

    private void setupViewPager(ViewPager viewPager) {
        this.viewpagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.viewpagerAdapter.addFrag(new Swipe(), "Home");
        this.viewpagerAdapter.addFrag(new FragmentOne(), "Wallet");
        this.viewpagerAdapter.addFrag(new ProfileFragment(), "Profile");
        this.viewpagerAdapter.addFrag(new FragmentOne(), "Setting");
        viewPager.setAdapter(this.viewpagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.t_tabs);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home1);
        tabLayout.getTabAt(1).setIcon(R.drawable.wallet1);
        tabLayout.getTabAt(2).setIcon(R.drawable.user1);
        tabLayout.getTabAt(3).setIcon(R.drawable.setting1);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(0).select();
    }

    public void onBackPressed() {
        CommonUtils.fundTransferStatus = "false";
        CommonUtils.rch_status = "false";
        if (this.isDrawerOpen) {
            this.drawerLayout.closeDrawer(8388611);
        } else if (((long) this.viewPager.getCurrentItem()) == this.viewpagerAdapter.getItemId(1)) {
            this.viewPager.setCurrentItem(0);
        } else if (getFragmentManager().findFragmentByTag("PREPAID") != null && getFragmentManager().findFragmentByTag("PREPAID").isVisible()) {
            getFragmentManager().popBackStack();
        } else if ((getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof ProfileFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof BankListFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof BankDetailsFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundReceiveFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof RetailerListFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundTransferFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundTransReportFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof CreateRetailerFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof DistributerOperatorStatus) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof ChangePasswordFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof DealerRechHistFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundRequestFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof PrepaidFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof PostpaidFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof LandLineFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof DataCardFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof InsuranceFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof Dispute_Report) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundRequestHistoryFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof GasFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof ElectricityFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof DTHFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof IncomeFragment)) {
            this.tabLayout.setVisibility(View.GONE);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.swipe, this.swipe);
            this.tab = this.tabLayout.getTabAt(0);
            this.tab.setText((CharSequence) "Home");
            transaction.commit();
        } else if (this.exit.booleanValue()) {
            finish();
        } else {
            Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
            this.exit = Boolean.valueOf(true);
            new Handler().postDelayed(new C05075(), 3000);
        }
    }

    public void updateHistory() {
        Log.d("CALLED", "updateHistory: ");
        this.historyFragment.showDealerRechHistory();
    }
}
