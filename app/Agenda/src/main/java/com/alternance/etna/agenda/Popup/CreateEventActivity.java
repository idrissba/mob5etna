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
import android.widget.TimePicker;

import com.alternance.etna.agenda.Class.Constant;
import com.alternance.etna.agenda.R;
import com.alternance.etna.agenda.URLS;
import com.alternance.etna.agenda.models.EventEntity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.alternance.etna.agenda.Class.Constant.FORMAT_DATE_STORED;
import static com.alternance.etna.agenda.Class.Constant.FORMAT_TIME;

public class CreateEventActivity extends Activity {

    private EditText mTitle;
    private EditText mDescription;
    private DatePicker mDate;
    private TimePicker mTime;
    private Button mValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.popup_create_event);

            //Ce block permet de reduire la taille de la vue pour lui donner un look de popup
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = (int) (dm.widthPixels * 0.8);
            int height = (int) (dm.heightPixels * 0.6);
            getWindow().setLayout(width, height);

            //Recuperation des elements de notre page
            mTitle = findViewById(R.id.createEventTitle);
            mDescription = findViewById(R.id.createEventDescription);
            mDate = findViewById(R.id.createEventDatePicker);
            mTime = findViewById(R.id.createEventTimePicker);


            //Initialisation des elements de la page
            mTime.setIs24HourView(true);
            mValidate = findViewById(R.id.createEventValidation);
            mValidate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        //Envoi des informations à la page parent
                        //Ce block est tres moche (Date & Time), mais celle la seule facon que j'ai trouvé (pour le moment) pour que les dates soit exact. Sinon le tri des dates ne fonctionne pas
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), mTime.getHour(), mTime.getMinute());
                        EventEntity eventEntity = new EventEntity(
                            0, //cette Id est modifier plus loin dans le code par le bon
                            mTitle.getText().toString(),
                            mDescription.getText().toString(),
                               // new Date(new SimpleDateFormat(FORMAT_DATE_STORED, Locale.getDefault()).parse(calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)).getTime()),
                            new Date(calendar.getTimeInMillis())
                            //new Time(new SimpleDateFormat(FORMAT_TIME).parse(calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)).getTime())
                        );

                        saveOnDistantDatabase();

                        Intent i = new Intent();
                        i.putExtra("EventEntity", eventEntity);
                        setResult(Constant.ACTION_CREATE, i);
                        finish();
                    }
                    catch (Exception exp){
                        Log.e("Exception", "CreateEventActivity.onCreate().onClick() => " + exp.getMessage());
                    }
                }
            });

        }
        catch (Exception exp){
            Log.e("Exception", "CreateEventActivity.onCreate() => " + exp.getMessage());
        }
    }

    private void saveOnDistantDatabase() {
        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.POST,
                URLS.URL_EVENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Une fois l'ajout de l'event, on crée le EventGroup
                        CreateEventGroup(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), mTime.getHour(), mTime.getMinute());

                    params.put("Title",  mTitle.getText().toString());
                    params.put("Date", calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
                    params.put("Description",  mDescription.getText().toString());
                }
                catch (Exception exp){
                    Log.e("Exception", "CreateEventActivity.saveOnDistantDatabase().getParams => " + exp.getMessage());
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjRequest);
    }

    private void CreateEventGroup(final String eventId) {
        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.POST,
                URLS.URL_EVENTGROUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("EventId", eventId);
                //TODO add userID
                params.put("UserId", "16");
                params.put("Status", "0");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjRequest);
    }
}
