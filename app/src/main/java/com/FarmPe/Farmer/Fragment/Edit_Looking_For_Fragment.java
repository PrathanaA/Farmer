package com.FarmPe.Farmer.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.Farmer.Activity.EnterOTP;
import com.FarmPe.Farmer.Adapter.AddFirstAdapter;
import com.FarmPe.Farmer.Adapter.AddModelAdapter;
import com.FarmPe.Farmer.Adapter.You_Address_Adapter;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.FarmPe.Farmer.Adapter.AddHpAdapter;
import com.FarmPe.Farmer.Adapter.FarmsImageAdapter;
import com.FarmPe.Farmer.Bean.FarmsImageBean;
import com.FarmPe.Farmer.R;
import com.FarmPe.Farmer.SessionManager;
import com.FarmPe.Farmer.Urls;
import com.FarmPe.Farmer.Volly_class.Crop_Post;
import com.FarmPe.Farmer.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.Farmer.Volly_class.VolleySingletonQuee;
import com.FarmPe.Farmer.volleypost.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;
import static com.FarmPe.Farmer.Volly_class.Crop_Post.progressDialog;



public class Edit_Looking_For_Fragment extends Fragment {

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static AddHpAdapter farmadapter;
    SessionManager sessionManager;
    public static String back;
    TextView toolbar_title,update_btn_txt,brand;
    JSONObject lngObject;
    JSONArray edit_req_array;
    String toast_name,toast_mobile,toast_passwrd,toast_new_mobile,toast_minimum_toast,  toast_update,toast_image;
    String addressID=null;
    LinearLayout update_btn,linearLayout,address;
    private static int RESULT_LOAD_IMG = 1;
    Bitmap selectedImage,imageB;
    EditText profile_name,profile_phone,profile_mail,profile_passwrd;
    RadioButton month_1,month_2,month_3,month_4,finance_yes,finance_no;
    CircleImageView prod_img;
ImageView b_arrow;
    TextView farmer_name,farmer_phone,farmer_email,farmer_loc,delete_req,hp_power,address_text,request;
    LinearLayout back_feed;
    Fragment selectedFragment;
    RadioGroup radio_group_time,radioGroup_finance;
    String time_period,lookingfordetails_id,modelid;
    boolean finance;
    public static int selectedId_time_recent;


