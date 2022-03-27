package com.sanjeevani.rent.Ui.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sanjeevani.rent.Adapters.HouseAdapter;
import com.sanjeevani.rent.Api.ApiClient;
import com.sanjeevani.rent.Api.ApiInterface;
import com.sanjeevani.rent.Models.House;
import com.sanjeevani.rent.R;
import com.sanjeevani.rent.Ui.Activity.HouseDesc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HouseAdapter.ItemClickListener{
    private RecyclerView rchouse,rccomm,rcpg;
    private RecyclerView foodRecyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        rchouse = view.findViewById(R.id.rv_rentAHome);
        rcpg = view.findViewById(R.id.rv_pgco);
        rccomm = view.findViewById(R.id.rv_office);
        rchouse.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false));
        rcpg.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false));
        rccomm.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false));

        

        loadhouses();

        return view;
    }

    private void loadhouses() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Getting your Homes");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<ArrayList<House>> getFoodItems = apiInterface.getHouses();

        getFoodItems.enqueue(new Callback<ArrayList<House>>() {
            @Override
            public void onResponse(Call<ArrayList<House>> call, Response<ArrayList<House>> response) {

                if (response.isSuccessful()) {
                    progressDialog.hide();
                    ArrayList<House> value = response.body();
                    if (value.size()<1){
                        Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();

                    }else{
                        HouseAdapter foodAdapter = new HouseAdapter(getContext(),value,HomeFragment.this);
                        rchouse.setAdapter(foodAdapter);
                        rcpg.setAdapter(foodAdapter);
                        rccomm.setAdapter(foodAdapter);
                    }

                }
                else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<House>> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                if (t instanceof IOException) {

                }
                else {
                    Log.e("Logs",t.toString());
                }
            }
        });
    }

    @Override
    public void onItemClick(House h) {
        Intent intent = new Intent(getActivity(), HouseDesc.class);
        intent.putExtra("address",h.getHome_address());
        intent.putExtra("desc",h.getHome_description());
        intent.putExtra("price",String.valueOf(h.getHome_selling_price()));
        intent.putExtra("name",String.valueOf(h.getHome_name()));
        intent.putExtra("id",h.getId());
        startActivity(intent);

    }
}