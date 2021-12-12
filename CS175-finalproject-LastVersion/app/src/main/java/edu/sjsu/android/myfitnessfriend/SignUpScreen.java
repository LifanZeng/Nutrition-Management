package edu.sjsu.android.myfitnessfriend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpScreen extends AppCompatActivity implements View.OnClickListener
{
    private EditText newEmail;
    private EditText newPassword;
    private EditText newPasswordAgain;
    private Button  signUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        newEmail = (EditText)findViewById(R.id.newEmail);
        newPassword = (EditText)findViewById(R.id.newPassword);
        newPasswordAgain = (EditText)findViewById(R.id.newPasswordAgain);

        signUp = (Button)findViewById(R.id.signUp);
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        if (view.equals(signUp))
        {
            if(!newPassword.toString().equals(newPassword.toString())) {
                Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                String email = newEmail.getText().toString();
                String password = newPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "SignUp Successful",
                                    Toast.LENGTH_SHORT).show();
                            Intent toConfirm = new Intent(SignUpScreen.this, LoginScreen.class);
                            startActivity(toConfirm);
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }

}
