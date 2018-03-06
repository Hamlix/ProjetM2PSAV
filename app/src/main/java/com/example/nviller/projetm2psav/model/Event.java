package com.example.nviller.projetm2psav.model;

import com.google.firebase.database.Exclude;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Event {

    public String idEvent;
    public String nameEvent;
    public String description;
    public String debut;
    public String fin;
    public String adresse;

    public static DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    public static DateFormat dateFormatter = new SimpleDateFormat("kk:mm dd/MM/yyyy");

    public Event(){

    }

    public Event(String idEvent, String nameEvent, String description, String debut, String fin, String adresse) {
        this.idEvent = idEvent;
        this.nameEvent = nameEvent;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.adresse = adresse;
    }

    public Event(String nameEvent, String description, String debut, String fin, String adresse) {
        this.nameEvent = nameEvent;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.adresse = adresse;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", nameEvent);
        result.put("description", description);
        result.put("debut", debut);
        result.put("fin", fin);
        result.put("adresse", adresse);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name= ").append(nameEvent).append("\n");
        sb.append("debut= ").append(debut).append("\n");
        sb.append("fin= ").append(fin).append("\n");
        return sb.toString();
    }
}
