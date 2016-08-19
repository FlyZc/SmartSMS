package com.example.zhouchi.smartsms;

import android.content.Intent;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.zhouchi.smartsms.adapter.ViewPagerAdapter;
import com.example.zhouchi.smartsms.base.BaseActivity;
import com.example.zhouchi.smartsms.ui.fragment.ConversationFragment;
import com.example.zhouchi.smartsms.ui.fragment.GroupFragment;
import com.example.zhouchi.smartsms.ui.fragment.SearchFragment;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager vp;
    private List<Fragment> fragmentList;
    private TextView tvConservation;
    private TextView tvGroup;
    private TextView tvSearch;
    private LinearLayout llConservation;
    private LinearLayout llGroup;
    private LinearLayout llSearch;
    private View vReadLine;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);

        System.out.println("测试");
        //拿到布局文件中的组件
        vp = (ViewPager)findViewById(R.id.vp);
        tvConservation = (TextView) findViewById(R.id.tvConversation);
        tvGroup = (TextView) findViewById(R.id.tvGroup);
        tvSearch = (TextView) findViewById(R.id.tvSearch);
        llConservation = (LinearLayout)findViewById(R.id.llConservation);
        llGroup = (LinearLayout)findViewById(R.id.llGroup);
        llSearch = (LinearLayout)findViewById(R.id.llSearch);
        vReadLine = (View)findViewById(R.id.vRedLine);

    }

    @Override
    public void initListener() {
        //viewpager界面切换时触发
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int distance = positionOffsetPixels / 3;
                ViewPropertyAnimator.animate(vReadLine).translationX(distance + position * vReadLine.getWidth()).setDuration(0);
            }

            @Override
            public void onPageSelected(int position) {
                textLightAndScale();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        llConservation.setOnClickListener(this);
        llGroup.setOnClickListener(this);
        llSearch.setOnClickListener(this);
    }

    @Override
    public void initData() {
        fragmentList = new ArrayList<Fragment>();
        //创建Fragment并存入集合
        ConversationFragment cf = new ConversationFragment();
        GroupFragment gf = new GroupFragment();
        SearchFragment sf = new SearchFragment();
        fragmentList.add(cf);
        fragmentList.add(gf);
        fragmentList.add(sf);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        vp.setAdapter(adapter);
        textLightAndScale();
        coputeIndiacateLinewWidth();


    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.llConservation:
                vp.setCurrentItem(0);
                break;
            case R.id.llGroup:
                vp.setCurrentItem(1);
                break;
            case R.id.llSearch:
                vp.setCurrentItem(2);
                break;
        }
    }

    //改变选项卡的颜色和大小
    private void textLightAndScale() {
        int itemNum = vp.getCurrentItem();
        tvConservation.setTextColor(itemNum == 0 ? 0xFFFFFFFF : 0xaa666666);
        tvGroup.setTextColor(itemNum == 1 ? 0xFFFFFFFF : 0xaa666666);
        tvSearch.setTextColor(itemNum == 2 ? 0xFFFFFFFF : 0xaa666666);
        ViewPropertyAnimator.animate(tvConservation).scaleX(itemNum == 0 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tvConservation).scaleY(itemNum == 0 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tvGroup).scaleX(itemNum == 1 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tvGroup).scaleY(itemNum == 1 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tvSearch).scaleX(itemNum == 2 ? 1.2f : 1).setDuration(200);
        ViewPropertyAnimator.animate(tvSearch).scaleY(itemNum == 2 ? 1.2f : 1).setDuration(200);
    }

    private void coputeIndiacateLinewWidth() {
        int width = getWindowManager().getDefaultDisplay().getWidth();
        vReadLine.getLayoutParams().width = width / 3;
    }


}
