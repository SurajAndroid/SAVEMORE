package utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class MarshMallowPermission {

    public static final int RECORD_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    public static final int LOCATION_PERMISSION_REQUEST_CODE=4;
    public static final int CALL_PERMISSION_REQUEST_CODE=5;
    Activity activity;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissionForRecord(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermissionForExternalStorage(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermissionForCamera(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }


    public boolean checkPermissionForLocation(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForCall(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)){
            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CALL_PHONE},CALL_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean checkPermissionForCall(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }




    public void requestPermissionForRecord(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)){
            Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.RECORD_AUDIO},RECORD_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForExternalStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)){
            Toast.makeText(activity, "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForCamera(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)){
            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
        }
    }


    public void requestPermissionForLocation(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForSms(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECEIVE_SMS)  && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS)){
            Toast.makeText(activity, "Sms permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestPermissionForReadCallLog(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CALL_LOG)){
            Toast.makeText(activity, "Sms permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.RECEIVE_SMS},LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean checkPermissionForReadCallLog(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermissionForSms(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermissionForSmsReed(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

}