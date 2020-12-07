package com.example.sdl.manager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdl.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BillPaymentActivity extends AppCompatActivity {
    RecyclerView rvgroupbill;
    ArrayList<String> arrayListGroupBill;
    LinearLayoutManager layoutManagerGroupBill;
    BillGroupAdapter adapterGroupBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);

        rvgroupbill =findViewById(R.id.rv_group_bill);

        //using for loop to add multiple Group
        arrayListGroupBill = new ArrayList<>();
        System.out.println("insideBillActivity");

        loadFromDataBaseBill();

        /*//Initailize group adapter
        adapterGroupBill = new BillGroupAdapter(BillPaymentActivity.this,arrayListGroupBill);

        //Initialize layout manager
        layoutManagerGroupBill = new LinearLayoutManager(this);
       // layoutManagerGroup.setOrientation(LinearLayoutManager.VERTICAL);

        //set layout manager
        rvgroupbill.setLayoutManager(layoutManagerGroupBill);
        rvgroupbill.setAdapter(adapterGroupBill);*/

    }


    protected void loadFromDataBaseBill(){
        System.out.println("inside loadFromDataBaseBill");
        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference tableNode = databaseInstance.getReference("OrderForCheckout/" );
        tableNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("inside onDataChange");

                arrayListGroupBill.clear();

                // ArrayList<Order> orderListFromDatabase = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    System.out.println("inside forloop");
                    String string = ds.getKey();
                    arrayListGroupBill.add(string);
                    System.out.println(string);
                }
                System.out.println(arrayListGroupBill.size()+" size to arraylist");
                displayBill();

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    protected void displayBill(){
        //Initailize group adapter
        adapterGroupBill = new BillGroupAdapter(BillPaymentActivity.this,arrayListGroupBill);

        //Initialize layout manager
        layoutManagerGroupBill = new LinearLayoutManager(this);
        // layoutManagerGroup.setOrientation(LinearLayoutManager.VERTICAL);

        //set layout manager
        rvgroupbill.setLayoutManager(layoutManagerGroupBill);
        rvgroupbill.setAdapter(adapterGroupBill);
    }
}