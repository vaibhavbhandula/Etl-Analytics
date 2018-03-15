package com.noonEdu.nAnalytics;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.noonEdu.nAnalytics.analytics.NAnalytics;
import com.noonEdu.nAnalytics.commons.Utils;
import com.noonEdu.nAnalytics.data.Event;
import com.noonEdu.nAnalytics.db.EventDatabase;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

/**
 * @author Vaibhav Bhandula on 13/03/18.
 */

@RunWith(AndroidJUnit4.class)
public class AnalyticsApiTest {

    private Context context;

    //TODO delete this test class before push to gradle or maven
    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getContext();
        NAnalytics.initialize(context, "https://hyyhuwu318.execute-api.us-east-2.amazonaws.com/v1/event",
                NAnalytics.RequestType.POST);
        EventDatabase.getInstance(context)
                .getEventDao()
                .deleteAll();
    }

    @Test
    public void eventTestDbNotEmpty() throws Exception {
        insertEvent();
        insertEvent();
        insertEvent();
        NAnalytics analytics = NAnalytics.getInstance();
        JSONObject dataObject = new JSONObject();
        dataObject.put("user_id", 2);
        dataObject.put("flashcard_image_id", 13);
        dataObject.put("event", "FlashCardOut");
        analytics.logEvent("records", dataObject.toString());
        Thread.sleep(2000);
    }

    @Test
    public void eventTestDbEmpty() throws Exception {
        NAnalytics analytics = NAnalytics.getInstance();
        JSONObject dataObject = new JSONObject();
        dataObject.put("user_id", 2);
        dataObject.put("flashcard_image_id", 13);
        dataObject.put("event", "FlashCardOut");
        analytics.logEvent("records", dataObject.toString());
        Thread.sleep(2000);
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
