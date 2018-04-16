package com.alternance.etna.agenda.Class;

import android.util.Log;

import com.alternance.etna.agenda.models.EventEntity;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by Thibault on 10/04/2018.
 */

/*
 * Cette classe permet de trier la liste d'évenement
 */
public class EventEntityComparator implements Comparator<EventEntity> {
    public int compare(EventEntity event1, EventEntity event2) {
        int result = 0;
        try {
            result = event1.getStartDate().compareTo(event2.getStartDate());
//            if (result == 0) {
//                result = event1.getStartTime().compareTo(event2.getStartTime());
//            }
        }
        catch (Exception exp){
            Log.e("Exception", "EventEntityComparator.compare() => " + exp.getMessage());
        }
        return result;
    }

}
