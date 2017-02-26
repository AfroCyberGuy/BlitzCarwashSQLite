package com.codeguru.blitz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tatendakabike on 11/26/16.
 */

public class DbController extends SQLiteOpenHelper{

    //Loging string
    private static final String TAG = DbController.class.getSimpleName();

    //Database name
    private static final String DATABASE_NAME = "my_carwash";

    //Database versioning
    private static final int DATABASE_VERSION = 4;

    //Table name
    private static final String CAR_WASH_TABLE = "carwash";


    //Table fields
    private static final String KEY_ID = "id";
    private static final String KEY_CUSTOMER_NAME = "customer_name";
    private static final String KEY_PHONENEMBER = "phonenumber";
    private static final String KEY_CAR_MODEL = "car_model";
    private static final String KEY_NUMBER_PLATE = "number_plates";
    private static final String KEY_SERVICE_DATE = "service_date";
    private static final String KEY_CHECK_IN = "check_in";
    private static final String KEY_COST = "cost";





    public DbController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_MY_TABLE = "CREATE TABLE " + CAR_WASH_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CUSTOMER_NAME + " TEXT,"
                + KEY_PHONENEMBER + " TEXT," + KEY_CAR_MODEL + " TEXT,"
                + KEY_NUMBER_PLATE + " TEXT," + KEY_SERVICE_DATE + " TEXT,"
                + KEY_CHECK_IN + " TEXT," + KEY_COST + " TEXT" + ")";


        database.execSQL(CREATE_MY_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {

        String query;

        query = "DROP TABLE IF EXISTS " + CAR_WASH_TABLE;

        Log.d(TAG, query);

        database.execSQL(query);
        onCreate(database);

    }


    public void insert(String customersName, String phonenumber, String carModel, String numberPlates,
                       String serviceDate, String checkIn, String cost){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values =  new ContentValues();
        values.put(KEY_CUSTOMER_NAME, customersName);
        values.put(KEY_PHONENEMBER, phonenumber);
        values.put(KEY_CAR_MODEL, carModel);
        values.put(KEY_NUMBER_PLATE, numberPlates);
        values.put(KEY_SERVICE_DATE, serviceDate);
        values.put(KEY_CHECK_IN, checkIn);
        values.put(KEY_COST, cost);

        //Insert row
        long id = db.insert(CAR_WASH_TABLE, null, values);

        Log.d(TAG, String.format("Car recorded successfully   %d", id));
    }


    public int update(long _id, String checkIn){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values =  new ContentValues();
        values.put(KEY_CHECK_IN, checkIn);

        //Insert row
        int id = db.update(CAR_WASH_TABLE, values, KEY_ID + " = " + _id, null);

        Log.d(TAG, "Car recorded successfully" + id);

        return id;

    }


    public ArrayList<HashMap<String, String>>  getCustomers(){

        ArrayList<HashMap<String, String>> customerList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT * FROM " + CAR_WASH_TABLE;

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{
                HashMap<String, String> map = new HashMap<>();

                map.put(KEY_ID, cursor.getString(0));
                map.put(KEY_CUSTOMER_NAME, cursor.getString(1));
                map.put(KEY_PHONENEMBER, cursor.getString(2));
                map.put(KEY_CAR_MODEL, cursor.getString(3));
                map.put(KEY_NUMBER_PLATE, cursor.getString(4));
                map.put(KEY_SERVICE_DATE, cursor.getString(5));
                map.put(KEY_CHECK_IN, cursor.getString(6));
                map.put(KEY_COST, cursor.getString(7));

                customerList.add(map);


            }while (cursor.moveToNext());
        }

        database.close();

        return customerList;

    }

    public void removeCustomer(long _id){

        SQLiteDatabase database = getWritableDatabase();

        database.delete(CAR_WASH_TABLE, KEY_ID + " = " + _id, null);

        database.close();

    }



    public ArrayList<HashMap<String, String>>  searchCustomers(String searchParam){

        ArrayList<HashMap<String, String>> customerList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT * FROM " + CAR_WASH_TABLE + " WHERE " + KEY_CUSTOMER_NAME
                + " LIKE '" + searchParam + "%'";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){

            do{
                HashMap<String, String> map = new HashMap<>();

                map.put(KEY_ID, cursor.getString(0));
                map.put(KEY_CUSTOMER_NAME, cursor.getString(1));
                map.put(KEY_PHONENEMBER, cursor.getString(2));
                map.put(KEY_CAR_MODEL, cursor.getString(3));
                map.put(KEY_NUMBER_PLATE, cursor.getString(4));
                map.put(KEY_SERVICE_DATE, cursor.getString(5));
                map.put(KEY_CHECK_IN, cursor.getString(6));
                map.put(KEY_COST, cursor.getString(7));


                customerList.add(map);


            }while (cursor.moveToNext());
        }

        database.close();

        return customerList;

    }



}
