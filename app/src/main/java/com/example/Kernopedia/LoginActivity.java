package com.example.Kernopedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etUserName, etPassword;
    Button btnLogin, btnRegister;
    DbHelper DB;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //variables
        etUserName = findViewById(R.id.etUserName);
        etPassword  = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        DB = new DbHelper(this);


        //Onclick listener for Register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startRegistration = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(startRegistration);
            }

        });

        //Onclick listener for Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etUserName.getText().toString();
                String pass = etPassword.getText().toString();

                login(user,pass);
            }
        });


    }//end of OnCreate

    public void login(String user, String pass){

        //store boolean to see if users exists in database
        boolean checkcredentials = DB.checkusernamepassword(user, pass);

        //display toast if entered user is not in database
        if(validateLoginFields() && !checkcredentials) {

            Toast.makeText(LoginActivity.this, "Please check Username and password", Toast.LENGTH_SHORT).show();

            //if user exists and credentials are good, move to the game home screen
        }else if(validateLoginFields() && checkcredentials) {


            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent GoToGame = new Intent(LoginActivity.this, GameMainActivity.class);

            //pass logged in users  First name  to GameMainActivity
            Cursor loggeduserFirstName = DB.getFirstName(user, pass);
            if (loggeduserFirstName.moveToFirst()) {
                @SuppressLint("Range") String usersfirstname = loggeduserFirstName.getString(loggeduserFirstName.getColumnIndex("firstname"));
                //GoToGame.putExtra("logged_user", user);
                GoToGame.putExtra("logged_user", usersfirstname);
                startActivity(GoToGame);
            }
        }
        }

        // validation to see if fields are empty, false if any field empty, true if both fields filled.
        public boolean validateLoginFields(){

        String usercheck = etUserName.getText().toString().trim();
        String passcheck = etPassword.getText().toString().trim();

        if(usercheck.equals("")){
            etUserName.setError("Username is required");
            Toast.makeText(LoginActivity.this, "Enter User Name" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if( passcheck.equals("")){
            etPassword.setError("Password is required");
            Toast.makeText(LoginActivity.this, "Enter Password" , Toast.LENGTH_SHORT).show();

            return false;
        }
        else{
            return true;
        }
    }
}