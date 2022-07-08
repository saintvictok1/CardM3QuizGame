package com.example.Kernopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

     EditText etFirstname, etLastname, etBirthDate, etEmail, etUserName, etPassword;
    Button btnRegisterUser;
    final Calendar myCalendar = Calendar.getInstance();
    DbHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        DB = new DbHelper(this);


        //register fields and buttons with their IDs
        etFirstname = findViewById(R.id.etFirstName);
        etLastname = findViewById(R.id.etLastName);
        etBirthDate = findViewById(R.id.etBirthDate);
        etEmail = findViewById(R.id.etEmail);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnRegisterUser = findViewById(R.id.btnRegisterUser);

        //Date picker for birthdate
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                etBirthDate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        //Onclick listener for Birthdate field, pops Date Picker Dialog
        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Onclick listener for Register button Action
        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Store data in SQLite DB if validation passes
                if(validateFields()){

                    String newUsername = etUserName.getText().toString();
                    String newPassword = etPassword.getText().toString();
                    String newFirstName = etFirstname.getText().toString();
                    String newLastName = etLastname.getText().toString();
                    String newEmail = etEmail.getText().toString();
                    String newBirthDate = etBirthDate.getText().toString();

                    Boolean userexists =  DB.checkusername(newUsername);

                    //check if users already exists in db.
                    if(userexists){
                        //will not allow same user to be registered again
                        Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        etUserName.setError("Choose another User Name");
                    }else{
                        //if unique user, add user to db
                        Boolean insert = DB.insertData(newUsername, newPassword, newFirstName, newLastName, newEmail, newBirthDate);

                        //on successful insert query, show toast, return to login
                        if(insert){
                            Toast.makeText(RegisterActivity.this, "New User Added", Toast.LENGTH_LONG).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Go to Login activity
                                    Intent returnToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(returnToLogin);
                                }
                            }, 3*1000);

                            //show Toast if query failed
                        }else{
                            Toast.makeText(RegisterActivity.this, "Insert Failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            }
        }); //End Registerbutton onclicklistener

    }//End OnCreate

//Validation for all form fields. No validation for birthdate since calendar is used.
    private boolean validateFields(){
        //validate First Name
        if(etFirstname.length() == 0){
            etFirstname.setError("First Name is required");
            return false;
        }else if (etFirstname.length() < 3){
            etFirstname.setError("First Name must be at least 3 characters");
            return false;
        } if (etFirstname.length() > 30) {
            etFirstname.setError("First Name cannot be longer than 30 characters");
            return false;
        }
        //validate Last name
        if(etLastname.length() == 0) {
            etLastname.setError("Last Name is required");
            return false;
        } if(etLastname.length() > 30){
            etLastname.setError("Last Name cannot be longer than 30 characters");
            return false;
        }

        //validate email
        if(etEmail.length() == 0){
            etEmail.setError("Email is required");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            etEmail.setError("Not a valid email address");
            return false;
        }if(etUserName.length() == 0){
            etUserName.setError("User Name is required");
            return false;

        }if(etPassword.length() == 0) {
            etPassword.setError("Password is required");
            return false;
        }
        return true;
        }

}