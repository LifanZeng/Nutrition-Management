package edu.sjsu.android.myfitnessfriend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class logWeight extends AppCompatActivity
{
    private Button enter;
    private Button checkPrev;
    private EditText weight;
    private TextView date;
    private Bundle data;
    final static HashMap<String, String> input=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_weight);

        enter = (Button)findViewById(R.id.inputWeight);
        checkPrev = (Button)findViewById(R.id.viewPrevWeights);
        weight = (EditText) findViewById(R.id.enterWeight);
        date = (TextView)findViewById(R.id.todaysDate);
        SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd");

        String currentDate = myDate.format(new Date());
        date.setText(currentDate);
        data = new Bundle();
    }

    public void logWeightClick(View v) {
        Intent weightList = new Intent(logWeight.this, WeightList.class);
        startActivity(weightList);
    }
    public void addWeight(View v)
    {
        String currentDate = "Date: "+ date.getText().toString();
        String theWeight = "Weight: "+ weight.getText().toString();

        SQLiteDatabase db = new WeightDbHelper(this).getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(WeightDbHelper.DATE_COLUMN, currentDate);
        newValues.put(WeightDbHelper.WEIGHT_COLUMN, theWeight);
        db.insert(WeightDbHelper.DATABASE_TABLE, null, newValues);



        Toast.makeText(getApplicationContext(), "Weight logged", Toast.LENGTH_LONG).show();
    }
}
