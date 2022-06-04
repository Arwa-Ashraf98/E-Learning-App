package com.example.firebaseprojectmarkshandler.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.databinding.ActivityAttendanceBinding;
import com.example.firebaseprojectmarkshandler.helper.Constant;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class AttendanceActivity extends AppCompatActivity {

    private ActivityAttendanceBinding binding;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private String courseName, courseId;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onClicks();
        String courseId = getIntent().getStringExtra(Constant.COURSE_ID);
        String courseName = getIntent().getStringExtra(Constant.COURSE_NAME);
        this.courseId = courseId;
        this.courseName = courseName;
    }

    private void onClicks() {
        binding.btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int courseCode = random.nextInt(10000);
                code = courseCode;

                binding.textCode.setText(courseCode + "");

                //   another way to cast int to String
                //   binding.textCode.setText(Integer.toString(courseCode));
                binding.btnConfirm.setEnabled(true);
            }
        });
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAttendanceRef();

            }
        });
    }

    private void openAttendanceRef() {
        reference.child(Constant.REF_ATTENDANCE).child(courseId).child(courseName).child(code + "")
                .setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                binding.textCode.setText("");
                binding.btnConfirm.setEnabled(false);

            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(AttendanceActivity.this, getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding = null;
        }
    }
}