package com.example.sdl.chef;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdl.Order;
import com.example.sdl.R;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupAdp extends RecyclerView.Adapter<GroupAdp.ViewHolder> {
    // Initialize activity and arraylist
     Activity activity;
     ArrayList<String> arrayListGroup;
     String location;
    SharedPreferences sharedPreferences;
    public static final String Table_Name = "Name_PREFS";
    public static final String Table_Key = "String_PREFS";
    public String[] checkStatus = new String[7];



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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //set Group name on  textview

        holder.tvName.setText(arrayListGroup.get(position));
        holder.linearLayoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = holder.getLayoutPosition();
                System.out.println(layoutPosition+" layout position");

                location =arrayListGroup.get(layoutPosition);
                System.out.println(location+" this is table number");

                String status= getStatus(location);


                System.out.println("value fetched "+ status);

                if(status!=null){
                    if(status.equals("not started")){
                        System.out.println("in if blockkkkkkkkkkkkkkkkkkkkk");

                        sharedPreferences = activity.getSharedPreferences(Table_Name, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(Table_Key);
                        editor.apply();

                        holder.linearLayoutCard.setBackgroundColor(Color.GREEN);
                        FirebaseDatabase setStatus = FirebaseDatabase.getInstance();
                        DatabaseReference refSetStatus = setStatus.getReference("ChefOrderStatus/"+location);
                        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
                        refSetStatus.setValue("cooking");


                    }
                    else {

                        System.out.println("in else blockkkkkkkkkkkkkkkkkkkkk");
                        sharedPreferences = activity.getSharedPreferences(Table_Name, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(Table_Key);
                        editor.apply();
                        FirebaseDatabase setStatus = FirebaseDatabase.getInstance();
                        DatabaseReference refSetStatus = setStatus.getReference("ChefOrderStatus/"+location);
                        refSetStatus.removeValue();
                        DatabaseReference removeCooked=setStatus.getReference("OrderToPrepare/"+location+"/");
                        removeCooked.removeValue();


                    }
                }

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
/*        MemberAdp adapterMember = new MemberAdp(arrayListMember);

        //Initailize layoutManager
        LinearLayoutManager layoutManagerMember = new LinearLayoutManager(activity);
        //set layout manager
        holder.rvMember.setLayoutManager(layoutManagerMember);
        //set adapter
        holder.rvMember.setAdapter(adapterMember);*/

    }


    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }

    protected String getStatus(final String location){



        System.out.println("inside getstatus method "+ location+ " talbe no");
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference statusNode= firebaseDatabase.getReference("ChefOrderStatus/"+location);

        statusNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // System.out.println(status+" in getsnapshot");
                if(snapshot.exists()) {
                    String value = snapshot.getValue().toString();
                    System.out.println(value+" value in ondataChange");

                    sharedPreferences = activity.getSharedPreferences(Table_Name, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Table_Key, value);
                    editor.apply();
                }
                System.out.println("ondatachang method called for"+ location);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       /* statusNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // System.out.println(status+" in getsnapshot");
                if(snapshot.exists()) {
                    String value = snapshot.getValue().toString();
                    System.out.println(value+" value in ondataChange");

                    sharedPreferences = activity.getSharedPreferences(Table_Name, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Table_Key, value);
                    editor.apply();
                }
                System.out.println("ondatachang method called for"+ location);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        sharedPreferences = activity.getSharedPreferences(Table_Name, Context.MODE_PRIVATE);
        String string= sharedPreferences.getString(Table_Key, null);
        System.out.println(string + " status....");
        if(string==null){
            string="not started";
        }
        return string;
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
