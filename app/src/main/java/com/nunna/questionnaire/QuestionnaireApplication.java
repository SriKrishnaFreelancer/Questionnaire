package com.nunna.questionnaire;

import android.app.Application;


/**
 * Created by Sri Krishna on 13/05/15.
 */

public class QuestionnaireApplication extends Application {

    public static final String TAG = QuestionnaireApplication.class
            .getSimpleName();

    private static QuestionnaireApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized QuestionnaireApplication getInstance() {
        return mInstance;
    }

}