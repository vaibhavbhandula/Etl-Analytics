package com.noonEdu.nAnalytics.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.noonEdu.nAnalytics.dao.EventDao;
import com.noonEdu.nAnalytics.data.Event;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

@Database(entities = {Event.class}, version = 1)
public abstract class EventDatabase extends RoomDatabase {

    private static final String DB_NAME = "eventDatabase.db";
    private static volatile EventDatabase instance;

    public static synchronized EventDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static EventDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                EventDatabase.class,
                DB_NAME).build();
    }

    public abstract EventDao getEventDao();
}
