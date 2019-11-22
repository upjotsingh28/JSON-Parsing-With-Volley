package com.antino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ViewHolder> {


    List<ModelClass> list;
    Context context;

    RequestOptions requestOptions = new RequestOptions();


    public ApiAdapter(List<ModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ApiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ApiAdapter.ViewHolder holder, int position) {


        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.error);

        ModelClass modelClass=list.get(position);
        holder.name.setText(modelClass.name);
        holder.age.setText(modelClass.age);
        holder.location.setText(modelClass.location);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(modelClass.url)
                .into(holder.imageView)
        ;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView name,age,location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.userName);
            age=itemView.findViewById(R.id.userAge);
            location=itemView.findViewById(R.id.userLocation);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
