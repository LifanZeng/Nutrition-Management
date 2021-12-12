package edu.sjsu.android.myfitnessfriend;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class PreviousIntake extends AppCompatActivity {

    EditText eText;
    DatePickerDialog picker;
    Button btnGet;

    private TextView calorieCount;

    private TextView cholestrolCount;

    private TextView totalfatCount;

    private TextView proteinCount;

    private TextView fiberCount;

    private TextView sodiumCount;

    private TextView transfatCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_intake);

        eText=(EditText) findViewById(R.id.editText1);

        calorieCount = (TextView)findViewById(R.id.CaloriesVal);

        cholestrolCount = (TextView)findViewById(R.id.cholestrolVal);

        totalfatCount = (TextView)findViewById(R.id.TotalFatVal);

        transfatCount = (TextView)findViewById(R.id.TransFatVal);

        proteinCount = (TextView)findViewById(R.id.ProteinVal);

        sodiumCount = (TextView)findViewById(R.id.SodiumVal);

        fiberCount = (TextView)findViewById(R.id.FiberVal);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabaseRef = database.getReference().child("Member").child(uid);
        final SQLiteDatabase db = new WeightDbHelper(this).getWritableDatabase();


        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Integer caloriesInt = 0;
                Integer cholestrolInt = 0;
                Integer sodiumInt = 0;
                Integer fiberInt = 0;
                Integer proteinInt = 0;
                Integer transFatInt = 0;
                Integer totalFatInt = 0;

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Calorie cal = ds.getValue(Calorie.class);
                    caloriesInt += cal.getnCalories();
                    cholestrolInt += cal.getnCholesterol();
                    sodiumInt += cal.getnSodium();
                    fiberInt += cal.getnFiber();
                    proteinInt += cal.getnProtein();
                    transFatInt += cal.getnTransFat();
                    totalFatInt += cal.getnTotalFat();
                }

                    ContentValues newValues = new ContentValues();
                    newValues.put(WeightDbHelper.DATE_COLUMN, dataSnapshot.getKey().toString());
                    newValues.put(WeightDbHelper.UID, FirebaseAuth.getInstance().getCurrentUser().getUid());
                    newValues.put(WeightDbHelper.CAL_COLUMN, caloriesInt);
                    newValues.put(WeightDbHelper.CHOL_CLOUMN, cholestrolInt);
                    newValues.put(WeightDbHelper.SODIUM_COLUMN, sodiumInt);
                    newValues.put(WeightDbHelper.FIBER_COLUMN, fiberInt);
                    newValues.put(WeightDbHelper.PRO_COLUMN, proteinInt);
                    newValues.put(WeightDbHelper.TRANSFAT_COLUMN, transFatInt);
                    newValues.put(WeightDbHelper.TOTALFAT_COLUMN, totalFatInt);

                    db.insert(WeightDbHelper.DATABASE_TABLE_HIST, null, newValues);



                }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//
//                    Integer caloriesInt = 0;
//                    Integer cholestrolInt = 0;
//                    Integer sodiumInt = 0;
//                    Integer fiberInt = 0;
//                    Integer proteinInt = 0;
//                    Integer transFatInt = 0;
//                    Integer totalFatInt = 0;
//
//                    for(DataSnapshot postSnapShot: ds.getChildren()) {
//                        Calorie cal = postSnapShot.getValue(Calorie.class);
//                        caloriesInt += cal.getnCalories();
//                        cholestrolInt += cal.getnCholesterol();
//                        sodiumInt += cal.getnSodium();
//                        fiberInt += cal.getnFiber();
//                        proteinInt += cal.getnProtein();
//                        transFatInt += cal.getnTransFat();
//                        totalFatInt += cal.getnTotalFat();
//                    }
//
//
//                        ContentValues newValues = new ContentValues();
//                        newValues.put(WeightDbHelper.DATE_COLUMN, ds.getKey().toString());
//                        newValues.put(WeightDbHelper.CAL_COLUMN, caloriesInt);
//                        newValues.put(WeightDbHelper.CHOL_CLOUMN, cholestrolInt);
//                        newValues.put(WeightDbHelper.SODIUM_COLUMN, sodiumInt);
//                        newValues.put(WeightDbHelper.FIBER_COLUMN, fiberInt);
//                        newValues.put(WeightDbHelper.PRO_COLUMN, proteinInt);
//                        newValues.put(WeightDbHelper.TRANSFAT_COLUMN, transFatInt);
//                        newValues.put(WeightDbHelper.TOTALFAT_COLUMN, totalFatInt);
//
//                        db.insert(WeightDbHelper.DATABASE_TABLE_HIST, null, newValues);
//
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(PreviousIntake.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        SQLiteDatabase db1 = new WeightDbHelper(this).getReadableDatabase();
        btnGet=(Button)findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selection = "date =? and uid =?";
                String[] selectionArgs = {eText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid()};
                String[] resultColumns = {WeightDbHelper.DATE_COLUMN, WeightDbHelper.CAL_COLUMN, WeightDbHelper.CHOL_CLOUMN,
                        WeightDbHelper.PRO_COLUMN, WeightDbHelper.TOTALFAT_COLUMN, WeightDbHelper.TRANSFAT_COLUMN, WeightDbHelper.FIBER_COLUMN,WeightDbHelper.SODIUM_COLUMN};
                Cursor cursor = db.query(WeightDbHelper.DATABASE_TABLE_HIST, resultColumns, selection, selectionArgs, null, null, null);
                if(cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        calorieCount.setText(cursor.getString(1));
                        cholestrolCount.setText(cursor.getString(2));
                        totalfatCount.setText(cursor.getString(4));
                        proteinCount.setText(cursor.getString(3));
                        sodiumCount.setText(cursor.getString(7));
                        transfatCount.setText(cursor.getString(5));
                        fiberCount.setText(cursor.getString(6));

                    }
                } else {
                    calorieCount.setText("0.0");
                    cholestrolCount.setText("0.0");
                    totalfatCount.setText("0.0");
                    proteinCount.setText("0.0");
                    sodiumCount.setText("0.0");
                    transfatCount.setText("0.0");
                    fiberCount.setText("0.0");
                }
            }
        });

    }

}
