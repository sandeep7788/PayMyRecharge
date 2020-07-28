package com.codeunite.paymyrch.helper;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationChecker {
    public Boolean isValidNumber(String mob) {
        Matcher matcher = Pattern.compile("[7-9][0-9]{9}").matcher(mob);
        if (matcher.find() && matcher.group() == mob) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public void HideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService("input_method");
        if (activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
        }
    }
}
