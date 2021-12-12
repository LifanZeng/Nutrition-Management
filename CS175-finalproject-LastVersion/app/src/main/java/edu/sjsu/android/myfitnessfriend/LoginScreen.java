package edu.sjsu.android.myfitnessfriend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener
{
    private EditText email;
    private EditText password;
    private Button login;
    private Button signUp;
    private FirebaseAuth mAuth;
   // private FirebaseAuth.AuthStateListener mAuthStateListener;
  //  FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        email= (EditText)findViewById(R.id.emailField);
        password= (EditText)findViewById(R.id.passwordField);

        login= (Button)findViewById(R.id.loginButton);
        signUp= (Button)findViewById(R.id.signUp);

        login.setOnClickListener(this);
        signUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view)
    {
        if(view.equals(login))
        {
            String emailStr = email.getText().toString();
            String pwdStr = password.getText().toString();
            mAuth.signInWithEmailAndPassword(emailStr, pwdStr);

            if(TextUtils.isEmpty(emailStr)) {
                Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(pwdStr)) {
                Toast.makeText(getApplicationContext(), "Please enter password...", Toast.LENGTH_LONG).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(emailStr, pwdStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Login Successful...", Toast.LENGTH_LONG).show();
                        Intent signUp = new Intent(LoginScreen.this, MainScreen.class);
                        startActivity(signUp);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed...", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else if(view.equals(signUp))
        {
            Intent signUp = new Intent(LoginScreen.this, SignUpScreen.class);
            startActivity(signUp);
        }
    }
}
