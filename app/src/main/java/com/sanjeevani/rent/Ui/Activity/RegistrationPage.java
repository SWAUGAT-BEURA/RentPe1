
package com.sanjeevani.rent.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import com.sanjeevani.rent.Models.UserRequest;
import com.sanjeevani.rent.Models.UserResponse;
import com.sanjeevani.rent.R;
import com.sanjeevani.rent.utils.NetworkChangeListener;
import com.sanjeevani.rent.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPage extends AppCompatActivity {
    private TextView signinpg;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmpassword;
    private String str;
    private LinearLayout progressregister;
    private String token;
    private int id;

    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        signinpg=findViewById(R.id.signinpage);
        signinpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotologin();

            }
        });

        username=findViewById(R.id.Username);
        email=findViewById(R.id.EmailAddress);
        password=findViewById(R.id.Password);
        confirmpassword=findViewById(R.id.ConfirmPassword);
        progressregister=findViewById(R.id.progressregister);
    }

    private void gotologin() {
        startActivity(new Intent(RegistrationPage.this, LoginAcitivty.class));
        finish();
    }
    private boolean validateUsername() {
        String val = username.getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getText().toString().trim();
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
            password.setError("Field can not be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
    private boolean confirmpassword(){
        String val = password.getText().toString().trim();
        String val2= confirmpassword.getText().toString().trim();

        Boolean b=val.equals(val2);
        if (b) {
            return true;
        }else{
            Toast.makeText(RegistrationPage.this,"password didn't match with confirm password",Toast.LENGTH_SHORT).show();
            return false;

        }

    }

    public void movetologin(View view) {
        Intent intent=new Intent(RegistrationPage.this, LoginAcitivty.class);
        startActivity(intent);
        finish();
    }

    public void registerUser1(View view) {
        if ( validateUsername() && validateEmail() && validatePassword() && confirmpassword()){
            UserRequest userRequest1=createUserRequest();
            registerUser(userRequest1);
            progressregister.setVisibility(View.VISIBLE);
        }
    }


    private UserRequest createUserRequest(){
        UserRequest userRequest=new UserRequest();
        userRequest.setUsername(username.getText().toString().trim());
        userRequest.setEmail(email.getText().toString());
        userRequest.setPassword(password.getText().toString().trim());
        return userRequest;
    }

    private void gotohome() {
        SharedPreferences sharedPreferences=getSharedPreferences(LoginAcitivty.PREFS_NAME,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        startActivity(new Intent(RegistrationPage.this, MainActivity.class));
        finish();
    }
    private void registerUser(UserRequest userRequest){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> userResponseCall=apiInterface.registerUser(userRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                response.body();
                if ((response.isSuccessful())){
                    token=response.body().getToken();
                    id=response.body().getUser().getId();
                    if(token!=null){
//                        Button btn = (Button)findViewById(R.id.register_button);
//                        btn.setEnabled(false);
                        SessionManager sessionManager=new SessionManager(RegistrationPage.this);
                        sessionManager.createloginsession(username.getText().toString(),id,token);
                        progressregister.setVisibility(View.GONE);
                        gotologin();
                        Toast.makeText(RegistrationPage.this,"SuccessFul",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegistrationPage.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistrationPage.this,"Error Occured,Please Try Again",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegistrationPage.this,"Error Occured While Registering,Please Try Again",Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegistrationPage.this, LoginAcitivty.class));
        finish();
    }
}