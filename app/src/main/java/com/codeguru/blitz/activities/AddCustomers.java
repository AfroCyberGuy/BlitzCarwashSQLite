package com.codeguru.blitz.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codeguru.blitz.R;
import com.codeguru.blitz.db.DbController;
import com.codeguru.blitz.utils.Constants;

import java.util.Calendar;
import java.util.Date;

import fr.arnaudguyon.smartfontslib.FontManager;

public class AddCustomers extends AppCompatActivity {


    private EditText etCustomerName;
    private EditText etPhonenumber;
    private EditText etCarModel;
    private EditText etNumberPlate;
    private EditText etServiceFee;

    Typeface typeface;


    private Button btnSave;
    private DbController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        typeface = FontManager.getInstance().getTypeface(this, R.string.arvin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DbController(this);

        etCustomerName = (EditText)findViewById(R.id.txtCustomerName);
        etPhonenumber = (EditText)findViewById(R.id.txtPhoneNumber);
        etCarModel = (EditText)findViewById(R.id.txtCarModel);
        etNumberPlate = (EditText)findViewById(R.id.txtNumberPlate);
        etServiceFee = (EditText)findViewById(R.id.txtServiceFee);

        etCustomerName.setTypeface(typeface);
        etPhonenumber.setTypeface(typeface);
        etCarModel.setTypeface(typeface);
        etServiceFee.setTypeface(typeface);


        btnSave = (Button)findViewById(R.id.btnSave);

        btnSave.setTypeface(typeface);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String customerName = etCustomerName.getText().toString().trim();
                String phonenumber = etPhonenumber.getText().toString().trim();
                String carModel = etCarModel.getText().toString().trim();
                String numberPlate = etNumberPlate.getText().toString().trim();
                String serviceFee = etServiceFee.getText().toString().trim();
//String customersName, String phonenumber, String carModel, String numberPlates,
               // String serviceDate, String checkIn, String cost

                if(TextUtils.isEmpty(customerName)){

                    Toast.makeText(getApplicationContext(), "Please fill in customer name", Toast.LENGTH_LONG).show();
                    etCustomerName.setError("This field cannot be empty");
                } else if (TextUtils.isEmpty(phonenumber)) {

                    Toast.makeText(getApplicationContext(), "Please fill in customer phone number", Toast.LENGTH_LONG).show();
                    etPhonenumber.setError("This field cannot be empty");
                }else if (TextUtils.isEmpty(carModel)) {

                    Toast.makeText(getApplicationContext(), "Please fill in customer's car model", Toast.LENGTH_LONG).show();
                    etCarModel.setError("This field cannot be empty");
                }else if (TextUtils.isEmpty(numberPlate)) {

                    Toast.makeText(getApplicationContext(), "Please fill in number plate", Toast.LENGTH_LONG).show();
                    etNumberPlate.setError("This field cannot be empty");
                }else if (TextUtils.isEmpty(serviceFee)) {

                    Toast.makeText(getApplicationContext(), "Please enter amount", Toast.LENGTH_LONG).show();
                    etServiceFee.setError("This field cannot be empty");
                }else {


                    db.insert(customerName, phonenumber, carModel, numberPlate, getServiceDate(), Constants.CHECK_IN,
                            serviceFee);

                    Toast.makeText(getApplicationContext(), "Data added successfully", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), BlitzCarWash.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }


    private String getServiceDate(){

        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy/MMM/dd");
        return simpleDateFormat.format(currentDate);
    }

}
