package utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import DTOs.MissedCall;
import DTOs.MissedCallDTO;
import DTOs.ProfileDTO;
import DTOs.SettingDTO;


@SuppressWarnings("deprecation")
public class WebserviceHelper extends AsyncTask<Void, Void, String[]> {

    private RequestReceiver mContext;
    @SuppressWarnings("unused")
    private String method = null;
    private Map<String, String> paramMap = new HashMap<String, String>();
    private String errorMessage;
    private boolean error_flag = false;
    ProgressDialog mProgressDialog;

    public static int action;

    ProgressDialog dialog;
    Activity mcont;

    public WebserviceHelper() {
    }

    public WebserviceHelper(RequestReceiver context, Activity mcontext) {
        mContext = context;
        mcont = mcontext;
        dialog = new ProgressDialog(mcontext);
    }

    WebserviceHelper(RequestReceiver context, String setMethod) {
        mContext = context;
        method = setMethod;
    }

    private void clearErrors() {
        this.errorMessage = null;
        this.error_flag = false;
    }

    public void setMethod(String m) {
        method = m;
    }

    public void addParam(String key, String value) {
        paramMap.put(key, value);
    }

    @Override
    protected void onPreExecute() {
        this.clearErrors();
        dialog.setMessage("Please Wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String[] doInBackground(Void... params) {

        HttpClient httpclient = new DefaultHttpClient();
        JSONObject jsonObj = new JSONObject();
        HttpResponse response1 = null;
        HttpPost httppost = null;
        HttpGet httpGet = null;
        InputStream inputStream = null;
        JSONObject jsonData = new JSONObject();
        HttpResponse httpResponse = null;
        switch (action) {

            case Constant.REGISTER_DEVICE:
                String[] register = new String[3];

                try {

                    httpGet = new HttpGet(Constant.BASE_URL+Constant.REGISTER_DEVICE_URL+Constant.PHONE_NO);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.REGISTER_DEVICE_URL+Constant.PHONE_NO);

                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring.toString());
//                        Toast.makeText(mcont,"JSON : "+jsonstring,Toast.LENGTH_SHORT).show();
                        JSONObject json = new JSONObject(jsonstring);

                        if(json.getString("result").equals("true")){
                            Constant.GETDEVICE_ID= json.getString("textData");
                            register[0] = "101";

                        }else {
                            register[0] = "000";
                        }

                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return register;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;

            case Constant.GENRATE_OTP:
                String[] genotp = new String[3];


                try {
                    httpGet = new HttpGet(Constant.BASE_URL+Constant.GENERATE_OTP_URL+Constant.PHONE_NO);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.GENERATE_OTP_URL+Constant.PHONE_NO);

                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        JSONObject json = new JSONObject(jsonstring);

                        if(json.getString("result").equals("true")){
                            genotp[0] = "1";
                        }else {
                            genotp[0] = "0";
                        }

                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return genotp;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;

            case Constant.VERIFY_OTP:
                String[] verifyOtp = new String[3];

                try {

                    httpGet = new HttpGet(Constant.BASE_URL+Constant.VERIFY_OTP_URL+Constant.PHONE_NO+"&otpnumber="+Constant.OTP+"&deviceid="+Constant.DEVICE_ID);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.VERIFY_OTP_URL+Constant.PHONE_NO+"&otpnumber="+Constant.OTP+"&deviceid="+Constant.DEVICE_ID);

                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        JSONObject json = new JSONObject(jsonstring);

                        if(json.getString("result").equals("true")){
                            verifyOtp[0] = "101";
                        }else {
                            verifyOtp[0] = "0";
                        }

                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return verifyOtp;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;


            case Constant.GET_PROFILE:
                String[] profile = new String[3];

                try {
                    httpGet = new HttpGet(Constant.BASE_URL+Constant.GET_PROFILE_URL+Constant.PHONE_NO);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.GET_PROFILE_URL+Constant.PHONE_NO);
                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        JSONObject json = new JSONObject(jsonstring);
                        ProfileDTO profileDTO = new ProfileDTO();
                        Global.profileList.clear();
                        if(json.getString("result").equals("true")){
                            profile[0] = "1";
                            JSONObject object =  json.getJSONObject("object");

                            profileDTO.setId(object.getString("id"));
                            profileDTO.setFirstName(object.getString("firstName"));
                            profileDTO.setLastName(object.getString("lastName"));
                            profileDTO.setEmailId(object.getString("emailId"));
                            profileDTO.setContactNo(object.getString("contactNo"));
                            profileDTO.setCity(object.getString("city"));
                            profileDTO.setAddress(object.getString("address"));
                            profileDTO.setPincode(object.getString("pincode"));
                            profileDTO.setOtherContactNo(object.getString("otherContactNo"));
                            profileDTO.setDeviceId(object.getString("deviceId"));
                            profileDTO.setStatus(object.getString("status"));

                            Global.profileList.add(profileDTO);
                        }else {
                            profile[0] = "0";
                        }

                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return profile;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;


            case Constant.GET_ALLOCATE_NUMBER:
                String[] all_number = new String[3];

                try {
                    httpGet = new HttpGet(Constant.BASE_URL+Constant.GET_ALLOCATE_NUMBER_URL+Constant.PHONE_NO);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.GET_ALLOCATE_NUMBER_URL+Constant.PHONE_NO);
                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        JSONObject json = new JSONObject(jsonstring);
                        MissedCallDTO missedCallDTO = null;
                        Global.missedcallList.clear();
                        if(json.getString("result").equals("true")){
                            all_number[0] = "1";
                            JSONArray array = json.getJSONArray("list");
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                missedCallDTO = new MissedCallDTO();

                                missedCallDTO.setNumber(object.getString("number"));
                                missedCallDTO.setNickName(object.getString("nickName"));
                                missedCallDTO.setUserId(object.getString("userId"));
                                missedCallDTO.setValidFrom(object.getString("validFrom"));
                                missedCallDTO.setValidTo(object.getString("validTo"));
                                missedCallDTO.setTotalCount(object.getString("totalCount"));
                                missedCallDTO.setCurrentMonthCount(object.getString("currentMonthCount"));
                                missedCallDTO.setId(object.getString("id"));
                                missedCallDTO.setStatusId(object.getString("statusId"));
                                missedCallDTO.setCreatedById(object.getString("createdById"));
                                missedCallDTO.setModifiedById(object.getString("modifiedById"));
                                missedCallDTO.setCreationDate(object.getString("creationDate"));
                                missedCallDTO.setModificationDate(object.getString("modificationDate"));

                                Global.missedcallList.add(missedCallDTO);
                            }
                        }else {
                            all_number[0] = "0";
                        }

                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return all_number;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;

            case Constant.UPDATE_NICK_NAME:
                String[] udateNumber = new String[3];

                try {
                    httpGet = new HttpGet(Constant.BASE_URL+Constant.GET_UPADTE_NICKNAME_URL+Constant.PHONE_NO+"&DidNumber="+Constant.NICKPHONE_NO+"&nickname="+Constant.NICK_NAME);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.GET_UPADTE_NICKNAME_URL+Constant.PHONE_NO+"&DidNumber="+Constant.NICKPHONE_NO+"&nickname="+Constant.NICK_NAME);
                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        JSONObject json = new JSONObject(jsonstring);
                        if(json.getString("result").equals("true")){
                            udateNumber[0] = "101";
                        }else {
                            udateNumber[0] = "0";
                        }
                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return udateNumber;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;

            case Constant.GET_MISSEDCALL:
                String[] get_missed_call = new String[3];

                try {
                    httpGet = new HttpGet(Constant.BASE_URL+Constant.GET_MISSED_CALL_URL+Constant.NICKPHONE_NO);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.GET_MISSED_CALL_URL+Constant.NICKPHONE_NO);

//                    httpGet = new HttpGet(Constant.BASE_URL+Constant.GET_MISSED_CALL_URL+Constant.NICKPHONE_NO+"&startDate="+Constant.CREATIONDATE+"&endDate="+Constant.TILLNOWDATE);
//                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.GET_MISSED_CALL_URL+Constant.NICKPHONE_NO+"&startDate="+Constant.CREATIONDATE+"&endDate="+Constant.TILLNOWDATE);
                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        JSONObject json = new JSONObject(jsonstring);
                        Global.missedList.clear();
                        if(json.getString("result").equals("true")){
                            get_missed_call[0] = "101";

                            JSONArray array = json.getJSONArray("list");
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                MissedCall missedCall = new MissedCall();

                                missedCall.setLogId(object.getString("logId"));
                                missedCall.setSourceNumber(object.getString("sourceNumber"));
                                missedCall.setDidNumber(object.getString("didNumber"));
                                missedCall.setStartTime(object.getString("startTime"));
                                missedCall.setWaitDuration(object.getString("waitDuration"));
                                missedCall.setCallType(object.getString("callType"));
                                missedCall.setPriId(object.getString("priId"));
                                missedCall.setChannelId(object.getString("channelId"));
                                missedCall.setTimestamp(object.getString("timestamp"));
                                missedCall.setTimestampString(object.getString("timestampString"));
                                missedCall.setStartDate(object.getString("startDate"));
                                missedCall.setEndDate(object.getString("endDate"));
                                missedCall.setTotalCount(object.getString("totalCount"));
                                missedCall.setWeekCount(object.getString("weekCount"));
                                missedCall.setTodayCount(object.getString("todayCount"));

                                Global.missedList.add(missedCall);
                            }


                            Log.e("","Size : "+Global.missedList.size());
                        }else {
                            get_missed_call[0] = "0";
                        }
                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return get_missed_call;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;


