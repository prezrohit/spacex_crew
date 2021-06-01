package com.prezrohit.spacexcrew.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.prezrohit.spacexcrew.R;
import com.prezrohit.spacexcrew.databinding.ActivityMainBinding;
import com.prezrohit.spacexcrew.webservice.CrewResponse;
import com.prezrohit.spacexcrew.webservice.WebService;
import com.prezrohit.spacexcrew.webservice.WebServiceClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		binding.rvCrewMembers.setLayoutManager(new GridLayoutManager(this, 2));

		WebService webService = WebServiceClient.getRetrofit().create(WebService.class);
		webService.getCrewMembers().enqueue(new Callback<List<CrewResponse>>() {
			@Override
			public void onResponse(@NonNull Call<List<CrewResponse>> call, @NonNull Response<List<CrewResponse>> response) {
				Log.d(TAG, "onResponse: " + response.body());
				CrewAdapter adapter = new CrewAdapter(getApplicationContext(), response.body());
				binding.rvCrewMembers.setAdapter(adapter);
			}

			@Override
			public void onFailure(@NonNull Call<List<CrewResponse>> call, @NonNull Throwable t) {
				Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
				Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			}
		});
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
				Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
				return true;

			case R.id.menu_delete:
				Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

}