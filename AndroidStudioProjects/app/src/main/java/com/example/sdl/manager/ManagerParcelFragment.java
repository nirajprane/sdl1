package com.example.sdl.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sdl.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ManagerParcelFragment extends Fragment {
    GridView gridView;
    TextView textView;
    String[] parcelNumbers = {
            "P1", "P2", "P3", "P4", "P5", "P6", "P7"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       //String table =getActivity().getIntent().getStringExtra("tableNoFromOrderSummary");//t1


        View rootView = inflater.inflate(R.layout.table_list, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        textView = (TextView)rootView.findViewById(R.id.textView);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),R.layout.table,R.id.textView,parcelNumbers);
        final GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(adapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                ///Toast.makeText(getActivity(),"Text! "+position,Toast.LENGTH_SHORT).show();
                //System.out.println(fromOrderActivity[position]);
               // System.out.println(tablePosition[position]);
                FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
                DatabaseReference tableNode = databaseInstance.getReference("Parcel/"+ parcelNumbers[position]);
                tableNode.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            // The child exist
                           Intent orderIntent = new Intent(getContext(), ManagerParcelActivity.class);
                           orderIntent.putExtra("parcelNo", parcelNumbers[position]);
                           startActivity(orderIntent);
                       }
                       else{
                           Toast toast = Toast.makeText(getContext(), "No Order Placed", Toast.LENGTH_SHORT);
                           toast.show();
                       }

                        // ArrayList<Order> orderListFromDatabase = new ArrayList<>();
                       /* for (DataSnapshot ds : snapshot.getKey()) {
                            String key = ds.getKey().toString()
                            orderListManager.add(order);
                        }
                        if(orderListManager.isEmpty()){

                        }
                        displayList();*/
                /*managerParcelSummaryListAdapter = new ManagerParcelSummaryListAdapter(this, orderListManager);
                list.setAdapter(managerParcelSummaryListAdapter);*/
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


               /* Intent orderIntent = new Intent(getContext(), ManagerParcelActivity.class);
                    orderIntent.putExtra("tableNo", tableNumbers[position]);
                    startActivity(orderIntent);*/



            }
        });
    return rootView;
    }
}
