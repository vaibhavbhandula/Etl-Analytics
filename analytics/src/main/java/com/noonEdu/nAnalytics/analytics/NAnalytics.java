package com.noonEdu.nAnalytics.analytics;

import android.content.Context;
import android.text.TextUtils;

import com.noonEdu.nAnalytics.commons.LogUtils;
import com.noonEdu.nAnalytics.data.Event;
import com.noonEdu.nAnalytics.db.EventDatabase;
import com.noonEdu.nAnalytics.exception.UrlEmptyException;
import com.noonEdu.nAnalytics.exception.WrongRequestMethodException;
import com.noonEdu.nAnalytics.network.ApiClient;
import com.noonEdu.nAnalytics.network.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

public class NAnalytics {

    /**
     * TAG for Logging
     */
    private static final String TAG = NAnalytics.class.getSimpleName();

    /**
     * Enum for Request Type
     */
    public enum RequestType {
        POST,
        PUT
    }

    private static NAnalytics nAnalytics;
    private static WeakReference<Context> contextWeakReference;

    private static RequestType type;
    private static String url = "";

    /**
     * Initialize the Library
     *
     * @param context Application Context
     * @param type    Request Type PUT or POST
     * @param url     Url to make request
     * @throws UrlEmptyException if url is empty
     */
    public static void initialize(Context context, String url, RequestType type) throws UrlEmptyException {
        if (TextUtils.isEmpty(url)) {
            throw new UrlEmptyException("Url Empty!");
        }
        contextWeakReference = new WeakReference<>(context);
        NAnalytics.type = type;
        NAnalytics.url = url;
    }

    /**
     * @return current instance
     */
    public static NAnalytics getInstance() {
        if (nAnalytics == null) {
            nAnalytics = new NAnalytics();
        }
        return nAnalytics;
    }

    private Context getContext() {
        return contextWeakReference.get();
    }

    /**
     * Method used for sending the event to your server
     *
     * @param key      Key on which array of records will be sent in api
     * @param jsonData JsonObject to send in String format
     * @throws WrongRequestMethodException Exception thrown if request method is not PUT or POST
     * @throws JSONException               JsonException
     */
    public void logEvent(String key, String jsonData) throws WrongRequestMethodException, JSONException {
        if (key == null || key.isEmpty() || jsonData == null || jsonData.isEmpty()) {
            return;
        }
        sendEventToServer(key, jsonData);
    }

    /**
     * This method is used for Testing internally. Do NOT use in code.
     */
    public void logTestEvent(String event) {
        if (event == null || event.isEmpty()) {
            return;
        }
        sendTestEventToServer(event);
    }

    /**
     * private Method used for sending the event to your server
     *
     * @param key      Key on which array of records will be sent in api
     * @param jsonData JsonObject to send in String format
     * @throws WrongRequestMethodException Exception thrown if request method is not PUT or POST
     * @throws JSONException               JsonException
     */
    private void sendEventToServer(String key, String jsonData) throws WrongRequestMethodException, JSONException {
        LogUtils.printLog(TAG, "sendEventToServer");
        EventDatabase eventDatabase = EventDatabase.getInstance(getContext());
        final List<Event> events = eventDatabase.getEventDao().getAllEvents();
        if (events.isEmpty()) {
            //empty db directly make api call
            //if success do nothing, if fail add map to db.
            if (type == RequestType.POST) {
                sendPostCallDbEmpty(key, jsonData);
            } else if (type == RequestType.PUT) {
                sendPutCallDbEmpty(key, jsonData);
            } else {
                throw new WrongRequestMethodException("Wrong Request Method!");
            }
        } else {
            //delete all from db add new map to list and make api call
            //if success do nothing else just add all back to db.
            eventDatabase.getEventDao().deleteAll();
            if (type == RequestType.POST) {
                sendPostCallDbNotEmpty(key, jsonData, events);
            } else if (type == RequestType.PUT) {
                sendPutCallDbNotEmpty(key, jsonData, events);
            } else {
                throw new WrongRequestMethodException("Wrong Request Method!");
            }
        }
    }

    /**
     * Test Method used for internal testing
     */
    private void sendTestEventToServer(String jsonData) {
        LogUtils.printLog(TAG, "sendTestEventToServer");
        EventDatabase eventDatabase = EventDatabase.getInstance(getContext());
        List<Event> events = eventDatabase.getEventDao().getAllEvents();
        if (events.isEmpty()) {
            //empty db directly make api call
            //if success do nothing, if fail add map to db.
            //testing
            if (true) {
                apiFailDbEmpty(jsonData);
            }
        } else {
            //delete all from db add new map to list and make api call
            //if success do nothing else just add all back to db.
            //testing
            eventDatabase.getEventDao().deleteAll();

            if (true) {
                events.add(new Event(jsonData));
                apiFailDbNotEmpty(events);
            }
        }
    }

