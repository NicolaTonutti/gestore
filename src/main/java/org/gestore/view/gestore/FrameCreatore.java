package org.gestore.view.gestore;

import org.gestore.controller.ControllerGestore;
import org.gestore.view.BarraStato;
import org.gestore.model.UserManager;
import org.gestore.util.IO;

import javax.swing.*;
import java.awt.*;

import static org.gestore.view.BarraStato.IdentStato.*;

public class FrameCreatore extends JFrame
{
    public FrameCreatore(ControllerGestore controller)
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Crea nuovo");
        setIconImage(IO.loadImage("/icon.png"));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.anchor = GridBagConstraints.LINE_START;

        if(UserManager.isAdmin())
        {
            int idTabellaAttiva = controller.getIdTabellaAttiva();
            FieldCreatore[] arrFieldCreatore;

            if(idTabellaAttiva == 0)
            {
                arrFieldCreatore = creatoreGioco();
            }
            else if(idTabellaAttiva == 1)
            {
                arrFieldCreatore = creatoreAutore();
            }
            else if(idTabellaAttiva == 2)
            {
                arrFieldCreatore = creatoreGiocatore();
            }
            else
            {
                arrFieldCreatore = new FieldCreatore[0];
            }

            for(FieldCreatore fc : arrFieldCreatore)
            {
                add(fc, gbc);
                gbc.gridy += 1;
            }

            gbc.fill = GridBagConstraints.HORIZONTAL;

            JButton creaBtn = new JButton("Crea");
            creaBtn.addActionListener((e) -> {
                controller.creaElementoTabellaAttiva(arrFieldCreatore);
                dispose();
            });
            add(creaBtn, gbc);
        }

        gbc.gridy += 1;

        JButton annullaBtn = new JButton("Annulla");
        annullaBtn.addActionListener((e) -> dispose());
        add(annullaBtn, gbc);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        getContentPane().requestFocusInWindow();

        BarraStato.setStato(IN_CREAZIONE);
    }

    private FieldCreatore[] creatoreGioco()
    {
        return new FieldCreatore[] {
            new FieldCreatore("Nome"),
            new FieldCreatore("Anno"),
            new FieldCreatore("Autori"),
            new FieldCreatore("Giocatori Min"),
            new FieldCreatore("Giocatori Max"),
            new FieldCreatore("Genere")
        };
    }

    private FieldCreatore[] creatoreAutore()
    {
        return new FieldCreatore[] {
            new FieldCreatore("Nome"),
            new FieldCreatore("Cognome"),
            new FieldCreatore("Data di Nascita"),
            new FieldCreatore("Biografia"),
            new FieldCreatore("Numero Premi Vinti")
        };
    }

    private FieldCreatore[] creatoreGiocatore()
    {
        return new FieldCreatore[] {
            new FieldCreatore("Nome"),
            new FieldCreatore("Cognome"),
            new FieldCreatore("Nickname"),
            new FieldCreatore("Password")
        };
    }
}