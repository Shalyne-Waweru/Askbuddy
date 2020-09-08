package com.kevine.askbuddy;

import android.graphics.Color;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseFragment extends Fragment {

    public SweetAlertDialog pDialog;
    public Sessions session;

    public void showProgress(){
        if(pDialog != null)
            dismissProgress();
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
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

}
