package com.sanjeevani.rent.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanjeevani.rent.Adapters.SliderAdapter;
import com.sanjeevani.rent.R;

public class onboardingscreen extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button btn, getstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboardingscreen);

        viewPager = findViewById(R.id.viewPager2);
        dotsLayout = findViewById(R.id.dotslayout);
        btn = findViewById(R.id.nextbtn);
        getstart = findViewById(R.id.getstartbtn);

        //call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    private void addDots(int position) {
        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) dots[position].setTextColor(getResources().getColor(R.color.black));
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);

            if (position == 0) {
                getstart.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                getstart.setVisibility(View.INVISIBLE);
            } else {
                getstart.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void nextFunction(View view) {

    }

    public void getStartedFun(View view) {
        Intent i = new Intent(onboardingscreen.this, LoginAcitivty.class);
        startActivity(i);
        finish();
    }
}