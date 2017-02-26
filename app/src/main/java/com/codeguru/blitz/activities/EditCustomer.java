package com.codeguru.blitz.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codeguru.blitz.R;
import com.codeguru.blitz.db.DbController;
import com.codeguru.blitz.model.Customer;

import fr.arnaudguyon.smartfontslib.FontManager;

public class EditCustomer extends AppCompatActivity {

    Customer customer;

    private TextView tvCustomername;
    private TextView tvPhonenumber;
    private TextView tvCarModel;
    private TextView tvNumberPlate;
    private TextView tvServiceFee;

    private Button btnSave;


    Typeface typeface;

    private Spinner spinStatus;

    DbController db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        typeface = FontManager.getInstance().getTypeface(this, R.string.arvin);

        customer = (Customer)getIntent().getSerializableExtra(BlitzCarWash.BLITZ_EXTRA);

        db = new DbController(this);

        tvCustomername = (TextView)findViewById(R.id.tvCustomerName);
        tvPhonenumber = (TextView)findViewById(R.id.tvPhoneNumber);
        tvCarModel = (TextView)findViewById(R.id.tvCarModel);
        tvNumberPlate = (TextView)findViewById(R.id.tvNumberPlate);
        tvServiceFee = (TextView)findViewById(R.id.tvServiceFee);

        tvCustomername.setTypeface(typeface);
        tvPhonenumber.setTypeface(typeface);
        tvCarModel.setTypeface(typeface);
        tvNumberPlate.setTypeface(typeface);
        tvServiceFee.setTypeface(typeface);

        btnSave = (Button)findViewById(R.id.btnSave);

        btnSave.setTypeface(typeface);

        spinStatus = (Spinner)findViewById(R.id.spinner);

        tvCustomername.setText(customer.getCustomerName());
        tvPhonenumber.setText(customer.getPhoneNumber());
        tvCarModel.setText(customer.getCarModel());
        tvNumberPlate.setText(customer.getNumberPlate());
        tvServiceFee.setText(customer.getCost());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String status = spinStatus.getSelectedItem().toString();

                db.update(Integer.parseInt(customer.getId()), status);

                Toast.makeText(getApplicationContext(), "Changes committed successfully", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), BlitzCarWash.class);
                startActivity(i);

            }
        });

    }

}