            case Constant.GET_CAMPAING:
                String[] get_camp = new String[3];

                try {
                    httpGet = new HttpGet(Constant.BASE_URL+Constant.GET_CAMPAING_URL+Constant.PHONE_NO);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.GET_CAMPAING_URL+Constant.PHONE_NO);
                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        Global.setting.clear();
                        JSONObject json = new JSONObject(jsonstring);
                        SettingDTO settingDTO = null;
                        if(json.getString("result").equals("true")){
                            get_camp[0] = "101";

                            JSONObject object = json.getJSONObject("object");
                            settingDTO = new SettingDTO();
                            settingDTO.setNumber(object.getString("number"));
//                            settingDTO.setExpiryDate(object.getString("expiryDate"));
                            String[] date = object.getString("expiryDate").split("T");
                            settingDTO.setExpiryDate(date[0]);
                            settingDTO.setTotalDistinctNumbersCount(object.getString("totalDistinctNumbersCount"));
                            settingDTO.setTotalSmsCount(object.getString("totalSmsCount"));
                            settingDTO.setRemainingSmsCount(object.getString("remainingSmsCount"));

                            Global.setting.add(settingDTO);

                        }else {
                            get_camp[0] = "0";
                        }
                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return get_camp;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;


            case Constant.GET_ACCOUNT:
                String[] get_account = new String[3];

