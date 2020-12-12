package com.example.sdl.OrderSummary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sdl.ActivityForTable;
import com.example.sdl.menu.MenuActivityParcel;
import com.example.sdl.Order;
import com.example.sdl.R;
import com.example.sdl.menu.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.sdl.Flags.cParcelFlag;
import static com.example.sdl.Flags.fromParcelActivity;

public class ParcelActivity extends AppCompatActivity {

    ArrayList<Menu> menuList = new ArrayList<>();
    public ArrayList<Order> orderList = new ArrayList<Order>();
    public ArrayList<Order> orderListForDatabase = new ArrayList<>();
    public ArrayList<Order> orderListForChefDatabase = new ArrayList<>();
    ListView list;
    public String parcelNoFromMenu;
   public String parcelNoFromParcel;

    OrderSummaryListAdapter orderSummaryListAdapter;
    int sizeOfOrderListBeforeNewAdd=0;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Button addItemButton = (Button) findViewById(R.id.add_item);
        Button passOrderButton = (Button) findViewById(R.id.passToChef);
        Button endOrder = (Button) findViewById(R.id.endOrder);
        TextView parcel = (TextView) findViewById(R.id.tableNo);

        parcelNoFromMenu = getIntent().getStringExtra("parcelNoFromMenu");
        parcelNoFromParcel = getIntent().getStringExtra("parcelNoFromActivityForParcel");


        list = findViewById(R.id.listViewOrder);


        //to check from where orderActivity is opened
        if (parcelNoFromParcel == null  ) {

            Bundle args = getIntent().getBundleExtra("BUNDLE");
            menuList =args.getParcelableArrayList("ARRAYLIST");

            addItemToOrderList();
            parcel.setText(parcelNoFromMenu);
            displayList();
        }
        else {

            loadFromDatabase();
            parcel.setText(parcelNoFromParcel);
        }


        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dataExist=false;
                Intent i = new Intent(ParcelActivity.this, MenuActivityParcel.class);

                if(parcelNoFromParcel==null){
                    i.putExtra("parcelNo", parcelNoFromMenu);
                    i.putExtra("additem","abcd");
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("Before",menuList);
                    i.putExtras(bundle);
                }else{
                    i.putExtra("parcelNo",parcelNoFromParcel);
                }
                startActivityForResult(i, 1);
            }

        });

        passOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadToDatabase("Parcel/"+parcelNoFromMenu);
                int parcelPos = Integer.parseInt(String.valueOf(parcelNoFromMenu.charAt(1)));

                cParcelFlag[parcelPos-1] = true;
                System.out.println(cParcelFlag[parcelPos-1]+" cccccccccccccccccccccc");
                Intent intent = new Intent(ParcelActivity.this, ActivityForTable.class);
                intent.putExtra("parcelNoFromParcelSummary", parcelNoFromMenu);
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == 2) {
                System.out.println("on act result..........................");
                Bundle bundle = data.getExtras();
                ArrayList<Menu> addItemList = bundle.getParcelableArrayList("AddedMenu");
                parcelNoFromMenu = data.getStringExtra("parcelNoFromMenu");

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

    protected void displayList() {

        orderSummaryListAdapter = new OrderSummaryListAdapter(this, orderList);
        list.setAdapter(orderSummaryListAdapter);
    }


    protected void loadToDatabase(String path) {

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
        DatabaseReference itemNode = databaseInstance.getReference("OrderToPrepare/" +parcelNoFromMenu+ "/");
        orderListForDatabase =  orderSummaryListAdapter.passOrderListForDatabase();
        int newOrder = sizeOfOrderListBeforeNewAdd-menuList.size();
        for (int i = newOrder; i < orderListForDatabase.size(); i++) {
            orderListForChefDatabase.add(orderListForDatabase.get(i));

        }
        itemNode.setValue(orderListForChefDatabase);

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference statusNode= firebaseDatabase.getReference("ChefOrderStatus/"+parcelNoFromMenu);
        statusNode.setValue("not started");

    }

    protected void loadFromDatabase() {
        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference tableNode = databaseInstance.getReference("Parcel/" + parcelNoFromParcel + "/");
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

    }

    protected void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ParcelActivity.this);
        builder.setTitle("Confirmation").setMessage("Do you want this to CheckOut")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadToDatabase("OrderForCheckout/"+parcelNoFromParcel);
                        removeDataFromCaptain(parcelNoFromParcel);
                        int parcelNo= Integer.parseInt(String.valueOf(parcelNoFromParcel.charAt(1)));
                        fromParcelActivity[parcelNo-1]=false;
                        cParcelFlag[parcelNo-1] = false;
                        Intent checkoutIntent = new Intent(ParcelActivity.this, ActivityForTable.class);
                        startActivity(checkoutIntent);
                        finish();

                    }
                }).setNegativeButton("cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void removeDataFromCaptain(String parcelNo){

        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference tableNode = databaseInstance.getReference("Parcel/" + parcelNo );
        tableNode.removeValue();
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ParcelActivity.this, ActivityForTable.class));
        finish();

    }



}