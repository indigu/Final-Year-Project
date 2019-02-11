package com.example.sign_app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;

import com.example.sign_app.Activities.NewMemoryActivity;
import com.example.sign_app.Database.MemoriesAdapter;
import com.example.sign_app.Database.MemoryDbHelper;
import com.example.sign_app.R;

public class DatabaseFragment extends Fragment {

    private MemoryDbHelper dbHelper;
    private GridView gridView;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_database, container, false);

        this.gridView = view.findViewById(R.id.activity_main_grid_view);
        this.dbHelper = new MemoryDbHelper(getActivity());
        this.gridView.setAdapter(new MemoriesAdapter(getActivity(), this.dbHelper.readAllMemories(), false));
        this.gridView.setEmptyView(view.findViewById(R.id.activity_main_empty_view));

        fab = view.findViewById(R.id.myFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), NewMemoryActivity.class);
                startActivity(in);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CursorAdapter)gridView.getAdapter()).swapCursor(this.dbHelper.readAllMemories());
    }
}
