package com.FarmPe.Farmer.Fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.Farmer.Adapter.AddFirstAdapter;
import com.FarmPe.Farmer.R;


public class No_Request_Fragment extends Fragment {


    TextView make_request;
    LinearLayout back_feed1;
    Fragment selectedFragment = null;
ImageView b_arrow;

    public static No_Request_Fragment newInstance() {
        No_Request_Fragment fragment = new No_Request_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.no_requst_new, container, false);

        make_request = view.findViewById(R.id.make_requesttttt);
        back_feed1 = view.findViewById(R.id.back_feed1);
        b_arrow=view.findViewById(R.id.b_arrow);


        back_feed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_whitecancel));
                HomeMenuFragment.onBack_status = "no_request";

                selectedFragment = HomeMenuFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    HomeMenuFragment.onBack_status = "no_request";
                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();

                    return true;
                }
                return false;
            }
        });



        make_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddFirstAdapter.looinkgId = null;
                selectedFragment = AddFirstFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });

        return view;
    }

}
