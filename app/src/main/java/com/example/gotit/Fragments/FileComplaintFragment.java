package com.example.gotit.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gotit.Activities.UserRegistrationActivity;
import com.example.gotit.ParseClasses.Cart;
import com.example.gotit.ParseClasses.Order;
import com.example.gotit.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.File;

public class FileComplaintFragment extends Fragment {

    private TextView title;
    private TextView desc;
    private TextView userReg_fname;
    private TextView userReg_lname;
    private Button cancel;
    private Button submit;
    private Order order;

    private Cart customerCart;


    //----------------------------------------------------------------------------------
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML menu_top_nav inflation.
    //----------------------------------------------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_complaint, container, false);

        title = view.findViewById(R.id.title);
        title.setText("File a Complaint");

        desc = view.findViewById(R.id.desc);
        userReg_fname = view.findViewById(R.id.userReg_fname);
        userReg_lname = view.findViewById(R.id.userReg_lname);
        submit = view.findViewById(R.id.btn_Submit);
        cancel = view.findViewById(R.id.btn_Cancel);

        //----------------------------------------------------------------------------------
        // Listen for click on the Cancel Button
        //----------------------------------------------------------------------------------
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

            }
        });


        return view;
    }

    //----------------------------------------------------------------------------------
    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    //----------------------------------------------------------------------------------
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //----------------------------------------------------------------------------------
        // Listen for click on the Submit Button
        //----------------------------------------------------------------------------------
        Log.d("desc", "-" + desc.getText().toString());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseObject supportTicket = ParseObject.create("SupportTicket");
                ParseObject ordObj = ParseObject.createWithoutData("Order", order.getObjectId());
                supportTicket.put("ord_id", ordObj);
                supportTicket.put("sup_notes", desc.getText().toString());
                supportTicket.put("sup_status", "Open");
                ParseObject cusObj = ParseObject.createWithoutData("Customer",customerCart.getCustomerId());
                supportTicket.put("sup_user_id", cusObj);


                supportTicket.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e!=null)
                            e.printStackTrace();
                    }
                });

                Toast.makeText(v.getContext(), "Complaint was submitted", Toast.LENGTH_SHORT).show();

                getFragmentManager().popBackStack();
            }
        });



    }

    FileComplaintFragment(Order order, Cart cart){
        this.order = order;
        this.customerCart = cart;
    }
}
