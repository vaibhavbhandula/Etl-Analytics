package com.noonEdu.nAnalytics;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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
 * @author Vaibhav Bhandula on 06/03/18.
 */

@RunWith(AndroidJUnit4.class)
public class DBOperationsTest {

    private Context context;
    private HashMap<String, Object> map = new HashMap<>();
    private Event event;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getContext();
        map.put("test", 1);
        map.put("test again", 0.00f);
        map.put("hello test", "yes test");
        event = new Event(Utils.mapToString(map));
        EventDatabase.getInstance(context)
                .getEventDao()
                .deleteAll();
    }

    @Test
    public void insertionTest() throws Exception {
        EventDatabase.getInstance(context)
                .getEventDao()
                .insert(event);
        List<Event> events = EventDatabase.getInstance(context)
                .getEventDao()
                .getAllEvents();
        Assert.assertEquals(1, events.size());
    }

    @Test
    public void deletionTest() throws Exception {
        insertionTest();
        EventDatabase.getInstance(context)
                .getEventDao()
                .delete(event);
        List<Event> events = EventDatabase.getInstance(context)
                .getEventDao()
                .getAllEvents();
        Assert.assertEquals(0, events.size());
    }

}
