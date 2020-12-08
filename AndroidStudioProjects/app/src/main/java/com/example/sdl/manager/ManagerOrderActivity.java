package com.example.sdl.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sdl.ActivityForTable;
import com.example.sdl.Order;
import com.example.sdl.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerOrderActivity extends AppCompatActivity {

//    ArrayList<Menu> menuList = new ArrayList<>();
    public ArrayList<Order> orderListManager = new ArrayList<Order>();
    /*public ArrayList<Order> orderListForDatabase = new ArrayList<>();
    public ArrayList<Order> orderListForChefDatabase = new ArrayList<>();*/
    ListView list;
   // String tableNoFromMenu;
    String tableNo;
    ManagerOrderSummaryListAdapter managerOrderSummaryListAdapter;
    int sizeOfOrderListBeforeNewAdd=0;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Button addItemButton = (Button) findViewById(R.id.add_item);
        Button passOrderButton = (Button) findViewById(R.id.passToChef);
        Button endOrder = (Button) findViewById(R.id.endOrder);
        TextView table = (TextView) findViewById(R.id.tableNo);

        addItemButton.setVisibility(View.INVISIBLE);
        passOrderButton.setVisibility(View.INVISIBLE);
        endOrder.setVisibility(View.INVISIBLE);

       /* tableNoFromMenu = getIntent().getStringExtra("tableNoFromMenu");*/
        tableNo = getIntent().getStringExtra("tableNo");

        list = findViewById(R.id.listViewOrder);


        //to check from where orderActivity is opened
        /*if (tableNoFromOrder == null ) {

            Bundle args = getIntent().getBundleExtra("BUNDLE");
            menuList =args.getParcelableArrayList("ARRAYLIST");

            addItemToOrderList();
            table.setText(tableNoFromMenu);
            displayList();
        }
        else {
*/

        table.setText(tableNo);
        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference tableNode = databaseInstance.getReference("Table/" + tableNo + "/");
        tableNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // ArrayList<Order> orderListFromDatabase = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    orderListManager.add(order);
                }
                if(orderListManager.isEmpty()){

                }
                displayList();
                /*managerOrderSummaryListAdapter = new ManagerOrderSummaryListAdapter(this, orderListManager);
                list.setAdapter(managerOrderSummaryListAdapter);*/
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        /*addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dataExist=false;
                Intent i = new Intent(ManagerOrderActivity.this, MenuActivity.class);
                i.putExtra("tableNo", tableNoFromOrder);
                startActivityForResult(i, 1);
            }

        });

        passOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadToDatabase("Table/"+tableNoFromMenu);

                Intent intent = new Intent(com.example.sdl.OrderSummary.OrderActivity.this, ActivityForTable.class);
                intent.putExtra("tableNoFromOrderSummary", tableNoFromMenu);
                //intent.putExtra();
                startActivity(intent);
                loadToDatabaseForChef();
                finish();
            }
        });
        endOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
*/

    }

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == 2) {

                Bundle bundle = data.getExtras();
                ArrayList<Menu> addItemList = bundle.getParcelableArrayList("AddedMenu");
                tableNoFromMenu = data.getStringExtra("tableNoFromMenu");

                for (int i = 0; i < addItemList.size(); i++) {
                    menuList.add(addItemList.get(i));


                }

                addItemToOrderList();
                displayList();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    protected void addItemToOrderList() {
        sizeOfOrderListBeforeNewAdd = orderList.size();
        for(Menu menu : menuList)
        {
            orderList.add(new Order(sizeOfOrderListBeforeNewAdd++ + 1,
                    menu.getItemName(),1,menu.getItemPrice()));

        }
    }
*/
    protected void displayList() {

        managerOrderSummaryListAdapter = new ManagerOrderSummaryListAdapter(this, orderListManager);
                list.setAdapter(managerOrderSummaryListAdapter);
    }


   /* protected void loadToDatabase(String path) {

        //Getting Instance of Firebase realtime database
        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();


        //Getting Reference to a User node, (it will be created if not already there)
        DatabaseReference itemNode = databaseInstance.getReference(path + "/");
        orderListForDatabase =  orderSummaryListAdapter.passOrderListForDatabase();
        itemNode.setValue(orderListForDatabase);

    }
    protected void loadToDatabaseForChef() {

        //Getting Instance of Firebase realtime database
        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
     //   String

        //Getting Reference to a User node, (it will be created if not already there)
        DatabaseReference itemNode = databaseInstance.getReference("OrderToPrepare/" +tableNoFromMenu+ "/");
        orderListForDatabase =  orderSummaryListAdapter.passOrderListForDatabase();
        int newOrder = sizeOfOrderListBeforeNewAdd-menuList.size();
        for (int i = newOrder; i < orderListForDatabase.size(); i++) {
            orderListForChefDatabase.add(orderListForDatabase.get(i));

        }
        itemNode.setValue(orderListForChefDatabase);

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference statusNode= firebaseDatabase.getReference("ChefOrderStatus/"+tableNoFromMenu);
        statusNode.setValue("not started");

    }
*/
    /*protected void loadFromDatabase() {
        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference tableNode = databaseInstance.getReference("Table/" + tableNoFromOrder + "/");
        tableNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // ArrayList<Order> orderListFromDatabase = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    orderList.add(order);
                }
                displayList();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/
/*
    protected void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(com.example.sdl.OrderSummary.OrderActivity.this);
        builder.setTitle("Confirmation").setMessage("Do you want this to CheckOut")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadToDatabase("OrderForCheckout/"+tableNoFromOrder);
                        removeDataFromCaptain(tableNoFromOrder);
                        int tableNo= Integer.parseInt(String.valueOf(tableNoFromOrder.charAt(1)));
                        fromOrderActivity[tableNo-1]=false;
                        cFlag[tableNo-1] = false;
                        Intent checkoutIntent = new Intent(com.example.sdl.OrderSummary.OrderActivity.this, ActivityForTable.class);
                        startActivity(checkoutIntent);
                        finish();

                    }
                }).setNegativeButton("cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void removeDataFromCaptain(String tableNo){

        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference tableNode = databaseInstance.getReference("Table/" + tableNo );
        tableNode.removeValue();
    }*/
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ManagerOrderActivity.this, ManagerActivityForTable.class));
        finish();

    }



}