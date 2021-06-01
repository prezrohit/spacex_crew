package com.prezrohit.spacexcrew.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prezrohit.spacexcrew.R;
import com.prezrohit.spacexcrew.webservice.CrewResponse;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewViewHolder> {

	private final Context context;
	private final List<CrewResponse> crewList;

	public CrewAdapter(Context context, List<CrewResponse> crewList) {
		this.context = context;
		this.crewList = crewList;
	}

	@NonNull
	@Override
	public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_crew, parent, false);
		return new CrewViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
		CrewResponse crew = crewList.get(position);
		holder.textViewName.setText(crew.getName());
		holder.textViewAgency.setText(crew.getAgency());
		holder.textViewStatus.setText(crew.getStatus());
		Glide.with(context).load(crew.getImage()).into(holder.image);
	}

	@Override
	public int getItemCount() {
		return crewList == null ? 0 : crewList.size();
	}
}
