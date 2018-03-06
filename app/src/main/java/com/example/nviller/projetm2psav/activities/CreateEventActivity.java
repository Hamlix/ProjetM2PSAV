package com.example.nviller.projetm2psav.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.nviller.projetm2psav.R;
import com.example.nviller.projetm2psav.model.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mouna on 29/01/2018.
 */

public class CreateEventActivity extends AppCompatActivity {

    protected EditText nameEventEditText;
    protected EditText descriptionEditText;
    protected EditText dateDebutEditText;
    protected EditText heureDebutEditText;
    protected EditText dateFinEditText;
    protected EditText heureFinEditText;
    protected EditText adresseEditText;
    protected Button enregistrerButton;
    protected Calendar calendar;

    protected DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("event");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        nameEventEditText = (EditText)findViewById(R.id.activity_create_event_name);
        descriptionEditText = (EditText) findViewById(R.id.activity_create_event_description);
        dateDebutEditText = (EditText) findViewById(R.id.activity_create_event_date_debut);
        heureDebutEditText = (EditText) findViewById(R.id.activity_create_event_heure_debut);
        dateFinEditText = (EditText) findViewById(R.id.activity_create_event_date_fin);
        heureFinEditText = (EditText) findViewById(R.id.activity_create_event_heure_fin);
        adresseEditText = (EditText)findViewById(R.id.activity_create_event_adresse);
        enregistrerButton = (Button) findViewById(R.id.activity_create_event_enregistrer);
        calendar = Calendar.getInstance();

        //Date et heure par défaut
        dateDebutEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
        heureDebutEditText.setText(new SimpleDateFormat("kk:mm").format(calendar.getTime()));
        dateFinEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
        heureFinEditText.setText(new SimpleDateFormat("kk:mm").format(calendar.getTime()));
        calendar.setTime(calendar.getTime());
        calendar.add(Calendar.HOUR, 1);

        dateDebutEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog(dateDebutEditText);
            }
        });

        heureDebutEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog(heureDebutEditText);
            }
        });

        dateFinEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog(dateFinEditText);
            }
        });

        heureFinEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog(heureFinEditText);
            }
        });

        enregistrerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameEvent = nameEventEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                final String debut = eventDatesConverter(heureDebutEditText, dateDebutEditText);
                final String fin = eventDatesConverter(heureFinEditText, dateFinEditText);
                final  String adresse = adresseEditText.getText().toString().trim();

                if (description.isEmpty()){
                    description = "";
                }


                // NOTE : SEATS / PRICE / DESCRIPTION  =>  OPTIONAL

                if (nameEvent.isEmpty() || debut == null || fin == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                    builder.setMessage(R.string.create_event_Enregistrer_error_message)
                            .setTitle(R.string.create_event_submit_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (!firstDateAnteriorToSecond(debut, fin)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                    builder.setMessage(R.string.create_event_submit_date_error_message)
                            .setTitle(R.string.create_event_submit_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    databaseReference.push()
                            .setValue(new Event("", nameEvent, description, debut, fin, adresse).toMap());
                    loadMain();
                }
            }
        });

    }
        public void timeDialog(final EditText editText){
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                editText.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
    }

    public void dateDialog(final EditText editText){
        Calendar currentDate = Calendar.getInstance();
        int cYear = currentDate.get(Calendar.YEAR);
        int cMonth = currentDate.get(Calendar.MONTH);
        int cDay = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectedmonth = selectedmonth + 1;
                editText.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
            }
        }, cYear, cMonth, cDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    private boolean firstDateAnteriorToSecond(String debut, String fin) {
        DateFormat dateFormatter = new SimpleDateFormat("kk:mm dd/MM/yyyy");
        Date start;
        Date end;

        try {
            start = dateFormatter.parse(debut);
            end = dateFormatter.parse(fin);
            if (start.after(end)){
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String eventDatesConverter(EditText time, EditText date){
        DateFormat dateFormatter = new SimpleDateFormat("kk:mm dd/MM/yyyy");
        String tmpTime = time.getText().toString().trim();
        String tmpDate = date.getText().toString().trim();

        if (tmpTime.isEmpty() || tmpDate.isEmpty()){
            return null;
        }

        try {
            Date tmp = dateFormatter.parse(tmpTime + " " + tmpDate);
            return dateFormatter.format(tmp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadMain(){
        Intent intent = new Intent(this, GestionEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}