package com.example.gudduroy.mymall;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private  HomePageAdapter adapter;
    private List<CategoryModel> categoryModelsList;
    private FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        categoryModelsList = new ArrayList<CategoryModel>();

        categoryAdapter = new CategoryAdapter(categoryModelsList);
        categoryRecyclerView.setAdapter(categoryAdapter);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    categoryModelsList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }

                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();

                            Toast.makeText(getContext(), "Here is ERROR  "+error, Toast.LENGTH_LONG).show();
                        }

                    }
                });

        //////////// horizontal product layout

    //    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
     //   horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.add_profile_photo,"Redmi 5A","SD 625processor","Rs.5999/-"));
    //    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.bell,"Redmi 5A","SD 625processor","Rs.5999/-"));
    //    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.app_round_icon,"Redmi 5A","SD 625processor","Rs.5999/-"));
     //   horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.cart_white,"Redmi 5A","SD 625processor","Rs.5999/-"));
    //    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.custom_error_icon,"Redmi 5A","SD 625processor","Rs.5999/-"));
    //    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.green_email,"Redmi 5A","SD 625processor","Rs.5999/-"));
    //    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.red_email,"Redmi 5A","SD 625processor","Rs.5999/-"));
    //    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.app_icon,"Redmi 5A","SD 625processor","Rs.5999/-"));
    //    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.red_email,"Redmi 5A","SD 625processor","Rs.5999/-"));
        ////////////horizontal product layout


        ///////////////////////////////////////
        homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);
        final List<HomePageModel> homePageModelsList = new ArrayList<>();
        adapter = new HomePageAdapter(homePageModelsList);
        homePageRecyclerView.setAdapter(adapter);

        firebaseFirestore.collection("CATEGORIES")
                .document("HOME")
                .collection("TOP_DEALS")
                .orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if((long)documentSnapshot.get("view_type")== 0){
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long)documentSnapshot.get("no_of_banner");
                                    for(long  x = 1; x < no_of_banners + 1;x++)
                                    {
                                        sliderModelList.add(new SliderModel(String.valueOf(documentSnapshot.get("banner_"+x))
                                                ,String.valueOf(documentSnapshot.get("banner_"+x+"_background"))));
                                    }
                                    homePageModelsList.add(new HomePageModel(0,sliderModelList));
                                }
                                else if((long)documentSnapshot.get("view_type") == 1)
                                {
                                   homePageModelsList.add(new HomePageModel(1,String.valueOf(documentSnapshot.get("strip_ad_banner")),
                                           String.valueOf(documentSnapshot.get("background"))));
                                }
                                else if((long)documentSnapshot.get("view_type") == 2)
                                {
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long)documentSnapshot.get("no_of_products");
                                    for(long x=1;x<no_of_products+1;x++){
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString()
                                        ,documentSnapshot.get("product_image_"+x).toString()
                                       ,documentSnapshot.get("product_title_"+x).toString()
                                        ,documentSnapshot.get("product_subtitle_"+x).toString()
                                        ,documentSnapshot.get("product_price_"+x).toString()));
                                    }
                                    homePageModelsList.add(new HomePageModel(2,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),horizontalProductScrollModelList));
                                } else if((long)documentSnapshot.get("view_type") == 3)
                                {

                                }
                            }

                            adapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();

                            Toast.makeText(getContext(), "Here is ERROR  "+error, Toast.LENGTH_LONG).show();
                        }

                    }
                });

        return view;

    }


}
