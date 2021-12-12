package edu.sjsu.android.myfitnessfriend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class ConfirmationCode extends AppCompatActivity implements View.OnClickListener
{
    private EditText confirmCode;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_code);

        confirmCode = (EditText)findViewById(R.id.confirmCode);
        confirm = (Button)findViewById(R.id.confirm);

        confirm.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        if(view.equals(confirm))
        {
            //validate confirmation code
            Intent goBack = new Intent(ConfirmationCode.this, LoginScreen.class);//)
            goBack.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(goBack);
        }
    }
}
