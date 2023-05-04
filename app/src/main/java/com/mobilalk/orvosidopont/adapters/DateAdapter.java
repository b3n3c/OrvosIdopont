package com.mobilalk.orvosidopont.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.ui.home.EditAppointmentFragment;
import com.mobilalk.orvosidopont.ui.home.ScreenChanger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateAdapter extends RecyclerView.Adapter<CardViewHolder>{
    ArrayList<Date> mDates;
    ScreenChanger screenChanger;

    public DateAdapter(ArrayList<Date> mDates, ScreenChanger screenChanger) {
        this.mDates = mDates;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        holder.textView.setText(dateFormat.format(mDates.get(position)));
        holder.mCardView.setOnClickListener(v -> {
            screenChanger.addFragment(EditAppointmentFragment.newInstance(mDates.get(position)));
            screenChanger.goForward();
        });
    }

    public void setDates(ArrayList<Date> dates){
        mDates = dates;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDates.size();
    }
}
