package com.parifix.app.helper;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parifix.app.R;
import com.parifix.app.model.MarketChangeEventBusHelperModel;
import com.parifix.app.model.MarketOrderEventBusModel;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import static com.parifix.app.AppConstants.SELECTED_COIN_CODE;

public class ConnectWebSocketHelper {

    private static WebSocketClient mWebSocketClient;
    public static String message;
    public static String connectWebSocket(final Activity activity) {
        URI uri;
        try {
            uri = new URI("ws://185.195.254.26:9004");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("{\"flag\" : \"initMarket\" ,\"params\": {\"Pair\":\"SKKTRY\"}}");
            }

            @Override
            public void onMessage(String s) {
                message = s;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        TextView textView = activity.findViewById(R.id.messages);
                        String newMessage = message.replace("\\", "");
//                        Log.i("Websocket", "OnMessage " + newMessage);
                        output(newMessage, activity);
//                        textView.setText(textView.getText() + "\n" + message);
//                        EventBus.getDefault().post(new MarketOrderEventBusModel(message));
//                        try {
////                            JSONObject reader = new JSONObject(newMessage);
////                            String ordersSell = reader.optString("OrdersSell");
////                            JSONArray txArray = reader.getJSONArray("tx_items");
//
////                            Log.i("Websocket_JsonObj", "OnMessage " + ordersSell);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();

        return message;
    }

    private static void output(final String txt, Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new MarketOrderEventBusModel(txt));
            }
        });
    }

    public void sendMessage(View view) {
        EditText editText = view.findViewById(R.id.message);
        mWebSocketClient.send(editText.getText().toString());
        editText.setText("");
    }
}
