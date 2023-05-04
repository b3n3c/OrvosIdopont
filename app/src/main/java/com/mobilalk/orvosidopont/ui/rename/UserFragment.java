package com.mobilalk.orvosidopont.ui.rename;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.mobilalk.orvosidopont.R;

public class UserFragment extends Fragment {
    FirebaseAuth auth;
    TextView email;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        email = view.findViewById(R.id.emailTextView);
        auth = FirebaseAuth.getInstance();
        email.setText(auth.getCurrentUser().getEmail());
        return view;
    }

}