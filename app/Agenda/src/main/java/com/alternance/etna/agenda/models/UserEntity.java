package com.alternance.etna.agenda.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "UserId",
        "FirstName",
        "LastName",
        "Login",
        "Password",
        "Birthday",
        "Number"
})
public class UserEntity {

    boolean isNew = true;

    @JsonProperty("UserId")
    int UserId;
    @JsonProperty("FirstName")
    String  FirstName;
    @JsonProperty("LastName")
    String  LastName;
    @JsonProperty("Login")
    String  Login;
    @JsonProperty("Password")
    String Password;
    @JsonProperty("Birthday")
    Date Birthday;
    @JsonProperty("Number")
    String Number;

    public UserEntity()
    {
    }

    public UserEntity(int id, String firstName, String lastName){
        UserId = id;
        FirstName = firstName;
        LastName = lastName;
    }


    public String getLogin()
    {
        return this.Login;
    }

    public String getPassword()
    {
        return this.Password;
    }

    public String getFirstName()
    {
        return this.FirstName;
    }

    public String getLastName()
    {
        return this.LastName;
    }

    public int getUserId()
    {
        return this.UserId;
    }

    public Date getBirthday() { return this.Birthday; }

    public String getNumber() { return this.Number; }


    public void setUserId(int userId)
    {
        this.UserId = userId;
    }

    public void setLogin(String username)
    {
        this.Login = username;
    }

    public void setPassword(String password)
    {
        this.Password = password;
    }

    public void setFirstName(String firstname)
    {
        this.FirstName = firstname;
    }

    public void setLastName(String lastname)
    {
        this.LastName = lastname;
    }

    public void  setBirthday(Date birthday) { this.Birthday = birthday; }

    public void setNumber(String number) { this.Number = number; }
}
