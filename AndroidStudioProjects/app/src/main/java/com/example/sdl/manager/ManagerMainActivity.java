package com.example.sdl.manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sdl.ActivityForTable;
import com.example.sdl.LoginActivity;
import com.example.sdl.MainActivity;
import com.example.sdl.OrderSummary.OrderActivity;
import com.example.sdl.R;
import com.example.sdl.chef.ChefMainActivity;

public class ManagerMainActivity extends AppCompatActivity {

    TextView heading;
    Button waiterScreen;
    Button kitchenScreen;
    Button addUser;
    Button empInfo;
    Button billPayment;
    Button logout;


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
        logout= findViewById(R.id.manager_logout);


        billPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BillPaymentActivity.class));
                finish();
            }
        });

        waiterScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManagerActivityForTable.class));
                finish();
            }
        });

        kitchenScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChefMainActivity.class));
                finish();
            }
        });

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddUserActivity.class));
                finish();
            }
        });

        empInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserDisplayActivity.class));
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManagerMainActivity.this);
                builder.setTitle("Confirmation PopUp!").
                        setMessage("You sure, that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(getApplicationContext(),
                                        LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });





    }
    @Override
    public void onBackPressed()
    {
        Toast toast = Toast.makeText(getApplicationContext(), "Please Logout", Toast.LENGTH_SHORT);
        toast.show();

    }
}