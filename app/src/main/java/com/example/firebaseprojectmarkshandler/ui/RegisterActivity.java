package com.example.firebaseprojectmarkshandler.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.databinding.ActivityRegisterBinding;
import com.example.firebaseprojectmarkshandler.helper.Constant;
import com.example.firebaseprojectmarkshandler.models.ModelUsers;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding ;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    ModelUsers modelUsers ;
    String userType = Constant.USER_TYPE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onClicks();
    }

    private void onClicks(){
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

            }
        });
    }

    private void getData(){
        String name = binding.editTextName.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();
        validation(name,email,password);
    }

    private void validation (String name , String email , String password){
        if (name.isEmpty()){
            binding.editTextName.setError(getString(R.string.reqiured));
        }else if (email.isEmpty()){
            binding.editTextEmail.setError(getText(R.string.reqiured));
        }else if (password.isEmpty()){
            binding.editTextPassword.setError(getString(R.string.reqiured));
        }else if (password.length()<6){
            binding.editTextPassword.setError(getString(R.string.should_be_six_least));
        }else {
            binding.progressbar.setVisibility(View.VISIBLE);
            registerUser(name, email, password);
        }

    }

    private void registerUser(String name , String email , String password){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                binding.progressbar.setVisibility(View.GONE);
                sendDataToRealTime(name,email,userType);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.progressbar.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                clear();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                binding.progressbar.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
                clear();

            }
        });
    }

    private void sendDataToRealTime (String name, String email , String userType){
        // user id we get it from FB_auth
        String Uid = auth.getCurrentUser().getUid();
        modelUsers = new ModelUsers(name,email,userType,Uid);
        reference.child(Constant.REF_USERS).child(Uid).setValue(modelUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterActivity.this,getString(R.string.success), Toast.LENGTH_SHORT).show();

                // intent to student or doctor

                if (Constant.DOCTOR.equals(userType)){
                    startActivity(new Intent(RegisterActivity.this, DoctorHomeActivity.class));
                    finish();
                }else if (Constant.STUDENT.equals(userType)){
                    startActivity(new Intent(RegisterActivity.this, StudentHomeActivity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(RegisterActivity.this,getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clear (){
        binding.editTextName.setText("");
        binding.editTextEmail.setText("");
        binding.editTextPassword.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding!=null)
            binding = null;
    }
}