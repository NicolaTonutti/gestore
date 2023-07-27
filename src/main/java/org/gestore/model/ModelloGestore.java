package org.gestore.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gestore.view.BarraStato;
import org.gestore.view.ModelloTabella;
import org.gestore.util.IO;

import java.util.*;

import static org.gestore.view.BarraStato.IdentStato.*;

public class ModelloGestore
{
    private final ObjectMapper mapper;

    private final List<Gioco> giochi;
    private final List<AutoreGioco> autori;
    private final List<Giocatore> giocatori;

    private ModelloTabella[] modelliTabelle;

    public ModelloGestore()
    {
        giochi = new ArrayList<>();
        autori = new ArrayList<>();
        giocatori = new ArrayList<>();

        mapper = new ObjectMapper();

        precaricaIstanzeGiocatori();
    }

    public void setTabelle( ModelloTabella[] modelli )
    {
        modelliTabelle = modelli;
    }

    public List<Giocatore> getGiocatori() {return giocatori;}

    public OggettoGestore getIstanza(int id, int idRow)
    {
        OggettoGestore og = null;

        switch(id)
        {
            case 0:
            {
                if( idRow < 0 || idRow > giochi.size() )
                    return null;

                og = giochi.get(idRow);
                break;
            }

            case 1:
            {
                if( idRow < 0 || idRow > autori.size() )
                    return null;

                og = autori.get(idRow);
                break;
            }

            case 2:
            {
                if( idRow < 0 || idRow > giocatori.size() )
                    return null;

                og = giocatori.get(idRow);
                break;
            }

            default: break;
        }

        return og;
    }

