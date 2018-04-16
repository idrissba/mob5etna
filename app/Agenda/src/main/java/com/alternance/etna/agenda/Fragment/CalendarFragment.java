package com.alternance.etna.agenda.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.alternance.etna.agenda.Class.*;
import com.alternance.etna.agenda.Manager.UserData;
import com.alternance.etna.agenda.Popup.CreateEventActivity;
import com.alternance.etna.agenda.Popup.UpdateEventActivity;
import com.alternance.etna.agenda.R;
import com.alternance.etna.agenda.models.EventEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.util.Locale;
import java.util.TreeSet;

import static com.alternance.etna.agenda.Class.Constant.*;

public class CalendarFragment extends Fragment {

    public class CalendarTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected Boolean doInBackground(String... params) {
            UserData ud = UserData.getInstance();

//            ud._myContacts = ud.getContactList();
  //          if (ud._myContacts.size() > 0) {
                CalendarFragment.this.mCalendar.setEventList((ud.getEventList()));
    //            return true;
      //      }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean backgroundRet) {

            if (backgroundRet) {
                CalendarFragment.this.Update();
//                ContactFragment.this.mCustomContactAdapter.notifyDataSetChanged();
            }
        }
    }

    private TextView mCalendarTitleTextView;
    private ListView mCalendarListView;
    private ImageButton mCalendarAddEvent;

    private MyCalendar mCalendar;
    private CustomCalendarAdapter mAdapter;
    private Boolean mIsEditable;

    public CalendarFragment() {
        // Required empty public constructor
//        Bundle bundle = getArguments();
//        if (bundle != null)
//            mCalendar = (MyCalendar)bundle.get("MyCalendar");
    }

    public static CalendarFragment MyCalendarFragment(MyCalendar calendar, Boolean isEditable) {
        CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.mCalendar = calendar;
        calendarFragment.mIsEditable = isEditable;
        return calendarFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CalendarFragment.CalendarTask task = new CalendarFragment.CalendarTask();
        task.execute();
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mCalendarTitleTextView = view.findViewById(R.id.calendarTitle);
            mCalendarListView = view.findViewById(R.id.calendarListView);
            mCalendarAddEvent = view.findViewById(R.id.calendarAddButton);

            mAdapter = new CustomCalendarAdapter(getContext(), this.mIsEditable);
            mCalendarTitleTextView.setText(this.mCalendar.getName().substring(0,1).toUpperCase() + this.mCalendar.getName().substring(1));

            Update();

            if (this.mIsEditable) {
                //Lorsque l'on click sur le bouton "+", on lance la popup d'ajout d'evenement
                mCalendarAddEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(getActivity(), CreateEventActivity.class), 999);
                    }
                });
            }
            else
                mCalendarAddEvent.setVisibility(View.INVISIBLE);
        }
        catch (Exception exp){
            Log.e("Exception", "CalendarFragment.onViewCreated() => " + exp.getMessage());
        }
    }

    /*
    * Cette fonction permet de rafraichir la liste d'evenements
    */
    private  void Update(){
        try {
            mAdapter.Clear();
            for (Day d : mCalendar.getDayList())
            {
                mAdapter.addSectionHeaderItem(d.getEventList().get(0));
                for (EventEntity e : d.getEventList())
                {
                    mAdapter.addItem(e);
                }
            }
            mCalendarListView.setAdapter(mAdapter);
            mCalendarListView.setSelection(mAdapter.GetItemFocused());
        }
        catch (Exception exp){
            Log.e("Exception", "CalendarFragment.Update() => " + exp.getMessage());
        }
    }

    /*
     * Cette classe permet de construire chaque item de notre listView
     */
    public class CustomCalendarAdapter extends BaseAdapter {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;

        private ArrayList<EventEntity> mEvents = new ArrayList<>();
        private TreeSet<Integer> sectionHeader = new TreeSet<>();
        private Boolean mIsEditable;
        private LayoutInflater mInflater;
        private int mItemFocused;

        public CustomCalendarAdapter(Context context, Boolean isEditable) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mIsEditable = isEditable;
            mItemFocused = 0;
        }

        public int GetItemFocused()
        {
            return this.mItemFocused;
        }

        public void Clear()
        {
            try {
                mEvents.clear();
                sectionHeader.clear();
            }
            catch (Exception exp){
                Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.Clear() => " + exp.getMessage());
            }
        }

        public void addItem(final EventEntity item) {
            try {
                mEvents.add(item);
                notifyDataSetChanged();
            }
            catch (Exception exp){
                Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.addItem() => " + exp.getMessage());
            }
        }

        public void addSectionHeaderItem(final EventEntity item) {
            try {
                mEvents.add(item);
                sectionHeader.add(mEvents.size() - 1);
                notifyDataSetChanged();
            }
            catch (Exception exp){
                Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.addSectionHeaderItem() => " + exp.getMessage());
            }
        }

        @Override
        public int getItemViewType(int position) {
            return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getCount() {
            return mEvents.size();
        }

        @Override
        public EventEntity getItem(int position) {
            return mEvents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, final ViewGroup parent) {
            final CustomCalendarAdapter.ViewHolder holder;
            int rowType;

            try {
                holder = new CustomCalendarAdapter.ViewHolder();
                rowType = getItemViewType(position);

                switch (rowType) {
                    case TYPE_ITEM:
                        convertView = mInflater.inflate(R.layout.layout_event_item_new, null);
                        holder.eventId = convertView.findViewById(R.id.eventIdNew);
                        holder.eventTitle = convertView.findViewById(R.id.eventTitleNew);
                        holder.eventDescription = convertView.findViewById(R.id.eventDescriptionNew);
                        holder.eventStartDate = convertView.findViewById(R.id.eventStartDateNew);
                        holder.eventStartTime = convertView.findViewById(R.id.eventTimeNew);
                        holder.eventEditButton = convertView.findViewById(R.id.eventEditButtonNew);

                        holder.eventId.setText(String.valueOf(mEvents.get(position).getId()));
                        holder.eventTitle.setText(mEvents.get(position).getTitle());
                        holder.eventDescription.setText(mEvents.get(position).getDescription());
                        holder.eventStartDate.setText(new SimpleDateFormat(FORMAT_DATE_DISPLAYED).format(mEvents.get(position).getStartDate())); //On convertie notre Date en format plus agréable à la lecture
//                        holder.eventStartTime.setText(new SimpleDateFormat(FORMAT_TIME).format(mEvents.get(position).getStartTime())); //On convertir notre Time en format plus agréable à la lecture
                        holder.eventStartTime.setText(new SimpleDateFormat(FORMAT_TIME).format(mEvents.get(position).getStartDate())); //On convertir notre Time en format plus agréable à la lecture
                        if (this.mIsEditable) {
                            holder.eventEditButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        int id = 0;
                                        String title = null;
                                        String description = null;
                                        Date startDate = null;
                                        Time startTime = null;
                                        try {
                                            id = Integer.parseInt(holder.eventId.getText().toString());
                                        } catch (Exception exp) {
                                            Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.getView().onClick().id => " + exp.getMessage());
                                        }
                                        try {
                                            title = holder.eventTitle.getText().toString();
                                        } catch (Exception exp) {
                                            Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.getView().onClick().title => " + exp.getMessage());
                                        }
                                        try {
                                            description = holder.eventDescription.getText().toString();
                                        } catch (Exception exp) {
                                            Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.getView().onClick().description => " + exp.getMessage());
                                        }
                                        try {
                                            startDate = new Date(new SimpleDateFormat(FORMAT_DATE_DISPLAYED+" "+FORMAT_TIME, Locale.getDefault()).parse(holder.eventStartDate.getText().toString()+" "+holder.eventStartTime.getText().toString()).getTime()); //On convertie la date afficher (String) en Date
                                        //ajouter time a startDate
                                        } catch (Exception exp) {
                                            Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.getView().onClick().startDate => " + exp.getMessage());
                                        }
//                                        try {
//                                            startTime = new Time(new SimpleDateFormat(FORMAT_TIME).parse(holder.eventStartTime.getText().toString()).getTime()); // On convertie le temps afficher (String) en Time
//                                        } catch (Exception exp) {
//                                            Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.getView().onClick().startTime => " + exp.getMessage());
//                                        }
                                        EventEntity eventEntity = new EventEntity(
                                                id,
                                                title,
                                                description,
                                                startDate//,
//                                                startTime
                                        );
                                        Intent i = new Intent(getActivity(), UpdateEventActivity.class);
                                        i.putExtra("EventEntity", eventEntity);
                                        startActivityForResult(i, 998);
                                    } catch (Exception exp) {
                                        Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.getView().onClick() => " + exp.getMessage());
                                    }
                                }
                            });

                        } else
                            holder.eventEditButton.setVisibility(View.INVISIBLE);
                        break;

                    case TYPE_SEPARATOR:
                        convertView = mInflater.inflate(R.layout.layout_day_item, null);
                        holder.dayName = convertView.findViewById(R.id.dayName);
                        String dayName = new SimpleDateFormat(FORMAT_DATE_DISPLAYED, Locale.getDefault()).format(mEvents.get(position).getStartDate());

                        //                    if (new Date().compareTo(mData.get(position).getStartDate())==0)
                        if (dayName.equals(new SimpleDateFormat(FORMAT_DATE_DISPLAYED, Locale.getDefault()).format(new Date()))) {
                            dayName = convertView.getContext().getString(R.string.custom_calendar_adapter_today_text);
                            mItemFocused = position;
                        }
                        holder.dayName.setText(dayName.substring(0, 1).toUpperCase() + dayName.substring(1));
                        break;
                }
                convertView.setTag(holder);
            }
            catch (Exception exp){
                Log.e("Exception", "CalendarFragment.CustomCalendarAdapter.getView() => " + exp.getMessage());
            }
            return convertView;
        }

        public  class ViewHolder {
            public TextView dayName;
            public TextView eventId;
            public TextView eventTitle;
            public TextView eventDescription;
            public TextView eventStartDate;
            public TextView eventStartTime;
            public ImageButton eventEditButton;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        try {
            EventEntity event = (EventEntity) data.getSerializableExtra("EventEntity");
            //Création d'un evenement
            if (requestCode == 999 && resultCode == Constant.ACTION_CREATE && data != null) {
                try {
                    Integer id = event.getId();
                    String title = event.getTitle();
                    String description = event.getDescription();
                    Date startDate = event.getStartDate();
//                    Time startTime = event.getStartTime();
//                    this.mCalendar.AddElement(new EventEntity(id, title == null ? "" : title, description == null ? "" : description, startDate == null ? new Date() : startDate, startTime == null ? null : startTime));
                    this.mCalendar.AddElement(new EventEntity(id, title == null ? "" : title, description == null ? "" : description, startDate == null ? new Date() : startDate));
                }
                catch (Exception exp){
                    Log.e("Exception", "CalendarFragment.onActivityResult().CREATE => " + exp.getMessage());
                }
            }
            if (requestCode == 998 && data != null) {
                //Modification d'un evenement
                if (resultCode == ACTION_UPDATE) {
                    try {
                        Integer id = event.getId();
                        String title = event.getTitle();
                        String description = event.getDescription();
                        Date startDate = event.getStartDate();
//                        Time startTime = event.getStartTime();
//                        mCalendar.UpdateElement(new EventEntity(id, title == null ? "" : title, description == null ? "" : description, startDate == null ? new Date() : startDate, startTime == null ? null : startTime));
                        mCalendar.UpdateElement(new EventEntity(id, title == null ? "" : title, description == null ? "" : description, startDate == null ? new Date() : startDate));
                    }
                    catch (Exception exp){
                        Log.e("Exception", "CalendarFragment.onActivityResult().UPDATE => " + exp.getMessage());
                    }
                }
                //Suppression d'un evenement
                else if (resultCode == ACTION_DELETE) {
                    try {
                        mCalendar.DeleteElement(event);
                    }
                    catch (Exception exp){
                        Log.e("Exception", "CalendarFragment.onActivityResult().DELETE => " + exp.getMessage());
                    }
                }
            }
            //Après modification de la liste d'évenement, on raffraichi l'affichage
            Update();
        }
        catch (Exception exp){
            Log.e("Exception", "CalendarFragment.onActivityResult() => " + exp.getMessage());
        }
    }
}

