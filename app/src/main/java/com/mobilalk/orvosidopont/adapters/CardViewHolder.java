package com.mobilalk.orvosidopont.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilalk.orvosidopont.R;

public class CardViewHolder extends RecyclerView.ViewHolder{
    CardView mCardView;
    TextView textView;
    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.data);
        mCardView = itemView.findViewById(R.id.cardView);
    }
}
