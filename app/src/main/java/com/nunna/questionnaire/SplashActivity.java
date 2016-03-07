package com.nunna.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.nunna.questionnaire.model.StateDO;
import com.nunna.questionnaire.model.StateResponse;
import com.nunna.questionnaire.services.RestCallback;
import com.nunna.questionnaire.services.RestClient;
import com.nunna.questionnaire.services.RestError;
import com.nunna.questionnaire.utils.NetworkUtils;
import com.nunna.questionnaire.utils.ShPrefManager;

import java.util.ArrayList;

import retrofit.client.Response;

/**
 * Created by Sri Krishna on 05-03-2016.
 */
public class SplashActivity  extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(NetworkUtils.isNetworkConnected(this))
            getStateCodes();
        else
            showErrorAlertDialog(this, "Internet Unavailable!", "Please check your Internet connection, and try again later.");
    }

    private void navigateToRegistrationPage()
    {
        Intent i = new Intent(SplashActivity.this, SmsActivity.class);
        startActivity(i);
    }

    private void navigateToQuestionnairePage(int userId)
    {
        Intent i = new Intent(SplashActivity.this, QuestionnaireActivity.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

    private void navigateToNextScreen()
    {
        int userId = ShPrefManager.with(SplashActivity.this).getUserId();
        if(userId == 0)
            navigateToRegistrationPage();
        else
            navigateToQuestionnairePage(userId);
    }

    private void getStateCodes()
    {
        boolean isStateCodesAcquired = ShPrefManager.with(this).isStateCodesAcquired();
        if(isStateCodesAcquired)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateToNextScreen();
                    finish();
                }
            }, 3000);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callStatesService();
                }
            }, 1500);

        }

    }

    private void callStatesService()
    {
        RestClient.getAPI().getStateCodes(new RestCallback<StateResponse>() {
            @Override
            public void failure(RestError restError) {
                ShPrefManager.with(SplashActivity.this).setStateCodeAcquired(false);
                String errorMessage = String.valueOf(restError.getErrorMessage());
                Log.i("RestClient", errorMessage);
                showErrorAlertDialog(SplashActivity.this, "Service Failure!", "We couldn't retrieve the data from Server, please try again later.");
            }

            @Override
            public void success(StateResponse stateResponse, Response response) {
                if ("Success".equalsIgnoreCase(stateResponse.getMessage())) {
                    ArrayList<StateDO> stateDOs = stateResponse.getStateDOArrayList();
                    ShPrefManager.with(SplashActivity.this).saveStateIDs(stateDOs);
                    ShPrefManager.with(SplashActivity.this).setStateCodeAcquired(true);
                    navigateToNextScreen();
                    finish();
                }
                else
                    showErrorAlertDialog(SplashActivity.this, "Service Failure!",
                            "We couldn't retrieve the data from Server, please try again later.\nError code: "+stateResponse.getErrorcode());

            }
        });
    }

}
