package com.noonEdu.nAnalytics.analytics;

import android.content.Context;
import android.os.Handler;

import com.noonEdu.nAnalytics.data.Event;
import com.noonEdu.nAnalytics.db.EventDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

public class NAnalytics {

    private static final String TAG = NAnalytics.class.getSimpleName();

    private static NAnalytics nAnalytics;
    private static WeakReference<Context> contextWeakReference;

    private int userId = 0;

    private NAnalytics(int userId) {
        this.userId = userId;
    }

    public static NAnalytics getInstance(Context context, int userId) {
        contextWeakReference = new WeakReference<>(context);
        if (nAnalytics == null) {
            nAnalytics = new NAnalytics(userId);
        }
        return nAnalytics;
    }

    private Context getContext() {
        return contextWeakReference.get();
    }

    public void logEvent(String event, HashMap<String, Object> map) {
        if (map == null) {
            return;
        }
        map.put("eventType", event);
        map.put("source", "Android");
        map.put("userID", userId);
        sendEventToServer(map);
    }

    private void sendEventToServer(HashMap<String, Object> map) {
        new Handler().post(new Runnable() {
            @Override public void run() {
                ArrayList<Event> events = EventDatabase.
                        getInstance(getContext())
                        .getRepoDao()
                        .getAllEvents();
                if (events.isEmpty()) {
                    //empty db directly make api call
                    //if success do nothing, if fail add map to db.
                } else {
                    //convert all element in list to hash map and make api call.
                    //if success clear db else just add new map to db.
                }
            }
        });
    }
}
