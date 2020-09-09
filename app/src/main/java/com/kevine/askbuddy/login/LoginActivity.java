package com.kevine.askbuddy.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kevine.askbuddy.BaseActivity;
import com.kevine.askbuddy.Constants;
import com.kevine.askbuddy.Log;
import com.kevine.askbuddy.MainActivity;
import com.kevine.askbuddy.R;
import com.kevine.askbuddy.RegisterActivity;
import com.kevine.askbuddy.Sessions;
import com.kevine.askbuddy.StringUtilities;
import com.kevine.askbuddy.login.contract.LoginActivityContract;
import com.kevine.askbuddy.login.presenter.LoginActivityModel;
import com.kevine.askbuddy.login.presenter.LoginActivityPresenter;
import com.kevine.askbuddy.network.ApiClientString;
import com.kevine.askbuddy.network.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private TextView registerUser;

    private FirebaseAuth mAuth;
    private LoginActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        registerUser = findViewById(R.id.register_user);
        session = new Sessions(this);
        //presenter = new LoginActivityPresenter(this, new LoginActivityModel());

        //mAuth = FirebaseAuth.getInstance();

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        email.setText("shalynewaweru@gmail.com");
        password.setText("123456");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtilities.checkFilledEditText(email,"Email Address is required")&&
                        StringUtilities.checkFilledEditText(password,"Password is required")){
                    invokeLogin(email.getText().toString(),password.getText().toString());
                }
            }
        });

        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txt_email , txt_password);
                }
            }
        });*/
    }

    /*private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Update the profile " +
                            "for better experience", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }*/

    //method to make api call
    public void invokeLogin(String email, String password) {
        showProgress(); //defined in the BaseActivity

        ApiInterface apiService = ApiClientString.getClient().create(ApiInterface.class);
        //making api call
        Call<String> call = apiService.loginApp(email, password);

        //handling the response from api
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //login response
                Log.debug("loginResp: ",response.body());

                //try catch for the response body
                try {
                    assert response.body() != null;
                    JSONObject obj = new JSONObject(response.body());
                    dismissProgress();
                    String respcode = obj.optString(Constants.KEY_RESPCODE);
                    String respdesc = obj.optString(Constants.KEY_RESPDESC);


                    if (respcode.equals("01")){
                        JSONObject Ob = obj.optJSONObject("details");
                        session.setUserId(Ob.optString("u_id"));
                        Toast.makeText(LoginActivity.this, "Update the profile " +
                                "for better experience", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }else {
                        showSnackBar(respdesc);
                    }
                }catch (JSONException e){

                }

            }

            //if an error occurs in network transaction
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dismissProgress();
                Log.debug("loginResp: ",t.getMessage());
            }
        });
    }
}
