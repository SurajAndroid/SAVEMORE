package DTOs;

/**
 * Created by SURAJ on 9/12/2017.
 */

public class MissedCall {

    String logId, sourceNumber, didNumber, startTime, waitDuration,
            callType, priId, channelId, timestamp, timestampString, startDate, endDate, todayCount, weekCount, totalCount;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(String todayCount) {
        this.todayCount = todayCount;
    }

    public String getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(String weekCount) {
        this.weekCount = weekCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(String sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public String getDidNumber() {
        return didNumber;
    }

    public void setDidNumber(String didNumber) {
        this.didNumber = didNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getWaitDuration() {
        return waitDuration;
    }

    public void setWaitDuration(String waitDuration) {
        this.waitDuration = waitDuration;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getPriId() {
        return priId;
    }

    public void setPriId(String priId) {
        this.priId = priId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestampString() {
        return timestampString;
    }

    public void setTimestampString(String timestampString) {
        this.timestampString = timestampString;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
