package com.example.sign_app.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sign_app.R;

import java.util.List;

public class PictionaryFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        getActivity().setTitle("Online Pictionary");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pictionary, container, false);

        String[] pictionaryItems = {"Alphabet", "Greetings", "Verbs"};

        ListView listView = (ListView) view.findViewById(R.id.pictionaryListView);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                pictionaryItems
        );

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch(position){
                    case 0:
                        Intent alphabet = new Intent(Intent.ACTION_VIEW, Uri.parse(("http://116.203.40.3/alphabet/")));
                        startActivity(alphabet);
                        break;
                    case 1:
                        showMessage("Sorry, this collection of images has not been implemented yet!");
                        break;
                    case 2:
                        showMessage("Sorry, this collection of images has not been implemented yet!");
                        break;
                }
            }
            @SuppressWarnings("unused")
            public void onClick(View v){
            };
        });

        return view;
    }

    private void showMessage(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
}
