package com.alternance.etna.agenda.Class;

import android.util.Log;

import com.alternance.etna.agenda.models.EventEntity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Thibault on 10/04/2018.
 */

public class MyCalendar implements Serializable {
    private String mName = "";
    private List<Day> mDayList = new ArrayList<>();
    private List<EventEntity> mEventList = new ArrayList<>();

    public MyCalendar(){}

    public MyCalendar(String name, List<EventEntity> eventList)
    {
        this.mName = name;
//        this.setEventList(eventList);
//        this.mEventList.clear();
//        this.mEventList.addAll(eventList);
//        this.mDayList = SortEventList(this.mEventList);
    }

    public String getName(){ return this.mName; }
    public void setName(String name){ this.mName = name; }

    public List<Day> getDayList(){return this.mDayList;}
    public void setDayList(List<Day> dayList){this.mDayList = dayList;}

    public void setEventList(List<EventEntity> eventList)
    {
        this.mEventList.clear();
        this.mEventList.addAll(eventList);
        this.mDayList = SortEventList(this.mEventList);
    }

    public void AddElement(EventEntity eventEntity)
    {
        try {
            eventEntity.setId(mEventList.size()); //Id a modifier par l'id incrémenté de la bdd
            this.mEventList.add(eventEntity);
            this.mDayList = SortEventList(this.mEventList);
        }
        catch (Exception exp){
        }
    }

    public void UpdateElement(EventEntity eventEntity)
    {
        try {
            int i = 0;
            //On recherche la position de l'element dans la liste a l'aide de son id
            for (EventEntity e : mEventList) {
                if (e.getId() == eventEntity.getId()) {
                    this.mEventList.set(i, eventEntity);
                    break;
                }
                i++;
            }
            this.mDayList = SortEventList(this.mEventList);
        }
        catch (Exception exp){
        }
    }

    public void DeleteElement(EventEntity eventEntity)
    {
        try {
            int i = 0;
            //On recherche la position de l'element dans la liste a l'aide de son id
            for (EventEntity e : mEventList) {
                if (e.getId() == eventEntity.getId()) {
                    this.mEventList.remove(i);
                    break;
                }
                i++;
            }
            this.mDayList = SortEventList(this.mEventList);
        }
        catch (Exception exp){
        }
    }

    /*
     * Cette classe permet de trier les evenements et de créer une liste de jours d'evenement
     */
    private List<Day> SortEventList(List<EventEntity> eventList)
    {
        try {
            //On clean la liste d'évenements pour etre sur de ne pas concaténer les evenements
            mDayList.clear();
            Day day = null;
            //On classe les evenements par ordre chronologique
            Collections.sort(eventList, new EventEntityComparator());
            //On sépare les evenements par jour et on créer une liste de jours
            for (EventEntity e : eventList) {
                if (day == null) { //Premier element traité
                    day = new Day(e.getStartDate());
                    day.getEventList().add(e);
                }
                else {
//                    if (day.getDate().equals(e.getStartDate()))
                    if (IsEqual(day.getDate(), e.getStartDate()))
                        day.getEventList().add(e);
                    else //nouveau jour
                    {
                        mDayList.add(day);
                        day = new Day(e.getStartDate());
                        day.getEventList().add(e);
                    }
                }
            }
            mDayList.add(day);
        }
        catch (Exception exp){
        }
        return mDayList;
    }

    private boolean IsEqual(Date date1, Date date2)
    {
        try {
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            c1.setTime(date1);
            c2.setTime(date2);

            if (       (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                    && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                    && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)))
                return true;
        }
        catch (Exception exp){
            Log.e("Exception", "MyCalendar:IsEqual() => " + exp.getMessage());
        }
        return false;
    }

}
