package com.example.sign_app.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sign_app.Database.ImageAdapter;
import com.example.sign_app.Database.OnlineUpload;
import com.example.sign_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImagesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<OnlineUpload> mUploads;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        mAdapter = new ImageAdapter(getActivity(), mUploads);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);

        mProgressCircle = view.findViewById(R.id.online_progress);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mAdapter = new ImageAdapter(getActivity(),mUploads);
        mRecyclerView.setAdapter(mAdapter);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    OnlineUpload upload = postSnapshot.getValue(OnlineUpload.class);
                    mUploads.add(upload);
                }

                mAdapter = new ImageAdapter(getActivity(), mUploads);

                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showMessage(databaseError.getMessage());
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    private void showMessage(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
}