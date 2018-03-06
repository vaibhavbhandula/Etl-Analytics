package com.noonEdu.nAnalytics.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

@Entity
public class Event {

    private static final AtomicInteger count = new AtomicInteger(0);

    @PrimaryKey private int id;

    @NonNull private String eventString = "";

    public Event(@NonNull String eventString) {
        this.eventString = eventString;
        this.id = count.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    @NonNull public String getEventString() {
        return eventString;
    }

    public void setId(int id) {
        this.id = id;
    }
}
