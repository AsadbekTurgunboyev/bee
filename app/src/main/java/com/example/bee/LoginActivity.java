package com.example.bee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bee.activites.FillActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout txtNumber,txtParol;
    MaterialButton btnKirish;
    FirebaseAuth mAuth;
    Button btnGo;
    FirebaseAuthSettings firebaseAuthSettings ;
    PhoneAuthCredential credential;
    String id;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthSettings = mAuth.getFirebaseAuthSettings();
        btnKirish.setOnClickListener(view -> {
            String phone  = txtNumber.getEditText().getText().toString();

            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phone)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    id = s;
                                }

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    String code = phoneAuthCredential.getSmsCode();
                                    verifycode(code);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {

                                }
                            })          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);


        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifycode(txtParol.getEditText().getText().toString());
            }
        });



    }

    private void verifycode(String code) {
        credential = PhoneAuthProvider.getCredential(id,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void initViews() {
        txtNumber = findViewById(R.id.textInputLayout);
        btnKirish = findViewById(R.id.btnKirish);
        txtParol = findViewById(R.id.txtCode);
        btnGo = findViewById(R.id.btnGo);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();
                    Toast.makeText(LoginActivity.this, "Siz royhatgdan otdingiz", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, FillActivity.class);
                    i.putExtra("phone",txtNumber.getEditText().getText().toString());
                    startActivity(i);
                    finish();
                }
            }
        });
    }



}