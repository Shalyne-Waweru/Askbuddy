package com.kevine.askbuddy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kevine.askbuddy.Fragments.HomeFragment;
import com.kevine.askbuddy.Fragments.NotificationFragment;
import com.kevine.askbuddy.Fragments.ProfileFragment;
import com.kevine.askbuddy.Fragments.SearchFragment;
import com.kevine.askbuddy.network.ApiClientString;
import com.kevine.askbuddy.network.ApiInterface;
import com.kevine.askbuddy.post.PostActivity;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchPost();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_home :
                        selectorFragment = new HomeFragment();
                        break;

                    case R.id.nav_search :
                        selectorFragment = new SearchFragment();
                        break;

                    case R.id.nav_add :
                        selectorFragment = null;
                        startActivity(new Intent(MainActivity.this , PostActivity.class));
                        break;

                    case R.id.nav_heart :
                        selectorFragment = new NotificationFragment();
                        break;

                    case R.id.nav_profile :
                        selectorFragment = new ProfileFragment();
                        break;
                }

                if (selectorFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , selectorFragment).commit();
                }

                return  true;

            }
        });

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String profileId = intent.getString("publisherId");

            getSharedPreferences("PROFILE", MODE_PRIVATE).edit().putString("profileId", profileId).apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new HomeFragment()).commit();
        }
    }

    private void fetchPost() {
        showProgress();

        ApiInterface apiInterface = ApiClientString.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.fetchPostByUserID(session.getUserId());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dismissProgress();
                try {
                    JSONObject jsonObject = new JSONObject("details");

                    String respdesc = jsonObject.optString(Constants.KEY_RESPDESC);
                    String respcode = jsonObject.optString(Constants.KEY_RESPCODE);


                }catch (JSONException e){
                    dismissProgress();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dismissProgress();

            }
        });
    }
}
