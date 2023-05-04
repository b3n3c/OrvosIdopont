package com.mobilalk.orvosidopont.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.model.Specialization;
import com.mobilalk.orvosidopont.ui.home.PickDoctorFragment;
import com.mobilalk.orvosidopont.ui.home.PickSpecializationFragment;
import com.mobilalk.orvosidopont.ui.home.ScreenChanger;

import java.util.ArrayList;

public class SpecializationAdapter extends RecyclerView.Adapter<CardViewHolder>{

    private ArrayList<Specialization> mSpecializationData;
    private ScreenChanger screenChanger;
    Context mContext;

    public SpecializationAdapter(ArrayList<Specialization> mSpecializationData, PickSpecializationFragment screenChanger) {
        this.mSpecializationData = mSpecializationData;
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
        holder.textView.setText(mSpecializationData.get(position).getName());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Specialization specialization = mSpecializationData.get(holder.getAdapterPosition());
                screenChanger.addFragment(PickDoctorFragment.newInstance(specialization.getDoctors(), specialization.getName()));
                screenChanger.goForward();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpecializationData.size();
    }

    public void setData(ArrayList<Specialization> data){
        mSpecializationData = data;
        notifyDataSetChanged();
    }
}
