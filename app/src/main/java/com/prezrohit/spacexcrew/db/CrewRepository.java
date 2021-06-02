package com.prezrohit.spacexcrew.db;

import android.content.Context;

import java.util.List;

public class CrewRepository {
	private final CrewDao crewDao;
	private final AppDatabase appDatabase;
	private List<CrewEntity> entityList;

	public CrewRepository(Context context) {
		appDatabase = AppDatabase.getInstance(context);
		crewDao = appDatabase.crewDao();
		AppDatabase.databaseWriteExecutor.execute(() -> {
			entityList = crewDao.getCrewList();
		});
	}

	public List<CrewEntity> getSavedCrewList() {
		return entityList;
	}

	public void saveCrewData(List<CrewEntity> entityList) {
		AppDatabase.databaseWriteExecutor.execute(() -> {
			crewDao.saveCrewList(entityList);
		});
	}

	public void deleteSavedData() {
		AppDatabase.databaseWriteExecutor.execute(appDatabase::clearAllTables);
	}

}
