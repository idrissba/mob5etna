package com.alternance.etna.agenda.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Created by sky on 3/24/18.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "UserId",
        "FirstName",
        "LastName",
        "Login"
})
public class ContactEntity implements Serializable
{
    boolean mIsNew = true;
    @JsonProperty("UserId")
    private int mUserId;
    @JsonProperty("FirstName")
    private String  mFirstName;
    @JsonProperty("LastName")
    private String  mLastName;
    @JsonProperty("Login")
    private String  mLogin;
    @JsonProperty("Number")
    private String mNumber;
    @JsonProperty("Birthday")
    private String mBirthday;


//    @JsonIgnore
    //  List<ContactEntity> Friends = new ArrayList<>();

    public ContactEntity(){
    }

    public ContactEntity(Integer id, String firstName, String lastName, String number){
        mUserId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mNumber = number;
    }

    public Integer getId(){ return mUserId; }
    public void setId(Integer id){ this.mUserId = id; }

    public String getFirstName(){ return mFirstName; }
    public void setFirstName(String firstName){ this.mFirstName = firstName; }

    public String getLastName(){ return mLastName; }
    public void setLastName(String lastName){ this.mLastName = lastName; }

    public String getNumber(){ return mNumber; }
    public void setNumber(String number){ this.mNumber = number; }

    public String getLogin(){ return mLogin; }
    public void setLogin(String login){ this.mLogin = login; }


}
