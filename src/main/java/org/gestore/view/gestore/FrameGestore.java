package org.gestore.view.gestore;

import org.gestore.controller.ControllerGestore;
import org.gestore.view.BarraStato;
import org.gestore.model.UserManager;
import org.gestore.util.IO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FrameGestore extends JFrame
{
    private final JTable[] tabelle;
    private int tabellaAttiva;

    private final ControllerGestore controller;

    public FrameGestore(ControllerGestore controller)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gestore");
        setIconImage(IO.loadImage("/icon.png"));

        addWindowListener(new FrameGestoreListener(this));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JTabbedPane tabbedPane = new JTabbedPane();

        this.controller = controller;

        this.tabelle = new JTable[] {
            new JTable(), new JTable(), new JTable()
        };

        this.tabelle[0].setModel(controller.getModelloTabella(0));
        this.tabelle[1].setModel(controller.getModelloTabella(1));
        this.tabelle[2].setModel(controller.getModelloTabella(2));

        JScrollPane panGiochi = new JScrollPane(this.tabelle[0]);
        JScrollPane panAutori = new JScrollPane(this.tabelle[1]);
        JScrollPane panGiocatori = new JScrollPane(this.tabelle[2]);

        if(!UserManager.isAdmin())
        {
            JPanel tabGiochi = new JPanel(new GridBagLayout());
            GridBagConstraints gbc1 = new GridBagConstraints();
            gbc1.gridx = 0;
            gbc1.gridy = 0;
            tabGiochi.add(panGiochi, gbc1);

            gbc1.fill = GridBagConstraints.HORIZONTAL;
            gbc1.anchor = GridBagConstraints.LINE_START;
            gbc1.gridy = 1;

            JButton btnWish = new JButton("Desidera...");
            btnWish.addActionListener((e) -> controller.aggiungiDesiderio(tabelle[tabellaAttiva].getSelectedRow()));
            tabGiochi.add(btnWish, gbc1);

            gbc1.gridy = 2;

            JButton btnBuy = new JButton("Acquista...");
            btnBuy.addActionListener((e) -> controller.faiAcquisto(tabelle[tabellaAttiva].getSelectedRow()));
            tabGiochi.add(btnBuy, gbc1);

            tabbedPane.addTab("Giochi", tabGiochi);
        }
        else
        {
            tabbedPane.addTab("Giochi", panGiochi);
        }

        tabbedPane.addTab("Autori", panAutori);
        tabbedPane.addTab("Giocatori", panGiocatori);

        this.tabellaAttiva = 0;
        controller.setIdTabellaAttiva(0);

        tabbedPane.addChangeListener((e) -> {
            this.tabellaAttiva = tabbedPane.getSelectedIndex();
            controller.setIdTabellaAttiva(this.tabellaAttiva);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.anchor = GridBagConstraints.LINE_START;
        add(new JLabel("Utente: " + UserManager.getLoggedUserInfo()));
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy += 1;
        add(tabbedPane, gbc);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridBagLayout());
        {
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.insets = new Insets(4, 0, 4, 0);
            gbc2.fill = GridBagConstraints.HORIZONTAL;
            gbc2.gridwidth = GridBagConstraints.REMAINDER;

            if(UserManager.isAdmin())
            {
                JButton btnAdd = new JButton("Crea...");
                btnAdd.addActionListener((e) -> new FrameCreatore(controller));
                btnPanel.add(btnAdd, gbc2);
            }

            JButton btnView = new JButton("Visualizza...");
            btnView.addActionListener((e) -> {
                int selectedRow = tabelle[tabellaAttiva].getSelectedRow();

                if(selectedRow == -1)
                    return;

                new FrameVisualizza(controller, selectedRow);
            });
            btnPanel.add(btnView, gbc2);

            if(UserManager.isAdmin())
            {
                JButton btnRemove = new JButton("Elimina");
                btnRemove.addActionListener((e) -> {
                    int len = tabelle[tabellaAttiva].getSelectedRowCount();

                    for(int i=0; i<len; ++i)
                        controller.rimuoviElementoTabellaAttiva(tabelle[tabellaAttiva].getSelectedRow());
                });
                btnPanel.add(btnRemove, gbc2);
            }
        }

        gbc.gridx += 1;
        add(btnPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets.top = 0;
        add(BarraStato.get(), gbc);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        getContentPane().requestFocusInWindow();
    }

    public ControllerGestore getController() {return controller;}

    private class FrameGestoreListener implements WindowListener
    {
        private final FrameGestore frameGestore;

        FrameGestoreListener(FrameGestore frameGestore)
        {
            this.frameGestore = frameGestore;
        }

        @Override public void windowClosing(WindowEvent e)
        {
            frameGestore.getController().salvaIstanze();

            frameGestore.dispose();
            controller.stopAutosalva();
            System.exit(0);
        }


        @Override public void windowOpened(WindowEvent e) {}
        @Override public void windowClosed(WindowEvent e) {}
        @Override public void windowIconified(WindowEvent e) {}
        @Override public void windowDeiconified(WindowEvent e) {}
        @Override public void windowActivated(WindowEvent e) {}
        @Override public void windowDeactivated(WindowEvent e) {}
    }
}