package com.example.sdl.OrderSummary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.sdl.R;
import com.example.sdl.menu.MenuActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    ArrayList<String> menuList = new ArrayList<>();
    ArrayList<Integer> quantityOfItem;
    ArrayList<Order> orderList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        menuList = (ArrayList<String>) getIntent().getSerializableExtra("key");
        // System.out.println(menuList.size()+"original menu size");

        list = findViewById(R.id.listViewOrder);
        orderList = new ArrayList<>();


        quantityOfItem = new ArrayList<>(menuList.size());
        displayList();

        SummaryListAdapter summaryListAdapterAdapter = new SummaryListAdapter(this, orderList);
        list.setAdapter(summaryListAdapterAdapter);

        Button addItemButton = (Button) findViewById(R.id.add_item);
        Button passOrderButton = (Button) findViewById(R.id.passToChef);
        Button endOrder = (Button) findViewById(R.id.endOrder);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, MenuActivity.class);
                startActivityForResult(i, 1);
            }

        });

        passOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadToDatabase();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == 2) {
                ArrayList<String> addItemList = (ArrayList<String>) getIntent().getSerializableExtra("key");


                System.out.println(addItemList.get(0) + "arrayllist has come");

                for (int i = 0; i < addItemList.size(); i++) {
                    menuList.add(addItemList.get(i));

                }

                System.out.println(menuList.size() + "added menu size");

                displayList();


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    protected void displayList() {
        for (int i = 0; i < menuList.size(); i++) {
            quantityOfItem.add(1);
            orderList.add(new Order(i + 1, menuList.get(i), quantityOfItem.get(i)));
            System.out.println(orderList.get(i));

        }
        SummaryListAdapter summaryListAdapterAdapter = new SummaryListAdapter(this, orderList);
        list.setAdapter(summaryListAdapterAdapter);
    }

    protected void loadToDatabase(){

    }
}