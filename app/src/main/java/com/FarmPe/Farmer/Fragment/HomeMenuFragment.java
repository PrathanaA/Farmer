package com.FarmPe.Farmer.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Activity.LandingPageActivity;
import com.FarmPe.Farmer.Activity.LoginActivity;
import com.FarmPe.Farmer.Adapter.AddPhotoAdapter;
import com.FarmPe.Farmer.Adapter.List_Farm_Adapter2;
import com.FarmPe.Farmer.Bean.AddPhotoBean;
import com.FarmPe.Farmer.volleypost.VolleyMultipartRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.FarmPe.Farmer.Activity.LandingPageActivity.toast_click_back;
import static com.android.volley.VolleyLog.TAG;


public class HomeMenuFragment extends Fragment implements  View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    Fragment selectedFragment;
    public static DrawerLayout drawer;
    ImageView plus_sign_add;
    RelativeLayout menu,prof_tab;
    LinearLayout update_acc_layout,near_by,linear_connection;
    SessionManager sessionManager;
    public static CircleImageView prod_img,prod_img1;
    public static boolean isEng = false;
    String mob_no;
    String userid;
    private static int RESULT_LOAD_IMG = 1;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    TextView home,settings,nw_request,nearby,connections,connection_nw,your_requests,list_farm,invitation;
    public static TextView your_farms,cart_count_text,user_name_menu,phone_no;
    View looking_view,farms_view,farmer_view;
    RelativeLayout notification_bell;
    JSONObject lngObject;
    static boolean fragloaded;
    //  public static SearchView searchView;
    LinearLayout linearLayout;
    public static Bitmap selectedImage;

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

        home = view.findViewById(R.id.home);
        phone_no = view.findViewById(R.id.phone_no);
        invitation = view.findViewById(R.id.invitation);
        connections = view.findViewById(R.id.connections);
        //linear_connection = view.findViewById(R.id.linear_connection);
        update_acc_layout=view.findViewById(R.id.update_acc_layout);
        notification_bell=view.findViewById(R.id.notification_bell);
        settings=view.findViewById(R.id.settings);
        prod_img=view.findViewById(R.id.prod_img);
        prod_img1=view.findViewById(R.id.prod_img1);

        looking_view=view.findViewById(R.id.looking_view);
        farms_view=view.findViewById(R.id.farms_view);
        farmer_view=view.findViewById(R.id.farmer_view);


        your_farms=view.findViewById(R.id.your_farms);
        your_requests=view.findViewById(R.id.your_requests);
        list_farm=view.findViewById(R.id.list_farm);



        plus_sign_add=view.findViewById(R.id.plus_sign_add);
        user_name_menu=view.findViewById(R.id.user_name_menu);
        sessionManager = new SessionManager(getActivity());
        userid=sessionManager.getRegId("userId");






        prod_img1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // to go to gallery
                startActivityForResult(i, 100); // on activity method will execute
            }
        });





        //      ComingSoonFragment.backfeed.setVisibility(View.GONE);

        // searchView.setBackgroundColor(Color.parseColor("#1ba261"));




//                searchView.setOnSearchClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // back_feed.setVisibility(View.GONE);
//                        // title.setVisibility(View.GONE);
//                        searchView.setMaxWidth(Integer.MAX_VALUE);
//                        searchView.setBackgroundColor(Color.WHITE);
//                    }
//                });

//
//                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//                    @Override
//                    public boolean onClose() {
//                        // back_feed.setVisibility(View.VISIBLE);
//                        //title.setVisibility(View.VISIBLE);
//                        searchView.setBackgroundColor(Color.parseColor("#000000"));
//                        return false;
//                    }
//                });



        user_name_menu.setText(sessionManager.getRegId("name"));

        phone_no.setText(sessionManager.getRegId("phone").substring(3));



      /*  Glide.with(getActivity()).load(sessionManager.getRegId("image"))

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.avatarmale)
                .into(prod_img);



        Glide.with(getActivity()).load(sessionManager.getRegId("image"))

                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.avatarmale)
                .into(prod_img1);
*/

        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);


        System.out.println("lajfdhsjkd");



        if (LoginActivity.change_lang.getText().toString().equals("English")){
            isEng = true;
            Log.d("GGGGGGGG", "Here: "+LoginActivity.isEng);
        }
        else{
            isEng = false;
            Log.d("GGGGGGGG", "Here: "+LoginActivity.isEng);
        }




        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));


