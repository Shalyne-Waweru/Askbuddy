package com.kevine.askbuddy.login.presenter;

import com.kevine.askbuddy.Constants;
import com.kevine.askbuddy.Log;
import com.kevine.askbuddy.Sessions;
import com.kevine.askbuddy.login.contract.LoginActivityContract;
import com.kevine.askbuddy.network.ApiClientString;
import com.kevine.askbuddy.network.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityModel implements LoginActivityContract.Model {

    private LoginActivityContract.View view;
    private Sessions sessions;

    @Override
    public void invokeLogin(String email, String password) {
        ApiInterface apiService = ApiClientString.getClient().create(ApiInterface.class);
        Call<String> call = apiService.loginApp(email, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.debug("loginResp: ",response.body());
                view.onDismissProgress();
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response.body());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String respcode = obj.optString(Constants.KEY_RESPCODE);
                String respdesc = obj.optString(Constants.KEY_RESPDESC);

                if ((respcode.equals("01"))){
                    JSONObject loginDetails = obj.optJSONObject("details");
                    sessions.setUserId(loginDetails.optString("u_id"));
                    view.onSuccessLogin();
                }else {
                    view.onFailedLogin(respdesc);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.onDismissProgress();
                Log.debug("loginResp: ",t.getMessage());
            }
        });
    }
}
