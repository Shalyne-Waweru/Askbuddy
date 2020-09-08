package com.kevine.askbuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kevine.askbuddy.login.LoginActivity;
import com.kevine.askbuddy.network.ApiClientString;
import com.kevine.askbuddy.network.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private EditText username;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button register;
    private TextView loginUser;

         private DatabaseReference mRootRef;
         private FirebaseAuth mAuth;

         ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        loginUser = findViewById(R.id.login_user);

            mRootRef = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            pd = new ProgressDialog(this);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtilities.checkFilledEditText(username,"Username is required")&&
                        StringUtilities.checkFilledEditText(name,"Name is required")&&
                StringUtilities.checkFilledEditText(email,"Email Address is required")&&
                        StringUtilities.checkFilledEditText(password,"Password is required")){
                    invokeRegister(email.getText().toString(),password.getText().toString(),name.getText().toString(),username.getText().toString());
                }
            }
        });

        /*register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = username.getText().toString();
                String txtName = name.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();

                if (TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtName)
                    || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txtUsername , txtName , txtEmail , txtPassword);
                }
            }
        });*/
    }

    private void invokeRegister(String email, String password, String name, String username) {
        showProgress();
        ApiInterface apiService = ApiClientString.getClient().create(ApiInterface.class);
        //making api call

        Call<String> call = apiService.registerApp("",email,password, "",name,username);

        //handling the response from the api
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dismissProgress();
                //register response
                Log.debug("registerResp: ",response.body());

                //try catch for the response body
                try {
                    assert response.body() != null;
                    JSONObject obj = new JSONObject(response.body());

                    String respcode = obj.optString(Constants.KEY_RESPCODE);
                    String respdesc = obj.optString(Constants.KEY_RESPDESC);

                    if (respcode.equals("01")){
                        JSONObject Ob = obj.optJSONObject("details");
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
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
                Log.debug("registerResp: ",t.getMessage());
            }
        });
    }

   /* private void registerUser(final String username, final String name, final String email, String password) {

        pd.setMessage("Please Wait!");
        pd.show();

        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                HashMap<String , Object> map = new HashMap<>();
                map.put("name" , name);
                map.put("email", email);
                map.put("username" , username);
                map.put("id" , mAuth.getCurrentUser().getUid());
                map.put("bio" , "");
                map.put("imageurl" , "default");

                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "Update the profile " +
                                    "for better experience", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}