    public static Edit_Looking_For_Fragment newInstance() {
        Edit_Looking_For_Fragment fragment = new Edit_Looking_For_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.farmers_detail_page, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        b_arrow=view.findViewById(R.id.b_arrow);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
       // farmer_name=view.findViewById(R.id.farmer_name);
        farmer_phone=view.findViewById(R.id.phone_no);
       // farmer_email=view.findViewById(R.id.email);
        brand=view.findViewById(R.id.brand);
       // farmer_loc=view.findViewById(R.id.loc);
        prod_img=view.findViewById(R.id.prod_img);
        hp_power=view.findViewById(R.id.hp_power);
        delete_req=view.findViewById(R.id.delete_req);
        month_1=view.findViewById(R.id.month_1);
        month_2=view.findViewById(R.id.month_2);
        month_3=view.findViewById(R.id.month_3);
        month_4=view.findViewById(R.id.month_4);
        finance_yes=view.findViewById(R.id.finance_yes);
        finance_no=view.findViewById(R.id.finance_no);
       // tractor_img=view.findViewById(R.id.tractor_img);

        linearLayout=view.findViewById(R.id.linearLayout);
        address_text=view.findViewById(R.id.address_text);
        sessionManager = new SessionManager(getActivity());

        request=view.findViewById(R.id.update_rqt);
        radio_group_time=view.findViewById(R.id.radio_group_time);
        radioGroup_finance=view.findViewById(R.id.radioGroup_finance);
        address=view.findViewById(R.id.address_layout);

        try {
            addressID=getArguments().getString("add_id");
        }catch (Exception e){

        }


        if (addressID==null){
            getting_edit();
        }else{

            String stret_name=getArguments().getString("streetname");
            addressID=getArguments().getString("add_id");
            lookingfordetails_id=getArguments().getString("looking_forId");
            modelid=getArguments().getString("modelId");
            System.out.print("wwwwwefsdwwwwwefsdddddddwwwwwefsdwwwwwefsdddddddvvvvvvvvvvvvvvvvvv" + stret_name);

            address_text.setText(stret_name);

        }


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_whitecancel));
                HomeMenuFragment.onBack_status = "looking_frg";
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("looking1", FragmentManager.POP_BACK_STACK_INCLUSIVE);


            }
        });



        radioGroup_finance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedId_time_recent = radioGroup_finance.getCheckedRadioButtonId();
                RadioButton  radioButton = (RadioButton)view.findViewById(selectedId_time_recent);
                if (String.valueOf(radioButton.getText()).equals("yes")){
                    finance=true;
                }else {
                    finance=false;
                }
            }
        });

        radio_group_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedId_time_recent = radio_group_time.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)view.findViewById(selectedId_time_recent);
                time_period=String.valueOf(radioButton.getText());
            }
        });



            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    update_profile_details();
                }
            });




        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("navigation_from", "edit_lokng_frg");
                bundle.putString("looking_forId",lookingfordetails_id);
                bundle.putString("modelId", modelid);
                selectedFragment = Add_New_Address_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.addToBackStack("edit");
                transaction.commit();
            }
        });



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    HomeMenuFragment.onBack_status = "looking_frg";

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("looking1", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });





        delete_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final TextView yes1,no1,text_desctxt,popup_headingtxt;
                final LinearLayout close_layout;
                System.out.println("aaaaaaaaaaaa");
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.delete_request_layout);
                text_desctxt =  dialog.findViewById(R.id.text_desc);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                yes1 =  dialog.findViewById(R.id.yes_1);
                no1 =  dialog.findViewById(R.id.no_1);
                popup_headingtxt =  dialog.findViewById(R.id.popup_heading);



                close_layout =  dialog.findViewById(R.id.close_layout);
                no1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                close_layout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                yes1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        delete_request();

                        dialog.dismiss();
                    }
                });


                dialog.show();




            }
        });


        return view;
    }

    private void getting_edit() {

        try{

            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));

            Crop_Post.crop_posting(getActivity(), Urls.Get_Edit_Request, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("dfdsfsf" + result);
                    try{

                        edit_req_array = result.getJSONArray("LookingForList");
                        for(int i=0;i<edit_req_array.length();i++){

                            JSONObject jsonObject1 = edit_req_array.getJSONObject(i);
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("Address");

                            String id = jsonObject1.getString("Id");
                            String purchasetimeline = jsonObject1.getString("PurchaseTimeline");
                            Boolean lookin_true = jsonObject1.getBoolean("LookingForFinance");
                            String brand_name = jsonObject1.getString("BrandName");
                            String model_name = jsonObject1.getString("Model");
                            String model_image = jsonObject1.getString("ModelImage");
                            String horse_range = jsonObject1.getString("HorsePowerRange");
                            String addrss_id = jsonObject2.getString("Id");
                            String addrss_name = jsonObject2.getString("Name");
                            String mobile_no = jsonObject2.getString("MobileNo");
                            String street_address = jsonObject2.getString("StreeAddress1");
                            String pincode = jsonObject2.getString("Pincode");
                            String state = jsonObject2.getString("State");
                            String district = jsonObject2.getString("District");
                            String taluk = jsonObject2.getString("Taluk");
                            lookingfordetails_id = jsonObject1.getString("LookingForDetailsId");
                            addrss_id = jsonObject1.getString("AddressId");
                            modelid = jsonObject1.getString("ModelId");


                            brand.setText("Brand - " + brand_name);
                            hp_power.setText("HP - " + horse_range);
                            hp_power.setText("Model - " + model_name);
                            address_text.setText(street_address + " , " + state + " , " + pincode);



                            if(lookin_true){
                                finance_yes.setChecked(true);

                            }else{

                                finance_no.setChecked(true);
                            }


                            if(purchasetimeline.equals("Immediately")){

                                month_1.setChecked(true);

                            }else if(purchasetimeline.equals("1 Month")){

                                month_2.setChecked(true);


                            }else if(purchasetimeline.equals("3 Months")){

                                month_3.setChecked(true);


                            }else if(purchasetimeline.equals("After 3 Months")){
                                month_4.setChecked(true);


                            }

//
//                            Glide.with(getActivity()).load(model_image)
//
//                                    .thumbnail(0.5f)
//                                    .crossFade()
//                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                    .into(tractor_img);
//


                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void delete_request() {

        try{


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            jsonObject.put("Id",FarmsImageAdapter.looking_forId);

            System.out.print("wwwwwefsdwwwwwefsdddddddwwwwwefsdwwwwwefsddddddd" + jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.Delete_Request, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.print("wwwwwefsdddd" + result);

                    try{

                        String status = result.getString("Status");
                        String message = result.getString("Message");


                        if(status.equals("1")){
                            int duration = 1000;
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout,message, duration);
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


                            selectedFragment = HomeMenuFragment.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.commit();


                        }else{

                            Toast.makeText(getActivity(), "Request Quotation not deleted", Toast.LENGTH_SHORT).show();
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }




    }


    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }
                }
                return null;
          /*  char c = source.charAt(index);
            if (isCharAllowed(c))
                sb.append(c);
            else
                keepOriginal = false;*/
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };



    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);

                selectedImage = BitmapFactory.decodeStream(imageStream);
                prod_img.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }else {
            Toast.makeText(getActivity(),toast_image,Toast.LENGTH_LONG).show();
        }
    }






    private void update_profile_details() {




        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            jsonObject.put("Id",FarmsImageAdapter.looking_forId);
            jsonObject.put("PurchaseTimeline",time_period);
            jsonObject.put("ModelId",modelid);

            jsonObject.put("LookingForFinance",finance);
            jsonObject.put("AddressId", addressID);
            jsonObject.put("IsAgreed","True");
            jsonObject.put("LookingForDetailsId",lookingfordetails_id);

            System.out.print("wwwwwefsdwwwwwefsdddddddwwwwwefsdwwwwwefsdddddddjjjjjjjjjjjjjjjjjjjjjjjjjjjj" + jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.AddRequestForQuotation, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.print("wwwwwefsdddd" + result);

                    try{

                        String status = result.getString("Status");
                        String message = result.getString("Message");


                        if(!(status.equals("0"))){
                            int duration = 1000;
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout,message, duration);
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


                            selectedFragment = LookingForFragment.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.commit();


                        }else{

                            Toast.makeText(getActivity(), "Request Quotation not updated", Toast.LENGTH_SHORT).show();
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });




        }catch(Exception e){
            e.printStackTrace();
        }

}

}

