package com.example.firebaseprojectmarkshandler.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.firebaseprojectmarkshandler.R;
import com.example.firebaseprojectmarkshandler.databinding.ActivityCreateQuizBinding;
import com.example.firebaseprojectmarkshandler.helper.Constant;
import com.example.firebaseprojectmarkshandler.models.ModelQuestion;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateQuizActivity extends AppCompatActivity {
    private ActivityCreateQuizBinding binding;
    private ArrayList<ModelQuestion> listQuiz = new ArrayList<>();
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    ModelQuestion modelQuestion;
    private int rightAnswer;
    String courseId , courseName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCreateQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseId = getIntent().getStringExtra(Constant.COURSE_ID);
        courseName = getIntent().getStringExtra(Constant.COURSE_NAME);
        onClicks();
    }
    private void onClicks(){
        binding.btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuestion();
            }
        });
        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateQuizActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                if (listQuiz!=null){
                    Toast.makeText(CreateQuizActivity.this,getString(R.string.uploaded), Toast.LENGTH_SHORT).show();
                    uploadQuiz();
                }else{
                    Toast.makeText(CreateQuizActivity.this,getString( R.string.youadd), Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    rightAnswer = 1 ;
                    binding.checkBox2.setChecked(false);
                    binding.checkBox3.setChecked(false);
                    binding.checkBox4.setChecked(false);
                }
            }
        });
        binding.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    rightAnswer = 2 ;
                    binding.checkBox1.setChecked(false);
                    binding.checkBox3.setChecked(false);
                    binding.checkBox4.setChecked(false);
                }
            }
        });
        binding.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    rightAnswer=3;
                    binding.checkBox1.setChecked(false);
                    binding.checkBox2.setChecked(false);
                    binding.checkBox4.setChecked(false);
                }
            }
        });
        binding.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    rightAnswer=4;
                    binding.checkBox1.setChecked(false);
                    binding.checkBox2.setChecked(false);
                    binding.checkBox3.setChecked(false);

                }
            }
        });
    }
    private void getQuestion(){
        String question = binding.editCreateQuestion.getText().toString().trim();
        String choose1 = binding.editChoose1.getText().toString().trim();
        String choose2 = binding.editChoose2.getText().toString().trim();
        String choose3 = binding.editChoose3.getText().toString().trim();
        String choose4 = binding.editChoose4.getText().toString().trim();
        validation(question,choose1,choose2,choose3,choose4);
    }
    private void validation (String question , String choose1 , String choose2 , String choose3 , String choose4){
        if(question.isEmpty()){
            binding.editCreateQuestion.setError(getString(R.string.reqiured));
        }else if (choose1.isEmpty()){
            binding.editChoose1.setError(getString(R.string.reqiured));
        }else if (choose2.isEmpty()){
            binding.editChoose2.setError(getString(R.string.reqiured));
        }else if (choose3.isEmpty()){
            binding.editChoose3.setError(getString(R.string.reqiured));
        }else if (choose4.isEmpty()){
            binding.editChoose4.setError(getString(R.string.reqiured));
        }else if (rightAnswer==0){
            Toast.makeText(this,getString( R.string.selectRightAnswer), Toast.LENGTH_SHORT).show();
        }else {
            toNextQuestion(question, choose1, choose2, choose3, choose4);
        }
    }
    private void toNextQuestion(String question , String choose1 , String choose2 , String choose3 , String choose4){
        Toast.makeText(this,getString(R.string.added), Toast.LENGTH_SHORT).show();
        modelQuestion= new ModelQuestion(question,choose1,choose2,choose3,choose4,rightAnswer);
        listQuiz.add(modelQuestion);
        clear();
    }
    private void clear(){
        binding.editCreateQuestion.setText("");
        binding.editChoose1.setText("");
        binding.editChoose2.setText("");
        binding.editChoose3.setText("");
        binding.editChoose4.setText("");
        binding.checkBox1.setChecked(false);
        binding.checkBox2.setChecked(false);
        binding.checkBox3.setChecked(false);
        binding.checkBox4.setChecked(false);
    }
    private void uploadQuiz(){
        reference.child(Constant.REF_COURSE_QUIZ).child(courseId)
                .child(courseName)
                .push()
                .setValue(listQuiz).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CreateQuizActivity.this,getString(R.string.success), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateQuizActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(CreateQuizActivity.this,getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
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