package com.prezrohit.spacexcrew.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prezrohit.spacexcrew.R;

public class CrewViewHolder extends RecyclerView.ViewHolder {

	final ImageView image;
	final TextView textViewName;
	final TextView textViewAgency;
	final TextView textViewStatus;

	public CrewViewHolder(@NonNull View itemView) {
		super(itemView);

		image = itemView.findViewById(R.id.image);
		textViewName = itemView.findViewById(R.id.name);
		textViewAgency = itemView.findViewById(R.id.agency);
		textViewStatus = itemView.findViewById(R.id.status);
	}
}
