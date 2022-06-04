package com.example.firebaseprojectmarkshandler.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.databinding.ActivityAddCourseBinding;
import com.example.firebaseprojectmarkshandler.helper.Constant;
import com.example.firebaseprojectmarkshandler.models.ModelCourse;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCourseActivity extends AppCompatActivity {
    ActivityAddCourseBinding binding ;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String courseId = reference.push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onClicks();
    }

    private void onClicks() {
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }
    private void getData (){
        String courseName = binding.editCourseName.getText().toString().toLowerCase().trim();
        String quizGrade = binding.editQuizGrade.getText().toString().trim();
        String projectGrade = binding.editProjectGrade.getText().toString().trim();
        String attendanceGrade = binding.editAttendanceGrade.getText().toString().trim();

        validation(courseName,quizGrade,projectGrade,attendanceGrade);
    }

    private void validation (String courseName ,String quizGrade, String projectGrade,String attendanceGrade){
        if (courseName.isEmpty()){
            binding.editCourseName.setError(getString(R.string.reqiured));
        }else if (quizGrade.isEmpty()){
            binding.editQuizGrade.setError(getString(R.string.reqiured));
        }else if (projectGrade.isEmpty()){
            binding.editProjectGrade.setError(getString(R.string.reqiured));
        }else if (attendanceGrade.isEmpty()){
            binding.editAttendanceGrade.setError(getString(R.string.reqiured));
        }else {
            double quiz = Double.parseDouble(quizGrade);
            double project = Double.parseDouble(projectGrade);
            double attendance = Double.parseDouble(attendanceGrade);
            createCourse(courseName,quiz,project,attendance);
        }
    }


    private void createCourse(String courseName ,double quizGrade, double projectGrade,double attendanceGrade){
        String doctorUid = auth.getUid();

        // you can set these variables using setter instead of constructor
        ModelCourse modelCourse = new ModelCourse(courseName,quizGrade,projectGrade,attendanceGrade,doctorUid,courseId);
        reference.child(Constant.REF_COURSE).child(courseId).
                setValue(modelCourse).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddCourseActivity.this,getString(R.string.success), Toast.LENGTH_SHORT).show();
                clear();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCourseActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                clear();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(AddCourseActivity.this,getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
                clear();
            }
        });
    }
    private void clear (){
        binding.editCourseName.setText("");
        binding.editQuizGrade.setText("");
        binding.editProjectGrade.setText("");
        binding.editAttendanceGrade.setText("");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCourseActivity.this, DoctorHomeActivity.class);
        //intent.putExtra(Constant.REF_COURSE_NAME,courseId);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding!=null)
            binding=null;
    }
}