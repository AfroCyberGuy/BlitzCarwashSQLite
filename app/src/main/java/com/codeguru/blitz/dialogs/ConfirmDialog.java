package com.codeguru.blitz.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.codeguru.blitz.R;

/**
 * Created by tatendakabike on 2/5/17.
 */

public class ConfirmDialog extends DialogFragment implements View.OnClickListener {


    private Button btnYes;
    private Button btnNo;

    Communicator communicator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        communicator = (Communicator)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.confirm_dialog, null);

        btnYes = (Button)view.findViewById(R.id.btnYes);
        btnNo = (Button)view.findViewById(R.id.btnNo);

        btnNo.setOnClickListener(this);
        btnYes.setOnClickListener(this);

        getDialog().setTitle("Delete customer?");
        setCancelable(false);


        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnYes) {

            communicator.deleteCustomer();
            dismiss();
            Toast.makeText(getActivity(), "Customer deleted successfully", Toast.LENGTH_LONG).show();

        }

        if (v.getId() == R.id.btnNo) {

            dismiss();

        }

    }


    public interface Communicator{

        void deleteCustomer();
    }
}
