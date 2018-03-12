package com.noonEdu.nAnalytics;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.noonEdu.nAnalytics.analytics.NAnalytics;
import com.noonEdu.nAnalytics.commons.Utils;
import com.noonEdu.nAnalytics.data.Event;
import com.noonEdu.nAnalytics.db.EventDatabase;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

/**
 * @author Vaibhav Bhandula on 09/03/18.
 */

@RunWith(AndroidJUnit4.class)
public class AnalyticsTest {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getContext();
        NAnalytics.initialize(context, "", "", true);
        NAnalytics.getInstance().setUserId(1);
        EventDatabase.getInstance(context)
                .getEventDao()
                .deleteAll();
    }

    @Test
    public void analyticsTestDbNotEmpty() throws Exception {
        insertEvent();
        insertEvent();
        List<Event> events = EventDatabase.getInstance(context)
                .getEventDao()
                .getAllEvents();
        HashMap<String, Object> map = new HashMap<>();
        map.put("test", 1);
        map.put("value", 2);
        map.put("test-value", "testing");
        NAnalytics nAnalytics = NAnalytics.getInstance();
        nAnalytics.logTestEvent("test", map);
        List<Event> afterEvents = EventDatabase.getInstance(context)
                .getEventDao()
                .getAllEvents();
        Assert.assertEquals(events.size() + 1, afterEvents.size());
    }

    @Test
    public void analyticsTestDbEmpty() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("test", 1);
        map.put("value", 2);
        map.put("test-value", "testing");
        NAnalytics nAnalytics = NAnalytics.getInstance();
        nAnalytics.logTestEvent("test", map);
        List<Event> events = EventDatabase.getInstance(context)
                .getEventDao()
                .getAllEvents();
        Assert.assertEquals(1, events.size());
    }

    private void insertEvent() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("test", 1);
        map.put("test again", 0.00f);
        map.put("hello test", "yes test");
        EventDatabase.getInstance(context)
                .getEventDao()
                .insert(new Event(Utils.mapToString(map)));
    }
}
