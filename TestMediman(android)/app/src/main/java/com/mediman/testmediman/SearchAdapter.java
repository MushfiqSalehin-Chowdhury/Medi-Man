package com.mediman.testmediman;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Admin on 1/14/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    //DataSnapshot snapshot;
    Context context;
    ArrayList<String> fullNameList;
    //ArrayList<String> userNameList;
    ArrayList<String> profilePicList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView full_name;

        public SearchViewHolder(View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.profileImage);
            full_name = (TextView) itemView.findViewById(R.id.full_name);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> fullNameList, ArrayList<String> profilePicList) {
        this.context = context;
        //String uid = snapshot.getKey();
        this.fullNameList = fullNameList;
        //this.userNameList = userNameList;
        this.profilePicList = profilePicList;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.full_name.setText(fullNameList.get(position));
        Glide.with(context).load(profilePicList.get(position)).asBitmap().placeholder(R.drawable.default_avatar).into(holder.profileImage);

        holder.full_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Full Name Clicked", Toast.LENGTH_SHORT).show();
                //SearchAdapter.this.context.startActivity(new Intent(SearchAdapter.this.context, UserProfileActivity.class));
                /*Intent intent = new Intent(context.getApplicationContext(), UserProfileActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);*/
                gotoUser();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fullNameList.size();
    }

    public void gotoUser(){
        Intent intent = new Intent(context.getApplicationContext(), UserProfileActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("user_id", 1);
        context.getApplicationContext().startActivity(intent);
    }
}
