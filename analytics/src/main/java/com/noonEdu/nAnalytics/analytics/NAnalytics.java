package com.noonEdu.nAnalytics.analytics;

import android.content.Context;
import android.os.Handler;

import com.noonEdu.nAnalytics.data.Event;
import com.noonEdu.nAnalytics.db.EventDatabase;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

public class NAnalytics {

    private static final String TAG = NAnalytics.class.getSimpleName();

    private static NAnalytics nAnalytics;
    private static WeakReference<Context> contextWeakReference;

    private int userId = 0;

    public static void initialize(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    public static NAnalytics getInstance() {
        if (nAnalytics == null) {
            nAnalytics = new NAnalytics();
        }
        return nAnalytics;
    }

    private Context getContext() {
        return contextWeakReference.get();
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
                List<Event> events = EventDatabase.
                        getInstance(getContext())
                        .getEventDao()
                        .getAllEvents();
                if (events.isEmpty()) {
                    //empty db directly make api call
                    //if success do nothing, if fail add map to db.
                } else {
                    //delete all from db add new map to list and make api call
                    //if success do nothing else just add all back to db.
                }
            }
        });
    }
}
