package com.kevine.askbuddy;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


public abstract class BaseActivity extends AppCompatActivity {

    public SweetAlertDialog pDialog;
    public Sessions session;
    //private Toolbar mTopToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Sessions(this);

    }

    public void showProgress(){
        if(pDialog != null)
            dismissProgress();
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void dismissProgress(){
        if(pDialog != null){
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void showSnackBar(String msg) {
        Snackbar.make(findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_LONG).show();
    }

    public void noInternetError(){
        showSnackBar("Please turn on data or connect to wifi.");
    }
}
