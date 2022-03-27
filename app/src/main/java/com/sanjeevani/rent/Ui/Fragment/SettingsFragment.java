package com.sanjeevani.rent.Ui.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sanjeevani.rent.R;
import com.sanjeevani.rent.Ui.Activity.LoginAcitivty;
import com.sanjeevani.rent.utils.SessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    private int i,j;
    private LinearLayout privacypolicy,termsConditions;
    private RecyclerView recyclerView;
    private TextView changepasswordtv,usernameSettings,feedback,addAddress;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout logoutsection,ll_rcorder,changepasswordsection,addresssection,feedbacksection,addresslistsection,arrowaddressright,arrowaddressdown;

    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        changepasswordtv=view.findViewById(R.id.tv_changepassword_settings);
        feedback=view.findViewById(R.id.tv_feedback);
        addAddress=view.findViewById(R.id.tv_Addresses);
        usernameSettings=view.findViewById(R.id.tv_username_settings);
        logoutsection=view.findViewById(R.id.logout_section);
        changepasswordsection=view.findViewById(R.id.password_section);
        addresssection=view.findViewById(R.id.myaddresses);
        feedbacksection=view.findViewById(R.id.feedback_section);
        addresslistsection=view.findViewById(R.id.addresseslistSection);

        ll_rcorder=view.findViewById(R.id.ll_rcorder);


        termsConditions=view.findViewById(R.id.TermsAndConditions);
        privacypolicy=view.findViewById(R.id.PrivacyPolicy);




        logoutsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext()).setTitle("Logout")
                        .setMessage("Are you sure you want to Logout")
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SessionManager sessionManager=new SessionManager(getContext());
                                sessionManager.logoutuserfromsession();
                                startActivity(new Intent(getContext(), LoginAcitivty.class));
                                getActivity().finish();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

            }
        });



        SessionManager sessionManager=new SessionManager(getContext());
        String username=sessionManager.getUsername();
        usernameSettings.setText(username);

        return view;
    }


}