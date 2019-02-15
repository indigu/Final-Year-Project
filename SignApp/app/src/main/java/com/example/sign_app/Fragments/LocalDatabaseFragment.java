package com.example.sign_app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sign_app.Activities.NewMemoryActivity;
import com.example.sign_app.Database.LocalDatabaseAdapter;
import com.example.sign_app.Database.LocalDatabaseHelper;
import com.example.sign_app.R;

public class LocalDatabaseFragment extends Fragment {

    private static final String TAG = "tag";

    private LocalDatabaseHelper dbHelper;
    private GridView gridView;
    FloatingActionButton fab;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        getActivity().setTitle("User Database");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_local_database, container, false);

        this.gridView = view.findViewById(R.id.activity_main_grid_view);
        this.dbHelper = new LocalDatabaseHelper(getActivity());
        this.gridView.setAdapter(new LocalDatabaseAdapter(getActivity(), this.dbHelper.readAllMemories(), false));
        this.gridView.setEmptyView(view.findViewById(R.id.activity_main_empty_view));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String title = gridView.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: " + title);
                Toast.makeText(getActivity().getApplicationContext(),
                        "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

//                Cursor data = dbHelper.getItemID(title);
//
//                int itemID = -1;
//                if(data.moveToFirst()){
//
//                    while(data.moveToNext()){
//                        itemID = data.getInt(0);
//                    }
//                }
//                if(itemID > -1){
//                    Intent editScreenIntent = new Intent(getActivity().getApplicationContext(), EditDataActivity.class);
//                    editScreenIntent.putExtra("id",itemID);
//                    editScreenIntent.putExtra("title",title);
//                    startActivity(editScreenIntent);
//                }
//                else{
//                    toastMessage("No ID associated with that name");
//                }
            }
        });

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
        ((CursorAdapter) gridView.getAdapter()).swapCursor(this.dbHelper.readAllMemories());
    }

    private void toastMessage(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}