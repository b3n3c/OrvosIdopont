package com.mobilalk.orvosidopont.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.mobilalk.orvosidopont.R;
import com.mobilalk.orvosidopont.adapters.AppointmentPagerAdapter;

public class HomeFragment extends Fragment{

    ViewPager2 viewPager;
    AppointmentPagerAdapter myAdapter;
    int currentItem = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.viewPager);
        myAdapter = new AppointmentPagerAdapter(getChildFragmentManager(), getLifecycle());

        // add Fragments in your ViewPagerFragmentAdapter class
        myAdapter.addFragment(new PickSpecializationFragment());

        // set Orientation in your ViewPager2
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setUserInputEnabled(false);

        viewPager.setAdapter(myAdapter);
    }

    public void addFragment(Fragment fragment){
        myAdapter.addFragment(fragment);
        myAdapter.notifyItemInserted(currentItem+1);
        Log.d("KECSKE", "ADDFRAGMENT");
    }

    public void goForward(){
        currentItem++;
        viewPager.setCurrentItem(currentItem, true);
        myAdapter.notifyDataSetChanged();
        Log.d("KECSKE", "CURRENT ITEM: " + viewPager.getCurrentItem() + " / " + currentItem);
    }

    public void goBack(){
        currentItem--;
        viewPager.setCurrentItem(currentItem, true);
        myAdapter.removeLastFragment();
    }
}