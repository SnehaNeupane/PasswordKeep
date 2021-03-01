package com.example.pwdkeeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {

    private Context context;
    Activity activity;
    private ArrayList diary_id, diary_subject,diary_user, diary_context;
    Animation translate_anim;

    CustomAdapter(Activity activity, Context context, ArrayList diary_id, ArrayList diary_subject, ArrayList diary_user, ArrayList diary_context){
        this.activity = activity;
        this.context = context;
        this.diary_id = diary_id;
        this.diary_subject = diary_subject;
        this.diary_user = diary_user;
        this.diary_context = diary_context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.record_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //holder.diary_id_txt.setText(String.valueOf(diary_id.get(position)));
        holder.diary_subject_txt.setText(String.valueOf(diary_subject.get(position)));
        holder.diary_username_txt.setText(String.valueOf(diary_user.get(position)));
        holder.diary_context_txt.setText(String.valueOf(diary_context.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(diary_id.get(position)));
                intent.putExtra("subject", String.valueOf(diary_subject.get(position)));
                intent.putExtra("user", String.valueOf(diary_user.get(position)));
                intent.putExtra("contexts", String.valueOf(diary_context.get(position)));
                activity.startActivityForResult(intent, 1);


            }
        });
    }

    @Override
    public int getItemCount(){
        return diary_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView diary_id_txt, diary_subject_txt, diary_context_txt,diary_username_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            diary_id_txt = itemView.findViewById(R.id.record_id);
            diary_subject_txt = itemView.findViewById(R.id.record_title);
            diary_username_txt = itemView.findViewById(R.id.record_user);
            diary_context_txt = itemView.findViewById(R.id.record_content);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recycler view
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }

}
