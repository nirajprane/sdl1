package com.example.sdl;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sdl.OrderSummary.ParcelActivity;
import com.example.sdl.menu.MenuActivityParcel;

import static com.example.sdl.Flags.fromParcelActivity;

public class ParcelFragment extends Fragment {
    GridView gridView;
    TextView textView;
    String[] parcelNumbers = {
            "P1", "P2", "P3", "P4", "P5", "P6", "P7"};
    int parcelNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String parcel =getActivity().getIntent().getStringExtra("parcelNoFromParcelSummary");//t1
        System.out.println(parcel+" vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");

        if(parcel!=null){
            parcelNo= Integer.parseInt(String.valueOf(parcel.charAt(1)));

            fromParcelActivity[parcelNo-1]=true;
        }


        View rootView = inflater.inflate(R.layout.table_list, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        textView = (TextView)rootView.findViewById(R.id.textView);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),R.layout.table,R.id.textView,parcelNumbers);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println(fromParcelActivity[position]+" ggggggggggggggggggggggggg");
                if(fromParcelActivity[position]) {
                    Intent orderIntent = new Intent(getContext(), ParcelActivity.class);
                    orderIntent.putExtra("parcelNoFromActivityForParcel", parcelNumbers[position]);
                    startActivity(orderIntent);
                    getActivity().finish();

                }
                else{
                    Intent menuIntent = new Intent(getContext(), MenuActivityParcel.class);
                    menuIntent.putExtra("parcelNo", parcelNumbers[position]);
                    startActivity(menuIntent);
                    getActivity().finish();


                }

            }
        });
        return rootView;}
}