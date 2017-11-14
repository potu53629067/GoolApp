package com.peng.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.peng.base.BaseFragment;
import com.peng.factory.FragmentFactory;
import com.peng.utils.UIUtils;

import appmark.peng.com.myapplication.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.mainTabs)
    PagerSlidingTabStrip mMainTabs;
    @Bind(R.id.mainViewPage)
    ViewPager mMainViewPage;
    @Bind(R.id.main_drawerLayout)
    DrawerLayout mMainDrawerLayout;
   /* private PagerSlidingTabStrip mMainTabs;
    private ViewPager mMainViewPage;
    private DrawerLayout mMainDrawerLayout;*/
    private ActionBar mActionBar;
    private ActionBarDrawerToggle mToggle;
    private String[] mMainTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initActionBar();
        initActionBarDrawerToggle();
        initData();
        initListener();

    }

    private void init() {
      /*  mMainTabs = (PagerSlidingTabStrip) findViewById(R.id.mainTabs);
        mMainViewPage = (ViewPager) findViewById(R.id.mainViewPage);
        mMainDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);*/
    }

    private void initData() {
        //1.viewpage绑定adapter
        //测试模拟数据
        mMainTitles = UIUtils.getStrings(R.array.main_titles);
        MyFragmentStatePagerAdapter fragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager());
        mMainViewPage.setAdapter(fragmentStatePagerAdapter);
        //2.tabs绑定viewpager
        mMainTabs.setViewPager(mMainViewPage);
    }

    //3. ActionBarDrawerToggle初始化操作
    private void initActionBarDrawerToggle() {
        mToggle = new ActionBarDrawerToggle(this, mMainDrawerLayout, R.string.open, R.string.close);
        //3.2同步状态
        mToggle.syncState();
        //3.3布局drawerLayout对象设置drawerLayout的监听
        mMainDrawerLayout.addDrawerListener(mToggle);
    }

    private void initActionBar() {
        // 获取ActionBar(兼容包里的)
        mActionBar = getSupportActionBar();
        //getActionBar()//高版本的

        mActionBar.setTitle("秦志鹏");// 设置主标题部分
        mActionBar.setSubtitle("peng");// 设置副标题部分

        mActionBar.setIcon(R.mipmap.ic_launcher);// 设置图标(优先)
        mActionBar.setLogo(R.mipmap.ic_action_star);

        //显示图标
        mActionBar.setDisplayShowHomeEnabled(true); //默认是false,默认是隐藏图标
        mActionBar.setDisplayShowTitleEnabled(true);// 设置菜单的标题是否可见(默认是true,显示标题)
        mActionBar.setDisplayUseLogoEnabled(true);// 设置是否显示Logo优先
        mActionBar.setDisplayHomeAsUpEnabled(true);// 设置back按钮是否可见
    }

    @Override       //actionBar的点击事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(getApplicationContext(), "菜单", Toast.LENGTH_SHORT).show();
                mToggle.onOptionsItemSelected(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //监听tabs选中事件
    private void initListener() {
        final MyOnPageChangeListener onPageChangeListener = new MyOnPageChangeListener();
        mMainTabs.setOnPageChangeListener(onPageChangeListener);
        //ViewPager渲染完成调用
        mMainViewPage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //手动选择第一页
                onPageChangeListener.onPageSelected(0);
                mMainViewPage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (FragmentFactory.mCacheFragments.containsKey(position)) {
                BaseFragment baseFragment = FragmentFactory.mCacheFragments.get(position);
                baseFragment.getLoadingPager().triggerLoadData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPageAdapter extends PagerAdapter {

        //1.获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的图片的ImageView数量
        @Override
        public int getCount() {
            if (mMainTitles != null) {
                return mMainTitles.length;
            }
            return 0;
        }

        //2.来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //3.当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //data
            String data = mMainTitles[position];
            //view
            TextView tv = new TextView(UIUtils.getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.RED);

            //data+view(数据和视图的绑定)
            tv.setText(data);
            //加入容器
            container.addView(tv);
            //返回控件
            return tv;

        }
        //4.PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        //*5.必须覆写该方法:新增方法,目前较多用于Design库中的TabLayout与ViewPager进行绑定时,提供显示的标题。
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }
    }

    class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
        public MyFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            if (mMainTitles != null) {
                return mMainTitles.length;
            }
            return 0;
        }

        //不管什么Adapter记得重写getPageTitle方法,不然父类是返回null报空指针
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitles[position];
        }
    }
}
