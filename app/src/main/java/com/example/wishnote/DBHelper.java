package com.example.wishnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Listdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Listdetails(name TEXT primary key, price TEXT, descr TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists Listdetails");
    }

    public void open() throws SQLException{
        SQLiteDatabase db = this.getWritableDatabase();
    }
    public boolean insertlistdata(String name, String price, String descr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("descr", descr);
        long result =db.insert("Listdetails", null, contentValues);
        if (result == -1 ){
            return false;
        } else {
            return true;
        }
    }

    public boolean deletelistdata(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Listdetails where name = ? ", new String[] {name});
        if (cursor.getCount()>0){
            long result =db.delete("Listdetails", "name=?", new String[] {name});
            if (result == 1 ){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public ArrayList<Model> getAll(String orderBy){

        ArrayList<Model> arrayList = new ArrayList<>();
        Model model = new Model("", null, "");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Listdetails order by "+orderBy, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            for (int j = 0; j < cursor.getColumnCount(); j++){
                cursor.moveToPosition(i);
                model = new Model (
                        "" + cursor.getString(0),
                        "" + cursor.getString(1),
                        "" + cursor.getString(2)
                );
            }arrayList.add(model);
        }
        return arrayList;
    }
}
