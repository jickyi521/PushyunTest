package com.pushyun.test;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.augmentum.pushyun.PushGlobals;
import com.augmentum.pushyun.service.MsgHandlerIntentService;

public class PushMsgIntentService extends MsgHandlerIntentService
{

    private static final String LOG_TAG = "PushMsgIntentService";
    private static String mMsg = "";
    public static Long mCounter = 0L;

    @Override
    protected void onMessageDelivered(Context context, HashMap<String, String> msgMap)
    {
        mMsg = "***** onMessageDelivered *****message=" +msgMap.get("message");
        mCounter ++ ;
        PushGlobals.sendPushBroadcast(context, PushGlobals.DISPLAY_MESSAGE_ACTION, mMsg);
        Log.v(LOG_TAG, mMsg);

    }

    @Override
    protected void onError(Context context, String error)
    {
        mMsg = "***** onError *****" +error; 
        PushGlobals.sendPushBroadcast(context, PushGlobals.DISPLAY_MESSAGE_ACTION, mMsg);
        Log.v(LOG_TAG, mMsg);
    }

    @Override
    protected void onRegistered(Context context, String regId, boolean inGCM)
    {
        PushGlobals.sendPushBroadcast(context, PushGlobals.DISPLAY_MESSAGE_ACTION, "Registration Id = " +regId);
        Log.v(LOG_TAG, "Register in GCM" + inGCM + "***** onRegistered *****" + regId);
    }

}
