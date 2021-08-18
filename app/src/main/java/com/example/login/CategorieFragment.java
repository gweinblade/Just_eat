package com.example.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategorieFragment extends Fragment {
CardView rest,fast;

    List<resto> items = new ArrayList();

    public CategorieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.new_categorie_frag, container, false);

        rest= v.findViewById(R.id.rest);
        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                Intent intent = new Intent(getActivity(),resto.class);
                intent.putExtra("path","restaurants");
                startActivity(intent);

                /*SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("MYPREFERENCENAME", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("path","restaurants");
                editor.apply();*/

            }
        });
        fast= v.findViewById(R.id.fast);
        fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                Intent intent = new Intent(getActivity(), resto.class);
                intent.putExtra("path","fastFoods");
                startActivity(intent);
               /* SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("MYPREFERENCENAME", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("path","fastFoods");
                editor.apply();*/
            }
        });
        return  v;
    }
    private   void handleFrame(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fram1, fragment);

        fragmentTransaction.commit();
    }
    public void clear() {
        items.clear();
    }

}
