package com.nunna.questionnaire;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nunna.questionnaire.model.QuestionDO;
import com.nunna.questionnaire.model.QuestionItemRequest;
import com.nunna.questionnaire.model.QuestionItemResponse;
import com.nunna.questionnaire.model.QuestionPollResponse;
import com.nunna.questionnaire.model.QuestionSubPartDO;
import com.nunna.questionnaire.services.RestCallback;
import com.nunna.questionnaire.services.RestClient;
import com.nunna.questionnaire.services.RestError;
import com.nunna.questionnaire.utils.ShPrefManager;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit.client.Response;
import retrofit.mime.TypedString;

/**
 * Created by Sri Krishna on 06-03-2016.
 */
public class QuestionDialog extends Dialog implements
        android.view.View.OnClickListener {


    public Activity c;
    public Dialog d;
    public TextView tvCaption, tvQuestion;
    public ImageView ivImage, ivClose;
    public Button optionOne, optionTwo, optionThree;
    public ProgressBar progressBar;
    private ProgressBar optionOneProgress, optionTwoProgress, optionThreeProgress;
    private TextView tvOptionOne, tvOptionTwo, tvOptionThree, tvTotalVotes;

    public LinearLayout llQuestion;
    public FrameLayout flOptionThree;
    private QuestionDO questionDO;

    String BASE_IMAGE_URL = "http://54.179.128.29:8080/";

    public QuestionDialog(Activity a, QuestionDO questionDO) {
        super(a);
        this.c = a;
        this.questionDO = questionDO;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.three_question_type);
        llQuestion = (LinearLayout) findViewById(R.id.llQuestion);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        optionThree = (Button) findViewById(R.id.optionThree);
        optionTwo = (Button) findViewById(R.id.optionTwo);
        optionOne = (Button) findViewById(R.id.optionOne);
        tvCaption = (TextView) findViewById(R.id.tvCaption);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        flOptionThree = (FrameLayout) findViewById(R.id.flOptionThree);
        tvTotalVotes = (TextView) findViewById(R.id.tvTotalVotes);

        optionOneProgress = (ProgressBar) findViewById(R.id.optionOneProgress);
        optionTwoProgress = (ProgressBar) findViewById(R.id.optionTwoProgress);
        optionThreeProgress = (ProgressBar) findViewById(R.id.optionThreeProgress);

        tvOptionOne = (TextView) findViewById(R.id.tvOptionOne);
        tvOptionTwo = (TextView) findViewById(R.id.tvOptionTwo);
        tvOptionThree = (TextView) findViewById(R.id.tvOptionThree);

        tvCaption.setText(questionDO.getCaption());
        tvQuestion.setText(questionDO.getQuestion());

        String imagePath = BASE_IMAGE_URL + questionDO.getImage();
        if (imagePath != null && !"".equalsIgnoreCase(imagePath)) {
            Picasso.with(c)
                    .load(imagePath)
                    .resize(300, 150)
                    .centerCrop()
                    .into(ivImage);
        }

        optionThree.setOnClickListener(this);
        optionTwo.setOnClickListener(this);
        optionOne.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        if("2#Buttons".equalsIgnoreCase(questionDO.getQuestiontype()))
        {
            flOptionThree.setVisibility(View.GONE);
        }

        progressBar.setVisibility(View.VISIBLE);
        llQuestion.setVisibility(View.INVISIBLE);
        callQuestionService(questionDO.getQuestion_id());

    }

    private void updateUI(ArrayList<QuestionSubPartDO> questionSubPartDOs)
    {
        for(int i = 0; i < questionSubPartDOs.size(); i++)
        {
            QuestionSubPartDO questionSubPartDO = questionSubPartDOs.get(i);
            switch (questionSubPartDO.button_pos){
                case 1:
                    optionOne.setText(questionSubPartDO.getButtonlabel());
                    tvOptionOne.setText(questionSubPartDO.getButtonlabel());
                    break;
                case 2:
                    optionTwo.setText(questionSubPartDO.getButtonlabel());
                    tvOptionTwo.setText(questionSubPartDO.getButtonlabel());
                    break;
                case 3:
                    optionThree.setText(questionSubPartDO.getButtonlabel());
                    tvOptionThree.setText(questionSubPartDO.getButtonlabel());
                    flOptionThree.setVisibility(View.VISIBLE);
                    break;
            }
        }

        progressBar.setVisibility(View.GONE);
        llQuestion.setVisibility(View.VISIBLE);
    }

    private void callQuestionService(int questionId)
    {
        int userId = ShPrefManager.with(c).getUserId();
        TypedString userIdTypedString = new TypedString(String.valueOf(userId));
        TypedString questionIdTypedString = new TypedString(String.valueOf(questionId));


        RestClient.getAPI().getQuestionPartList(userIdTypedString, questionIdTypedString, new RestCallback<QuestionItemResponse>() {
            @Override
            public void failure(RestError restError) {
                Toast.makeText(c, restError.getErrorMessage(), Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void success(QuestionItemResponse questionItemResponse, Response response) {
                if ("Success".equalsIgnoreCase(questionItemResponse.getMessage())) {
                    updateUI(questionItemResponse.getQuestionSubPartDOs());
                } else {
                    Toast.makeText(c, questionItemResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.optionThree:
                callQuestionPollService(questionDO.getQuestion_id(), 3);
                break;
            case R.id.optionTwo:
                callQuestionPollService(questionDO.getQuestion_id(), 2);
                break;
            case R.id.optionOne:
                callQuestionPollService(questionDO.getQuestion_id(), 1);
                break;
            case R.id.ivClose:
                dismiss();
            default:
                break;
        }
    }

    private void callQuestionPollService(int questionId, int answerId)
    {
//        progressBar.setVisibility(View.VISIBLE);
//        llQuestion.setVisibility(View.INVISIBLE);
        RestClient.getAPI().getQuestionPolls(answerId, questionId, new RestCallback<QuestionPollResponse>() {
            @Override
            public void failure(RestError restError) {
                Toast.makeText(c, restError.getErrorMessage(), Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void success(QuestionPollResponse questionPollResponse, Response response) {
                if ("Success".equalsIgnoreCase(questionPollResponse.getMessage())) {
                    updateUI(questionPollResponse.getResult());
//                    progressBar.setVisibility(View.GONE);
//                    llQuestion.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(c, questionPollResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }

    private void updateUI(HashMap<String, Integer> hashMap)
    {
        int pollsForOne = hashMap.containsKey("1") ? hashMap.get("1") : 0;
        int pollsForTwo = hashMap.containsKey("2")? hashMap.get("2") : 0;
        int pollsForThree = hashMap.containsKey("3") ? hashMap.get("3") : 0;
        float total = pollsForOne + pollsForTwo + pollsForThree;
        float progressOne =  0f, progressTwo = 0f, progressThree = 0f;
        optionOne.setVisibility(View.INVISIBLE);
        optionTwo.setVisibility(View.INVISIBLE);
        optionThree.setVisibility(View.INVISIBLE);

        tvOptionOne.setVisibility(View.VISIBLE);
        tvOptionTwo.setVisibility(View.VISIBLE);
        tvOptionThree.setVisibility(View.VISIBLE);

        optionOneProgress.setVisibility(View.VISIBLE);
        optionTwoProgress.setVisibility(View.VISIBLE);
        optionThreeProgress.setVisibility(View.VISIBLE);

        optionOneProgress.setMax((int) total);
        optionTwoProgress.setMax((int) total);
        optionThreeProgress.setMax((int) total);

        progressOne = (pollsForOne/total) * 100;
        progressTwo = (pollsForTwo/total) * 100;
        progressThree = (pollsForThree/total) * 100;

        optionOneProgress.setProgress((int) progressOne);
        optionTwoProgress.setProgress((int) progressTwo);
        optionThreeProgress.setProgress((int) progressThree);

        float maxProgress = Math.max(Math.max(progressOne,progressTwo),progressThree);
        if(maxProgress == progressOne)
        {
            tvOptionOne.setTypeface(null, Typeface.BOLD);
        }
        else if(maxProgress == progressOne)
        {
            tvOptionTwo.setTypeface(null, Typeface.BOLD);;
        }
        else if(maxProgress == progressOne)
        {
            tvOptionThree.setTypeface(null, Typeface.BOLD);;
        }

        tvOptionOne.setText(String.format(Locale.US, "%s (%d Votes), %2d %% Share", tvOptionOne.getText(), (int)pollsForOne, (int)progressOne));
        tvOptionTwo.setText(String.format(Locale.US, "%s (%d Votes), %2d %% Share", tvOptionTwo.getText(), (int)pollsForTwo, (int)progressTwo ));
        tvOptionThree.setText(String.format(Locale.US, "%s (%d Votes), %2d %% Share", tvOptionThree.getText(), (int)pollsForThree, (int)progressThree ));

        tvTotalVotes.setText(String.format(Locale.US,"%d Votes Cast till date.", (int)total));
        tvTotalVotes.setVisibility(View.VISIBLE);
    }

}

