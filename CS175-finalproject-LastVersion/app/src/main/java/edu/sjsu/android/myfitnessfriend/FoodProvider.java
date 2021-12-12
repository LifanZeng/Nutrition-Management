package edu.sjsu.android.myfitnessfriend;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class FoodProvider extends ContentProvider {
    static final String PROVIDER_NAME = "edu.sjsu.android.myfitnessfriend.foodsDatabase";
    static final String URL = "content://" + PROVIDER_NAME + "/foodsTable";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String _ID = "_id";
    static final String FOODNAME = "foodname";
    static final String CALORIES = "calories";
    static final String TOTALFAT = "totalfat";
    static final String TRANSFAT = "transfat";
    static final String PROTEIN = "protein";
    static final String CHOLESTEROL = "cholesterol";
    static final String FIBER = "fiber";
    static final String SODIUM = "sodium";
    private static HashMap<String, String> FOODS_PROJECTION_MAP; ////what's the meaning?
    static final int FOODS = 1;
    static final int FOOD_ID = 2;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "foods", FOODS);
        uriMatcher.addURI(PROVIDER_NAME, "foods/#", FOOD_ID);
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "foodsDatabase";
    static final String FOODS_TABLE_NAME = "foodsTable";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + FOODS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " foodname TEXT NOT NULL, " +
                    " calories TEXT NOT NULL," +
                    " totalfat TEXT NOT NULL, " +
                    " transfat TEXT NOT NULL," +
                    " protein TEXT NOT NULL, " +
                    " cholesterol TEXT NOT NULL," +
                    " fiber TEXT NOT NULL," +
                    " sodium TEXT NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + FOODS_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowID = db.insert( FOODS_TABLE_NAME, "", values);
        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(FOODS_TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case FOODS:
                qb.setProjectionMap(FOODS_PROJECTION_MAP);
                break;
            case FOOD_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            sortOrder = FOODNAME;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case FOODS:
                count = db.delete(FOODS_TABLE_NAME, selection, selectionArgs);
                break;
            case FOOD_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( FOODS_TABLE_NAME, _ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case FOODS:
                count = db.update(FOODS_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case FOOD_ID:
                count = db.update(FOODS_TABLE_NAME, values, _ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case FOODS:
                return "vnd.android.cursor.dir/vnd.example.students";
            case FOOD_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
