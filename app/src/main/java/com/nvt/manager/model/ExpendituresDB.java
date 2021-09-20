package com.nvt.manager.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expenditures.class}, version = 1)
public abstract class ExpendituresDB extends RoomDatabase {
    public static ExpendituresDB expenditures;

    public static ExpendituresDB getExpendituresDB(Context context) {
        if (expenditures == null) {
            expenditures = Room.databaseBuilder(context, ExpendituresDB.class, "expenditures_db").build();

        }
        return expenditures;
    }

    public abstract ExpendituresDao getExpendituresDao();

    public void destroyDB() {
        expenditures = null;
    }

}
