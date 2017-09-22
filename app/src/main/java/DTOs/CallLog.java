package DTOs;

/**
 * Created by SURAJ on 7/30/2017.
 */

public class CallLog {

    String number, name, type,date ,duration,datTime;

    public String getDatTime() {
        return datTime;
    }

    public void setDatTime(String datTime) {
        this.datTime = datTime;
    }

    public String getNumber() {

        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
