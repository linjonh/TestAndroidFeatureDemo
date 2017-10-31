package com.jaysen.testfeaturedemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaysen.testfeaturedemo.span.SpanTestActivity;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button                    button;
    private   ViewPager                 viewPager;
    private   TabLayout                 designTabLayout;
    private   ChallengeViewPagerAdapter pagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SimplePagerTabLayout simplePagerTabLayout = (SimplePagerTabLayout) findViewById(
                R.id.challenge_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.challengeViewPager);
        designTabLayout = (TabLayout) findViewById(R.id.designTabLayout);
        pagerAdapter = new ChallengeViewPagerAdapter(getSupportFragmentManager(), new String[]{"哈哈哈", "二按方"});
        viewPager.setAdapter(pagerAdapter);

        simplePagerTabLayout.setViewPager(viewPager);


//        designTabLayout.setupWithViewPager(viewPager);

        View view = simplePagerTabLayout.getChildAt(0);
        if (view instanceof TextView) {//默认选中的tab设置粗体
            ((TextView) view).setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Badge badge = new QBadgeView(this).bindTarget(view);
//            badge.setBadgeTextColor(Color.YELLOW);
            badge.setBadgeGravity(Gravity.TOP | Gravity.END);
            badge.setBadgeTextSize(12, true);
//            badge.setBadgeBackgroundColor(Color.BLUE);
            badge.setGravityOffset(10, 0, true);
            badge.setBadgeNumber(22);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int count = simplePagerTabLayout.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = simplePagerTabLayout.getChildAt(i);

                    if (position == i) {
                        if (view instanceof TextView) {
//                            ((TextView) view).getPaint().setFakeBoldText(true);
                            ((TextView) view).setTypeface(Typeface.DEFAULT, Typeface.BOLD);

                        }
                    } else {
                        if (view instanceof TextView) {
//                            ((TextView) view).getPaint().setFakeBoldText(false);
                            ((TextView) view).setTypeface(Typeface.DEFAULT, Typeface.NORMAL);

                        }
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        LevelListDrawable levelListDrawable = (LevelListDrawable) getResources().getDrawable(
//                R.drawable.challenge_level_progress_drawable);
//        levelListDrawable.setLevel(10);
        LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.progress);
        progressBar.setProgressDrawable(layerDrawable);
        progressBar.setProgress(50);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        Log.e("MainActivity", "onCreate");
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setUpTab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.spanTest:
                Intent intent = new Intent(this, SpanTestActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpTab() {
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            TabLayout.Tab tab           = designTabLayout.newTab();
            View          view          = View.inflate(this, R.layout.tab_text, null);
            TextView      pointTextView = (TextView) view.findViewById(android.R.id.text1);
            /**为了兼容yoyochat， 改变以下逻辑*/


            Badge badgeView = new QBadgeView(this).bindTarget(pointTextView);
            badgeView.setBadgeTextSize(12, true);
            badgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
            badgeView.setBadgeBackgroundColor(getResources().getColor(R.color.red_0_5));
            badgeView.setBadgeTextColor(Color.WHITE);
            badgeView.setBadgeNumber(i);


            tab.setCustomView(view).setText(pagerAdapter.getPageTitle(i));
            designTabLayout.addTab(tab, i);
        }

        designTabLayout.setSelectedTabIndicatorColor(Color.YELLOW);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(designTabLayout));
        designTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "onResume");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MainActivity", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("MainActivity", "onRestart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity", "onDestroy");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("MainActivity", "onConfigurationChanged newConfig.orientation: " + newConfig.orientation);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button2) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.fragmentContainer, BlankDialogFragment.newInstance("", ""), "dlg")
                                       .commitNow();
        }
        if (view.getId() == R.id.button) {
            BlankDialogFragment.newInstance("", "").show(getSupportFragmentManager(), "dlg");
        }
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(MainActivity.this);
        findViewById(R.id.button2).setOnClickListener(this);

    }
}
