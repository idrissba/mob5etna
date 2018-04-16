package com.alternance.etna.agenda.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class EventWithGroupEntity implements Serializable {

    private int mEventId;
    private int mUserId;
    private String mTitle;
    private String mDescription;
    private Date mStartDate;
    private Date mEndDate;
    private Time mStartTime;
    private Time mEndTime;
    private String mFirstName;
    private String mLastName;
    boolean mIsNew;
    String  mEventNote;

    public EventWithGroupEntity(){}

    public EventWithGroupEntity(int eventId,int userId, String title, String description, Date startDate, Time startTime)
    {
        this.mEventId = eventId;
        this.mUserId = userId;
        this.mTitle = title;
        this.mDescription = description;
        this.mStartDate = startDate;
        this.mStartTime = startTime;
    }

//    public EventWithGroupEntity(int id, String title, String description, Date startDate, Date endDate, Time startTime, Time endTime) {
//        this(id, title, description, startDate, startTime);
//        this.mEndDate = endDate;
//        this.mEndTime = endTime;
//    }

    public int getEventId(){ return this.mEventId; }
    public void setEventId(int eventId){ this.mEventId = eventId; }

    public int getUserId(){ return this.mUserId; }
    public void setUserId(int userId){ this.mUserId = userId; }

    public String getTitle(){ return mTitle; }
    public void setTitle(String title){ this.mTitle = title; }

    public String getDescription(){ return this.mDescription; }
    public void setDescription(String description){ this.mDescription = description; }

    public Date getStartDate(){ return this.mStartDate; }
    public void setStartDate(Date date){ this.mStartDate = date; }

    public Date getEndDate(){ return this.mEndDate; }
    public void setEndDate(Date date){ this.mEndDate = date; }

    public Time getStartTime(){ return this.mStartTime; }
    public void setStartTime(Time time){ this.mStartTime = time; }

    public Time getEndTime(){ return this.mEndTime; }
    public void setEndTime(Time time){ this.mEndTime = time; }

}
