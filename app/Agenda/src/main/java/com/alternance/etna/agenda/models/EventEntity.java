package com.alternance.etna.agenda.models;

import android.service.quicksettings.Tile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sky on 3/20/18.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "EventId",
        "Title",
        "Description",
        "DateStart",
        "DateEnd"
})
public class EventEntity implements Serializable{

    @JsonProperty("EventId")
    private int mId;
    @JsonProperty("Title")
    private String mTitle;
    @JsonProperty("Description")
    private String mDescription;
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @JsonProperty("DateStart")
    private Date mStartDate;
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @JsonProperty("DateEnd")
    private Date mEndDate;

    public EventEntity(){}

    public EventEntity(int id, String title, String description, Date startDate)
    {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mStartDate = startDate;
    }

    public EventEntity(int id, String title, String description, Date startDate, Date endDate) {
        this(id, title, description, startDate);
        this.mEndDate = endDate;
    }

    public int getId(){ return this.mId; }
    public void setId(int id){ this.mId = id; }

    public String getTitle(){ return mTitle; }
    public void setTitle(String title){ this.mTitle = title; }

    public String getDescription(){ return this.mDescription; }
    public void setDescription(String description){ this.mDescription = description; }

    public Date getStartDate(){ return this.mStartDate; }
    public void setStartDate(Date date){ this.mStartDate = date; }

    public Date getEndDate(){ return this.mEndDate; }
    public void setEndDate(Date date){ this.mEndDate = date; }
}
