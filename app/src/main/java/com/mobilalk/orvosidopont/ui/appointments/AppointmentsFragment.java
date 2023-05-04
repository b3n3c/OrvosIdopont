package com.mobilalk.orvosidopont.ui.appointments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.adapters.AppointmentAdapter;
import com.mobilalk.orvosidopont.model.Appointment;

import java.util.ArrayList;

public class AppointmentsFragment extends Fragment implements DeleteListener{

    RecyclerView mRecyclerView;
    AppointmentAdapter adapter;
    ArrayList<Appointment> mAppointments;
    private FirebaseFirestore mFirestore;
    private Query mItems;
    FirebaseUser user;
    FirebaseAuth auth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointments,container,false);

        mRecyclerView = view.findViewById(R.id.appointmentRecyclerView);
        mAppointments = new ArrayList<>();
        adapter = new AppointmentAdapter(mAppointments, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setAdapter(adapter);

        mFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mItems = mFirestore.collection("appointments").whereEqualTo("email", user.getEmail());
        mItems.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Appointment item = document.toObject(Appointment.class);
                    mAppointments.add(item);
                }
                adapter.setData(mAppointments);
            }else{
                Log.d("error", task.getException().toString());
            }
        });


        return view;
    }

    @Override
    public void delete(Appointment appointment) {
        DocumentReference ref = mFirestore.collection("appointments").document(appointment.getId());
        ref.delete()
                .addOnSuccessListener(success -> {
                    Log.d("delete", "Item is successfully deleted: " + appointment.getId());
                    mAppointments.clear();
                    mItems.get().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Appointment item = document.toObject(Appointment.class);
                                mAppointments.add(item);
                            }
                            adapter.setData(mAppointments);
                        }else{
                            Log.d("error", task.getException().toString());
                        }
                    });
                })
                .addOnFailureListener(fail -> {
                    Log.d("delete", "Item " + appointment.getId() + " cannot be deleted.");
                });
    }
}