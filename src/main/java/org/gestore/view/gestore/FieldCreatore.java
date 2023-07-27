package org.gestore.view.gestore;

import javax.swing.*;
import java.awt.*;

public class FieldCreatore extends JPanel
{
    private JLabel label;
    private JTextField textField;

    public FieldCreatore(String nomeField)
    {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(240, 24));

        label = new JLabel(nomeField);
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(120, 24));

        add(label, BorderLayout.LINE_START);
        add(textField, BorderLayout.LINE_END);
    }

    public String getText()
    {
        return textField.getText();
    }
}