package edu.sjsu.android.myfitnessfriend;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class GetNutritionsActivity extends AppCompatActivity implements View.OnClickListener {
    public Button btn_Quantity;
    public Button btn_Confirm;
    public EditText et_FoodQality;
    private ListView listview;
    private EditText et_foodname;
    private EditText et_calories;
    private EditText et_totalfat;
    private EditText et_transfat;
    private EditText et_protein;
    private EditText et_cholesterol;
    private EditText et_fiber;
    private EditText et_sodium;
    private String selId;
    public static String ID;
    private ArrayList<Map<String, Object>> data;
    private Map<String, Object> item;
    private Cursor cursor;
    private SimpleAdapter listAdapter;

    int nCalories;
    int nTotalFat;
    int nTransFat;
    int nProtein;
    int nCholesterol;
    int nFiber;
    int nSodium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getnutrition_layout);
        listview = (ListView) findViewById(R.id.listView);
        btn_Quantity=(Button)findViewById(R.id.btnQuantity);
        btn_Confirm=(Button)findViewById(R.id.btnConfirm);
        btn_Quantity.setOnClickListener(this);
        btn_Confirm.setOnClickListener(this);
        et_FoodQality=(EditText)findViewById(R.id.txtFoodQuality) ;

        et_foodname=(EditText)findViewById(R.id.txtFoodName);
        et_calories=(EditText)findViewById(R.id.txtCalories);
        et_totalfat=(EditText)findViewById(R.id.txtTotalFat);
        et_transfat=(EditText)findViewById(R.id.txtTransFat);
        et_protein=(EditText)findViewById(R.id.txtProtein);
        et_cholesterol=(EditText)findViewById(R.id.txtCholesterol);
        et_fiber=(EditText)findViewById(R.id.txtFiber);
        et_sodium=(EditText)findViewById(R.id.txtSodium);
        data = new ArrayList<Map<String, Object>>();
        dbFindAll(); /////?????

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Map<String, Object> listItem = (Map<String, Object>) listview.getItemAtPosition(position);
                et_foodname.setText((String) listItem.get("foodname"));
                et_calories.setText((String) listItem.get("calories"));
                et_totalfat.setText((String) listItem.get("totalfat"));
                et_transfat.setText((String) listItem.get("transfat"));
                et_protein.setText((String) listItem.get("protein"));
                et_cholesterol.setText((String) listItem.get("cholesterol"));
                et_fiber.setText((String) listItem.get("fiber"));
                et_sodium.setText((String) listItem.get("sodium"));
                selId = (String) listItem.get("_id");
                ID= (String) listItem.get("_id");
                Log.i("mydbDemo", "id=" + selId);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {



        if(v.equals(btn_Quantity)){
            Integer n = 1;
            if ((et_FoodQality.getText().length() != 0)) {
                n = Integer.parseInt(String.valueOf(et_FoodQality.getText()));
            }
            Integer cal = Integer.parseInt(et_calories.getText().toString())* n/100;
            et_calories.setText(cal.toString());

            Integer totalfat = Integer.parseInt(et_totalfat.getText().toString())* n/100;
            et_totalfat.setText(totalfat.toString());

            Integer transFat = Integer.parseInt(et_transfat.getText().toString())* n/100;
            et_transfat.setText(transFat.toString());

            Integer pro = Integer.parseInt(et_protein.getText().toString())* n/100;
            et_protein.setText(pro.toString());

            Integer chol = Integer.parseInt(et_cholesterol.getText().toString())* n/100;
            et_cholesterol.setText(chol.toString());

            Integer fiber = Integer.parseInt(et_fiber.getText().toString())* n/100;
            et_fiber.setText(fiber.toString());

            Integer sod = Integer.parseInt(et_sodium.getText().toString())* n/100;
            et_sodium.setText(sod.toString());
        }


        if(v.equals(btn_Confirm)){

            nCalories=Integer.parseInt(String.valueOf(et_calories.getText()));

            nTotalFat=Integer.parseInt(String.valueOf(et_totalfat.getText()));

            nTransFat=Integer.parseInt(String.valueOf(et_transfat.getText()));

            nProtein=Integer.parseInt(String.valueOf(et_protein.getText()));

            nCholesterol=Integer.parseInt(String.valueOf(et_cholesterol.getText()));

            nFiber=Integer.parseInt(String.valueOf(et_fiber.getText()));

            nSodium=Integer.parseInt(String.valueOf(et_sodium.getText()));

            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Date date = new Date();
            String today = date.getYear()+1900 + "-" + (1+date.getMonth()) + "-" + date.getDate();


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference().child("Member");
            Calorie cal = new Calorie();
            cal.setnCalories(nCalories);
            cal.setnCholesterol(nCholesterol);
            cal.setnFiber(nFiber);
            cal.setnProtein(nProtein);
            cal.setnSodium(nSodium);
            cal.setnTotalFat(nTotalFat);
            cal.setnTransFat(nTransFat);
            ref.child(uId).child(today).push().setValue(cal);


            Intent nutritionalResults = new Intent(GetNutritionsActivity.this, MainScreen.class);
            nutritionalResults.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(nutritionalResults);
        }

    }



    protected void dbFindAll() {
        data.clear();
        String URL = "content://" + getString(R.string.provider) + "/foods";
        Uri foods = Uri.parse(URL);
        Cursor cursor = getContentResolver().query(foods, null, null, null, "foodname");
        cursor.moveToFirst();
        Toast.makeText(getApplicationContext(), "Moved is finished", Toast.LENGTH_SHORT).show();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(0);
            String foodname = cursor.getString(1);
            String calories = cursor.getString(2);
            String totalfat =  cursor.getString(3);
            String transfat =  cursor.getString(4);
            String protein = cursor.getString(5);
            String cholesterol = cursor.getString(6);
            String fiber =  cursor.getString(7);
            String sodium = cursor.getString(8);

            item = new HashMap<String, Object>();
            item.put("_id", id);
            item.put("foodname", foodname);
            item.put("calories", calories);
            item.put("totalfat", totalfat);
            item.put("transfat", transfat);
            item.put("protein", protein);
            item.put("cholesterol", cholesterol);
            item.put("fiber", fiber);
            item.put("sodium", sodium);
            data.add(item);
            cursor.moveToNext();
        }
        showList();
    }


    private void showList() {
        listAdapter = new SimpleAdapter(this, data,
                R.layout.listview, new String[]{"_id", "foodname", "calories",
                "totalfat","transfat","protein","cholesterol","fiber","sodium"},
                new int[]{R.id.tvID, R.id.tvFoodName, R.id.tvCalories, R.id.tvTotalFat,
                        R.id.tvTransFat, R.id.tvProtein, R.id.tvCholesterol, R.id.tvFiber,
                        R.id.tvSodium});
        listview.setAdapter(listAdapter);
    }
}
