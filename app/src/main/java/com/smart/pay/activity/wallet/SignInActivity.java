package com.smart.pay.activity.wallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smart.pay.R;
import com.smart.pay.Retrofit_Models.LoginModel;
import com.smart.pay.api.ApiUtils;
import com.smart.pay.api.MainAPIInterface;
import com.smart.pay.models.output.UserProfileOutput;
import com.smart.pay.utils.DataVaultManager;
import com.smart.pay.views.MyEditText;
import com.smart.pay.views.MyTextView;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    MyTextView signup;
    MyTextView signin1;

    MyEditText edtMobile, edtPassword;

    String strMobile, strPassword;

    ProgressDialog dialog;
   // MainAPIInterface mainAPIInterface;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        //mainAPIInterface = ApiUtils.getAPIService();

        signup = (MyTextView) findViewById(R.id.signup);
        signin1 = (MyTextView) findViewById(R.id.signin1);

        edtMobile = (MyEditText) findViewById(R.id.edtMobile);
        edtPassword = (MyEditText) findViewById(R.id.edtPassword);


        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strMobile = edtMobile.getText().toString();
                strPassword = edtPassword.getText().toString();

                if (strMobile.length() < 5) {
                    edtMobile.setFocusable(true);
                    edtMobile.setError("Eneter mobile number");
                } else if (strPassword.length() < 5) {
                    edtPassword.setFocusable(true);
                    edtPassword.setError("Enter password");
                } else {
                    //  userLoginRequest();
                   /* Intent newIntent = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(newIntent);
                    finish();*/

                    userlogin1();
                }


            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newIntent = new Intent(SignInActivity.this, MobileNumberRegistrationActivity.class);
                startActivity(newIntent);
                finish();

            }
        });
    }

   /* public void userlogin() {

        String xAccessToken = "mykey";

        dialog = new ProgressDialog(SignInActivity.this);

        dialog.setMessage("Verifying your details.");
        dialog.show();

        MultipartBody.Part phone_body = MultipartBody.Part.createFormData("phone_number", strMobile);

        MultipartBody.Part password_body = MultipartBody.Part.createFormData("password", strPassword);

//        Toast.makeText(this, "MultipartBody Phone "+phone_body, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "MultipartBody pass "+password_body, Toast.LENGTH_SHORT).show();

        Log.v("Login","String Phone "+strMobile);
        Log.v("Login","String pass "+strPassword);

        Log.v("Login","MultipartBody Phone "+phone_body);
        Log.v("Login","MultipartBody pass "+password_body);

        mainAPIInterface.userLogin(strMobile,strPassword).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call call, Response response) {

                Log.d("Login", "onResponse: "+response);

                if (response.isSuccessful()) {
                    dialog.dismiss();

                    Toast.makeText(SignInActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                    *//*if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        Toast.makeText(SignInActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        DataVaultManager.getInstance(SignInActivity.this).saveName(response.body().getProfile().getUsername());
                        DataVaultManager.getInstance(SignInActivity.this).saveUserEmail(response.body().getProfile().getEmail());
                        DataVaultManager.getInstance(SignInActivity.this).saveUserPassword(strPassword);
                        DataVaultManager.getInstance(SignInActivity.this).saveUserMobile(response.body().getProfile().getMobile());
                        DataVaultManager.getInstance(SignInActivity.this).saveUserId(response.body().getProfile().getUserId());
                        DataVaultManager.getInstance(SignInActivity.this).saveWalletId(response.body().getProfile().getWallet_id());


                        Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();


                    }*//*

                }else{
                    dialog.dismiss();
                    Toast.makeText(SignInActivity.this, "Error" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                dialog.dismiss();
                Toast.makeText(SignInActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }*/

    public void userlogin1(){

        dialog = new ProgressDialog(SignInActivity.this);

        dialog.setMessage("Verifying your details.");
        dialog.show();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.224.184.209/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create(gson))
                // at last we are building our retrofit builder.
                .build();

        MainAPIInterface mainAPIInterface1 = retrofit.create(MainAPIInterface.class);

        // passing data from our text fields to our modal class.
        LoginModel loginModel = new LoginModel(strMobile, strPassword);

        // calling a method to create a post and passing our modal class.
        Call<ResponseBody> call = mainAPIInterface1.userLogin(loginModel);

        // on below line we are executing our method

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("Log", "onResponse: "+response);

                if (response.isSuccessful()) {
                    try {
                        String s = response.body().string();
                        Toast.makeText(SignInActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                        finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                    Toast.makeText(SignInActivity.this, "Phone Number or Password is Incorrect" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Log.d("Log", "onthrowable: "+t.getMessage());
            }
        });


    }

    /*private void userLoginRequest() {
        String xAccessToken = "mykey";

        dialog = new ProgressDialog(SignInActivity.this);

        dialog.setMessage("Verifying your details.");
        dialog.show();

        MultipartBody.Part phone_body = MultipartBody.Part.createFormData("phone_no", strMobile);

        MultipartBody.Part password_body = MultipartBody.Part.createFormData("password", strPassword);


        mainAPIInterface.userLogin(xAccessToken, phone_body,password_body).enqueue(new Callback<UserProfileOutput>() {
            @Override
            public void onResponse(Call<UserProfileOutput> call, Response<UserProfileOutput> response) {

                if (response.isSuccessful()) {

                    dialog.dismiss();

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        Toast.makeText(SignInActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        DataVaultManager.getInstance(SignInActivity.this).saveName(response.body().getProfile().getUsername());
                        DataVaultManager.getInstance(SignInActivity.this).saveUserEmail(response.body().getProfile().getEmail());
                        DataVaultManager.getInstance(SignInActivity.this).saveUserPassword(strPassword);
                        DataVaultManager.getInstance(SignInActivity.this).saveUserMobile(response.body().getProfile().getMobile());
                        DataVaultManager.getInstance(SignInActivity.this).saveUserId(response.body().getProfile().getUserId());
                        DataVaultManager.getInstance(SignInActivity.this).saveWalletId(response.body().getProfile().getWallet_id());


                        Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();


                    }

                }
            }

            @Override
            public void onFailure(Call<UserProfileOutput> call, Throwable t) {
                dialog.dismiss();
                Log.i("tag", t.getMessage().toString());
            }
        });


    }*/

}
