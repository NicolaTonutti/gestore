package org.gestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Acquisto
{
    @JsonProperty("giocoAcquistato")
    private Gioco giocoAcquistato;

    @JsonProperty("dataAcquisto")
    private long dataAcquisto;


    @JsonIgnore
    private String formatDataAcquisto;

    public Acquisto()
    {
    }

    public Acquisto(Gioco giocoAcquistato, long dataAcquisto)
    {
        this.giocoAcquistato = giocoAcquistato;
        this.dataAcquisto = dataAcquisto;
    }

    public Acquisto(Gioco giocoAcquistato)
    {
        this(giocoAcquistato, System.currentTimeMillis());
    }

    public Gioco getGiocoAcquistato() {return giocoAcquistato;}
    public long getDataAcquisto() {return dataAcquisto;}

    public String formatData()
    {
        if(formatDataAcquisto != null)
            return formatDataAcquisto;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataAcquisto);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        formatDataAcquisto = sdf.format(calendar.getTime());

        return formatDataAcquisto;
    }

    @Override
    public String toString()
    {
        return ( formatData() + ": " + giocoAcquistato );
    }
}