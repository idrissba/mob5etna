package com.alternance.etna.agenda.Popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alternance.etna.agenda.Class.Constant;
import com.alternance.etna.agenda.R;
import com.alternance.etna.agenda.models.EventEntity;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.alternance.etna.agenda.Class.Constant.FORMAT_DATE_STORED;
import static com.alternance.etna.agenda.Class.Constant.FORMAT_TIME;

public class UpdateEventActivity extends Activity {

    EditText mTitle;
    EditText mDescription;
    TextView mId;
    DatePicker mDate;
    TimePicker mTime;
    Button mUpdate;
    Button mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.popup_update_event);

            //Ce block permet de reduire la taille de la fenètre pour lui donner un look de Popup
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = (int) (dm.widthPixels * 0.8);
            int height = (int) (dm.heightPixels * 0.6);
            getWindow().setLayout(width, height);

            //Recupération des elements de la page
            mTitle = findViewById(R.id.updateEventTitle);
            mDescription = findViewById(R.id.updateEventDescription);
            mId = findViewById(R.id.updateEventId);
            mDate = findViewById(R.id.updateEventDatePicker);
            mTime = findViewById(R.id.updateEventTimePicker);
            mTime.setIs24HourView(true);
            mUpdate = findViewById(R.id.updateEventUpdate);
            mUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //Envoi des informations de modification à la page parent
                        //Block moche mais pas trouvé mieux idem Create event
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), mTime.getHour(), mTime.getMinute());
                        EventEntity eventEntity = new EventEntity(
                                Integer.parseInt(mId.getText().toString()),
                                mTitle.getText().toString(),
                                mDescription.getText().toString(),
                                 new Date(calendar.getTimeInMillis())
                                //new Date(new SimpleDateFormat(FORMAT_DATE_STORED, Locale.getDefault()).parse(calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)).getTime()),
                                //new Time(new SimpleDateFormat(FORMAT_TIME).parse(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)).getTime())
                        );
                        Intent i = new Intent();
                        i.putExtra("EventEntity", eventEntity);
                        setResult(Constant.ACTION_UPDATE, i);
                        finish();
                    }
                    catch (Exception exp){
                        Log.e("Exception", "UpdateEventActivity.onCreate().onClick().UPDATE => " + exp.getMessage());
                    }
                }
            });
            mDelete = findViewById(R.id.updateEventDelete);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //Envoi des informations de suppression à la page parent
                        EventEntity eventEntity = new EventEntity(
                                Integer.parseInt(mId.getText().toString()),
                                null,
                                null,
                                null,
                                null
                        );
                        Intent i = new Intent();
                        i.putExtra("EventEntity", eventEntity);
                        setResult(Constant.ACTION_DELETE, i);
                        finish();
                    }
                    catch (Exception exp){
                        Log.e("Exception", "UpdateEventActivity.onCreate().onClick().DELETE => " + exp.getMessage());
                    }
                }
            });

            //Recupération des informations de l'item selectionné pour préremplir le formulaire de modification
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                EventEntity eventEntity = (EventEntity)extras.getSerializable("EventEntity");
                mId.setText(String.valueOf(eventEntity.getId()));
                mTitle.setText(eventEntity.getTitle());
                mDescription.setText(eventEntity.getDescription());

                Calendar calendar = Calendar.getInstance();
                if (eventEntity.getStartDate() != null)
                    calendar.setTimeInMillis(eventEntity.getStartDate().getTime());
                mDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), null);
//                Time startTime = eventEntity.getStartTime();
//                calendar.setTimeInMillis(eventEntity.getStartTime().getTime());
                mTime.setIs24HourView(true);
                mTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                mTime.setMinute(calendar.get(Calendar.MINUTE));
            }
        }
        catch (Exception exp){
            Log.e("Exception", "UpdateEventActivity:onCreate() => " + exp.getMessage());
        }
    }
}
