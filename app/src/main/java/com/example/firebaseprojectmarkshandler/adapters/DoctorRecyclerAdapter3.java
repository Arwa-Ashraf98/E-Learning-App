package com.example.firebaseprojectmarkshandler.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.models.ModelCourse;

import java.util.ArrayList;

public class DoctorRecyclerAdapter3 extends RecyclerView.Adapter<DoctorRecyclerAdapter3.Holder> {

    private ArrayList<ModelCourse> list ;
    private onItemListener onItemListener;

    // Activity will initialize this interface so it won't implement override method her and
    // that when i set lick on item
    public void setOnItemListener(DoctorRecyclerAdapter3.onItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public void setList(ArrayList<ModelCourse> list, int position) {
        this.list = list;
        notifyItemChanged(list.size());
    }
    public void setList2(ArrayList<ModelCourse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_courses_recycler,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ModelCourse modelCourse = list.get(position);
        holder.textCourseName.setText(modelCourse.getCourseName());
        //holder.textCourseName.setOnClickListener((View.OnClickListener) onItemListener);

    }

    @Override
    public int getItemCount() {
        return list==null ?0: list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView textCourseName ;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textCourseName=itemView.findViewById(R.id.text_view_courseName);

            textCourseName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemListener!=null){
                        onItemListener.onItemClick(list.get(getLayoutPosition()).getCourseName()
                        ,list.get(getLayoutPosition()).getCourseId());
                    }
                }
            });
        }
    }
    public interface onItemListener {
        void onItemClick(String courseName , String courseId);
    }
}