//            connections.setText(lngObject.getString("Connections"));
//            nw_request.setText(lngObject.getString("NewRequest"));
//            nearby.setText(lngObject.getString("Nearby"));
//            home.setText(lngObject.getString("Message"));
//            settings.setText(lngObject.getString("Settings"));
//
//            connection_nw.setText(lngObject.getString("Connections"));
//            list_farm.setText(lngObject.getString("ListyourFarm"));
//            your_requests.setText(lngObject.getString("YourRequests"));




        } catch (JSONException e) {
            e.printStackTrace();
        }

        // scrollView.requestFocus(View.FOCUS_UP);

        NavigationView navigationView = (NavigationView)view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        System.out.println("hhhrtryur");

        if(Edit_Looking_For_Fragment.back==null) {


            selectedFragment = FarmPe_Logo_Fragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.first_full_frame, selectedFragment);
            transaction.commit();

        }else {

            selectedFragment = LookingForFragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.first_full_frame, selectedFragment);
            transaction.commit();
        }



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

                selectedFragment = AddFirstLookingFor.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();
                drawer.closeDrawers();

            }
        });


        if(Comming_soon_looking.coming_back=="look_back"){
            selectedFragment = LookingForFragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.first_full_frame, selectedFragment);
            transaction.commit();

        }else{

        }
        your_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    selectedFragment = LookingForFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.first_full_frame, selectedFragment);
                    transaction.addToBackStack("home");
                    transaction.commit();
                    drawer.closeDrawers();


            }
        });


        connections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ConnectionsFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();
            }
        });




        invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedFragment = InvitationsLeadsFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();
                drawer.closeDrawers();

            }
        });

        list_farm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("RB_S", 0);
                selectedFragment = ListYourFarms.newInstance();
                selectedFragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("list_farm");
                transaction.commit();
                drawer.closeDrawers();

            }
        });


        if(Comming_soon_farms.back_farm=="look_farm"){
            selectedFragment = FarmsHomePageFragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.first_full_frame, selectedFragment);
            transaction.commit();
        }else {

        }

        your_farms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    selectedFragment = FarmsHomePageFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.first_full_frame, selectedFragment);
                    transaction.addToBackStack("home");
                    transaction.commit();
                    drawer.closeDrawers();


            }
        });


        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = NotificationFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("home");
                transaction.commit();

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
                        phone_no.setText(ProfilePhone.substring(3));

                        // phone_no.setText(ProfilePhone.substring(0,13-6) + ""); // masking + deleting last line
                        // profile_mail.setText(ProfileEmail);


                        Glide.with(getActivity()).load(ProfileImage)

                                .thumbnail(0.5f)
                                .crossFade()
                                .error(R.drawable.avatarmale)
                                .into(prod_img);



                        Glide.with(getActivity()).load(ProfileImage)

                                .thumbnail(0.5f)
                                .crossFade()
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  super.onActivityResult(requestCode, resultCode, data);
       // Toast.makeText(getActivity(),"Your Changed Your Profile Photo", Toast.LENGTH_SHORT).show();

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
          //  Toast.makeText(getActivity(),"Your Changed Your Profile Photo", Toast.LENGTH_SHORT).show();
            Uri imageUri = data.getData();
          //  bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

               prod_img1.setImageBitmap(bitmap);
                prod_img.setImageBitmap(bitmap);
                uploadImage(getResizedBitmap(bitmap,100,100));
                Toast.makeText(getActivity(), "Your Changed Your Profile Photo", Toast.LENGTH_SHORT).show();


            }   catch (Exception e) {
                e.printStackTrace();
            }
        }

      /*  if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                prod_img1.setImageBitmap(bitmap);
                prod_img.setImageBitmap(bitmap);
                uploadImage(getResizedBitmap(bitmap,100,100));
                Toast.makeText(getActivity(),"Your Changed Your Profile Photo", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadImage(final Bitmap bitmap2){
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
                "Loading....Please wait.");

        progressDialog.show();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.Update_Profile_Details,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e(TAG,"afaeftagsbillvalue"+response.data);
                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        progressDialog.dismiss();

//                        Toast.makeText(getActivity(),"Profile Details Updated Successfully", Toast.LENGTH_SHORT).show();
//                        selectedFragment = SettingFragment.newInstance();
//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.frame_layout,selectedFragment);
//                        ft.commit();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();



                Log.e(TAG,"Im here " + params);
                if (bitmap2!=null) {
                    params.put("File", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap2)));

                }

                Log.e(TAG,"Im here " + params);
                return params;
            }



            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId",sessionManager.getRegId("userId"));
                params.put("FullName",sessionManager.getRegId("name"));
                params.put("PhoneNo",sessionManager.getRegId("phone"));
                params.put("Password",sessionManager.getRegId("pass"));
                Log.e(TAG,"afaeftagsparams"+params);
                return params;
            }

        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }



    public Bitmap getResizedBitmap(Bitmap bm1, int newWidth, int newHeight) {
        if (bm1==null){

            return null;
        }else {
            System.out.println("llllllllllllllllllllllllllllll"+newHeight);
            int width = bm1.getWidth();
            int height = bm1.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm1, 0, 0, width, height, matrix, false);
          //  bm1.recycle();
            return resizedBitmap;
        }

    }



}

