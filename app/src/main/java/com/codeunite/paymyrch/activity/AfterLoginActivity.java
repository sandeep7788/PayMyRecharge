package com.codeunite.paymyrch.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.fragment.LoginFragment;
import com.codeunite.paymyrch.fragment.OfflineFragment;
import com.codeunite.paymyrch.fragment.ProfileFragment;
import com.codeunite.paymyrch.utils.CommonUtils;
import java.util.ArrayList;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AfterLoginActivity extends AppCompatActivity {
    FrameLayout container;
    Dialog dialog;
    ImageView img_support;
    public Tab tab;
    public TabLayout tabLayout;
    private Toolbar toolbar;
    ViewPager viewPager;
    ViewPagerAdapter viewpagerAdapter;

    /* renamed from: com.codeunite.PayMyRecharge.activity.AfterLoginActivity$1 */
    class C05011 implements OnClickListener {

        /* renamed from: com.codeunite.PayMyRecharge.activity.AfterLoginActivity$1$1 */
        class C05001 implements OnClickListener {
            C05001() {
            }

            public void onClick(View view) {
                AfterLoginActivity.this.dialog.dismiss();
            }
        }

        C05011() {
        }

        public void onClick(View v) {
            AfterLoginActivity.this.dialog = new Dialog(AfterLoginActivity.this, R.style.DialogFragmentTheme);
            AfterLoginActivity.this.dialog.setContentView(R.layout.fragment_support_dialog);
            TextView email = (TextView) AfterLoginActivity.this.dialog.findViewById(R.id.mail);
            TextView mobile = (TextView) AfterLoginActivity.this.dialog.findViewById(R.id.txt_mobile);
            ((ImageView) AfterLoginActivity.this.dialog.findViewById(R.id.back)).setOnClickListener(new C05001());
            AfterLoginActivity.this.dialog.show();
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
            if (position == 0 && AfterLoginActivity.this.viewpagerAdapter.getItem(0).equals(new ProfileFragment())) {
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        CalligraphyConfig.initDefault(new Builder().setDefaultFontPath("fonts/fontregular.ttf").setFontAttrId(R.attr.fontPath).build());
        setContentView((int) R.layout.activity_after_login);
        this.img_support = (ImageView) findViewById(R.id.ivSearch);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager1);
        this.container = (FrameLayout) findViewById(R.id.frame);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs1);
        this.tabLayout.setupWithViewPager(this.viewPager);
        setupViewPager(this.viewPager);
        if (CommonUtils.offline_status == null) {
            CommonUtils.offline_status = "false";
        }
        if (CommonUtils.offline_status.equalsIgnoreCase("true")) {
            this.viewPager.setCurrentItem(1);
        }
        this.img_support.setOnClickListener(new C05011());
    }

    private void setupViewPager(ViewPager viewPager) {
        this.viewpagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.viewpagerAdapter.addFrag(new LoginFragment(), "ONLINE");
        this.viewpagerAdapter.addFrag(new OfflineFragment(), "OFFLINE");
        viewPager.setAdapter(this.viewpagerAdapter);
    }
}
