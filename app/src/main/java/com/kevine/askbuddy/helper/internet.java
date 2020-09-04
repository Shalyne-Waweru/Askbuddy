package com.kevine.askbuddy.helper;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.kevine.askbuddy.http.UserFunctions;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class internet {
    public String status,Action,result="";
   public Context c,Destination;
   public String[] Values,Tags;
   public boolean done;

   ListView listView;
   SweetAlertDialog progress;
    private static String KEY_SUCCESS = "success";
    public  internet(Context c, String[] Tags, String[] Values){
        this.Tags = Tags;
        this.Values = Values;
        this.c = c;
        this.Destination = Destination;
        this.Action = Action;

        progress= new SweetAlertDialog(c, SweetAlertDialog.PROGRESS_TYPE);
        progress.setTitleText("Processing");
        progress.setCancelable(false);
      //  checkInternetConnection();

    }
//    public  internet(Context c, String [] Tags, String [] Values, String Action, ListView listView){
//        this.Tags = Tags;
//        this.Values = Values;
//        this.c = c;
//        this.Action = Action;
//        this.listView = listView;
//        progress= new SweetAlertDialog(c,SweetAlertDialog.PROGRESS_TYPE);
//        progress.setTitleText("Processing");
//        progress.setCancelable(false);
//        //  checkInternetConnection();
//
//    }
    public String PerformAction(){
        checkInternetConnection();
        return result;
    }


    public boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
//            Tags = new String[]{"tag", "names","emails","countrycode","phonenumber","password","firebasetoken"};
//            Values = new String[]{register_tag, userName.getText().toString(),userEmail.getText().toString(),
//                    countrycode,userPhone.getText().toString().trim(), userPassword.getText().toString(),token};
            new InternetTask().execute();
            return true;
        } else {
            new SweetAlertDialog(c, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet Connection")
                    .setContentText("Please check your internet settings!!")
                    .setConfirmText("Try again")
                    .show();
            return false;
        }
    }
    private  class  InternetTask extends AsyncTask {
        private internet.InternetTask createTask = null;
        @Override
        protected void onPreExecute() {
            createTask = this;
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (isCancelled()) {
            } else {


                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.GenericFunction(Tags, Values);
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        String res = json.getString(KEY_SUCCESS);
                        if (Integer.parseInt(res) == 1) {
                            status = "1";
                            result = json.getString("response");


                        } else if (Integer.parseInt(res) == 0) {
                            status = "0";
                        }

                    }
                } catch (JSONException e1) {
                    Log.v("1", e1.toString());
                    status = "3";
                } catch (ParseException e1) {
                    Log.v("2", e1.toString());
                    status = "3";
                } catch (Exception e) {
                    Log.v("3", e.toString());
                    status = "3";
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            progress.dismiss();
            done = true;
            progress.cancel();
            if (status == "1") {
            if(Action.equals("intent")){
                c.startActivity(new Intent(c, Destination.getClass()));

            }
            }
            if (status == "0") {

            }
            if (status == "3") {

            }

        }
    }

    public String getResult() {
        if (done){
        return result;
        }
        return null;
    }
}

