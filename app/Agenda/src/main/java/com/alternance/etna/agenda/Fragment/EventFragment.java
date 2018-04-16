//package com.alternance.etna.agenda.Fragment;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.provider.CalendarContract;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.alternance.etna.agenda.Class.Constant;
//import com.alternance.etna.agenda.Class.EventEntityComparator;
//import com.alternance.etna.agenda.Popup.CreateEventActivity;
//import com.alternance.etna.agenda.Popup.UpdateEventActivity;
//import com.alternance.etna.agenda.R;
//import com.alternance.etna.agenda.models.EventEntity;
//
//import java.sql.Time;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import static com.alternance.etna.agenda.Class.Constant.FORMAT_DATE_DISPLAYED;
//import static com.alternance.etna.agenda.Class.Constant.FORMAT_DATE_STORED;
//import static com.alternance.etna.agenda.Class.Constant.FORMAT_TIME;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class EventFragment extends Fragment {
//
//    List<EventEntity> eventList = new ArrayList<>();
//    TextView todayTitle;
//    ListView eventListView;
//    CustomAdapter customAdapter;
//
//    public EventFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_event, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        try {
//            todayTitle = view.findViewById(R.id.eventTodayTitle);
//            eventListView = view.findViewById(R.id.eventListView);
//            customAdapter = new CustomAdapter();
//
//            //Affichage de la date du jour
//            SimpleDateFormat sdf = new SimpleDateFormat("EEEE d MMMM yyyy"); //heure :  HH:mm
//            String currentDateandTime = sdf.format(new Date());
////            todayTitle.setText(getString(R.string.EventFragment_today_title) + ": " + currentDateandTime);
//            todayTitle.setText(currentDateandTime.substring(0,1).toUpperCase() + currentDateandTime.substring(1));
//
//            //Création de la liste d'evenements (Provisoir)
//
//            eventList.clear();
//            eventList.add(new EventEntity(eventList.size(),"Rdv Médecin", "Rumatisme du Mardi", new Date(new SimpleDateFormat(FORMAT_DATE_STORED).parse("01/04/2018").getTime()), new Time(new SimpleDateFormat(FORMAT_TIME).parse("14:00").getTime())));
//            eventList.add(new EventEntity(eventList.size(),"Rdv Garage", "Voiture cassé", new Date(new SimpleDateFormat(FORMAT_DATE_STORED).parse("05/04/2018").getTime()), new Time(new SimpleDateFormat(FORMAT_TIME).parse("14:30").getTime())));
//            eventList.add(new EventEntity(eventList.size(),"Rdv Moto GP", "Devant la TV", new Date(new SimpleDateFormat(FORMAT_DATE_STORED).parse("10/04/2018").getTime()), new Time(new SimpleDateFormat(FORMAT_TIME).parse("15:00").getTime())));
//            eventList.add(new EventEntity(eventList.size(),"Rdv Balade", "A nous les oiseaux", new Date(new SimpleDateFormat(FORMAT_DATE_STORED).parse("15/04/2018").getTime()), new Time(new SimpleDateFormat(FORMAT_TIME).parse("15:30").getTime())));
//            eventList.add(new EventEntity(eventList.size(),"Rdv Ecole", "Allez chercher les enfants", new Date(new SimpleDateFormat(FORMAT_DATE_STORED).parse("20/04/2018").getTime()), new Time(new SimpleDateFormat(FORMAT_TIME).parse("16:00").getTime())));
//            eventList.add(new EventEntity(eventList.size(),"Rdv Gouter", "Gateaux & vienoiseries", new Date(new SimpleDateFormat(FORMAT_DATE_STORED).parse("25/04/2018").getTime()), new Time(new SimpleDateFormat(FORMAT_TIME).parse("16:30").getTime())));
//            eventList.add(new EventEntity(eventList.size(),"Rdv Test", "Test de test", new Date(new SimpleDateFormat(FORMAT_DATE_STORED).parse("01/04/2018").getTime()), new Time(new SimpleDateFormat(FORMAT_TIME).parse("14:30").getTime())));
//
//            Collections.sort(eventList, new EventEntityComparator());
//
//            Update();
//
//            //Lorsque l'on click sur le bouton "+", on lance la popup d'ajout d'evenement
//            ImageButton eventAdd = view.findViewById(R.id.eventAddButton);
//            eventAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivityForResult(new Intent(getActivity(), CreateEventActivity.class), 999);
//                }
//            });
//        }
//        catch (Exception exp){
//            Log.e("Exception", "EventFragment:onViewCreated => " + exp.getMessage());
//            //Toast.makeText(getContext(), "EventFragment:onViewCreated", Toast.LENGTH_LONG);
//        }
//    }
//
//    /*
//     * Cette fonction permet de rafraichir la liste d'evenements
//     */
//    private  void Update(){
//        try {
//            eventListView.setAdapter(customAdapter);
//        }
//        catch (Exception exp){
//            Log.e("Exception", "EventFragment:Update => " + exp.getMessage());
//            //Toast.makeText(getContext(), "EventFragment:Update", Toast.LENGTH_LONG);
//        }
//    }
//
//    /*
//     * Cette classe indique comment remplir chaque element personalisé de notre liste d'evenement
//     */
//        class CustomAdapter extends BaseAdapter {
//
//            @Override
//            public int getCount() {
//                return eventList.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup parent) {
//                try {
//                    //on donne le layout a instantier pour chaque element de la liste
//                    view = getLayoutInflater().inflate(R.layout.layout_event_item, null);
//
//                    //récupération des elements de la vue (layout)
//                    final TextView eventDate = view.findViewById(R.id.eventDate);
//                    final TextView eventTime = view.findViewById(R.id.eventTime);
//                    final TextView eventTitle = view.findViewById(R.id.eventTitle);
//                    final TextView eventDescription = view.findViewById(R.id.eventDescription);
//                    final TextView eventId = view.findViewById(R.id.eventId);
//                    ImageButton eventEdit = view.findViewById(R.id.eventEditButton);
//
//                    //On affecte des valeurs au elements du layout
//                    eventTitle.setText(eventList.get(i).getTitle());
//                    eventDescription.setText(eventList.get(i).getDescription());
////                    eventDate.setText(eventList.get(i).getStartDate().toString());
//                    String date = new SimpleDateFormat(FORMAT_DATE_DISPLAYED, Locale.FRANCE).format(eventList.get(i).getStartDate());
//                    eventDate.setText(date.substring(0,1).toUpperCase() + date.substring(1));
////                    eventTime.setText(eventList.get(i).getStartTime().toString());
//                    eventTime.setText(new SimpleDateFormat(FORMAT_TIME).format(eventList.get(i).getStartTime()));
//                    eventId.setText(String.valueOf(i));
//
//                    //Si on appuie sur le bouton edit de l'evenement, on envoi ses informations a la popup de modification d'évenement
//                    eventEdit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Date startDate = null;
//                            Time startTime = null;
//                            try {
//                                startDate = new Date(new SimpleDateFormat(FORMAT_DATE_DISPLAYED).parse(eventDate.getText().toString()).getTime());
//                                startTime = new Time(new SimpleDateFormat(FORMAT_TIME).parse(eventTime.getText().toString()).getTime());
//                            }catch (Exception exp) {Log.e("Exception", "EventFragment:CustomAdapter:getView:onClick => " + exp.getMessage());}
//                            EventEntity eventEntity = new EventEntity(
//                                    Integer.parseInt(eventId.getText().toString()),
//                                    eventTitle.getText().toString(),
//                                    eventDescription.getText().toString(),
//                                    startDate,
//                                    startTime
//                                    );
//                            Intent i = new Intent(getActivity(), UpdateEventActivity.class);
//                            i.putExtra("EventEntity", eventEntity);
//                            startActivityForResult(i, 998);
//                        }
//                    });
//                }
//                catch (Exception exp){
//                    Log.e("Exception", "EventFragment:CustomAdapter => " + exp.getMessage());
//                    //Toast.makeText(getContext(), "EventFragment:CustomAdapter", Toast.LENGTH_LONG);
//                }
//                return view;
//            }
//        }
//
//    /*
//     * Cette fonction gère les retours des Intents appelés avec la méthode "startActivityForResult()"
//     * Comme c'est le cas pour CreateEvent, UpdateEvent et DeleteEvent
//     */
//        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data){
//            try {
//                //Création d'un evenement
//                if (requestCode == 999 && resultCode == Constant.ACTION_CREATE && data != null) {
//                    EventEntity event = (EventEntity)data.getSerializableExtra("EventEntity");
//                    Integer id = event.getId();
//                    String title = event.getTitle();
//                    String description = event.getDescription();
//                    Date startDate = event.getStartDate();
//                    Time startTime = event.getStartTime();
//                    eventList.add(new EventEntity(id, title.equals(null) ? "" : title, description.equals(null) ? "" : description, startDate.equals(null) ? null : startDate, startTime.equals(null) ? null : startTime));
////                    addEventToAndroidCalendar();
//                }
//                if (requestCode == 998 && data != null) {
//                    //Modification d'un evenement
//                    if (resultCode == Constant.ACTION_UPDATE) {
//                        EventEntity event = (EventEntity)data.getSerializableExtra("EventEntity");
//                        Integer id = event.getId();
//                        String title = event.getTitle();
//                        String description = event.getDescription();
//                        Date startDate = event.getStartDate();
//                        Time startTime = event.getStartTime();
//                        eventList.set(id, new EventEntity(id, title.equals(null) ? "" : title, description.equals(null) ? "" : description, startDate.equals(null) ? null : startDate, startTime.equals(null) ? null : startTime));
//                    }
//                    //Suppression d'un evenement
//                    else if (resultCode == Constant.ACTION_DELETE) {
//                        try {
//                            EventEntity event = (EventEntity) data.getSerializableExtra("EventEntity");
////                            int id = event.getId(); //l'id est en final sinon le remove ne fonctionne pas
//                            eventList.remove(event.getId());
//                        }
//                        catch (Exception exp){Log.e("Exception", "EventFragment:onActivityResult:DELETE => " + exp.getMessage());
//                        }
//                    }
//                }
//                //Après modification de la liste d'évenement, on raffraichi l'affichage
//                Update();
//            }
//            catch (Exception exp){
//                Log.e("Exception", "EventFragment:onActivityResult => " + exp.getMessage());
//                //Toast.makeText(getContext(), "EventFragment:onActivityResult", Toast.LENGTH_LONG);
//            }
//        }
//
//    public void addEventToAndroidCalendar() {
//        try {
////            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
////                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CALENDAR}, 1);
////            }
////
////            ContentResolver cr = getContext().getContentResolver();
////            ContentValues contentValues = new ContentValues();
////
////            MyCalendar beginTime = MyCalendar.getInstance();
////            beginTime.set(2018, 04, 10, 9, 30);
////
////            MyCalendar endTime = MyCalendar.getInstance();
////            endTime.set(2018, 04, 10, 10, 30);
////
////            ContentValues values = new ContentValues();
////            values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
////            values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
////            values.put(CalendarContract.Events.TITLE, "Tech Stores");
////            values.put(CalendarContract.Events.DESCRIPTION, "Successful Startups");
////            values.put(CalendarContract.Events.CALENDAR_ID, 2);
////            values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/London");
////            values.put(CalendarContract.Events.EVENT_LOCATION, "London");
////            values.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, "1");
////            values.put(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, "1");
////
////            cr.insert(CalendarContract.Events.CONTENT_URI, values);
//
//            Intent intent = new Intent(Intent.ACTION_INSERT);
//            intent.setType("vnd.android.cursor.item/event");
//
//            Calendar cal = Calendar.getInstance();
//            long startTime = cal.getTimeInMillis();
//            long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;
//
//            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
//            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
//
//            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//
//            //This is Information about Calender Event.
//            intent.putExtra(CalendarContract.Events.TITLE, "Siddharth Birthday");
//            intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a description");
//            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
//            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
//
//            startActivity(intent);
//
//        }
//        catch (Exception exp){
//            Log.e("Exception", "EventFragment:addEventToAndroidCalendar => " + exp.getMessage());
//            //Toast.makeText(getContext(), "EventFragment:onActivityResult", Toast.LENGTH_LONG);
//        }
//    }
/////*
//// * Cette classe permet de trier la liste d'évenement
//// */
////    public class EventEntityComparator implements Comparator<EventEntity> {
////        public int compare(EventEntity event1, EventEntity event2) {
////            int result =  event1.getStartDate().compareTo(event2.getStartDate());
////            if(result == 0){
////                result = event1.getStartTime().compareTo(event2.getStartTime());
////            }
////            return result;
////        }
////    }
//
//}
