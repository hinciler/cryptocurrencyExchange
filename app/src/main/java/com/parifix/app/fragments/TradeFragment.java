package com.parifix.app.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parifix.app.R;
import com.parifix.app.adapter.MyPagerAdapter;
import com.parifix.app.model.MarketChangeEventBusHelperModel;
import com.parifix.app.util.CustomPager;
import com.parifix.app.util.NonSwipeableViewPagerWrapContent;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.parifix.app.AppConstants.SELECTED_COIN_CODE;
import static com.parifix.app.AppConstants.SELECTED_COIN_COUNTER_CODE;


public class TradeFragment extends Fragment {

    PagerAdapter adapter;
    MyPagerAdapter adapterBottom;
    View rootView;

    @BindView(R.id.selected_market_name_txt)
    TextView selectedMarketNameTxt;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_layout_bottom)
    TabLayout tabLayoutBottom;
    @BindView(R.id.pager)
    NonSwipeableViewPagerWrapContent pager;
    @BindView(R.id.pager_bottom)
    CustomPager pagerBottom;

    Unbinder unbinder;

    public static TradeFragment newInstance() {

        return new TradeFragment();
    }

    class MarketChange implements Runnable {
        MarketChange() {
        }

        public void run() {
            TextView textView = TradeFragment.this.selectedMarketNameTxt;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Prefs.getString(SELECTED_COIN_CODE, "BTC"));
            stringBuilder.append("/");
            stringBuilder.append(Prefs.getString(SELECTED_COIN_COUNTER_CODE, "LTC"));
            Log.v("SELECTED_COIN", Prefs.getString(SELECTED_COIN_COUNTER_CODE, "LTC"));
            textView.setText(stringBuilder.toString());
        }
    }

    class TabListener implements TabLayout.OnTabSelectedListener {
        public void onTabReselected(TabLayout.Tab tab) {
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        TabListener() {
        }

        public void onTabSelected(TabLayout.Tab tab) {
            TradeFragment.this.pager.setCurrentItem(tab.getPosition());
        }
    }

    class BottomTabListener implements TabLayout.OnTabSelectedListener {
        public void onTabReselected(TabLayout.Tab tab) {
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        BottomTabListener() {
        }

        public void onTabSelected(TabLayout.Tab tab) {
            TradeFragment.this.pagerBottom.setCurrentItem(tab.getPosition());
        }
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fragmentManager, int i) {
            super(fragmentManager);
            this.mNumOfTabs = i;
        }

        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new BuyFragment();
                case 1:
                    return new BuyFragment();
                default:
                    return null;
            }
        }

        public int getCount() {
            return this.mNumOfTabs;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_trade, container, false);
        this.unbinder = ButterKnife.bind(this, this.rootView);

        initViews();
        // Inflate the layout for this fragment
        return this.rootView;
    }

    @Subscribe
    public void onEvent(MarketChangeEventBusHelperModel marketChangeEventBusHelperModel) {
        getActivity().runOnUiThread(new MarketChange());
    }


    private void initViews() {
        TextView textView = this.selectedMarketNameTxt;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Prefs.getString(SELECTED_COIN_CODE, "BTC"));
        stringBuilder.append("/");
        stringBuilder.append(Prefs.getString(SELECTED_COIN_COUNTER_CODE, "TRY"));
        textView.setText(stringBuilder.toString());
        initBottomViewPager();
        initBuyAndSellFragments();
    }


    private void initBuyAndSellFragments() {
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Limit"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText("Piyasa"));
        this.adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), this.tabLayout.getTabCount());
        this.pager.setAdapter(this.adapter);
        this.pager.setOffscreenPageLimit(2);
        this.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
        this.tabLayout.addOnTabSelectedListener(new TabListener());
    }

    private void initBottomViewPager() {
        this.tabLayoutBottom.addTab(this.tabLayoutBottom.newTab().setText("Emirler"));
        this.tabLayoutBottom.addTab(this.tabLayoutBottom.newTab().setText("Son Islemler"));
        this.tabLayoutBottom.addTab(this.tabLayoutBottom.newTab().setText("Acik Emirler"));
        this.adapterBottom = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        this.pagerBottom.setAdapter(this.adapterBottom);
        this.pagerBottom.setOffscreenPageLimit(3);
        this.pagerBottom.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayoutBottom));
        this.tabLayoutBottom.addOnTabSelectedListener(new BottomTabListener());
    }

    @Override
    public void onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }


}
