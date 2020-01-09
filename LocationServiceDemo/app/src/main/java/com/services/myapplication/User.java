package com.services.myapplication;

import java.util.List;

public class User {

    public String userid;
    public String session;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public List<UserLocation> getLocs() {
        return locs;
    }

    public void setLocs(List<UserLocation> locs) {
        this.locs = locs;
    }

    public List<UserLocation> locs;

}
