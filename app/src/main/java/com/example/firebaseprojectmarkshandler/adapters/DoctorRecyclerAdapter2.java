package com.example.firebaseprojectmarkshandler.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseprojectmarkshandler.models.ModelCourse;
import com.example.firebaseprojectmarkshandler.R;

import java.util.ArrayList;

public class DoctorRecyclerAdapter2 extends RecyclerView.Adapter<DoctorRecyclerAdapter2.Holder> {

    private ArrayList<ModelCourse> list ;
    private onItemListener mOnItemListener;

    public DoctorRecyclerAdapter2(ArrayList<ModelCourse> list, DoctorRecyclerAdapter2.onItemListener mOnItemListener) {
        this.list = list;
        this.mOnItemListener = mOnItemListener;
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_courses_recycler,parent,false);
        return new Holder(view,mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.courseName.setText(list.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return list==null?0: list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView courseName ;
        onItemListener onItemListener;

        public Holder(@NonNull View itemView ,onItemListener onItemListener) {
            super(itemView);
            this.onItemListener=onItemListener;
            courseName=itemView.findViewById(R.id.text_view_courseName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }
    public interface onItemListener{
         void onItemClick(int position);
    }
}
