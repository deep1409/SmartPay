package com.smart.pay.activity.wallet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smart.pay.R;
import com.smart.pay.views.MyEditText;
import com.smart.pay.views.MyTextView;

public class RegisterCustomerDetailsActivity extends AppCompatActivity {

    MyEditText txtUserName,txtUserEmail,txtUserPassword;
    MyTextView btnSignUp;
    ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail_layout);

        txtUserName = findViewById(R.id.txtUserName);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        txtUserPassword = findViewById(R.id.txtUserPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

    }
}
