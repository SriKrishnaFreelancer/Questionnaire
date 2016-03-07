package com.nunna.questionnaire;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nunna.questionnaire.adapters.QuestionnaireAdapter;
import com.nunna.questionnaire.model.QuestionDO;
import com.nunna.questionnaire.model.QuestionnaireListRequest;
import com.nunna.questionnaire.model.QuestionnaireResponse;
import com.nunna.questionnaire.services.RestCallback;
import com.nunna.questionnaire.services.RestClient;
import com.nunna.questionnaire.services.RestError;
import com.nunna.questionnaire.utils.DividerItemDecoration;
import com.nunna.questionnaire.utils.ShPrefManager;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit.client.Response;

/**
 * Created by Sri Krishna on 04-03-2016.
 */
public class QuestionnaireActivity extends BaseActivity {

    private RecyclerView mTimeLineResultList;
    private QuestionnaireAdapter mQuestionnaireAdapter;
    private ProgressBar progressBar;
    private TextView tvNoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTimeLineResultList = (RecyclerView) findViewById(R.id.timeLineResultList);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvNoItems = (TextView) findViewById(R.id.tvNoItems);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(QuestionnaireActivity.this);
        mTimeLineResultList.setLayoutManager(mLayoutManager);
        mTimeLineResultList.addItemDecoration(new DividerItemDecoration(QuestionnaireActivity.this, DividerItemDecoration.VERTICAL_LIST));

        mQuestionnaireAdapter = new QuestionnaireAdapter(QuestionnaireActivity.this, null);
        mTimeLineResultList.setItemAnimator(new SlideInUpAnimator());
        mTimeLineResultList.setAdapter(mQuestionnaireAdapter);

        progressBar.setVisibility(View.VISIBLE);
        int userId = getIntent().getIntExtra("userId", 0);
        if(userId == 0)
        {
            finish();
            return;
        }

        callService(userId);
    }

    private void callService(int userId)
    {
        QuestionnaireListRequest questionnaireListRequest = new QuestionnaireListRequest();
        questionnaireListRequest.setUserid(userId);
        RestClient.getAPI().getQuestionList(questionnaireListRequest, new RestCallback<QuestionnaireResponse>() {
            @Override
            public void failure(RestError restError) {
                ShPrefManager.with(QuestionnaireActivity.this).setStateCodeAcquired(false);
                String errorMessage = String.valueOf(restError.getErrorMessage());
                Log.i("RestClient", errorMessage);
                showErrorAlertDialog(QuestionnaireActivity.this, "Service Failure!", "We couldn't retrieve the data from Server, please try again later.");
            }

            @Override
            public void success(QuestionnaireResponse questionnaireResponse, Response response) {
                if ("Success".equalsIgnoreCase(questionnaireResponse.getMessage())) {
                    ArrayList<QuestionDO> questionDOs = questionnaireResponse.getQuestionList();
                    mQuestionnaireAdapter.refresh(questionDOs);
                    if(questionDOs == null || questionDOs.size() == 0)
                    {
                        mTimeLineResultList.setVisibility(View.GONE);
                        tvNoItems.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        mTimeLineResultList.setVisibility(View.VISIBLE);
                        tvNoItems.setVisibility(View.GONE);
                    }
                    progressBar.setVisibility(View.GONE);

                } else
                    showErrorAlertDialog(QuestionnaireActivity.this, "Service Failure!",
                            "We couldn't retrieve the data from Server, please try again later.\nError code: " + questionnaireResponse.getErrorcode());

            }
        });
    }

}
