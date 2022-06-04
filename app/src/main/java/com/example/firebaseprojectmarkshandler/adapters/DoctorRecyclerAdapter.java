package com.example.firebaseprojectmarkshandler.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseprojectmarkshandler.models.ModelCourse;
import com.example.firebaseprojectmarkshandler.databinding.ItemCoursesRecyclerBinding;

import java.util.ArrayList;

public class DoctorRecyclerAdapter extends RecyclerView.Adapter<DoctorRecyclerAdapter.Holder> {

    private ArrayList<ModelCourse> list;
    private onItemListener onItemListener;



    public void setList(ArrayList<ModelCourse> list) {
        this.list = list;
    }

    public void setOnItemListener(DoctorRecyclerAdapter.onItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCoursesRecyclerBinding binding = ItemCoursesRecyclerBinding.
                inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Holder(binding,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
    //    ModelCourse modelCourse = list.get(position);
    //    holder.binding.textViewCourseName.setText(modelCourse.getCourseName());

        // another way
        holder.binding.textViewCourseName.setText(list.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return list==null?0: list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements DoctorRecyclerAdapter.onItemListener{

        ItemCoursesRecyclerBinding binding;
        onItemListener onItemListener;

        public Holder(ItemCoursesRecyclerBinding binding , onItemListener onItemListener) {
            super(binding.getRoot());
            this.binding=binding;
            this.onItemListener=onItemListener;

        }
        @Override
        public void onItemClick(int position) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }
    public interface onItemListener{
        void onItemClick (int position);
    }

}