                try {
                    httpGet = new HttpGet(Constant.BASE_URL+Constant.GET_ACCOUNT_URL+Constant.PHONE_NO);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.GET_ACCOUNT_URL+Constant.PHONE_NO);
                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        JSONObject json = new JSONObject(jsonstring);
                        SettingDTO settingDTO = null;
                        Global.setting.clear();
                        if(json.getString("result").equals("true")){
                            get_account[0] = "101";
                            JSONObject object = json.getJSONObject("object");
                            settingDTO = new SettingDTO();
                            settingDTO.setNumber(object.getString("number"));
//                            settingDTO.setExpiryDate(object.getString("expiryDate"));
                            String[] date = object.getString("expiryDate").split("T");
                            settingDTO.setExpiryDate(date[0]);
                            settingDTO.setTotalDistinctNumbersCount(object.getString("totalDistinctNumbersCount"));
                            settingDTO.setTotalSmsCount(object.getString("totalSmsCount"));
                            settingDTO.setRemainingSmsCount(object.getString("remainingSmsCount"));

                            Global.setting.add(settingDTO);
                        }else {
                            get_account[0] = "0";
                        }
                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return get_account;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;


            case Constant.SEND_SMS:
                String[] send_sms = new String[3];

                try {
                    httpGet = new HttpGet(Constant.BASE_URL+Constant.SEND_SMS_URL+Constant.PHONE_NO+"&textMessage="+Constant.MESSAGE_TXT+"&numberFrom="+Constant.START_VALUE+"&numberTo="+Constant.END_VALUE);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.SEND_SMS_URL+Constant.PHONE_NO+"&textMessage="+Constant.MESSAGE_TXT+"&numberFrom="+Constant.START_VALUE+"&numberTo="+Constant.END_VALUE);
                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        JSONObject json = new JSONObject(jsonstring);
                        if(json.getString("result").equals("true")){
                            send_sms[0] = "102";
                        }else {
                            send_sms[0] = "0";
                        }
                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return send_sms;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;

