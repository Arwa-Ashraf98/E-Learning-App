package com.example.firebaseprojectmarkshandler.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.databinding.ActivityLoginBinding;
import com.example.firebaseprojectmarkshandler.helper.Constant;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    String email  ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onClicks();

    }

    private void onClicks(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        binding.textviewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void getData(){
         String email = binding.editTextEmail.getText().toString().trim();
         this.email=email;
         String password = binding.editTextPassword.getText().toString().trim();
        validation(email,password);
    }

    private void validation (String email , String password){
        if (email.isEmpty()){
            binding.editTextEmail.setError(getString(R.string.reqiured));
        }else if (password.isEmpty()){
            binding.editTextPassword.setError(getString(R.string.reqiured));
        }else {
            loginUser(email, password);
        }
    }

    private void loginUser (String email , String password){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this,getString(R.string.success), Toast.LENGTH_SHORT).show();
                if (Constant.DOCTOR.equals(Constant.USER_TYPE)){
                    startActivity(new Intent(LoginActivity.this, DoctorHomeActivity.class));
                    finish();
                }else if (Constant.STUDENT.equals(Constant.USER_TYPE)){
                    Intent intent= new Intent(LoginActivity.this, StudentHomeActivity.class);
                    intent.putExtra(Constant.STUDENT_EMAIL,email);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(LoginActivity.this,getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding!=null)
        binding = null;
    }


}