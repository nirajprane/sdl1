package com.example.sdl.chef;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdl.OrderSummary.Order;
import com.example.sdl.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_main);

        rvgroup =findViewById(R.id.rv_group);

        //using for loop to add multiple Group
        arrayListGroup = new ArrayList<>();
        loadFromDatabase();

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
                    String string = ds.getKey();
                    arrayListGroup.add(string);
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
}
