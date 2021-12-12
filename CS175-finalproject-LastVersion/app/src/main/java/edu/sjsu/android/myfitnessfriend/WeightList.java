package edu.sjsu.android.myfitnessfriend;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeightList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;

    List<String[]> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_list);
        recyclerView=(RecyclerView) findViewById(R.id.my_recycler_view);



        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        SQLiteDatabase db = new WeightDbHelper(this).getReadableDatabase();
        String[] resultColumns = {WeightDbHelper.ID_COLUMN, WeightDbHelper.DATE_COLUMN, WeightDbHelper.WEIGHT_COLUMN};
        Cursor cursor = db.query(WeightDbHelper.DATABASE_TABLE, resultColumns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String [] wts = new String[2];
            wts[0] = cursor.getString(1);
            wts[1] = cursor.getString(2);

            list.add(wts);
        }
        mAdapter=new MyAdapter(list);
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target)
            {
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,int swipeDir)
            {}

        };
        ItemTouchHelper itemTouch=new ItemTouchHelper(simpleItemTouchCallback);
        itemTouch.attachToRecyclerView(recyclerView);
    }
}