    /**
     * Method used for sending the the POST Event to your server in case DB is Empty
     *
     * @param key      Key on which array of records will be sent in api
     * @param jsonData JsonObject to send in String format
     * @throws JSONException JsonException
     */
    private void sendPostCallDbEmpty(String key, final String jsonData) throws JSONException {
        JSONArray array = new JSONArray();
        array.put(jsonData);
        JSONObject object = new JSONObject();
        object.put(key, array);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), object.toString());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiInterface.sendPostEvent(url, body);
        call.enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> call, Response<Void> response) {
                if (!ApiClient.isResponseOk(response.code())) {
                    onFailure(call, new Throwable());
                } else {
                    LogUtils.printLog(TAG, "post api success db empty");
                }
            }

            @Override public void onFailure(Call<Void> call, Throwable t) {
                LogUtils.printLog(TAG, "post api fail db empty");
                apiFailDbEmpty(jsonData);
            }
        });
    }

    /**
     * Method used for sending the the POST Event to your server in case DB is not Empty
     *
     * @param key      Key on which array of records will be sent in api
     * @param jsonData JsonObject to send in String format
     * @param events   List of Events stored in DB
     * @throws JSONException JsonException
     */
    private void sendPostCallDbNotEmpty(String key, final String jsonData, final List<Event> events)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (Event event : events) {
            array.put(event.getEventString());
        }
        array.put(jsonData);
        JSONObject object = new JSONObject();
        object.put(key, array);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), object.toString());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiInterface.sendPostEvent(url, body);
        call.enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> call, Response<Void> response) {
                if (!ApiClient.isResponseOk(response.code())) {
                    onFailure(call, new Throwable());
                } else {
                    LogUtils.printLog(TAG, "post api success db not empty");
                }
            }

            @Override public void onFailure(Call<Void> call, Throwable t) {
                LogUtils.printLog(TAG, "post api fail db not empty");
                events.add(new Event(jsonData));
                apiFailDbNotEmpty(events);
            }
        });
    }

    /**
     * Method used for sending the the PUT Event to your server in case DB is Empty
     *
     * @param key      Key on which array of records will be sent in api
     * @param jsonData JsonObject to send in String format
     * @throws JSONException JsonException
     */
    private void sendPutCallDbEmpty(String key, final String jsonData) throws JSONException {
        JSONArray array = new JSONArray();
        array.put(jsonData);
        JSONObject object = new JSONObject();
        object.put(key, array);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), object.toString());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiInterface.sendPutEvent(url, body);
        call.enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> call, Response<Void> response) {
                if (!ApiClient.isResponseOk(response.code())) {
                    onFailure(call, new Throwable());
                } else {
                    LogUtils.printLog(TAG, "put api success db empty");
                }
            }

            @Override public void onFailure(Call<Void> call, Throwable t) {
                LogUtils.printLog(TAG, "put api fail db empty");
                apiFailDbEmpty(jsonData);
            }
        });
    }

    /**
     * Method used for sending the the PUT Event to your server in case DB is not Empty
     *
     * @param key      Key on which array of records will be sent in api
     * @param jsonData JsonObject to send in String format
     * @param events   List of Events stored in DB
     * @throws JSONException JsonException
     */
    private void sendPutCallDbNotEmpty(String key, final String jsonData, final List<Event> events)
            throws JSONException {
        JSONArray array = new JSONArray();
        for (Event event : events) {
            array.put(event.getEventString());
        }
        array.put(jsonData);
        JSONObject object = new JSONObject();
        object.put(key, array);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), object.toString());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiInterface.sendPutEvent(url, body);
        call.enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> call, Response<Void> response) {
                if (!ApiClient.isResponseOk(response.code())) {
                    onFailure(call, new Throwable());
                } else {
                    LogUtils.printLog(TAG, "put api success db not empty");
                }
            }

            @Override public void onFailure(Call<Void> call, Throwable t) {
                LogUtils.printLog(TAG, "put api fail db not empty");
                events.add(new Event(jsonData));
                apiFailDbNotEmpty(events);
            }
        });
    }

    /**
     * Method used for inserting an event to DB
     *
     * @param event Event String
     */
    private void apiFailDbEmpty(String event) {
        if (event == null || event.isEmpty()) {
            return;
        }
        EventDatabase.getInstance(getContext())
                .getEventDao()
                .insert(new Event(event));
        LogUtils.printLog(TAG, "apiFailDbEmpty");
    }

    /**
     * Method used for inserting events to DB
     *
     * @param eventList List of Events
     */
    private void apiFailDbNotEmpty(List<Event> eventList) {
        if (eventList == null || eventList.isEmpty()) {
            return;
        }
        Event[] events = new Event[eventList.size()];
        for (int i = 0; i < eventList.size(); i++) {
            events[i] = eventList.get(i);
        }
        EventDatabase.getInstance(getContext())
                .getEventDao()
                .insert(events);
        LogUtils.printLog(TAG, "apiFailDbNotEmpty");
    }
}
