package com.example.sdl.chef;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdl.LoginActivity;
import com.example.sdl.R;
import com.example.sdl.manager.ManagerMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChefMainActivity extends AppCompatActivity {
    RecyclerView rvgroup;
    ArrayList<String> arrayListGroup;
    LinearLayoutManager layoutManagerGroup;
    GroupAdp adapterGroup;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_main);

        rvgroup =findViewById(R.id.rv_group);

        //using for loop to add multiple Group
        arrayListGroup = new ArrayList<>();
        loadFromDatabase();

        //logout button
        logout= findViewById(R.id.chef_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChefMainActivity.this);
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

        /*//Initailize group adapter
        adapterGroup = new GroupAdp(ChefMainActivity.this,arrayListGroup);

        //Initialize layout manager
        layoutManagerGroup = new LinearLayoutManager(this);
       // layoutManagerGroup.setOrientation(LinearLayoutManager.VERTICAL);

        //set layout manager
        rvgroup.setLayoutManager(layoutManagerGroup);
        rvgroup.setAdapter(adapterGroup);
*/
    }

    protected void loadFromDatabase() {
        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference tableNode = databaseInstance.getReference("OrderToPrepare/" );
        tableNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayListGroup.clear();

                // ArrayList<Order> orderListFromDatabase = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    System.out.println("inside forloop");
                    String string = ds.getKey();
                    arrayListGroup.add(string);
                    System.out.println(string);
                }
                display();

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    protected void display(){

        //Initailize group adapter
        adapterGroup = new GroupAdp(ChefMainActivity.this,arrayListGroup);

        //Initialize layout manager
        layoutManagerGroup = new LinearLayoutManager(this);
        // layoutManagerGroup.setOrientation(LinearLayoutManager.VERTICAL);

        //set layout manager
        rvgroup.setLayoutManager(layoutManagerGroup);
        rvgroup.setAdapter(adapterGroup);

    }
    @Override
    public void onBackPressed()
    {
        Toast toast = Toast.makeText(getApplicationContext(), "Please Logout", Toast.LENGTH_SHORT);
        toast.show();

    }
}
