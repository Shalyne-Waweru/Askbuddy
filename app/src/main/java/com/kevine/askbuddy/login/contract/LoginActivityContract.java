package com.kevine.askbuddy.login.contract;

public interface LoginActivityContract {

     interface View{
         void onShowProgress();
         void onDismissProgress();
         void onSuccessLogin();
         void onFailedLogin(String errorMsg);
     }

     interface Presenter{
         void onFormSubmitted(String email,String passowrd);
     }

     interface Model{
         void invokeLogin(String email,String passowrd);
     }
}
