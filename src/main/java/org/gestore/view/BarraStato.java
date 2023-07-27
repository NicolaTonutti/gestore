package org.gestore.view;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BarraStato extends JLabel
{
    private static BarraStato istanza = null;

    public enum IdentStato
    {
        IN_CREAZIONE                        ("Creazione nuovo elemento"),
        IN_VISUALIZZAZIONE                  ("Visualizzazione elemento"),

        ON_CARICAMENTO_COMPLETATO           ("Caricamento elementi completato"),
        ON_CARICAMENTO_FALLITO              ("Caricamento elementi fallito"),

        ON_SALVATAGGIO_COMPLETATO_A         ("Salvataggio automatico completato"),
        ON_SALVATAGGIO_FALLITO_A            ("Salvataggio automatico fallito"),

        ON_ELEMENTO_CREATO                  ("Elemento creato"),
        ON_ELEMENTO_RIMOSSO                 ("Elemento rimosso"),

        ON_DESIDERIO_AGGIUNTO               ("Elemento aggiunto ai desideri"),
        ON_DESIDERIO_ESISTENTE              ("Elemento gia' nella lista dei desideri"),

        ON_ELEMENTO_ACQUISTATO              ("Elemento acquistato con successo"),

        STATO                              ("Stato");


        private final String labelText;

        IdentStato(String labelText)
        {
            this.labelText = labelText;
        }
    }

    private BarraStato()
    {
        setText(IdentStato.STATO.labelText);
    }

    public static BarraStato get()
    {
        if(istanza == null)
            istanza = new BarraStato();

        return istanza;
    }

    public static void setStato(IdentStato identStato)
    {
        if(istanza == null)
            return;

        if(identStato == null)
            return;

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();

        String s = "";
        s += dateFormat.format(calendar.getTime());
        s += " | ";
        s += identStato.labelText;

        istanza.setText(s);
    }
}