package com.codeguru.blitz.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import com.codeguru.blitz.R;
import com.codeguru.blitz.model.Customer;

import java.util.List;

import fr.arnaudguyon.smartfontslib.FontManager;

/**
 * Created by tatendakabike on 1/2/17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Customer> customerList;

    Typeface typeface;


    public CustomerAdapter(Context context, List<Customer> customerList){
        this.customerList = customerList;
        layoutInflater = LayoutInflater.from(context);

        typeface = FontManager.getInstance().getTypeface(context, R.string.arvin);

    }

    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.customer_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerAdapter.MyViewHolder holder, int position) {

        Customer customer = customerList.get(position);

        holder.tvCustomerName.setText(customer.getCustomerName());
        holder.tvPhoneNumber.setText(customer.getPhoneNumber());
        holder.tvCarModel.setText(customer.getCarModel());
        holder.tvNumberPlate.setText(customer.getNumberPlate());
        holder.tvServiceDate.setText(customer.getServiceDate());
        holder.tvStatus.setText(customer.getStatus());
        holder.tvCost.setText(String.format("$%s", customer.getCost()));

    }

    @Override
    public int getItemCount() {

        return customerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName, tvPhoneNumber, tvCarModel, tvNumberPlate,
        tvServiceDate, tvStatus, tvCost;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvCustomerName = (TextView)itemView.findViewById(R.id.txtCustomerFullname);
            tvPhoneNumber = (TextView)itemView.findViewById(R.id.txtCustomerPhoneNumber);
            tvCarModel = (TextView)itemView.findViewById(R.id.txtCarModel);
            tvNumberPlate = (TextView)itemView.findViewById(R.id.txtNumberPlate);
            tvServiceDate = (TextView)itemView.findViewById(R.id.txtServiceDate);
            tvStatus = (TextView)itemView.findViewById(R.id.txtStatus);
            tvCost = (TextView)itemView.findViewById(R.id.txtAmount);

            if(typeface != null){

                tvCustomerName.setTypeface(typeface);
                tvPhoneNumber.setTypeface(typeface);
                tvCarModel.setTypeface(typeface);
                tvNumberPlate.setTypeface(typeface);
                tvServiceDate.setTypeface(typeface);
                tvStatus.setTypeface(typeface);
                tvCost.setTypeface(typeface);
            }

        }
    }
}
