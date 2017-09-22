package utils;

import java.util.ArrayList;

import DTOs.CallLog;
import DTOs.MissedCall;
import DTOs.MissedCallDTO;
import DTOs.MissedCount;
import DTOs.ProfileDTO;
import DTOs.SettingDTO;


/**
 * Created by Suraj shakya on 1/3/17.
 */
public class Global {
    public static ArrayList<CallLog> callLogList = new ArrayList<>();

    public static ArrayList<ProfileDTO> profileList = new ArrayList<>();
    public static ArrayList<MissedCallDTO> missedcallList = new ArrayList<>();
    public static ArrayList<MissedCall> missedList = new ArrayList<>();
    public static ArrayList<MissedCall> missed_detailList = new ArrayList<>();
    public static ArrayList<MissedCount> missedcount = new ArrayList<>();
    public static ArrayList<SettingDTO> setting = new ArrayList<>();
}
