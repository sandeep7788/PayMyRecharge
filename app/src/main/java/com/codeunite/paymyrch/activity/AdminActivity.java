package com.codeunite.paymyrch.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.fragment.ApiHistory_admin_Fragment;
import com.codeunite.paymyrch.fragment.ChangeApi_Admin_Fragment;
import com.codeunite.paymyrch.fragment.FundTransfer_Admin_Fragment;
import com.codeunite.paymyrch.fragment.History_Admin_Fragment;
import com.codeunite.paymyrch.fragment.Pending_Admin_Fragment;
import com.codeunite.paymyrch.fragment.ProfileFragment;
import com.codeunite.paymyrch.fragment.Swipe;
import com.codeunite.paymyrch.helper.ValidationChecker;
import java.util.ArrayList;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AdminActivity extends AppCompatActivity {
    public static ProgressDialog progressDialog;
    public static String walletBalance;
    FrameLayout container;
    Context context;
    MenuItem currentMenuItem;
    private Boolean exit = Boolean.valueOf(false);
    Swipe swipe;
    public Tab tab;
    public TabLayout tabLayout;
    private Toolbar toolbar;
    TextView tvBalance;
    TextView user_name;
    ValidationChecker validationChecker;
    ViewPager viewPager;
    ViewPagerAdapter viewpagerAdapter;

    /* renamed from: com.codeunite.PayMyRecharge.activity.AdminActivity$1 */
    class C04991 implements Runnable {
        C04991() {
        }
        public void run() {
            AdminActivity.this.exit = Boolean.valueOf(false);
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
            if (position == 0 && AdminActivity.this.viewpagerAdapter.getItem(0).equals(new ProfileFragment())) {
                return "Profile";
            }
            return (CharSequence) this.mFragmentTitleList.get(position);
        }

        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new Builder().setDefaultFontPath("fonts/fontregular.ttf").setFontAttrId(R.attr.fontPath).build());
        setContentView((int) R.layout.activity_admin);
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
        this.swipe = new Swipe();
        this.tabLayout.setVisibility(View.GONE);
    }

    private void setupViewPager(ViewPager viewPager) {
        this.viewpagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.viewpagerAdapter.addFrag(new Swipe(), "Home");
        viewPager.setAdapter(this.viewpagerAdapter);
    }

    public void onBackPressed() {
        if ((getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof ApiHistory_admin_Fragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof ChangeApi_Admin_Fragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof History_Admin_Fragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof Pending_Admin_Fragment) || (getSupportFragmentManager().findFragmentById(R.id.swipe) instanceof FundTransfer_Admin_Fragment)) {
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
            new Handler().postDelayed(new C04991(), 3000);
        }
    }
}
