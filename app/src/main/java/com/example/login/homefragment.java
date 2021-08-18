package com.example.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class homefragment extends Fragment {
    ViewPager viewPager;

    List<Model> models;
    RecyclerView neww,month,top;

    slider_ada sliders;


    DatabaseReference ref,reff,refff;
    ArrayList<monthmodel> list;
    ArrayList<newestmodel> list2;

    ArrayList<topmodel> list3;
TabLayout tabIndicator;
    restoadapter adap;
    monthadapter monthadapter;
    newestadapter newestadapter;
    topadapter topadapter;

    public homefragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayoutManagaer3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        neww = v.findViewById(R.id.whatsnewrecycler);
        month = v.findViewById(R.id.restoofthemonthrecycler);
        tabIndicator = v.findViewById(R.id.tab_indicator2);

        top = v.findViewById(R.id.topratingrecycler);
        neww.setLayoutManager( horizontalLayoutManagaer);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));

        list2 = new ArrayList<newestmodel>();
        neww.addItemDecoration(itemDecorator);

        month.setLayoutManager( horizontalLayoutManagaer2);
        list = new ArrayList<monthmodel>();
        month.addItemDecoration(itemDecorator);
        top.setLayoutManager( horizontalLayoutManagaer3);
        list3 = new ArrayList<topmodel>();
        top.addItemDecoration(itemDecorator);

        models = new ArrayList<>();

        models.add(new Model(R.drawable.p1,"Provide the best services and  food "));
        models.add(new Model(R.drawable.p2,"Your favorite dish , deliver to your home"));
        models.add(new Model(R.drawable.p3,"Deliver with JUST EAT"));


        sliders = new slider_ada(getContext(),models);
        viewPager= v.findViewById(R.id.viewe);
        viewPager.setAdapter(sliders);


        tabIndicator.setupWithViewPager(viewPager);
        //this is for the newest
        ref = FirebaseDatabase.getInstance().getReference().child("fastFoods");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    newestmodel p = dataSnapshot1.getValue(newestmodel.class);

                    list2.add(p);
                }

                newestadapter = new newestadapter(getContext(),list2);
                neww.setAdapter(newestadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"something wrong ",Toast.LENGTH_LONG).show();
            }
        });

        //this is for the month
        reff = FirebaseDatabase.getInstance().getReference().child("restaurants");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    monthmodel l = dataSnapshot1.getValue(monthmodel.class);

                    list.add(l);
                }

                monthadapter = new monthadapter(getContext(),list);
                month.setAdapter(monthadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"something wrong ",Toast.LENGTH_LONG).show();
            }
        });


        //this is for the top
        refff = FirebaseDatabase.getInstance().getReference("home").child("newest");
        refff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    topmodel k = dataSnapshot1.getValue(topmodel.class);

                    list3.add(k);
                }

                topadapter  = new topadapter(getContext(),list3);
                top.setAdapter(topadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"something wrong ",Toast.LENGTH_LONG).show();
            }
        });
        Timer timer =new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),4000,6000);



        return v;
    }
    public  class  MyTimerTask extends TimerTask{


        @Override
        public void run() {

            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(2);
                        } else if (viewPager.getCurrentItem() == 2) {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
            else return;
        }
    }

}
