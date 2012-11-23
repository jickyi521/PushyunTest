package com.pushyun.test;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.augmentum.pushyun.PushGlobals;
import com.augmentum.pushyun.service.PushA2DMService;
import com.augmentum.pushyun.service.PushService;

public class MainActivity extends Activity {

    TextView mDisplay;
    AsyncTask<Void, Void, Void> mRegisterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDisplay = (TextView)findViewById(R.id.display);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(PushGlobals.DISPLAY_MESSAGE_ACTION));

        startPushService();
        //sendRegisterBroadcast("Test");
    }
    
    private void startPushService()
    {
        Intent intent = new Intent();
        intent.putExtra("app_key", PushGlobals.SENDER_ID);
        intent.putExtra("app_service_path", "com.pushyun.test.PushMsgIntentService");
        intent.putExtra("gcm_enabled", false);
        PushService.startToLoad(this, intent);
    }
    
    @Override
    protected void onDestroy()
    {
        if (mRegisterTask != null)
        {
            mRegisterTask.cancel(true);
        }
        unregisterReceiver(mHandleMessageReceiver);
        super.onDestroy();
    }

    /**
     * Handle receiver message.
     */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String msg = intent.getExtras().getString(PushGlobals.EXTRA_MESSAGE);
            mDisplay.append(msg + "\n");
        }
    };
}
