package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class cartadapter extends RecyclerView.Adapter<cartadapter.MyViewHolder3> {
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    Context context;
    ArrayList<cartmodel> carts;

    public cartadapter(Context context, ArrayList<cartmodel> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new cartadapter.MyViewHolder3(LayoutInflater.from(context).inflate(R.layout.cartitemcard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder3 holder, final int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser userss  = firebaseAuth.getCurrentUser();
        final String users= Objects.requireNonNull(userss).getUid();
        holder.name.setText(carts.get(position).getName());
        holder.price.setText(carts.get(position).getPrice()+" DA");
        Picasso.get().load(carts.get(position).getImage()).into(holder.image);
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = Integer.parseInt(carts.get(position).getQuantite());
                String id = carts.get(position).getID();
                num = num + 1;
                reference = FirebaseDatabase.getInstance().getReference("cart").child(users).child(id);
                reference.child("quantite").setValue(String.valueOf(num));
                carts.clear();


            }
        });
        holder.quant.setText(carts.get(position).getQuantite());
        holder.moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.quant.getText().toString().equals("1")){
                    holder.moins.setVisibility(View.VISIBLE);

                }
                else{ int num = Integer.parseInt(carts.get(position).getQuantite());
                    String id = carts.get(position).getID();
                    num = num - 1;
                    reference = FirebaseDatabase.getInstance().getReference("cart").child(users).child(id);
                    reference.child("quantite").setValue(String.valueOf(num));
                    carts.clear();}
            }
        });



    }
    public void delete(String name) {
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser userss  = firebaseAuth.getCurrentUser();
        final String users= Objects.requireNonNull(userss).getUid();
        DatabaseReference names = FirebaseDatabase.getInstance().getReference("cart").child(users).child(name);
        names.removeValue();



    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder {
        TextView name,price,quant;
        ImageView image;
        ImageButton plus,moins;

        public MyViewHolder3(@NonNull View item) {
            super(item);
            name = item.findViewById(R.id.foodname2);
            price = item.findViewById(R.id.foodprice2);
            image = item.findViewById(R.id.foodimage2);
            quant = item.findViewById(R.id.quant);
            moins = item.findViewById(R.id.del);
            plus = item.findViewById(R.id.adde);


        }
    }
}