package com.codeguru.blitz.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.codeguru.blitz.R;
import com.codeguru.blitz.adapters.CustomerAdapter;
import com.codeguru.blitz.db.DbController;
import com.codeguru.blitz.dialogs.ConfirmDialog;
import com.codeguru.blitz.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.arnaudguyon.smartfontslib.FontManager;

public class BlitzCarWash extends AppCompatActivity implements ConfirmDialog.Communicator, SearchView.OnQueryTextListener {

    //Database column fields
    private static final String KEY_ID = "id";
    private static final String KEY_CUSTOMER_NAME = "customer_name";
    private static final String KEY_PHONENEMBER = "phonenumber";
    private static final String KEY_CAR_MODEL = "car_model";
    private static final String KEY_NUMBER_PLATE = "number_plates";
    private static final String KEY_SERVICE_DATE = "service_date";
    private static final String KEY_CHECK_IN = "check_in";
    private static final String KEY_COST = "cost";

    public static final String BLITZ_EXTRA = "blitz_extra";

    int customerId;

    int trackAdapterItemPosition;
    int arrayListItemPosition;

    SearchView mSerchView;


    private static final String TAG = BlitzCarWash.class.getSimpleName();
    private RecyclerView customerRecyclerView;
    private List<Customer> customerList = new ArrayList<>();
    private CustomerAdapter customerAdapter;
    DbController db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blitz_car_wash);

        customerRecyclerView = (RecyclerView)findViewById(R.id.carRecyclerView);

        customerRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                customerRecyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

               Intent i = new Intent(getApplicationContext(), EditCustomer.class);

                Customer customer = customerList.get(position);
                i.putExtra(BLITZ_EXTRA, customer);
                startActivity(i);
                finish();

            }

            @Override
            public void onLongClick(View view, int position) {

                Customer customer = customerList.get(position);

                trackAdapterItemPosition = position;
                arrayListItemPosition = position;

                customerId = Integer.parseInt(customer.getId());

                ConfirmDialog confirmDialog = new ConfirmDialog();
                confirmDialog.show(getSupportFragmentManager(), "CONFIRM");
            }
        }));


        loadCustomerData();

    }

    private void loadCustomerData() {

        db = new DbController(getApplicationContext());

        ArrayList<HashMap<String,String>> customersFound = db.getCustomers();

        Log.d(TAG, customersFound.toString());

        //Check if there is any data found

        if (customersFound.size() == 0) {

            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
        }

        if(customersFound.size() != 0){

            for(Map<String, String> customers : customersFound){

                Customer customerObject =  new Customer();
                customerObject.setId(customers.get(KEY_ID));
                customerObject.setCustomerName(customers.get(KEY_CUSTOMER_NAME));
                customerObject.setPhoneNumber(customers.get(KEY_PHONENEMBER));
                customerObject.setCarModel(customers.get(KEY_CAR_MODEL));
                customerObject.setNumberPlate(customers.get(KEY_NUMBER_PLATE));
                customerObject.setServiceDate(customers.get(KEY_SERVICE_DATE));
                customerObject.setStatus(customers.get(KEY_CHECK_IN));
                customerObject.setCost(customers.get(KEY_COST));


                customerList.add(customerObject);

                customerAdapter =  new CustomerAdapter(getApplicationContext(), customerList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                customerRecyclerView.setLayoutManager(layoutManager);
                customerRecyclerView.setAdapter(customerAdapter);
                customerRecyclerView.setHasFixedSize(true);

                customerAdapter.notifyDataSetChanged();

            }

        }
    }


    private void search(String searchParam) {

        ArrayList<HashMap<String,String>> customersFound = null;

        if (customerAdapter != null) {
            customerAdapter = null;
        }
        if (customerList.size() != 0) {

            customerList.clear();
        }



        db = new DbController(getApplicationContext());

        customersFound = db.searchCustomers(searchParam);

        Log.d(TAG, customersFound.toString());

        //Check if there is any data found

        if (customersFound.size() == 0) {

            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
        }

        if(customersFound.size() != 0){

            for(Map<String, String> customers : customersFound){

                Customer customerObject =  new Customer();
                customerObject.setId(customers.get(KEY_ID));
                customerObject.setCustomerName(customers.get(KEY_CUSTOMER_NAME));
                customerObject.setPhoneNumber(customers.get(KEY_PHONENEMBER));
                customerObject.setCarModel(customers.get(KEY_CAR_MODEL));
                customerObject.setNumberPlate(customers.get(KEY_NUMBER_PLATE));
                customerObject.setServiceDate(customers.get(KEY_SERVICE_DATE));
                customerObject.setStatus(customers.get(KEY_CHECK_IN));
                customerObject.setCost(customers.get(KEY_COST));


                customerList.add(customerObject);

                customerAdapter =  new CustomerAdapter(getApplicationContext(), customerList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                customerRecyclerView.setLayoutManager(layoutManager);
                customerRecyclerView.setAdapter(customerAdapter);
                customerRecyclerView.setHasFixedSize(true);

                customerAdapter.notifyDataSetChanged();

            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate xml menu layout.
        getMenuInflater().inflate(R.menu.view_cars_menu, menu);

        mSerchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        mSerchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_search:

                mSerchView.setIconified(true);
                mSerchView.setQueryHint("Search customer");

                break;

            case R.id.action_add_car:


                Intent intent = new Intent(getApplicationContext(), AddCustomers.class);
                startActivity(intent);
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void deleteCustomer() {


        db.removeCustomer(customerId);
        customerList.remove(arrayListItemPosition);
        customerAdapter.notifyItemRemoved(trackAdapterItemPosition);
        customerAdapter.notifyItemRangeChanged(trackAdapterItemPosition, customerAdapter.getItemCount());

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

       search(newText);

        return true;
    }


    public interface ClickListener {

        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private BlitzCarWash.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                                     final BlitzCarWash.ClickListener clickListener) {
            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }




}
