package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class restoadapter extends RecyclerView.Adapter<restoadapter.MyViewHolder> {
    Context context;
    FirebaseAuth firebaseAuth;

    ArrayList<restomodel> restos;
    fav cartmodels;
    DatabaseReference reference;



    public restoadapter(Context c, ArrayList<restomodel> r) {
        context = c;
        restos = r;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.restocard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(restos.get(position).getName());
        holder.rating.setText(restos.get(position).getRating());
        Picasso.get().load(restos.get(position).getImage()).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,restos.get(position).getName(),Toast.LENGTH_LONG).show();

                Intent i = new Intent(context,foodpage.class);
                i.putExtra("name",restos.get(position).getName());
                i.putExtra("id",restos.get(position).getID());
                i.putExtra("rating",restos.get(position).getRating());
                i.putExtra("image",restos.get(position).getImage());
                i.putExtra("adresse",restos.get(position).getAdress());
                i.putExtra("numero",restos.get(position).getPhone());
                i.putExtra("heure",restos.get(position).getTime());
                i.putExtra("pathe",restos.get(position).getPath());


                //Toast.makeText(context, restos.get(position).getID(), Toast.LENGTH_SHORT).show();
                //i.putExtra("menu",restos.get(position).getName());
                context.startActivity(i);
            }
        });





    }

    @Override
    public int getItemCount() {
        return restos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,rating;
        ImageView image;
        CardView cardView;
        public MyViewHolder(View item)
        {
            super(item);
            name = item.findViewById(R.id.restoname);
            rating = item.findViewById(R.id.restrating);
            image = item.findViewById(R.id.restoimage);
            cardView = item.findViewById(R.id.card);
        }
    }
}

