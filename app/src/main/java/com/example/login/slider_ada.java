package com.example.login;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class slider_ada extends PagerAdapter {
    static Context context;
    private List<Model> models;
    LayoutInflater layoutInflater;

    public slider_ada(Context context, List<Model> models) {
        this.context= context;
        this.models = models;
    }




    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.slider,container,false );

        ImageView imageView = view.findViewById(R.id.image);
        TextView textView =  view.findViewById(R.id.texte);

        imageView.setImageResource(models.get(position).getImage());
        textView.setText(models.get(position).getTitle());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
