//package com.alternance.etna.agenda.Class;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Fragment;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.alternance.etna.agenda.Popup.UpdateEventActivity;
//import com.alternance.etna.agenda.R;
//import com.alternance.etna.agenda.models.EventEntity;
//
//import java.sql.Time;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Locale;
//import java.util.TreeSet;
//
//import static android.support.v4.content.ContextCompat.startActivity;
//import static com.alternance.etna.agenda.Class.Constant.FORMAT_DATE_DISPLAYED;
//import static com.alternance.etna.agenda.Class.Constant.FORMAT_TIME;
//
///**
// * Created by Thibault on 12/04/2018.
// */
//public class CustomCalendarAdapter extends BaseAdapter {
//
//    private static final int TYPE_ITEM = 0;
//    private static final int TYPE_SEPARATOR = 1;
//
//    private int mItemFocued;
//
//    private ArrayList<EventEntity> mEvents = new ArrayList<>();
//    private TreeSet<Integer> sectionHeader = new TreeSet<>();
//    private Boolean mIsEditable;
//    private LayoutInflater mInflater;
//
//    public CustomCalendarAdapter(Context context, Boolean isEditable) {
//        mInflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.mIsEditable = isEditable;
//        mItemFocued = 0;
//    }
//
//    public int GetItemFocused()
//    {
//        return this.mItemFocued;
//    }
//
//    public void Clear()
//    {
//        mEvents.clear();
//        sectionHeader.clear();
//    }
//
//    public void addItem(final EventEntity item) {
//        mEvents.add(item);
//        notifyDataSetChanged();
//    }
//
//    public void addSectionHeaderItem(final EventEntity item) {
//        mEvents.add(item);
//        sectionHeader.add(mEvents.size() - 1);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
//
//    @Override
//    public int getCount() {
//        return mEvents.size();
//    }
//
//    @Override
//    public EventEntity getItem(int position) {
//        return mEvents.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public View getView(int position, View convertView, final ViewGroup parent) {
//        final ViewHolder holder = new ViewHolder();
//        int rowType = getItemViewType(position);
////        final View view2 = convertView;
//
////        if (convertView == null) {
////            holder = new ViewHolder();
//        switch (rowType) {
//            case TYPE_ITEM:
//                convertView = mInflater.inflate(R.layout.layout_event_item_new, null);
//                holder.eventId = convertView.findViewById(R.id.eventIdNew);
//                holder.eventTitle = convertView.findViewById(R.id.eventTitleNew);
//                holder.eventDescription = convertView.findViewById(R.id.eventDescriptionNew);
////                    holder.eventDate = convertView.findViewById(R.id.eventDateNew);
//                holder.eventTime = convertView.findViewById(R.id.eventTimeNew);
//                holder.eventEditButton = convertView.findViewById(R.id.eventEditButtonNew);
//
//                holder.eventTitle.setText(mEvents.get(position).getTitle());
//                holder.eventDescription.setText(mEvents.get(position).getDescription());
//
//                holder.eventTime.setText(new SimpleDateFormat(FORMAT_TIME).format(mEvents.get(position).getStartTime()));
//                if (this.mIsEditable) {
//                    holder.eventEditButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            try {
//                                Date startDate = new Date(new SimpleDateFormat("EEEE d MMM yyyy").parse(holder.eventDate.getText().toString()).getTime());
//                                Time startTime = new Time(new SimpleDateFormat("HH:mm").parse(holder.eventTime.getText().toString()).getTime());
//                                EventEntity eventEntity = new EventEntity(
//                                    Integer.parseInt(holder.eventId.getText().toString()),
//                                    holder.eventTitle.getText().toString(),
//                                    holder.eventDescription.getText().toString(),
//                                    startDate,
//                                    startTime
//                                );
//                                Intent i = new Intent(parent.getContext(), UpdateEventActivity.class);
//                                i.putExtra("EventEntity", eventEntity);
//                                ((Activity)parent.getContext()).startActivityForResult(i, 998);
////                                startActivity(i);
//                            }
//                            catch (Exception exp){
//                                Log.e("Exception", "UserData:getContactList => " + exp.getMessage());
//                            }
//                        }
//                    });
////                    holder.eventEditButton.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
////                            new AlertDialog.Builder(view2.getContext())
////                                    .setTitle("Title")
////                                    .setMessage("Description")
////                                    .show();
////                        }
////                    });
//
//
////                    holder.eventEditButton. (new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            Date startDate = null;
////                            Time startTime = null;
////                            try {
////                                startDate = new Date(new SimpleDateFormat("EEEE d MMM yyyy").parse(holder.eventDate.getText().toString()).getTime());
////                                startTime = new Time(new SimpleDateFormat("HH:mm").parse(holder.eventTime.getText().toString()).getTime());
////                            }catch (Exception exp) {Log.e("Exception", "EventFragment:CustomAdapter:getView:onClick => " + exp.getMessage());}
////                            EventEntity eventEntity = new EventEntity(
////                                    Integer.parseInt(holder.eventId.getText().toString()),
////                                    holder.eventTitle.getText().toString(),
////                                    holder.eventDescription.getText().toString(),
////                                    startDate,
////                                    startTime
////                            );
////                            new AlertDialog.Builder(convertView.getContext())
////                                    .setTitle(eventEntity.getTitle())
////                                    .setMessage(eventEntity.getDescription())
////                                    .show();
////                            //Intent i ;= new Intent( .getActivity(), UpdateEventActivity.class);
//////                            i.putExtra("EventEntity", eventEntity);
//////                            startActivityForResult(i, 998);
//////                            startActivity(i);
////                        }
////                    });
//                }
//                else
//                    holder.eventEditButton.setVisibility(View.INVISIBLE);
//                break;
//
//            case TYPE_SEPARATOR:
//                convertView = mInflater.inflate(R.layout.layout_day_item, null);
//                holder.dayName = convertView.findViewById(R.id.dayName);
//                String dayName = new SimpleDateFormat(FORMAT_DATE_DISPLAYED, Locale.FRANCE).format(mEvents.get(position).getStartDate());
//
//                //                    if (new Date().compareTo(mData.get(position).getStartDate())==0)
//                if (dayName.equals(new SimpleDateFormat(FORMAT_DATE_DISPLAYED, Locale.FRANCE).format(new Date()))) {
//                    dayName = convertView.getContext().getString(R.string.custom_calendar_adapter_today_text);
//                    mItemFocued = position;
//                }
//                holder.dayName.setText(dayName.substring(0,1).toUpperCase() + dayName.substring(1));
//                break;
//        }
//        convertView.setTag(holder);
////        }
//
//        return convertView;
//    }
//
//    public static class ViewHolder {
//        public TextView dayName;
//        public TextView eventId;
//        public TextView eventTitle;
//        public TextView eventDescription;
//        public TextView eventDate;
//        public TextView eventTime;
//        public ImageButton eventEditButton;
//    }
//}
//
//
////    public class CustomCalendarAdapter extends RecyclerView.Adapter<CustomCalendarAdapter.MyViewHolder> {
////
////        @Override
////        public int getItemCount() {
////            return calendar.size();
////        }
////
////        @Override
////        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
////            View view = inflater.inflate(R.layout.layout_day_item, parent, false);
////            return new MyViewHolder(view);
////        }
////
////        @Override
////        public void onBindViewHolder(MyViewHolder holder, int position) {
////            Day day = calendar.get(position);
////            holder.display(day);
////        }
////
////        public class MyViewHolder extends RecyclerView.ViewHolder {
////
////            private final TextView dayName;
////            private final ListView dayEventList;
////
//////            private Day currentDay;
////
////            public MyViewHolder(final View itemView) {
////                super(itemView);
////
////                dayName = itemView.findViewById(R.id.dayName);
////                dayEventList = itemView.findViewById(R.id.dayEventList);
////
//////                itemView.setOnClickListener(new View.OnClickListener() {
//////                    @Override
//////                    public void onClick(View view) {
//////                        new AlertDialog.Builder(itemView.getContext())
//////                                .setTitle(currentPair.first)
//////                                .setMessage(currentPair.second)
//////                                .show();
//////                    }
//////                });
////            }
////
////            public void display(Day _day) {
////                try {
//////                currentDay = day;
////                    day = _day;
////                    dayName.setText((new SimpleDateFormat("EEEE d MMMM yyyy")).format(_day.getDate()));
////                    dayEventList.setAdapter(customDayAdapter);
////                }
////                catch (Exception exp){}
////            }
////        }
////    }
//
////    class CustomCalendarAdapter extends BaseAdapter {
////
////        private static final int TYPE_ITEM = 0;
////        private static final int TYPE_SEPARATOR = 1;
////
////        private ArrayList<EventEntity> events = new ArrayList<EventEntity>();
////        private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
////
////        private LayoutInflater mInflater;
////
////        public CustomCalendarAdapter(Context context) {
////            mInflater = (LayoutInflater) context
////                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        }
////
////        public void addItem(final EventEntity item) {
////            events.add(item);
////            notifyDataSetChanged();
////        }
////
////        public void addSectionHeaderItem(final EventEntity item) {
////            events.add(item);
////            sectionHeader.add(events.size() - 1);
////            notifyDataSetChanged();
////        }
////
////        @Override
////        public int getItemViewType(int position) {
////            return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
////        }
////
////        @Override
////        public int getViewTypeCount() {
////            return 2;
////        }
////
////        @Override
////        public int getCount() {
////            return events.size();
////        }
////
////        @Override
////        public EventEntity getItem(int position) {
////            return events.get(position);
////        }
////
////        @Override
////        public long getItemId(int position) {
////            return position;
////        }
////
////        public View getView(int position, View convertView, ViewGroup parent) {
////            try {
////                ViewHolder holder = null;
////                int rowType = getItemViewType(position);
////
////                if (convertView == null) {
////                    holder = new ViewHolder();
////                    switch (rowType) {
////                        case TYPE_ITEM:
////                            convertView = mInflater.inflate(R.layout.layout_event_item, null);
////                            holder.title = convertView.findViewById(R.id.eventTitle);
////                            holder.description = convertView.findViewById(R.id.eventDescription);
////                            holder.title.setText(events.get(position).getTitle());
////                            holder.description.setText(events.get(position).getDescription());
////                            break;
////
////                        case TYPE_SEPARATOR:
////                            convertView = mInflater.inflate(R.layout.layout_day_item, null);
////                            holder.name = convertView.findViewById(R.id.dayName);
////                            holder.name.setText(new SimpleDateFormat("EEEE d MMM yyyy", Locale.FRANCE).format(events.get(position).getStartDate()));
////                            break;
////                    }
////                    convertView.setTag(holder);
////                } else {
////                    holder = (ViewHolder) convertView.getTag();
////                }
////
////                return convertView;
////            }
////            catch (Exception exp)
////            {
////                Log.e("Exception", "CalendarFragment:CustomCalendarAdapter:getView => " + exp.getMessage());
////                return convertView;
////            }
////        }
////
////        public class ViewHolder {
////            public TextView name;
////            public TextView title;
////            public TextView description;
////        }
////
//////        @Override
//////        public View getView(int i, View view, ViewGroup parent) {
//////            try {
//////                //on donne le layout a instantier pour chaque element de la liste
//////                view = getLayoutInflater().inflate(R.layout.layout_day_item, null);
//////
//////                //récupération des elements de la vue (layout)
//////                final TextView dayName = view.findViewById(R.id.dayName);
//////                final ListView dayEventList = view.findViewById(R.id.dayEventList);
//////
//////                //On affecte des valeurs au elements du layout
//////                day = calendar.get(i);
//////                dayName.setText((new SimpleDateFormat("EEEE d MMMM yyyy")).format(calendar.get(i).getDate()));
//////                dayEventList.setAdapter(new CustomDayAdapter());
//////            }
//////            catch (Exception exp){
//////                Log.e("Exception", "CustomCalendarAdapter:getView => " + exp.getMessage());
//////                //Toast.makeText(getContext(), "EventFragment:CustomAdapter", Toast.LENGTH_LONG);
//////            }
//////            return view;
//////        }
////    }
//
////    class CustomDayAdapter extends BaseAdapter {
////
////        @Override
////        public int getCount() {
////            return day.getEventList().size();
////        }
////
////        @Override
////        public Object getItem(int position) {
////            return null;
////        }
////
////        @Override
////        public long getItemId(int position) {
////            return 0;
////        }
////
////        @Override
////        public View getView(int i, View view, ViewGroup parent) {
////            try {
////                //on donne le layout a instantier pour chaque element de la liste
////                view = getLayoutInflater().inflate(R.layout.layout_event_item, null);
////
////                //récupération des elements de la vue (layout)
////                final TextView eventDate = view.findViewById(R.id.eventDate);
////                final TextView eventTime = view.findViewById(R.id.eventTime);
////                final TextView eventTitle = view.findViewById(R.id.eventTitle);
////                final TextView eventDescription = view.findViewById(R.id.eventDescription);
////                final TextView eventId = view.findViewById(R.id.eventId);
////                ImageButton eventEdit = view.findViewById(R.id.eventEditButton);
////
////                //On affecte des valeurs au elements du layout
////                eventDate.setVisibility(View.INVISIBLE);
////                eventTime.setText(new SimpleDateFormat("HH:mm").format(day.getEventList().get(i).getStartTime()));
////                eventTitle.setText(day.getEventList().get(i).getTitle());
////                eventDescription.setText(day.getEventList().get(i).getDescription());
////                eventEdit.setVisibility(View.INVISIBLE);
////
//////                String date = new SimpleDateFormat("EEEE d MMM yyyy", Locale.FRANCE).format(eventList.get(i).getStartDate());
//////                eventDate.setText(date.substring(0,1).toUpperCase() + date.substring(1));
////////                    eventTime.setText(eventList.get(i).getStartTime().toString());
//////                eventId.setText(String.valueOf(i));
////
//////                //Si on appuie sur le bouton edit de l'evenement, on envoi ses informations a la popup de modification d'évenement
//////                eventEdit.setOnClickListener(new View.OnClickListener() {
//////                    @Override
//////                    public void onClick(View v) {
//////                        Date startDate = null;
//////                        Time startTime = null;
//////                        try {
//////                            startDate = new Date(new SimpleDateFormat("EEEE d MMM yyyy").parse(eventDate.getText().toString()).getTime());
//////                            startTime = new Time(new SimpleDateFormat(timeFormat).parse(eventTime.getText().toString()).getTime());
//////                        }catch (Exception exp) {Log.e("Exception", "EventFragment:CustomAdapter:getView:onClick => " + exp.getMessage());}
//////                        EventEntity eventEntity = new EventEntity(
//////                                Integer.parseInt(eventId.getText().toString()),
//////                                eventTitle.getText().toString(),
//////                                eventDescription.getText().toString(),
//////                                startDate,
//////                                startTime
//////                        );
//////                        Intent i = new Intent(getActivity(), UpdateEventActivity.class);
//////                        i.putExtra("EventEntity", eventEntity);
//////                        startActivityForResult(i, 998);
//////                    }
//////                });
////            }
////            catch (Exception exp){
////                Log.e("Exception", "EventFragment:CustomAdapter => " + exp.getMessage());
////                //Toast.makeText(getContext(), "EventFragment:CustomAdapter", Toast.LENGTH_LONG);
////            }
////            return view;
////        }
////    }
