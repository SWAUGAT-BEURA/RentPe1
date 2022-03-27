package com.sanjeevani.rent.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sanjeevani.rent.Api.ApiClient;
import com.sanjeevani.rent.Api.ApiInterface;
import com.sanjeevani.rent.Models.LoginRequest;
import com.sanjeevani.rent.Models.LoginToken;
import com.sanjeevani.rent.Models.UserInfo;
import com.sanjeevani.rent.R;
import com.sanjeevani.rent.utils.NetworkChangeListener;
import com.sanjeevani.rent.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAcitivty extends AppCompatActivity {
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();
    private Button signinbtn;
    private TextView signup;
    public static final String PREFS_NAME = "PrefsFile";
    private ArrayList<UserInfo> userInfoArrayList;
    private String token;
    private int id,i;
    private LinearLayout progresslogin;
    private EditText passwordlogin,namelogin;
    private String rudra,rudrausername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivty);

        namelogin=findViewById(R.id.EmailAddress);
        passwordlogin=findViewById(R.id.Password);
        progresslogin=findViewById(R.id.progresslogin);

        SessionManager sessionManager=new SessionManager(LoginAcitivty.this);
        boolean isloggedin=sessionManager.checkLogin();
        if(isloggedin){
            gotohome();
        }
        signinbtn=findViewById(R.id.SignInBtn);
        signup=findViewById(R.id.newAccount);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginclicked();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singuppage();
            }
        });

    }

    private void singuppage() {
        startActivity(new Intent(LoginAcitivty.this, RegistrationPage.class));
        finish();
    }

    private void gotohome() {
        startActivity(new Intent(LoginAcitivty.this, MainActivity.class));
        finish();
    }




    public void movetosignup(View view) {
        Intent intent=new Intent(LoginAcitivty.this, RegistrationPage.class);
        startActivity(intent);
    }

    private boolean validateUsername() {
        String val = namelogin.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            namelogin.setError("Field can not be empty");
            return false;
        }else {
            namelogin.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = passwordlogin.getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            passwordlogin.setError("Field can not be empty");
            return false;
        } else {
            passwordlogin.setError(null);
            return true;
        }
    }

    public void loginclicked() {
        if (validateUsername() && validatePassword()){
            LoginRequest loginRequest=creatLoginRequest();
            loginUsertohome(loginRequest);
            progresslogin.setVisibility(View.VISIBLE);
        }

    }

    private LoginRequest creatLoginRequest(){
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setUsername(namelogin.getText().toString());
        loginRequest.setPassword(passwordlogin.getText().toString());
        return loginRequest;
    }

    private void loginUsertohome(LoginRequest loginRequest){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginToken> loginTokenCall=apiInterface.loginUser(loginRequest.getUsername(),loginRequest.getPassword());
        loginTokenCall.enqueue(new Callback<LoginToken>() {
            @Override
            public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {

                if ((response.isSuccessful())){
                    token=response.body().getToken();
                    if(token!=null){
                        getuseridaftertoken();
                        gotohome();

                    }else{
                        Toast.makeText(LoginAcitivty.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginAcitivty.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginToken> call, Throwable t) {
                Toast.makeText(LoginAcitivty.this,"Login error occured,try again",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getuseridaftertoken() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<UserInfo>> userinfo = apiInterface.getuserdetails();
        userinfo.enqueue(new Callback<ArrayList<UserInfo>>() {
            @Override
            public void onResponse(Call<ArrayList<UserInfo>> call, Response<ArrayList<UserInfo>> response) {
                if (response.isSuccessful()){
                    userInfoArrayList=response.body();
                    for (i=0;i<userInfoArrayList.size();i++){
                        rudra=namelogin.getText().toString().trim();
                        rudrausername=userInfoArrayList.get(i).getUsername();
                        Boolean b=rudra.equals(rudrausername);

                        if (b!=false){
                            id=userInfoArrayList.get(i).getId();
                            SessionManager sessionManager=new SessionManager(LoginAcitivty.this);
                            sessionManager.createloginsession(namelogin.getText().toString(),id,token);
                            progresslogin.setVisibility(View.GONE);
                            gotohome();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserInfo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        new AlertDialog.Builder(LoginAcitivty.this)
                .setTitle("Exit")
                .setMessage("Do you really want to exit?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(LoginAcitivty.this);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }



    @Override
    protected void onStart() {
        IntentFilter filter1=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter1);
        super.onStart();
    }


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }


}