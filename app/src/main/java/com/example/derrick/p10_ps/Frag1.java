package com.example.derrick.p10_ps;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;
import java.util.zip.CheckedOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag1 extends Fragment {
    ArrayList<Integer> alColour;
    Button btnChange;
    public Frag1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View frag1 = inflater.inflate(R.layout.fragment_1, container, false);
        btnChange = frag1.findViewById(R.id.btnChange);
        String[] colors = getActivity().getResources().getStringArray(R.array.colors);
        alColour = new ArrayList<Integer>();
        for (int i =0; i< colors.length; i++){
            int newColor = Color.parseColor(colors[i]);
            alColour.add(newColor);
        }

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int random = new Random().nextInt(alColour.size());
                Integer randomColor = alColour.get(random);
                frag1.setBackgroundColor(randomColor);
            }
        });
        return frag1;
    }

}
