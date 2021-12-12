package edu.sjsu.android.myfitnessfriend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class CalorieGetter extends AppCompatActivity implements View.OnClickListener
{
    private static final String NUTRITION_VALUE = "https://www.nutritionvalue.org/search.php?food_query=";
    private WebView searchResults;
    private EditText cals;
    private EditText carbs;
    private EditText fats;
    private EditText pros;
    private Button enter;
    private DatabaseReference ref;

    private Bundle b;

    FirebaseDatabase database;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_getter);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        searchResults = (WebView)findViewById(R.id.results) ;
        b = getIntent().getExtras().getBundle("extra");
        String search = b.getString("name");
        searchResults.getSettings().setJavaScriptEnabled(true);
        searchResults.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        searchResults.loadUrl(NUTRITION_VALUE+ search);
        searchResults.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String newUrl)
            {
                searchResults.getSettings().setJavaScriptEnabled(true);
                searchResults.loadUrl(newUrl);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url)
            {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }
        });

        cals = (EditText)findViewById(R.id.calCount);
        carbs = (EditText)findViewById(R.id.carbCount);
        fats = (EditText)findViewById(R.id.fatCount);
        pros = (EditText)findViewById(R.id.proteinsCount);
        enter = (Button)findViewById(R.id.enter);
        enter.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(enter))
        {

            int calorieCount = Integer.valueOf(cals.getText().toString());
            int carbCount = Integer.valueOf(carbs.getText().toString());
            int fatCount = Integer.valueOf(fats.getText().toString());
            int proteinCount = Integer.valueOf(pros.getText().toString());
            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Date date = new Date();
            String today = date.getYear()+1900 + "-" + (1+date.getMonth()) + "-" + date.getDate();


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReference().child("Member");
            Calorie cal = new Calorie();
            cal.setnCalories(calorieCount);
            cal.setnCholesterol(carbCount);
            cal.setnFiber(proteinCount);
            cal.setnProtein(proteinCount);
            cal.setnSodium(proteinCount);
            cal.setnTotalFat(fatCount);
            cal.setnTransFat(fatCount);
            ref.child(uId).child(today).push().setValue(cal);

//            mDatabase = database.getReference("/users/user/calorieCounter/calories");
//            mDatabase.setValue(cals.getText().toString());
//
//            mDatabase = database.getReference("/users/user/calorieCounter/carbs");
//            mDatabase.setValue(carbs.getText().toString());
//
//            mDatabase = database.getReference("/users/user/calorieCounter/fats");
//            mDatabase.setValue(fats.getText().toString());
//
//            mDatabase = database.getReference("/users/user/calorieCounter/pros");
//            mDatabase.setValue(pros.getText().toString());



            Intent nutritionalResults = new Intent(CalorieGetter.this, MainScreen.class);
//            Bundle newBundle = new Bundle();
//            newBundle.putFloat("Calories", calorieCount);
//            newBundle.putFloat("Carbs", carbCount);
//            newBundle.putFloat("Fats", fatCount);
//            newBundle.putFloat("Proteins", proteinCount);
//            nutritionalResults.putExtra("all", newBundle);
            nutritionalResults.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(nutritionalResults);
        }
    }
}