    private void creaIstanzaGioco(String[] rowInfo)
    {
        String nome = rowInfo[0];
        int anno = 0;

        try {
            anno = Integer.parseInt(rowInfo[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] arrInfoAutori = rowInfo[2].split(";");
        List<String> autori = new ArrayList<>(Arrays.asList(arrInfoAutori));

        int giocatoriMin = 1;
        int giocatoriMax = 1;

        boolean multigiocatore = false;

        try {
            giocatoriMin = Integer.parseInt(rowInfo[3]);
            giocatoriMax = Integer.parseInt(rowInfo[4]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        giocatoriMin = Math.max(giocatoriMin, 1);
        giocatoriMax = Math.max(giocatoriMin, giocatoriMax);

        if (giocatoriMin > 1 || giocatoriMax > 1)
            multigiocatore = true;

        String genere = rowInfo[5];


        Gioco istanza = new Gioco(
            nome,
            anno,
            autori,
            multigiocatore,
            giocatoriMin,
            giocatoriMax,
            genere
        );

        giochi.add(istanza);
        modelliTabelle[0].addRow(istanza.toTabFields());
    }
    
    private void creaIstanzaAutore(String[] rowInfo)
    {
        String nome = rowInfo[0];
        String cognome = rowInfo[1];

        int dataNascita = 0;

        try
        {
            dataNascita = Integer.parseInt(rowInfo[2]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String bio = rowInfo[3];
        int numPremi = 0;

        try
        {
            numPremi = Integer.parseInt(rowInfo[4]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        AutoreGioco istanza = new AutoreGioco(
            nome,
            cognome,
            dataNascita,
            bio,
            numPremi
        );

        autori.add(istanza);
        modelliTabelle[1].addRow(istanza.toTabFields());
    }

    private void creaIstanzaGiocatore(String[] rowInfo)
    {
        String nome = rowInfo[0];
        String cognome = rowInfo[1];
        String nick = rowInfo[2];

        String password = rowInfo[3];

        Giocatore istanza = new Giocatore(
            nome,
            cognome,
            nick,
            password
        );

        giocatori.add(istanza);
        modelliTabelle[2].addRow(istanza.toTabFields());
    }

    private void aggiornaRiferimentiAutori()
    {
        for(AutoreGioco autore : autori)
        {
            String nome = autore.getNome();
            String cognome = autore.getCognome();

            List<Gioco> giochiCreati = new ArrayList<>(
                giochi.stream()
                    .filter(gioco -> gioco.isAutore(nome, cognome))
                    .toList()
            );

            autore.setGiochiCreati(giochiCreati);
        }
    }

    public void creaElemento(int id, String[] row)
    {
        if(!UserManager.isAdmin()) return;

        switch(id)
        {
            case 0: creaIstanzaGioco(row); break;
            case 1: creaIstanzaAutore(row); break;
            case 2: creaIstanzaGiocatore(row); break;
            default: break;
        }

        aggiornaRiferimentiAutori();
        BarraStato.setStato(ON_ELEMENTO_CREATO);
    }

    public void rimuoviElemento(int id, int idRow)
    {
        if(!UserManager.isAdmin()) return;

        switch(id)
        {
            case 0:
            {
                giochi.remove(idRow);
                modelliTabelle[0].removeRow(idRow);
                break;
            }

            case 1:
            {
                autori.remove(idRow);
                modelliTabelle[1].removeRow(idRow);
                break;
            }

            case 2:
            {
                giocatori.remove(idRow);
                modelliTabelle[2].removeRow(idRow);
                break;
            }

            default: break;
        }

        aggiornaRiferimentiAutori();
        BarraStato.setStato(ON_ELEMENTO_RIMOSSO);
    }

    public void salvaIstanze(Runnable onFinishCallback, Runnable onFailCallback)
    {
        try
        {
            String jsonGiochi = mapper.writeValueAsString(giochi.toArray(new Gioco[0]));
            String jsonAutori = mapper.writeValueAsString(autori.toArray(new AutoreGioco[0]));
            String jsonGiocatori = mapper.writeValueAsString(giocatori.toArray(new Giocatore[0]));

            IO.writeJson("giochi.json", jsonGiochi);
            IO.writeJson("autori.json", jsonAutori);
            IO.writeJson("giocatori.json", jsonGiocatori);

            if(onFinishCallback != null)
                onFinishCallback.run();
        }
        catch(Exception e)
        {
            e.printStackTrace();

            if(onFailCallback != null)
                onFailCallback.run();
        }
    }

    public void caricaIstanze(Runnable onFinishCallback, Runnable onFailCallback) throws Exception
    {
        String jsonGiochi = "";
        String jsonAutori = "";

        try
        {
            jsonGiochi = IO.readJson("giochi.json");
            jsonAutori = IO.readJson("autori.json");

            if(onFinishCallback != null)
                onFinishCallback.run();
        }
        catch(Exception e)
        {
            e.printStackTrace();

            if(onFailCallback != null)
                onFailCallback.run();
        }

        {
            if(jsonGiochi == null)
                return;

            Gioco[] arrIstanze = mapper.readValue(jsonGiochi, Gioco[].class);

            for (Gioco oggetto : arrIstanze) {
                giochi.add(oggetto);
                modelliTabelle[0].addRow(oggetto.toTabFields());
            }
        }

        {
            if(jsonAutori == null)
                return;

            AutoreGioco[] arrIstanze = mapper.readValue(jsonAutori, AutoreGioco[].class);

            for (AutoreGioco oggetto : arrIstanze) {
                autori.add(oggetto);
                modelliTabelle[1].addRow(oggetto.toTabFields());
            }
        }

        {
            for (Giocatore oggetto : giocatori) {
                modelliTabelle[2].addRow(oggetto.toTabFields());
            }
        }

        aggiornaRiferimentiAutori();
    }

    public void precaricaIstanzeGiocatori()
    {
        try
        {
            String jsonGiocatori = IO.readJson("giocatori.json");

            if(jsonGiocatori == null)
                return;

            Giocatore[] arrIstanze = mapper.readValue(jsonGiocatori, Giocatore[].class);
            giocatori.addAll(Arrays.asList(arrIstanze));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}