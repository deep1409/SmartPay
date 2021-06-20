package com.smart.pay.activity.wallet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.smart.pay.R;
import com.smart.pay.Retrofit_Models.RechargeModel;
import com.smart.pay.SmartPayApplication;
import com.smart.pay.api.ApiUtils;
import com.smart.pay.api.MainAPIInterface;
import com.smart.pay.models.input.MobileRechargeModel;
import com.smart.pay.models.output.CommonOutput;
import com.smart.pay.utils.DataVaultManager;
import com.smart.pay.views.MyEditText;
import com.smart.pay.views.MyTextView;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.smart.pay.utils.DataVaultManager.KEY_USER_ID;
import static com.smart.pay.utils.DataVaultManager.KEY_WALLET_ID;

public class MobileRechargeActivity extends AppCompatActivity implements PaymentResultListener {

    private static final int RESULT_PICK_CONTACT=1;

    MyTextView payButton;

    Switch rechargeSwitch;

    ImageView contact_list;
    MyEditText edtphone_number;
    MyEditText edtOperator;
    MyEditText edtAmount;

    MyTextView btnSeePlans;

    String strPhone, strOperatorCode, strAmount, strPlan, recharge_type = "1";

    ProgressDialog newProgressDialog;
    MainAPIInterface mainAPIInterface;

    int amount;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recharge_activity);
        setupActionBar();

        mainAPIInterface = ApiUtils.getAPIService();


        payButton = (MyTextView) findViewById(R.id.payButton);
        rechargeSwitch = (Switch) findViewById(R.id.rechargeSwitch);

        edtphone_number = (MyEditText) findViewById(R.id.edtphone_number);
        edtOperator = (MyEditText) findViewById(R.id.edtOperator);
        edtAmount = (MyEditText) findViewById(R.id.edtAmount);

        btnSeePlans = (MyTextView) findViewById(R.id.btnSeePlans);

        contact_list = findViewById(R.id.contact_list);

        contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult (intent, RESULT_PICK_CONTACT);
            }
        });

        rechargeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                Log.v("Switch State=", "" + isChecked);

                if (isChecked) {
                    recharge_type = "2";
                } else {
                    recharge_type = "1";
                }

            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strPhone = edtphone_number.getText().toString();
                strOperatorCode = edtOperator.getText().toString();
                strAmount = edtAmount.getText().toString();

                if (strPhone.length() == 0) {
                    edtphone_number.setError("Enter mobile number");
                    edtphone_number.setFocusable(true);
                } else if (strOperatorCode.length() == 0) {
                    edtOperator.setFocusable(true);
                    edtOperator.setError("Select Operator");
                } else if (strAmount.length() == 0) {
                    edtAmount.setFocusable(true);
                    edtAmount.setError("Enter the amount");
                } else {

                  //  placeRechargeRequest();

                    openRazorPay();

                }

            }
        });

    }

    private void openRazorPay() {

        //Round off amount
        amount = Math.round(Float.parseFloat(strAmount) * 100);

        //Initialize Razorpay
        Checkout checkout = new Checkout();

        //Api key
        checkout.setKeyID("rzp_test_ghacHhDVqlrHSf");

        //Set Image
        checkout.setImage(R.drawable.logo2);

        //Initialize JSONObject
        JSONObject object = new JSONObject();

        try {

            //Name
            object.put("name","Mobile Recharge");

            //Description
            object.put("description","Test Payment");

            //Theme Color
            object.put("theme.color","#0093DD");

            //Currency
            object.put("currency","INR");

            //Amount
            object.put("amount",amount);

//            //Contact number
//            object.put("prefill.contact",strPhone);
//            //Email
//            object.put("prefill.email","youremail@gmail.com");

            checkout.open(MobileRechargeActivity.this,object);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public  void m_recharge(){

        newProgressDialog = new ProgressDialog(MobileRechargeActivity.this);

        newProgressDialog.setMessage("Adding your details.");
        newProgressDialog.show();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mpesa.co.in/Recharge/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create(gson))
                // at last we are building our retrofit builder.
                .build();

        /*MainAPIInterface mainAPIInterface1 = retrofit.create(MainAPIInterface.class);

        RechargeModel rechargeModel = new RechargeModel("","","","","","","");

        Call<ResponseBody> call = mainAPIInterface1.mpesa_recharge(rechargeModel);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Toast.makeText(this, "Failed to pick the contact ", Toast.LENGTH_SHORT).show();
        }
    }

    private  void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null,null,null,null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);

            String final_phone_number = phoneNo;
            String str;

            if(final_phone_number.substring(0,3).equals("+91") ){
                str = ""+final_phone_number.substring(3);
            }else if(final_phone_number.substring(0,1).equals("0")){
                str = ""+final_phone_number.substring(1);
            }
            else{
                str = final_phone_number;
            }


            char[] strArray = str.toCharArray();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < strArray.length; i++) {
                if ((strArray[i] != ' ') && (strArray[i] != '\t')) {
                    stringBuffer.append(strArray[i]);
                }
            }
            String space_removed_phone_number = stringBuffer.toString();


            edtphone_number.setText(space_removed_phone_number);

            }
        catch (Exception e)
         {
            e.printStackTrace();
        }
    }

    private void placeRechargeRequest() {

        String xAccessToken = "mykey";

        String user_id = DataVaultManager.getInstance(SmartPayApplication.getInstance()).getVaultValue(KEY_USER_ID);

        newProgressDialog = new ProgressDialog(MobileRechargeActivity.this);

        newProgressDialog.setMessage("Placing your request.");

        newProgressDialog.show();


        MobileRechargeModel mobileRechargeModel = new MobileRechargeModel();
        mobileRechargeModel.setUser_id(user_id);
        mobileRechargeModel.setAmount(strAmount);
        mobileRechargeModel.setOperatorCode(strOperatorCode);
        mobileRechargeModel.setOperatorName("Airtel");
        mobileRechargeModel.setRechargeType(recharge_type);
        mobileRechargeModel.setRechargeNumber(strPhone);
        mobileRechargeModel.setPlanId("1");
        mobileRechargeModel.setPaymentType("1");
        mobileRechargeModel.setPaymentStatus("1");


        mainAPIInterface.placeMobileRechargeRequest(xAccessToken, mobileRechargeModel).enqueue(new Callback<CommonOutput>() {
            @Override
            public void onResponse(Call<CommonOutput> call, Response<CommonOutput> response) {

                if (response.isSuccessful()) {

                    newProgressDialog.dismiss();

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        Toast.makeText(MobileRechargeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MobileRechargeActivity.this, ThankYouRechargeDone.class);
                        startActivity(intent);
                        finish();


                    }


                }
            }

            @Override
            public void onFailure(Call<CommonOutput> call, Throwable t) {
                newProgressDialog.dismiss();
                Log.i("tag", t.getMessage().toString());
            }
        });

    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView menu_icon = toolbar.findViewById(R.id.menu_icon);
        menu_icon.setVisibility(View.GONE);


        TextView toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setVisibility(View.VISIBLE);

        toolbarTitle.setText("Phone Recharge");

        ActionBar bar = getSupportActionBar();
        bar.setDisplayUseLogoEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // action bar menu behaviour
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Context context;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Payment ID");
//        builder.setMessage(s);
//        builder.show();

        abc(s);

    }

    public void abc(String id){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage("Phone Number: "+strPhone +"\nAmount: "+strAmount +"\nPayment ID: "+id);
        builder.show();

    }

    @Override
    public void onPaymentError(int i, String s) {
     //   Toast.makeText(this, "Payment failed "+s, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Failed");
        builder.setMessage(s);
        builder.show();
    }
}
