package com.parifix.app.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.parifix.app.R;
import com.parifix.app.helper.ConnectWebSocketHelper;
import com.parifix.app.model.MarketChangeEventBusHelperModel;
import com.parifix.app.model.MarketOrderEventBusModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.net.URI;
import java.net.URISyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.parifix.app.AppConstants.SELECTED_COIN_CODE;
import static com.parifix.app.AppConstants.SELECTED_COIN_COUNTER_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends Fragment {


    private View rootView;

    public BuyFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_buy, container, false);
        String jsonStr;

//        jsonStr =  ConnectWebSocketHelper.connectWebSocket(getActivity());
//        Log.v("txArray", jsonStr);
//        try {
////            JSONObject reader = new JSONObject(jsonStr);
////            String txArray = reader.optString("StatsPiyasa");
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return rootView;
    }

    @Subscribe
    public void onEvent(MarketOrderEventBusModel marketOrderEventBusModel) {

//        ConnectWebSocketHelper.connectWebSocket(getActivity());
        Log.v("market_order",marketOrderEventBusModel.getMarketOrder() );
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






}
