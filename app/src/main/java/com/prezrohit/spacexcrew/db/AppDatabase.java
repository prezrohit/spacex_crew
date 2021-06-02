package com.prezrohit.spacexcrew.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CrewEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
	private static volatile AppDatabase instance;
	private static final int NUMBER_OF_THREADS = 4;
	public static final ExecutorService databaseWriteExecutor =
			Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public static AppDatabase getInstance(final Context context) {
		if (instance == null)
			synchronized (AppDatabase.class) {
				if (instance == null)
					instance = Room.databaseBuilder(context,
							AppDatabase.class,
							"spacex_crew")
							.build();
			}
		return instance;
	}

	public abstract CrewDao crewDao();
}
