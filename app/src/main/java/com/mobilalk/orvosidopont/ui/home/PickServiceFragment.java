package com.mobilalk.orvosidopont.ui.home;

import android.os.Bundle;

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
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.adapters.DoctorAdapter;
import com.mobilalk.orvosidopont.adapters.ServiceAdapter;
import com.mobilalk.orvosidopont.model.Doctor;

import java.util.HashMap;

public class PickServiceFragment extends Fragment implements ScreenChanger{

    private static final String ARG_DOCTOR = "doctor";

    private String mDoctorId;
    RecyclerView mRecyclerView;
    SharedViewModel sharedViewModel;
    TextView categoryTextView;
    MaterialButton backButton;
    HomeFragment parentFragment;
    private FirebaseFirestore mFirestore;
    private Query mItems;
    Doctor mDoctor;
    ServiceAdapter adapter;

    public PickServiceFragment() {
    }

    public static PickServiceFragment newInstance(String doctorId) {
        PickServiceFragment fragment = new PickServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DOCTOR, doctorId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDoctorId = getArguments().getString(ARG_DOCTOR);
        }else
            mDoctorId = "";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pick_service, container, false);
        mRecyclerView = view.findViewById(R.id.doctorRecyclerView);
        categoryTextView = view.findViewById(R.id.categoryTextView);
        backButton = view.findViewById(R.id.backButton);
        parentFragment = ((HomeFragment)PickServiceFragment.this.getParentFragment());

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.setDoctor(mDoctorId);
        mDoctor = new Doctor();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        adapter = new ServiceAdapter(new HashMap<>(), this);
        mRecyclerView.setAdapter(adapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("doctors").whereEqualTo(FieldPath.documentId(), mDoctorId).limit(1);
        mItems.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Doctor item = document.toObject(Doctor.class);
                    Log.d("item", item.getName());
                    mDoctor = item;
                    sharedViewModel.setDoctorName(mDoctor.getName());
                }
                adapter.setData(mDoctor.getServices());
                categoryTextView.setText(mDoctor.getName());
            }else{
                Log.d("error", task.getException().toString());
            }
        });

        categoryTextView.setText(mDoctor.getName());
        backButton.setOnClickListener(v -> goBack());
        categoryTextView.setOnClickListener(v -> goBack());

        return view;
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