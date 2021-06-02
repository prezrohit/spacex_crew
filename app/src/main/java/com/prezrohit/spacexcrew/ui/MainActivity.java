package com.prezrohit.spacexcrew.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.prezrohit.spacexcrew.R;
import com.prezrohit.spacexcrew.databinding.ActivityMainBinding;
import com.prezrohit.spacexcrew.db.CrewEntity;
import com.prezrohit.spacexcrew.db.CrewRepository;
import com.prezrohit.spacexcrew.webservice.CrewResponse;
import com.prezrohit.spacexcrew.webservice.WebService;
import com.prezrohit.spacexcrew.webservice.WebServiceClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

	private CrewRepository repository;
	private ActivityMainBinding binding;
	private ProgressDialog progressDialog;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Loading...");
		progressDialog.setMessage("Please Wait");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);

		binding.rvCrewMembers.setLayoutManager(new GridLayoutManager(this, 2));
		binding.rvCrewMembers.setHasFixedSize(true);

		repository = new CrewRepository(this);

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

		if (isConnected) loadFromWebService();
		else {
			Toast.makeText(this, "There is no internet connection. Loading data from saved database", Toast.LENGTH_LONG).show();
			loadFromLocalDb();
		}
	}

	private void loadFromWebService() {
		progressDialog.show();
		WebService webService = WebServiceClient.getRetrofit().create(WebService.class);
		webService.getCrewMembers().enqueue(new Callback<List<CrewResponse>>() {
			@Override
			public void onResponse(@NonNull Call<List<CrewResponse>> call, @NonNull Response<List<CrewResponse>> response) {
				progressDialog.dismiss();
				if (response.body() == null || response.body().isEmpty()) {
					binding.rvCrewMembers.setVisibility(View.GONE);
					binding.errorMessage.setVisibility(View.VISIBLE);
					return;
				}

				binding.rvCrewMembers.setVisibility(View.VISIBLE);
				binding.errorMessage.setVisibility(View.GONE);

				CrewAdapter adapter = new CrewAdapter(getApplicationContext(), response.body());
				binding.rvCrewMembers.setAdapter(adapter);

				List<CrewEntity> entityList = new ArrayList<>();
				for (CrewResponse crewResponse : response.body())
					entityList.add(CrewEntity.fromCrewResponse(crewResponse));
				repository.saveCrewData(entityList);

			}

			@Override
			public void onFailure(@NonNull Call<List<CrewResponse>> call, @NonNull Throwable t) {
				progressDialog.dismiss();
				Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
				Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void loadFromLocalDb() {
		List<CrewEntity> savedCrewList = repository.getSavedCrewList();
		if (savedCrewList == null || savedCrewList.isEmpty()) {
			binding.errorMessage.setVisibility(View.VISIBLE);
			binding.rvCrewMembers.setVisibility(View.GONE);
			return;
		}

		binding.rvCrewMembers.setVisibility(View.VISIBLE);
		binding.errorMessage.setVisibility(View.GONE);
		List<CrewResponse> crewList = new ArrayList<>();
		for (CrewEntity entity : savedCrewList)
			crewList.add(CrewResponse.fromCrewEntity(entity));

		binding.rvCrewMembers.setAdapter(new CrewAdapter(this, crewList));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
				boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
				if (isConnected) loadFromWebService();
				else
					Toast.makeText(this, "You need to connect to the internet to do that", Toast.LENGTH_LONG).show();

				return true;

			case R.id.menu_delete:
				AlertDialog alertDialog = new AlertDialog.Builder(this)
						.setTitle("Delete all Saved Crew Data?")
						.setMessage("Are you sure you want to delete all locally saved Crew Data?")
						.setPositiveButton("YES", (dialog, which) -> {
							repository.deleteSavedData();
						})
						.setNegativeButton("NO", (dialog, which) -> {
							dialog.dismiss();
						})
						.create();

				alertDialog.show();

				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
