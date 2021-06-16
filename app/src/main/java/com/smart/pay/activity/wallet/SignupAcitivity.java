package com.smart.pay.activity.wallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smart.pay.R;
import com.smart.pay.Retrofit_Models.LoginModel;
import com.smart.pay.Retrofit_Models.UserRegistrationModel;
import com.smart.pay.SmartPayApplication;
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

public class SignupAcitivity extends AppCompatActivity {

    MyTextView btnSignUp;
    MyTextView btnSignIn;

    MyEditText txtUserName;
    MyEditText txtUserEmail;
    MyEditText txtUserPassword;

    CheckBox checkbocremember;

    ProgressDialog dialog;

    MainAPIInterface mainAPIInterface;

    String strUserName, strUserEmail, strUserPassword;

    String phone_number = "8888888888";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail_layout);

        mainAPIInterface = ApiUtils.getAPIService();

        btnSignUp = (MyTextView) findViewById(R.id.btnSignUp);
        btnSignIn = (MyTextView) findViewById(R.id.btnSignIn);

        txtUserName = (MyEditText) findViewById(R.id.txtUserName);
        txtUserEmail = (MyEditText) findViewById(R.id.txtUserEmail);
        txtUserPassword = (MyEditText) findViewById(R.id.txtUserPassword);

        checkbocremember = (CheckBox) findViewById(R.id.checkbocremember);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strUserName = txtUserName.getText().toString();
                strUserEmail = txtUserEmail.getText().toString();
                strUserPassword = txtUserPassword.getText().toString();

                if (strUserName.length() < 5) {
                    txtUserName.setFocusable(true);
                    txtUserName.setError("Enter name");
                } else if (strUserEmail.length() < 4) {
                    txtUserEmail.setFocusable(true);
                    txtUserEmail.setError("Enter email");
                } else if (strUserPassword.length() < 6) {
                    txtUserPassword.setFocusable(true);
                    txtUserPassword.setError("Enter password,with atleast 6 digit");
                } else {
                    userSignupRequest();
                }

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newIntent = new Intent(SignupAcitivity.this, SignInActivity.class);
                startActivity(newIntent);
                finish();

            }
        });

    }

    private void userSignupRequest() {

        dialog = new ProgressDialog(SignupAcitivity.this);

        dialog.setMessage("Adding your details.");
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
        UserRegistrationModel registrationModel = new UserRegistrationModel(phone_number,strUserName,strUserEmail, strUserPassword);

        // calling a method to create a post and passing our modal class.
        Call<ResponseBody> call = mainAPIInterface1.userSignup(registrationModel);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("Log", "onResponse: "+response);

                if (response.isSuccessful()) {
                    try {
                        String s = response.body().string();
                        Toast.makeText(SignupAcitivity.this, ""+s, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupAcitivity.this,HomeActivity.class));
                        finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                    Toast.makeText(SignupAcitivity.this, "Registration Failed...Please try again" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Log.d("Log", "onthrowable: "+t.getMessage());
            }
        });


    }

}
/*
private void userSignupRequest() {

        String xAccessToken = "mykey";

        dialog = new ProgressDialog(SignupAcitivity.this);

        dialog.setMessage("Adding your details.");
        dialog.show();

        MultipartBody.Part phone_body = MultipartBody.Part.createFormData("phone_no", SmartPayApplication.CUSTOMER_MOBILE);
        MultipartBody.Part email_body = MultipartBody.Part.createFormData("email", strUserEmail);
        MultipartBody.Part username_body = MultipartBody.Part.createFormData("username", strUserName);

        MultipartBody.Part password_body = MultipartBody.Part.createFormData("password", strUserPassword);


        mainAPIInterface.userSignup(xAccessToken, email_body, phone_body, username_body, password_body).enqueue(new Callback<UserProfileOutput>() {
            @Override
            public void onResponse(Call<UserProfileOutput> call, Response<UserProfileOutput> response) {

                if (response.isSuccessful()) {

                    dialog.dismiss();

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        Toast.makeText(SignupAcitivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        DataVaultManager.getInstance(SignupAcitivity.this).saveName(response.body().getProfile().getUsername());
                        DataVaultManager.getInstance(SignupAcitivity.this).saveUserEmail(response.body().getProfile().getEmail());
                        DataVaultManager.getInstance(SignupAcitivity.this).saveUserPassword(strUserPassword);
                        DataVaultManager.getInstance(SignupAcitivity.this).saveUserMobile(response.body().getProfile().getMobile());
                        DataVaultManager.getInstance(SignupAcitivity.this).saveUserId(response.body().getProfile().getUserId());
                        DataVaultManager.getInstance(SignupAcitivity.this).saveWalletId(response.body().getProfile().getWallet_id());

                        Intent i = new Intent(SignupAcitivity.this, HomeActivity.class);
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


    }
 */