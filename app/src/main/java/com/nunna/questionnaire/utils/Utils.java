package com.nunna.questionnaire.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sri Krishna on 02-02-2016.
 */
public class Utils {

    /**
     * Method to validate email id
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * Method to validate url
     *
     * @param websiteAddress
     * @return
     */
    public static boolean isValidWebsiteAddress(String websiteAddress) {
        return Patterns.WEB_URL.matcher(websiteAddress).matches();
    }

    /**
     * Method to show toast message
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void showToast(final Context context, final String message, final int duration) {
        if(!(context instanceof Activity))
            return;
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, duration).show();
            }
        });

    }

    public static double formatToSixDecimalPoints(double coordinate)
    {
        return Double.parseDouble(new DecimalFormat("##.######").format(coordinate));
    }

    /**
     * See Link : http://stackoverflow.com/questions/27263746/how-to-validate-phone-number-format
     * @param phoneNo
     * @return
     */
    public static boolean validatePhone(String phoneNo)
    {
        String Regex = "[^\\d]";
        String PhoneDigits = phoneNo.replaceAll(Regex, "");
        if (PhoneDigits.length()!=10)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

}
