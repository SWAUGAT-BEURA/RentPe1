package com.sanjeevani.rent.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sanjeevani.rent.R;

public class HouseDesc extends AppCompatActivity {

    ImageView btnBack;
    TextView title,price,location,desc;
    Button buttonapply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_desc);

        btnBack = findViewById(R.id.webback1);
        title = findViewById(R.id.tv_jobtitledesc);
        price = findViewById(R.id.tv_companyName);
        location = findViewById(R.id.tv_location);
        desc = findViewById(R.id.jobdesc);
        buttonapply = findViewById(R.id.btn_applyjob);


        Intent intent = getIntent();
        title.setText(intent.getStringExtra("name"));
        price.setText(intent.getStringExtra("price"));
        location.setText(intent.getStringExtra("address"));
        desc.setText(intent.getStringExtra("desc"));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Your application has gone to verification" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}