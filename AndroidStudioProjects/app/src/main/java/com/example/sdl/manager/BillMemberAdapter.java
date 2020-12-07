package com.example.sdl.manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdl.Order;
import com.example.sdl.R;

import java.util.ArrayList;

public class BillMemberAdapter extends RecyclerView.Adapter<BillMemberAdapter.ViewHolder> {

    //Initailize arraylist
    ArrayList<Order> arrayListMemberBill;
    //create contructor
    public BillMemberAdapter(ArrayList<Order> arrayListMember) {
        this.arrayListMemberBill=arrayListMember;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initailize view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_row_member,parent,false);

        return new BillMemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order=arrayListMemberBill.get(position);

        //set membername on textview
        holder.sNoBill.setText(Integer.toString(order.getsNo()));
        holder.itemNameBill.setText(order.getItem());
        holder.itemQuantityBill.setText(Integer.toString(order.getQuantity()));
        holder.itemPriceBill.setText(Integer.toString(order.getTotalPrice()));

    }

    @Override
    public int getItemCount() {
        return arrayListMemberBill.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        //Initailize variable

        TextView itemNameBill;
        TextView itemQuantityBill;
        TextView sNoBill;
        TextView itemPriceBill;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //assign value
            itemNameBill=itemView.findViewById(R.id.item_name_bill);
            itemQuantityBill=itemView.findViewById(R.id.item_quantity_bill);
            sNoBill=itemView.findViewById(R.id.sNo_bill);
            itemPriceBill=itemView.findViewById(R.id.item_price_bill);
        }
    }
}
