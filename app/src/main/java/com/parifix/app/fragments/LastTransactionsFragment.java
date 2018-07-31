package com.parifix.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parifix.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LastTransactionsFragment extends Fragment {


    public LastTransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_last_transactions, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }

}
