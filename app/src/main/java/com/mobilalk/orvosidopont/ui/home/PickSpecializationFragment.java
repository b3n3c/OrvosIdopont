package com.mobilalk.orvosidopont.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.adapters.SpecializationAdapter;
import com.mobilalk.orvosidopont.model.Specialization;

import java.util.ArrayList;

public class PickSpecializationFragment extends Fragment implements ScreenChanger{
    RecyclerView mRecyclerView;
    SpecializationAdapter adapter;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private ArrayList<Specialization> mSpecializations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_specialization, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.serviceRecyclerView);
        mSpecializations = new ArrayList<>();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        adapter = new SpecializationAdapter(mSpecializations, this);
        mRecyclerView.setAdapter(adapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("specializations");
        mItems.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Specialization item = document.toObject(Specialization.class);
                    Log.d("item", item.getName());
                    mSpecializations.add(item);
                }
                adapter.setData(mSpecializations);
            }else{
                Log.d("error", task.getException().toString());
            }
        });
    }

    @Override
    public void addFragment(Fragment fragment) {
        HomeFragment parentFrag = ((HomeFragment) PickSpecializationFragment.this.getParentFragment());
        parentFrag.addFragment(fragment);

    }

    @Override
    public void goBack() {

    }

    @Override
    public void goForward() {
        HomeFragment parentFrag = ((HomeFragment) PickSpecializationFragment.this.getParentFragment());
        parentFrag.goForward();
    }
}