package com.nunna.questionnaire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nunna.questionnaire.model.ConfirmSMSResponse;
import com.nunna.questionnaire.service.OTPService;
import com.nunna.questionnaire.services.RestCallback;
import com.nunna.questionnaire.services.RestClient;
import com.nunna.questionnaire.services.RestError;
import com.nunna.questionnaire.utils.ShPrefManager;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import retrofit.client.Response;

public class SmsActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = SmsActivity.class.getSimpleName();

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Button btnRequestSms, btnVerifyOtp;
    private EditText inputOtp, etContactNumber;
    private Spinner stateSpinner, genderSpinner;
    private ProgressBar progressBar;
    private ShPrefManager pref;
    private ImageButton btnEditMobile;
    private TextView txtEditMobile;
    private LinearLayout layoutEditMobile;

    public HashMap<String, Integer> hmStates;
    public HashMap<String, String> hmGender;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if("KILL_SMS_ACTIVITY".equalsIgnoreCase(intent.getAction()))
            {
                finish();
            }
            else if("ACTION_SMS_RECIEVER_SEND_OTP".equalsIgnoreCase(intent.getAction()))
            {
                String otp = intent.getStringExtra("otp");
                if(!TextUtils.isEmpty(otp))
                    inputOtp.setText(String.valueOf(otp));
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("KILL_SMS_ACTIVITY");
        filter.addAction("ACTION_SMS_RECIEVER_SEND_OTP");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        viewPager = (ViewPager) findViewById(R.id.viewPagerVertical);
        inputOtp = (EditText) findViewById(R.id.inputOtp);
        btnRequestSms = (Button) findViewById(R.id.btn_request_sms);
        btnVerifyOtp = (Button) findViewById(R.id.btn_verify_otp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnEditMobile = (ImageButton) findViewById(R.id.btn_edit_mobile);
        txtEditMobile = (TextView) findViewById(R.id.txt_edit_mobile);
        layoutEditMobile = (LinearLayout) findViewById(R.id.layout_edit_mobile);
        etContactNumber = (EditText) findViewById(R.id.etContactNumber);
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);

        setUpStatesSpinner();
        setUpGenderSpinner();

        // view click listeners
        btnEditMobile.setOnClickListener(this);
        btnRequestSms.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);

        // hiding the edit mobile number
        layoutEditMobile.setVisibility(View.GONE);

        pref = ShPrefManager.with(this);

        // Checking for user session
        // if user is already logged in, take him to main activity
        if (pref.getUserId() != 0) {
            Intent intent = new Intent(SmsActivity.this, QuestionnaireActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("userId", pref.getUserId());
            startActivity(intent);
            finish();
        }

        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /**
         * Checking if the device is waiting for sms
         * showing the user OTP screen
         */
        if (pref.isWaitingForSms()) {
            viewPager.setCurrentItem(1);
            layoutEditMobile.setVisibility(View.VISIBLE);
        }
    }

    private void setUpStatesSpinner()
    {
        try
        {
            hmStates = ShPrefManager.with(this).getStatesHM();
            SortedSet<String> states = new TreeSet<>(hmStates.keySet());
            String[] array = states.toArray(new String[states.size()]);
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(SmsActivity.this, android.R.layout.simple_spinner_item, array);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stateSpinner.setAdapter(adapter);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            showToast("Error setting up States Spinner!");
        }

    }

    private void setUpGenderSpinner()
    {
        hmGender = new HashMap<>();
        hmGender.put("Male", "M");
        hmGender.put("Female", "F");
        hmGender.put("Transgender", "T");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_request_sms:
                validateForm();
                break;

            case R.id.btn_verify_otp:
                verifyOtp();
                break;

            case R.id.btn_edit_mobile:
                viewPager.setCurrentItem(0);
                layoutEditMobile.setVisibility(View.GONE);
                pref.setIsWaitingForSms(false);
                break;
        }
    }

    /**
     * Validating user details form
     */
    private void validateForm() {

        String mobile = etContactNumber.getText().toString().trim();

        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile)) {

            // request for sms
            progressBar.setVisibility(View.VISIBLE);

            // saving the mobile number in shared preferences
            pref.setMobileNumber(mobile);

            // requesting for sms
            requestForSMS(mobile);

        } else {
            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }
    }

    int otpFromServer;

    /**
     * Method initiates the SMS request on the server
     *
     * @param mobile user valid mobile number
     */
    private void requestForSMS(final String mobile) {
        otpFromServer = 0;
        RestClient.getAPI().sendOTPRequest(mobile, new RestCallback<ConfirmSMSResponse>() {
            @Override
            public void failure(RestError restError) {
                String errorMessage = String.valueOf(restError.getErrorMessage());
                Log.e(TAG, "Error: " + errorMessage);
                showToast(errorMessage/*"Services need to be corrected"*/);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void success(ConfirmSMSResponse confirmSMSResponse, Response response) {
                if ("Success".equalsIgnoreCase(confirmSMSResponse.getMessage())) {
                    otpFromServer = confirmSMSResponse.getOTP();

                    pref.setIsWaitingForSms(true);

                    // moving the screen to next pager item i.e otp screen
                    viewPager.setCurrentItem(1);
                    txtEditMobile.setText(pref.getMobileNumber());
                    layoutEditMobile.setVisibility(View.VISIBLE);

                } else {
                    showToast("Error: " + confirmSMSResponse.getMessage());
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    /**
     * sending the OTP to server and activating the user
     */
    private void verifyOtp() {
        if(otpFromServer == 0)
            return;

        String otp = inputOtp.getText().toString().trim();

        if (!otp.isEmpty()) {
            if(otp.equalsIgnoreCase(String.valueOf(otpFromServer)))
            {
                Intent grapprIntent = new Intent(getApplicationContext(), OTPService.class);
                grapprIntent.putExtra("gender", hmGender.get(genderSpinner.getSelectedItem()));
                grapprIntent.putExtra("stateId", hmStates.get(stateSpinner.getSelectedItem()).intValue());
                grapprIntent.putExtra("phoneNumber", etContactNumber.getText().toString());
                startService(grapprIntent);

                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Please enter the correct OTP", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Regex to validate the mobile number
     * mobile number should be of 10 digits length
     *
     * @param mobile
     * @return
     */
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }


    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.layout_sms;
                    break;
                case 1:
                    resId = R.id.layout_otp;
                    break;
            }
            return findViewById(resId);
        }
    }

}
