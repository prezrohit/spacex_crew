package com.prezrohit.spacexcrew.webservice;

import androidx.annotation.NonNull;

import com.prezrohit.spacexcrew.db.CrewEntity;

import java.util.List;

public class CrewResponse {
	private String id;
	private String name;
	private String image;
	private String status;
	private String agency;
	private String wikipedia;
	private List<String> launches;

	public CrewResponse() {
	}

	public static CrewResponse fromCrewEntity(CrewEntity crewEntity) {
		CrewResponse crewResponse = new CrewResponse();
		crewResponse.name = crewEntity.getName();
		crewResponse.agency = crewEntity.getAgency();
		crewResponse.image = crewEntity.getImage();
		crewResponse.status = crewEntity.getStatus();
		return crewResponse;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getWikipedia() {
		return wikipedia;
	}

	public void setWikipedia(String wikipedia) {
		this.wikipedia = wikipedia;
	}

	public List<String> getLaunches() {
		return launches;
	}

	public void setLaunches(List<String> launches) {
		this.launches = launches;
	}

	@NonNull
	@Override
	public String toString() {
		return "CrewResponse{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", image='" + image + '\'' +
				", status='" + status + '\'' +
				", agency='" + agency + '\'' +
				", wikipedia='" + wikipedia + '\'' +
				", launches=" + launches +
				'}';
	}
}
