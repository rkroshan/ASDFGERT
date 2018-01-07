package com.rajatv.surajv.roshank.sac.dashboard;

/**
 * Created by CREATOR on 11/10/2017.
 */

public class RegisterObject {
    private String Buttonkey;
    private String Eventname;
    private String Clubname;

    public String getClubname() {
        return Clubname;
    }

    public void setClubname(String clubname) {
        Clubname = clubname;
    }


    public RegisterObject(String buttonkey, String eventname,String clubname) {
        Buttonkey = buttonkey;
        Eventname = eventname;
        Clubname = clubname;
    }

    public RegisterObject(String buttonkey, String eventname) {
        Buttonkey = buttonkey;
        Eventname = eventname;
    }

    public RegisterObject() {
    }

    public String getButtonkey() {
        return Buttonkey;
    }

    public void setButtonkey(String buttonkey) {
        Buttonkey = buttonkey;
    }

    public String getEventname() {
        return Eventname;
    }

    public void setEventname(String eventname) {
        Eventname = eventname;
    }
}
