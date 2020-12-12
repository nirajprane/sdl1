package com.example.sdl.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sdl.ActivityForTable;
import com.example.sdl.OrderSummary.OrderActivity;
import com.example.sdl.R;
import com.google.android.material.tabs.TabLayout;

public class ManagerActivityForTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_table);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        Button logout = findViewById(R.id.waiter_logout);
        logout.setVisibility(View.GONE);

        // Create an adapter that knows which fragment should be shown on each page
        ManagerSimpleFragmentPageAdapterTable adapter = new ManagerSimpleFragmentPageAdapterTable(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        finish();

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ManagerActivityForTable.this, ManagerMainActivity.class));
        finish();

    }
}