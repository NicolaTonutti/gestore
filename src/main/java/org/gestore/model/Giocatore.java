package org.gestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.gestore.view.BarraStato;

import java.util.ArrayList;
import java.util.List;

import static org.gestore.view.BarraStato.IdentStato.*;

public class Giocatore implements OggettoGestore, User
{
    public static final String[] TAB_FIELDS = new String[] {"Nickname", "Nome", "Cognome"};

    @JsonProperty("nick")
    private String nick;

    @JsonProperty("password")
    private String password;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cognome")
    private String cognome;

    @JsonProperty("acquisti")
    private List<Acquisto> acquisti;

    @JsonProperty("listaDesideri")
    private List<Gioco> desideri;


    public Giocatore()
    {
    }

    public Giocatore(String nome, String cognome, String nick, String password)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.nick = nick;
        this.password = password;

        desideri = new ArrayList<>();
        acquisti = new ArrayList<>();
    }

    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
    public String getNick() {return nick;}

    public void setNick(String nick) {this.nick = nick;}

    @Override
    public String[] toTabFields()
    {
        return new String[] {
            nick,
            nome,
            cognome
        };
    }

    @Override
    public String[] toStringFields()
    {
        return new String[] {
            nome,
            cognome,
            nick,
            (acquisti.isEmpty() ? "Nessuno" : acquisti.toString()),
            (desideri.isEmpty() ? "Nessuno" : desideri.toString())
        };
    }


    @Override
    public void acquista(Gioco gioco)
    {
        desideri.removeIf((g) -> g.equals(gioco));

        Acquisto acquisto = new Acquisto(gioco);
        acquisti.add(acquisto);
        BarraStato.setStato(ON_ELEMENTO_ACQUISTATO);
    }

    @Override
    public void desidera(Gioco gioco)
    {
        if(desideri.contains(gioco))
        {
            BarraStato.setStato(ON_DESIDERIO_ESISTENTE);
            return;
        }

        desideri.add(gioco);
        BarraStato.setStato(ON_DESIDERIO_AGGIUNTO);
    }

    @JsonIgnore
    @Override
    public String getUsername()
    {
        return nick;
    }

    @JsonIgnore
    @Override
    public String getPassword()
    {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAdmin()
    {
        return false;
    }
}