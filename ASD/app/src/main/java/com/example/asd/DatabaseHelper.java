package com.example.asd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static String DB_PATH = "";
    private Context mContext;
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        dataBaseCheck();
    }
    private void dataBaseCheck()
    {
        File dbFile = new File(DB_PATH + DATABASE_NAME);

        if (!dbFile.exists())
        {
            dbCopy();
        }
    }
    private void dbCopy()
    {
        try
        {
            File folder = new File(DB_PATH);
            if (!folder.exists())
            {
                folder.mkdir();
            }

            InputStream inputStream = mContext.getAssets().open(DATABASE_NAME);
            String out_filename = DB_PATH + DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(out_filename);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = inputStream.read(mBuffer)) > 0)
            {
                outputStream.write(mBuffer, 0, mLength);
            }
            outputStream.flush();
            ;
            outputStream.close();
            inputStream.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(com.example.asd.DataBases.CreateDB._CREATE0);
        db.execSQL(com.example.asd.DataBases.CreateDB._CREATE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ com.example.asd.DataBases.CreateDB._TABLENAME0);
        db.execSQL("DROP TABLE IF EXISTS "+ com.example.asd.DataBases.CreateDB._TABLENAME1);
        onCreate(db);
    }
    public void create(){
        mDBHelper.onCreate(mDB);
    }
    public void close(){
        mDB.close();
    }

    //Delete DB in user_table
    public boolean deleteColumn1(Long id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(com.example.asd.DataBases.CreateDB._TABLENAME1, "_id="+id, null) > 0;
    }
    public boolean insertData( String[] nowData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("DBHelper nowdata 0: ",nowData[0]);
        Log.d("DBHelper nowdata 1: ",nowData[1]);
        Log.d("DBHelper nowdata 2: ",nowData[2]);
        Log.d("DBHelper nowdata 3: ",nowData[3]);
        Log.d("DBHelper nowdata 4: ",nowData[4]);
        Log.d("DBHelper nowdata 5: ",nowData[5]);
        Log.d("DBHelper nowdata 6: ",nowData[6]);
        values.put(com.example.asd.DataBases.CreateDB.NAME,nowData[0]);
        values.put(com.example.asd.DataBases.CreateDB.SUGAR,Long.parseLong(nowData[1]));
        values.put(com.example.asd.DataBases.CreateDB.ALCOHOL,Long.parseLong(nowData[2]));
        values.put(com.example.asd.DataBases.CreateDB.BODY,Long.parseLong(nowData[3]));
        values.put(com.example.asd.DataBases.CreateDB.UNIQUE_,Long.parseLong(nowData[4]));
        values.put(com.example.asd.DataBases.CreateDB.BASE,nowData[5]);
        values.put(com.example.asd.DataBases.CreateDB._ID,nowData[6]);
        long result = db.insert(com.example.asd.DataBases.CreateDB._TABLENAME1,null,values);
        if(result==-1){
            Log.d("Insert Error"," error");
            return false;
        }
        else{
            Log.d("Insert result", String.valueOf(result));
            return true;
        }
    }
    public boolean isFavorite(long _id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+com.example.asd.DataBases.CreateDB._TABLENAME1 +" WHERE _ID = "+_id,null);
        if(cursor.getCount()>=1){
            return true;
        }
        else
            return false;
    }
    public ArrayList<Cocktail> getAllData0(){
        ArrayList<Cocktail> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+com.example.asd.DataBases.CreateDB._TABLENAME0,null);
        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            int sugar = cursor.getInt(1);
            int alcohol = cursor.getInt(2);
            int body = cursor.getInt(3);
            int unique_ = cursor.getInt(4);
            String base = cursor.getString(5);
            int _id = cursor.getInt(6);
            Cocktail cocktail = new Cocktail(name,sugar,alcohol,body,unique_,base,_id);
            arrayList.add(cocktail);
        }
        return arrayList;
    }
    public ArrayList<Cocktail> getAllData1(){
        ArrayList<Cocktail> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+com.example.asd.DataBases.CreateDB._TABLENAME1,null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            int sugar = cursor.getInt(1);
            int alcohol = cursor.getInt(2);
            int body = cursor.getInt(3);
            int unique_ = cursor.getInt(4);
            String base = cursor.getString(5);
            int _id = cursor.getInt(6);
            Cocktail cocktail = new Cocktail(name,sugar,alcohol,body,unique_,base,_id);

            Log.d("cursor 0: ",name);
            Log.d("cursor 1: ",Integer.toString(sugar));
            Log.d("cursor 2: ",Integer.toString(alcohol));
            Log.d("cursor 3: ",Integer.toString(body));
            Log.d("cursor 4: ",Integer.toString(unique_));
            Log.d("cursor 5: ",base);
            Log.d("cursor 6: ",Integer.toString(_id));

            arrayList.add(cocktail);
        }
        return arrayList;
    }
    public String[] getRequireData(){
        int sugar=0;
        int alcohol=0;
        int body=0;
        int unique_=0;
        int n=0;
        String Name="";
        String Sugar="";
        String Alcohol="";
        String Body="";
        String Unique_="";
        String N="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+com.example.asd.DataBases.CreateDB._TABLENAME1,null);
        while(cursor.moveToNext()){
            Name=Name+cursor.getString(0)+",";
            sugar =sugar+ cursor.getInt(1);
            alcohol =alcohol+ cursor.getInt(2);
            body =body+ cursor.getInt(3);
            unique_ =unique_+ cursor.getInt(4);
            cursor.getInt(6);
            n=n+1;
        }
        Sugar=Integer.toString(sugar);
        Alcohol=Integer.toString(alcohol);
        Body=Integer.toString(body);
        Unique_=Integer.toString(unique_);
        N=Integer.toString(n);
        String[] res={Name,Sugar, Alcohol, Body, Unique_,N};
        return res;
    }
    public String[] getById(int index){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+com.example.asd.DataBases.CreateDB._TABLENAME0 +" WHERE _ID = "+index,null);
        String name="";
        String base="";
        String Sugar="";
        String Alcohol="";
        String Body="";
        String Unique_="";
        String _Id="";
        if(cursor.moveToFirst()){
            name = cursor.getString(0);
            int sugar = cursor.getInt(1);
            int alcohol = cursor.getInt(2);
            int body = cursor.getInt(3);
            int unique_ = cursor.getInt(4);
            base = cursor.getString(5);
            int _id = cursor.getInt(6);
            Log.d("cursor 0: ",name);
            Log.d("cursor 1: ",Integer.toString(sugar));
            Log.d("cursor 2: ",Integer.toString(alcohol));
            Log.d("cursor 3: ",Integer.toString(body));
            Log.d("cursor 4: ",Integer.toString(unique_));
            Log.d("cursor 5: ",base);
            Log.d("cursor 6: ",Integer.toString(_id));
            Sugar=Integer.toString(sugar);
            Alcohol=Integer.toString(alcohol);
            Body=Integer.toString(body);
            Unique_=Integer.toString(unique_);
            _Id=Integer.toString(_id);
        }
        String[] a={name, Sugar, Alcohol, Body, Unique_, base, _Id};
        cursor.close();
        return a;

    }
}
