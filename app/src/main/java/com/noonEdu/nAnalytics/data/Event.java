package com.noonEdu.nAnalytics.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

@Entity
public class Event {

    @PrimaryKey(autoGenerate = true) private int id;

    @NonNull private String eventString = "";

    public Event(@NonNull String eventString) {
        this.eventString = eventString;
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
