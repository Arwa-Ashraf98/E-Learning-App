package com.example.firebaseprojectmarkshandler.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.adapters.DoctorRecyclerAdapter;
import com.example.firebaseprojectmarkshandler.adapters.DoctorRecyclerAdapter2;
import com.example.firebaseprojectmarkshandler.adapters.DoctorRecyclerAdapter3;
import com.example.firebaseprojectmarkshandler.databinding.ActivityDoctorHomeBinding;
import com.example.firebaseprojectmarkshandler.helper.Constant;
import com.example.firebaseprojectmarkshandler.models.ModelCourse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DoctorHomeActivity extends AppCompatActivity {
    private ActivityDoctorHomeBinding binding ;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private ArrayList<ModelCourse> courseList = new ArrayList<>();
    private final DoctorRecyclerAdapter3 adapter3=new DoctorRecyclerAdapter3();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getCourses();
    //    if (courseList.size()==0){
     //       Toast.makeText(this,getString(R.string.nocoursesfound), Toast.LENGTH_SHORT).show();
     //   }
        onClicks();
    }

    private void onClicks(){
        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorHomeActivity.this, AddCourseActivity.class));
                finish();
            }
        });
        adapter3.setOnItemListener(new DoctorRecyclerAdapter3.onItemListener() {
            @Override
            public void onItemClick(String courseName, String courseId) {
                Intent intent = new Intent(DoctorHomeActivity.this,DoctorControlActivity.class);
                intent.putExtra(Constant.COURSE_NAME,courseName);
                intent.putExtra(Constant.COURSE_ID,courseId);
                startActivity(intent);
            }
        });
    }

    private void getCourses (){
        String doctorUid = auth.getCurrentUser().getUid();
        reference.child(Constant.REF_COURSE).
                orderByChild(Constant.REF_USER_ID).equalTo(doctorUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            //    courseList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    courseList.add(dataSnapshot.getValue(ModelCourse.class));
                }
            //    Collections.reverse(courseList);
                adapter3.setList(courseList,courseList.size()-1);
                binding.recyclerCourse.setAdapter(adapter3);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DoctorHomeActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding!=null){
            binding=null;
        }
    }


  /*  @Override
    public void onItemClick(int position) {
        // Intent incomingIntent = getIntent();
        //  String incomingCourseId = incomingIntent.getStringExtra(Constant.REF_COURSE_ID);
        Intent intent = new Intent(DoctorHomeActivity.this, DoctorControlActivity.class);
        intent.putExtra(Constant.REF_COURSE_NAME, courseList.get(position).getCourseName());
        startActivity(intent);
        finish();
    }*/
}