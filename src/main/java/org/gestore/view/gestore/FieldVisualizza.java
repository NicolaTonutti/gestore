package org.gestore.view.gestore;

import javax.swing.*;
import java.awt.*;

public class FieldVisualizza extends JPanel
{
    private JLabel nome;
    private JLabel info;

    public FieldVisualizza(String nome, String info)
    {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(240, 24));

        this.nome = new JLabel(nome);
        this.info = new JLabel(info);

        add(this.nome, BorderLayout.LINE_START);
        add(this.info, BorderLayout.LINE_END);
    }
}