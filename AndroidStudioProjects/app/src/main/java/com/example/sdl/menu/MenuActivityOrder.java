package com.example.sdl.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sdl.ActivityForTable;
import com.example.sdl.OrderSummary.OrderActivity;
import com.example.sdl.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.sdl.Flags.cOrderFlag;


public class MenuActivityOrder extends AppCompatActivity {
    private RecyclerView recycler_view;
    private Button confirm;
    private Button reset;
    int tablePos;
    String tableNo;
    String tableNoFirst;
    public ArrayList<Menu> menuList = new ArrayList<>( );
    DocExpandableRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        tableNo =  intent.getStringExtra("tableNo");
        tableNoFirst= intent.getStringExtra("addbutton");
        if(tableNoFirst!=null){
            Bundle bundle = intent.getExtras();
            menuList = bundle.getParcelableArrayList("before");

        }

        System.out.println(tableNo+" table no");
        if(tableNo!=null) {
             tablePos = Integer.parseInt(String.valueOf(tableNo.charAt(1)));
        }

        //Define buttons
        confirm= (Button) findViewById(R.id.confirm_button);
        reset= (Button) findViewById(R.id.reset_button);
        //Define recycleview
        recycler_view = (RecyclerView) findViewById(R.id.recycler_Expand);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        //Initialize your Firebase app
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Reference to your entire Firebase database
        DatabaseReference parentReference;
        parentReference = database.getReference("menu");

        parentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<ParentList> Parent = new ArrayList<>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){


                    final String ParentKey = snapshot.getKey().toString();

                    snapshot.child("titre").getValue();

                    DatabaseReference childReference =
                            FirebaseDatabase.getInstance().getReference().child("menu/"+ParentKey);
                    childReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final List<ChildList> Child = new ArrayList<>();


                            for (DataSnapshot snapshot1:dataSnapshot.getChildren())
                            {
                                final String ChildName =  snapshot1.getKey().toString();
                                final int ChildValue =( (Long) snapshot1.getValue()).intValue();


                                snapshot1.child("titre").getValue();

                                Child.add(new ChildList(ChildName,ChildValue));

                            }

                            Parent.add(new ParentList(ParentKey, Child));

                            adapter = new DocExpandableRecyclerAdapter(Parent,menuList);
                            recycler_view.setItemViewCacheSize(30);
                            recycler_view.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            System.out.println("Failed to read value." + error.toException());
                        }

                    });}}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!cOrderFlag[tablePos-1]) {
                    if (menuList.size() != 0) {
                        System.out.println("from menu first time");
                        Intent orderIntent = new Intent(MenuActivityOrder.this, OrderActivity.class);
                        Bundle args = new Bundle();
                        args.putParcelableArrayList("ARRAYLIST",menuList);
                        orderIntent.putExtra("BUNDLE",args);
                        orderIntent.putExtra("tableNoFromMenu",tableNo);
                        //cOrderFlag[tablePos-1] = true;
                        startActivity(orderIntent);
                        finish();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "No item selected", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else{
                    //System.out.println("aaaaaaaaaaaaaaaa");
                 //   cFlag[tablePos-1]=false;
                    Intent returnIntent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("AddedMenu",menuList);
                    returnIntent.putExtras(bundle);
                    returnIntent.putExtra("tableNoFromMenu",tableNo);
                    setResult(2,returnIntent);
                    finish();
                }
            }

        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuList.clear();
                adapter.uncheckAll();
            }
        });

    }




    /*public class DocExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<MyParentViewHolder,MyChildViewHolder> {


        public DocExpandableRecyclerAdapter(List<ParentList> groups) {
            super(groups);
        }
        @Override
        public MyParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_item, parent, false);
            return new MyParentViewHolder(view);
        }

        @Override
        public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
            return new MyChildViewHolder(view);
        }

        @Override
        public void onBindChildViewHolder(final MyChildViewHolder holder, final int flatPosition, ExpandableGroup group, int childIndex) {
            final ChildList childItem = ((ParentList) group).getItems().get(childIndex);

            holder.onBind(childItem.getTitle());

            final String TitleChild=childItem.getTitle();
            final int TitlePrice=childItem.getPrice();

            if (uncheckAll) {
                holder.checkBox.setChecked(false);
                uncheckAll=false;
            }

            holder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    menuList.add(new Menu(TitleChild,TitlePrice));
                    holder.checkBox.setChecked(true);
                    Toast toast = Toast.makeText(getApplicationContext(), menuList.size()+" item selected", Toast.LENGTH_SHORT);
                    toast.show();

                }


            });



        }

        @Override
        public void onBindGroupViewHolder(MyParentViewHolder holder, int flatPosition, final ExpandableGroup group) {
            holder.setParentTitle(group);

            if(group.getItems()==null)
            {
                holder.listGroup.setOnClickListener(  new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getApplicationContext(), group.toString(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

            }
        }

        public void uncheckAll(){
            uncheckAll=true;
            notifyDataSetChanged();

        }


    }*/

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(MenuActivityOrder.this, ActivityForTable.class));
        finish();

    }




}