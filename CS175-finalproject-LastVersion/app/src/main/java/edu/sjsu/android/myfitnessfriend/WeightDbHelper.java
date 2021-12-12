package edu.sjsu.android.myfitnessfriend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WeightDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fitnessfriend";
    public static final String DATABASE_TABLE = "weights";
    public static final String DATABASE_TABLE_HIST = "foodtracker";

    public static final int DATABASE_VERSION = 1;

    public static final String ID_COLUMN = "id";
    public static final String DATE_COLUMN = "date";
    public static final String WEIGHT_COLUMN = "weight";

    public static final String UID = "uid";
    public static final String CHOL_CLOUMN = "cholestrol";
    public static final String CAL_COLUMN = "calories";
    public static final String PRO_COLUMN = "protein";
    public static final String TRANSFAT_COLUMN = "transfat";
    public static final String TOTALFAT_COLUMN = "totalfat";
    public static final String FIBER_COLUMN = "fiber";
    public static final String SODIUM_COLUMN = "sodium";



    private static final String DATABASE_CREATE = String.format
            ("CREATE TABLE %s (" +"  %s integer primary key autoincrement, "
                    +"  %s text," +"  %s text )",
                    DATABASE_TABLE, ID_COLUMN, DATE_COLUMN, WEIGHT_COLUMN);

    private static final String DATABASE_CREATE1 = String.format
            ("CREATE TABLE %s (" +"  %s integer primary key autoincrement, "
                            +"  %s text," +"  %s text, " + "  %s text, " + "  %s text, "+"  %s text, "+"  %s text, "+"  %s text, "+"  %s text, "+"  %s text )",
                    DATABASE_TABLE_HIST, ID_COLUMN, DATE_COLUMN,UID,CHOL_CLOUMN,CAL_COLUMN,PRO_COLUMN,TRANSFAT_COLUMN,TOTALFAT_COLUMN,FIBER_COLUMN,SODIUM_COLUMN);

    public WeightDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_HIST);
        onCreate(db);
    }
}
