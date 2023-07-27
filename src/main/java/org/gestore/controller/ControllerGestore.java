package org.gestore.controller;

import org.gestore.model.*;
import org.gestore.view.BarraStato;
import org.gestore.view.ModelloTabella;
import org.gestore.view.gestore.FieldCreatore;
import org.gestore.model.UserManager;

import static org.gestore.view.BarraStato.IdentStato.*;

public class ControllerGestore
{
    private final ModelloGestore modelloGestore;
    private final ModelloTabella[] modelliTabelle;
    private int idTabellaAttiva;
    private ThreadAutosalva threadAutosalva;

    public ControllerGestore(ModelloGestore modelloGestore)
    {
        this.modelloGestore = modelloGestore;

        modelliTabelle = new ModelloTabella[] {
            new ModelloTabella(Gioco.TAB_FIELDS),
            new ModelloTabella(AutoreGioco.TAB_FIELDS),
            new ModelloTabella(Giocatore.TAB_FIELDS)
        };
    }

    public void init()
    {
        modelloGestore.setTabelle( modelliTabelle );

        this.idTabellaAttiva = 0;

        try
        {
            modelloGestore.caricaIstanze(
                () -> BarraStato.setStato(ON_CARICAMENTO_COMPLETATO),
                () -> BarraStato.setStato(ON_CARICAMENTO_FALLITO)
            );
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        this.threadAutosalva = new ThreadAutosalva(modelloGestore, 10000);
        threadAutosalva.start();
    }

    public void salvaIstanze()
    {
        modelloGestore.salvaIstanze(null, null);
    }

    public void stopAutosalva()
    {
        threadAutosalva.termina();
    }

    public int getIdTabellaAttiva() {return idTabellaAttiva;}
    public void setIdTabellaAttiva(int id) {idTabellaAttiva = id;}

    public String[] getInfoIstanzaSelezionata(int row)
    {
        OggettoGestore istanza = modelloGestore.getIstanza(idTabellaAttiva, row);

        if(istanza == null)
            return new String[0];

        return istanza.toStringFields();
    }

    public ModelloTabella getModelloTabella(int id)
    {
        if(id < 0 || id >= modelliTabelle.length)
            return null;

        return modelliTabelle[id];
    }

    public void creaElementoTabellaAttiva(FieldCreatore[] arrFieldCreatore)
    {
        String[] row = new String[arrFieldCreatore.length];

        for(int i=0; i<row.length; ++i)
        {
            String txt = arrFieldCreatore[i].getText();
            txt = txt.strip();

            if(txt.isBlank())
                return;

            row[i] = txt;
        }

        modelloGestore.creaElemento( idTabellaAttiva, row );
    }

    public void rimuoviElementoTabellaAttiva(int row)
    {
        if(row < 0 || row >= modelliTabelle[idTabellaAttiva].getRowCount())
            return;

        modelloGestore.rimuoviElemento(idTabellaAttiva, row);
    }

    public void faiAcquisto(int row)
    {
        Gioco istanza = (Gioco) modelloGestore.getIstanza(0, row);

        if(istanza == null)
            return;

        User user = UserManager.get().loggedUser;
        user.acquista(istanza);
    }

    public void aggiungiDesiderio(int row)
    {
        Gioco istanza = (Gioco) modelloGestore.getIstanza(0, row);

        if(istanza == null)
            return;

        User user = UserManager.get().loggedUser;
        user.desidera(istanza);
    }
}