package com.rajatv.surajv.roshank.sac.SasCouncil;

/**
 * Created by CREATOR on 11/8/2017.
 */

public class SacMemberObject {
    private String index,name,post,Branch,Year,Calling,Whatsapp,facebook,Instagram,Gmail,Quote,POSITION;

    public int getImage_id() {
        return Image_id;
    }

    public void setImage_id(int image_id) {
        Image_id = image_id;
    }

    private int Image_id;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(String POSITION) {
        this.POSITION = POSITION;
    }

    public SacMemberObject(){}

    public SacMemberObject(String indexx,int image_id, String name, String post, String branch, String year, String calling, String whatsapp, String facebook, String instagram, String gmail, String quote,String position) {
        this.name = name;
        this.post = post;
        Image_id = image_id;
        Branch = branch;
        Year = year;
        Calling = calling;
        Whatsapp = whatsapp;
        this.facebook = facebook;
        Instagram = instagram;
        Gmail = gmail;
        Quote = quote;
        POSITION = position;
        index=indexx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getCalling() {
        return Calling;
    }

    public void setCalling(String calling) {
        Calling = calling;
    }

    public String getWhatsapp() {
        return Whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        Whatsapp = whatsapp;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return Instagram;
    }

    public void setInstagram(String instagram) {
        Instagram = instagram;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public String getQuote() {
        return Quote;
    }

    public void setQuote(String quote) {
        Quote = quote;
    }
}
