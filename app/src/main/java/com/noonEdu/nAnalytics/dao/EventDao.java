package com.noonEdu.nAnalytics.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.noonEdu.nAnalytics.data.Event;

import java.util.ArrayList;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

@Dao
public interface EventDao {

    @Query("Select * from event") ArrayList<Event> getAllEvents();

    @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(Event... events);

    @Update void update(Event... events);

    @Delete void delete(Event... events);
}
