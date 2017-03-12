package fr.miage.paris10.projetm1.helpu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by david on 09/03/2017.
 */

public class Data extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Nanterre";
    public static final String TABLE_PRO = "Resultat";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_RPO = "CREATE TABLE IF NOT EXISTS "+ TABLE_PRO+ "( ID integer primary key autoincrement, EC TEXT, SEMESTRE TEXT, UFR TEXT, FILLIERE TEXT  )";

    public static final String DELETE_PRO="DROP TABLE IF EXISTS " + TABLE_PRO;

    public Data(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    public void onCreate(SQLiteDatabase db) {
        // Create the table
        db.execSQL(CREATE_RPO);
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop older table if existed
        db.execSQL(DELETE_PRO);
        //Create tables again
        onCreate(db);

    }


    public void insertData(InputStream inputStream) {

        // Open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String tableName = TABLE_PRO;
            String columns = "EC, SEMESTRE,UFR, FILLIERE ";
            String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
            String str2 = ");";
            // Start the transaction.
            db.beginTransaction();

                while (( line = buffer.readLine()) != null) {
                    String[] str = line.split(",");

                    String requette = str1 + "'" +str[0] + "', '" + str[1] + "', '" + str[2]  +  "', '" + str[3] + "'" + str2;
               //     Log.d(Data.class.getSimpleName(), requette);
                    db.execSQL(requette);
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getAllUFR(){

        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();


        try
        {

            String selectQuery = "SELECT UFR FROM "+ TABLE_PRO +" GROUP BY UFR";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)

            {
                while (cursor.moveToNext()) {
                    String ufr= cursor.getString(cursor.getColumnIndex("UFR"));
                       Log.d(Data.class.getSimpleName(), ufr);
                    list.add(ufr);

                }


            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;


    }


    public ArrayList<String> getAllFilliere(String ufr){

        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();


        try
        {

            String selectQuery = "SELECT DISTINCT FILLIERE FROM "+ TABLE_PRO +" WHERE UFR = '"+ufr +"'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String filliere= cursor.getString(cursor.getColumnIndex("FILLIERE"));
                    list.add(filliere);

                }


            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;


    }

    public ArrayList<String> getEc(String filliere, String level){

        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        int semestre = 9;
        if ("L1".equals(level)){
            semestre = 2;
        }
        else if ("L2".equals(level)){
            semestre = 4;
        }
        else if ("L3".equals(level)){
            semestre = 6;
        }
        else if ("M1".equals(level)){
            semestre = 8;
        }
        else if ("M2".equals(level)){
            semestre = 0;
        }



        try
        {

            String selectQuery = "SELECT EC FROM "+ TABLE_PRO +" WHERE FILLIERE = '" + filliere +"' AND  SEMESTRE = '" + semestre + "'" ;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String ec= cursor.getString(cursor.getColumnIndex("EC"));
                    list.add(ec);

                }


            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;


    }


}

