package com.example.gudduroy.mymall;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {


    public MyOrdersFragment() {
        // Required empty public constructor
    }
    private RecyclerView myOrdersRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_orders, container, false);

        myOrdersRecyclerView=view.findViewById(R.id.my_orders_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(layoutManager);


        List<MyOrderItemModel> myOrderItemModelsList = new ArrayList<>();
        myOrderItemModelsList.add(new MyOrderItemModel(R.drawable.banner,2,"Pixel 2Xl (Black)","Delivered on Mon,15th jan 2013"));
        myOrderItemModelsList.add(new MyOrderItemModel(R.drawable.image,1,"Pixel 2Xl (Black)","Delivered on Mon,15th jan 2013"));
        myOrderItemModelsList.add(new MyOrderItemModel(R.drawable.image3,0,"Pixel 2Xl (Black)","Cancelled"));
        myOrderItemModelsList.add(new MyOrderItemModel(R.drawable.image4,4,"Pixel 2Xl (Black)","Delivered on Mon,15th jan 2013"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelsList);
        myOrdersRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }

}
