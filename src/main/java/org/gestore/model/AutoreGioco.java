package org.gestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AutoreGioco implements OggettoGestore
{
    public static final String[] TAB_FIELDS = new String[] {"Nome", "Cognome", "Premi"};

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cognome")
    private String cognome;

    @JsonProperty("premiVinti")
    private int premiVinti;

    @JsonProperty("dataNascita")
    private int dataNascita;

    @JsonProperty("bio")
    private String bio;


    @JsonIgnore
    private List<Gioco> giochiCreati;


    public AutoreGioco()
    {
    }

    public AutoreGioco(String nome, String cognome, int dataNascita, String bio, int premiVinti)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.bio = bio;
        this.premiVinti = premiVinti;
    }

    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
    public int getDataNascita() {return dataNascita;}
    public String getBio() {return bio;}
    public int getPremiVinti() {return premiVinti;}
    public void setPremiVinti(int premiVinti) {this.premiVinti = premiVinti;}
    public List<Gioco> getGiochiCreati() {return giochiCreati;}
    public void setGiochiCreati(List<Gioco> giochiCreati) {this.giochiCreati = giochiCreati;}

    @Override
    public String[] toTabFields()
    {
        return new String[] {
            nome,
            cognome,
            Integer.toString(premiVinti)
        };
    }

    @Override
    public String[] toStringFields()
    {
        return new String[] {
            nome,
            cognome,
            Integer.toString(dataNascita),
            bio,
            Integer.toString(premiVinti),
            (giochiCreati == null) ? "Nessuno" : giochiCreati.toString()
        };
    }
}