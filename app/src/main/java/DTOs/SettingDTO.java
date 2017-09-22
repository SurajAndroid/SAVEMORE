package DTOs;

/**
 * Created by SURAJ on 9/15/2017.
 */

public class SettingDTO {

    String number, expiryDate, totalDistinctNumbersCount, totalSmsCount, remainingSmsCount;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getTotalDistinctNumbersCount() {
        return totalDistinctNumbersCount;
    }

    public void setTotalDistinctNumbersCount(String totalDistinctNumbersCount) {
        this.totalDistinctNumbersCount = totalDistinctNumbersCount;
    }

    public String getTotalSmsCount() {
        return totalSmsCount;
    }

    public void setTotalSmsCount(String totalSmsCount) {
        this.totalSmsCount = totalSmsCount;
    }

    public String getRemainingSmsCount() {
        return remainingSmsCount;
    }

    public void setRemainingSmsCount(String remainingSmsCount) {
        this.remainingSmsCount = remainingSmsCount;
    }
}
