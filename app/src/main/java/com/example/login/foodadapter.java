package com.example.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class foodadapter extends RecyclerView.Adapter<foodadapter.MyViewHolder2> {
    DatabaseReference reference,reff;
    FirebaseAuth firebaseAuth;

    Context context;
    ArrayList<food> foods;
    base cartmodels;
    boolean exp=false;

    public foodadapter(Context c, ArrayList<food> f) {
        this.context = c;
        this.foods = f;
    }


    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new foodadapter.MyViewHolder2(LayoutInflater.from(context).inflate(R.layout.teste,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder2 holder, final int position) {

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser userss  = firebaseAuth.getCurrentUser();
        final String users= Objects.requireNonNull(userss).getUid();
        reference = FirebaseDatabase.getInstance().getReference("cart").child(users);
        reff = FirebaseDatabase.getInstance().getReference("rest").child(users);

        holder.name.setText(foods.get(position).getName());
        holder.price.setText(foods.get(position).getPrice()+" DA");
        holder.description.setText(foods.get(position).getDescription());
        Picasso.get().load(foods.get(position).getImage()).into(holder.image);
        //holder.image.setImageResource(foods.get(position).getImage());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.expand.getVisibility()==View.GONE)
                {
                    holder.expand.setVisibility(View.VISIBLE);
                }
                else {holder.expand.setVisibility(View.GONE);}
            }
        });
        holder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, CartFragment.class);
                final String namez = foods.get(position).getName();
                final String pricez = foods.get(position).getPrice();
                final String imagez = foods.get(position).getImage();
                final String quant = foods.get(position).getQuantite();
                final String id = foods.get(position).getID();
                final  String idreso = foods.get(position).getidresto();
reff.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(!dataSnapshot.exists())
        {
            cartmodels = new base();
            cartmodels.setImage(imagez);
            cartmodels.setName(namez);
            cartmodels.setPrice(pricez);
            cartmodels.setID(id);
            cartmodels.setQuantite(quant);
            Toast.makeText(context, namez + "  1  ", Toast.LENGTH_LONG).show();
            reference.child(id).setValue(cartmodels);
            reff.child("restoid").setValue(idreso);


        }
        else {
            if (dataSnapshot.child("restoid").getValue().toString().equals(idreso)) {
                cartmodels = new base();
                cartmodels.setImage(imagez);
                cartmodels.setName(namez);
                cartmodels.setPrice(pricez);
                cartmodels.setID(id);
                cartmodels.setQuantite(quant);
                Toast.makeText(context, namez + "  2   ", Toast.LENGTH_LONG).show();
                reference.child(id).setValue(cartmodels);
                //reff.child("restoid").setValue(idreso);


            }
            else {

                new AlertDialog.Builder(context)
                        .setTitle("Confirm Order")
                        .setMessage("Your basket contains products from another  restaurant do you want to delete the selection and add products from this restaurant?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                DatabaseReference names = FirebaseDatabase.getInstance().getReference("cart").child(users);
                                names.removeValue();
                                cartmodels = new base();
                                cartmodels.setImage(imagez);
                                cartmodels.setName(namez);
                                cartmodels.setPrice(pricez);
                                cartmodels.setID(id);
                                cartmodels.setQuantite(quant);
                                Toast.makeText(context, namez + "  3  " , Toast.LENGTH_LONG).show();
                                reference.child(id).setValue(cartmodels);
                                reff.child("restoid").setValue(idreso);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
                /**/





            }
        }




        }


    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder
    {
        TextView name,description,price;
        ImageView image;
        CardView button;
        LinearLayout expand;
        ImageButton but;
        private MyViewHolder2(View item)
        {
            super(item);
            but =item.findViewById(R.id.buybutt22);
            name = item.findViewById(R.id.foodname22);
            price = item.findViewById(R.id.foodprice22);
            description = item.findViewById(R.id.fooddescuption22);
            image = item.findViewById(R.id.foodimage22);
            button = item.findViewById(R.id.foodcard22);
            expand = item.findViewById(R.id.expandeddescription22);
        }
    }
}
