package com.nunna.questionnaire.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nunna.questionnaire.QuestionnaireActivity;
import com.nunna.questionnaire.model.RegisterUserRequest;
import com.nunna.questionnaire.model.RegisterUserResponse;
import com.nunna.questionnaire.services.RestCallback;
import com.nunna.questionnaire.services.RestClient;
import com.nunna.questionnaire.services.RestError;
import com.nunna.questionnaire.utils.ShPrefManager;

import retrofit.client.Response;

/**
 * Created by Sri Krishna on 05/03/16.
 */
public class OTPService extends IntentService {

    private static String TAG = OTPService.class.getSimpleName();

    public OTPService() {
        super(OTPService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String gender = intent.getStringExtra("gender");
            String phoneNumber = intent.getStringExtra("phoneNumber");
            int stateId = intent.getIntExtra("stateId", 0);

            verifyOtp(phoneNumber, gender, stateId);
        }
    }

    private void navigateToQuestionnairePage(int userId)
    {
        Intent intent = new Intent(OTPService.this, QuestionnaireActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

//    private void killSMSActivity()
//    {
//        Intent killBroadcast = new Intent("KILL_SMS_ACTIVITY");
//        sendBroadcast(killBroadcast);
//    }

    /**
     * Posting the OTP to server and activating the user
     */
    private void verifyOtp(String phoneNumber, String gender, int stateId) {

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setStateid(stateId);
        registerUserRequest.setTelnumber(phoneNumber);
        registerUserRequest.setGender(gender);
        RestClient.getAPI().registerUser(registerUserRequest, new RestCallback<RegisterUserResponse>() {
            @Override
            public void failure(RestError restError) {
                String errorMessage = String.valueOf(restError.getErrorMessage());
                Log.e(TAG, "Error: " + errorMessage);
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(RegisterUserResponse registerUserResponse, Response response) {
                if ("Success".equalsIgnoreCase(registerUserResponse.getMessage())) {
                    int userId = registerUserResponse.getUserid();
                    ShPrefManager.with(OTPService.this).saveUserId(userId);
                    navigateToQuestionnairePage(userId);
//                    killSMSActivity();
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + registerUserResponse.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
