package com.suraj.missedcall;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import utils.Constant;
import utils.RequestReceiver;
import utils.SmsListener;
import utils.WebserviceHelper;


public class Otp extends Activity implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher, RequestReceiver {
    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinHiddenEditText;
    Button veify,Resend;
    RequestReceiver receiver;
    SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MainLayout(this, null));

        init();
        setPINListeners();
        clickListener();


    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * Hides soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

/**
 * Initialize EditText fields.
 */
private void init() {



    sharedPreferences = this.getSharedPreferences("verify", Context.MODE_PRIVATE);
    receiver = this;
    mPinFirstDigitEditText = (EditText) findViewById(R.id.pin_first_edittext);
    mPinSecondDigitEditText = (EditText) findViewById(R.id.pin_second_edittext);
    mPinThirdDigitEditText = (EditText) findViewById(R.id.pin_third_edittext);
    mPinForthDigitEditText = (EditText) findViewById(R.id.pin_forth_edittext);
    mPinHiddenEditText = (EditText) findViewById(R.id.pin_hidden_edittext);

    veify=(Button)findViewById(R.id.veify);
    Resend =(Button)findViewById(R.id.Resend);

    SmsReceiver.bindListener(new SmsListener() {
        @Override
        public void messageReceived(String messageText) {
            Log.d("Text",messageText);
            String otp = messageText.split(":")[1];
//            Toast.makeText(Otp.this,"Message: "+otp,Toast.LENGTH_LONG).show();
            char[] cArray = otp.toCharArray();
            mPinFirstDigitEditText.setText(""+cArray[1]);
            mPinSecondDigitEditText.setText(""+cArray[2]);
            mPinThirdDigitEditText.setText(""+cArray[3]);
            mPinForthDigitEditText.setText(""+cArray[4]);

            StringBuilder builder = new StringBuilder();
            builder.append(mPinFirstDigitEditText.getText().toString());
            builder.append(mPinSecondDigitEditText.getText().toString());
            builder.append(mPinThirdDigitEditText.getText().toString());
            builder.append(mPinForthDigitEditText.getText().toString());
            Constant.OTP = builder.toString();
            VerifyOTPService();
        }
    });

}


    public void clickListener(){
        veify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(mPinHiddenEditText.getText().length()==4){
                    StringBuilder builder = new StringBuilder();
                    builder.append(mPinFirstDigitEditText.getText().toString());
                    builder.append(mPinSecondDigitEditText.getText().toString());
                    builder.append(mPinThirdDigitEditText.getText().toString());
                    builder.append(mPinForthDigitEditText.getText().toString());
                    Constant.OTP = builder.toString();

                }else {
                    Toast.makeText(getApplicationContext(),"Enter OTP",Toast.LENGTH_SHORT).show();
                }*/
                StringBuilder builder = new StringBuilder();
                builder.append(mPinFirstDigitEditText.getText().toString());
                builder.append(mPinSecondDigitEditText.getText().toString());
                builder.append(mPinThirdDigitEditText.getText().toString());
                builder.append(mPinForthDigitEditText.getText().toString());
                Constant.OTP = builder.toString();
                VerifyOTPService();
            }
        });

        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOTPService();
            }
        });
    }



    public void getOTPService(){
        WebserviceHelper otp= new WebserviceHelper(receiver,Otp.this);
        otp.setAction(Constant.GENRATE_OTP);
        otp.execute();
    }

    public void VerifyOTPService(){
        WebserviceHelper verify= new WebserviceHelper(receiver,Otp.this);
        verify.setAction(Constant.VERIFY_OTP);
        verify.execute();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        switch (id) {
            case R.id.pin_first_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_second_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_third_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_forth_edittext:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            switch (id) {
                case R.id.pin_hidden_edittext:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {

                        if (mPinHiddenEditText.getText().length() == 4)
                            mPinForthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 3)
                            mPinThirdDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 2)
                            mPinSecondDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 1)
                            mPinFirstDigitEditText.setText("");

                        if (mPinHiddenEditText.length() > 0)
                            mPinHiddenEditText.setText(mPinHiddenEditText.getText().subSequence(0, mPinHiddenEditText.length() - 1));

                        return true;
                    }

                    break;

                default:
                    return false;
            }
        }

        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setDefaultPinBackground(mPinFirstDigitEditText);
        setDefaultPinBackground(mPinSecondDigitEditText);
        setDefaultPinBackground(mPinThirdDigitEditText);
        setDefaultPinBackground(mPinForthDigitEditText);

        if (s.length() == 0) {
            setFocusedPinBackground(mPinFirstDigitEditText);
            mPinFirstDigitEditText.setText("");
        } else if (s.length() == 1) {
            setFocusedPinBackground(mPinSecondDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText("");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
        } else if (s.length() == 2) {
            setFocusedPinBackground(mPinThirdDigitEditText);
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");

        } else if (s.length() == 3) {
            setFocusedPinBackground(mPinForthDigitEditText);
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText("");
        } else if (s.length() == 4) {

            mPinForthDigitEditText.setText(s.charAt(3) + "");

        } else if (s.length() == 5) {


        }
    }

    /**
     * Sets default PIN background.
     *
     * @param editText edit text to change
     */
    private void setDefaultPinBackground(EditText editText) {
        //setViewBackground(editText, getResources().getDrawable(R.drawable.ic_launcher));
    }



    /**
     * Sets focus on a specific EditText field.
     *
     * @param editText EditText to set focus on
     */
    public static void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    /**
     * Sets focused PIN field background.
     *
     * @param editText edit text to change
     */
    private void setFocusedPinBackground(EditText editText) {
        //setViewBackground(editText, getResources().getDrawable(R.drawable.ic_launcher));
    }

