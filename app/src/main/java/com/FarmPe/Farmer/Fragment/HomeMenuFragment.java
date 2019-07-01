
package com.FarmPe.Farmer.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeMenuFragment extends Fragment implements  View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
Fragment selectedFragment;
    public static DrawerLayout drawer;
    ImageView plus_sign_add;
    RelativeLayout menu,prof_tab;
    LinearLayout update_acc_layout,near_by,linear_connection;
    SessionManager sessionManager;
      CircleImageView prod_img,prod_img1;
    String userid;
    TextView home,settings,nw_request,nearby,connections,connection_nw,your_requests,list_farm;
    public static TextView cart_count_text,user_name_menu,phone_no;
    View looking_view,farms_view,farmer_view;
    RelativeLayout notification_bell;
    JSONObject lngObject;
     static boolean fragloaded;
   public static SearchView searchView;

   static Fragment myloadingfragment;
    public static NestedScrollView scrollView;
    boolean doubleBackToExitPressedOnce = false;


    public static HomeMenuFragment newInstance() {
        fragloaded =true;
        HomeMenuFragment fragment = new HomeMenuFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_navigation_menu_home, container, false);

        menu=view.findViewById(R.id.menu);
        searchView=view.findViewById(R.id.search1);
       //scrollView=view.findViewById(R.id.scroll);
        home = view.findViewById(R.id.home);
        phone_no = view.findViewById(R.id.phone_no);
        linear_connection = view.findViewById(R.id.linear_connection);
        //map=view.findViewById(R.id.map);
        update_acc_layout=view.findViewById(R.id.update_acc_layout);
        notification_bell=view.findViewById(R.id.notification_bell);
        settings=view.findViewById(R.id.settings);
        prod_img=view.findViewById(R.id.prod_img);
        prod_img1=view.findViewById(R.id.prod_img1);
       /* looking_for=view.findViewById(R.id.looking_for);
        farms=view.findViewById(R.id.farms);phone_no
        farmer=view.findViewById(R.id.farmer);*/
        looking_view=view.findViewById(R.id.looking_view);
        farms_view=view.findViewById(R.id.farms_view);
        farmer_view=view.findViewById(R.id.farmer_view);

        connections=view.findViewById(R.id.connections);
        connection_nw=view.findViewById(R.id.connection_nw);
        your_requests=view.findViewById(R.id.your_requests);
        list_farm=view.findViewById(R.id.list_farm);

        nw_request=view.findViewById(R.id.nw_request);
        nearby=view.findViewById(R.id.nearby);

        plus_sign_add=view.findViewById(R.id.plus_sign_add);
        user_name_menu=view.findViewById(R.id.user_name_menu);
        near_by=view.findViewById(R.id.near_by);
        sessionManager = new SessionManager(getActivity());
        userid=sessionManager.getRegId("userId");

       // searchView.setBackgroundColor(Color.parseColor("#1ba261"));


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // back_feed.setVisibility(View.GONE);
               // title.setVisibility(View.GONE);
                searchView.setMaxWidth(Integer.MAX_VALUE);
                searchView.setBackgroundColor(Color.WHITE);
            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
               // back_feed.setVisibility(View.VISIBLE);
                //title.setVisibility(View.VISIBLE);
                searchView.setBackgroundColor(Color.parseColor("#000000"));
                return false;
            }
        });

       user_name_menu.setText(sessionManager.getRegId("name"));
      phone_no.setText(sessionManager.getRegId("phone"));

        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        

        System.out.println("lajfdhsjkd");




        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));


            connections.setText(lngObject.getString("Connections"));
            nw_request.setText(lngObject.getString("NewRequest"));
            nearby.setText(lngObject.getString("Nearby"));
            home.setText(lngObject.getString("Message"));
            settings.setText(lngObject.getString("Settings"));

            connection_nw.setText(lngObject.getString("Connections"));
            list_farm.setText(lngObject.getString("ListyourFarm"));
            your_requests.setText(lngObject.getString("YourRequests"));




        } catch (JSONException e) {
            e.printStackTrace();
        }


        // scrollView.requestFocus(View.FOCUS_UP);

        NavigationView navigationView = (NavigationView)view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        System.out.println("hhhrtryur");


        selectedFragment = DashboardFragment.newInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.first_full_frame, selectedFragment);
        transaction.commit();

        plus_sign_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = AddFirstLookingFor.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = ComingSoonFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });

        your_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = NotificationFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });

        list_farm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = ComingSoonFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });

        nw_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = AddFirstLookingFor.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("looking");
                transaction.commit();
                drawer.closeDrawers();

//                selectedFragment = ComingSoonFragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, selectedFragment);
//                transaction.addToBackStack("home");
//                transaction.commit();

            }
        });


        near_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = ComingSoonFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });


        linear_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = ComingSoonFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });

        connection_nw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = ComingSoonFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

            }
        });

        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = NotificationList.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();
//
//  Bundle bundle = new Bundle();
//                bundle.putString("navigation_from", "home");
//                selectedFragment = NotificationFragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, selectedFragment);
//                selectedFragment.setArguments(bundle);
//                transaction.addToBackStack("home");
//                transaction.commit();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                selectedFragment = DashboardFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.first_full_frame, selectedFragment);
                transaction.addToBackStack("home_menu");
                transaction.commit();
                drawer.closeDrawers();


            }
        });




        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = SettingFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();
                drawer.closeDrawers();

            }
        });

        near_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ComingSoonFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();
                drawer.closeDrawers();


            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);



            }
        });




        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_object = new JSONObject();

            jsonObject.put("Id",sessionManager.getRegId("userId"));
            post_object.put("objUser",jsonObject);


            Crop_Post.crop_posting(getActivity(), Urls.Get_Profile_Details, post_object, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("ggpgpgpg" + result);

                    try{

                        JSONObject jsonObject1 = result.getJSONObject("user");
                        String ProfileName = jsonObject1.getString("FullName");
                        String ProfilePhone = jsonObject1.getString("PhoneNo");
                        String ProfileEmail = jsonObject1.getString("EmailId");
                        String ProfileImage = jsonObject1.getString("ProfilePic");


                        user_name_menu.setText(ProfileName);
                        phone_no.setText(ProfilePhone);
                       // profile_mail.setText(ProfileEmail);

                        Glide.with(getActivity()).load(ProfileImage)

                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.avatarmale)
                                .into(prod_img);



                        Glide.with(getActivity()).load(ProfileImage)

                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.avatarmale)
                                .into(prod_img1);




                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }






        return view;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }





}
