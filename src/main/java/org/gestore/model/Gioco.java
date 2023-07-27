package org.gestore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Gioco implements OggettoGestore
{
    public static final String[] TAB_FIELDS = new String[] {"Nome", "Anno", "MP", "Genere"};

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("anno")
    private int anno;

    @JsonProperty("multigiocatore")
    private boolean multigiocatore;

    @JsonProperty("genere")
    private String genere;

    @JsonProperty("minGiocatori")
    private int minGiocatori;

    @JsonProperty("maxGiocatori")
    private int maxGiocatori;

    @JsonProperty("autori")
    private List<String> autori;


    public Gioco()
    {
    }

    public Gioco(String nome, int anno, List<String> autori, boolean multigiocatore, int minGiocatori, int maxGiocatori, String genere)
    {
        this.nome = nome;
        this.anno = anno;
        this.autori = autori;
        this.multigiocatore = multigiocatore;
        this.minGiocatori = minGiocatori;
        this.maxGiocatori = maxGiocatori;
        this.genere = genere;
    }

    public String getNome() {return nome;}
    public int getAnno() {return anno;}
    public List<String> getAutori() {return autori;}
    public boolean isMultigiocatore() {return multigiocatore;}
    public int getMinGiocatori() {return minGiocatori;}
    public int getMaxGiocatori() {return maxGiocatori;}
    public String getGenere() {return genere;}

    public boolean isAutore(String nome, String cognome)
    {
        return autori.contains(nome + " " + cognome);
    }

    @Override
    public boolean equals(Object gioco)
    {
        if(!(gioco instanceof Gioco))
            return false;

        Gioco g = (Gioco) gioco;

        return (
            g.nome.equals(this.nome) &&
            g.genere.equals(this.genere) &&
            g.anno == this.anno &&
            g.minGiocatori == this.minGiocatori &&
            g.maxGiocatori == this.maxGiocatori
        );
    }

    @Override
    public String toString()
    {
        return nome;
    }

    @Override
    public String[] toTabFields()
    {
        return new String[] {
            nome,
            Integer.toString(anno),
            multigiocatore ? "Si" : "No",
            genere
        };
    }

    @Override
    public String[] toStringFields()
    {
        return new String[] {
            nome,
            Integer.toString(anno),
            autori.toString(),
            multigiocatore ? "Si" : "No",
            Integer.toString(minGiocatori),
            Integer.toString(maxGiocatori),
            genere
        };
    }
}