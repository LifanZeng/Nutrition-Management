package edu.sjsu.android.myfitnessfriend;

import android.content.ContentValues;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodDataManagerActivity extends AppCompatActivity implements View.OnClickListener {
    public Button btn_Add;
    public Button btn_Upgrade;
    public Button btn_Delete;
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
    private ArrayList<Map<String, Object>> data;
    private Map<String, Object> item;
    private Cursor cursor;
    private SimpleAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datamanager_layout);
        listview = (ListView) findViewById(R.id.listView);
        btn_Add=(Button)findViewById(R.id.btnAdd);
        btn_Upgrade=(Button)findViewById(R.id.btnUpgrade);
        btn_Delete=(Button)findViewById(R.id.btnDelete);
        btn_Add.setOnClickListener(this);
        btn_Upgrade.setOnClickListener(this);
        btn_Delete.setOnClickListener(this);
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
                Log.i("mydbDemo", "id=" + selId);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {


        if(v.equals(btn_Add)){
            ContentValues values = new ContentValues();
            values.put(FoodProvider.FOODNAME,
                    ((EditText)findViewById(R.id.txtFoodName)).getText().toString().trim());
            values.put(FoodProvider.CALORIES,
                    ((EditText)findViewById(R.id.txtCalories)).getText().toString().trim());
            values.put(FoodProvider.CHOLESTEROL,
                    ((EditText)findViewById(R.id.txtCholesterol)).getText().toString().trim());
            values.put(FoodProvider.TOTALFAT,
                    ((EditText)findViewById(R.id.txtTotalFat)).getText().toString().trim());
            values.put(FoodProvider.TRANSFAT,
                    ((EditText)findViewById(R.id.txtTransFat)).getText().toString().trim());
            values.put(FoodProvider.PROTEIN,
                    ((EditText)findViewById(R.id.txtProtein)).getText().toString().trim());
            values.put(FoodProvider.FIBER,
                    ((EditText)findViewById(R.id.txtFiber)).getText().toString().trim());
            values.put(FoodProvider.SODIUM,
                    ((EditText)findViewById(R.id.txtSodium)).getText().toString().trim());
            Uri uri = getContentResolver().insert(
                    FoodProvider.CONTENT_URI, values);
            Log.i("zDbDemo", "Data inserted");
            dbFindAll();
            et_foodname.setText("");
            et_calories.setText("");
            et_totalfat.setText("");
            et_transfat.setText("");
            et_protein.setText("");
            et_cholesterol.setText("");
            et_fiber.setText("");
            et_sodium.setText("");
        }


        if(v.equals(btn_Upgrade)){
            ContentValues values = new ContentValues();
            values.put(FoodProvider.FOODNAME,
                    ((EditText)findViewById(R.id.txtFoodName)).getText().toString().trim());
            values.put(FoodProvider.CALORIES,
                    ((EditText)findViewById(R.id.txtCalories)).getText().toString().trim());
            values.put(FoodProvider.CHOLESTEROL,
                    ((EditText)findViewById(R.id.txtCholesterol)).getText().toString().trim());
            values.put(FoodProvider.TOTALFAT,
                    ((EditText)findViewById(R.id.txtTotalFat)).getText().toString().trim());
            values.put(FoodProvider.TRANSFAT,
                    ((EditText)findViewById(R.id.txtTransFat)).getText().toString().trim());
            values.put(FoodProvider.PROTEIN,
                    ((EditText)findViewById(R.id.txtProtein)).getText().toString().trim());
            values.put(FoodProvider.FIBER,
                    ((EditText)findViewById(R.id.txtFiber)).getText().toString().trim());
            values.put(FoodProvider.SODIUM,
                    ((EditText)findViewById(R.id.txtSodium)).getText().toString().trim());
            String where = "_id=" + selId;
            String URL = "content://" + getString(R.string.provider) + "/foods/"+ selId;
            Uri foods = Uri.parse(URL);
            int i = getContentResolver().update(
                    foods, values, where, null);
            if (i > 0)
                Log.i("zDbDemo", "Modified！");
            else
                Log.i("zDbDemo", "Failed to modify!");
            dbFindAll();
            et_foodname.setText("");
            et_calories.setText("");
            et_totalfat.setText("");
            et_transfat.setText("");
            et_protein.setText("");
            et_cholesterol.setText("");
            et_fiber.setText("");
            et_sodium.setText("");
        }


        if(v.equals(btn_Delete)){
            String where = "_id=" + selId;
            String URL = "content://" + getString(R.string.provider) + "/foods/";
            Uri foods = Uri.parse(URL);
            int i = getContentResolver().delete(foods, where, null);
            if (i > 0)
                Log.i("zDbDemo", "Data deleted");
            else
                Log.i("zDbDemo", "Delete failed");
            dbFindAll();
            et_foodname.setText("");
            et_calories.setText("");
            et_totalfat.setText("");
            et_transfat.setText("");
            et_protein.setText("");
            et_cholesterol.setText("");
            et_fiber.setText("");
            et_sodium.setText("");
        }

    }



    protected void dbFindAll() {
        data.clear();
        String URL = "content://" + getString(R.string.provider) + "/foods";
        Uri foods = Uri.parse(URL);
        Cursor cursor = getContentResolver().query(foods, null, null, null, "foodname");
        cursor.moveToFirst();
        //Toast.makeText(getApplicationContext(), "Moved is finished", Toast.LENGTH_SHORT).show();
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
        //public SimpleAdapter (Context context, ///Context: The context where the View associated with this SimpleAdapter is running
        //                List<? extends Map<String, ?>> data, ///List: A List of Maps. Each entry in the List corresponds to one row in the list. The Maps contain the data for each row, and should include all the entries specified in "from"
        //                int resource,   ///int: Resource identifier of a view layout that defines the views for this list item. The layout file should include at least those named views defined in "to"
        //                String[] from, ///String: A list of column names that will be added to the Map associated with each item.
        //                int[] to)     ///int: The views that should display column in the "from" parameter. These should all be TextViews. The first N views in this list are given the values of the first N columns in the from parameter.
        listview.setAdapter(listAdapter);
    }
}
