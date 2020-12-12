package com.example.sdl.manager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdl.Order;
import com.example.sdl.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BillGroupAdapter extends RecyclerView.Adapter<BillGroupAdapter.ViewHolder> {
    // Initialize activity and arraylist
     Activity activityBill;
     ArrayList<String> arrayListGroupBill;



    //create constructor


    public BillGroupAdapter(Activity activity, ArrayList<String> arrayListGroup) {
        this.activityBill = activity;
        this.arrayListGroupBill = arrayListGroup;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_row_group,parent,false);

        return new BillGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //set Group name on  textview
        holder.tvNameBill.setText(arrayListGroupBill.get(position));

        //Initialize member arraylist
        final ArrayList<Order> arrayListMemberBill= new ArrayList<>();
        System.out.println("inside BillGropAdapter");
        /*int total=0;
        for(int i=0;i<arrayListMemberBill.size();i++)
        {
            Order order=arrayListMemberBill.get(position);
            System.out.println(order.getTotalPrice()+" total price ");
            total = total + order.getTotalPrice();
        }
        holder.totalAmount.setText(String.valueOf(total));*/

        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference tableNode = databaseInstance.getReference("OrderForCheckout/" + arrayListGroupBill.get(position) + "/");
        tableNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int total=0;
                // ArrayList<Order> orderListFromDatabase = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    total=total+order.getTotalPrice();
                    arrayListMemberBill.add(order);
                }


                holder.totalAmount.setText(String.valueOf(total));
        //Initialze member adapter
        BillMemberAdapter billMemberAdapter = new BillMemberAdapter(arrayListMemberBill);

        //Initailize layoutManager
        LinearLayoutManager billLayoutManagerMember = new LinearLayoutManager(activityBill);
        //set layout manager
        holder.rvMemberBill.setLayoutManager(billLayoutManagerMember);
        //set adapter
        holder.rvMemberBill.setAdapter(billMemberAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.confirmCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to save order in reciept and delete from orderforcheckoutnode
                FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
                DatabaseReference addOrderRecipt = databaseInstance.getReference("Reciept/"  );
                addOrderRecipt.push().child(arrayListGroupBill.get(position)).setValue(arrayListMemberBill);

                DatabaseReference endOrder = databaseInstance.getReference("OrderForCheckout/" + arrayListGroupBill.get(position) + "/");
                endOrder.removeValue();


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListGroupBill.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //initailize variable
        TextView tvNameBill;
        RecyclerView rvMemberBill;
        Button confirmCheckout;
        TextView totalAmount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //assign variable
            tvNameBill=itemView.findViewById(R.id.tv__name_bill);
            rvMemberBill=itemView.findViewById(R.id.rv_member_bill);
            confirmCheckout=itemView.findViewById(R.id.confirm_checkout);
            totalAmount=itemView.findViewById(R.id.total_amount);
        }
    }
}
