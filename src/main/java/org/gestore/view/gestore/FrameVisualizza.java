package org.gestore.view.gestore;

import org.gestore.controller.ControllerGestore;
import org.gestore.view.BarraStato;
import org.gestore.util.IO;

import javax.swing.*;
import java.awt.*;

import static org.gestore.view.BarraStato.IdentStato.*;

public class FrameVisualizza extends JFrame
{
    public FrameVisualizza(ControllerGestore controller, int row)
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Visualizza");
        setIconImage(IO.loadImage("/icon.png"));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.anchor = GridBagConstraints.LINE_START;

        int idTabellaAttiva = controller.getIdTabellaAttiva();
        FieldVisualizza[] fields = null;

        switch(idTabellaAttiva)
        {
            case 0: fields = visualizzaGioco(controller.getInfoIstanzaSelezionata(row)); break;
            case 1: fields = visualizzaAutore(controller.getInfoIstanzaSelezionata(row)); break;
            case 2: fields = visualizzaGiocatore(controller.getInfoIstanzaSelezionata(row)); break;
            default: break;
        }

        if(fields != null)
        {
            for(FieldVisualizza fv : fields)
            {
                add(fv, gbc);
                gbc.gridy += 1;
            }
        }

        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton chiudiBtn = new JButton("Chiudi");
        chiudiBtn.addActionListener((e) -> dispose());
        add(chiudiBtn, gbc);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        getContentPane().requestFocusInWindow();

        BarraStato.setStato(IN_VISUALIZZAZIONE);
    }

    private FieldVisualizza[] visualizzaGioco(String[] info)
    {
        if(info == null)
            return null;

        return new FieldVisualizza[] {
            new FieldVisualizza("Nome: ", info[0]),
            new FieldVisualizza("Anno: ", info[1]),
            new FieldVisualizza("Autori: ", info[2]),
            new FieldVisualizza("Multigiocatore: ", info[3]),
            new FieldVisualizza("Giocatori Min: ", info[4]),
            new FieldVisualizza("Giocatori Max: ", info[5]),
            new FieldVisualizza("Genere: ", info[6])
        };
    }

    private FieldVisualizza[] visualizzaAutore(String[] info)
    {
        if(info == null)
            return null;

        return new FieldVisualizza[] {
            new FieldVisualizza("Nome: ", info[0]),
            new FieldVisualizza("Cognome: ", info[1]),
            new FieldVisualizza("Data di Nascita: ", info[2]),
            new FieldVisualizza("Biografia: ", info[3]),
            new FieldVisualizza("Numero Premi Vinti: ", info[4]),
            new FieldVisualizza("Giochi Realizzati: ", info[5])
        };
    }

    private FieldVisualizza[] visualizzaGiocatore(String[] info)
    {
        if(info == null)
            return null;

        return new FieldVisualizza[] {
            new FieldVisualizza("Nome: ", info[0]),
            new FieldVisualizza("Cognome: ", info[1]),
            new FieldVisualizza("Nickname: ", info[2]),
            new FieldVisualizza("Acquisti: ", info[3]),
            new FieldVisualizza("Desideri: ", info[4])
        };
    }
}