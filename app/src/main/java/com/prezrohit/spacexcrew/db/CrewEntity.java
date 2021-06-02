package com.prezrohit.spacexcrew.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.prezrohit.spacexcrew.webservice.CrewResponse;

@Entity(tableName = "crew")
public class CrewEntity {

	@NonNull
	@PrimaryKey
	private String id;
	private String name;
	private String agency;
	private String status;
	private String image;

	public CrewEntity(@NonNull String id, String name, String agency, String status, String image) {
		this.id = id;
		this.name = name;
		this.agency = agency;
		this.status = status;
		this.image = image;
	}

	public static CrewEntity fromCrewResponse(CrewResponse response) {
		return new CrewEntity(response.getId(), response.getName(), response.getAgency(), response.getStatus(), response.getImage());
	}

	@NonNull
	public String getId() {
		return id;
	}

	public void setId(@NonNull String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
