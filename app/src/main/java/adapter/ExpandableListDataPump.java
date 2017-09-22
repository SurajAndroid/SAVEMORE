package adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SURAJ on 7/27/2017.
 */
public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> missedcall = new ArrayList<String>();
        missedcall.add("Missed Call");

        List<String> campian = new ArrayList<String>();
        campian.add("Send sms");
        campian.add("Uniqe user");
        campian.add("Total Balance(20/50)");

        List<String> account = new ArrayList<String>();
        account.add("Service Year");
        account.add("Licence upto 20/02/2017");
        account.add("Renue Now!");
        account.add("SMS Credits(20)");

        List<String> profile = new ArrayList<String>();
        profile.add("Profile");


        List<String> defoultService = new ArrayList<String>();
        defoultService.add("Defoult Service");


        expandableListDetail.put("Missed Call", missedcall);
        expandableListDetail.put("Campaign", campian);
        expandableListDetail.put("Account", account);
        expandableListDetail.put("Profile", profile);
        expandableListDetail.put("Defoult Service", defoultService);


        return expandableListDetail;
    }
}