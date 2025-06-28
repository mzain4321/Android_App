package com.zain.myapp1.sqlhelpers;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProductDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "products";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PRICE = "price";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_PRICE + " REAL NOT NULL)";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop old table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // INSERT product
    public boolean insertProduct(String name, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_PRICE, price);
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    // READ all products
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // UPDATE product
    public boolean updateProduct(String id, String name, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_PRICE, price);
        int result = db.update(TABLE_NAME, cv, "id=?", new String[]{id});
        return result > 0;
    }

    // DELETE product
    public boolean deleteProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "id=?", new String[]{id});
        return result > 0;
    }
}
