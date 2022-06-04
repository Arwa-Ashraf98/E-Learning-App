package com.example.firebaseprojectmarkshandler.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.adapters.DoctorRecyclerAdapter3;
import com.example.firebaseprojectmarkshandler.databinding.ActivityStudentHomeBinding;
import com.example.firebaseprojectmarkshandler.helper.Constant;
import com.example.firebaseprojectmarkshandler.models.ModelCourse;
import com.example.firebaseprojectmarkshandler.models.ModelMember;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentHomeActivity extends AppCompatActivity {

    ActivityStudentHomeBinding binding ;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    String studentEmail , courseName ;
    ArrayList<ModelMember> listMember = new ArrayList<>();
    ArrayList<ModelCourse> listCourses  = new ArrayList<>();
//    ArrayList<String>  courseNameList  = new ArrayList<>();
    DoctorRecyclerAdapter3 adapter = new DoctorRecyclerAdapter3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String email = getIntent().getStringExtra(Constant.STUDENT_EMAIL);
        studentEmail=email;
        onClicks();


    }
    private void onClicks(){
        binding.btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCourseId();
            }
        });
        adapter.setOnItemListener(new DoctorRecyclerAdapter3.onItemListener() {
            @Override
            public void onItemClick(String courseName, String courseId) {

            }
        });
    }
    private void getCourseId (){
        String courseId = binding.editAddMember.getText().toString().trim();
        validation(courseId);
    }
    private void validation (String courseId){
        if (courseId.isEmpty()){
            binding.editAddMember.setError(getString(R.string.reqiured));
        }else {
           addMember(courseId);
        }
    }
    private void addMember(String courseId){
        ModelMember modelMember = new ModelMember(studentEmail,courseId,0.0,0.0);
        listMember.add(modelMember);
        reference.child(Constant.REF_COURSE)
                .child(courseId)
                .child(Constant.REF_MEMBERS)
                .setValue(listMember)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(StudentHomeActivity.this,getString(R.string.added), Toast.LENGTH_SHORT).show();
                        getCourses(courseId);
                        clear();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentHomeActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCourses (String courseId){
        reference.child(Constant.REF_COURSE)
                .child(courseId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listCourses.add(snapshot.getValue(ModelCourse.class));
                        adapter.setList2(listCourses);
                        binding.recyclerCoursesStudent.setAdapter(adapter);
//                        ArrayList<String> courseName = new ArrayList<>();
//                        for (int i=0 ; i< listCourses.size();i++){
//                            courseName.set(i, listCourses.get(i).getCourseName());
//                        }
//                         adapter.setList(listCourses, listCourses.size()-1);
//                         binding.editAddMember.setText(courseName.get(0));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(StudentHomeActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clear (){
        binding.editAddMember.setText("");
    }

    private void getCourseName(String courseId){
        reference.child(Constant.REF_COURSE)
                .child(courseId)
                .child(Constant.COURSE_NAME)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        courseName = snapshot.getValue(ModelCourse.class).getCourseName();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
}