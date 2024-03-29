package com.FarmPe.Farmer.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Activity.LoginActivity;
import com.FarmPe.Farmer.Adapter.AddBrandAdapter;
import com.FarmPe.Farmer.Adapter.AddFirstAdapter;
import com.FarmPe.Farmer.Bean.AddTractorBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Login_post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddFirstFragment extends Fragment {

    public static List<AddTractorBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddFirstAdapter farmadapter;
    LinearLayout back_feed,linearLayout;
    TextView continue_button;
    public static String tracter_title = "";
    Fragment selectedFragment;
ImageView b_arrow;



    public static AddFirstFragment newInstance() {
        AddFirstFragment fragment = new AddFirstFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_first_recy, container, false);
        recyclerView=view.findViewById(R.id.recycler_what_looking);
        continue_button=view.findViewById(R.id.continue_button);
        back_feed=view.findViewById(R.id.back_feed);
        linearLayout=view.findViewById(R.id.linearLayout);
        b_arrow=view.findViewById(R.id.b_arrow);

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeMenuFragment.onBack_status="no_request";
                b_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_whitecancel));
                selectedFragment = HomeMenuFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }
        });


        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkk"+AddFirstAdapter.looinkgId);

                if (AddFirstAdapter.looinkgId==null){

                    int duration = 1000;
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Please choose any option", duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }

                    snackbar.show();


                }else {

                    AddBrandAdapter.brandId = null;
                    selectedFragment = AddBrandFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.frame_layout, selectedFragment);
                    transaction.addToBackStack("first");
                    transaction.commit();
                }

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    HomeMenuFragment.onBack_status="no_request";

                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });



        AddLookigFor();
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    private void AddLookigFor() {

        try {
            newOrderBeansList.clear();

            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("Id", 3);


            JSONObject postjsonObject = new JSONObject();
            postjsonObject.put("LookingForObj", userRequestjsonObject);

            System.out.println("postObj"+postjsonObject.toString());

            Login_post.login_posting(getActivity(), Urls.GetLookingForItems,postjsonObject,new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("cropsresult"+result);
                    JSONArray cropsListArray=null;
                    try {
                        cropsListArray=result.getJSONArray("LookingForDetailsList");
                        System.out.println("e     e e ddd"+cropsListArray.length());
                        for (int i=0;i<cropsListArray.length();i++){
                            JSONObject jsonObject1=cropsListArray.getJSONObject(i);

                            String getPrice=jsonObject1.getString("LookingForDetails");
                            String LookingForDetailsIcon=jsonObject1.getString("LookingForDetailsIcon");

                            // String lookingForId=jsonObject1.getString("LookingForId");
                            String lookingForId=jsonObject1.getString("Id");


                            AddTractorBean crops = new AddTractorBean(LookingForDetailsIcon, getPrice,lookingForId,false);
                            newOrderBeansList.add(crops);

                        }
                        farmadapter=new AddFirstAdapter(getActivity(),newOrderBeansList);
                        recyclerView.setAdapter(farmadapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
