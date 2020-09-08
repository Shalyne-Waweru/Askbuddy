package com.kevine.askbuddy.login.presenter;

import com.kevine.askbuddy.login.contract.LoginActivityContract;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {
    private LoginActivityContract.View view;
    private LoginActivityContract.Model model;

    /*public LoginActivityPresenter(LoginActivityContract.View view, LoginActivityContract.Model model) {
        this.view = view;
        this.model = model;
    }
*/
    @Override
    public void onFormSubmitted(String email, String password) {
//        view.onShowProgress();
        model.invokeLogin(email,password);
    }
}
