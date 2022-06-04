package com.example.firebaseprojectmarkshandler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.databinding.ActivityDoctorControlBinding;
import com.example.firebaseprojectmarkshandler.helper.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorControlActivity extends AppCompatActivity {
    private ActivityDoctorControlBinding binding;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private String courseName, courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorControlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String courseName = getIntent().getStringExtra(Constant.COURSE_NAME);
        this.courseName = courseName;
        String courseId = getIntent().getStringExtra(Constant.COURSE_ID);
        this.courseId = courseId;
        onClicks();
        displayCourseId(courseId);

    }

    private void onClicks() {
        binding.textCourseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clipBoardText();
            }
        });
        binding.btnOpenAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorControlActivity.this, AttendanceActivity.class);
                intent.putExtra(Constant.COURSE_ID, courseId);
                intent.putExtra(Constant.COURSE_NAME, courseName);
                startActivity(intent);
            }
        });
        binding.btnOpenQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorControlActivity.this, CreateQuizActivity.class);
                intent.putExtra(Constant.COURSE_ID, courseId);
                intent.putExtra(Constant.COURSE_NAME, courseName);
                startActivity(intent);
            }
        });

    }

    private void displayCourseId(String courseId) {
        binding.textCourseId.setText(courseId);
    }

    private void clipBoardText() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String text = binding.textCourseId.getText().toString().trim();
        ClipData clipData = ClipData.newPlainText(Constant.CLIPBOARD_LABEL, text);
        clipboard.setPrimaryClip(clipData);
        Toast.makeText(this, getString(R.string.copied), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding = null;
        }
    }
}