/**
 * Sets listeners for EditText fields.
 */


private void setPINListeners() {
    mPinHiddenEditText.addTextChangedListener(this);

    mPinFirstDigitEditText.setOnFocusChangeListener(this);
    mPinSecondDigitEditText.setOnFocusChangeListener(this);
    mPinThirdDigitEditText.setOnFocusChangeListener(this);
    mPinForthDigitEditText.setOnFocusChangeListener(this);
    //mPinFifthDigitEditText.setOnFocusChangeListener(this);

    mPinFirstDigitEditText.setOnKeyListener(this);
    mPinSecondDigitEditText.setOnKeyListener(this);
    mPinThirdDigitEditText.setOnKeyListener(this);
    mPinForthDigitEditText.setOnKeyListener(this);
    mPinHiddenEditText.setOnKeyListener(this);
}

/**
 * Sets background of the view.
 * This method varies in implementation depending on Android SDK version.
 *
 * @param view       View to which set background
 * @param background Background to set to view
 */


@SuppressWarnings("deprecation")
public void setViewBackground(View view, Drawable background) {
    if (view == null || background == null)
        return;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        view.setBackground(background);
    } else {
        view.setBackgroundDrawable(background);
    }
}

    /**
     * Shows soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    @Override
    public void requestFinished(String[] result) throws Exception {
            if(result[0].equals("101")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("status", "1");
                editor.putString("phone",""+Constant.PHONE_NO);
                editor.commit();
                Intent  intent = new Intent(Otp.this, HomeActivityy.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }else if(result[0].equals("1")) {

            }else {
                Toast.makeText(getApplicationContext(),"Invalid user.",Toast.LENGTH_SHORT).show();
            }
    }

    /**
     * Custom LinearLayout with overridden onMeasure() method
     * for handling software keyboard show and hide events.
     */
    public class MainLayout extends LinearLayout {

        public MainLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.otp_layout, this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#feb211"));
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            final int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
            final int actualHeight = getHeight();

            Log.d("TAG", "proposed: " + proposedHeight + ", actual: " + actualHeight);

            if (actualHeight >= proposedHeight) {
                // Keyboard is shown
                if (mPinHiddenEditText.length() == 0)
                    setFocusedPinBackground(mPinFirstDigitEditText);
                else
                    setDefaultPinBackground(mPinFirstDigitEditText);
            }

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
