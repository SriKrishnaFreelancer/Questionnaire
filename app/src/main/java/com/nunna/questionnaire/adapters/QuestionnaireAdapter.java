package com.nunna.questionnaire.adapters;

/**
 * Created by Sri Krishna on 04-02-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nunna.questionnaire.QuestionDialog;
import com.nunna.questionnaire.R;
import com.nunna.questionnaire.model.QuestionDO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionnaireAdapter extends
        RecyclerView.Adapter<QuestionnaireAdapter.ViewHolder> {

    private Context context;
    private List<QuestionDO> questionDOs;

    public QuestionnaireAdapter(Context context, List<QuestionDO> questionDOs) {
        this.context = context;
        this.questionDOs = questionDOs;
    }

    public void refresh(List<QuestionDO> questionDOs)
    {
        this.questionDOs = questionDOs;
        notifyDataSetChanged();
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, null);
            return new ViewHolder(inflateView);
    }

    String BASE_IMAGE_URL = "http://54.179.128.29:8080/";
    String FILE_PATH = "images/";

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        QuestionDO questionDO = questionDOs.get(position);

        viewHolder.tvCreationDate.setText(questionDO.getCreated_at());
        viewHolder.tvQuestion.setText(questionDO.getQuestion());

        String imagePath = BASE_IMAGE_URL + questionDO.getImage();
//        if(position%3 == 0)
//            imagePath = BASE_IMAGE_URL + FILE_PATH + "2016-02-19-13-04-11-sportentertain.jpg";
//        else if(position%3 == 1)
//            imagePath = BASE_IMAGE_URL + FILE_PATH + "2016-02-16-08-46-50-ent1.jpg";
//        else
//            imagePath = BASE_IMAGE_URL + FILE_PATH + "2016-02-14-18-30-19-parties.jpg";
        if (imagePath != null && !"".equalsIgnoreCase(imagePath)) {
            Picasso.with(context)
                    .load(imagePath)
                    .resize(100, 100)
                    .centerInside()
                    .into(viewHolder.ivQuestion);
        }
        viewHolder.rlMain.setTag(R.string.app_name, questionDO);
        viewHolder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionDO questionDO = (QuestionDO) v.getTag(R.string.app_name);
                boolean isQuestionTypeSupported = "3#Buttons".equalsIgnoreCase(questionDO.getQuestiontype())
                        || "2#Buttons".equalsIgnoreCase(questionDO.getQuestiontype());
                if(isQuestionTypeSupported)
                {
                    QuestionDialog questionDialog = new QuestionDialog((Activity) context, questionDO);
                    questionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    questionDialog.show();
                }
                else
                {
                    Snackbar.make(v, "This Type of Question will be supported in future.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if(questionDOs != null && questionDOs.size() > 0)
            return questionDOs.size();
        else
            return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlMain;
        public TextView tvCreationDate;
        public TextView tvQuestion;
        public ImageView ivQuestion;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            rlMain = (RelativeLayout) itemLayoutView;
            tvCreationDate = (TextView) itemLayoutView.findViewById(R.id.tvCreationDate);
            tvQuestion = (TextView) itemLayoutView.findViewById(R.id.tvQuestion);
            ivQuestion = (ImageView) itemLayoutView.findViewById(R.id.ivQuestion);
        }
    }

    // method to access in activity after updating selection
    public List<QuestionDO> getQuestions() {
        return questionDOs;
    }
}
