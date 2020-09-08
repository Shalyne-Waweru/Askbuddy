package com.kevine.askbuddy.login.contract;

public interface LoginActivityContract {

     interface View{
         void onShowProgress();
         void onDismissProgress();
         void onSuccessLogin();
         void onFailedLogin(String errorMsg);
     }

     interface Presenter{
         void onFormSubmitted(String email,String password);
     }

     interface Model{
         void invokeLogin(String email,String password);
         //void invokeRegister(String username,String name,String email,String password);
     }
}
