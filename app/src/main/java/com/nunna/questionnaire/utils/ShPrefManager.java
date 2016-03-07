package com.nunna.questionnaire.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.nunna.questionnaire.model.StateDO;
import com.nunna.questionnaire.model.StateResponse;
import com.nunna.questionnaire.model.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sri Krishna on 02-02-2016.
 */
public class ShPrefManager
{
    private static final String TAG = ShPrefManager.class.getSimpleName();
    private static final String PREFS_NAME = "GoDingItPrefs";
    private static ShPrefManager instance;
    private SharedPreferences sharedPreferences;

    // All Shared Preferences Keys
    private static final String KEY_IS_WAITING_FOR_SMS = "IsWaitingForSms";
    private static final String KEY_MOBILE_NUMBER = "mobile_number";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";

    private ShPrefManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static ShPrefManager with(Context context)
    {
        if (instance == null)
        {
            instance = new ShPrefManager(context.getApplicationContext());
        }

        return instance;
    }

    public SharedPreferences.Editor edit() {
        return sharedPreferences.edit();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean("logged_in", false);
    }

    public String getUsername()
    {
        return sharedPreferences.getString("username", null);
    }

//    public String getUserId() {
//        return sharedPreferences.getString("user_id", null);
//    }




    public String getOfJID()
    {
        return sharedPreferences.getString("of_jid", null);
    }

    public boolean isStateCodesAcquired() {
        return sharedPreferences.getBoolean("StateCodes", false);
    }
    public void setStateCodeAcquired(boolean isStateCodesAquired) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("StateCodes", isStateCodesAquired);
        editor.commit();
    }

    public String getBearerToken() {
        return sharedPreferences.getString("bearerToken", null);
    }

    public String getUserFullName() {
        return sharedPreferences.getString("full_name", null);
    }

    public void  setUserForgotPasswordFlag(String flag) {
         sharedPreferences.getString("forgotPasswordFlag", "false");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("forgotPasswordFlag", String.valueOf(flag));

        // editor.apply();
        editor.commit();
    }


    public String getUserForgotPasswordFlag() {
        return sharedPreferences.getString("forgotPasswordFlag", "");
    }

    public void putUserDetails(UserDetails userDetails)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", true);
        editor.putString("user_id", userDetails.getUserId());
        editor.putString("full_name", userDetails.getFullname());
        editor.putString("username", userDetails.getUsername());
        editor.putString("of_jid", userDetails.getOfJid());
        editor.putString("bearerToken", userDetails.getBearerToken());


        // editor.apply();
        editor.commit();
    }

    public void saveUserId(int userId)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId);
        editor.commit();
    }

    public int getUserId()
    {
        return sharedPreferences.getInt("userId", 0);
    }

    public void saveDeliveryCoordinate(double lat, double lng)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("delivery_latitude", String.valueOf(lat));
        editor.putString("delivery_longitude", String.valueOf(lng));
        // editor.apply();
        editor.commit();
    }




    public String getDeliveryLat()
    {
        String lat = sharedPreferences.getString("delivery_latitude", "0d");
        return lat;
    }

    public String getDeliveryLng()
    {
        String lng = sharedPreferences.getString("delivery_longitude", "0d");
        return lng;
    }

    public void ClearUserDetails()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", false);
        editor.putString("user_id", "");
        editor.putString("full_name", "");
        editor.putString("username","");
        editor.putString("of_jid", "");
        editor.putString("bearerToken","");

        editor.apply();
    }

    public final static String STATE_ID_DELIMITER = "::::";
    public final static String STATE_DELIMITER = ",";

    public void saveStateIDs(ArrayList<StateDO> stateDOArrayList)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < stateDOArrayList.size(); i++)
        {
            StateDO stateDO = stateDOArrayList.get(i);
            sb.append(stateDO.getId());
            sb.append(STATE_ID_DELIMITER);
            sb.append(stateDO.getName());
            if(i != stateDOArrayList.size() - 1)
                sb.append(STATE_DELIMITER);
        }

        editor.putString("states", sb.toString());
        editor.apply();
    }

    public ArrayList<StateDO> getState()
    {
        String states = sharedPreferences.getString("states", "");
        if(TextUtils.isEmpty(states))
            return null;
        String[] stateParts = states.split(STATE_DELIMITER);
        ArrayList<StateDO> stateDOs = new ArrayList<>();
        for(int i = 0; i < stateParts.length; i++)
        {
            String statePart = stateParts[i];
            String parts[] = statePart.split(STATE_ID_DELIMITER);
            StateDO stateDO = new StateDO();
            stateDO.setId(Integer.parseInt(parts[0]));
            stateDO.setName(parts[1]);
            stateDOs.add(stateDO);
        }
        return stateDOs;
    }

    public HashMap<String,Integer> getStatesHM()
    {
        ArrayList<StateDO> stateDOs = getState();
        HashMap<String,Integer> hmStates = new HashMap<>();
        for(int i = 0; i < stateDOs.size(); i++){
            hmStates.put(stateDOs.get(i).getName(), stateDOs.get(i).getId());
        }
        return hmStates;
    }


    public void setIsWaitingForSms(boolean isWaiting) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_WAITING_FOR_SMS, isWaiting);
        editor.commit();
    }

    public boolean isWaitingForSms() {
        return sharedPreferences.getBoolean(KEY_IS_WAITING_FOR_SMS, false);
    }

    public void setMobileNumber(String mobileNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return sharedPreferences.getString(KEY_MOBILE_NUMBER, null);
    }
}
