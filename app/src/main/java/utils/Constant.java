package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*Define all constant variables here
 */
public class Constant {

	public static final int GENRATE_OTP = 1;
	public static final int VERIFY_OTP = 2;
	public static final int REGISTER_DEVICE = 3;
	public static final int GET_PROFILE = 4;
	public static final int GET_ALLOCATE_NUMBER = 5;
	public static final int UPDATE_NICK_NAME = 6;
	public static final int GET_MISSEDCALL = 7;
	public static final int GET_CAMPAING = 8;
	public static final int GET_ACCOUNT = 9;
	public static final int SEND_SMS = 10;
	public static final int GET_MISSED_DETAIL = 11;
	public static final int UPDATE_PROFILE = 12;

	public static String BASE_URL ="http://app.rajhans.digital/api/";

	public static String REGISTER_DEVICE_URL ="Webservice/CheckMobileNumber?mobilenumber=";
	public static String GENERATE_OTP_URL ="Webservice/GenerateOtp?mobilenumber=";
	public static String VERIFY_OTP_URL ="Webservice/VerifyOtp?mobileno=";
	public static String GET_PROFILE_URL ="Webservice/GetProfile?mobilenumber=";
	public static String GET_ALLOCATE_NUMBER_URL ="webservice/GetAllotedNumbersList?mobilenumber=";
	public static String GET_UPADTE_NICKNAME_URL ="webservice/UpdateNickname?mobilenumber=";
//	public static String GET_MISSED_CALL_URL ="Webservice/GetMisscallDetailsByDate?didnumber=";
	public static String GET_MISSED_CALL_URL ="webservice/GetTotalMisscallDistinctListWithCount?DidNumber=";

	public static String GET_CAMPAING_URL ="webservice/GetDistinctNumbersAndSmsCount?mobilenumber=";
	public static String GET_ACCOUNT_URL ="webservice/GetPackageDetails?mobilenumber=";
	public static String SEND_SMS_URL ="webservice/SendSmsToUsers?mobileNumber=";
	public static String GET_MISSED_DETAIL_URL ="webservice/GetMisscallDetailsBySourceNumber?DidNumber=";
	public static String UPDATE_PROFILE_URL ="Webservice/UpdateProfile";


	public static String PHONE_NO ="";
	public static String NICKPHONE_NO ="";
	public static String CREATIONDATE ="";
	public static String TILLNOWDATE ="";
	public static String NICK_NAME ="";
	public static String OTP ="";
	public static String DEVICE_ID ="";
	public static String GETDEVICE_ID ="";

	public static String START_VALUE ="";
	public static String END_VALUE ="";
	public static String MESSAGE_TXT ="";
	public static String SOURCE_NUMBER ="";
	public static String OTHER_NUMBER ="";



	public static String EMAIL_PETTERN = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\"\n" +"  + \"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";

	public static boolean emailValidation(String emailtxt) {
		boolean isValid = false;
		CharSequence inputStr = emailtxt;
		Pattern pattern = Pattern.compile(Constant.EMAIL_PETTERN, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean checkInternetConnection(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}

	public static String gerDeviceId(Context context) {
		String android_id = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		return android_id;
	}


	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		params.height = params.height+60;
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	public static String getDeviceName() {
		String str = android.os.Build.MODEL;
		return str;
	}

	public static int getDeviceWidth(Context context) {
		int width;
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		width = displayMetrics.widthPixels;
		return width;
	}


}
