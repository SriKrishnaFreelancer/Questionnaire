package com.nunna.questionnaire.services;

import android.text.TextUtils;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by Sri Krishna on 02-02-2016.
 */
public abstract class RestCallback<T> implements Callback<T> {

    public abstract void failure(RestError restError);

    @Override
    public void failure(RetrofitError error) {
        try{
            RestError restError = (RestError) error.getBodyAs(RestError.class);
            if (restError != null &&
                    !(TextUtils.isEmpty(restError.getError()) &&
                            TextUtils.isEmpty(restError.getErrorMessage())
                    )
                    )
                failure(restError);
            else {
                failure(new RestError(error.getMessage()));
            }
        }
        catch(Exception e)
        {
            String ErrorMessage = e.getMessage();
            if(error != null)
            {
                ErrorMessage = !"".equalsIgnoreCase(error.getMessage()) ? error.getMessage() : e.getMessage();
            }
            failure(new RestError(ErrorMessage));
        }
    }
}
