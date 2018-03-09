package com.noonEdu.nAnalytics.analytics;

import android.content.Context;

import com.noonEdu.nAnalytics.commons.LogUtils;
import com.noonEdu.nAnalytics.commons.Utils;
import com.noonEdu.nAnalytics.data.Event;
import com.noonEdu.nAnalytics.db.EventDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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

    private void sendEventToServer(final HashMap<String, Object> map) {
        LogUtils.printLog(TAG, "sendEventToServer");
        EventDatabase eventDatabase = EventDatabase.getInstance(getContext());
        List<Event> events = eventDatabase.getEventDao().getAllEvents();
        if (events.isEmpty()) {
            //empty db directly make api call
            //if success do nothing, if fail add map to db.
            //testing
            if (true) {
                apiFailDbEmpty(map);
            }
        } else {
            //delete all from db add new map to list and make api call
            //if success do nothing else just add all back to db.
            //testing
            eventDatabase.getEventDao().deleteAll();

            if (true) {
                events.add(new Event(Utils.mapToString(map)));
                apiFailDbNotEmpty(getEventsMapList(events));
            }
        }
    }

    private void apiFailDbEmpty(HashMap<String, Object> map) {
        if (map == null) {
            return;
        }
        EventDatabase.getInstance(getContext())
                .getEventDao()
                .insert(new Event(Utils.mapToString(map)));
        LogUtils.printLog(TAG, "apiFailDbEmpty");
    }

    private void apiFailDbNotEmpty(List<HashMap<String, Object>> maps) {
        if (maps == null || maps.isEmpty()) {
            return;
        }
        Event[] events = new Event[maps.size()];
        for (int i = 0; i < maps.size(); i++) {
            HashMap<String, Object> map = maps.get(i);
            events[i] = new Event(Utils.mapToString(map));
        }
        EventDatabase.getInstance(getContext())
                .getEventDao()
                .insert(events);
        LogUtils.printLog(TAG, "apiFailDbNotEmpty");
    }

    private List<HashMap<String, Object>> getEventsMapList(List<Event> events) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        if (events == null || events.isEmpty()) {
            return list;
        }
        for (Event event : events) {
            list.add(Utils.stringToMap(event.getEventString()));
        }
        return list;
    }
}