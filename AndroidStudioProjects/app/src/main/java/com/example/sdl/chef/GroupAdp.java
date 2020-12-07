package com.example.sdl.chef;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

import static com.example.sdl.Flags.colorChef;

public class GroupAdp extends RecyclerView.Adapter<GroupAdp.ViewHolder> {
    // Initialize activity and arraylist
     Activity activity;
     ArrayList<String> arrayListGroup;



    //create constructor


    public GroupAdp(Activity activity, ArrayList<String> arrayListGroup) {
        this.activity = activity;
        this.arrayListGroup = arrayListGroup;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_group,parent,false);

        return new GroupAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //set Group name on  textview
        holder.tvName.setText(arrayListGroup.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if(!colorChef[position]) {
                    holder.linearLayoutCard.setBackgroundColor(Color.GREEN);
                    colorChef[position]=true;
                }
                else{
                    //holder.linearLayoutCard.setBackgroundColor(Color.BLACK);
                    colorChef[position]=false;

                }*/
            }
        });

        //Initialize member arraylist
        final ArrayList<Order> arrayListMember= new ArrayList<>();




        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference tableNode = databaseInstance.getReference("OrderToPrepare/" + arrayListGroup.get(position) + "/");
        tableNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                // ArrayList<Order> orderListFromDatabase = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    arrayListMember.add(order);
                }

                MemberAdp adapterMember = new MemberAdp(arrayListMember);

                //Initailize layoutManager
                LinearLayoutManager layoutManagerMember = new LinearLayoutManager(activity);
                //set layout manager
                holder.rvMember.setLayoutManager(layoutManagerMember);
                //set adapter
                holder.rvMember.setAdapter(adapterMember);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        /*//using for loop to add multiple members
        for(int i=1; i<=4;i++){
            arrayListMember.add(new Order(i,"item"+i,2,44));
        }*/
        //Initialze member adapter
        MemberAdp adapterMember = new MemberAdp(arrayListMember);

        //Initailize layoutManager
        LinearLayoutManager layoutManagerMember = new LinearLayoutManager(activity);
        //set layout manager
        holder.rvMember.setLayoutManager(layoutManagerMember);
        //set adapter
        holder.rvMember.setAdapter(adapterMember);

    }

    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //initailize variable
        TextView tvName;
        RecyclerView rvMember;
        LinearLayout linearLayoutCard;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //assign variable
            tvName=itemView.findViewById(R.id.tv__name);
            rvMember=itemView.findViewById(R.id.rv_member);
            linearLayoutCard=itemView.findViewById(R.id.linear_layout_card);
            cardView=itemView.findViewById(R.id.card_view);
        }
    }

}
