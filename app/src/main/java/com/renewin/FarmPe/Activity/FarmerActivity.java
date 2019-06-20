package com.renewin.FarmPe.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renewin.FarmPe.Adapter.AddHpAdapter;
import com.renewin.FarmPe.Bean.FarmsImageBean;
import com.renewin.FarmPe.Fragment.Add_New_Address_Fragment;
import com.renewin.FarmPe.R;
import com.renewin.FarmPe.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class FarmerActivity extends AppCompatActivity {

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddHpAdapter farmadapter;
    TextView toolbar_title;
    Fragment selectedFragment;
    LinearLayout back_feed,next_layout;
    CheckBox check_box;
    @Override
    public void onResume() {
       // LocalBroadcastManager.getInstance(FarmerUser.this).registerReceiver(receiver, new IntentFilter("otp_forgot"));
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_request);
        back_feed=findViewById(R.id.back_feed);
        toolbar_title=findViewById(R.id.toolbar_title);
      //  check_box=findViewById(R.id.check_box);
        next_layout=findViewById(R.id.next_layout);
        toolbar_title.setText("Request for Quotation");

       // check_box.setText("I agree that by clicking 'Request for Tractor Price' button, I am explicitly soliciting a call from Xohri App users on my 'Mobile' in order to assist me with my tractor purchase.");

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FarmerActivity.this,FarmerUser.class);
                startActivity(intent);
            }
        });


        next_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FarmerActivity.this,FarmerActivityNext.class);
                startActivity(intent);
                /*selectedFragment = Add_New_Address_Fragment.newInstance();
                FragmentTransaction transaction = FarmerActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("request");
                transaction.commit();*/
            }
        });

}}