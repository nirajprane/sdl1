package com.example.sdl.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sdl.MainActivity;
import com.example.sdl.R;
import com.example.sdl.chef.ChefMainActivity;

public class ManagerMainActivity extends AppCompatActivity {

    TextView heading;
    Button waiterScreen;
    Button kitchenScreen;
    Button addUser;
    Button empInfo;
    Button billPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main_screen);
        heading = findViewById(R.id.textView);
        billPayment = findViewById(R.id.buttonBP);
        waiterScreen = findViewById(R.id.buttonWS);
        kitchenScreen = findViewById(R.id.buttonKS);
        addUser = findViewById(R.id.buttonAU);
        empInfo = findViewById(R.id.buttonEI);


        billPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BillPaymentActivity.class));
            }
        });

        waiterScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManagerActivityForTable.class));
            }
        });

        kitchenScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChefMainActivity.class));
            }
        });

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddUserActivity.class));
            }
        });

        empInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserDisplayActivity.class));
            }
        });





    }
}