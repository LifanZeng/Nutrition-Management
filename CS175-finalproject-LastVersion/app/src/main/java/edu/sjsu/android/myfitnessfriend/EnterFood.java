package edu.sjsu.android.myfitnessfriend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class EnterFood extends AppCompatActivity implements View.OnClickListener
{
    public EditText edtFoodName;
    public Button btnSendFoodname;
    public Button btnFoodDdataManager;
    public Bundle title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_food);
        //edtFoodName = (EditText) findViewById(R.id.foodName);
        btnSendFoodname=(Button)findViewById(R.id.button1);
        btnSendFoodname.setOnClickListener(this);
        btnFoodDdataManager=(Button)findViewById(R.id.button2);
        btnFoodDdataManager.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(btnSendFoodname))
        {
            Intent myIntent = new Intent(this, GetNutritionsActivity.class);
            //myIntent.putExtra("extra_foodName", edtFoodName.getText().toString());
            this.startActivity(myIntent);
        }


        if(v.equals(btnFoodDdataManager))
        {
            Intent myIntent2 = new Intent(this, FoodDataManagerActivity.class);
            this.startActivity(myIntent2);
        }




    }
}