            case Constant.GET_MISSED_DETAIL:
                String[] get_msd = new String[3];

                try {
                    httpGet = new HttpGet(Constant.BASE_URL+Constant.GET_MISSED_DETAIL_URL+Constant.NICKPHONE_NO+"&SourceNumber="+Constant.SOURCE_NUMBER);
                    Log.e("URL", "Map" + Constant.BASE_URL+Constant.GET_MISSED_DETAIL_URL+Constant.NICKPHONE_NO+"&SourceNumber="+Constant.SOURCE_NUMBER);
                    try {
                        httpResponse = httpclient.execute(httpGet);
                        Log.e("", "httpResponse...." + httpResponse);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                        String jsonstring = reader.readLine();
                        Log.e("", "jsonstring data.. " + jsonstring);
                        JSONObject json = new JSONObject(jsonstring);
                        Global.missed_detailList.clear();
                        if(json.getString("result").equals("true")){
                            get_msd[0] = "101";


                            JSONArray array = json.getJSONArray("list");
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                MissedCall missedCall = new MissedCall();


                                missedCall.setLogId(object.getString("logId"));
                                missedCall.setSourceNumber(object.getString("sourceNumber"));
                                missedCall.setDidNumber(object.getString("didNumber"));
                                missedCall.setStartTime(object.getString("startTime"));
                                missedCall.setWaitDuration(object.getString("waitDuration"));
                                missedCall.setCallType(object.getString("callType"));
                                missedCall.setPriId(object.getString("priId"));
                                missedCall.setChannelId(object.getString("channelId"));
                                missedCall.setTimestamp(object.getString("timestamp"));
                                missedCall.setTimestampString(object.getString("timestampString"));
                                missedCall.setStartDate(object.getString("startDate"));
                                missedCall.setEndDate(object.getString("endDate"));
                                missedCall.setTotalCount(object.getString("totalCount"));
                                missedCall.setWeekCount(object.getString("weekCount"));
                                missedCall.setTodayCount(object.getString("todayCount"));

                                Global.missed_detailList.add(missedCall);
                            }


                            Log.e("","Size : "+Global.missed_detailList.size());

                        }else {
                            get_msd[0] = "0";
                        }
                        Log.e("", "json data.. " + json);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                        Log.e("", "e1  " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("", "e2  " + e);
                    }
                    return get_msd;
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Log.e("", "e3  " + e);
                }

                break;

            case Constant.UPDATE_PROFILE:
                String[] updateprofile = new String[3];
                httppost = new HttpPost("http://app.rajhans.digital/api/Webservice/UpdateProfile");
                try {
                    try {

                        jsonData.accumulate("OtherContactNo", Constant.OTHER_NUMBER);
                        jsonData.accumulate("ContactNo", Constant.PHONE_NO);

                        Log.e("", "URL " + Constant.UPDATE_PROFILE_URL);
                        Log.e("Json : ", "" + jsonData.toString(5));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 = httpclient.execute(httppost);
                            if (response1 != null) {
                                Log.e("", "responce");
                                jsonData.has("success");
                            } else {
                                Log.e("", "Null responce");
                            }
                            response1.getStatusLine().getStatusCode();

                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response1.getEntity().getContent(), "UTF-8"));
                    String jsonstring = reader.readLine();
                    Log.e("", "jsonstring data.. " + jsonstring);
                    JSONObject json = new JSONObject(jsonstring);
                    if(json.getString("result").equals("true")){
                        updateprofile[0] = "101";
                    }else {
                        updateprofile[0] = "0";
                    }


                    return updateprofile;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;




            default:
                break;
        }
        return null;
    }


    @Override
    protected void onPostExecute(String[] result) {
        if (dialog.isShowing()) {
            dialog.cancel();
        }
        try {
            ((RequestReceiver) mContext).requestFinished(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }

    public boolean errors_occurred() {
        return this.error_flag;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    private void releaseListMemory() {
    }


    public void setAction(int action) {
        WebserviceHelper.action = action;
    }

}
