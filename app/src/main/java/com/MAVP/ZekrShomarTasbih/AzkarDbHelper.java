package com.MAVP.ZekrShomarTasbih;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class AzkarDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Azkar_db";
    private static final int DB_VESION = 1 ;
    private static final String TABLE_AZKAR = "tb_Azkar";
    private static final String CMD = "CREATE TABLE IF NOT EXISTS '" + TABLE_AZKAR + "' ('"+
            Azkar.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '" +
            Azkar.KEY_ZEKR + "' TEXT, '"+
            Azkar.KEY_TRANSLATE + "' TEXT, '"+
            Azkar.KEY_FULLCOUNT + "' INTEGER, '"+
            Azkar.KEY_LASTCOUNT + "' INTEGER, '"+
            Azkar.KEY_SUGGESTED + "' INTEGER, '"+
            Azkar.KEY_SOUND + "' INTEGER)";
    private String[] AllColumns = {Azkar.KEY_ZEKR,Azkar.KEY_TRANSLATE,Azkar.KEY_LASTCOUNT,Azkar.KEY_FULLCOUNT,Azkar.KEY_SUGGESTED,Azkar.KEY_SOUND};

    public AzkarDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "  + TABLE_AZKAR);
        onCreate(sqLiteDatabase);
    }

    public void InsertAzkar(Azkar azkar){
        ContentValues values = new ContentValues();
        values.put(Azkar.KEY_ZEKR,azkar.getZekr());
        values.put(Azkar.KEY_TRANSLATE,azkar.getTranslate());
        values.put(Azkar.KEY_FULLCOUNT,azkar.getFullCount());
        values.put(Azkar.KEY_LASTCOUNT,azkar.getLastCount());
        values.put(Azkar.KEY_SUGGESTED,azkar.getSuggested());
        values.put(Azkar.KEY_SOUND,azkar.getSound());


        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_AZKAR,null,values);
        if (db.isOpen()){
            db.close();
        }
    }

    public List<Azkar> GetAzkarZekrPage(){
        SQLiteDatabase db = getReadableDatabase();
        List<Azkar> AzkarList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM '"+TABLE_AZKAR+"' WHERE "+ Azkar.KEY_ID + " > 10 ",null) ;
        if (cursor.moveToFirst()){
            do {
                Azkar azkar =  new Azkar();
                azkar.setZekr(cursor.getString(cursor.getColumnIndex(Azkar.KEY_ZEKR)));
                azkar.setTranslate(cursor.getString(cursor.getColumnIndex(Azkar.KEY_TRANSLATE)));
                azkar.setFullCount(cursor.getInt(cursor.getColumnIndex(Azkar.KEY_FULLCOUNT)));
                azkar.setLastCount(cursor.getInt(cursor.getColumnIndex(Azkar.KEY_LASTCOUNT)));
                azkar.setSuggested(cursor.getInt(cursor.getColumnIndex(Azkar.KEY_SUGGESTED)));
                azkar.setSound(cursor.getInt(cursor.getColumnIndex(Azkar.KEY_SOUND)));
                azkar.setID(cursor.getInt(cursor.getColumnIndex(Azkar.KEY_ID)));
                AzkarList.add(azkar);
            }while (cursor.moveToNext());
        }
        cursor.close();
        if (db.isOpen()){
            db.close();
        }
        return AzkarList;
    }


    public Azkar GetAzkar (int ID){
        SQLiteDatabase db = getReadableDatabase();
        Azkar azkar = new Azkar();
        Cursor cursor = db.query(TABLE_AZKAR,AllColumns,Azkar.KEY_ID +" = ?", new String[] {String.valueOf(ID)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                azkar.setZekr(cursor.getString(cursor.getColumnIndex(Azkar.KEY_ZEKR)));
                azkar.setTranslate(cursor.getString(cursor.getColumnIndex(Azkar.KEY_TRANSLATE)));
                azkar.setFullCount(cursor.getInt(cursor.getColumnIndex(Azkar.KEY_FULLCOUNT)));
                azkar.setLastCount(cursor.getInt(cursor.getColumnIndex(Azkar.KEY_LASTCOUNT)));
                azkar.setSuggested(cursor.getInt(cursor.getColumnIndex(Azkar.KEY_SUGGESTED)));
                azkar.setSound(cursor.getInt(cursor.getColumnIndex(Azkar.KEY_SOUND)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        if (db.isOpen()){
            db.close();
        }
        return azkar ;
    }

    public void Update(int ID , ContentValues contentValues){
        SQLiteDatabase db = getWritableDatabase() ;
        db.update(TABLE_AZKAR,contentValues,Azkar.KEY_ID + " = " +ID ,null);
    }


    public void DeleteAzkar(int ZekrId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_AZKAR,Azkar.KEY_ID + " = ?", new String[] {String.valueOf(ZekrId)});
        if (db.isOpen()){
            db.close();
        }
    }
}
