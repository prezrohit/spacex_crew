package com.prezrohit.spacexcrew.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceClient {
	private static Retrofit retrofit;
	private static final String BASE_URL = "https://api.spacexdata.com/v4/";

	public static Retrofit getRetrofit() {
		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
					.baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}

		return retrofit;
	}
}
