package com.mobilalk.orvosidopont.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.model.Doctor;
import com.mobilalk.orvosidopont.ui.home.PickServiceFragment;
import com.mobilalk.orvosidopont.ui.home.ScreenChanger;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<CardViewHolder>{
    ArrayList<Doctor> doctors;
    ScreenChanger screenChanger;

    public DoctorAdapter(ArrayList<Doctor> doctors, ScreenChanger screenChanger) {
        this.doctors = doctors;
        this.screenChanger = screenChanger;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.textView.setText(doctors.get(position).getName());
        holder.mCardView.setOnClickListener(v -> {
            screenChanger.addFragment(PickServiceFragment.newInstance(doctors.get(position).getId()));
            screenChanger.goForward();
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public void setData(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
        notifyDataSetChanged();
    }
}
