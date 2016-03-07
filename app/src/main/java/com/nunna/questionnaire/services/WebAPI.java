package com.nunna.questionnaire.services;


import com.nunna.questionnaire.model.ConfirmSMSResponse;
import com.nunna.questionnaire.model.QuestionItemRequest;
import com.nunna.questionnaire.model.QuestionItemResponse;
import com.nunna.questionnaire.model.QuestionPollResponse;
import com.nunna.questionnaire.model.QuestionnaireListRequest;
import com.nunna.questionnaire.model.QuestionnaireResponse;
import com.nunna.questionnaire.model.RegisterUserRequest;
import com.nunna.questionnaire.model.RegisterUserResponse;
import com.nunna.questionnaire.model.ResponseData;
import com.nunna.questionnaire.model.StateResponse;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by Sri Krishna on 02-02-2016.
 */
public interface WebAPI {

    @GET("/mobileapi/getstates")
    void getStateCodes(RestCallback<StateResponse> callback);

    @POST("/mobileapi/newuserreg")
    void registerUser(@Body RegisterUserRequest verifyEmailRequest, RestCallback<RegisterUserResponse> callback);

    @POST("/mobileapi/questionlist")
    void getQuestionList(@Body QuestionnaireListRequest questionnaireListRequest, RestCallback<QuestionnaireResponse> callback);

    @GET("/mobileapi/smsconfirm")
    void sendOTPRequest(@Query("telnumber") String phoneNumber, RestCallback<ConfirmSMSResponse> callback);

    @Multipart
    @POST("/mobileapi/question")
    void getQuestionPartList(@Part("userid") TypedString userid, @Part("questionid") TypedString questionid, RestCallback<QuestionItemResponse> callback);

    @GET("/mobileapi/storeresult")
    void getQuestionPolls(@Query("ansid") int ansid, @Query("qid") int qid, RestCallback<QuestionPollResponse> callback);

}
