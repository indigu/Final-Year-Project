package com.example.sign_app.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sign_app.R;

public class OnlineDatabasesFragment extends Fragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance){
        super.onViewCreated(view, savedInstance);

        getActivity().setTitle("Online Databases");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online_databases, container, false);
    }
}
