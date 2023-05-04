package com.mobilalk.orvosidopont.ui.home;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.adapters.DateAdapter;
import com.mobilalk.orvosidopont.adapters.ServiceAdapter;
import com.mobilalk.orvosidopont.model.Appointment;
import com.mobilalk.orvosidopont.model.Doctor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PickAppointmentFragment extends Fragment implements DatePickerDialog.OnDateSetListener, ScreenChanger {

    private static final String ARG_NAME = "name";
    private static final String ARG_DURATION = "duration";

    private String mName;
    private int mDuration;

    ArrayList<Date> allDates;
    ArrayList<Date> possibleDates;

    RecyclerView mRecyclerView;
    SharedViewModel sharedViewModel;
    TextView categoryTextView;
    MaterialButton backButton;
    HomeFragment parentFragment;
    MaterialButton mDateButton;
    private FirebaseFirestore mFirestore;
    private Query mItems;
    String date;
    DateAdapter adapter;

    public PickAppointmentFragment() {
    }

    public static PickAppointmentFragment newInstance(String name, int duration) {
        PickAppointmentFragment fragment = new PickAppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putInt(ARG_DURATION, duration);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
            mDuration = getArguments().getInt(ARG_DURATION);
        }

        View view = inflater.inflate(R.layout.fragment_pick_appointment, container, false);
        allDates = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.doctorRecyclerView);
        categoryTextView = view.findViewById(R.id.categoryTextView);
        backButton = view.findViewById(R.id.backButton);
        parentFragment = ((HomeFragment)PickAppointmentFragment.this.getParentFragment());

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.setServiceName(mName);
        sharedViewModel.setServiceDuration(mDuration);
        mDateButton = view.findViewById(R.id.datePickButton);
        mDateButton.setOnClickListener(v -> {
            com.mobilalk.orvosidopont.dialogs.DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new com.mobilalk.orvosidopont.dialogs.DatePicker();
            mDatePickerDialogFragment.show(getChildFragmentManager(), "DATE PICK");
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
        possibleDates = new ArrayList<>();
        adapter = new DateAdapter(possibleDates, this);
        mRecyclerView.setAdapter(adapter);

        categoryTextView.setText(mName);

        categoryTextView.setOnClickListener(v -> goBack());
        backButton.setOnClickListener(v -> goBack());

        return view;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
        allDates.clear();
        possibleDates.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 8, 0);
        date = dateFormat.format(calendar.getTime());
        mDateButton.setText(new SimpleDateFormat("yyyy.MM.dd.").format(calendar.getTime()));
        mFirestore = FirebaseFirestore.getInstance();
        Date pickedDate = calendar.getTime();

        calendar.set(year, month, dayOfMonth, 17, 1);
        Date endDate = calendar.getTime();

        while(pickedDate.before(endDate) && new Date(pickedDate.getTime()+(mDuration * 60 * 1000)).before(endDate)){
            allDates.add(new Date(pickedDate.getTime()));
            pickedDate.setTime(pickedDate.getTime()+(mDuration * 60 * 1000));
        }

        for (Date date : allDates){
            Log.d("date", dateFormat.format(date));
        }

        mItems = mFirestore.collection("appointments");
        mItems.whereEqualTo("doctorID", sharedViewModel.getDoctor()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Appointment> appointments = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Appointment item = document.toObject(Appointment.class);
                    appointments.add(item);
                }
                for (Date startDate : allDates) {
                    boolean overlaps = false;
                    for (Appointment appointment : appointments) {
                        long startTime = 0;
                        long endTime = 0;
                        try {
                            startTime = dateFormat.parse(appointment.getStartDate()).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            endTime = dateFormat.parse(appointment.getEndDate()).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long date = startDate.getTime();
                        if (date >= startTime && date <= endTime) {
                            overlaps = true;
                            break;
                        }
                    }
                    if (!overlaps) {
                        possibleDates.add(startDate);
                    }
                }
                adapter.setDates(possibleDates);
            }
        });


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