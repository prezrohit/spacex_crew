package com.prezrohit.spacexcrew.webservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {
	@GET("crew")
	Call<List<CrewResponse>> getCrewMembers();

	// TODO define other endpoints
}
