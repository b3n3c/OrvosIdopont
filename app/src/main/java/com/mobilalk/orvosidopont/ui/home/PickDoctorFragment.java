package com.mobilalk.orvosidopont.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.adapters.DoctorAdapter;
import com.mobilalk.orvosidopont.adapters.SpecializationAdapter;
import com.mobilalk.orvosidopont.model.Doctor;
import com.mobilalk.orvosidopont.model.Specialization;

import java.util.ArrayList;

public class PickDoctorFragment extends Fragment implements ScreenChanger{
    private static final String ARG_DOCTORS = "doctors";
    private static final String ARG_SPECIALIZATION = "specialization";
    RecyclerView mRecyclerView;
    SharedViewModel sharedViewModel;
    TextView categoryTextView;
    MaterialButton backButton;
    HomeFragment parentFragment;
    ArrayList<String> doctorIds;
    private FirebaseFirestore mFirestore;
    private Query mItems;
    ArrayList<Doctor> doctors;
    DoctorAdapter adapter;
    String specialization;

    public static PickDoctorFragment newInstance(ArrayList<String> doctors, String specialization){
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_DOCTORS,doctors);
        args.putString(ARG_SPECIALIZATION, specialization);
        PickDoctorFragment pickDoctorFragment = new PickDoctorFragment();
        pickDoctorFragment.setArguments(args);
        return pickDoctorFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pick_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.doctorRecyclerView);
        categoryTextView = view.findViewById(R.id.categoryTextView);
        backButton = view.findViewById(R.id.backButton);
        parentFragment = ((HomeFragment)PickDoctorFragment.this.getParentFragment());

        if(getArguments() != null){
            doctorIds = getArguments().getStringArrayList(ARG_DOCTORS);
            specialization = getArguments().getString(ARG_SPECIALIZATION);
        }else{
            doctorIds = new ArrayList<>();
            specialization = "";
        }

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.setCategory(specialization);
        categoryTextView.setText(sharedViewModel.getCategory());

        doctors = new ArrayList<>();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        adapter = new DoctorAdapter(doctors, this);
        mRecyclerView.setAdapter(adapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("doctors").whereIn(FieldPath.documentId(), doctorIds);
        mItems.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Doctor item = document.toObject(Doctor.class);
                    Log.d("item", item.getName());
                    doctors.add(item);
                }
                adapter.setData(doctors);
            }else{
                Log.d("error", task.getException().toString());
            }
        });

        backButton.setOnClickListener(v -> goBack());
        categoryTextView.setOnClickListener(v -> goBack());
    }

    @Override
    public void addFragment(Fragment fragment) {
        parentFragment.addFragment(fragment);
    }

    @Override
    public void goBack() {
        parentFragment.goBack();
    }

    @Override
    public void goForward() {
        parentFragment.goForward();
    }
}