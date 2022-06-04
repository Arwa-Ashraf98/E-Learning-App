package com.example.firebaseprojectmarkshandler.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.databinding.ActivityMainBinding;
import com.example.firebaseprojectmarkshandler.helper.Constant;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userUid = auth.getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (userUid != null) {
            if (Constant.DOCTOR.equals(Constant.USER_TYPE)) {
                navigateToDoctor();
            } else if (Constant.STUDENT.equals(Constant.USER_TYPE)) {
                navigateToStudent();
            }
        } else if (userUid == null) {
            return;
        }
        onClicks();
    }

    private void onClicks() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.cbDoctor.isChecked() && !binding.cbStudent.isChecked()) {
                    Toast.makeText(MainActivity.this, getString(R.string.you_have_to_check_one), Toast.LENGTH_SHORT).show();
                } else if (binding.cbDoctor.isChecked() && binding.cbStudent.isChecked()) {
                    Toast.makeText(MainActivity.this, getString(R.string.just_one), Toast.LENGTH_SHORT).show();
                } else if (binding.cbDoctor.isChecked()) {
                    Constant.USER_TYPE = Constant.DOCTOR;
                    navigateLogin();
                } else if (binding.cbStudent.isChecked()) {
                    Constant.USER_TYPE = Constant.STUDENT;
                    navigateLogin();
                    //  String userUid2 = auth.getUid();
                    //    if (userUid!=null&&userUid.equals(userUid2)){
                    //       navigateToStudent();
                    //    }else if (userUid==null){
                    //        navigateLogin();
                    //
                    //  }
                }
            }
        });
    }

    private void navigateToDoctor() {
        startActivity(new Intent(MainActivity.this, DoctorHomeActivity.class));
        finish();
    }

    private void navigateToStudent() {
        startActivity(new Intent(MainActivity.this, StudentHomeActivity.class));
        finish();
    }

    private void navigateLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null)
            binding = null;
    }
}