package com.parifix.app.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.TextView;

import com.parifix.app.MyApplication;
import com.parifix.app.R;
import com.parifix.app.fragments.AccountFragment;
import com.parifix.app.fragments.LastTransactionsFragment;
import com.parifix.app.fragments.SettingsFragment;
import com.parifix.app.fragments.TradeFragment;
import com.parifix.app.helper.ConnectWebSocketHelper;
import com.parifix.app.model.MarketChangeEventBusHelperModel;
import com.parifix.app.model.MarketOrderEventBusModel;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

import static com.parifix.app.AppConstants.SELECTED_COIN_CODE;
import static com.parifix.app.AppConstants.SELECTED_COIN_COUNTER_CODE;

public class MainActivity extends BaseActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_trade:
                    fragment = TradeFragment.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_account:
                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_transactions:
                    fragment = new LastTransactionsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_settings:
                    fragment = new SettingsFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        EventBus.getDefault().post(new MarketOrderEventBusModel( ConnectWebSocketHelper.connectWebSocket(this)));
        ConnectWebSocketHelper.connectWebSocket(this);
//        EventBus.getDefault().post(new MarketOrderEventBusModel( ConnectWebSocketHelper.connectWebSocket(this)));

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_trade); // change to whichever id should be default
        }

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onResume() {

        EventBus.getDefault().post(new MarketChangeEventBusHelperModel(Prefs.getString(SELECTED_COIN_CODE, "BTC")));
        EventBus.getDefault().post(new MarketChangeEventBusHelperModel(Prefs.getString(SELECTED_COIN_COUNTER_CODE, "LTC")));

        super.onResume();
    }


}
