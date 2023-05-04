package com.mobilalk.orvosidopont.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.ui.home.PickAppointmentFragment;
import com.mobilalk.orvosidopont.ui.home.ScreenChanger;

import java.util.HashMap;

public class ServiceAdapter extends RecyclerView.Adapter<CardViewHolder>{
    HashMap<String, Integer> mServices;
    ScreenChanger screenChanger;

    public ServiceAdapter(HashMap<String, Integer> mServices, ScreenChanger screenChanger) {
        this.mServices = mServices;
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
        String item = mServices.keySet().toArray()[position].toString();
        holder.textView.setText(item + " - " + mServices.get(item) + " perc");
        holder.mCardView.setOnClickListener(v -> {
            screenChanger.addFragment(PickAppointmentFragment.newInstance(item, mServices.get(item)));
            screenChanger.goForward();
        });
    }

    @Override
    public int getItemCount() {
        return mServices.size();
    }

    public void setData(HashMap<String, Integer> services) {
        mServices = services;
        notifyDataSetChanged();
    }
}
