package com.alternance.etna.agenda.Class;

import com.alternance.etna.agenda.models.EventEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thibault on 10/04/2018.
 */

public class Day implements Serializable {
    private String mName;
    private Date mDate;
    private List<EventEntity> mEventList = new ArrayList<>();

    public Day(){}

    public Day(Date date){
        this.mDate = date;
    }

    public Day(String name, Date date, List<EventEntity> eventList){
        this(date);
        this.mName = name;
        this.mEventList = eventList;
    }

    public String getName(){return this.mName;}
    public void setName(String name){this.mName = name;}

    public Date getDate(){return this.mDate;}
    public void setDate(Date date){this.mDate = date;}

    public List<EventEntity> getEventList(){return this.mEventList;}
    public void setEventList(List<EventEntity> eventList){this.mEventList = eventList;}
}
