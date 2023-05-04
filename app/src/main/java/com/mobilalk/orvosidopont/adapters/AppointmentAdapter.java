package com.mobilalk.orvosidopont.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.model.Appointment;
import com.mobilalk.orvosidopont.ui.appointments.DeleteListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewholder> {
    ArrayList<Appointment> mAppointments;
    DeleteListener deleteListener;

    public AppointmentAdapter(ArrayList<Appointment> mAppointments, DeleteListener deleteListener) {
        this.mAppointments = mAppointments;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentViewholder holder, int position) {
        Appointment item = mAppointments.get(position);
        holder.doctorTextView.setText(item.getDoctorName());
        holder.serviceTextView.setText(item.getService());
        try {
            holder.dateTextView.setText(new SimpleDateFormat("yyyy.MM.dd. HH:mm").format(
                    new SimpleDateFormat("yyyyMMdd_HHmm").parse(item.getStartDate()))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.delete(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

    public void setData(ArrayList<Appointment> appointments){
        mAppointments = appointments;
        notifyDataSetChanged();
    }

    public class AppointmentViewholder extends RecyclerView.ViewHolder {
        TextView doctorTextView, serviceTextView, dateTextView;
        MaterialButton mDelete;
        public AppointmentViewholder(@NonNull View itemView) {
            super(itemView);
            doctorTextView = itemView.findViewById(R.id.doctortextView);
            serviceTextView = itemView.findViewById(R.id.serviceTextView);
            dateTextView = itemView.findViewById(R.id.timeTextView);
            mDelete = itemView.findViewById(R.id.delete);
        }
    }
}
