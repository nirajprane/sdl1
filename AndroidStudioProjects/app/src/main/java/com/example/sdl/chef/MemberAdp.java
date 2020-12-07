package com.example.sdl.chef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdl.Order;
import com.example.sdl.R;

import java.util.ArrayList;

public class MemberAdp extends RecyclerView.Adapter<MemberAdp.ViewHolder> {

    //Initailize arraylist
    ArrayList<Order> arrayListMember;
    //create contructor
    public MemberAdp(ArrayList<Order> arrayListMember) {
        this.arrayListMember=arrayListMember;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initailize view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_member,parent,false);

        return new MemberAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order=arrayListMember.get(position);
        //set membername on textview
        holder.itemName.setText(order.getItem());
        holder.itemQuantity.setText(Integer.toString(order.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return arrayListMember.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        //Initailize variable

        TextView itemName;
        TextView itemQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //assign value
            itemName=itemView.findViewById(R.id.item_name);
            itemQuantity=itemView.findViewById(R.id.item_quantity);
        }
    }
}
