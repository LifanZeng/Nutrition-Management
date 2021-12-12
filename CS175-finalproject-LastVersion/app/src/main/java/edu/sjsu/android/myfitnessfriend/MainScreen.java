package edu.sjsu.android.myfitnessfriend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MainScreen extends AppCompatActivity implements View.OnClickListener
{
    private Button enterFoodBtn;
    private Button weight;
    private Button prevIntakeBtn;

    private TextView calorieCount;

    private TextView cholestrolCount;

    private TextView totalfatCount;

    private TextView proteinCount;

    private TextView fiberCount;

    private TextView sodiumCount;

    private TextView transfatCount;

    private TextView userId;

    private Intent enterFood;

    private Integer caloriesInt;
    private Integer totalFatInt;
    private Integer transFatInt;
    private Integer sodiumInt;
    private Integer fiberInt;
    private Integer cholestrolInt;
    private Integer proteinInt;


    FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        enterFoodBtn = (Button)findViewById(R.id.enterFood);

        weight = (Button)findViewById(R.id.weight);

        prevIntakeBtn = (Button) findViewById(R.id.prevIntake);

        calorieCount = (TextView)findViewById(R.id.calorieTracker);

        cholestrolCount = (TextView)findViewById(R.id.carbTracker);

        totalfatCount = (TextView)findViewById(R.id.totalFatTracker);

        transfatCount = (TextView)findViewById(R.id.transFatTracker);

        proteinCount = (TextView)findViewById(R.id.proteinTracker);

        sodiumCount = (TextView)findViewById(R.id.sodiumTracker);

        fiberCount = (TextView)findViewById(R.id.fiberTracker);

        userId = (TextView) findViewById(R.id.userId);
        String userName = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userId.setText(userName);

        database = FirebaseDatabase.getInstance();
        Date date = new Date();
        String today = date.getYear()+1900 + "-" + (1+date.getMonth()) + "-" + date.getDate();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseRef = database.getReference().child("Member").child(uid).child(today);



        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Date date = new Date();
//                String today = date.getYear()+1900 + "-" + (1+date.getMonth()) + "-" + date.getDate();
//                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                caloriesInt = 0;
                cholestrolInt = 0;
                sodiumInt = 0;
                fiberInt = 0;
                proteinInt = 0;
                transFatInt = 0;
                totalFatInt = 0;

                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                    Calorie cal = postSnapShot.getValue(Calorie.class);

                    caloriesInt += cal.getnCalories();
                    cholestrolInt += cal.getnCholesterol();
                    sodiumInt += cal.getnSodium();
                    fiberInt += cal.getnFiber();
                    proteinInt += cal.getnProtein();
                    transFatInt += cal.getnTransFat();
                    totalFatInt += cal.getnTotalFat();

                }

                calorieCount.setText(caloriesInt.toString());
                cholestrolCount.setText(cholestrolInt.toString());
                totalfatCount.setText(totalFatInt.toString());
                proteinCount.setText(proteinInt.toString());
                sodiumCount.setText(sodiumInt.toString());
                transfatCount.setText(transFatInt.toString());
                fiberCount.setText(fiberInt.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        enterFoodBtn.setOnClickListener(this);
        weight.setOnClickListener(this);
        prevIntakeBtn.setOnClickListener(this);
        enterFood = new Intent(MainScreen.this, EnterFood.class);



    }



    @Override
    public void onClick(View v)
    {
        Bundle data = new Bundle();
        if(v.equals(enterFoodBtn))
        {

            data.putString("meal", "Food");
            enterFood.putExtra("bundle", data);
            startActivity(enterFood);

        }
        else if(v.equals(weight))
        {

            Intent checkWeight = new Intent(MainScreen.this, logWeight.class);
            startActivity(checkWeight);

        } else if(v.equals(prevIntakeBtn)) {

            Intent prevIntake = new Intent(MainScreen.this, PreviousIntake.class);
            startActivity(prevIntake);
        }
    }

    /*@Override
    protected void onResume()
    {
        *//*super.onResume();
        if(pass != null)
        {
            Bundle newPass = pass.getBundle("all");

            calFromIntent = newPass.getFloat("Calories");
            carbFromIntent = newPass.getFloat("Carbs");
            fatFromIntent = newPass.getFloat("Fats");
            proFromIntent = newPass.getFloat("Proteins");
            //add to current values
            calTemp = calOld + calFromIntent;
            carbTemp = carbOld + carbFromIntent;
            fatTemp = fatOld + fatFromIntent;
            proTemp = proOld + proFromIntent;


            calorieCount.setText(Float.toString(calTemp));
            carbCount.setText(Float.toString(carbTemp));
            fatCount.setText(Float.toString(fatTemp));
            proteinCount.setText(Float.toString(proTemp));
        }*//*
    }*/

    @Override
    protected void onStop()
    {
        super.onStop();

    }

}
