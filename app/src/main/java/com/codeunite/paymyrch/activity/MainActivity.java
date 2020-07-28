package com.codeunite.paymyrch.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
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
import com.codeunite.paymyrch.fragment.DTHFragment;
import com.codeunite.paymyrch.fragment.DataCardFragment;
import com.codeunite.paymyrch.fragment.Dispute_Report;
import com.codeunite.paymyrch.fragment.ElectricityFragment;
import com.codeunite.paymyrch.fragment.FundReceiveFragment;
import com.codeunite.paymyrch.fragment.FundRequestFragment;
import com.codeunite.paymyrch.fragment.FundRequestHistoryFragment;
import com.codeunite.paymyrch.fragment.GasFragment;
import com.codeunite.paymyrch.fragment.HistoryFragment;
import com.codeunite.paymyrch.fragment.IncomeFragment;
import com.codeunite.paymyrch.fragment.InsuranceFragment;
import com.codeunite.paymyrch.fragment.LandLineFragment;
import com.codeunite.paymyrch.fragment.PostpaidFragment;
import com.codeunite.paymyrch.fragment.PrepaidFragment;
import com.codeunite.paymyrch.fragment.ProfileFragment;
import com.codeunite.paymyrch.fragment.Swipe;
import com.codeunite.paymyrch.helper.ValidationChecker;
import java.util.ArrayList;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity  {
    public static ProgressDialog progressDialog;
    public static String walletBalance;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ExpandableListAdapter adapter;
    DealerExpandableListAdapter adapter1;
    FrameLayout container;
    Context context;
    MenuItem currentMenuItem;
    DrawerLayout drawerLayout;
    private Boolean exit = Boolean.valueOf(false);
    FragmentTransaction fragmentTransaction;
    HistoryFragment historyFragment;
    boolean isDrawerOpen = false;
    ImageView ivSearch;
    NavigationView navigationView;
    TableRow searchTableRow;
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

    /* renamed from: com.codeunite.PayMyRecharge.activity.MainActivity$1 */
    class C05091 implements OnClickListener {
        C05091() {
        }

        public void onClick(View view) {
            Fragment profileFragment = new ProfileFragment();
            FragmentTransaction fragmentTransactionProfile = MainActivity.this.getSupportFragmentManager().beginTransaction();
            fragmentTransactionProfile.add((int) R.id.swipe, profileFragment);
            fragmentTransactionProfile.commit();
            MainActivity.this.drawerLayout.closeDrawers();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.activity.MainActivity$2 */
    class C05112 implements OnClickListener {
        C05112() {
        }

        public void onClick(View v) {
            Dialog dialog = new Dialog(MainActivity.this.context);
            dialog.setContentView(R.layout.dialog_search_mobile);
            dialog.setTitle("Search Number");
            final EditText edtMobile = (EditText) dialog.findViewById(R.id.edtMobile);
            final ExpandableListView lvSearchMobile = (ExpandableListView) dialog.findViewById(R.id.listviewnew);
            Button dialogButton = (Button) dialog.findViewById(R.id.btnSend);
            MainActivity.this.searchTableRow = (TableRow) dialog.findViewById(R.id.searchtablerow);
            dialogButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String mobileNo = edtMobile.getText().toString().trim();
                    if (mobileNo.isEmpty()) {
                        edtMobile.setError("Please enter valid  number.");
                        return;
                    }
                    try {
                        new SearchMobileTask(MainActivity.this, mobileNo, MainActivity.this.adapter1, lvSearchMobile, MainActivity.progressDialog, MainActivity.this.searchTableRow, MainActivity.this.tvBalance).execute(new String[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            dialog.show();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.activity.MainActivity$3 */
    class C05123 implements OnNavigationItemSelectedListener {
        C05123() {
        }

        @RequiresApi(api = 18)
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            MainActivity.this.currentMenuItem = menuItem;
            if (menuItem.isChecked()) {
                menuItem.setChecked(false);
            } else {
                menuItem.setChecked(true);
            }
            MainActivity.this.drawerLayout.closeDrawers();
            FragmentTransaction fragmentTransactionChange;
            switch (menuItem.getItemId()) {
                case R.id.home:
                    MainActivity.this.tvNews.setVisibility(View.VISIBLE);
                    MainActivity.this.tabLayout.setVisibility(View.GONE);
                    FragmentTransaction fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) MainActivity.this.viewPager.getCurrentItem()) == MainActivity.this.viewpagerAdapter.getItemId(1)) {
                        MainActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    if (MainActivity.this.container != null) {
                        MainActivity.this.container.removeAllViewsInLayout();
                        MainActivity.this.container.removeAllViews();
                    }
                    fragmentTransaction.replace(R.id.swipe, MainActivity.this.swipe);
                    MainActivity.this.tab = MainActivity.this.tabLayout.getTabAt(0);
                    if (MainActivity.this.tab != null) {
                        MainActivity.this.tab.setText((CharSequence) "Home");
                    }
                    fragmentTransaction.commit();
                    return true;
                case R.id.change_pass:
                    MainActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    ChangePasswordFragment changeFragment = new ChangePasswordFragment();
                    fragmentTransactionChange = MainActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) MainActivity.this.viewPager.getCurrentItem()) == MainActivity.this.viewpagerAdapter.getItemId(1)) {
                        MainActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionChange.replace(R.id.swipe, changeFragment);
                    fragmentTransactionChange.commit();
                    MainActivity.this.tab = MainActivity.this.tabLayout.getTabAt(0);
                    if (MainActivity.this.tab != null) {
                        MainActivity.this.tab.setText((CharSequence) "Change Password");
                    }
                    return true;
                case R.id.logout:
                    if (VERSION.SDK_INT >= 16) {
                        Editor editor = MainActivity.this.getSharedPreferences("LogDIN", 0).edit();
                        editor.clear();
                        editor.commit();
                        MainActivity.this.startActivity(new Intent(MainActivity.this, AfterLoginActivity.class));
                        MainActivity.this.finishAffinity();
                    }
                    return true;
                case R.id.income:
                    MainActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    IncomeFragment incomeFragment = new IncomeFragment();
                    FragmentTransaction fragmentTransactionFund = MainActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) MainActivity.this.viewPager.getCurrentItem()) == MainActivity.this.viewpagerAdapter.getItemId(1)) {
                        MainActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionFund.replace(R.id.swipe, incomeFragment);
                    fragmentTransactionFund.commit();
                    MainActivity.this.tab = MainActivity.this.tabLayout.getTabAt(0);
                    MainActivity.this.tab.setText((CharSequence) "Income");
                    return true;
                case R.id.rc_history:
                    MainActivity.this.tabLayout.setVisibility(View.GONE);
                    FragmentTransaction fragmentTransactionhistory = MainActivity.this.getSupportFragmentManager().beginTransaction();
                    Fragment historyFragment = new HistoryFragment();
                    if (((long) MainActivity.this.viewPager.getCurrentItem()) == MainActivity.this.viewpagerAdapter.getItemId(1)) {
                        MainActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionhistory.add((int) R.id.swipe, historyFragment);
                    MainActivity.this.tab = MainActivity.this.tabLayout.getTabAt(0);
                    if (MainActivity.this.tab != null) {
                        MainActivity.this.tab.setText((CharSequence) "Profile");
                    }
                    fragmentTransactionhistory.commit();
                    return true;
                case R.id.dispute_history:
                    MainActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    Dispute_Report dispute_report = new Dispute_Report();
                    fragmentTransactionChange = MainActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) MainActivity.this.viewPager.getCurrentItem()) == MainActivity.this.viewpagerAdapter.getItemId(1)) {
                        MainActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionChange.replace(R.id.swipe, dispute_report);
                    fragmentTransactionChange.commit();
                    MainActivity.this.tab = MainActivity.this.tabLayout.getTabAt(0);
                    if (MainActivity.this.tab != null) {
                        MainActivity.this.tab.setText((CharSequence) "Dispute Report");
                    }
                    return true;
                case R.id.bankDetails:
                    MainActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    BankListFragment bankDetailsFragment = new BankListFragment();
                    fragmentTransactionChange = MainActivity.this.getSupportFragmentManager().beginTransaction();
                    fragmentTransactionChange.replace(R.id.swipe, bankDetailsFragment);
                    fragmentTransactionChange.commit();
                    MainActivity.this.tab = MainActivity.this.tabLayout.getTabAt(0);
                    if (MainActivity.this.tab != null) {
                        MainActivity.this.tab.setText((CharSequence) "Bank Details");
                    }
                    if (((long) MainActivity.this.viewPager.getCurrentItem()) == MainActivity.this.viewpagerAdapter.getItemId(1)) {
                        MainActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    return true;
                case R.id.fund_request_report:
                    MainActivity.this.tabLayout.setVisibility(View.VISIBLE);
                    FundRequestHistoryFragment fundRequestHistoryFragment = new FundRequestHistoryFragment();
                    fragmentTransactionChange = MainActivity.this.getSupportFragmentManager().beginTransaction();
                    if (((long) MainActivity.this.viewPager.getCurrentItem()) == MainActivity.this.viewpagerAdapter.getItemId(1)) {
                        MainActivity.this.viewPager.setCurrentItem(0, true);
                    }
                    fragmentTransactionChange.replace(R.id.swipe, fundRequestHistoryFragment);
                    fragmentTransactionChange.commit();
                    MainActivity.this.tab = MainActivity.this.tabLayout.getTabAt(0);
                    if (MainActivity.this.tab != null) {
                        MainActivity.this.tab.setText((CharSequence) "Fund Request Report");
                    }
                    return true;
                default:
                    return true;
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.activity.MainActivity$5 */
    class C05145 implements Runnable {
        C05145() {
        }

        public void run() {
            MainActivity.this.exit = Boolean.valueOf(false);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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
            if (position == 0 && MainActivity.this.viewpagerAdapter.getItem(0).equals(new ProfileFragment())) {
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
            new BalanceTask(this.context, this.tvBalance, progressDialog).execute(new String[0]);
            new NewsMarqueeTask(this.context, this.tvNews, progressDialog).execute(new String[0]);
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
        setContentView((int) R.layout.activity_main);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        this.context = this;
        progressDialog = new ProgressDialog(this);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager1);
        this.validationChecker = new ValidationChecker();
        this.container = (FrameLayout) findViewById(R.id.frame);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs1);
        this.tabLayout.setupWithViewPager(this.viewPager);
        setupViewPager(this.viewPager);
        this.tvBalance = (TextView) findViewById(R.id.tvBalance);
        this.user_name = (TextView) findViewById(R.id.txt_note);
        this.tvNews = (TextView) findViewById(R.id.tvNews);
        this.ivSearch = (ImageView) findViewById(R.id.ivSearch);
        this.tvNews.setSelected(true);
        this.swipe = new Swipe();
        this.tabLayout.setVisibility(View.GONE);
         this.navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header = this.navigationView.getHeaderView(0);
        this.user_name_header = (TextView) header.findViewById(R.id.user_name_text);
        this.user_number_header = (TextView) header.findViewById(R.id.user_number_text);
        this.userProfile_header = (RelativeLayout) header.findViewById(R.id.userProfile_header);
        new ProfileTask(this.context, null, this.user_name, null, null, null, this.user_name_header, this.user_number_header).execute(new String[0]);
        this.userProfile_header.setOnClickListener(new C05091());
        this.ivSearch.setOnClickListener(new C05112());
        this.navigationView.setNavigationItemSelectedListener(new C05123());
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar, R.string.openDrawer, R.string.closeDrawer) {
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                MainActivity.this.isDrawerOpen = false;
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                MainActivity.this.isDrawerOpen = true;
            }
        };
        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);
        this.actionBarDrawerToggle.syncState();


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager1);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFrag(new FragmentOne(),"FRAG1");
//        adapter.addFragment(new FragmentOne(), "FRAG1");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }
    //@@

    private void setupViewPager(ViewPager viewPager) {
        this.viewpagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.viewpagerAdapter.addFrag(new Swipe(), "Home");
        viewPager.setAdapter(this.viewpagerAdapter);
    }

    public void onBackPressed() {
        if (this.isDrawerOpen) {
            this.drawerLayout.closeDrawer(8388611);
        } else if (((long) this.viewPager.getCurrentItem()) == this.viewpagerAdapter.getItemId(1)) {
            this.viewPager.setCurrentItem(0);
        } else if (getFragmentManager().findFragmentByTag("PREPAID") != null && getFragmentManager().findFragmentByTag("PREPAID").isVisible()) {
            getFragmentManager().popBackStack();
        } else if ((getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof ProfileFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof ChangePasswordFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundReceiveFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundRequestFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof HistoryFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof IncomeFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundRequestFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof PrepaidFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof PostpaidFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof LandLineFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof DataCardFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof InsuranceFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof Dispute_Report) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof GasFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundRequestHistoryFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof ElectricityFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof DTHFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof BankDetailsFragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof BankListFragment)) {
            Log.d("IT RAN FROM CONDITION", "B*ACK");
            if (this.currentMenuItem != null && this.currentMenuItem.isChecked()) {
                this.currentMenuItem.setChecked(false);
            }
            this.tabLayout.setVisibility(View.GONE);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.swipe, this.swipe);
            this.tab = this.tabLayout.getTabAt(0);
            this.tab.setText((CharSequence) "Home");
            fragmentTransaction.commit();
        } else if (this.exit.booleanValue()) {
            finish();
        } else {
            Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
            this.exit = Boolean.valueOf(true);
            new Handler().postDelayed(new C05145(), 3000);
        }
    }

    public void updateHistory() {
        Log.d("CALLED", "updateHistory: ");
        this.historyFragment.showDealerRechHistory();
    }
}
