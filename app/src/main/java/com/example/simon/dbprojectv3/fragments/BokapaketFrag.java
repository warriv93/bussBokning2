package com.example.simon.dbprojectv3.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simon.dbprojectv3.R;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BokapaketFrag extends Fragment {


    public BokapaketFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bokapaket, container, false);
        startStuff(view);
        return view;
    }

    private void startStuff(View view) {

    }
}
