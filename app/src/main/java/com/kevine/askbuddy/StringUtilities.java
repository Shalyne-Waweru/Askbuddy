package com.kevine.askbuddy;

import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtilities {

    public static boolean checkFilledEditText(EditText editText, String errorMessage) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(errorMessage);
            return false;
        } else {
            return true;
        }
    }
}
