package com.example.sdl.manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdl.R;
import com.example.sdl.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class UserDisplayAdapter extends FirebaseRecyclerAdapter<
        User,UserDisplayAdapter.UserViewholder> {

    public UserDisplayAdapter(
            @NonNull FirebaseRecyclerOptions<User> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "user_card.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull UserViewholder holder,
                     int position, @NonNull User model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "user_card.xml")
        holder.name.setText(model.getName());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "user_card.xml")
        holder.phoneNumber.setText(model.getPhoneNumber());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "user_card.xml")
        holder.email.setText(model.getEmail());
        holder.type.setText(model.getType());
        holder.password.setText(model.getPassword());



    }

    // Function to tell the class about the Card view (here
    // "user_card.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public UserViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_card, parent, false);
        return new UserDisplayAdapter.UserViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "user_card.xml")


    public class UserViewholder extends RecyclerView.ViewHolder {
        TextView name, phoneNumber, email,type,password;

        public UserViewholder(@NonNull View itemView) {
            super(itemView);

            name   = itemView.findViewById(R.id.display_name);
            phoneNumber = itemView.findViewById(R.id.display_phone_no);
            email = itemView.findViewById(R.id.display_email);
            type = itemView.findViewById(R.id.display_type);
            password = itemView.findViewById(R.id.display_password);
        }
    }
}