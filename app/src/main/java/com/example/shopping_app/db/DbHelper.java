package com.example.shopping_app.db;

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

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "userdb";
    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private static String DB_PATH = "";
    File dbFile;
    private static final String TAG = "DataBaseHelper";

    public DbHelper(Context context){
        super(context,DB_NAME,null,1);
        this.mContext=context;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
    }

    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }
    public void createDataBase() throws IOException
    {
        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            mDataBase =this.getReadableDatabase();
            mDataBase.close();
            try
            {
                copyDataBase();
                Log.e(TAG, " Database Created");
            }
            catch (IOException mIOException)
            {
                Log.e(TAG,""+mIOException);
                throw new Error("ErrorCopyingDataBase");
            }
            finally {
                mDataBase.close();
            }
        }

    }

    private boolean checkDataBase()
    {
        dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[3072];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer,0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {   }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {   }


    public boolean insertData(String username,String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password",password);
        long result = sqLiteDatabase.insert("users",null,contentValues);
        if (result==-1)return false;
        else return true;
    }

    public boolean checkExistUser(String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from users where username = '"+ username+"'",null);
        if (cursor.getCount()>0)return true;
        else return false;
    }

    public boolean checkUserDetails(String username, String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from users where username = '"+ username+"' and password = '"+ password+"' ",null);
        if (cursor.getCount()>0)return true;
        else return false;
    }

}
