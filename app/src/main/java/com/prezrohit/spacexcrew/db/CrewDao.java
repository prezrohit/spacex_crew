package com.prezrohit.spacexcrew.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CrewDao {
	@Query("SELECT * FROM crew")
	List<CrewEntity> getCrewList();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void saveCrewList(List<CrewEntity> crewList);

}
