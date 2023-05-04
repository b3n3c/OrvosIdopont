package com.mobilalk.orvosidopont.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobilalk.orvosidopont.MainActivity;
import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.model.Appointment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditAppointmentFragment extends Fragment {

    private static final String ARG_TIME = "time";

    private Date mDate;

    MaterialButton mSaveButton;
    TextView mCategoryTextView, mServiceTextview, mDoctorTextView, mDateTextView;
    SharedViewModel sharedViewModel;
    FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    Activity a;

    public EditAppointmentFragment() {
    }

    public static EditAppointmentFragment newInstance(Date time) {
        EditAppointmentFragment fragment = new EditAppointmentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            a=(Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mDate = (Date) getArguments().getSerializable(ARG_TIME);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
        View view = inflater.inflate(R.layout.fragment_edit_appointment, container, false);
        mSaveButton = view.findViewById(R.id.saveButton);
        mCategoryTextView = view.findViewById(R.id.categoryTextView);
        mDoctorTextView = view.findViewById(R.id.doctorTextView);
        mDateTextView = view.findViewById(R.id.dateTextView);
        mServiceTextview = view.findViewById(R.id.serviceTextView);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        mCategoryTextView.setText(sharedViewModel.getCategory());
        mDateTextView.setText(new SimpleDateFormat("yyyy.MM.dd. HH:mm").format(mDate));
        mServiceTextview.setText(sharedViewModel.getServiceName());
        mDoctorTextView.setText(sharedViewModel.getDoctorName());

        mAuth = FirebaseAuth.getInstance();
        Date endDate = new Date();
        endDate.setTime(mDate.getTime()+(sharedViewModel.getServiceDuration()) * 60 * 1000);

        Date currentDate = new Date();
        String dateString = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(currentDate);


        Appointment appointment = new Appointment(
                sharedViewModel.getDoctor(),
                mAuth.getCurrentUser().getEmail(),
                dateFormat.format(mDate),
                dateFormat.format(endDate),
                sharedViewModel.getServiceName(),
                sharedViewModel.getDoctorName(),
                dateString
        );

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirestore = FirebaseFirestore.getInstance();
                mFirestore.collection("appointments").document(dateString).set(appointment);

                ((MainActivity)a).performStreamClick(2);
            }
        });


        return view;
    }
}